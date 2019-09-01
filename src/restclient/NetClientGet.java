package restclient;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.imageio.IIOException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class NetClientGet {


    /*private static HttpURLConnection connection;

    public static void main(String args[]) {

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
            parse(responseContent.toString());

        } catch(MalformedURLException e) {
            System.out.println(e);
        } catch (IOException e){
            System.out.println(e);
        } finally {
            //Disconnect our connection
            connection.disconnect();
        }
    }

    public static String parse(String response){
        JSONArray users = new JSONArray(response);
        for(int i = 0; i < users.length(); i++) {
            JSONObject user = users.getJSONObject(i);
            int id = user.getInt("id");
            String email = user.getString("email");
            String name = user.getString("name");
            System.out.println(id + " - " + email + " - " + name);
        }
        return null;
    }*/
}
