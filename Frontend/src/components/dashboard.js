import React, { useState } from 'react';
import BookSearch from './BookSearch';
import Header from './Header';
import './Dashboard.css';

const Dashboard = () => {
  const [searchFilters, setSearchFilters] = useState({
    title: '',
    genre: '',
    author: '',
    available: '',
  });

  const handleFilterChange = (filters) => {
    setSearchFilters(filters);
  };

  return (
    <>
      <Header />
      <div className="search-section">
      <div className="book-list">
      <h2>Available Books</h2>
        <BookSearch filters={searchFilters} onFilterChange={handleFilterChange} />
      </div>
      </div>
    </>
  );
};

export default Dashboard;
