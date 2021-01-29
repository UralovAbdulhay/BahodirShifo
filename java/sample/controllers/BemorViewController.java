package sample.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import sample.Connections.BemorConnection;
import sample.modules.Bemor;
import sample.modules.Davolanish;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class BemorViewController implements Initializable {

    @FXML
    private JFXTextField qidirish;

    @FXML
    private JFXButton clsBt;

    @FXML
    private TableView<Bemor> bemorTable;

    @FXML
    private TableColumn<Bemor, String> ismCol;

    @FXML
    private TableColumn<Bemor, String> famCol;

    @FXML
    private TableColumn<Bemor, String> sanaCol;

    private Davolanish davolanish;
    private ResourceBundle bundle;
    private ObservableList<Bemor> bemors;


    @FXML
    void clear(ActionEvent event) {
        qidirish.clear();
    }

    @FXML
    void qidir(KeyEvent event) {

        String natija = qidirish.getText().trim().toLowerCase();

        if (natija.isEmpty()) {
            bemorTable.setItems(bemors);
        } else {
            bemorTable.setItems(
                    FXCollections.observableArrayList(
                            bemors.stream()
                                    .filter(e -> e.toString().trim().toLowerCase().contains(natija))
                                    .collect(Collectors.toList())
                    )
            );
        }
        bemorTable.refresh();

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bundle = resources;
        bemors = FXCollections.observableArrayList(new BemorConnection().getBemorListFromSql());


        ismCol.setCellValueFactory(
                e -> new SimpleStringProperty(e.getValue().getName())
        );


        famCol.setCellValueFactory(
                e -> new SimpleStringProperty(e.getValue().getSurname())
        );



        sanaCol.setCellValueFactory(
                e -> new SimpleStringProperty(e.getValue().getBirthDateStr())
        );


        bemorTable.setItems(bemors);

        bemorTable.setRowFactory(tv -> {
            TableRow<Bemor> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    davolanish.setBemor(row.getItem());
                    bemorTable.getScene().getWindow().hide();
                }
            });
            return row;
        });

    }


    private <S, T> TableColumn<S, T> creatTabCol(String title) {
        TableColumn<S, T> newColumn = new TableColumn<S, T>(title);
        newColumn.setStyle("-fx-alignment: CENTER");
        return newColumn;
    }

    public void setDavolanish(Davolanish davolanish) {
        this.davolanish = davolanish;
    }
}
