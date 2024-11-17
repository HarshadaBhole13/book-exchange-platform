import './HomePage.css';
import axios from 'axios';
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const HomePage = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [email, setEmail] = useState(''); // For forgot password
    const [error, setError] = useState(null);
    const [isForgotPassword, setIsForgotPassword] = useState(false); // Toggle between login and forgot password
    const navigate = useNavigate();

    // Handle login
    const handleLogin = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post('http://localhost:8080/api/auth/login', {
                username: username,
                password: password
            });
            console.log("Login successful:", response.data);
            localStorage.setItem('userId', response.data.userId);
            navigate('/dashboard');
        } catch (err) {
            console.error("Login failed:", err);
            setError("Invalid credentials");
        }
    };

    // Handle forgot password
    const handleForgotPassword = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post(`http://localhost:8080/api/auth/forgot-password?email=${email}`);
            console.log("Password reset request sent:", response.data);
            alert("Password reset link sent to your email.");
            setIsForgotPassword(false); // Hide forgot password form after success
        } catch (err) {
            console.error("Failed to send password reset email:", err);
            setError("Failed to send reset email.");
        }
    };

    return (
        <div className="home-container">
            {/* Left side: Login and Registration */}
            <div className="left-section">
                <div className="form-container">
                    <h2>{isForgotPassword ? 'Forgot Password' : 'Login'}</h2>

                    {/* Login Form */}
                    {!isForgotPassword ? (
                        <form onSubmit={handleLogin}>
                            <input
                                type="text"
                                placeholder="Username"
                                value={username}
                                onChange={(e) => setUsername(e.target.value)}
                                required
                            />
                            <input
                                type="password"
                                placeholder="Password"
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                                required
                            />
                            <button type="submit">Login</button>
                        </form>
                    ) : (
                        // Forgot Password Form
                        <form onSubmit={handleForgotPassword}>
                            <input
                                type="email"
                                placeholder="Enter your email"
                                value={email}
                                onChange={(e) => setEmail(e.target.value)}
                                required
                            />
                            <button type="submit">Send Reset Link</button>
                        </form>
                    )}

                    {error && <p style={{ color: 'red' }}>{error}</p>}

                    {/* Toggle Link */}
                   {/* Toggle Link */}
{!isForgotPassword ? (
    <p>Don't have an account? <a href="/register">Register here</a></p>
) : (
    <p>Remembered your password? <span onClick={() => setIsForgotPassword(false)} style={{ color: 'blue', cursor: 'pointer', textDecoration: 'underline' }}>Login</span></p>
)}

                    {/* Forgot Password Link */}
                    {!isForgotPassword && (
                        <p><a href="#" onClick={() => setIsForgotPassword(true)}>Forgot Password?</a></p>
                    )}
                </div>
            </div>

            {/* Right side: Website name and image */}
            <div className="right-section">
                <p>Your gateway to exchanging and discovering books!</p>
                <img src="/IMG.jpg" alt="Books" className="home-image" />
            </div>
        </div>
    );
};

export default HomePage;
