const express= require("express");

const {AddCharges, updateCharge, deleteCharge, getCharge} = require("../controller/ChargesDescription");

    const router = express.Router();

const {protect} = require("../middleware/auth");

router.post("/AddCharge",protect,AddCharges);

router.post("/updateCharge/:id",protect,updateCharge);

router.post("/deleteCharge/:id",protect,deleteCharge);

router.get("/getCharge/:id",protect, getCharge);

module.exports = router;