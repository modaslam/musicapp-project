import React, { useState, useEffect } from 'react';
import { useGetSongs } from 'Hooks/useSongs';
import {
  Box,
  Button,
  CircularProgress,
  FormControl,
  InputLabel,
  List,
  ListItemButton,
  ListItemText,
  MenuItem,
  Select,
  TextField,
  Backdrop,
  Alert
} from '@mui/material';
import SongDetailsModal from 'Components/SongDetailsModal/SongDetailsModal';
import debounce from 'lodash.debounce';

const SongList = () => {
  const [filters, setFilters] = useState({
    artist: '',
    year: '',
    page: 0,
    size: 10,
    sortProperty: 'name',
    sortDir: 'asc',
  });

  const [selectedSongId, setSelectedSongId] = useState(null);
  const [modalOpen, setModalOpen] = useState(false);

  // Separate state for artist and year to apply debounce
  const [artist, setArtist] = useState('');
  const [year, setYear] = useState('');

  const { data: songs, isLoading, error } = useGetSongs(filters);

  // Apply debounce to artist and year input changes
  useEffect(() => {
    const debouncedRefetch = debounce(() => {
      setFilters((filters) => ({ ...filters, artist, year }));
      handlePageChange(0);
    }, 500);

    debouncedRefetch();

    // Cleanup debounce
    return () => {
      debouncedRefetch.cancel();
    };
  }, [artist, year]);

  const handleArtistChange = (event) => {
    setArtist(event.target.value);
  };

  const handleYearChange = (event) => {
    setYear(event.target.value);
  };

  const handleSelectChange = (event) => {
    const { name, value } = event.target;
    setFilters((f) => ({ ...f, [name]: value }));
  };

  const handlePageChange = (newPage) => {
    setFilters((f) => ({ ...f, page: newPage }));
  };

  const handleOpenModal = (id) => {
    setSelectedSongId(id);
    setModalOpen(true);
  };

  const handleCloseModal = () => setModalOpen(false);

  return (
    <Box>
      <Box sx={{ display: 'flex', gap: 2, mb: 2 }}>
        <TextField fullWidth name="artist" label="Artist" value={artist} onChange={handleArtistChange} />
        <TextField sx={{ minWidth: 100 }} name="year" label="Year" type="number" value={year} onChange={handleYearChange} />
        <FormControl sx={{ minWidth: 135 }}>
          <InputLabel id="sort-select-label">Sort Direction</InputLabel>
          <Select name="sortDir" value={filters.sortDir} onChange={handleSelectChange}>
            <MenuItem value="asc">Ascending</MenuItem>
            <MenuItem value="desc">Descending</MenuItem>
          </Select>
        </FormControl>
        <FormControl sx={{ minWidth: 100 }}>
          <InputLabel>Page Size</InputLabel>
          <Select name="size" value={filters.size} onChange={handleSelectChange}>
            {[5, 10, 15, 20].map(size => (
              <MenuItem key={size} value={size}>{size}</MenuItem>
            ))}
          </Select>
        </FormControl>
      </Box>
      {error && (
        <Alert severity="error" sx={{ mb: 2 }}>
          Error loading songs: {error?.message || "Unknown error occurred."}
        </Alert>
      )}
      {isLoading ? (
        <Backdrop open={true} sx={{ color: '#fff', zIndex: (theme) => theme.zIndex.drawer + 1 }}>
          <CircularProgress color="inherit" />
        </Backdrop>
      ) : <List>
        {songs && songs?.map(song => (
          <ListItemButton key={song.id} onClick={() => handleOpenModal(song.id)}>
            <ListItemText primary={song.name} secondary={`by ${song.artist}`} />
          </ListItemButton>
        ))}
      </List>
      }

      <Box sx={{ display: 'flex', justifyContent: 'space-between', mt: 2 }}>
        <Button onClick={() => handlePageChange(Math.max(0, filters.page - 1))} disabled={filters.page < 1}>Previous</Button>
        <Button onClick={() => handlePageChange(filters.page + 1)} disabled={songs?.length < filters.size}>Next</Button>
      </Box>
      {selectedSongId && <SongDetailsModal id={selectedSongId} open={modalOpen} handleClose={handleCloseModal} />}
    </Box>
  );
};

export default SongList;