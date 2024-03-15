import React, { useState } from 'react';
import { Button, TextField, Box, Snackbar } from '@mui/material';
import CheckIcon from '@mui/icons-material/Check';
import Alert from '@mui/material/Alert';
import { useAddSong } from 'Hooks/useSongs';
import { useNavigate } from 'react-router-dom';

const AddSongForm = () => {
	const [songData, setSongData] = useState({
		name: '', artist: '', album: '', year: '', length: '', genre: ''
	});
	const addSongMutation = useAddSong();
	const navigate = useNavigate();
	const [open, setOpen] = useState(false);

	const handleChange = (event) => {
		const { name, value } = event.target;
		setSongData({ ...songData, [name]: value });
	};

	const handleSubmit = (event) => {
		event.preventDefault();
		addSongMutation.mutate(songData, {
			onSuccess: () => {
				setOpen(true); // Open snackbar
				setTimeout(() => navigate("/"), 2000);
			},
		});
	};
	
	const handleClose = (event, reason) => {
		if (reason === 'clickaway') {
			return;
		}
		setOpen(false);
	};

	return (
		<>
		<Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>
			<TextField
				margin="normal"
				required
				fullWidth
				label="Song Name"
				name="name"
				value={songData.name}
				onChange={handleChange}
				autoFocus />
			<TextField
				margin="normal"
				required
				fullWidth
				label="Artist"
				name="artist"
				value={songData.artist}
				onChange={handleChange} />
			<TextField
				margin="normal"
				fullWidth
				label="Album"
				name="album"
				value={songData.album}
				onChange={handleChange} />
			<TextField
				margin="normal"
				required
				fullWidth
				label="Year"
				name="year"
				type="number"
				value={songData.year}
				onChange={handleChange} />
			<TextField
				margin="normal"
				required
				fullWidth
				label="Length (minutes)"
				name="length"
				type="number"
				value={songData.length}
				onChange={handleChange} />
			<TextField
				margin="normal"
				fullWidth
				label="Genre"
				name="genre"
				value={songData.genre}
				onChange={handleChange} />
			<Button
				type="submit"
				fullWidth
				variant="contained" sx={{ mt: 3, mb: 2 }}
				disabled={addSongMutation.isLoading}>
				Add Song
			</Button>
		</Box>
		<Snackbar open={open} autoHideDuration={6000} onClose={handleClose}>
				<Alert icon={<CheckIcon fontSize="inherit" />} onClose={handleClose} severity="success" sx={{ width: '100%' }}>
					Song added successfully!
				</Alert>
			</Snackbar>
			{addSongMutation.isError && (
				<Alert severity="error">Error adding song. Please try again.</Alert>
			)}
		</>
	);
};

export default AddSongForm;
