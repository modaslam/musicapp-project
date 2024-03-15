package com.ice.musicappbackend.service;

import com.ice.musicappbackend.controller.error.NotFoundException;
import com.ice.musicappbackend.model.Song;
import com.ice.musicappbackend.repository.SongRepository;
import com.ice.musicappbackend.repository.SongSpecifications;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for managing songs.
 */
@Service
public class SongService {
    private static final Logger logger = LoggerFactory.getLogger(SongService.class);
    private final SongRepository songRepository;

    @Autowired
    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    /**
     * Adds a new song to the repository.
     *
     * @param song Song to add.
     * @return Saved song with its generated ID.
     */
    public Song addSong(Song song) {
        return songRepository.save(song);
    }

    /**
     * Retrieves a song by its ID.
     *
     * @param id ID of the song to retrieve.
     * @return Requested song.
     * @throws NotFoundException If the song with the specified ID is not found.
     */
    public Song getSongById(Long id) {
        return songRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Song with ID " + id + " not found"));
    }

    /**
     * Retrieves a list of songs filtered by artist and/or year, sorted and paginated.
     *
     * @param artist Artist to filter by.
     * @param year Release year to filter by.
     * @param page Page number.
     * @param size Size of the page.
     * @param sortProperty Property to sort by.
     * @param sortDir Direction of the sort.
     * @return List of songs matching the filters, sorted and paginated.
     */
    public List<Song> getSongs(String artist, Integer year, int page, int size, String sortProperty, String sortDir) {
        logger.info("Received page: {}, size: {}", page, size);
        page = Math.max(0, page); // Ensure the page number is not negative.

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(sortDir), sortProperty);
        Specification<Song> spec = Specification.where(null);

        // Apply filtering if parameters are not null
        if (artist != null) {
            spec = spec.and(SongSpecifications.hasArtist(artist));
        }
        if (year != null) {
            spec = spec.and(SongSpecifications.hasYear(year));
        }

        Page<Song> songPage = songRepository.findAll(spec, pageable);

        return songPage.getContent();
    }

    /**
     * Deletes a song by its ID.
     *
     * @param id ID of the song to delete.
     * @throws NotFoundException If the song with the specified ID does not exist.
     */
    public void deleteSong(Long id) {
        // Check if the song exists before attempting to delete
        if (!songRepository.existsById(id)) {
            throw new NotFoundException("Song with ID " + id + " not found");
        }
        songRepository.deleteById(id);
    }
}
