package com.smartcar.dororok.spotify;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;

import java.io.IOException;

@Configuration
public class SpotifyConfig {

    private final static String clientId = System.getenv("client_id");

    private final static String clientSecret = System.getenv("client_secret");

    private final SpotifyApi spotifyApi = new SpotifyApi.Builder().setClientId(clientId).setClientSecret(clientSecret).build();


    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public String spotifyAccessToken() {


            ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build();
            try {
                final ClientCredentials clientCredentials = clientCredentialsRequest.execute();
                // Set access token for further "spotifyApi" object usage
                spotifyApi.setAccessToken(clientCredentials.getAccessToken());
                return spotifyApi.getAccessToken();

            } catch (IOException | SpotifyWebApiException e) {
                System.out.println("Error: " + e.getMessage());
                return "error";
            } catch (org.apache.hc.core5.http.ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }

