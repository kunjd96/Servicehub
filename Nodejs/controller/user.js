const asyncHandler = require("../middleware/async");
//const Services = require("../models/Services");
const User = require("../models/user");
const ErrorResponse = require("../utils/errorResponse");
const crypto = require("crypto");
const sendEmail = require("../utils/sendEmail");
const availibility = require("../models/availibility");


// @desc            Register User
// @routes          POST /api/v1/auth/register
// access           Public

exports.register = asyncHandler(async(req, res, next) => {
    const user = await User.create(req.body);
    if (req.body.role == "ServiceProvider") {
        const daysdata = req.body.daysdata;
        Date.prototype.addHours = function(h) {
            this.setHours(this.getHours() + h);
            return this;
        }

        for (let i = 0; i < daysdata.length; i++) {
            let data = daysdata[i];
            let allTimeSlote = [];
            data.user = user.id;
            const starttime = new Date(daysdata[i].startTime);
            const endtime = new Date(daysdata[i].endTime);
            for (let timeindex = 1; endtime > starttime; timeindex++) {
                const timeData = {};
                timeData.startTime = new Date(starttime);
                timeData.endTime = new Date(starttime.addHours(1));
                timeData.indexpoint = timeindex;
                allTimeSlote.push(timeData);
            }
            data.allTimeSlots = allTimeSlote;
            await availibility.create(data);
        }
    }
    const validateToken = user.getActiveStatus();
    await user.save({ validateBeforeSave: false });
    const verifiedUrl = `${req.protocol}://${req.get('host')}/api/v1/auth/validateUser/${validateToken}`;
    const message = `Verified Link ${verifiedUrl}`;
    const html = `<p>Click <a href="${verifiedUrl}">here</a> to verifie your Account</p>`;
    try {
        await sendEmail({
            email: user.email,
            subject: "Verify Your Account",
            message,
            html
        });
        res.status(200).json({ success: true, data: "Email sent" });
    } catch (err) {
        console.log(err);
        user.activeUserToken = undefined;
        user.activeUserTokenExpire = undefined;

        await user.remove();

        next(new ErrorResponse("Email could not be sent", 500));
    }
    res.status(200).json({ success: true, data: user });
});



/// junk 

exports.registerUser = asyncHandler(async(req, res, next) => {
    const user = await User.create(req.body);
    const token = user.getverifiedToken();
    await user.save({ validateBeforeSave: false });
    const verifiedUrl = `${req.protocol}://${req.get('host')}/servicehub/V1/auth/activeAccount/${token}`;
    const message = `Verified Link ${verifiedUrl}`;
    const html = `<p>Click <a href="${verifiedUrl}">here</a> to verifie your Account</p>`;
    try {
        await sendEmail({
            email: user.email,
            subject: 'Verified token',
            message,
            html
        });
        res.status(200)
            .json({
                success: true,
                data: 'Email Sent'
            });
    } catch (error) {
        console.log(error);
        user.remove();
        return next(new ErrorResponse('Eamil Not Sent', 500));
    }

});


///



// @desc            Login User
// @routes          POST /api/v1/auth/login
// access           Public

exports.login = asyncHandler(async(req, res, next) => {
    const { email, password } = req.body;

    //Validate email & password
    if (!email || !password) {
        return next(new ErrorResponse("Please provide email and Password", 400));
    }

    //Check for User
    const user = await User.findOne({ email: email }).select("+password");

    if (!user) {
        return next(new ErrorResponse("User not exsist ", 400));
    }

    if (user.Match() === false) {
        return next(new ErrorResponse("Email address not verifired!!", 400));
    }


    //Check if password is matches
    const isMatch = await user.matchPassword(password);

    if (!isMatch) {
        return next(new ErrorResponse("Invalid credential ", 400));
    }

    //Create token 
    const token = user.getSignedJwtToken();
    res.status(200).json({ success: true, token });

});





// @desc            Update User
// @routes          POST /api/v1/auth/update/:id
// access           Private
exports.updateUser = asyncHandler(async(req, res, next) => {
    const user = await User.findByIdAndUpdate(req.params.id, req.body, {
        new: true,
        runValidators: true,
    });
    if (!user) {
        return next(new ErrorResponse("Bootcamp not found with id of ", 404));
    }
    res.status(200).json({ success: true, data: user });
});


// @desc            Delete User
// @routes          DELETE /api/v1/auth/:id
// access           Private
exports.deleteUser = asyncHandler(async(req, res, next) => {
    const user = await User.findByIdAndDelete(req.params.id);
    if (!user) {
        return next(new ErrorResponse("User not found with id of ", 404));
    }
    res.status(200).json({ success: true, data: {} });
});

// @desc            Delete Bootcamp
// @routes          POST /api/v1/auth/getMe
// access           Private

exports.getMe = asyncHandler(async(req, res, next) => {
    // let query;
    // const reqQuery = {...req.query};
    // let queryStr = JSON.stringify(reqQuery);
    // query = User.find(JSON.parse(queryStr));//.populate("availibility");
    const user = await User.findById(req.user.id).populate("availibility");
    res.status(200).json({ success: true, data: user });
});

// @desc            Reset Password
// @routes          PUT /api/v1/auth/resetPassword/:resetToken
// access           Public

exports.resetPassowrd = asyncHandler(async(req, res, next) => {

    //Get Hashed Token
    const ResetPasswordToken = crypto.createHash("sha256").update(req.params.resettoken).digest("hex");


    const user = await User.findOne({
        ResetPasswordToken,
        resetPasswordExpired: { $gt: Date.now() }
    });

    if (!user) {
        return next(new ErrorResponse("Invalid Token", 400));
    }

    //Set Password
    user.password = req.body.password;
    user.resetPasswordToken = undefined;
    user.resetPasswordExpired = undefined;
    await user.save();


    const token = user.getSignedJwtToken();
    res.status(200).json({ success: true, token });

});





// @desc            Validate User
// @routes          PUT /api/v1/auth/validateUser/:validateToken
// access           Public
exports.validateUser = asyncHandler(async(req, res, next) => {

    //Get Hashed Token
    const activeUserToken = crypto.createHash("sha256").update(req.params.validatetoken).digest("hex");


    const user = await User.findOne({
        activeUserToken,
        activeUserTokenExpire: { $gt: Date.now() },
    });

    if (!user) {
        return next(new ErrorResponse("Invalid Token", 400));
    }

    //Set Password
    user.isActive = true;
    user.resetPasswordToken = undefined;
    user.resetPasswordExpired = undefined;
    await user.save();


    const token = user.getSignedJwtToken();
    res.status(200).json({ success: true, data: "Acctount is active ...Please Login.." });
});



// @desc            Forgot Password
// @routes          POST /api/v1/auth/forgotPassword
// access           Public
exports.forgotPassword = asyncHandler(async(req, res, next) => {
    const user = await User.findOne({ email: req.body.email });

    if (!user) {
        return next(new ErrorResponse("There is no user with this email address", 404));
    }

    //get Reset Token
    const resetToken = user.getResetPasswordToken();

    await user.save({ validateBeforeSave: false });

    //Create reset URL
    const resetUrl = req.protocol + `:/` + `/` + req.get("host") + "/api/v1/auth/resetpassowrd/" + resetToken;

    const message = "You are eceiving this email because you (or someone else) has requested the reset of a password. Please make a PUT request to:\n\n" + resetUrl;

    try {
        await sendEmail({
            email: user.email,
            subject: "Password reset Token",
            message
        });

        res.status(200).json({ success: true, data: "Email sent" });
    } catch (err) {
        console.log(err);
        user.resetPasswordToken = undefined;
        user.resetPasswordExpired = undefined;

        await user.save({ validateBeforeSave: false });

        return next(new ErrorResponse("Email could not be sent", 500));
    }

    console.log(resetToken);
    res.status(200).json({ success: true, data: user });


});


// @desc            Verify User
// @routes          POST /api/v1/auth/verifyuser
// access           Public
exports.verifyUser = asyncHandler(async(req, res, next) => {

    const user = await User.findOne({ email: req.body.email });

    if (!user) {
        return next(new ErrorResponse("There is no user with this email address", 404));
    }

    //get Validate Token
    const validateToken = user.getActiveStatus();

    await user.save({ validateBeforeSave: false });

    //Create reset URL
    const resetUrl = req.protocol + `:/` + `/` + req.get("host") + "/api/v1/auth/validateUser/" + validateToken;

    const message = "You are eceiving this email because you (or someone else) has requested the reset of a password. Please make a PUT request to:\n\n" + resetUrl;

    try {
        await sendEmail({
            email: user.email,
            subject: "Verify Your Account",
            message
        });

        res.status(200).json({ success: true, data: "Email sent" });
    } catch (err) {
        console.log(err);
        user.activeUserToken = undefined;
        user.activeUserTokenExpire = undefined;

        await user.save({ validateBeforeSave: false });

        return next(new ErrorResponse("Email could not be sent", 500));
    }

    console.log(ActiveStatus);
    res.status(200).json({ success: true, data: user });

});