import axios from 'axios';

const API_BASE_URL = '/api/musicapp/songs';

// Fetch songs with optional filters
export const getSongs = async (filters = {}) => {
  try {
    const { data } = await axios.get(API_BASE_URL, { params: filters });
    return data;
  } catch (error) {
    console.error('Failed to fetch songs:', error);
    throw error;
  }
};

// Add a new song
export const addSong = async (songData) => {
  try {
    const { data } = await axios.post(API_BASE_URL, songData);
    return data;
  } catch (error) {
    console.error('Failed to add song:', error);
    throw error;
  }
};

// Fetch details of a single song by its ID
export const getSongDetails = async (id) => {
  try {
    const { data } = await axios.get(`${API_BASE_URL}/${id}`);
    return data;
  } catch (error) {
    console.error('Failed to fetch song details:', error);
    throw error;
  }
};
