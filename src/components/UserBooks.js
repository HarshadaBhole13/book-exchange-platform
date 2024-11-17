import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Model from './Model';
import UpdateBook from './UpdateBook';
import { useNavigate } from 'react-router-dom';
import Header from './Header';
import './UserBooks.css'; 

const UserBooks = () => {
    const [books, setBooks] = useState([]);
    const [error, setError] = useState(null);
    const [successMessage, setSuccessMessage] = useState(null);
    const userId = localStorage.getItem('userId');
    const navigate = useNavigate();
    const [selectedBook, setSelectedBook] = useState(null);
    const [showModal, setShowModal] = useState(false);

    
    useEffect(() => {
        const fetchUserBooks = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/api/auth/books?user_id=${userId}`);
                setBooks(response.data);
            } catch (err) {
                setError("Error fetching books");
                console.error(err);
            }
        };

        if (userId) fetchUserBooks();
    }, [userId]);

    const handleDelete = async (bookId) => {
        try {
            await axios.delete(`http://localhost:8080/api/auth/books/delete/${bookId}`);
            setBooks(books.filter(book => book.id !== bookId));
            setSuccessMessage("Book deleted successfully!");
        } catch (err) {
            console.error("Error deleting book:", err);
            setError("Could not delete the book");
        }
    };

    const handleUpdateClick = (book) => setSelectedBook(book);

    const handleUpdate = (updatedBook) => {
        setBooks(books.map((book) => (book.id === updatedBook.id ? updatedBook : book)));
        setSelectedBook(null);
        setSuccessMessage("Book updated successfully!");
        setShowModal(true);
    };

    const closeModal = () => {
        setShowModal(false);
        setSuccessMessage(null);
    };

   // Ensure this is only declared once in the file
   const formatDate = (dateString) => {
    if (!dateString) return "Invalid Date"; // If date is null or invalid

    let date = new Date(dateString);
    if (isNaN(date.getTime())) return "Invalid Date"; // Check if date is invalid

    return date.toLocaleDateString("en-US", {
        year: 'numeric',
        month: 'long',
        day: 'numeric',
        hour: 'numeric',
        minute: 'numeric',
        second: 'numeric',
    });
};





    return (
        <>
            <Header/>   
            <div className="container">
                <h2>User Books</h2>
                {error && <p className="error">{error}</p>}
                {successMessage && <p className="success">{successMessage}</p>}
                {books.length > 0 ? (
                    <table>
                        <thead>
                            <tr>
                                <th>Book ID</th>
                                <th>Title</th>
                                <th>Author</th>
                                <th>Genre</th>
                                <th>Condition</th>
                                <th>Available</th>
                                <th>Image</th> {/* Add Image column */}
                                <th>Added On</th> {/* Added Date column */}
                                <th>Last Updated</th> {/* Updated Date column */}
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            {books.map((book) => (
                                <tr key={book.id}>
                                    <td>{book.id}</td>
                                    <td>{book.title}</td>
                                    <td>{book.author}</td>
                                    <td>{book.genre}</td>
                                    <td>{book.bookCondition}</td>
                                    <td>{book.available ? "Yes" : "No"}</td>
                                    <td>
                                        {/* Display book image */}
                                        {book.bookImage && <img src={book.bookImage} alt={book.title} className="book-image" />}
                                    </td>
                                    <td>{formatDate(book.createdAt)}</td> {/* Display added date */}
                                    <td>{formatDate(book.updatedAt)}</td> {/* Display updated date */}
                                    <td>
                                        <button onClick={() => handleUpdateClick(book)}>Update</button>
                                        <button className="delete" onClick={() => handleDelete(book.id)}>Delete</button>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                ) : (
                    <p>No books found for this user.</p>
                )}
                <button className="add-book-btn" onClick={() => navigate('/AddBook')}>Add Book</button>
                <button className="back-to-profile-btn" onClick={() => navigate('/profile')}>Back to Profile</button>
            </div>

            {showModal && <Model message={successMessage} onClose={closeModal} />}
            {selectedBook && (
                <UpdateBook 
                    book={selectedBook} 
                    onClose={() => setSelectedBook(null)} 
                    onUpdate={handleUpdate} 
                />
            )}
        </>
    );
};

export default UserBooks;
