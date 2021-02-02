const ErrorResponse = require('../utils/errorResponse');

const errorHandler = (err, req, res, next) => {
    let errordata = {...err };

    errordata.message = err.message;

    // Mongoose bad ObjectId
    if (err.name === 'CastError') {
        const message = `Resource not found`;
        errordata = new ErrorResponse(message, 404);
    }
    // Mongoose duplicate key
    if (err.code === 11000) {
        const message = 'Duplicate field value entered';
        errordata = new ErrorResponse(message, 400);
    }

    // Mongoose validation error
    if (err.name === 'ValidationError') {
        const message = Object.values(err.errors).map(val => val.message);
        errordata = new ErrorResponse(message, 400);
    }

    res.status(errordata.status || 500).json({
        success: false,
        error: errordata.message || 'Server Error'
    });
};

module.exports = errorHandler;