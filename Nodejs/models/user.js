const mongoose = require("mongoose");
const crypto = require("crypto");
const bcrypt = require("bcrypt");
const jwt = require("jsonwebtoken");
const geocoder = require("../utils/geocoder");
const slugify = require("slugify");


// var SchemaTypes = mongoose.Schema.Types;

const Float = require('mongoose-float').loadType(mongoose, 2);

const UserSchema = new mongoose.Schema({
    role: {
        type: String,
        required: [true, "Please enter a role"],
        enum: [
            "Customer",
            "ServiceProvider",
            "Admin"
        ]
    },
    slug: String,
    firstname: {
        type: String,
        required: [true, "Please enter Firstname"],
        maxlength: [50, "Firstname can not be more than 50 characters"]
    },
    lastname: {
        type: String,
        required: [true, "Please enter Lastname"],
        maxlength: [50, "Lastname can not be more than 50 characters"]
    },
    contactNumber: {
        type: String,
        maxlength: [20, "Phone number can not be longer than 20 characters"]
    },
    address: {
        type: String,
        required: [true, "Please add an address"]
    },
    location: {
        // GeoJSON Point
        type: {
            type: String,
            enum: ['Point']
        },
        coordinates: {
            type: [Number],
            index: '2dsphere'
        },
        formattedAddress: String,
        street: String,
        city: String,
        state: String,
        zipcode: String,
        country: String
    },
    email: {
        type: String,
        match: [/^(([^<>()[\]\.,;:\s@\"]+(\.[^<>()[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i, "Please add Email address"],
        unique: true,
        required: [true, "Please enter an email address"]
    },
    password: {
        type: String,
        required: [true, "Please enter a password"],
        minlength: 6,
        select: false
    },
    modifyAt: {
        type: Date
    },
    createdAt: {
        type: Date,
        default: Date.now
    },
    serviceName: String,
    serviceid: {
        type: mongoose.Schema.ObjectId,
        ref: "Services",
    },
    basePrice: {
        type: Float,
        default: 0.0
    },
    ResetPasswordToken: String,
    resetPasswordExpired: Date,
    activeUserToken: String,
    averageRating: {
        type: Number,
        default: 0
    },
    activeUserTokenExpire: Date,
    isActive: {
        type: Boolean,
        default: false
    }

}, {
    toJSON: { virtuals: true },
    toObject: { virtuals: true }
});


//Encrypt password using bcrypt
UserSchema.pre('save', async function(next) {
    if (!this.isModified('password')) {
        next();
    }
    const salt = await bcrypt.genSalt(10);
    this.password = await bcrypt.hash(this.password, salt);
    const loc = await geocoder.geocode(this.address);
    this.location = {
        type: 'Point',
        coordinates: [loc[0].longitude, loc[0].latitude],
        formattedAddress: loc[0].formattedAddress,
        street: loc[0].streetName,
        city: loc[0].city,
        state: loc[0].stateCode,
        zipcode: loc[0].zipcode,
        country: loc[0].countryCode
    };
});

UserSchema.methods.Match = function() {
    if (this.isActive === true) {
        return true;
    } else {
        return false;
    }
};

UserSchema.methods.AdminCheck = function() {
    if (this.role === "Admin") {
        return true;
    } else {
        return false;
    }
};

//Generate and hash password token
UserSchema.methods.getResetPasswordToken = function() {
    //Generate Token
    const resetToken = crypto.randomBytes(20).toString("hex");

    //Hash token and set to resetPasswordToken field
    this.ResetPasswordToken = crypto.createHash("sha256").update(resetToken).digest("hex");

    //set expired
    this.resetPasswordExpired = Date.now() + 10 * 60 * 1000;

    return resetToken;
};

UserSchema.methods.get6digitCode = function() {
    //Generate Token
    const code = Math.floor(Math.random() * 899999 + 100000)

    //Hash token and set to resetPasswordToken field
    this.ResetPasswordToken = code;

    //set expired
    this.resetPasswordExpired = Date.now() + 10 * 60 * 1000;

    return code;
};


//Generate and hash Verify email Address token
UserSchema.methods.getActiveStatus = function() {
    //Generate Token
    const ActiveStatus = crypto.randomBytes(20).toString("hex");

    //Hash token and set to resetPasswordToken field
    this.activeUserToken = crypto.createHash("sha256").update(ActiveStatus).digest("hex");

    //set expired
    this.activeUserTokenExpire = Date.now() + 10 * 60 * 1000;

    return ActiveStatus;
};

//Sign JWT and return
UserSchema.methods.getSignedJwtToken = function() {
    return jwt.sign({ id: this._id }, process.env.JWT_SECRET, {
        expiresIn: process.env.JWT_EXPIRED
    });
};

//Match User entered password to the database
UserSchema.methods.matchPassword = async function(enteredPassword) {
    return await bcrypt.compare(enteredPassword, this.password);
};

//Create username slug
UserSchema.pre("save", function(next) {
    const slugFin = this.firstname + this.lastname;
    this.slug = slugify(slugFin, { lower: true });
    next();
});





//reverse populate with virtual
UserSchema.virtual("availibility", {
    ref: "Avaibility",
    localField: "_id",
    foreignField: "user",
    justOne: false
});

module.exports = mongoose.model("User", UserSchema);