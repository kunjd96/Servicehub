const mongoose = require("mongoose");

const ServicesSchema = new mongoose.Schema({
    serviceName: {
        type : String,
        required : true,
    },
    createdDate : {
        type : Date,
        default:Date.now
    },
    user:{
        type : mongoose.Schema.ObjectId,
        ref : "user",
        required : true
    },
    slugServiceName : String,
    isDelete : {
        type : Boolean,
        default : false
    }
});

module.exports = mongoose.model("Services", ServicesSchema);