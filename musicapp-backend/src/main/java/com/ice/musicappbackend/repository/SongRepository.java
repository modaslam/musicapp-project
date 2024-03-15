package com.ice.musicappbackend.repository;

import com.ice.musicappbackend.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SongRepository extends JpaRepository<Song, Long>, JpaSpecificationExecutor<Song> {
    // JpaSpecificationExecutor enables complex queries with filtering and sorting
}
