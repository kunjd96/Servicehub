const express= require("express");

const {addavailibility, getavailibility , update , deleteAvailibility,getavailibilities} = require("../controller/availibility");

const router = express.Router();

const {protect} = require("../middleware/auth");

router.get("/:userId/availibilities/get",protect,getavailibilities);

router.get("/availibility/:id",protect,getavailibility);

router.post("/:id/availibility/add",protect,addavailibility);

router.put("/availibility/update/:id",protect,update);

router.delete("/availibility/delete/:id",protect,deleteAvailibility);

module.exports = router;