const Service = require ("../models/services");
const asyncHandler = require("../middleware/async");
const ErrorResponse = require("../utils/errorResponse");



// @desc            Add service
// @routes          POST /api/v1/services/add
// access           Private
exports.add = asyncHandler(async (req,res,next) => {


    req.body.user = req.user.id;

    if(!req.user.role === "Admin"){
        return next(new ErrorResponse("Not authorised to add Service",400));
    }
    const publishedServices = await Service.findOne({ user: req.user.id });
    const service = await Service.create(req.body);
    res.status(200).json({success : true, data : service});

});

// @desc            Update service
// @routes          PUT /api/v1/services/update/:id
// access           Private
exports.updateService = asyncHandler(async (req,res,next) => {
    //let service = await Service.findById(req.params.id);
    service = await Service.findByIdAndUpdate(req.params.id, req.body,{
        new: true,
        runValidators: true
    });
    res.status(200).json({success : true, data : service});

});

// @desc            Delete service
// @routes          DELETE /api/v1/services/delete/:id
// access           Private
exports.deleteService = asyncHandler(async (req,res,next) => {
    let service = await Service.findById(req.params.id);
    await service.remove();
    res.status(200).json({success : true, data : service});

});

// @desc            Get all services
// @routes          GET /api/v1/services/getServices
// access           Private
exports.getServices = asyncHandler (async (req, res, next) =>{
    console.log("I am here");
    const service = await Service.find();
    res.status(200).json({success : true ,count : service.length, data : service}); 
})