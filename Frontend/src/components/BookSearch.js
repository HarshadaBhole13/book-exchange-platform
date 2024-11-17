import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './Dashboard.css';
import BookCard from './BookCard';

const BookSearch = ({ filters, onFilterChange }) => {
  const [books, setBooks] = useState([]);
  const [selectedBook, setSelectedBook] = useState(null);  // Track the selected book
  const [negotiationDetails, setNegotiationDetails] = useState({
    deliveryMethod: '',
    duration: '',
  });
  const [isModalOpen, setIsModalOpen] = useState(false);  // Manage modal visibility

  // Fetch books when filters change
  useEffect(() => {
    fetchBooks(filters);
  }, [filters]);

  // Fetch books from backend
  const fetchBooks = async (searchFilters) => {
    const filtersWithAvailability = {
      ...searchFilters,
      available: true,  // Always include available=true
    };

    const filteredFilters = Object.fromEntries(
      Object.entries(filtersWithAvailability).filter(([_, value]) => value)
    );

    const queryParams = new URLSearchParams(filteredFilters).toString();
    console.log("API Endpoint: ", `http://localhost:8080/api/auth/search?${queryParams}`);

    const url = `http://localhost:8080/api/auth/search?${queryParams}`;

    try {
      const response = await axios.get(url);
      setBooks(response.data);
      console.log("Books fetched:", response.data);
    } catch (error) {
      console.error('Error fetching books:', error);
    }
  };

  // Handle filter input change
  const handleInputChange = (e) => {
    const { name, value } = e.target;
    onFilterChange({
      ...filters,
      [name]: value,
    });
  };

  // Handle selecting a book
  const handleSelectBook = (book) => {
    setSelectedBook(book);
    setNegotiationDetails({
      deliveryMethod: '',
      duration: '',
    });
    setIsModalOpen(true);  // Open the modal when a book is selected
  };

  // Handle negotiation details change
  const handleNegotiationChange = (e) => {
    const { name, value } = e.target;
    setNegotiationDetails({
      ...negotiationDetails,
      [name]: value,
    });
  };

  const handleExchangeRequest = async () => {
    const userId = localStorage.getItem('userId'); // Assuming user_id is stored in localStorage
    console.log('User ID:', userId); // Debug log to check if user_id is retrieved correctly

    if (!userId) {
      alert('User is not logged in. Please log in first.');
      return;
    }

    if (selectedBook && negotiationDetails.deliveryMethod && negotiationDetails.duration) {
      const requestData = {
        senderId: userId,               // Add senderId to the request payload
        bookId: selectedBook.bookId,     // Assuming bookId is in book object
        deliveryMethod: negotiationDetails.deliveryMethod,
        duration: negotiationDetails.duration,
      };

      try {
        // Call the backend API to send the exchange request
        const response = await axios.post(
          'http://localhost:8080/api/auth/exchange', // Replace with your endpoint
          requestData
        );

        // Handle the response (e.g., success message)
        alert('Exchange request sent successfully!');
        console.log('Exchange request response:', response.data);

        // Clear form and close modal
        setNegotiationDetails({
          deliveryMethod: '',
          duration: '',
        });
        setIsModalOpen(false);
      } catch (error) {
        console.error('Error sending exchange request:', error);
        alert('Failed to send exchange request. Please try again.');
      }
    } else {
      alert('Please fill all fields for the exchange request.');
    }
  };

  // Close the modal
  const closeModal = () => {
    setIsModalOpen(false);
  };

  return (
    <div>
      {/* Search Filters */}
      <div style={{ display: 'flex', gap: '10px', marginBottom: '1rem' }}>
        <input
          type="text"
          name="title"
          placeholder="Search by title"
          value={filters.title || ''}
          onChange={handleInputChange}
        />
        <input
          type="text"
          name="genre"
          placeholder="Search by genre"
          value={filters.genre || ''}
          onChange={handleInputChange}
        />
        <input
          type="text"
          name="author"
          placeholder="Search by author"
          value={filters.author || ''}
          onChange={handleInputChange}
        />
        <select
          name="available"
          value={filters.available || ''}
          onChange={handleInputChange}
        >
          <option value="">All</option>
          <option value="true">Available</option>
          <option value="false">Not Available</option>
        </select>
        <button onClick={() => fetchBooks(filters)}>Search</button>
      </div>

      {/* Display Books */}
      <div>
        <h2>Book List</h2>
        {books.length > 0 ? (
          <div className="book-grid">
            {books.map((book) => (
              <BookCard key={book.bookId} book={book} onSelect={() => handleSelectBook(book)} />
            ))}
          </div>
        ) : (
          <p>No books found</p>
        )}
      </div>

      {/* Modal for Exchange Request */}
      {isModalOpen && selectedBook && (
        <div className="modal-overlay" onClick={closeModal}>
          <div className="modal-content" onClick={(e) => e.stopPropagation()}>
            <button className="close-modal" onClick={closeModal}>X</button>
            <h3>Send Exchange Request for {selectedBook.title}</h3>
            <label>
              Delivery Method:
              <input
                type="text"
                name="deliveryMethod"
                value={negotiationDetails.deliveryMethod}
                onChange={handleNegotiationChange}
                placeholder="Enter delivery method"
              />
            </label>
            <br />
            <label>
              Duration:
              <input
                type="text"
                name="duration"
                value={negotiationDetails.duration}
                onChange={handleNegotiationChange}
                placeholder="Enter duration"
              />
            </label>
            <br />
            <button onClick={handleExchangeRequest}>Send Request</button>
          </div>
        </div>
      )}
    </div>
  );
};

export default BookSearch;
