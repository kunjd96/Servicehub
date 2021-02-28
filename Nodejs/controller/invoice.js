const asyncHandler = require("../middleware/async");
const Invoice = require("../models/invoice");
const ErrorResponse = require("../utils/errorResponse");
const Charges = require("../models/ChargesDescription");
const Appointment = require("../models/appointment");

// @desc            Add Invoice
// @routes          POST /api/v1/Invoice/AddInvoice
// access           Private

exports.AddInvoice = asyncHandler(async(req, res, next) => {
    let appointment = await Appointment.findById(req.body.appointment);

    if (!appointment) {
        return next(
            new ErrorResponse(`appointment not found with id of ${req.body.appointment}`, 404)
        );
    }
    const invoice = await Invoice.create(req.body);
    const chargesDesc = req.body.ChargesDescription;
    for (let i = 0; i < chargesDesc.length; i++) {
        let data = chargesDesc[i];
        data.InvoiceId = invoice.id;
        await Charges.create(data);
    }
    var newvalues = { $set: { invoiceGenrated: true } };
    appointment = await Appointment.findOneAndUpdate(req.body.appointment, newvalues, {
        new: true,
        runValidators: true
    });

    res.status(200).json({ success: true, insertdata: invoice });

});


// @desc            Get Invoice
// @routes          GET /api/v1/Invoice/getInvoice
// access           Private

exports.getInvoice = asyncHandler(async(req, res, next) => {
    const invoice = await Invoice.findById(req.params.id).populate("charges").populate("CustomerID").populate("ServiceProviderId").populate("appointment");
    res.status(200).json({ success: true, data: invoice });
});

exports.getAllInvoiceByUser = asyncHandler(async(req, res, next) => {
    const userid = req.user.id;
    let invoiceList = [];
    if (req.user.role == "Customer") {
        invoiceList = await Invoice.find({
            CustomerID: req.user.id
        }).populate("CustomerID").populate("ServiceProviderId").populate("appointment").populate("charges");
    } else {
        invoiceList = await Invoice.find({
            ServiceProviderId: req.user.id
        }).populate("CustomerID").populate("ServiceProviderId").populate("appointment");
    }

    return res.status(200).json({
        success: true,
        data: invoiceList
    });
});


// @desc            Delete Invoice
// @routes          DELETE /api/v1/auth/:id
// access           Private
exports.deleteInvoice = asyncHandler(async(req, res, next) => {
    const invoice = await Invoice.findByIdAndDelete(req.params.id);
    if (!invoice) {
        return next(new ErrorResponse("Invoice not found with id of ", 404));
    }
    res.status(200).json({ success: true, data: {} });
});


// @desc            Update User
// @routes          POST /api/v1/auth/update/:id
// access           Private
exports.updateInvoice = asyncHandler(async(req, res, next) => {
    const invoice = await Invoice.findByIdAndUpdate(req.params.id, req.body, {
        new: true,
        runValidators: true,
    });
    if (!invoice) {
        return next(new ErrorResponse("Invoice not found with id of ", 404));
    }
    res.status(200).json({ success: true, data: invoice });
});