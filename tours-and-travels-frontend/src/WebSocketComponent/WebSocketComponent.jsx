import React, { useState, useEffect } from 'react';
import { Client } from '@stomp/stompjs';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import './WebSocketComponent.css'; // Import the CSS file

const WebSocketComponent = () => {
    const [client, setClient] = useState(null);
    const [messages, setMessages] = useState([]);
    const [inputText, setInputText] = useState('');

    // Retrieve connected user information
    const customer = JSON.parse(sessionStorage.getItem("active-customer"));
    const guide = JSON.parse(sessionStorage.getItem("active-guide"));

    const connectedUser = customer || guide;
    const connectedUserId = connectedUser ? connectedUser.id : null;
    const connectedUserFirstName = connectedUser ? connectedUser.firstName : '';
    const connectedUserLastName = connectedUser ? connectedUser.lastName : '';

    useEffect(() => {
        // Fetch historical messages
        fetch('http://localhost:8085/api/messages')
            .then(response => response.json())
            .then(data => {
                setMessages(data.map(msg => ({
                    text: msg.text,
                    senderName: msg.sender ? `${msg.sender.firstName} ${msg.sender.lastName}` : 'Unknown',
                    senderId: msg.sender ? msg.sender.id : null,
                })));
            })
            .catch(error => console.error('Failed to fetch messages:', error));

        // Set up WebSocket connection
        const stompClient = new Client({
            brokerURL: 'ws://localhost:8085/websocket',
            reconnectDelay: 5000,
            onConnect: () => {
                console.log("Connected to WebSocket server");

                stompClient.subscribe('/topic/messages', (message) => {
                    const receivedMsg = JSON.parse(message.body);
                    setMessages(prevMessages => [
                        ...prevMessages,
                        {
                            text: receivedMsg.text,
                            senderName: receivedMsg.sender ? `${receivedMsg.sender.firstName} ${receivedMsg.sender.lastName}` : 'Unknown',
                            senderId: receivedMsg.sender ? receivedMsg.sender.id : null,
                        }
                    ]);
                });
            },
            onDisconnect: () => {
                console.log("Disconnected from WebSocket server");
            }
        });

        stompClient.activate();
        setClient(stompClient);

        return () => {
            stompClient.deactivate();
        };
    }, []);

    const sendMessage = () => {
        if (!connectedUser) {
            toast.error('You must be logged in to send a message.', {
                position: "top-right",
                autoClose: 5000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
            });
            return;
        }
        if (client && inputText.trim() !== '') {
            const msg = { senderId: connectedUserId, text: inputText };
            client.publish({
                destination: '/app/chat',
                body: JSON.stringify(msg)
            });
            setInputText('');
        }
    };

    const handleKeyDown = (e) => {
        if (e.key === 'Enter') {
            e.preventDefault(); // Prevents the default action of submitting forms, if applicable
            sendMessage();
        }
    };

    return (
        <div className="chat-container">
            <div className="message-list">
                {messages.map((msg, index) => (
                    <div
                        key={index}
                        className={`message ${msg.senderId === connectedUserId ? 'message-sent' : 'message-received'}`}
                    >
                        <div
                            className={`message-content ${msg.senderId === connectedUserId ? 'sent' : 'received'}`}
                        >
                            <div className="message-sender">{msg.senderName}</div>
                            <div className="message-text">{msg.text}</div>
                        </div>
                    </div>
                ))}
            </div>
            <div className="input-container">
                <input
                    type="text"
                    value={inputText}
                    onChange={(e) => setInputText(e.target.value)}
                    onKeyDown={handleKeyDown} // Add onKeyDown event handler
                    placeholder="Type your message here..."
                />
                <button onClick={sendMessage} className="send-button">
                    Send
                </button>
            </div>
            <ToastContainer />
        </div>
    );
};

export default WebSocketComponent;
