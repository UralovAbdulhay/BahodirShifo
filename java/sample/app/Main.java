package sample.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.controllers.Main_Controller;

public class Main extends Application {
    private BorderPane borderPane;
    private Stage mainStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        mainStage = primaryStage;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/oyna.fxml"));
         borderPane = loader.load();


        mainStage.setTitle("Hello World");
        mainStage.setScene(new Scene(borderPane, 950, 650));
        mainStage.setMinHeight(650);
        mainStage.setMinWidth(950);


        Main_Controller controller = loader.getController();
        controller.setBorderPane(borderPane);
        controller.setMainStage(mainStage);
        controller.goToHome();

        mainStage.setMaximized(true);
        mainStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
