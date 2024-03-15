package com.ice.musicappbackend.service;

import com.ice.musicappbackend.controller.error.NotFoundException;
import com.ice.musicappbackend.model.Song;
import com.ice.musicappbackend.repository.SongRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SongServiceTest {

    @Mock
    private SongRepository songRepository;

    @InjectMocks
    private SongService songService;

    private Song song;

    @BeforeEach
    void setUp() {
        song = new Song();
        song.setId(1L);
        song.setName("Test Song");
    }

    @Test
    void addSong_Success() {
        when(songRepository.save(any(Song.class))).thenReturn(song);

        Song savedSong = songService.addSong(song);

        assertThat(savedSong.getId()).isEqualTo(song.getId());
        verify(songRepository).save(song);
    }

    @Test
    void getSongById_Found() {
        given(songRepository.findById(1L)).willReturn(Optional.of(song));

        Song foundSong = songService.getSongById(1L);

        assertThat(foundSong).isNotNull();
        assertThat(foundSong.getId()).isEqualTo(1L);
    }

    @Test
    void getSongById_NotFound() {
        given(songRepository.findById(any(Long.class))).willReturn(Optional.empty());

        assertThatThrownBy(() -> songService.getSongById(1L))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Song with ID 1 not found");
    }

    @Test
    void getSongs_Success() {
        Page<Song> songPage = new PageImpl<>(List.of(song));
        given(songRepository.findAll(any(Specification.class), any(PageRequest.class))).willReturn(songPage);

        List<Song> songs = songService.getSongs("Test Artist", 2020, 0, 10, "name", "asc");

        assertThat(songs).hasSize(1);
        assertThat(songs.get(0).getId()).isEqualTo(1L);
    }

    @Test
    void deleteSong_Success() {
        given(songRepository.existsById(1L)).willReturn(true);

        songService.deleteSong(1L);

        verify(songRepository).deleteById(1L);
    }

    @Test
    void deleteSong_NotFound() {
        given(songRepository.existsById(any(Long.class))).willReturn(false);

        assertThatThrownBy(() -> songService.deleteSong(1L))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Song with ID 1 not found");
    }
}