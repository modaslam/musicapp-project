package com.ice.musicappbackend.controller;

import com.ice.musicappbackend.controller.dto.SongDto;
import com.ice.musicappbackend.model.Song;
import com.ice.musicappbackend.service.SongService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    /**
     * Adds a new song to the database.
     *
     * @param songDto Song to be added.
     * @return Added song.
     */
    @PostMapping
    public ResponseEntity<SongDto> addSong(@RequestBody @Valid SongDto songDto) {
        Song song = SongDto.toEntity(songDto);
        song = songService.addSong(song);
        SongDto savedSongDto = SongDto.fromEntity(song);
        return new ResponseEntity<>(savedSongDto, HttpStatus.CREATED);
    }

    /**
     * Retrieves a list of songs, optionally filtered by artist and/or year.
     *
     * @param artist       Artist to filter by.
     * @param year         Year to filter by.
     * @param page         Page number of the result set.
     * @param size         Size of the page (Starts from 1).
     * @param sortProperty Property to sort by.
     * @param sortDir      Direction of the sort.
     * @return A list of songs.
     */
    @GetMapping
    @Operation(summary = "Get a list of songs", description = "Retrieves a list of songs, optionally filtered by artist and/or year.",
            responses = {
                    @ApiResponse(description = "Successful operation", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = SongDto.class)))
            })
    public ResponseEntity<List<SongDto>> getSongs(
            @Parameter(description = "Artist to filter by.") @RequestParam(required = false) String artist,
            @Parameter(description = "Year to filter by.") @RequestParam(required = false) Integer year,
            @Parameter(description = "Page number of the results.") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Size of the page") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Property to sort by") @RequestParam(defaultValue = "name") String sortProperty,
            @Parameter(description = "Direction of the sort") @RequestParam(defaultValue = "asc") String sortDir) {
        List<Song> songs = songService.getSongs(artist, year, page, size, sortProperty, sortDir);
        List<SongDto> songDtos = songs.stream().map(SongDto::fromEntity).toList();
        return ResponseEntity.ok(songDtos);
    }

    /**
     * Retrieves a song by its ID.
     *
     * @param id ID of the song to retrieve.
     * @return Requested song.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get a song by its ID",
            description = "Retrieves detailed information about a song by its unique identifier.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = SongDto.class))}),
                    @ApiResponse(responseCode = "404", description = "Song not found")
            })
    public ResponseEntity<SongDto> getSongById(
            @Parameter(description = "ID of the song", required = true) @PathVariable Long id) {
        Song song = songService.getSongById(id);
        SongDto songDto = SongDto.fromEntity(song);
        return ResponseEntity.ok(songDto);
    }

    /**
     * Deletes a song by its ID.
     *
     * @param id ID of the song to delete.
     * @return Response entity indicating the operation result.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a song by its ID",
            description = "Deletes a song from the database using its unique identifier.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Song successfully deleted"),
                    @ApiResponse(responseCode = "404", description = "Song not found")
            })
    public ResponseEntity<Void> deleteSong(
            @Parameter(description = "ID of the song", required = true) @PathVariable Long id) {
        songService.deleteSong(id);
        return ResponseEntity.noContent().build();
    }
}
