import { createContext, useContext, useState, useEffect } from 'react';
import axios from 'axios';  // Import axios to use it in the login function

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [isAuthenticated, setAuthenticated] = useState(false);

  useEffect(() => {
    // Check if JWT token and username exist in session storage
    const jwtToken = sessionStorage.getItem('jwt');
    const username = sessionStorage.getItem('username');

    if (jwtToken && username) {
      setAuthenticated(true);
    }
  }, []);

  const login = async (user) => {
    try {
      const res = await axios.post('http://localhost:8080/api/auth/login', user, {
        headers: { 'Content-Type': 'application/json' },
      });

      const jwtToken = res.headers.authorization;
      if (jwtToken !== null) {
        sessionStorage.setItem('jwt', jwtToken);
        sessionStorage.setItem('username', user.username);
        setAuthenticated(true);
      }
    } catch (error) {
      console.log("error in login", error);
      throw error;
    }
  };


  const logout = () => {
    // Perform your logout logic
    setAuthenticated(false);
    sessionStorage.removeItem('jwt');
    sessionStorage.removeItem('username');
    window.location.href = '/';
  };

  useEffect(() => {
    // Check if JWT token and username exist in session storage
    const jwtToken = sessionStorage.getItem('jwt');
    const username = sessionStorage.getItem('username');

    if (jwtToken && username) {
      setAuthenticated(true);
    }
  }, []);

  return (
    <AuthContext.Provider value={{ isAuthenticated, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};