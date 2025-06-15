const express = require('express');
const mongoose = require('mongoose');
const http = require('http');
const cors = require('cors');
const { Server } = require('socket.io');
require('dotenv').config();
const Message = require('./models/messageModel');

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
// map for storing the users that are online and map there actual userid with the socketid they got
const userSocketMap = new Map();

// Socket.io Logic
io.on("connection", (socket) => {
    console.log("🔌 New user connected:", socket.id);
    io.emit("userJoined", "a new User came online");

    // to send message
    socket.on("sendMessage", async (data) => {
        console.log("📨 Message received:", data);
        // get the receiver id 
        const receiverId = userSocketMap.get(data.receiverId)
        // check if reciever is online or not 
        
        try {
            if (!receiverId) {
                console.log(`Receiver with ID ${data.receiverId} is offline or not mapped.`);
            }
            if (receiverId) {
                const msg = new Message(data);
                await msg.save();
                io.to(receiverId).emit("newMessage", msg)
            } else {
                const msg = new Message(data);
                await msg.save();
            }
        } catch (err) {
            console.error("❌ Error saving message:", err.message);
        }
    });

    // world chat
    socket.on("publicChat", async(data) => {
        // dont save the message to database 
        const msg = new Message(data);
        // log this in server
        console.log(`data received ${msg}`);
        await msg.save();
        io.emit("worldChat", msg);
    })



    // to map the user
    socket.on("registerUser", (userid) => {
        currentUserId = userid;
        try {
            socket.userid = userid;
            userSocketMap.set(userid, socket.id);
            console.log(`${userid} is mapped to socket ${socket.id}`);
        } catch (err) {
            console.error("❌ error mapping the user: ", err.message);
        }
    })

    socket.on("disconnect", () => {
        // remove the user entry from current map
        if (socket.userId) {
            userSocketMap.delete(socket.userId);
            console.log(`🗑️ Removed user ${socket.userId} from userSocketMap`);
        }
        console.log("❌ User disconnected:", socket.id);
    });

});

// Message Routes
const messageRoutes = require('./routes/messageRoute');
const { log } = require('console');
app.use('/messages', messageRoutes);

// home route
app.get('/', (req, res) => {
    res.send("server running");
})

// route to send registered user data 
const userRoutes = require('./routes/userDataRoute');
app.use('/user', userRoutes);

// Start server
server.listen(port, () => {
    console.log(`🚀 Server + WebSocket running on port ${port}`);
});