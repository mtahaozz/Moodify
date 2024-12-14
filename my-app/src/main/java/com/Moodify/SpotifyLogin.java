package com.Moodify;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.Moodify.Frames.MainMenuFrame;
import com.Moodify.Frames.settingsPage1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.ArrayList;

public class SpotifyLogin {

    // Spotify'dan alınan bilgiler
    private static final String CLIENT_ID = "5dc669c3c0da451f9771a053c480ccd7";
    private static final String CLIENT_SECRET = "6ed37c567e6749879347cd7de0861286";
    private static final String REDIRECT_URI = "http://localhost:8181/callback";
    private static final String AUTH_URL = "https://accounts.spotify.com/authorize";
    private static final String TOKEN_URL = "https://accounts.spotify.com/api/token";




    public static void main(String[] args) throws Exception {

        System.out.println("Please authorize the app by visiting this URL: ");
        String authLink = AUTH_URL + "?client_id=" + CLIENT_ID +
                "&response_type=code&redirect_uri=" + REDIRECT_URI + "&scope=user-read-private%20user-library-read%20user-follow-read%20playlist-read-private";
        System.out.println(authLink);

        System.out.println("Enter the authorization code: ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String authCode = reader.readLine();
        String accessToken = getAccessToken(authCode);

        SpotifyAuthHandler.getPlaylistDetails(accessToken, accessToken);
        ArrayList<String> userPlaylistIDS = SpotifyAuthHandler.getUserPlaylistIds(accessToken);
        SpotifyAuthHandler.getPlaylistDetails(accessToken, userPlaylistIDS.get(1));


        ArrayList<song> songList = new ArrayList<>();
        songList.add(new song("ilk", null, 10, 0, "a"));
        songList.add(new song("ilk2", null, 10, 0, "a"));
        songList.add(new song("ilk3", null, 10, 0, "a"));
        songList.add(new song("ilk4", null, 10, 0, "a"));
        songList.add(new song("ilk5", null, 10, 0, "a"));
        songList.add(new song("b1", null, 10, 0, "b"));
        songList.add(new song("b2", null, 10, 0, "b"));
        songList.add(new song("b3", null, 10, 0, "b"));
        songList.add(new song("b4", null, 10, 0, "b"));
        songList.add(new song("b5", null, 10, 0, "b"));
        songList.add(new song("b6", null, 10, 0, "b"));
        songList.add(new song("c1", null, 10, 0, "c"));
        songList.add(new song("c2", null, 10, 0, "c"));
        songList.add(new song("c3", null, 10, 0, "c"));
        songList.add(new song("c4", null, 10, 0, "c"));
        songList.add(new song("c5", null, 10, 0, "c"));
        songList.add(new song("c6", null, 10, 0, "c"));

        
        ArrayList<String> userGenres = new ArrayList<>();
        userGenres.add("a");
        userGenres.add("c");

        Genremix newGenreMix = new Genremix(userGenres, "31", 5, true, songList);

        for (song song : newGenreMix.getSongsList()) {
            System.out.println(song.getSongName());
        }

        //ArrayList<String> userTrackIds = SpotifyAuthHandler.getTracksFromPlaylist(accessToken, userPlaylistIDS.get(1));
        /*for (String string : userTrackIds) {
            ArrayList<String> trackDetails = SpotifyAuthHandler.getTrackDetails(accessToken, string);
            System.out.println(trackDetails);
        }*/
        
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

    
}
