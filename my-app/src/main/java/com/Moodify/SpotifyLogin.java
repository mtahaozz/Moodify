package com.Moodify;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

public class SpotifyLogin {

    // Spotify'dan alınan bilgiler
    private static final String CLIENT_ID = "5dc669c3c0da451f9771a053c480ccd7";
    private static final String CLIENT_SECRET = "6ed37c567e6749879347cd7de0861286";
    private static final String REDIRECT_URI = "http://localhost:8181/callback";
    private static final String AUTH_URL = "https://accounts.spotify.com/authorize";
    private static final String TOKEN_URL = "https://accounts.spotify.com/api/token";
    private static final String USER_PROFILE_URL = "https://api.spotify.com/v1/me";
    private static final String USER_TRACKS_URL = "https://api.spotify.com/v1/me/tracks";
    private static final String RECCOMMENDATIONS_URL = "https://api.spotify.com/v1/recommendations";



    public static void main(String[] args) throws Exception {
        System.out.println("Please authorize the app by visiting this URL: ");
        String authLink = AUTH_URL + "?client_id=" + CLIENT_ID +
                "&response_type=code&redirect_uri=" + REDIRECT_URI + "&scope=user-read-private%20user-library-read%20user-follow-read%20playlist-read-private";
        System.out.println(authLink);

        System.out.println("Enter the authorization code: ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String authCode = reader.readLine();

        String accessToken = getAccessToken(authCode);
        
        getUserProfile(accessToken);
        getLikedSongs(accessToken);  // Yeni metot çağrısı
        getFollowedArtists(accessToken);
        createPlaylistByTempo(accessToken,60,10);
    }

    private static String getAccessToken(String authCode) throws Exception {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(TOKEN_URL);
        
        post.setHeader("Content-Type", "application/x-www-form-urlencoded");
        post.setHeader("Authorization", "Basic " + java.util.Base64.getEncoder().encodeToString((CLIENT_ID + ":" + CLIENT_SECRET).getBytes()));

        String requestBody = "grant_type=authorization_code&code=" + authCode + "&redirect_uri=" + REDIRECT_URI;
        post.setEntity(new org.apache.http.entity.StringEntity(requestBody));
        
        HttpResponse response = client.execute(post);
        System.out.println(response);
        String responseBody = EntityUtils.toString(response.getEntity());
        JSONObject json = new JSONObject(responseBody);

        return json.getString("access_token");
    }

    private static void getUserProfile(String accessToken) throws Exception {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(USER_PROFILE_URL);
        get.setHeader("Authorization", "Bearer " + accessToken);
        
        HttpResponse response = client.execute(get);
        String responseBody = EntityUtils.toString(response.getEntity());
        JSONObject json = new JSONObject(responseBody);

        System.out.println("User ID: " + json.getString("id"));
        System.out.println("Display Name: " + json.getString("display_name"));
    }

    private static void getLikedSongs(String accessToken) throws Exception {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(USER_TRACKS_URL);
        get.setHeader("Authorization", "Bearer " + accessToken);

        HttpResponse response = client.execute(get);
        String responseBody = EntityUtils.toString(response.getEntity());
        JSONObject json = new JSONObject(responseBody);
        JSONArray items = json.getJSONArray("items");

        System.out.println("Liked Songs:");
        for (int i = 0; i < items.length(); i++) {
            JSONObject trackObject = items.getJSONObject(i).getJSONObject("track");
            String songName = trackObject.getString("name");
            String artistName = trackObject.getJSONArray("artists").getJSONObject(0).getString("name");
            System.out.println((i + 1) + ". " + songName + " by " + artistName);
        }
    }
    private static void getFollowedArtists(String accessToken) throws Exception {
        CloseableHttpClient client = HttpClients.createDefault();
        String url = "https://api.spotify.com/v1/me/following?type=artist";
    
        HttpGet get = new HttpGet(url);
        get.setHeader("Authorization", "Bearer " + accessToken);
    
        HttpResponse response = client.execute(get);
        String responseBody = EntityUtils.toString(response.getEntity());
        

        // Yanıtı JSON nesnesine çevirme
        JSONObject json = new JSONObject(responseBody);
        
        JSONArray artists = json.getJSONObject("artists").getJSONArray("items");
    
        System.out.println("Followed Artists:");
        for (int i = 0; i < artists.length(); i++) {
            JSONObject artist = artists.getJSONObject(i);
            String artistName = artist.getString("name");
            System.out.println((i + 1) + ". " + artistName);
        }
    }
    
    private static void createPlaylistByTempo(String accessToken, int tempo, int songCount) throws Exception {
        CloseableHttpClient client = HttpClients.createDefault();
        String url = RECCOMMENDATIONS_URL + "?limit=" + songCount + "&target_tempo=" + tempo + "&seed_genres=country";  // seed_genres: Örnek bir tür
        HttpGet get = new HttpGet(url);
        get.setHeader("Authorization", "Bearer " + accessToken);

        HttpResponse response = client.execute(get);
        String responseBody = EntityUtils.toString(response.getEntity());
        JSONObject json = new JSONObject(responseBody);
        JSONArray tracks = json.getJSONArray("tracks");

        System.out.println("Generated Playlist (Tempo: " + tempo + "):");
        for (int i = 0; i < tracks.length(); i++) {
            JSONObject track = tracks.getJSONObject(i);
            String songName = track.getString("name");
            String artistName = track.getJSONArray("artists").getJSONObject(0).getString("name");
            System.out.println((i + 1) + ". " + songName + " by " + artistName);
        }
    }
}
