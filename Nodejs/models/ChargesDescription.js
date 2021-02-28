const mongoose = require("mongoose");
const crypto = require("crypto");
const bcrypt = require("bcrypt");
const jwt = require("jsonwebtoken");
const geocoder = require ("../utils/geocoder");
const slugify = require("slugify");

const Float = require('mongoose-float').loadType(mongoose, 2);

const ChargesSchema = new mongoose.Schema({
    InvoiceId : {
        type : mongoose.Schema.ObjectId,
        ref : "Invoice"
    },
    ChargesDescription : String,
    price : Float
});



module.exports = mongoose.model("Charges",ChargesSchema);