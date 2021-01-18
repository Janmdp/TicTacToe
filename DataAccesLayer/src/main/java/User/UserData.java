package User;

import classes.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.jetty.http.HttpStatus;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class UserData {
    private static final String GET_URL ="http://localhost:8080/user/";

    public UserData(){

    }

    public User tryLogin(String name, String password) throws IOException, InterruptedException {
        String URL = GET_URL+ "?name=" + name + "&password=" + password;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .uri(URI.create(URL))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(response.body(), new TypeReference<User>() {});
        System.out.println(user);
        return user;
    }

    public boolean register(String name, String password) throws IOException, InterruptedException {
        String URL = GET_URL+ "?name=" + name + "&password=" + password;
        HttpClient client = HttpClient.newHttpClient();

        User user = new User();
        user.setName(name);
        user.setPassword(password);
        ObjectMapper mapper = new ObjectMapper();
        String requestbody = mapper.writeValueAsString(user);
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(requestbody))
                .header("accept", "application/json")
                .setHeader("Content-type", "application/json")
                .uri(URI.create(GET_URL))
                .build();
        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.statusCode());
        if(response.statusCode() == HttpStatus.OK_200){
            return true;
        }
        return false;

    }


}
