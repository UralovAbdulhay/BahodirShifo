package sample.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.jfoenix.controls.*;
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
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import sample.Connections.*;
import sample.modules.Bemor;
import sample.modules.Davolanish;
import sample.modules.address.Address;
import sample.modules.inform.Passport;
import sample.modules.address.Tuman;
import sample.modules.address.Viloyat;
import sample.modules.Vrach;
import sample.modules.inform.Phone;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class AddPatient implements Initializable {

    @FXML
    private JFXButton addBemorBt;
    @FXML
    private JFXTextField nameTextF;
    @FXML
    private JFXTextField surnameTextF;

    @FXML
    private JFXTextField pasportSeria;

    @FXML
    private JFXTextField pasportRaqam;


    @FXML
    private JFXRadioButton erkakRB;
    @FXML
    private JFXRadioButton ayolRB;
    @FXML
    private JFXTextField tel_1TextF;
    @FXML
    private JFXTextField tel_2TextF;
    @FXML
    private JFXComboBox<Viloyat> vilComboBox;
    @FXML
    private JFXComboBox<Tuman> tumComboBox;
    @FXML
    private JFXTextField addressTextF;
    @FXML
    private JFXDatePicker birthDay;
    @FXML
    private JFXDatePicker cameDay;
    @FXML
    private JFXComboBox<Vrach> vrachComboBox;
    @FXML
    private JFXButton okButton;
    @FXML
    private JFXButton cancelButton;

    private ObservableList<Vrach> recommendVrachList = FXCollections.observableArrayList();
    private ObservableList<Viloyat> viloyatList = FXCollections.observableArrayList();
    private ObservableList<Tuman> tumanList = FXCollections.observableArrayList();

    private Davolanish davolanish;
    private ResourceBundle bundle;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bundle = resources;
        ToggleGroup toggleGroup = new ToggleGroup();
        erkakRB.setToggleGroup(toggleGroup);
        ayolRB.setToggleGroup(toggleGroup);

        okButton.setDisable(true);

        setValueFactory();

        viloyatList = new ViloyatConnection().getViloyatFromSql();
        vilComboBox.setItems(viloyatList);

        cameDay.setValue(LocalDate.now());

        recommendVrachList = new VrachConnection().getVrachFromSql();
        vrachComboBox.setItems(recommendVrachList);


        vrachComboBox.getEditor().setFont(new Font(16));
        vrachComboBox.getEditor().setEditable(false);
        cameDay.getEditor().setFont(new Font(16));
        birthDay.getEditor().setFont(new Font(16));
        vilComboBox.getEditor().setFont(new Font(16));
        vilComboBox.getEditor().setEditable(false);
        tumComboBox.getEditor().setFont(new Font(16));
        tumComboBox.getEditor().setEditable(false);



        Pattern integerP = Pattern.compile("[0-9]{0,7}");
        Pattern stringP = Pattern.compile("[A-Z]{0,2}");

        Pattern phonePattern = Pattern.compile("^(\\d{3}\\\\-?){2}\\d{4}$");

        UnaryOperator<TextFormatter.Change> filterI = c -> {
            if (integerP.matcher(c.getControlNewText()).matches()) {
                return c;
            } else {
                return null;
            }
        };

        UnaryOperator<TextFormatter.Change> filterS = c -> {
            if (stringP.matcher(c.getControlNewText()).matches()) {
                return c;
            } else {
                return null;
            }
        };

        UnaryOperator<TextFormatter.Change> filterT = c -> {
            if (phonePattern.matcher(c.getControlNewText()).matches()) {
                return c;
            } else {
                return null;
            }
        };

        pasportRaqam.setTextFormatter(new TextFormatter<Integer>(filterI));

        pasportSeria.setTextFormatter(new TextFormatter<String>(filterS));


//        MaskFormatter maskFormatter = new MaskFormatter(pasportRaqam);
//        maskFormatter.setMask(MaskFormatter.PASSPORT_RAQAM);
//
//        MaskFormatter maskFormatter1 = new MaskFormatter(tel_1TextF);
//        maskFormatter1.setMask(MaskFormatter.TEL_7DIG);
//
//        MaskFormatter maskFormatter2 = new MaskFormatter(tel_2TextF);
//        maskFormatter2.setMask(MaskFormatter.TEL_9DIG);

        tel_2TextF.clear();

    }

    private void setValueFactory() {

        vilComboBox.setCellFactory(new Callback<ListView<Viloyat>, ListCell<Viloyat>>() {
            @Override
            public ListCell<Viloyat> call(ListView<Viloyat> l) {
                return new ListCell<Viloyat>() {
                    @Override
                    protected void updateItem(Viloyat item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setGraphic(null);
                        } else {
                            setText(item.getViloyatName());
                        }
                    }
                };
            }
        });

        tumComboBox.setCellFactory(new Callback<ListView<Tuman>, ListCell<Tuman>>() {
            @Override
            public ListCell<Tuman> call(ListView<Tuman> l) {
                return new ListCell<Tuman>() {
                    @Override
                    protected void updateItem(Tuman item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setGraphic(null);
                        } else {
                            setText(item.getName());
                        }
                    }
                };
            }
        });


        vrachComboBox.setCellFactory(new Callback<ListView<Vrach>, ListCell<Vrach>>() {
            @Override
            public ListCell<Vrach> call(ListView<Vrach> l) {
                return new ListCell<Vrach>() {
                    @Override
                    protected void updateItem(Vrach item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setGraphic(null);
                        } else {
                            setText(item.toString());
                        }
                    }
                };
            }
        });


    }


    @FXML
    private void setTumanList() {

        if (vilComboBox.getValue() != null) {
            tumanList = viloyatList.get(vilComboBox.getSelectionModel().getSelectedIndex()).getTumanlar();
            tumComboBox.setItems(tumanList);
        }
    }


    @FXML
    private void registerPatient() {

        if (davolanish.getBemor() == null) {

            davolanish.setRecommendVrach(recommendVrachList.get(vrachComboBox.getSelectionModel().getSelectedIndex()));

            davolanish.setBemor(new Bemor(
                            -1,
                            nameTextF.getText().trim(),
                            surnameTextF.getText().trim(),
                            erkakRB.isSelected(),
                            new Phone(-1, tel_1TextF.getText().trim()),
                            birthDay.getValue(),
                            new Address(-1, addressTextF.getText().trim(),
                                    viloyatList.get(vilComboBox.getSelectionModel().getSelectedIndex()),
                                    tumanList.get(tumComboBox.getSelectionModel().getSelectedIndex())
                            )
                    )
            );

            davolanish.getBemor().setComeDate(cameDay.getValue());


            if (passportIsInvalid()) {
                davolanish.getBemor().setPassport(new Passport(-1, pasportSeria.getText().trim(),
                        Integer.parseInt(pasportRaqam.getText().trim())));
            }

            if (tel_2TextF != null)
                if (tel_2TextF.getText() != null)
                    if (!tel_2TextF.getText().isEmpty()) {
                        davolanish.getBemor().setPhone2(new Phone(-1, tel_2TextF.getText().trim()));
                    }

        } else {
            davolanish.getBemor().setName(nameTextF.getText().trim());
            davolanish.getBemor().setSurname(surnameTextF.getText().trim());
            davolanish.getBemor().setGender(erkakRB.isSelected());
            davolanish.getBemor().setBirthDate(birthDay.getValue());

            davolanish.getBemor().setAddress(
                    new Address(davolanish.getBemor().getAddress().getId(), addressTextF.getText().trim(),
                    viloyatList.get(vilComboBox.getSelectionModel().getSelectedIndex()),
                    tumanList.get(tumComboBox.getSelectionModel().getSelectedIndex())
            ));



            if (passportIsInvalid()) {

                if (davolanish.getBemor().getPassport()!=null) {
                    davolanish.getBemor().getPassport().setSeria(pasportSeria.getText().trim());
                    davolanish.getBemor().getPassport().setRaqam(Integer.parseInt(pasportRaqam.getText().trim()));
                }else {
                    davolanish.getBemor().setPassport(new Passport(-1, pasportSeria.getText().trim(),
                            Integer.parseInt(pasportRaqam.getText().trim())));
                }
            }

            if (tel_2TextF != null)
                if (tel_2TextF.getText() != null)
                    if (!tel_2TextF.getText().isEmpty()) {
                        davolanish.getBemor().getPhone2().setNumber(tel_2TextF.getText().trim());
                    }

            davolanish.setRecommendVrach(recommendVrachList.get(vrachComboBox.getSelectionModel().getSelectedIndex()));

        }
        this.tel_2TextF.getScene().getWindow().hide();
    }

    private boolean passportIsInvalid() {
        return (pasportSeria.getText().trim().length() == 2) && (pasportRaqam.getText().trim().length() == 7);
    }

    @FXML
    private void cancelPatient() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Bemorni o'chirishni tasdiqlaysizmi?");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            this.davolanish.setBemor(null);
            this.cancelButton.getScene().getWindow().hide();
        }
    }


    private void editPatient() {

        nameTextF.setText(davolanish.getBemor().getName());
        surnameTextF.setText(davolanish.getBemor().getSurname());
        if (davolanish.getBemor().getGender()) {
            erkakRB.setSelected(true);
        } else {
            ayolRB.setSelected(true);
        }

        tel_1TextF.setText(davolanish.getBemor().getPhone1().getNumber());

        if (davolanish.getBemor().getPhone2() != null) {
            tel_2TextF.setText(davolanish.getBemor().getPhone2().getNumber());
        }


        addressTextF.setText(davolanish.getBemor().getAddress().getUy());
        birthDay.setValue(davolanish.getBemor().getBirthDate());
        cameDay.setValue(davolanish.getBemor().getComeDate());
        cameDay.setDisable(true);

        if (davolanish.getBemor().getPassport() != null) {
            pasportSeria.setText(davolanish.getBemor().getPassport().getSeria());
            pasportRaqam.setText(davolanish.getBemor().getPassport().getRaqam() + "");
        }


        int d = -1;
        for (int i = 0; i < recommendVrachList.size(); i++) {
            if (recommendVrachList.get(i).toString().equals(davolanish.getRecommendVrach().toString())) {
                d = i;
            }
        }
        vrachComboBox.getSelectionModel().select(d);


        int v = -1;
        for (int i = 0; i < viloyatList.size(); i++) {
            if (viloyatList.get(i).toString().equals(davolanish.getBemor()
                    .getAddress().getViloyat().toString())) {
                v = i;
            }
        }

        vilComboBox.getSelectionModel().select(v);

        setTumanList();

        tumComboBox.setItems(tumanList);
        int t = -1;
        for (int i = 0; i < tumanList.size(); i++) {
            if (tumanList.get(i).toString().equals(davolanish.getBemor()
                    .getAddress().getTuman().toString())) {
                t = i;
            }
        }
        tumComboBox.getSelectionModel().select(t);

        controlButton();
    }


    @FXML
    private void controlButton() {

        boolean tuliqmi = !(nameTextF.getText().trim().isEmpty())
                && !(surnameTextF.getText().trim().isEmpty())
                && !(addressTextF.getText().trim().isEmpty())
                && !(tel_1TextF.getText().trim().isEmpty())
                && (birthDay.getValue() != null)
                && (tumComboBox.getValue() != null)
                && (vilComboBox.getValue() != null)
                && (vrachComboBox.getValue() != null);

        okButton.setDisable(!tuliqmi);

    }

    public void setDavolanish(Davolanish davolanish) {

        this.davolanish = davolanish;

        if (this.davolanish.getBemor() != null) {
            editPatient();
        }
    }

    public Davolanish getDavolanish() {
        return davolanish;
    }


    @FXML
    private void addBemorFromBase(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/bemorView.fxml"));
        loader.setResources(bundle);

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
        stage.initOwner(addBemorBt.getScene().getWindow());


        BemorViewController controller = loader.getController();

        controller.setDavolanish(davolanish);

        stage.showAndWait();

        if (davolanish.getBemor() != null) {
            editPatient();
            System.out.println(davolanish.getBemor().toString());
        } else {
            System.out.println(davolanish.getBemor());
        }


    }


    private void jsonParse() {

        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        File file;
        Scanner k = null;

        file = new File("viloyatlar.json");
        //1

        try {
            k = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String json = "";

        while (k.hasNextLine()) {
            json += k.nextLine();
        }

        Object obj = parser.parse(json);
        JsonArray array = (JsonArray) parser.parse(String.valueOf(obj));

        for (int i = 0; i < array.size(); i++) {
            Viloyat viloyat = gson.fromJson(array.get(i), Viloyat.class);
            viloyatList.add(viloyat);
        }
    }

}
