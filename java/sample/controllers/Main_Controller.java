package sample.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Main_Controller {
    private BorderPane borderPane;
    private Stage mainStage;
    private int switchWindow;
    private final int home = 1;
    private final int analise = 2;
    private final int kassa = 3;
    private final int base = 4;
    private final int profil = 5;
    private final int settings = 6;

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    public void setBorderPane(BorderPane borderPane) {
        this.borderPane = borderPane;
    }

/*
    1-home
    2-analise
    3-kassa
    4-base
    5-profil
    6-settings
*/


    @FXML
    public void goToHome() throws IOException {
        if (switchWindow != home) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/home.fxml"));
            VBox vBox = loader.load();
            borderPane.setCenter(vBox);

            switchWindow = home;
        }

    }

    @FXML
    public void goToAnalise() throws IOException {

        if (switchWindow != analise) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/analise.fxml"));
            HBox hBox = loader.load();

            AnaliseViewController analiseController = loader.getController();
            analiseController.setMainStage(mainStage);
            analiseController.setMainBorderPane(borderPane);
            borderPane.setCenter(hBox);

            switchWindow = analise;
        }
    }

    @FXML
    public void goToKassa() {
        if (switchWindow != kassa) {

            switchWindow = kassa;
        }
    }

    @FXML
    public void goToBase() {
        if (switchWindow != base) {

            switchWindow = base;
        }
    }

    @FXML
    public void goToProfil() {
        if (switchWindow != profil) {

            switchWindow = profil;
        }
    }

    @FXML
    private void goToSettings() {
        if (switchWindow != settings) {

            switchWindow = settings;
        }
    }


    @FXML
    private void newBemor(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/newBemor_register.fxml"));

        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        assert parent != null;
        stage.setScene(new Scene(parent));
        stage.setResizable(false);

        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(mainStage);

        stage.show();

    }



    @FXML
    private void exit(ActionEvent event) {
        mainStage.close();
    }
}
