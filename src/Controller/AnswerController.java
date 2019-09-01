package Controller;

import model.Answer;
import model.Question;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class AnswerController {

    ArrayList<Answer> answers = new ArrayList<>();
    private static HttpURLConnection connection;

    public AnswerController() {

    }

    public ArrayList<Answer> getAnswers(int questionId) {
        getAllAnswersFromDBByQuestionId(questionId);
        return answers;
    }

    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }


    public void getAllAnswersFromDBByQuestionId(int id) {

        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();

        try {
            URL url = new URL("http://localhost:8080/answers/search/questions/" + id);
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

            //System.out.println(responseContent.toString());
            setAnswers(parse(responseContent.toString()));

        } catch(MalformedURLException e) {
            System.out.println(e);
        } catch (IOException e){
            System.out.println(e);
        } finally {
            //Disconnect our connection
            connection.disconnect();
        }
    }

    public static ArrayList<Answer> parse(String responseBody) {
        ArrayList<Answer> listToReturn = new ArrayList<>();
        JSONArray answers = new JSONArray(responseBody);
        for(int i = 0; i < answers.length(); i++) {
            JSONObject answer = answers.getJSONObject(i);
            Answer newAnswer = new Answer();
            newAnswer.setId(answer.getInt("id"));
            newAnswer.setAnswer(answer.getString("answer"));
            listToReturn.add(newAnswer);
        }
        return listToReturn;
    }
}
