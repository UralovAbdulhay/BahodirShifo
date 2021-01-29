package sample.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;

import java.net.URL;
import java.util.ResourceBundle;

public class BaseController implements Initializable {

    @FXML
    private JFXTabPane parentTabPane;

    @FXML
    private Tab bemorTab;

    @FXML
    private JFXTextField bemorQidir;

    @FXML
    private JFXButton bemorQidirCls;

    @FXML
    private JFXButton delPrBt;

    @FXML
    private JFXButton bemorAddBt;

    @FXML
    private ToggleButton bemorDelBt;

    @FXML
    private TableView<?> bemorTable;

    @FXML
    private Tab analiseTab;

    @FXML
    private JFXTextField analiseQidir;

    @FXML
    private JFXButton analiseQidirCls;

    @FXML
    private JFXButton delPrBt1;

    @FXML
    private JFXButton analiseAddBt;

    @FXML
    private ToggleButton analiseDelBt;

    @FXML
    private TableView<?> analiseTable;

    @FXML
    private Tab tekshirishTab;

    @FXML
    private JFXTextField tekshirishQidir;

    @FXML
    private JFXButton tekshirishQidirCls;

    @FXML
    private JFXButton delPrBt2;

    @FXML
    private JFXButton tekshirishAddBt;

    @FXML
    private ToggleButton tekshirishDelBt;

    @FXML
    private TableView<?> tekshirishTable;

    @FXML
    private Tab konsultatsiyaTab;

    @FXML
    private JFXTextField konsultatsiyaQidir;

    @FXML
    private JFXButton konsultatsiyaQidirCls;

    @FXML
    private JFXButton delPrBt11;

    @FXML
    private JFXButton konsultatsiyaAddBt;

    @FXML
    private ToggleButton konsultatsiyaDelBt;

    @FXML
    private TableView<?> konsultatsiyaTable;

    @FXML
    private Tab qabulTab;

    @FXML
    private JFXTextField qabulQidir;

    @FXML
    private JFXButton qabulQidirCls;

    @FXML
    private JFXButton delPrBt3;

    @FXML
    private ToggleButton qabulDelBt;

    @FXML
    private TableView<?> qabulTable;

    @FXML
    private Tab vrachTab;

    @FXML
    private JFXTextField vrachQidir;

    @FXML
    private JFXButton vtachQidirCls;

    @FXML
    private JFXButton delPrBt12;

    @FXML
    private JFXButton vrachAddBt;

    @FXML
    private ToggleButton vrachDelBt;

    @FXML
    private TableView<?> vrachTable;

    @FXML
    private Tab kasbTab;

    @FXML
    private JFXTextField kasbQidir;

    @FXML
    private JFXButton kasbQidirCls;

    @FXML
    private JFXButton delPrBt21;

    @FXML
    private JFXButton kasbAddBt;

    @FXML
    private ToggleButton kasbDelBt;

    @FXML
    private TableView<?> kasbTable;

    @FXML
    void SelectionChanged(ActionEvent event) {

    }

    @FXML
    void analiseAddAction(ActionEvent event) {

    }

    @FXML
    void analiseDelAction(ActionEvent event) {

    }

    @FXML
    void bemorAddAction(ActionEvent event) {

    }

    @FXML
    void bemorDelAction(ActionEvent event) {

    }

    @FXML
    void deletePriseConfirmWithCheckBox(ActionEvent event) {

    }

    @FXML
    void kasbAddAction(ActionEvent event) {

    }

    @FXML
    void kasbDelAction(ActionEvent event) {

    }

    @FXML
    void konsultatsiyaAddAction(ActionEvent event) {

    }

    @FXML
    void konsultatsiyaDelAction(ActionEvent event) {

    }

    @FXML
    void qabulDelAction(ActionEvent event) {

    }

    @FXML
    void tekshirishAddAction(ActionEvent event) {

    }

    @FXML
    void tekshirishDelAction(ActionEvent event) {

    }

    @FXML
    void tozalaQidir(ActionEvent event) {

    }

    @FXML
    void vrachAddAction(ActionEvent event) {

    }

    @FXML
    void vrachDelAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


}
