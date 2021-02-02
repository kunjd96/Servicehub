const jwt = require("jsonwebtoken");
const asyncHandler = require("./async");
const User = require("../models/user");
const ErrorResponse = require('../utils/errorResponse');

exports.protect = asyncHandler(async(req, res, next) => {
    let token;
    if (req.headers.authorization && req.headers.authorization.startsWith("Bearer")) {
        token = req.headers.authorization.split(" ")[1];
    }

    if (!token) {
        next(new ErrorResponse("Not Authorised to access", 400));
    }
    try {
        //verify token
        const decoded = jwt.verify(token, process.env.JWT_SECRET);
        console.log(decoded);
        req.user = await User.findById(decoded.id);

        next();
    } catch (error) {
        return next(new ErrorResponse("Not Authorised to access", 400));
    }
});