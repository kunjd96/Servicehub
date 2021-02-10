const mongoose = require("mongoose");

const AppointmentSchema = new mongoose.Schema({
    clientId: {
        type: mongoose.Schema.ObjectId,
        ref: "User",
        required: true
    },
    serviceProviderId: {
        type: mongoose.Schema.ObjectId,
        ref: "User",
        required: true
    },
    serviceSummary: {
        type: String,
        required: true
    },
    preferedClientTime: {
        type: String,
        required: true
    },
    appointmentDate: {
        type: Date,
        default: Date.now
    },
    appoinmentStatus: {
        type: String,
        enum: [
            "Waiting For Service Provider Time",
            "Waiting For Client Confirmation",
        ],
        default: "Waiting For Service Provider Time"
    },
    serviceProviderTimeAssign: {
        type: Boolean,
        default: false
    },
    serviceProviderReject: {
        type: Boolean,
        default: false
    },
    serviceRejectSummary: {
        type: String
    },
    serviceDate: {
        type: Date
    },
    serviceTimeSlots: [{
        "startTime": Date,
        "endTime": Date,
        "indexpoint": Number
    }],
    serviceStartTime: {
        type: Date
    },
    serviceEndTime: {
        type: Date
    },
    clientConfirm: {
        type: Boolean,
        default: false
    },
    clientRejected: {
        type: Boolean,
        default: false
    },
    clientRejectSummary: {
        type: String
    },
    appoinmentSchedule: {
        type: Boolean,
        default: false
    },
    invoiceGenrated: {
        type: Boolean,
        default: false
    }
});

module.exports = mongoose.model("Appointment", AppointmentSchema);