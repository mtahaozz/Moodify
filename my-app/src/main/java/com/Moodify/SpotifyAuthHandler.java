package com.Moodify;

import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class SpotifyAuthHandler {
    private static final String USER_PROFILE_URL = "https://api.spotify.com/v1/me";
    private static final String USER_TRACKS_URL = "https://api.spotify.com/v1/me/tracks";

    public static String getUserProfile(String accessToken) throws Exception {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(USER_PROFILE_URL);
        get.setHeader("Authorization", "Bearer " + accessToken);
        
        HttpResponse response = client.execute(get);
        String responseBody = EntityUtils.toString(response.getEntity());
        JSONObject json = new JSONObject(responseBody);

        System.out.println("User ID: " + json.getString("id"));
        System.out.println("Display Name: " + json.getString("display_name"));
        return json.getString("display_name");
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

    public static ArrayList<String> getUserPlaylistIds(String accessToken) {
        String url = "https://api.spotify.com/v1/me/playlists";
        ArrayList<String> playlistIds = new ArrayList<>();

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet get = new HttpGet(url);
            get.setHeader("Authorization", "Bearer " + accessToken);

            HttpResponse response = client.execute(get);
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == 200) {
                String responseBody = EntityUtils.toString(response.getEntity());
                JSONObject jsonResponse = new JSONObject(responseBody);

                JSONArray playlists = jsonResponse.getJSONArray("items");

                for (int i = 0; i < playlists.length(); i++) {
                    JSONObject playlist = playlists.getJSONObject(i);
                    String id = playlist.getString("id"); // Playlist ID'sini al
                    playlistIds.add(id); // Listeye ekle
                }

            } else {
                String errorResponse = EntityUtils.toString(response.getEntity());
                System.err.println("Error: " + statusCode + " - " + errorResponse);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return playlistIds;
    }

    public static void getPlaylistDetails(String accessToken, String playlistId) {
        String url = "https://api.spotify.com/v1/playlists/" + playlistId;
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet get = new HttpGet(url);
            get.setHeader("Authorization", "Bearer " + accessToken);

            HttpResponse response = client.execute(get);
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == 200) {
                String responseBody = EntityUtils.toString(response.getEntity());
                JSONObject playlist = new JSONObject(responseBody);

                // Çalma listesi temel bilgilerini al
                String name = playlist.getString("name");
                String description = playlist.getString("description");
                int totalTracks = playlist.getJSONObject("tracks").getInt("total");
                String owner = playlist.getJSONObject("owner").getString("display_name");
                int likeCount = playlist.getJSONObject("followers").getInt("total");

                // Çalma listesi bilgilerini yazdır
                System.out.println("Playlist Details:");
                System.out.println("Name: " + name);
                System.out.println("Description: " + (description.isEmpty() ? "No description" : description));
                System.out.println("Total Tracks: " + totalTracks);
                System.out.println("Owner: " + owner);
                System.out.println("Likes (Followers): " + likeCount);
            } else {
                String errorResponse = EntityUtils.toString(response.getEntity());
                System.err.println("Error: " + statusCode + " - " + errorResponse);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static ArrayList<String> getTracksFromPlaylist(String accessToken, String playlistId) {
        String url = "https://api.spotify.com/v1/playlists/" + playlistId + "/tracks";
        ArrayList<String> trackIds = new ArrayList<>();

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet get = new HttpGet(url);
            get.setHeader("Authorization", "Bearer " + accessToken);

            HttpResponse response = client.execute(get);
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == 200) {
                String responseBody = EntityUtils.toString(response.getEntity());
                JSONObject jsonResponse = new JSONObject(responseBody);

                JSONArray items = jsonResponse.getJSONArray("items");
                for (int i = 0; i < items.length(); i++) {
                    JSONObject trackObject = items.getJSONObject(i).getJSONObject("track");
                    String trackId = trackObject.getString("id");
                    trackIds.add(trackId); // Şarkı ID'sini listeye ekle
                }
            } else {
                String errorResponse = EntityUtils.toString(response.getEntity());
                System.err.println("Error fetching tracks for playlist " + playlistId + ": " + statusCode + " - " + errorResponse);
            }
        } catch (Exception e) {
             e.printStackTrace();
        }
        return trackIds;
    }

    public static ArrayList<String> getTrackDetails(String accessToken, String trackId) {
        String url = "https://api.spotify.com/v1/tracks/" + trackId;
        ArrayList<String> trackDetails = new ArrayList<>();

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet get = new HttpGet(url);
            get.setHeader("Authorization", "Bearer " + accessToken);

            HttpResponse response = client.execute(get);
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == 200) {
                String responseBody = EntityUtils.toString(response.getEntity());
                JSONObject jsonResponse = new JSONObject(responseBody);

                // Extract track details
                String trackName = jsonResponse.getString("name");
                String albumName = jsonResponse.getJSONObject("album").getString("name");
                int popularity = jsonResponse.getInt("popularity");

                // Extract artist names
                StringBuilder artists = new StringBuilder();
                for (int i = 0; i < jsonResponse.getJSONArray("artists").length(); i++) {
                    if (i > 0) artists.append(", ");
                    artists.append(jsonResponse.getJSONArray("artists").getJSONObject(i).getString("name"));
                }

                // Add details to the list
                trackDetails.add("Track Name: " + trackName);
                trackDetails.add("Artists: " + artists);
                trackDetails.add("Album: " + albumName);
                trackDetails.add("Popularity: " + popularity);

            } else {
                String errorResponse = EntityUtils.toString(response.getEntity());
                System.err.println("Error: " + statusCode + " - " + errorResponse);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return trackDetails;
    }
}
