import React, { useState } from 'react';
import { Typography, Button, TextField } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { useAuth } from './AuthContext'; // Update the path accordingly

function RegisterPage() {
  const [user, setUser] = useState({
    username: '',
    password: '',
    email: '', // Add the email field
  });
  const { login } = useAuth();
  const { username, password, email } = user;
  const navigate = useNavigate();
  const [error, setError] = useState(null);

  const handleChange = (event) => {
    setUser({ ...user, [event.target.name]: event.target.value });
  };

  const handleRegister = (event) => {
    event.preventDefault(); // Prevent the default form submission behavior

    // Make the registration API call
    axios.post('http://localhost:8080/api/auth/register', user, {
      headers: { 'Content-Type': 'application/json' },
    })
      .then((res) => {
        // Assuming the registration was successful, log in the user
        console.log("inside handleregister .then (res)")
        sessionStorage.setItem('username', user.username);
        login(user);
        console.log('User registered and authenticated');
        navigate('/'); // Redirect to the home page or another route
      })
      .catch((error) => {
        if (error.response) {
          console.error('Registration failed with status code', error.response.status);
          console.error('Error details:', error.response.data);

          // Handle other error scenarios
        } else {
          console.error('Error occurred:', error.message);
        }
      });
  };

  return (
    <div>
      <Typography variant="h4">Register</Typography>

      <form onSubmit={handleRegister}>
        <div>
          <TextField
            label="Username"
            variant="outlined"
            fullWidth
            name="username"
            value={username}
            onChange={handleChange}
          />
        </div>

        <div style={{ margin: '16px 0' }}>
          <TextField
            type="password"
            label="Password"
            variant="outlined"
            fullWidth
            name="password"
            value={password}
            onChange={handleChange}
          />
        </div>

        <div style={{ margin: '16px 0' }}>
          <TextField
            label="Email"
            variant="outlined"
            fullWidth
            name="email"
            value={email}
            onChange={handleChange}
          />
        </div>
        {error && <p style={{ color: 'red' }}>{error}</p>}
        <Button variant="contained" color="primary" type="submit">
          Register
        </Button>
      </form>
    </div>
  );
}

export default RegisterPage;
