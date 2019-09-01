package Controller;

import model.Question;
import model.Survey;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class QuestionController {
    ArrayList<Question> questions = new ArrayList<>();
    Question singleQuestion;
    private static HttpURLConnection connection;

    public QuestionController() {

    }

    public ArrayList<Question> getQuestions(int surveyId) {
        getAllQuestionsFromDBBySurveyId(surveyId);
        return questions;
    }

    public Question getQuestionById(int questionId) {
        getQuestionFromDBById(questionId);
        return singleQuestion;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    private void getQuestionFromDBById(int questionId) {
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();

        try {
            URL url = new URL("http://localhost:8080/questions/" + questionId);
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
            //setQuestions(parse(responseContent.toString()));
            singleQuestion = singleParse(responseContent.toString());

        } catch(MalformedURLException e) {
            System.out.println(e);
        } catch (IOException e){
            System.out.println(e);
        } finally {
            //Disconnect our connection
            connection.disconnect();
        }
    }

    private void getAllQuestionsFromDBBySurveyId(int id) {

        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();

        try {
            URL url = new URL("http://localhost:8080/questions/search/survey/" + id);
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
            setQuestions(parse(responseContent.toString()));

        } catch(MalformedURLException e) {
            System.out.println(e);
        } catch (IOException e){
            System.out.println(e);
        } finally {
            //Disconnect our connection
            connection.disconnect();
        }
    }

    public static ArrayList<Question> parse(String responseBody) {
        ArrayList<Question> listToReturn = new ArrayList<>();
        JSONArray questions = new JSONArray(responseBody);
        for(int i = 0; i < questions.length(); i++) {
            JSONObject question = questions.getJSONObject(i);
            Question newQuestion = new Question();
            newQuestion.setId(question.getInt("id"));
            newQuestion.setQuestion(question.getString("question"));
            newQuestion.setHasMultipleAnswer(question.getBoolean("hasMultipleAnswer"));
            listToReturn.add(newQuestion);
        }
        return listToReturn;
    }

    public static Question singleParse(String responseBody) {
        Question questionToReturn = new Question();
        JSONObject question = new JSONObject(responseBody);
        questionToReturn.setId(question.getInt("id"));
        questionToReturn.setQuestion(question.getString("question"));
        questionToReturn.setHasMultipleAnswer(question.getBoolean("hasMultipleAnswer"));
        return questionToReturn;
    }
}
