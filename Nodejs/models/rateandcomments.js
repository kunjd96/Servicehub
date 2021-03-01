const mongoose = require("mongoose");
const User = require("../models/user");


const Float = require('mongoose-float').loadType(mongoose, 2);

const RateSchema = new mongoose.Schema({
    toRating: {
        type: Number,
        default: 0
    },
    toComment: {
        type: String
    },
    byUserId: {
        type: mongoose.Schema.ObjectId,
        ref: "User",
        required: true
    },
    toUserId: {
        type: mongoose.Schema.ObjectId,
        ref: "User",
        required: true
    },
    byrole: String,
    appointment: {
        type: mongoose.Schema.ObjectId,
        ref: "Appointment"
    }
});

// Static method to get avg rating and save
RateSchema.statics.getAverageRating = async function(userid) {

    const obj = await this.aggregate([{
            $match: { toUserId: userid }
        },
        {
            $group: {
                _id: '$toUserId',
                averageRating: { $avg: '$toRating' }
            }
        }
    ]);
    try {
        const user = await User.findByIdAndUpdate({
            _id: userid
        }, {
            averageRating: obj[0].averageRating
        });

    } catch (err) {
        console.error(err);
    }
};

RateSchema.pre('updateOne', function() {
    this.constructor.getAverageRating(this.toUserId);
});

// Call getAverageCost after save
RateSchema.post('save', function() {
    this.constructor.getAverageRating(this.toUserId);
});









module.exports = mongoose.model("Rate", RateSchema);