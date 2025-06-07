const express = require('express');
const mongoose = require('mongoose');
const http = require('http');
const cors = require('cors');
const { Server } = require('socket.io');
require('dotenv').config();

const app = express();
const port = 3000;

// Middleware
app.use(express.json());
app.use(cors());

// MongoDB Connection
const MONGO_URL = process.env.MONGO_URL;
mongoose.connect(MONGO_URL, {
    useNewUrlParser: true,
    useUnifiedTopology: true
})
.then(() => console.log('✅ MongoDB connected'))
.catch(err => console.error('❌ MongoDB connection error:', err));

// HTTP + WebSocket Server
const server = http.createServer(app);
const io = new Server(server, {
    cors: {
        origin: "*",
        methods: ["GET", "POST"]
    }
});

// Socket.io Logic
io.on("connection", (socket) => {
    console.log("🔌 New user connected:", socket.id);
    io.emit("userJoined", "a new User came online");

    socket.on("sendMessage", async (data) => {
        console.log("📨 Message received:", data);

        const Message = require('./models/messageModel');
        try {
            const msg = new Message(data);
            await msg.save();
            io.emit("newMessage", msg);
        } catch (err) {
            console.error("❌ Error saving message:", err.message);
        }
    });

    socket.on("disconnect", () => {
        console.log("❌ User disconnected:", socket.id);
    });
});

// Message Routes
const messageRoutes = require('./routes/messageRoute');
app.use('/messages', messageRoutes);

app.get('/', (req, res)=>{
    res.send("server running");
})

// Start server
server.listen(port, () => {
    console.log(`🚀 Server + WebSocket running on port ${port}`);
});