package sample.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;
import sample.Connections.BemorConnection;
import sample.Connections.ViloyatConnection;
import sample.modules.Bemor;
import sample.modules.address.Address;
import sample.modules.address.Tuman;
import sample.modules.address.Viloyat;
import sample.modules.inform.Passport;
import sample.modules.inform.Phone;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class NewBemorRegister implements Initializable {

    @FXML
    private JFXTextField nameTextF;

    @FXML
    private JFXTextField surnameTextF;

    @FXML
    private JFXRadioButton erkakRB;

    @FXML
    private ToggleGroup togGr;

    @FXML
    private JFXRadioButton ayolRB;

    @FXML
    private JFXDatePicker birthDay;

    @FXML
    private JFXTextField pasportSeria;

    @FXML
    private JFXTextField pasportRaqam;

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
    private JFXDatePicker cameDay;

    @FXML
    private JFXButton cancelButton;

    @FXML
    private JFXButton okButton;


    private ObservableList<Viloyat> viloyatList = FXCollections.observableArrayList();
    private ObservableList<Tuman> tumanList = FXCollections.observableArrayList();

    private ResourceBundle bundle;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bundle = resources;

        Pattern integerT = Pattern.compile("[0-9]{0,9}");
        Pattern integerP = Pattern.compile("[0-9]{0,7}");
        Pattern stringP = Pattern.compile("[A-Z]{0,2}");

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


        UnaryOperator<TextFormatter.Change> filterPhone = c -> {
            if (integerT.matcher(c.getControlNewText()).matches()) {
                return c;
            } else {
                return null;
            }
        };


        pasportRaqam.setTextFormatter(new TextFormatter<Integer>(filterI));

        pasportSeria.setTextFormatter(new TextFormatter<String>(filterS));

        tel_1TextF.setTextFormatter(new TextFormatter<Integer>(filterPhone));
        tel_2TextF.setTextFormatter(new TextFormatter<Integer>(filterPhone));

        setValueFactory();

        viloyatList = new ViloyatConnection().getViloyatFromSql();
        vilComboBox.setItems(viloyatList);

        cameDay.setValue(LocalDate.now());

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

    }

    @FXML
    void cancelPatient(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Bemorni o'chirishni tasdiqlaysizmi?");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            this.cancelButton.getScene().getWindow().hide();
        }
    }

    @FXML
    void controlButton(Event event) {

        boolean tuliqmi = !(nameTextF.getText().trim().isEmpty())
                && !(surnameTextF.getText().trim().isEmpty())
                && !(addressTextF.getText().trim().isEmpty())
                && !(tel_1TextF.getText().trim().isEmpty())
                && (birthDay.getValue() != null)
                && (tumComboBox.getValue() != null)
                && (vilComboBox.getValue() != null);

        okButton.setDisable(!tuliqmi);
    }

    @FXML
    void registerPatient(ActionEvent event) {

        Bemor bemor = new Bemor(
                -1,
                nameTextF.getText(),
                surnameTextF.getText(),
                erkakRB.isSelected(),
                new Phone(-1, tel_1TextF.getText().trim()),
                birthDay.getValue(),
                new Address(-1, addressTextF.getText().trim(),
                        viloyatList.get(vilComboBox.getSelectionModel().getSelectedIndex()),
                        tumanList.get(tumComboBox.getSelectionModel().getSelectedIndex())
                )

        );

        bemor.setComeDate(cameDay.getValue());


        if (passportIsInvalid()) {
            bemor.setPassport(new Passport(-1, pasportSeria.getText().trim(),
                    Integer.parseInt(pasportRaqam.getText().trim())));
        }

        if (tel_2TextF != null)
            if (tel_2TextF.getText() != null)
                if (!tel_2TextF.getText().isEmpty()) {
                    bemor.setPhone2(new Phone(-1, tel_2TextF.getText().trim()));
                }

        new BemorConnection().insertToBemor(bemor);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.showAndWait();

        this.tel_2TextF.getScene().getWindow().hide();

    }

    @FXML
    void setTumanList(Event event) {
        if (vilComboBox.getValue() != null) {
            tumanList = viloyatList.get(vilComboBox.getSelectionModel().getSelectedIndex()).getTumanlar();
            tumComboBox.setItems(tumanList);
        }
    }

    private boolean passportIsInvalid() {
        return (pasportSeria.getText().trim().length() == 2) && (pasportRaqam.getText().trim().length() == 7);
    }

}
