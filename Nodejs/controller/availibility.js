const path = require('path');
const asyncHandler = require("../middleware/async"); 
const Availibility = require("../models/availibility");
const crypto = require ("crypto");
const availibility = require('../models/availibility');
//const sendEmail = require("../utils/sendEmail");

// @desc            Get Availibility
// @routes          GET /api/v1/:userId/availibility/get
// access           Private
exports.getavailibilities = asyncHandler (async (req,res,next) => {
    
    let query;

    // if(req.params.userId){
    //     query = Availibility.find({user : req.params.userId});
    // }else{
        query = Availibility.find().populate('user');
    // }
    
    //const availibility = await Availibility.find({ user: req.params.id});

    const availibility = await query;
    return res.status(200).json({
        success: true,
        count: availibility.length,
        data: availibility
      });
});


// @desc            Get single Availibility
// @routes          GET /api/v1/availibility/:id
// access           Private
exports.getavailibility = asyncHandler (async (req,res,next) => {
    const availibility = await Availibility.findById(req.params.id).populate({
        path: 'User',
        select: 'name '
      });
    

    return res.status(200).json({
        success: true,
        data: availibility
      });
});


// @desc            Add Availibility
// @routes          POST /api/v1/:userId/availibility/add
// access           private
exports.addavailibility = asyncHandler (async (req,res,next) => {
    req.body.user = req.user.id;
    const avabiltydat = req.body.daysdata;
    for(let i = 0 ; i < avabiltydat.length ; i ++){
        let data = avabiltydat[i];
        data.user = req.user.id;
        await Availibility.create(data);
    }
    // const availibility = await Availibility.create(req.body);
    res.status(200).json({
        success: true,
        data: "Data added"
      });
});

// @desc            Update Availibility
// @routes          PUT /api/v1/availibility/update
// access           Private
exports.update = asyncHandler(async (req,res,next) => {
    //let service = await Service.findById(req.params.id);
    const availibility = await Availibility.findByIdAndUpdate(req.params.id, req.body,{
        new: true,
        runValidators: true
    });
    res.status(200).json({success : true, data : availibility});

});

// @desc            Delete Availibility
// @routes          DELETE /api/v1/availibility/delete/:id
// access           Private
exports.deleteAvailibility = asyncHandler(async (req,res,next) => {
    let availibility = await Availibility.findById(req.params.id);
    await availibility.remove();
    res.status(200).json({success : true});

});
