// AddBook.js
import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './AddBook.css'; // Import the new CSS file
import Header from './Header';

const AddBook = () => {
    const [title, setTitle] = useState('');
    const [author, setAuthor] = useState('');
    const [genre, setGenre] = useState('');
    const [bookCondition, setBookCondition] = useState('');
    const [available, setAvailable] = useState(true);
    const userId = localStorage.getItem('userId');
    const navigate = useNavigate();

    const handleAddBook = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post(`http://localhost:8080/api/auth/add?user_id=${userId}`, {
                title,
                author,
                genre,
                bookCondition,
                available,
                userId
            });
            console.log('Book added successfully:', response.data);
            navigate('/profile'); // Redirect to profile page after adding book
        } catch (error) {
            console.error("Error adding book:", error);
        }
    };

    return (

        <>
        <Header/>
        <div className="add-book-container">
            <h2>Add a New Book</h2>
            <form onSubmit={handleAddBook}>
                <label>Title:</label>
                <input type="text" value={title} onChange={(e) => setTitle(e.target.value)} required />

                <label>Author:</label>
                <input type="text" value={author} onChange={(e) => setAuthor(e.target.value)} required />

                <label>Genre:</label>
                <input type="text" value={genre} onChange={(e) => setGenre(e.target.value)} required />

                <label>Condition:</label>
                <input type="text" value={bookCondition} onChange={(e) => setBookCondition(e.target.value)} required />

                <label>Available:</label>
                <input type="checkbox" checked={available} onChange={(e) => setAvailable(e.target.checked)} />

                <button type="submit">Add Book</button>
            </form>
        </div>
        </>
    );
};

export default AddBook;
