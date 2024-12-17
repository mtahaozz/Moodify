package com.Moodify;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.channels.AcceptPendingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
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
    
    public static int getUserFollowers(String accessToken) {
        String url = "https://api.spotify.com/v1/me";
    
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            // API isteği oluşturuluyor
            HttpGet get = new HttpGet(url);
            get.setHeader("Authorization", "Bearer " + accessToken);
    
            // İstek gönderiliyor ve yanıt alınıyor
            HttpResponse response = client.execute(get);
            int statusCode = response.getStatusLine().getStatusCode();
    
            // Yanıt başarılı ise (HTTP 200 OK)
            if (statusCode == 200) {
                String responseBody = EntityUtils.toString(response.getEntity());
                JSONObject jsonResponse = new JSONObject(responseBody);
    
                // Takipçi sayısını döndür
                return jsonResponse.getJSONObject("followers").getInt("total");
            } else {
                // Hata durumunda mesaj yazdır
                String errorResponse = EntityUtils.toString(response.getEntity());
                System.err.println("Error: " + statusCode + " - " + errorResponse);
            }
        } catch (Exception e) {
            // İstisnaları yakala ve yazdır
            e.printStackTrace();
        }
    
        // Hata durumunda null döndür
        return 0;
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
    public static Playlist getLikedSongs(String accessToken) throws Exception {
        
        Playlist likedSongs = new Playlist();
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
            String songId = trackObject.getString("id");
            likedSongs.addSongByTrackID(songId);
        }

        return likedSongs;
    }

    
    public static int getUsersTotalFollowers(String accessToken) {
        String url = "https://api.spotify.com/v1/me";
        int totalFollowers = 0;

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet get = new HttpGet(url);
            get.setHeader("Authorization", "Bearer " + accessToken);

            try (CloseableHttpResponse response = client.execute(get)) {
                int statusCode = response.getStatusLine().getStatusCode();

                if (statusCode == 200) {
                    String responseBody = EntityUtils.toString(response.getEntity());
                    JSONObject jsonResponse = new JSONObject(responseBody);

                    // Kullanıcıya ait toplam takipçi sayısını al
                    JSONObject followersObject = jsonResponse.getJSONObject("followers");
                    totalFollowers = followersObject.optInt("total", 0);
                } else {
                    String errorResponse = EntityUtils.toString(response.getEntity());
                    System.err.println("Error: " + statusCode + " - " + errorResponse);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return totalFollowers;
    }
    public static void playTrackById(String accessToken, String trackId) {
        String url = "https://api.spotify.com/v1/me/player/play";

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPut putRequest = new HttpPut(url);
            putRequest.setHeader("Authorization", "Bearer " + accessToken);
            putRequest.setHeader("Content-Type", "application/json");

            // Request body içinde oynatılacak şarkı ID'sini belirt
            String jsonBody = "{\"uris\": [\"spotify:track:" + trackId + "\"]}";
            putRequest.setEntity(new StringEntity(jsonBody));

            try (CloseableHttpResponse response = client.execute(putRequest)) {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 204) {
                    System.out.println("Track is now playing!");
                } else {
                    String errorResponse = EntityUtils.toString(response.getEntity());
                    System.err.println("Error: " + statusCode + " - " + errorResponse);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static ArrayList<String> getFollowedArtists(String accessToken) throws Exception {
        ArrayList<String> artistNames = new ArrayList<>();
        CloseableHttpClient client = HttpClients.createDefault();
        String url = "https://api.spotify.com/v1/me/following?type=artist";

        HttpGet get = new HttpGet(url);
        get.setHeader("Authorization", "Bearer " + accessToken);

        HttpResponse response = client.execute(get);
        String responseBody = EntityUtils.toString(response.getEntity());

        // Yanıtı JSON nesnesine çevirme
        JSONObject json = new JSONObject(responseBody);

        JSONArray artists = json.getJSONObject("artists").getJSONArray("items");

        // Sanatçı isimlerini listeye ekleme
        for (int i = 0; i < artists.length(); i++) {
            JSONObject artist = artists.getJSONObject(i);
            String artistName = artist.getString("name");
            artistNames.add(artistName);
        }

        return artistNames;
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

    public static int getUserPlaylistTotalFollowers(String accessToken){
        
        ArrayList<String> PlaylistIds = getUserPlaylistIds(accessToken);
        int totalfollowers = 0;
        String currentUserID = getUserId(accessToken);
        for (String playlistid : PlaylistIds) {
            String url = "https://api.spotify.com/v1/playlists/" + playlistid;
            
            try (CloseableHttpClient client = HttpClients.createDefault()) {
                HttpGet get = new HttpGet(url);
                get.setHeader("Authorization", "Bearer " + accessToken);

                HttpResponse response = client.execute(get);
                int statusCode = response.getStatusLine().getStatusCode();

                if (statusCode == 200) {
                    String responseBody = EntityUtils.toString(response.getEntity());
                    JSONObject playlist = new JSONObject(responseBody);
                    // Çalma listesi temel bilgilerini al
                    String ownerID = playlist.getJSONObject("owner").getString("id");
                    if(currentUserID.equals(ownerID)){
                        totalfollowers += playlist.getJSONObject("followers").getInt("total");
                    }
                    
                } else {
                    String errorResponse = EntityUtils.toString(response.getEntity());
                    System.err.println("Error: " + statusCode + " - " + errorResponse);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return totalfollowers;
    }

    public static void skipToNextTrack(String accessToken) {
        String url = "https://api.spotify.com/v1/me/player/next";

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost postRequest = new HttpPost(url);
            postRequest.setHeader("Authorization", "Bearer " + accessToken);

            try (CloseableHttpResponse response = client.execute(postRequest)) {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 204) {
                    System.out.println("Skipped to the next track!");
                } else {
                    String errorResponse = EntityUtils.toString(response.getEntity());
                    System.err.println("Error: " + statusCode + " - " + errorResponse);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void skipToPreviousTrack(String accessToken) {
        String url = "https://api.spotify.com/v1/me/player/previous";

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost postRequest = new HttpPost(url);
            postRequest.setHeader("Authorization", "Bearer " + accessToken);

            try (CloseableHttpResponse response = client.execute(postRequest)) {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 204) {
                    System.out.println("Skipped to the previous track!");
                } else {
                    String errorResponse = EntityUtils.toString(response.getEntity());
                    System.err.println("Error: " + statusCode + " - " + errorResponse);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void pauseTrack(String accessToken) {
        String url = "https://api.spotify.com/v1/me/player/pause";

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPut putRequest = new HttpPut(url);
            putRequest.setHeader("Authorization", "Bearer " + accessToken);

            try (CloseableHttpResponse response = client.execute(putRequest)) {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 204) {
                    System.out.println("Playback paused successfully!");
                } else {
                    String errorResponse = EntityUtils.toString(response.getEntity());
                    System.err.println("Error: " + statusCode + " - " + errorResponse);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void resumePlayback(String accessToken) {
        String url = "https://api.spotify.com/v1/me/player/play";

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPut putRequest = new HttpPut(url);
            putRequest.setHeader("Authorization", "Bearer " + accessToken);

            try (CloseableHttpResponse response = client.execute(putRequest)) {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 204) {
                    System.out.println("Playback resumed successfully.");
                } else {
                    System.err.println("Error: " + statusCode + " - Unable to resume playback.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static ArrayList<String> getPlaylistDetails(String accessToken, String playlistId) {
        String url = "https://api.spotify.com/v1/playlists/" + playlistId;
        ArrayList<String> details = new ArrayList<>();
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
                details.add(name);
                details.add(totalTracks+"");
                details.add(owner);
            } else {
                String errorResponse = EntityUtils.toString(response.getEntity());
                System.err.println("Error: " + statusCode + " - " + errorResponse);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return details;
    }


    public static ArrayList<String> getTracksFromPlaylist(String accessToken, String playlistId) {
        String url = "https://api.spotify.com/v1/playlists/" + playlistId + "/tracks";
        ArrayList<String> trackIds = new ArrayList<>();

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            boolean hasNext = true;

            while (hasNext) {
                HttpGet get = new HttpGet(url);
                get.setHeader("Authorization", "Bearer " + accessToken);

                HttpResponse response = client.execute(get);
                int statusCode = response.getStatusLine().getStatusCode();

                if (statusCode == 200) {
                    String responseBody = EntityUtils.toString(response.getEntity());
                    JSONObject jsonResponse = new JSONObject(responseBody);

                    JSONArray items = jsonResponse.getJSONArray("items");
                    for (int i = 0; i < items.length(); i++) {
                        JSONObject item = items.getJSONObject(i);
                        
                        // 'track' kontrolü
                        if (item.isNull("track")) {
                            System.err.println("Item at index " + i + " has no 'track' object.");
                            continue;
                        }
                        JSONObject trackObject = item.optJSONObject("track");
                        if (trackObject != null && !trackObject.isNull("id")) {
                            try {
                                String trackId = trackObject.getString("id");
                                trackIds.add(trackId); // Şarkı ID'sini listeye ekle
                            } catch (Exception e) {
                                System.err.println("Error parsing 'id' for item at index " + i + ": " + e.getMessage());
                            }
                        } else {
                            System.err.println("Track object is null or 'id' is missing for item at index " + i);
                        }
                    }

                    // Sonraki sayfa kontrolü
                    hasNext = jsonResponse.has("next") && !jsonResponse.isNull("next");
                    if (hasNext) {
                        url = jsonResponse.getString("next");
                    }
                } else {
                    String errorResponse = EntityUtils.toString(response.getEntity());
                    System.err.println("Error fetching tracks for playlist " + playlistId + ": " + statusCode + " - " + errorResponse);
                    hasNext = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return trackIds;
        /*String url = "https://api.spotify.com/v1/playlists/" + playlistId + "/tracks";
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
        return trackIds;*/
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
                trackDetails.add(trackName);
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
