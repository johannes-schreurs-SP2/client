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
import model.User;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        StackPane root = new StackPane();
        Button button = new Button("Go To Second Form");
        Label label = new Label("Enter your username");
        label.setLayoutX(100);
        label.setLayoutY(100);
        root.getChildren().add(button); 
        root.getChildren().add(label);
        Scene scene = new Scene(root, 500,500); // create the scene and set the root, width and height
        primaryStage.setScene(scene); // set the scene
        primaryStage.setTitle("Register page");
        primaryStage.show();

        // add action listener, I will use the lambda style (which is data and code at the same time, read more about it in Oracle documentation)
        button.setOnAction(e->{
            //primaryStage.close(); // ndex++;
            // create the structure again for the second GUI
            // Note that you CAN use the previous root and scene and just create a new Stage
            //(of course you need to remove the button first from the root like this, root.getChildren().remove(0); at index 0)
            StackPane newRoot = new StackPane();
            Label label2 = new Label("Select a survey");
            newRoot.getChildren().add(label2);
            Scene secondScene = new Scene(newRoot, 500,500);
            Stage secondStage = new Stage();
            secondStage.setScene(secondScene); // set the scene
            secondStage.setTitle("select a survey");
            secondStage.show();
            primaryStage.close(); // close the first stage (Window)
        });
    }

    public static void main(String[] args) {
        launch();

    }
}
