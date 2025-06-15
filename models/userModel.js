const mongoose = require('mongoose');

// schema for the user details
const userSchema = new mongoose.Schema({
    userId: {type: String, required: true, unique: true},
    fullName: {type: String, required: true},
    email: {type: String, required: true},
    phone: {type: String, required: true},
    gender: {type: String, default: ""},
    bio: {type: String, default: ""},
    profileImageUrl: {type: String, default: ""},
    isEmailVerified: {type: Boolean, default: false},
    isPhoneVerified: {type: Boolean, default: false},
    accountCreatedAt: {type: Number, required: false},
    lastLoginAt: {type: Number, required: true}
});


// export the module
module.exports = mongoose.model('User', userSchema); 