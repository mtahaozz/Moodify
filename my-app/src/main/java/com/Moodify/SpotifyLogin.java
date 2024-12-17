package com.Moodify;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.Moodify.Frames.GenremixFrame;
import com.Moodify.Frames.MainMenuFrame;
import com.Moodify.Frames.settingsPage1;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Scanner;

public class SpotifyLogin {

    // Spotify'dan alınan bilgiler
    private static final String CLIENT_ID = "5dc669c3c0da451f9771a053c480ccd7";
    private static final String CLIENT_SECRET = "6ed37c567e6749879347cd7de0861286";
    private static final String REDIRECT_URI = "http://localhost:8181/callback";
    private static final String AUTH_URL = "https://accounts.spotify.com/authorize";
    private static final String TOKEN_URL = "https://accounts.spotify.com/api/token";


    public static void main(String[] args) throws Exception {
        Inventory.fillAllSongs();
        System.out.println("Please authorize the app by visiting this URL: ");
        String authLink = AUTH_URL + "?client_id=" + CLIENT_ID +
                "&response_type=code&redirect_uri=" + REDIRECT_URI + "&scope=user-read-private%20user-library-read%20user-follow-read%20playlist-read-private%20playlist-modify-public%20playlist-modify-private%20user-modify-playback-state";
        System.out.println(authLink);

        System.out.println("Enter the authorization code: ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String authCode = reader.readLine();
        String accessToken = getAccessToken(authCode);

        Inventory.accessToken = accessToken;
        GenremixFrame g = new GenremixFrame();
        g.setVisible(true);
        // Playlist deneme = SpotifyAuthHandler.getLikedSongs(accessToken);
        // for (song song : deneme.getSongsList()) {
        //     System.out.println(song.getTrackName());
        // }
        /* 
        while(true){
            Scanner input = new Scanner(System.in);
            System.out.println("1(new)or 2(next) or 3(previous) or 4(pause) or 5(resume)");
            int choise = input.nextInt();
            if(choise == 1){
                SpotifyAuthHandler.playTrackById(accessToken, "6lfxq3CG4xtTiEg7opyCyx");
            }else if(choise == 2){
                SpotifyAuthHandler.skipToNextTrack(accessToken);
            }else if(choise == 3){
                SpotifyAuthHandler.skipToPreviousTrack(accessToken);
            }else if(choise == 4){
                SpotifyAuthHandler.pauseTrack(accessToken);
            }else if(choise == 5){
                SpotifyAuthHandler.resumePlayback(accessToken);
            }
            

            
        }*/
        
        //System.out.println(SpotifyAuthHandler.getUsersTotalFollowers(accessToken));
        //MusicRead.fillMusicList(songList,accessToken);
        //MoodList m = new MoodList((float)0.267, (float)130.000, (float)0.212, songList, 50);
        /*for(int i = 0; i<100;i++){
            System.out.println(m.getSongsList().get(i).getMood());
        }*/
        //String artistName = "Chord Overstreet";
        //SpotifyAuthHandler.getArtistId(accessToken, artistName);

        //String userID = SpotifyAuthHandler.getUserId(accessToken);
        //SpotifyAuthHandler.createPlaylistFromTrackId(accessToken, userID, "Fatihin Playlisti", m.getTrackIds(),true);
        //Genremix newGenreMix = new Genremix(userGenres, "31", 5, true, songList);

       

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
