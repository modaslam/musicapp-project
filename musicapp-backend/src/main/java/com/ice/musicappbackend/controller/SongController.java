package com.ice.musicappbackend.controller;

import com.ice.musicappbackend.controller.dto.SongDto;
import com.ice.musicappbackend.model.Song;
import com.ice.musicappbackend.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/songs")
public class SongController {

    private final SongService songService;

    @Autowired
    public SongController(SongService songService) {
        this.songService = songService;
    }

    @PostMapping
    public ResponseEntity<SongDto> addSong(@RequestBody @Valid SongDto songDto) {
        Song song = SongDto.toEntity(songDto);
        song = songService.addSong(song);
        SongDto savedSongDto = SongDto.fromEntity(song);
        return new ResponseEntity<>(savedSongDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SongDto>> getSongs(@RequestParam(required = false) String artist,
                                                  @RequestParam(required = false) Integer year,
                                                  @RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size,
                                                  @RequestParam(defaultValue = "name") String sortProperty,
                                                  @RequestParam(defaultValue = "asc") String sortDir) {
        List<Song> songs = songService.getSongs(artist, year, page, size, sortProperty, sortDir);
        List<SongDto> songDtos = songs.stream().map(SongDto::fromEntity).toList();
        return ResponseEntity.ok(songDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SongDto> getSongById(@PathVariable Long id) {
        Song song = songService.getSongById(id);
        SongDto songDto = SongDto.fromEntity(song);
        return ResponseEntity.ok(songDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSong(@PathVariable Long id) {
        songService.deleteSong(id);
        return ResponseEntity.noContent().build();
    }
}
