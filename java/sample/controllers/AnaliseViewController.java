package sample.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Connections.AnaliseConnection;
import sample.Connections.KonsultatsiyaConnection;
import sample.Connections.TekshirishConnection;
import sample.modules.*;
import sample.modules.davolanish.Analise;
import sample.modules.davolanish.Konsultatsiya;
import sample.modules.davolanish.Tekshirish;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AnaliseViewController implements Initializable {


    @FXML
    private JFXButton analiseClearB;

    @FXML
    private JFXButton tekshirishClearB;

    @FXML
    private JFXButton konsultatsiyaClearB;

    private Stage mainStage;
    private BorderPane mainBorderPane;


    // qidirish TextField lari
    @FXML
    private JFXTextField analiseSearch;
    @FXML
    private JFXTextField tekshiruvSearch;
    @FXML
    private JFXTextField konsultatSearch;

    // label lar
    @FXML
    private Label fioLab;
    @FXML
    private Label summaLab;

    // Buttonlar
    @FXML
    private JFXButton clearButton;
    @FXML
    private JFXButton okButton;
    @FXML
    private JFXButton editPatientButton;
    @FXML
    private JFXButton addPatientButton;


    // 3 ta TableView
    @FXML
    private TableView<Analise> analiseTableV;
    @FXML
    private TableView<Tekshirish> tekshirishTableV;
    @FXML
    private TableView<Konsultatsiya> konsultatsiyaTableV;
    @FXML
    private TableView<Xizmat> xizmatTableView;


    //order TableColumn
    @FXML
    private TableColumn<Xizmat, String> order_cost;
    @FXML
    private TableColumn<Xizmat, String> order_name;
    @FXML
    private TableColumn<Xizmat, Button> order_button;
    @FXML
    private TableColumn<Xizmat, Integer> order_num;


    // analiseList TableColumn
    @FXML
    private TableColumn<Analise, String> analise_name;
    @FXML
    private TableColumn<Analise, Object> analise_cost;
    @FXML
    private TableColumn<Analise, Button> analise_button;


    //tekshirishList TableColumn
    @FXML
    private TableColumn<Tekshirish, String> tekshirish_name;
    @FXML
    private TableColumn<Tekshirish, String> tekshirish_cost;
    @FXML
    private TableColumn<Tekshirish, Button> tekshirish_button;


    // konsultatsiyaList TableColumn
    @FXML
    private TableColumn<Konsultatsiya, String> konsultatsiya_name;
    @FXML
    private TableColumn<Konsultatsiya, String> konsultatsiya_cost;
    @FXML
    private TableColumn<Konsultatsiya, Button> konsultatsiya_button;


    private Davolanish davolanish;


    private TulovController tulovController;
    private AddPatient addPatient;

    private ResourceBundle bundle;


    private ObservableList<Analise> analises;
    private ObservableList<Tekshirish> tekshirishes;
    private ObservableList<Konsultatsiya> konsultatsiyas;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.bundle = resources;

        davolanish = new Davolanish();


        clearButton.setDisable(true);
        okButton.setDisable(true);
        editPatientButton.setDisable(true);


        analises = FXCollections.observableArrayList(new AnaliseConnection().getAnaliseListFromSql());
        analiseTableV.getItems().addAll(analises);
        analiseTableV.refresh();

        analiseTableV.setRowFactory(tv -> {
            TableRow<Analise> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty()) && (!row.getItem().getXizmatBt().isDisable())) {
                    addAnalise(row.getItem());
                }
            });
            return row;
        });


        tekshirishes = FXCollections.observableArrayList(new TekshirishConnection().getTekshirishFromSql());
        tekshirishTableV.getItems().addAll(tekshirishes);
        tekshirishTableV.refresh();

        tekshirishTableV.setRowFactory(tv -> {
            TableRow<Tekshirish> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty()) && (!row.getItem().getXizmatBt().isDisable())) {
                    addTekshirish(row.getItem());
                }
            });
            return row;
        });


        konsultatsiyas = FXCollections.observableArrayList(new KonsultatsiyaConnection().getKonsultatsiyaFromSql());
        konsultatsiyaTableV.getItems().addAll(konsultatsiyas);
        konsultatsiyaTableV.refresh();

        konsultatsiyaTableV.setRowFactory(tv -> {
            TableRow<Konsultatsiya> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty()) && (!row.getItem().getXizmatBt().isDisable())) {
                    addKonsultatsiya(row.getItem());
                }
            });
            return row;
        });

        xizmatTableView.setRowFactory(tv -> {
            TableRow<Xizmat> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty()) && (!row.getItem().getXizmatBt().isDisable())) {
                    cancelXizmat(row.getItem());
                }
            });
            return row;
        });

        setOnActionToBts();


        setKonsultatsiyaTableViewValueFactory();
        setAnaliseTableViewValueFactory();
        setTekshirishTableViewValueFactory();

        setPaketListTableViewValueFactory();
        orderTableViewRefresh();

        analiseSearch.textProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                analiseSearchOnAction();
            }
        });

        konsultatSearch.textProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                konsultatsiyaSearchOnAction();
            }
        });

        tekshiruvSearch.textProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                tekshirishSearchOnAction();
            }
        });

        clearOrderList();


    }

    private void setOnActionToBts() {
        analises.forEach(e -> e.getXizmatBt().setOnAction(this::addAnaliseAction));
        tekshirishes.forEach(e -> e.getXizmatBt().setOnAction(this::addTekshirishAction));
        konsultatsiyas.forEach(e -> e.getXizmatBt().setOnAction(this::addKonsultatsiyaAction));

    }


    public void setMainBorderPane(BorderPane mainBorderPane) {
        this.mainBorderPane = mainBorderPane;
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    private void setAnaliseTableViewValueFactory() {


        setPrefSize(analise_name, 380);
        setPrefSize(analise_cost, 110);
        setPrefSize(analise_button, 80);

        analise_name.setCellValueFactory(
                cellValue -> new SimpleStringProperty(cellValue.getValue().getName())
        );
        analise_cost.setCellValueFactory(
                cellValue -> new SimpleObjectProperty<>(new Button(cellValue.getValue().getNarxStr()+""))
        );
        analise_button.setCellValueFactory(
                cellValue -> new SimpleObjectProperty<>(cellValue.getValue().getXizmatBt())
        );
    }



    private void setTekshirishTableViewValueFactory() {

        setPrefSize(tekshirish_name, 380);
        setPrefSize(tekshirish_cost, 110);
        setPrefSize(tekshirish_button, 80);


        tekshirish_name.setCellValueFactory(
                cellValue -> new SimpleStringProperty(cellValue.getValue().getName())
        );
        tekshirish_cost.setCellValueFactory(
                cellValue -> new SimpleStringProperty(cellValue.getValue().getNarxStr())
        );
        tekshirish_button.setCellValueFactory(
                cellValue -> new SimpleObjectProperty<>(cellValue.getValue().getXizmatBt())
        );
    }

    private void setKonsultatsiyaTableViewValueFactory() {

        setPrefSize(konsultatsiya_name, 380);
        setPrefSize(konsultatsiya_cost, 110);
        setPrefSize(konsultatsiya_button, 80);

        konsultatsiya_name.setCellValueFactory(
                cellValue -> new SimpleStringProperty(cellValue.getValue().getName())
        );
        konsultatsiya_cost.setCellValueFactory(
                cellValue -> new SimpleStringProperty(cellValue.getValue().getNarxStr())
        );
        konsultatsiya_button.setCellValueFactory(
                cellValue -> new SimpleObjectProperty<>(cellValue.getValue().getXizmatBt())
        );
    }

    private void setPrefSize(TableColumn column, double d) {
        column.setPrefWidth(d);
        column.setMinWidth(d);
        column.setMaxWidth(d);
        column.setStyle("-fx-alignment: CENTER");
    }

    private void setPaketListTableViewValueFactory() {

        setPrefSize(order_name, 300);
        setPrefSize(order_cost, 110);
        setPrefSize(order_button, 80);
        setPrefSize(order_num, 40);


        order_name.setCellValueFactory(
                cellValue -> new SimpleStringProperty(cellValue.getValue().getName())
        );
        order_cost.setCellValueFactory(
                cellValue -> new SimpleObjectProperty<>(cellValue.getValue().getNarxStr())
        );
        order_button.setCellValueFactory(
                cellValue -> new SimpleObjectProperty<>(cellValue.getValue().getXizmatBt())
        );

        order_num.setCellValueFactory(
                num -> new SimpleObjectProperty<>(num.getValue().getId())
        );
    }


    @FXML
    public void addPatient() {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/bemor_register.fxml"));

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


        addPatient = loader.getController();

        addPatient.setDavolanish(davolanish);

        stage.showAndWait();
        controlButtons();
        setBemor();
    }

    @FXML
    private void editPatient() {
        addPatient();
    }


    private void addAnaliseAction(ActionEvent event) {

        for (Analise analise : analises) {
            if (analise.getXizmatBt().isArmed() && paketListIsContain(analise)) {
                addAnalise(analise);
            }
        }
    }

    private void addAnalise(Analise analise) {

        Analise analise1 = new Analise(analise);
        analise1.setXizmatBt(new JFXButton());
        analise.getXizmatBt().setDisable(true);
        davolanish.addAnaliseLists(analise1);
        setOnActionXizmatListBn();

        orderTableViewRefresh();
        controlButtons();
        analiseTableV.refresh();
        orderTableViewRefresh();
    }


    private void addTekshirishAction(ActionEvent event) {
        for (Tekshirish tekshirish : tekshirishes) {
            if (tekshirish.getXizmatBt().isArmed() && paketListIsContain(tekshirish)) {
                addTekshirish(tekshirish);
            }
        }

    }


    private void addTekshirish(Tekshirish tekshirish) {

        Tekshirish tekshirish1 = new Tekshirish(tekshirish);
        tekshirish1.setXizmatBt(new JFXButton());
        davolanish.addAnaliseLists(tekshirish1);
        tekshirish.getXizmatBt().setDisable(true);
        setOnActionXizmatListBn();

        orderTableViewRefresh();
        controlButtons();
        tekshirishTableV.refresh();
        orderTableViewRefresh();
    }


    private void addKonsultatsiyaAction(ActionEvent event) {
        for (Konsultatsiya konsultatsiya : konsultatsiyas) {
            if (konsultatsiya.getXizmatBt().isArmed() && paketListIsContain(konsultatsiya)) {
                addKonsultatsiya(konsultatsiya);
            }
        }
    }

    private void addKonsultatsiya(Konsultatsiya konsultatsiya) {

        Konsultatsiya konsultatsiya1 = new Konsultatsiya(konsultatsiya);
        konsultatsiya1.setXizmatBt(new JFXButton());
        davolanish.addAnaliseLists(konsultatsiya1);
        konsultatsiya.getXizmatBt().setDisable(true);
        setOnActionXizmatListBn();


        orderTableViewRefresh();
        controlButtons();
        konsultatsiyaTableV.refresh();
        orderTableViewRefresh();
    }

    private boolean paketListIsContain(Xizmat xizmat1) {
        return davolanish.getXizmats().stream().noneMatch(new Predicate<Xizmat>() {
            @Override
            public boolean test(Xizmat xizmat) {
                return xizmat.toString().equals(xizmat1.toString());
            }
        });
    }


    void controlButtons() {

        clearButton.setDisable(xizmatTableView.getItems().isEmpty());
        okButton.setDisable(xizmatTableView.getItems().isEmpty() || this.davolanish.getBemor() == null);
        editPatientButton.setDisable(this.davolanish.getBemor() == null);
        addPatientButton.setDisable(this.davolanish.getBemor() != null);
    }


    private void setOnActionXizmatListBn() {
        for (Xizmat xizmat : davolanish.getXizmats()) {
            xizmat.getXizmatBt().setOnAction(event -> cancelXizmatAction());
        }
    }

    private void orderTableViewRefresh() {
        xizmatTableView.setItems(davolanish.getXizmats());
        xizmatTableView.refresh();
        summhisobla();
    }

    private void cancelXizmatAction() {

        for (int i = 0; i < davolanish.getXizmats().size(); i++) {
            if (davolanish.getXizmats().get(i).getXizmatBt().isArmed()) {
                cancelXizmat(davolanish.getXizmats().get(i));
            }
        }
    }


    private void cancelXizmat(Xizmat xizmat) {

        cancelHelper(xizmat);
        davolanish.getXizmats().remove(xizmat);
        orderTableViewRefresh();

        davolanish.paketListSort();
        controlButtons();
        setOnActionToBts();
        orderTableViewRefresh();
    }


    private void cancelHelper(Xizmat xizmat) {

        switch (xizmat.getXizmatType().toString()) {
            case "Analise":
                for (Analise analise : analises) {
                    if (analise.toString().equals(xizmat.toString())) {
                        analise.getXizmatBt().setDisable(false);
                    }
                }
                break;
            case "Konsultatsiya":
                for (Konsultatsiya konsultatsiya : konsultatsiyas) {
                    if (konsultatsiya.toString().equals(xizmat.toString())) {
                        konsultatsiya.getXizmatBt().setDisable(false);
                    }
                }
                break;
            case "Tekshirish":
                for (Tekshirish tekshirish : tekshirishes) {
                    if (tekshirish.toString().equals(xizmat.toString())) {
                        tekshirish.getXizmatBt().setDisable(false);
                    }
                }
                break;
        }
        orderTableViewRefresh();
        summhisobla();
    }

    public void clearOrderList() {

        for (Xizmat xizmat : davolanish.getXizmats()) {
            cancelHelper(xizmat);
        }

        davolanish.getXizmats().clear();
        summhisobla();
        controlButtons();
        setOnActionToBts();
    }


    private void summhisobla() {
        davolanish.getPayment().setPaymentAmount(davolanish.getXizmats().stream().mapToDouble(Xizmat::getCost).sum());
        summaLab.setText(decimalFormat(davolanish.getPayment().getPaymentAmount()));
        System.out.println("summhisobla = " + davolanish.getPayment().getSale());
        System.out.println("summhisobla = " + davolanish.getPayment().getPaymentAmount());
    }

    private void setBemor() {

        if (davolanish.getBemor() != null) {
            fioLab.setText(davolanish.getBemor().getSurname() + " " + davolanish.getBemor().getName());
        } else {
            fioLab.setText("--  --");
        }
        controlButtons();
    }

    @FXML
    private void okNext() {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/tulov.fxml"));

        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage stage = new Stage();
        stage.setTitle("To'lov");
        assert parent != null;
        stage.setScene(new Scene(parent));
        stage.setResizable(false);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(mainStage);


        tulovController = loader.getController();
        tulovController.setStage(stage);
        tulovController.setAnaliseViewController(this);
        tulovController.setDavolanish(davolanish);

        stage.showAndWait();
        System.out.println("stage.showAndWait()");

        setBemor();
    }

    @FXML
    public void analiseSearchFieldClear() {
        analiseSearch.clear();
    }

    @FXML
    public void konsultatSearchFieldClear() {
        konsultatSearch.clear();
    }

    @FXML
    public void tekshirishSearchFieldClear() {
        tekshiruvSearch.clear();
    }

    private void konsultatsiyaSearchOnAction() {

        String natija = konsultatSearch.getText().trim().toLowerCase();

        if (natija.isEmpty()) {
            konsultatsiyaTableV.setItems(konsultatsiyas);
        } else {
            konsultatsiyaTableV.setItems(
                    FXCollections.observableArrayList(
                            konsultatsiyas
                                    .stream()
                                    .filter(e -> e.getName().toLowerCase().contains(natija))
                                    .collect(Collectors.toList())
                    )
            );
        }
        konsultatsiyaTableV.refresh();
    }

    private void tekshirishSearchOnAction() {

        String natija = tekshiruvSearch.getText().trim().toLowerCase();

        if (natija.isEmpty()) {
            tekshirishTableV.setItems(tekshirishes);
        } else {
            tekshirishTableV.setItems(
                    FXCollections.observableArrayList(
                            tekshirishes.stream()
                                    .filter(e -> e.getName().toLowerCase().contains(natija))
                                    .collect(Collectors.toList())
                    )
            );
        }
        tekshirishTableV.refresh();
    }

    private void analiseSearchOnAction() {

        String natija = analiseSearch.getText().trim().toLowerCase();

        if (natija.isEmpty()) {
            analiseTableV.setItems(analises);
        } else {
            analiseTableV.setItems(
                    FXCollections.observableArrayList(
                            analises.stream()
                                    .filter(e -> e.getName().toLowerCase().contains(natija))
                                    .collect(Collectors.toList())
                    )
            );
        }
        analiseTableV.refresh();
    }

    private String decimalFormat(double d) {
        return new DecimalFormat("#,##0.0").format(d);
    }

}






