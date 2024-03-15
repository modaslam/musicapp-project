import { useQuery, useMutation, useQueryClient } from 'react-query';
import { addSong, getSongs, getSongDetails } from '../services/SongService';

const useGetSongs = (filters) => {
    return useQuery(['songs', filters], () => getSongs(filters));
};

const useGetSongDetails = (id) => {
    return useQuery(['songDetails', id], () => getSongDetails(id), {
      enabled: !!id, // Ensure query does not run until the id is provided
    });
  };

const useAddSong = () => {
    const queryClient = useQueryClient();

    return useMutation(addSong, {
        onSuccess: () => {
            // Invalidate cache and refetch
            queryClient.invalidateQueries('songs');
        },
        onError: (error) => {
            console.error('Error adding song:', error);
        },
    });
};

export { useGetSongs, useGetSongDetails, useAddSong }