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
    let appointment = await Appointment.findById(req.params.id);
    if (!appointment) {
        return next(
            new ErrorResponse(`Appointment not found with id of ${req.params.id}`, 404)
        );
    }
    if (req.user.role == "Customer") {
        if (appointment.clientId.toString() !== req.user.id) {
            return next(
                new ErrorResponse(
                    `User ${req.params.id} is not authorized to update this Appointment`,
                    401
                )
            );
        }
    } else {
        if (appointment.serviceProviderId.toString() !== req.user.id) {
            return next(
                new ErrorResponse(
                    `User ${req.params.id} is not authorized to update this Appointment`,
                    401
                )
            );
        }
    }
    appointment = await Appointment.findByIdAndUpdate(req.params.id, req.body, {
        new: true,
        runValidators: true,
    });
    res.status(200).json({ success: true, data: appointment });
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