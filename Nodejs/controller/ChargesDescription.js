const asyncHandler = require("../middleware/async"); 
const Charge = require("../models/ChargesDescription");
const ErrorResponse = require("../utils/errorResponse");

// @desc            Add Charges
// @routes          POST /api/Charges/AddCharge
// access           Private

exports.AddCharges = asyncHandler(async(req, res, next) => {
    const charge = await Charge.create(req.body);
    res.status(200).json({success : true, data : charge});

});

// @desc            Get Charge
// @routes          GET /api/Charge/getCharge
// access           Private

exports.getCharge = asyncHandler(async (req,res,next) => {
    const charge = await Charge.findById(req.params.id);
    res.status(200).json({success : true, data : charge});
});

// @desc            Delete Charge
// @routes          DELETE /api/Charge/deleteCharge/:id
// access           Private
exports.deleteCharge = asyncHandler(async(req,res,next) => {
    const charge = await Charge.findByIdAndDelete(req.params.id);  
    if (!charge){
        return next(new ErrorResponse ("Charge not found with id of ",404));
    }
    res.status(200).json({success : true, data : {}});  
});

// @desc            Update Charge
// @routes          POST /api/Charge/updateCahrge/:id
// access           Private
exports.updateCharge = asyncHandler(async (req,res,next) => {
    const charge = await Charge.findByIdAndUpdate(req.params.id, req.body,{
        new : true,
        runValidators : true,
    });
    if (!charge){
        return next(new ErrorResponse ("Invoice not found with id of ",404));
    }
    res.status(200).json({success : true, data : charge});
});

