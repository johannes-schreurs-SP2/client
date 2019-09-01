package Controller;

import model.User;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class UserController {

    User user = new User();
    private static HttpURLConnection connection;

    public UserController() {

    }

    public User getUserByEmail(String email) {
        getUserByEmailFromDB(email);
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    private void getUserByEmailFromDB(String email) {

        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();

        try {
            URL url = new URL("http://localhost:8080/users/search/email/" + email);
            connection = (HttpURLConnection) url.openConnection();

            //Request setup
            connection.setRequestMethod("GET");
            //Stop the process of fetching if it takes too long (5000 ms)
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);

            int status = connection.getResponseCode();

            //Log if there is a problem with the connection
            if(status > 299) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
            } else {
                //succesful connection
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                //Stop reading
                reader.close();
            }
            user = parseUser(responseContent.toString());

        } catch(MalformedURLException e) {
            System.out.println(e);
        } catch (IOException e){
            System.out.println(e);
        } finally {
            //Disconnect our connection
            connection.disconnect();
        }
    }

    public void addUserToDB(String email) {

        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();

        try {
            URL url = new URL("http://localhost:8080/users");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            String jsonInputString = "{\"id\": 1, \"email\": \""+email+"\"}";

            try(OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int status = connection.getResponseCode();

            //Log if there is a problem with the connection
            if(status > 299) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
            } else {
                //succesful connection
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                //Stop reading
                reader.close();
            }

        } catch(MalformedURLException e) {
            System.out.println(e);
        } catch(IOException e) {
            System.out.println(e);
        }

    }


    public static User parseUser(String responseBody) {
        User userToReturn = new User();
        JSONObject user = new JSONObject(responseBody);
        userToReturn.setId(user.getInt("id"));
        userToReturn.setEmail(user.getString("email"));

        return userToReturn;
    }

}
