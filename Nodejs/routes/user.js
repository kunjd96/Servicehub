const express = require("express");

const {
    register,
    login,
    updateUser,
    deleteUser,
    getMe,
    forgotPassword,
    resetPassowrd,
    validateUser,
    verifyUser
} = require("../controller/user");

const router = express.Router();

const { protect } = require("../middleware/authUser");


router.post('/register', register);
router.post('/login', login);
router.put('/:id', updateUser);
router.delete('/:id', deleteUser);
router.get('/getMe', protect, getMe);
router.post('/forgotpassword', forgotPassword);
router.put('/resetpassword/:resettoken', resetPassowrd);
router.post('/verifyUser', verifyUser);
router.get('/validateUser/:validatetoken', validateUser);

module.exports = router;