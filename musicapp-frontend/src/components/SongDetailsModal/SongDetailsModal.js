import React from 'react';
import { useGetSongDetails } from 'Hooks/useSongs';
import { Typography, CircularProgress, Modal, Card, CardContent, IconButton } from '@mui/material';
import CloseIcon from '@mui/icons-material/Close';

const modalStyle = {
  position: 'absolute',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  width: 400,
  bgcolor: 'background.paper',
  border: '2px solid #000',
  boxShadow: 24,
  p: 4,
};

const SongDetailsModal = ({ id, open, handleClose }) => {
  const { data: song, isLoading, error } = useGetSongDetails(id);

  if (isLoading) return <CircularProgress />;
  if (error) return <Typography color="error">Failed to load song details: {error.message}</Typography>;

  return (
    <Modal
      open={open}
      onClose={handleClose}
      aria-labelledby="song-details-title"
      aria-describedby="song-details-description"
    >
      <Card sx={modalStyle}>
        <CardContent>
          <IconButton aria-label="close" onClick={handleClose} sx={{ position: 'absolute', right: 8, top: 8 }}>
            <CloseIcon />
          </IconButton>
          <Typography id="song-details-title" variant="h6" component="h2">{song?.name}</Typography>
          <Typography id="song-details-description" sx={{ mt: 2 }}>
            Artist: {song?.artist}<br />
            Album: {song?.album}<br />
            Year: {song?.year}<br />
            Length: {song?.length} minutes<br />
            Genre: {song?.genre}
          </Typography>
        </CardContent>
      </Card>
    </Modal>
  );
};

export default SongDetailsModal;