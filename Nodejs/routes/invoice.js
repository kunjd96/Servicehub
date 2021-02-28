const express = require("express");

const { AddInvoice, getInvoice, deleteInvoice, updateInvoice, getAllInvoiceByUser } = require("../controller/invoice");

const router = express.Router();

const { protect } = require("../middleware/auth");

router.post("/AddInvoice", protect, AddInvoice);

router.get("/getInvoice/:id", protect, getInvoice);

router.get("/getAllInvoiceByUser", protect, getAllInvoiceByUser);

router.post("/deleteInvoice/:id", protect, deleteInvoice);

router.post("/updateInvoice/:id", protect, updateInvoice);

module.exports = router;