package com.spotmini.users.controllers;

import com.spotmini.users.models.SongModel;
import com.spotmini.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@RestController
public class PlaylistController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserService userService;

    //create
    //delete
    //add song from playlist
    //delete song from playlist

    @PostMapping("/playlists")
    public ResponseEntity<String> createPlaylist(@RequestBody String playlistName) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("name", playlistName);
        body.add("owner",userService.currentUserID());
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        String url = System.getenv("playlist-service") + "/playlists";
        return restTemplate.postForEntity(url, requestEntity, String.class);
    }

    @DeleteMapping("/playlists/{playlistName}")
    public ResponseEntity<String> deletePlaylist(@PathVariable String playlistName) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("name", playlistName);
        body.add("owner", userService.currentUserID());
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        String url = System.getenv("playlist-service") + "/playlists";
        return restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, String.class);
    }

    @PostMapping("/playlists/{playlistName}/songs")
    public ResponseEntity<String> addSong(@RequestBody SongModel songModel, @PathVariable String playlistName) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("songArtist", songModel.getSongArtist());
        body.add("songName", songModel.getSongName());
        body.add("playlistName", playlistName);
        body.add("ownerId", userService.currentUserID());
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        String url = System.getenv("playlist-service") + "/songs";
        return restTemplate.postForEntity(url, requestEntity, String.class);
    }

    @DeleteMapping("/playlists/{playlistName}/songs/{songName}")
    public ResponseEntity<String> deleteSong(@RequestBody String artistName, @PathVariable String playlistName, @PathVariable String songName) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("songArtist", artistName);
        body.add("songName", songName);
        body.add("playlistName", playlistName);
        body.add("ownerId", userService.currentUserID());
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        String url = System.getenv("playlist-service") + "/songs";
        return restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, String.class);
    }

}
