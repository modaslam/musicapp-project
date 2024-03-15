import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import { AppBar, Toolbar, Button, Typography, Container } from '@mui/material';
import AddSongForm from 'Components/AddSongForm/AddSongForm';
import SongList from 'Components/SongList/SongList';
import InfiniteSongList from 'Components/SongList/InfiniteSongList';

function App() {
  return (
    <Router>
      <AppBar position="static" color="default" elevation={0} sx={{ borderBottom: (theme) => `1px solid ${theme.palette.divider}` }}>
        <Toolbar sx={{ flexWrap: 'wrap' }}>
          <Typography variant="h6" color="inherit" noWrap sx={{ flexGrow: 1 }}>
            Music App
          </Typography>
          <nav>
            <Button variant="text" color="primary" component={Link} to="/" sx={{ my: 1, mx: 1.5 }}>
              Home
            </Button>
            <Button variant="text" color="primary" component={Link} to="/inf-song-list" sx={{ my: 1, mx: 1.5 }}>
              Infinite Song List
            </Button>
            <Button variant="text" color="primary" component={Link} to="/add-song" sx={{ my: 1, mx: 1.5 }}>
              Add Song
            </Button>
          </nav>
        </Toolbar>
      </AppBar>
      <Container maxWidth="md" sx={{ mt: 4 }}>
        <Routes>
          <Route path="/" element={<SongList />} />
          <Route path="/inf-song-list" element={<InfiniteSongList />} />
          <Route path="/add-song" element={<AddSongForm />} />
        </Routes>
      </Container>
    </Router>
  );
}

export default App;