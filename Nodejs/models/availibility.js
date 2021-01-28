const mongoose = require("mongoose");
const user = require("./user");

const AvailibilitySchema = new mongoose.Schema({
    dayName : {
        type : String,
        required : true
    },
    startTime : {
        type : Date,
        required : true
    },
    endTime : {
        type : Date,
        required : true
    },
    createdBy : {
        type : mongoose.Schema.ObjectId,
        ref : "User"
        
    },
    isDated : {
        type : Date,
        default : Date.now
    },
    modifyDate : {
        type : Date
    },
    user : {
        type : mongoose.Schema.ObjectId,
        ref : "User",
        required : true
    }
});

module.exports = mongoose.model("Avaibility",AvailibilitySchema);