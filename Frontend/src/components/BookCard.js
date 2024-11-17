import React from 'react';
import './BookCard.css'; // Import the styles for BookCard

const BookCard = ({ book, onSelect }) => {
  return (
    <div className="book-card" onClick={() => onSelect(book)}> {/* Ensure onSelect is triggered here */}
      <div className="book-info">
        <h3>{book.title}</h3>
        <p>Author: {book.author}</p>
        <p>Genre: {book.genre}</p>
        <p>Condition: {book.bookCondition}</p>
        <p>{book.available ? 'Available' : 'Not Available'}</p>
        <p>Created At: {new Date(book.createdAt).toLocaleString()}</p>
        <p>Updated At: {new Date(book.updatedAt).toLocaleString()}</p>
      </div>
    </div>
  );
};

export default BookCard;
