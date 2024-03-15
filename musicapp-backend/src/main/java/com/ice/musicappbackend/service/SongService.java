package com.ice.musicappbackend.service;

import com.ice.musicappbackend.controller.error.NotFoundException;
import com.ice.musicappbackend.model.Song;
import com.ice.musicappbackend.repository.SongRepository;
import com.ice.musicappbackend.repository.SongSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongService {
    private final SongRepository songRepository;

    @Autowired
    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public Song addSong(Song song) {
        return songRepository.save(song);
    }

    public Song getSongById(Long id) {
        return songRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Song with ID " + id + " not found"));
    }

    public List<Song> getSongs(String artist, Integer year, int page, int size, String sortProperty, String sortDir) {
        // Adjust for zero-based page indexing
        page = Math.max(0, page - 1);

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(sortDir), sortProperty);
        Specification<Song> spec = Specification.where(null);
        if (artist != null) spec = spec.and(SongSpecifications.hasArtist(artist));
        if (year != null) spec = spec.and(SongSpecifications.hasYear(year));

        Page<Song> songPage = songRepository.findAll(spec, pageable);

        return songPage.getContent();
    }

    public void deleteSong(Long id) {
        // Check if the song exists before attempting to delete
        if (!songRepository.existsById(id)) {
            throw new NotFoundException("Song with ID " + id + " not found");
        }
        songRepository.deleteById(id);
    }
}
