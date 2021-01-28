const jwt = require ("jsonwebtoken");
const ErrorResponse = require("../utils/errorResponse");
const asyncHandler = require ("./async");
const User = require("../models/user");


exports.protect = asyncHandler (async (req,res,next) => { 
    let token;
    if (req.headers.authorization && req.headers.authorization.startsWith('Bearer')) {
        token = req.headers.authorization.split(' ')[1];
    }

    if (!token) {
        return next(new ErrorResponse('Token Not exist', 401));
    }
    try {
        const decoded = jwt.verify(token, process.env.JWT_SECRET);
        req.user = await User.findById(decoded.id);
        next();
    } catch (error) {
        return next(new ErrorResponse('Token Not exist', 401));
    }
});