// src/components/Register.js

import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './Register.css';

const Register = () => {
    const [username, setUsername] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [message, setMessage] = useState('');
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();

        const user = {
            username,
            email,
            password,
        };

        try {
            const response = await axios.post('http://localhost:8080/api/auth/register', user);
            if (response.status === 200) {
                setMessage("Registration successful!");
                setTimeout(() => navigate('/'), 2000); // Redirect after 2 seconds to the root path
 // Redirect after 2 seconds
            } else {
                setMessage("Registration failed!");
            }    
        
        } catch (error) {
            if (error.response && error.response.data) {
                setMessage(error.response.data);
            } else {
                setMessage("Registration failed!");
            }
        }
    };

    return (
        <div className="register-container">
            <div className="header">
                <h1>Book Exchange Platform</h1>
            </div>  
            <div className="register-form">
                <h2>User Registration</h2>
                <form onSubmit={handleSubmit}>
                    <div className="form-group">
                        <label>Username:</label>
                        <input
                            type="text"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            required
                        />
                    </div>
                    <div className="form-group">
                        <label>Email:</label>
                        <input
                            type="email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            required
                        />
                    </div>
                    <div className="form-group">
                        <label>Password:</label>
                        <input
                            type="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                        />
                    </div>
                    <button type="submit" className="register-button">Register</button>
                </form>
                {message && <p className="message">{message}</p>}
            </div>
        </div>
    );
};

export default Register;
