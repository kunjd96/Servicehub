const mongoose = require("mongoose");
const crypto = require("crypto");
const bcrypt = require("bcrypt");
const jwt = require("jsonwebtoken");
const geocoder = require("../utils/geocoder");
const slugify = require("slugify");

const Float = require('mongoose-float').loadType(mongoose, 2);

const InvoiceSchema = new mongoose.Schema({
    Date: {
        type: Date,
        default: Date.now
    },
    ServiceProviderName: String,
    ServiceProviderId: {
        type: mongoose.Schema.ObjectId,
        ref: "User"
    },
    CutomerName: String,
    CustomerID: {
        type: mongoose.Schema.ObjectId,
        ref: "User"
    },
    totalAmount: Number,
    appointment: {
        type: mongoose.Schema.ObjectId,
        ref: "Appointment"
    }
}, {
    toJSON: { virtuals: true },
    toObject: { virtuals: true }
});


//reverse populate with virtual
InvoiceSchema.virtual("charges", {
    ref: "Charges",
    localField: "_id",
    foreignField: "InvoiceId",
    justOne: false
});





module.exports = mongoose.model("Invoice", InvoiceSchema);