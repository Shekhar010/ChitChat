const express = require('express');
const router = express.Router();
const Message = require('../models/messageModel');

// Send a message
router.post('/send', async (req, res) => {
  try {
    const { senderId, receiverId, content, roomId, messageType } = req.body;
    const message = new Message({ senderId, receiverId, content, roomId, messageType });
    await message.save();
    res.status(201).json({ success: true, message });
  } catch (err) {
    res.status(500).json({ success: false, error: err.message });
  }
});

// Get all messages for a room
router.get('/:roomId', async (req, res) => {
  try {
    const messages = await Message.find({ roomId: req.params.roomId }).sort({ timestamp: 1 });
    res.json({ success: true, messages });
  } catch (err) {
    res.status(500).json({ success: false, error: err.message });
  }
});

module.exports = router;
