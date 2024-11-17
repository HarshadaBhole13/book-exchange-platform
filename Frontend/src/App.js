// src/App.js

import React from 'react';
import './App.css';
import Register from './components/Register';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom'; // Import Router, Route, and Switch
import HomePage from './components/Home';
import Profile from './components/Profile'
import Dashboard from './components/dashboard.js'
import UserDashboard from './components/UserDashboard.js';
import AddBook from './components/AddBook.js';
import UpdateBook from './components/UpdateBook.js';
import UserBooks from './components/UserBooks.js';
import Headers from './components/Header.js';

function App() {
    return (
        
      <Router>
            <Routes> {/* Use Routes instead of Switch */}
                <Route path="/" element={<HomePage />} />
                <Route path="/register" element={<Register />} />
                {/* Add routes for Login and Profile pages here */}
                <Route path="/dashboard" element={<Dashboard />}/>
                <Route path="/profile" element={<Profile />}/>
                <Route path='/UserDashboard' element={<UserDashboard/>}/>
                <Route path='/AddBook' element={<AddBook/>}/>
                <Route path='/UpdateBook' element={<UpdateBook/>}/>
                <Route path='/user-books' element={<UserBooks/>}/>
                <Route path='/Header' element={<Headers/>}/>
            </Routes>
        </Router>
        
    );
}

export default App;
