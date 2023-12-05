import { Typography, Button, TextField } from '@mui/material';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from './AuthContext';
import axios from 'axios';

function LoginPage() {
  const [user, setUser] = useState({
    username: '',
    password: '',
  });
  const { login } = useAuth();
  const { username, password } = user;
  const navigate = useNavigate();
  const [error, setError] = useState(null);

  const handleChange = (event) => {
    setError(null);

    setUser({ ...user, [event.target.name]: event.target.value });
  };

  const handleLogin = async () => {
    try {
      await login(user);
      navigate('/');
    } catch (error) {
      if (error.response && error.response.status === 401) {
        setError('Invalid username or password');
      } else {
        setError('An unexpected error occurred. Please try again.');
        console.error('Error in login:', error);
      }
    }
  };
  const handleFormSubmit = (event) => {
    event.preventDefault(); // Prevents the default form submission
    handleLogin();
  };

  return (
    <div>
      <Typography variant="h4">Login</Typography>

      <form onSubmit={handleFormSubmit}>
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
        {error && <Typography color="error">{error}</Typography>}
        <Button type="submit" variant="contained" color="primary">
          Login
        </Button>
      </form>
    </div>
  );
}

export default LoginPage;
