import React from 'react';
import Profile from './Profile';

const UserDashboard = () => {
    // Example userId, this should be replaced with the actual user ID from your context or state management
    const userId = 1; // Replace with dynamic value after login

    return (
        <div>
            <h1>User Dashboard</h1>
            <Profile userId={userId} />
        </div>
    );
};

export default UserDashboard;
