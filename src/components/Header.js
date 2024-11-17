import React from 'react';
import { useNavigate } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faUser, faSignOutAlt,faHome } from '@fortawesome/free-solid-svg-icons';


const Header = () => {
    const navigate = useNavigate();

    const handleLogout = () => {
        // Clear any stored authentication data
        localStorage.removeItem('token'); // Example if token is stored in localStorage
        navigate('/'); // Redirect to home or login page
    };

    return (
        <header className="dashboard-header">
            <h1>Book Exchange Platform</h1>
            <div className="header-icons">
            <FontAwesomeIcon
                    icon={faHome}
                    onClick={() => navigate('/dashboard')}
                    style={{ cursor: 'pointer', marginRight: '20px' }}
                />
                <FontAwesomeIcon
                    icon={faUser}
                    onClick={() => navigate('/profile')}
                    style={{ cursor: 'pointer', marginRight: '20px' }}
                />
                <FontAwesomeIcon
                    icon={faSignOutAlt}
                    onClick={handleLogout}
                    style={{ cursor: 'pointer' }}
                />
            </div>
        </header>
    );
};

export default Header;
