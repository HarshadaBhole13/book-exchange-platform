import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './Profile.css';
import Header from './Header'; 
import { useNavigate } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEdit, faSave, faBook, faEnvelope } from '@fortawesome/free-solid-svg-icons';

const Profile = () => {
    const [userDetails, setUserDetails] = useState({
        username: '',
        email: '',
    });
    const [userPreferences, setUserPreferences] = useState({
        readingPreferences: '',
        favoriteGenres: '',
        bookwish: '',
    });
    const [receivedMessages, setReceivedMessages] = useState([]); // State to hold messages
    const [editing, setEditing] = useState(false);
    const [error, setError] = useState(null);
    const userId = localStorage.getItem('userId');
    const navigate = useNavigate();

    useEffect(() => {
        const fetchUserData = async () => {
            try {
                const userResponse = await axios.get(`http://localhost:8080/api/auth/user?user_id=${userId}`);
                setUserDetails(userResponse.data);
            } catch (err) {
                setError("Error fetching user data");
            }
        };

        const fetchUserPreferences = async () => {
            try {
                const preferencesResponse = await axios.get(`http://localhost:8080/api/auth/preferences?userId=${userId}`);
                setUserPreferences(preferencesResponse.data);
            } catch (err) {
                setError("Error fetching user preferences");
            }
        };

        const fetchMessages = async () => {
            try {
                const messagesResponse = await axios.get(`http://localhost:8080/api/auth/messages?userId=${userId}`);
                setReceivedMessages(messagesResponse.data);
            } catch (err) {
                setError("Error fetching received messages");
            }
        };

        if (userId) {
            fetchUserData();
            fetchUserPreferences();
            fetchMessages();  // Fetch messages when the component loads
        }
    }, [userId]);

    const toggleEditing = () => setEditing(!editing);

    const handleProfileSave = async () => {
        try {
            await axios.put(`http://localhost:8080/api/auth/updateProfile`, {
                userId,
                ...userDetails,
                ...userPreferences,
            });
            setEditing(false);
        } catch (err) {
            setError("Could not update profile");
        }
    };

    return (
        <>
            <Header />
            <div className="profile-container">
                <div className="profile-card">
                    <h2>User Profile</h2>
                    {error && <p className="error">{error}</p>}

                    <div className="profile-info">
                        <label>Username:</label>
                        {editing ? (
                            <input
                                type="text"
                                value={userDetails.username}
                                onChange={(e) => setUserDetails({ ...userDetails, username: e.target.value })}
                            />
                        ) : (
                            <p>{userDetails.username || "N/A"}</p>
                        )}

                        <label>Email:</label>
                        {editing ? (
                            <input
                                type="email"
                                value={userDetails.email}
                                onChange={(e) => setUserDetails({ ...userDetails, email: e.target.value })}
                            />
                        ) : (
                            <p>{userDetails.email || "N/A"}</p>
                        )}

                        <label>Reading Preferences:</label>
                        {editing ? (
                            <textarea
                                value={userPreferences.readingPreferences}
                                onChange={(e) => setUserPreferences({ ...userPreferences, readingPreferences: e.target.value })}
                            />
                        ) : (
                            <p>{userPreferences.readingPreferences || "N/A"}</p>
                        )}

                        <label>Favorite Genres:</label>
                        {editing ? (
                            <input
                                type="text"
                                value={userPreferences.favoriteGenres}
                                onChange={(e) => setUserPreferences({ ...userPreferences, favoriteGenres: e.target.value })}
                            />
                        ) : (
                            <p>{userPreferences.favoriteGenres || "N/A"}</p>
                        )}

                        <label>Books Wish/Own:</label>
                        {editing ? (
                            <input
                                type="text"
                                value={userPreferences.bookwish}
                                onChange={(e) => setUserPreferences({ ...userPreferences, bookwish: e.target.value })}
                            />
                        ) : (
                            <p>{userPreferences.bookwish || "N/A"}</p>
                        )}
                    </div>

                    <button className="edit-save-btn" onClick={editing ? handleProfileSave : toggleEditing}>
                        <FontAwesomeIcon icon={editing ? faSave : faEdit} />
                        {editing ? ' Save' : ' Edit Profile'}
                    </button>

                    <button className="book-info-btn" onClick={() => navigate('/user-books')}>
                        <FontAwesomeIcon icon={faBook} />
                        Show Book Information
                    </button>

                    {/* Messages Section */}
                    <div className="received-messages">
                        <h3><FontAwesomeIcon icon={faEnvelope} /> Received Exchange Requests</h3>
                        {receivedMessages.length > 0 ? (
                            <ul>
                                {receivedMessages.map((message) => (
                                    <li key={message.id}>
                                        <p><strong>From:</strong> {message.senderUsername}</p>
                                        <p><strong>Book:</strong> {message.bookTitle}</p>
                                        <p><strong>Message:</strong> {message.content}</p>
                                        {/* Add new fields */}
                                        <p><strong>Delivery Method:</strong> {message.deliveryMethod}</p>
                                        <p><strong>Duration:</strong> {message.duration}</p>
                                    </li>
                                ))}
                            </ul>
                        ) : (
                            <p>No exchange requests received.</p>
                        )}
                    </div>
                </div>
            </div>
        </>
    );
};

export default Profile;
