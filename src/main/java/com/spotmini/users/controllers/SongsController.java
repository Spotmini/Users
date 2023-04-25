package com.spotmini.users.controllers;

import com.spotmini.users.models.CreateSongModel;
import com.spotmini.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;

@RestController
public class SongsController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private KafkaTemplate<String, HashMap<String, String>> kafkaTemplate;

    @PostMapping("/artists")
    public ResponseEntity<String> createArtist(@RequestBody String artist) {
        if (!userService.isUserSpecial()) return null;
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("name", artist);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        String url = System.getenv("songs-service") + "/artists";
        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

    }

    @DeleteMapping("/artists/{artistName}")
    public ResponseEntity<String> deleteArtist(@PathVariable String artistName) {
        if (!userService.isUserSpecial()) return null;
        HashMap<String, String> message = new HashMap<>();
        message.put("artistName", artistName);
        message.put("songName", null);
        kafkaTemplate.send("delete-song", message);
        return ResponseEntity.ok().body("Artist deletion request sent to Kafka.");
    }

    @PostMapping("/artists/{artistName}/songs")
    public ResponseEntity<String> createSong(@PathVariable String artistName, @RequestBody CreateSongModel createSongModel) {
        if (!userService.isUserSpecial()) return null;
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("artist", artistName);
        body.add("name", createSongModel.getSongName());
        body.add("content", createSongModel.getContent());
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        String url = System.getenv("songs-service") + "/songs";
        return restTemplate.postForEntity(url, requestEntity, String.class);

    }

    @DeleteMapping("/artists/{artistName}/songs/{songName}")
    public ResponseEntity<String> deleteSong(@PathVariable String artistName, @PathVariable String songName) {
        if (!userService.isUserSpecial()) return null;
        HashMap<String, String> message = new HashMap<>();
        message.put("artistName", artistName);
        message.put("songName", songName);
        kafkaTemplate.send("delete-song", message);
        return ResponseEntity.ok().body("Song deletion request sent to Kafka.");
    }

}

