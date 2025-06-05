const express = require('express');
const mongoose = require('mongoose');
const router = require('./models/messageModel');
require('dotenv').config();


const app = express();
const port = 3000;

// middleware to parse the json bodies
app.use(express.json())

const MONGO_URL = process.env.MONGO_URL
// connect to MongoDB
mongoose.connect(MONGO_URL, {
    useNewUrlParser: true, useUnifiedTopology: true
})
    .then(() => console.log('✅ MongoDB connected'))
    .catch(err => console.error('❌ MongoDB connection error:', err));

// import router file 
const messageRoutes = require('./routes/messageRoute');
// route for message
app.use('/messages', messageRoutes)


app.listen(port, () => {
    console.log(`🚀 Server running on port ${port}`);
});