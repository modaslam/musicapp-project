package com.ice.musicappbackend.repository;

import com.ice.musicappbackend.model.Song;
import org.springframework.data.jpa.domain.Specification;

public class SongSpecifications {
    /**
     * Generates a specification for filtering songs by artist name.
     *
     * @param artist Artist name to filter by.
     * @return Specification that filters songs where the artist name contains the provided value.
     */
    public static Specification<Song> hasArtist(String artist) {
        // CriteriaBuilder's like function for partial string matching
        return (song, cq, cb) -> cb.like(song.get("artist"), "%" + artist + "%");
    }

    /**
     * Generates a specification for filtering songs by release year.
     *
     * @param year Year to filter by.
     * @return Specification that filters songs by the exact release year.
     */
    public static Specification<Song> hasYear(Integer year) {
        // CriteriaBuilder's equal function for exact matching of the year
        return (song, cq, cb) -> cb.equal(song.get("year"), year);
    }
}

