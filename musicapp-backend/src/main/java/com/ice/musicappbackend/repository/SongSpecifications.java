package com.ice.musicappbackend.repository;

import com.ice.musicappbackend.model.Song;
import org.springframework.data.jpa.domain.Specification;

public class SongSpecifications {
    public static Specification<Song> hasArtist(String artist) {
        return (song, cq, cb) -> cb.like(song.get("artist"), "%" + artist + "%");
    }

    public static Specification<Song> hasYear(Integer year) {
        return (song, cq, cb) -> cb.equal(song.get("year"), year);
    }
}

