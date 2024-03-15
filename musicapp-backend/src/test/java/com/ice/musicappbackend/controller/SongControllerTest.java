package com.ice.musicappbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ice.musicappbackend.controller.dto.SongDto;
import com.ice.musicappbackend.controller.error.NotFoundException;
import com.ice.musicappbackend.model.Song;
import com.ice.musicappbackend.service.SongService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SongController.class)
class SongControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SongService songService;

    @Autowired
    private ObjectMapper objectMapper;

    private SongDto songDto;
    private Song song;

    @BeforeEach
    void setUp() {
        songDto = new SongDto();
        songDto.setId(1L);
        songDto.setName("Test Song");
        songDto.setArtist("Test Artist");
        songDto.setAlbum("Test Album");
        songDto.setYear(2020);
        songDto.setLength(4.5);
        songDto.setGenre("Test Genre");

        song = SongDto.toEntity(songDto);
    }

    @Test
    void addSong_Success() throws Exception {
        Song expectedSavedSong = new Song();
        BeanUtils.copyProperties(song, expectedSavedSong);
        expectedSavedSong.setId(1L); // Assuming the ID is set upon saving

        given(songService.addSong(any(Song.class))).willReturn(expectedSavedSong);

        mockMvc.perform(post("/songs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(songDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(songDto.getName()));
    }

    @Test
    void getSongs_Success() throws Exception {
        given(songService.getSongs(null, null, 0, 10, "name", "asc")).willReturn(List.of(song));

        mockMvc.perform(get("/songs")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortProperty", "name")
                        .param("sortDir", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(songDto.getName()));
    }

    @Test
    void getSongById_Success() throws Exception {
        given(songService.getSongById(1L)).willReturn(song);

        mockMvc.perform(get("/songs/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(songDto.getName()));
    }

    @Test
    void getSongById_NotFound() throws Exception {
        given(songService.getSongById(1L)).willThrow(new NotFoundException("Song with ID 1 not found"));

        mockMvc.perform(get("/songs/{id}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Resource Not Found"));
    }

    @Test
    void deleteSong_Success() throws Exception {
        mockMvc.perform(delete("/songs/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteSong_NotFound() throws Exception {
        doThrow(new NotFoundException("Song with ID 1 not found")).when(songService).deleteSong(1L);

        mockMvc.perform(delete("/songs/{id}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Resource Not Found"));
    }
}
