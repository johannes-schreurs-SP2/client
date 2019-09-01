package sample;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Answer;
import model.Question;
import model.Survey;
import model.User;
import Controller.UserController;
import Controller.SurveyController;
import Controller.QuestionController;
import Controller.AnswerController;

public class Main extends Application {

    UserController userController = new UserController();
    SurveyController surveyController = new SurveyController();
    QuestionController questionController = new QuestionController();
    AnswerController answerController = new AnswerController();

    Survey activeSurvey;
    Question activeQuestion;

    @Override
    public void start(Stage primaryStage) throws Exception {
        StackPane root = new StackPane();
        ListView<Survey> listView = new ListView();

        for(Survey u : surveyController.getSurveys()) {
            System.out.println(u.getName() + ", id:" + u.getId());
            listView.getItems().add(u);
        }

        listView.setOnMouseClicked(e -> {
            System.out.println("Clicked on survey " + listView.getSelectionModel().getSelectedItem());
            activeSurvey = listView.getSelectionModel().getSelectedItem();
            System.out.println(activeSurvey.getName() + " " + activeSurvey.getId());
        });

        root.getChildren().add(listView);
        Scene scene = new Scene(root, 500,500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Survey Page");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
