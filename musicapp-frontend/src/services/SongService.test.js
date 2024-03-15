import axios from 'axios';
import MockAdapter from 'axios-mock-adapter';
import { getSongs, addSong, getSongDetails } from './SongService';

describe('SongService', () => {
  let mock;
  const baseURL = `/api`;

  beforeEach(() => {
    mock = new MockAdapter(axios);
  });

  afterEach(() => {
    mock.reset();
  });

  it('fetches songs correctly', async () => {
    const mockSongs = [{ id: 1, name: 'Test Song' }];
    mock.onGet(`${baseURL}/songs`).reply(200, mockSongs);

    const songs = await getSongs();
    expect(songs).toEqual(mockSongs);
  });

  it('adds a new song correctly', async () => {
    const newSong = { name: 'New Song', artist: 'New Artist' };
    const responseData = { ...newSong, id: 2 };
    mock.onPost(`${baseURL}/songs`).reply(201, responseData);

    const result = await addSong(newSong);
    expect(result).toEqual(responseData);
  });

  it('fetches song details correctly', async () => {
    const songDetails = { id: 1, name: 'Test Song', artist: 'Test Artist' };
    mock.onGet(`${baseURL}/songs/1`).reply(200, songDetails);

    const result = await getSongDetails(1);
    expect(result).toEqual(songDetails);
  });
});