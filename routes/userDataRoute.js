const express = require('express');
const router = express.Router();
const User = require('../models/userModel');


// send the user details and store them to the database
router.post('/registerUser', async (req, res) => {
    try {
        const { userId, fullName, email, phone, gender, bio, profileImageUrl, isEmailVerified, isPhoneVerified, accountCreatedAt, lastLoginAt } = req.body;
        const userData = new User({ userId, fullName, email, phone, gender, bio, profileImageUrl, isEmailVerified, isPhoneVerified, accountCreatedAt, lastLoginAt });  
        // store the data to the database(mongoDB)
        await userData.save();
        res.status(201).json({ success: true, userData });
    } catch(err){
        res.status(500).json({success: false, error: err.message});
    }
    
});

// get the user data from the database
router.get('/getUser', async(req, res)=> {
    try{
        // get the user id


        res.status(201).json({success: true});
    } catch(err){
        res.status(500).json({success: false, error: err.message});
    }
});

// get all the users
router.get('/getAll', async(req, res)=>{
    try {
        const users = await User.find();
        res.status(201).json({success: true, users});   
    } catch (err){
        res.status(500).json({success: false, error: err.message})
    }
})


module.exports = router