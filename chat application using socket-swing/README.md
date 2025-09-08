# 📨 Chat Application (Java)

A simple **Chat application** built with **Java Sockets** and **Swing GUI**.  
Supports multiple clients, profile pictures, and real-time messaging with a clean bubble-style UI.

---

## 🚀 Features
- 📡 **Client-Server Architecture** (Socket-based communication).
- 🧑‍💻 **Multiple Clients** can connect to the server simultaneously.
- 🖼️ **Profile Picture & Name** selection when joining.
- 💬 **Messenger-Style UI**:
    - Your messages appear on the **right (blue bubble)**.
    - Other users’ messages appear on the **left (gray bubble)** with their avatar.
- 🔄 **Real-time chat updates** using multi-threading.
- ⚡ Lightweight and works on any machine with Java installed.

---

## 📂 Project Structure
    
    ├── Server.java # Server program (handles multiple clients)
    ├── Client.java # Client program
    ├── Client2.java # Client2 program (same as Client.java)
    └── README.md # Project documentation


## 🛠️ How to Run

### 1. Compile the source code
```bash
javac Server.java Client.java
java Server
java Client
java Client2
```
### 2. Start the server
```bash
   java Server
```
Server will run on port 9000

3. Start clients

Open multiple terminals or run multiple instances:
```bash
java Client
```


- Enter your name.
- Select a profile picture.
- Start chatting 🎉.

### 📸 Screenshot (Example UI)
![Screenshot 2025-09-08 153553.png](Screenshots/Screenshot%202025-09-08%20153553.png)

### 📌 Future Improvements
- 📁 File/Image transfer support inside bubbles.
- 🔒 End-to-End Encryption (AES).
- 🎥 Voice/Video call support (long-term).
- 🌐 Custom server IP/Port configuration.

### 🤝 Contributing

Feel free to fork this repo and submit pull requests with improvements (UI, features, bug fixes).