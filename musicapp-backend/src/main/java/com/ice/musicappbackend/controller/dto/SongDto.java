package com.ice.musicappbackend.controller.dto;

import com.ice.musicappbackend.model.Song;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class SongDto {
    private Long id;

    @NotBlank(message = "Song name cannot be blank")
    @Size(min = 1, max = 255, message = "Song name must be between 1 and 255 characters")
    private String name;

    @NotBlank(message = "Artist name cannot be blank")
    @Size(min = 1, max = 255, message = "Artist name must be between 1 and 255 characters")
    private String artist;

    private String album;

    @NotNull(message = "Year cannot be null")
    @Min(value = 0, message = "Year must be a positive number")
    private Integer year;

    @NotNull(message = "Length cannot be null")
    @Min(value = 1, message = "Length must be greater than 0")
    private Double length;

    private String genre;

    public static SongDto fromEntity(Song song) {
        SongDto dto = new SongDto();
        dto.setId(song.getId());
        dto.setName(song.getName());
        dto.setArtist(song.getArtist());

        dto.setAlbum(StringUtils.defaultString(song.getAlbum())); // Converts null to empty string if album is null
        dto.setYear(song.getYear());
        dto.setLength(song.getLength());

        dto.setGenre(StringUtils.defaultString(song.getGenre()));
        return dto;
    }

    public static Song toEntity(SongDto dto) {
        Song song = new Song();
        song.setName(dto.getName());
        song.setArtist(dto.getArtist());

        song.setAlbum(StringUtils.isBlank(dto.getAlbum()) ? null : dto.getAlbum());
        song.setYear(dto.getYear());
        song.setLength(dto.getLength());

        song.setGenre(StringUtils.isBlank(dto.getGenre()) ? null : dto.getGenre());
        return song;
    }
}

