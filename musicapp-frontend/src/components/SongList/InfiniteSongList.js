import React, { useState, useEffect, useRef } from 'react';
import { useInfiniteSongs } from 'Hooks/useInfiniteSongs';
import {
  Box,
  CircularProgress,
  List,
  ListItemButton,
  ListItemText,
  Backdrop,
  Alert
} from '@mui/material';
import SongDetailsModal from 'Components/SongDetailsModal/SongDetailsModal';

const InfiniteSongList = () => {
  const {
    data,
    fetchNextPage,
    hasNextPage,
    isFetchingNextPage,
    isLoading,
    isError,
    error,
  } = useInfiniteSongs();

  const loadMoreRef = useRef(null);

  const [selectedSongId, setSelectedSongId] = useState(null);
  const [modalOpen, setModalOpen] = useState(false);

  useEffect(() => {
    const observer = new IntersectionObserver(
      entries => {
        if (entries[0].isIntersecting && hasNextPage && !isFetchingNextPage) {
          fetchNextPage();
        }
      },
      { threshold: 1 }
    );
    if (loadMoreRef.current) {
      observer.observe(loadMoreRef.current);
    }
    return () => observer.disconnect();
  }, [hasNextPage, isFetchingNextPage, fetchNextPage]);

  if (isLoading) {
    return (
      <Backdrop open={true} sx={{ color: '#fff', zIndex: (theme) => theme.zIndex.drawer + 1 }}>
        <CircularProgress color="inherit" />
      </Backdrop>
    );
  }

  if (isError) {
    return (
      <Alert severity="error" sx={{ mb: 2 }}>
        Error loading songs: {error?.message || 'Unknown error occurred.'}
      </Alert>
    );
  }

  const handleOpenModal = (id) => {
    setSelectedSongId(id);
    setModalOpen(true);
  };

  const handleCloseModal = () => setModalOpen(false);

  return (
    <Box>
      <List>
        {data.pages.map((group, i) => (
          <React.Fragment key={i}>
            {group.map(song => (
              <ListItemButton key={song.id} onClick={() => handleOpenModal(song.id)}>
                <ListItemText primary={song.name} secondary={`by ${song.artist}`} />
              </ListItemButton>
            ))}
          </React.Fragment>
        ))}
      </List>
      {selectedSongId && <SongDetailsModal id={selectedSongId} open={modalOpen} handleClose={handleCloseModal} />}
      <div ref={loadMoreRef} style={{ height: 20, marginBottom: '20px' }}></div>
    </Box>
  );
};

export default InfiniteSongList;