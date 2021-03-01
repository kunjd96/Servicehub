const express = require("express");

const { addandupdaterating, getRatingodAppointment, getAllReviewOfUsers } = require("../controller/rateandcomment");

const router = express.Router();

const { protect, authorize } = require("../middleware/authUser");

router.route('/addandupdaterating').post(protect, addandupdaterating);

router.route('/getRatingodAppointment/:id').get(protect, getRatingodAppointment);

router.route('/getAllReviewOfUsers/:id').get(protect, getAllReviewOfUsers);

module.exports = router;