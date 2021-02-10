const express = require("express");

const { addAppointment, getAllAppoinment, getAllAppoinmentUser } = require("../controller/appointment");

const router = express.Router();

const { protect, authorize } = require("../middleware/authUser");

router.route('/addAppointment').post(protect, addAppointment);
router.route('/getAllAppointment').get(protect, authorize('admin'), getAllAppoinment);
router.route('/getAllAppointmentUser').get(protect, getAllAppoinmentUser);

module.exports = router;