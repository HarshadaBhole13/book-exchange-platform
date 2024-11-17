// UpdateBook.js
import React, { useState, useEffect } from 'react';
import axios from 'axios';

const UpdateBook = ({ book, onClose, onUpdate }) => {
    const [title, setTitle] = useState(book.title);
    const [author, setAuthor] = useState(book.author);
    const [genre, setGenre] = useState(book.genre);
    const [condition, setCondition] = useState(book.bookCondition);
    const [available, setAvailable] = useState(book.available);

    const handleUpdate = async (e) => {
        e.preventDefault();
        try {
            // Format date fields to ISO string if they are Date objects
            const updatedBook = { 
                ...book, 
                title, 
                author, 
                genre, 
                bookCondition: condition, 
                available 
            };
    
            // Ensure that LocalDateTime fields are in ISO format (if applicable)
            if (updatedBook.localDateTime) {
                updatedBook.localDateTime = new Date(updatedBook.localDateTime).toISOString();
            }
            if (updatedBook.localDateTimechange) {
                updatedBook.localDateTimechange = new Date(updatedBook.localDateTimechange).toISOString();
            }
    
            await axios.put(`http://localhost:8080/api/auth/books/update/${book.id}`, updatedBook);
            onUpdate(updatedBook);
            onClose();
        } catch (err) {
            console.error("Error updating book:", err);
        }
    };
    

    return (
        <div className="modal-overlay">
            <div className="modal-content">
                <h2>Update Book</h2>
                <form onSubmit={handleUpdate}>
                    <label>
                        Title:
                        <input type="text" value={title} onChange={(e) => setTitle(e.target.value)} />
                    </label>
                    <label>
                        Author:
                        <input type="text" value={author} onChange={(e) => setAuthor(e.target.value)} />
                    </label>
                    <label>
                        Genre:
                        <input type="text" value={genre} onChange={(e) => setGenre(e.target.value)} />
                    </label>
                    <label>
                        Condition:
                        <input type="text" value={condition} onChange={(e) => setCondition(e.target.value)} />
                    </label>
                    <label>
                        Available:
                        <input type="checkbox" checked={available} onChange={(e) => setAvailable(e.target.checked)} />
                    </label>
                    <button type="submit">Update Book</button>
                    <button type="button" onClick={onClose}>Cancel</button>
                </form>
            </div>
        </div>
    );
};

export default UpdateBook;
