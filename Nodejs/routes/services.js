const express = require("express");

const { add, updateService, deleteService, getServices } = require("../controller/services");

const router = express.Router();

const { protect } = require("../middleware/auth");

const advancedResults = require('../middleware/advancedResults');
const Services = require('../models/services');

router.post("/add", protect, add);

router.get("/getServices", advancedResults(Services, ''), getServices)

router.put("/update/:id", protect, updateService);

router.delete("/delete/:id", protect, deleteService);

module.exports = router;