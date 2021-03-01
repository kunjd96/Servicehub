const asyncHandler = require("../middleware/async");
const ErrorResponse = require("../utils/errorResponse");
const Rate = require('../models/rateandcomments');
const User = require('../models/user');

exports.addandupdaterating = asyncHandler(async(req, res, next) => {
    const user = await User.findById(req.body.byUserId);
    let rate;
    if (!user) {
        return next(
            new ErrorResponse(
                `User not valid`,
                401
            )
        );
    } else {
        req.body.byrole = user.role;
        console.log(req.body);
        rate = await Rate.create(req.body);
    }
    res.status(200).json({ success: true, rate: rate });
});

exports.getRatingodAppointment = asyncHandler(async(req, res, next) => {

    const rate = await Rate.find({
        appointment: req.params.id
    }).populate("byUserId").populate("toUserId").populate("appointment");

    if (!rate) {
        return next(
            new ErrorResponse(
                `Rating not avaliable for this appointment`,
                401
            )
        );
    }
    res.status(200).json({ success: true, rate: rate });
});

exports.getAllReviewOfUsers = asyncHandler(async(req, res, next) => {
    const rate = await Rate.find({
        toUserId: req.params.id
    }).populate("byUserId").populate("toUserId").populate("appointment");

    if (!rate) {
        return next(
            new ErrorResponse(
                `Rating not avaliable for this appointment`,
                401
            )
        );
    }
    res.status(200).json({ success: true, rate: rate });
});