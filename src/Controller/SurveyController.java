package Controller;

import model.Question;
import model.Survey;
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

public class SurveyController {


    ArrayList<Survey> surveys = new ArrayList<>();
    Survey singleSurvey = new Survey();
    private static HttpURLConnection connection;

    public SurveyController() {

    }

    public ArrayList<Survey> getSurveys() {
        getAllSurveysFromDB();
        return surveys;
    }

    public Survey getSurveyById(int id) {
        getSurveyFromDBById(id);
        return singleSurvey;
    }

    public void setSurveys(ArrayList<Survey> surveys) {
        this.surveys = surveys;
    }

    public void getSurveyFromDBById(int surveyId){
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();

        try {
            URL url = new URL("http://localhost:8080/surveys/" + surveyId);
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
            //setSurveys(parse(responseContent.toString()));
            singleSurvey = singleParse(responseContent.toString());

        } catch(MalformedURLException e) {
            System.out.println(e);
        } catch (IOException e){
            System.out.println(e);
        } finally {
            //Disconnect our connection
            connection.disconnect();
        }
    }

    public void getAllSurveysFromDB() {

        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();

        try {
            URL url = new URL("http://localhost:8080/surveys");
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
            setSurveys(parse(responseContent.toString()));

        } catch(MalformedURLException e) {
            System.out.println(e);
        } catch (IOException e){
            System.out.println(e);
        } finally {
            //Disconnect our connection
            connection.disconnect();
        }
    }

    public static ArrayList<Survey> parse(String responseBody) {
        ArrayList<Survey> listToReturn = new ArrayList<>();
        JSONArray surveys = new JSONArray(responseBody);
        for(int i = 0; i < surveys.length(); i++) {
            JSONObject survey = surveys.getJSONObject(i);
            Survey newSurvey = new Survey();
            newSurvey.setId(survey.getInt("id"));
            newSurvey.setName(survey.getString("name"));
            listToReturn.add(newSurvey);
        }
        return listToReturn;
    }

    public static Survey singleParse(String responseBody) {
        Survey surveyToReturn = new Survey();
        JSONObject survey = new JSONObject(responseBody);
        surveyToReturn.setId(survey.getInt("id"));
        surveyToReturn.setName(survey.getString("name"));
        return surveyToReturn;
    }


}
