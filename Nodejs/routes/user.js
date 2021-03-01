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
    verifyUser,
    getOneService,
    changePassword
} = require("../controller/user");

const router = express.Router();

const { protect } = require("../middleware/authUser");

router.route('/register').post(register);

router.route('/login').post(login);
router.route('/changePassword').put(protect, changePassword);


router.put('/:id', updateUser);
router.delete('/:id', deleteUser);
router.get('/getMe', protect, getMe);
router.post('/forgotpassword', forgotPassword);
router.put('/resetpassword', protect, resetPassowrd);

router.post('/verifyUser', verifyUser);
router.get('/validateUser/:validatetoken', validateUser);
router.get('/getOneService/:serviceID', protect, getOneService);

module.exports = router;