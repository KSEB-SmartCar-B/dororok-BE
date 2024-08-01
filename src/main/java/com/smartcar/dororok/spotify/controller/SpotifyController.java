package com.smartcar.dororok.spotify.controller;

import com.smartcar.dororok.spotify.SpotifyConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
@RequestMapping("/spotify")
@Tag(name = "Spotify Controller", description = "Spotify Web API")
public class SpotifyController {

    private final RestTemplate restTemplate;

    private final SpotifyConfig spotifyConfig;

    private String spotifyAccessToken;

    public SpotifyController(RestTemplate restTemplate, String spotifyAccessToken, SpotifyConfig spotifyConfig) {
        this.restTemplate = restTemplate;
        this.spotifyAccessToken = spotifyAccessToken;
        this.spotifyConfig = spotifyConfig;
    }


    //@GetMapping("/search")
    public ResponseEntity<String> search(@RequestParam String query, @RequestParam String type) {
        String searchEndpoint = "https://api.spotify.com/v1/search";
        String url = searchEndpoint + "?type=" + type + "&q=" + query;



        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + spotifyAccessToken);
        headers.add("Host", "api.spotify.com");
        headers.add("Content-type", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        System.out.println(response.getBody());

        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }

    //@GetMapping("/new-token")
    @Operation(summary = "액세스 토큰 재발급", description = "액세스 토큰이 만료됐을 때, 재발급")
    public void newToken() {
        spotifyAccessToken = spotifyConfig.spotifyAccessToken();
    }
}

