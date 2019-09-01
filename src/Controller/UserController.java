package Controller;

import model.User;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class UserController {

    ArrayList<User> users = new ArrayList<>();
    private static HttpURLConnection connection;

    public UserController() {

    }

    public ArrayList<User> getUsers() {
        getAllUsersFromDB();
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public void getAllUsersFromDB() {

        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();

        try {
            URL url = new URL("http://localhost:8080/users");
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

            System.out.println(responseContent.toString());
            setUsers(parse(responseContent.toString()));

        } catch(MalformedURLException e) {
            System.out.println(e);
        } catch (IOException e){
            System.out.println(e);
        } finally {
            //Disconnect our connection
            connection.disconnect();
        }
    }

    public static ArrayList<User> parse(String responseBody) {
        ArrayList<User> listToReturn = new ArrayList<>();
        JSONArray users = new JSONArray(responseBody);
        for(int i = 0; i < users.length(); i++) {
            JSONObject user = users.getJSONObject(i);
            User newUser = new User(user.getInt("id"), user.getString("name"));
            listToReturn.add(newUser);
        }
        return listToReturn;
    }


}
