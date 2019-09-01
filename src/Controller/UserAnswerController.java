package Controller;

import model.User;
import model.UserAnswer;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class UserAnswerController {

    private static HttpURLConnection connection;

    private UserAnswer userAnswer = new UserAnswer();

    public void addUserAnswerToDB(int userId, int surveyId, int answerId, Boolean answered) {

        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();

        try {
            URL url = new URL("http://localhost:8080/useranswers");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            String jsonInputString = "{\"user\": {\"id\":"+userId+"}, \"survey\": {\"id\":\""+surveyId+"\"}, \"answer\": {\"id\":\""+ answerId +"\"}, \"answered\":\""+ answered +"\"}";

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
                System.out.println("Answer registerd");
            }

        } catch(MalformedURLException e) {
            System.out.println(e);
        } catch(IOException e) {
            System.out.println(e);
        }

    }

}


