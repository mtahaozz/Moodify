Spotify Java API Client

This project is a Java-based music application integrated with the Spotify Web API. It enables users to connect their Spotify accounts and create playlists based on mood and genre inputs.

How It Works
Run the file located at my-app\src\main\java\com\Moodify\SpotifyLogin.java.
The login frame will appear.
Visit the URL displayed on the login frame and log in with your Spotify account.
After logging in, copy the code parameter from the redirected URL (e.g., code=...) and paste it into the application to authenticate and execute.

Requirements
Java 8+
Apache Maven (for dependency management)
A Spotify account and an API key (Access Token) obtained from the Spotify Developer Console.

Dependencies
This project relies on the following Java libraries, which are included in the pom.xml file:

<dependencies>
    <!-- For JSON processing -->
    <dependency>
        <groupId>org.json</groupId>
        <artifactId>json</artifactId>
        <version>20210307</version>
    </dependency>
    
    <!-- For HTTP requests -->
    <dependency>
        <groupId>org.apache.httpcomponents</groupId>
        <artifactId>httpclient</artifactId>
        <version>4.5.13</version>
    </dependency>
</dependencies>
