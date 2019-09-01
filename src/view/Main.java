package view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
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
import Controller.UserAnswerController;

import java.util.ArrayList;

public class Main extends Application {

    UserController userController = new UserController();
    SurveyController surveyController = new SurveyController();
    QuestionController questionController = new QuestionController();
    AnswerController answerController = new AnswerController();
    UserAnswerController userAnswerController = new UserAnswerController();

    Survey activeSurvey;
    Question activeQuestion;
    User activeUser;

    //this is some pretty nested bullshit, but it works and refactoring just breaks it.
    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root = new Pane();

        TextField txtUsername = new TextField();
        Button btnUsername = new Button("Submit");
        txtUsername.setPromptText("Email");
        Label label = new Label("Please enter your email address");
        Stage secondStage = new Stage();
        Stage thirdStage = new Stage();

        //GO to the list of surveys
        btnUsername.setOnAction(e -> {
            userController.addUserToDB(txtUsername.getText());
            activeUser = userController.getUserByEmail(txtUsername.getText());


            StackPane root2 = new StackPane();

            ListView<Survey> listView = new ListView();
            for(Survey u : surveyController.getSurveys()) {
                listView.getItems().add(u);
            }

            //Go to the selected survey
            listView.setOnMouseClicked(event -> {
                activeSurvey = listView.getSelectionModel().getSelectedItem();


                Pagination pagination = new Pagination();
                pagination.getStylesheets().add("view/StyleSheet.css");

                ArrayList<Question> questions = questionController.getQuestions(activeSurvey.getId());

                pagination.setPageCount(questions.size() != 0 ? questions.size() : 1);

                int[] questionIds = new int[questions.size()];

                for(int i = 0; i<questionIds.length; i++) {
                    questionIds[i] = questions.get(i).getId();
                }

                pagination.setCurrentPageIndex(0);
                pagination.setMaxPageIndicatorCount(5);

                //Set the question to each survey
                pagination.setPageFactory(pageIndex -> {
                    activeQuestion = questionController.getQuestionById(questionIds[pageIndex]);

                    Label label1 = new Label(activeQuestion.getQuestion());
                    VBox vBox = new VBox();
                    vBox.setPadding(new Insets(10, 10, 50, 50));
                    Button submitButton = new Button("Choose an option first.");
                    submitButton.setDisable(true);

                    ArrayList<Answer> answersToDisplay = answerController.getAnswers(questionIds[pageIndex]);

                    ToggleGroup toggleGroup = new ToggleGroup();

                    vBox.getChildren().add(label1);

                    //Add all different options
                    for(Answer a : answersToDisplay) {
                        RadioButton radioButton = new RadioButton(a.getAnswer());
                        radioButton.setOnAction(radioButtonClick -> {
                            submitButton.setDisable(false);
                            submitButton.setText("Submit your answer.");
                        });
                        radioButton.setToggleGroup(toggleGroup);
                        radioButton.setUserData(a.getId());
                        vBox.getChildren().add(radioButton);
                    }



                    //Send the answers from the user to the database.
                    submitButton.setOnAction(click -> {
                        userAnswerController.addUserAnswerToDB(activeUser.getId(), activeSurvey.getId(), (int)toggleGroup.getSelectedToggle().getUserData(), true);
                        if(pageIndex != questionIds.length - 1) {
                            pagination.setCurrentPageIndex(pageIndex + 1);
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Thanks!");
                            alert.setHeaderText(null);
                            alert.setContentText("Thank you! Your answer has been registered.");
                            alert.showAndWait();


                        } else {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Thanks!");
                            alert.setHeaderText(null);
                            alert.setContentText("Thank you for completing this survey!");
                            alert.showAndWait();
                            Platform.exit();
                            System.exit(0);
                        }
                    });


                    vBox.getChildren().add(submitButton);
                    return vBox;
                });


                VBox vbox = new VBox(20, pagination);
                Scene thirdScene = new Scene(vbox, 400, 300);
                thirdStage.setScene(thirdScene);
                thirdStage.setTitle(activeSurvey.getName());
                thirdStage.show();
                secondStage.close();
            });

            root2.getChildren().add(listView);
            Scene secondScene = new Scene(root2, 500,500);
            secondStage.setScene(secondScene);
            secondStage.setTitle("Please select a survey");
            secondStage.show();
            primaryStage.close(); // close the first stage (Window)
        });

        label.setLayoutY(100);
        label.setLayoutX(150);

        txtUsername.setLayoutX(150);
        txtUsername.setLayoutY(150);

        btnUsername.setLayoutX(200);
        btnUsername.setLayoutY(200);

        root.getChildren().add(label);
        root.getChildren().add(txtUsername);
        root.getChildren().add(btnUsername);
        Scene scene = new Scene(root, 500,400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Log in page");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
