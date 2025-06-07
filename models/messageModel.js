const mongoose = require('mongoose');

// message schema 
const messageSchema = new mongoose.Schema({
    senderId: { type: String, ref: 'User', required: true },
    receiverId: { type: String, ref: 'User' },
    content: { type: String, required: true },
    timestamp: { type: Date, default: Date.now },
    messageType: { type: String, default: 'text' },
    roomId: { type: String, required: true },
    isRead: { type: Boolean, default: false }
}, { timestamps: true });

// ✅ Export the model only
module.exports = mongoose.model('Message', messageSchema);
