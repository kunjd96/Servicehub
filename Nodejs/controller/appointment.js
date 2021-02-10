const asyncHandler = require("../middleware/async");
const ErrorResponse = require("../utils/errorResponse");
const Appointment = require('../models/appointment');


exports.addAppointment = asyncHandler(async(req, res, next) => {
    const appointment = await Appointment.create(req.body);
    return res.status(200).json({
        success: true,
        data: appointment
    });
});


exports.updateAppointment = asyncHandler(async(req, res, next) => {

});


exports.getAllAppoinment = asyncHandler(async(req, res, next) => {

    const appointment = await Appointment.find().populate("clientId");
    return res.status(200).json({
        success: true,
        data: appointment
    });

});



exports.getAllAppoinmentUser = asyncHandler(async(req, res, next) => {
    const userid = req.user.id;
    let appointment = [];
    if (req.user.role == "Customer") {
        appointment = await Appointment.find({
            clientId: req.user.id
        }).populate("clientId").populate("serviceProviderId");
    } else {
        appointment = await Appointment.find({
            serviceProviderId: req.user.id
        }).populate("clientId").populate("serviceProviderId");
    }

    return res.status(200).json({
        success: true,
        data: appointment
    });

});