package com.Moodify;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
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
    public static String getUserId(String accessToken) {
        String url = "https://api.spotify.com/v1/me";

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet get = new HttpGet(url);
            get.setHeader("Authorization", "Bearer " + accessToken);

            HttpResponse response = client.execute(get);
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == 200) {
                String responseBody = EntityUtils.toString(response.getEntity());
                JSONObject jsonResponse = new JSONObject(responseBody);
                return jsonResponse.getString("id");
            } else {
                String errorResponse = EntityUtils.toString(response.getEntity());
                System.err.println("Error: " + statusCode + " - " + errorResponse);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
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
    public static ArrayList<String> getArtistTopTrackIds(String accessToken, String artistId, String market) {
        String url = "https://api.spotify.com/v1/artists/" + artistId + "/top-tracks?market=" + market;
        ArrayList<String> trackIds = new ArrayList<>();
    
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet get = new HttpGet(url);
            get.setHeader("Authorization", "Bearer " + accessToken);
    
            HttpResponse response = client.execute(get);
            int statusCode = response.getStatusLine().getStatusCode();
    
            if (statusCode == 200) {
                String responseBody = EntityUtils.toString(response.getEntity());
                JSONObject jsonResponse = new JSONObject(responseBody);
    
                JSONArray tracks = jsonResponse.getJSONArray("tracks");
    
                for (int i = 0; i < tracks.length(); i++) {
                    JSONObject track = tracks.getJSONObject(i);
                    String trackId = track.getString("id");
                    trackIds.add(trackId);
                }
    
                //System.out.println("Popüler Şarkı ID'leri:");
                // for (int i = 0; i < trackIds.size(); i++) {
                //     System.out.println((i + 1) + ". " + trackIds.get(i));
                // }
    
            } else {
                String errorResponse = EntityUtils.toString(response.getEntity());
                System.err.println("Hata: " + statusCode + " - " + errorResponse);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return trackIds;
    }
    
    public static String createPlaylistFromTrackId(String accessToken, String userId, String playlistName, ArrayList<String> trackIds, boolean isPublic) {
        String url = "https://api.spotify.com/v1/users/" + userId + "/playlists";

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            // Create the playlist
            HttpPost post = new HttpPost(url);
            post.setHeader("Authorization", "Bearer " + accessToken);
            post.setHeader("Content-Type", "application/json");

            JSONObject playlistDetails = new JSONObject();
            playlistDetails.put("name", playlistName);
            playlistDetails.put("description", "Generated playlist");
            playlistDetails.put("public", isPublic);

            StringEntity entity = new StringEntity(playlistDetails.toString());
            post.setEntity(entity);

            HttpResponse response = client.execute(post);
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == 201) {
                String responseBody = EntityUtils.toString(response.getEntity());
                JSONObject jsonResponse = new JSONObject(responseBody);
                String playlistId = jsonResponse.getString("id");

                // Add tracks to the playlist
                String addTracksUrl = "https://api.spotify.com/v1/playlists/" + playlistId + "/tracks";
                HttpPost addTracksPost = new HttpPost(addTracksUrl);
                addTracksPost.setHeader("Authorization", "Bearer " + accessToken);
                addTracksPost.setHeader("Content-Type", "application/json");

                JSONObject tracksBody = new JSONObject();
                JSONArray uris = new JSONArray();
                for (String trackId : trackIds) {
                    uris.put("spotify:track:" + trackId);
                }
                tracksBody.put("uris", uris);

                StringEntity tracksEntity = new StringEntity(tracksBody.toString());
                addTracksPost.setEntity(tracksEntity);

                HttpResponse addTracksResponse = client.execute(addTracksPost);
                int addTracksStatusCode = addTracksResponse.getStatusLine().getStatusCode();

                if (addTracksStatusCode == 201) {
                    return "Playlist created successfully with ID: " + playlistId;
                } else {
                    String errorResponse = EntityUtils.toString(addTracksResponse.getEntity());
                    System.err.println("Error adding tracks: " + addTracksStatusCode + " - " + errorResponse);
                }
            } else {
                String errorResponse = EntityUtils.toString(response.getEntity());
                System.err.println("Error creating playlist: " + statusCode + " - " + errorResponse);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "Failed to create playlist";
    }

    public static String getArtistId(String accessToken, String artistName) throws Exception {
        artistName = URLEncoder.encode(artistName, StandardCharsets.UTF_8.toString());

        String url = "https://api.spotify.com/v1/search?q=" + artistName + "&type=artist";
        
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet get = new HttpGet(url);
            get.setHeader("Authorization", "Bearer " + accessToken);
            
            HttpResponse response = client.execute(get);
            int statusCode = response.getStatusLine().getStatusCode();
            
            if (statusCode == 200) {
                String responseBody = EntityUtils.toString(response.getEntity());
                JSONObject jsonResponse = new JSONObject(responseBody);
                JSONArray artists = jsonResponse.getJSONObject("artists").getJSONArray("items");
    
                if (artists.length() > 0) {
                    JSONObject artist = artists.getJSONObject(0); // İlk sanatçı
                    String artistId = artist.getString("id");
                    String artistNameFound = artist.getString("name");
                    //System.out.println("Sanatçı: " + artistNameFound + " | ID: " + artistId);
                    return artistId;
                } else {
                    //System.out.println("Sanatçı bulunamadı!");
                }
            } else {
                String errorResponse = EntityUtils.toString(response.getEntity());
                System.err.println("Hata: " + statusCode + " - " + errorResponse);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
