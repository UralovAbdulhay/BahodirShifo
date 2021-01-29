package sample.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import sample.Connections.DavolanishConnection;
import sample.Connections.QabulConnection;
import sample.modules.Davolanish;
import sample.modules.Payment;
import sample.modules.Vrach;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class TulovController implements Initializable {

    @FXML
    private JFXTextField saleTF;
    @FXML
    private JFXCheckBox saleCheck;
    @FXML
    private JFXTextField summaTF;
    @FXML
    private ToggleGroup bigGroup;
    @FXML
    private ToggleGroup littleGroup;
    @FXML
    private JFXRadioButton naqtRB;
    @FXML
    private JFXRadioButton plastikRB;
    @FXML
    private JFXRadioButton terminalRB;
    @FXML
    private JFXRadioButton clikRB;
    @FXML
    private JFXCheckBox checkedBox;
    @FXML
    private JFXButton cancelButton;
    @FXML
    private JFXButton okButton;

    private Stage stage;
    private AnaliseViewController analiseViewController;

    private Davolanish davolanish;

    private ResourceBundle bundle;

    private double fakeSumma;
    private double fakeSale;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bundle = resources;

        radioSelected();
        summaTF.setFocusTraversable(false);

        Pattern integerP = Pattern.compile("[0-9]{0,3}");
        Pattern decimalPattern = Pattern.compile("\\d*(\\.\\d{0,4})?");
        Pattern decimalPatternSale = Pattern.compile("\\d{0,3}(\\.\\d{0,4})?");


        UnaryOperator<TextFormatter.Change> filterI = c -> {
            if (decimalPattern.matcher(c.getControlNewText()).matches()) {
                return c;
            } else {
                return null;
            }
        };


        UnaryOperator<TextFormatter.Change> filterSale = c -> {
            if (decimalPatternSale.matcher(c.getControlNewText()).matches()) {
                return c;
            } else {
                return null;
            }
        };
        summaTF.setTextFormatter(new TextFormatter<Integer>(filterI));
        saleTF.setTextFormatter(new TextFormatter<Integer>(filterSale));

    }

    public void radioSelected() {
        checkedBox.setDisable(!naqtRB.isSelected());
        clikRB.setDisable(!plastikRB.isSelected());
        terminalRB.setDisable(!plastikRB.isSelected());
    }


    public void setAnaliseViewController(AnaliseViewController analiseViewController) {
        this.analiseViewController = analiseViewController;
    }

    private void setStringSumma() {
        summaTF.setText(fakeSumma + "");
    }

    public void setDavolanish(Davolanish davolanish) {
        this.davolanish = davolanish;

        fakeSumma = davolanish.getPayment().getPaymentAmount();

        if (this.davolanish != null) {
            setStringSumma();
        }
    }

    @FXML
    void cancelTulov() {
        cancelButton.getScene().getWindow().hide();
    }

    @FXML
    void okTulov() {

        davolanish.getPayment().setPaymentType(naqtRB.isSelected());

        if (!saleCheck.isSelected()) {
            davolanish.getPayment().setPaymentAmount(Double.parseDouble(summaTF.getText()));
        }

        if (naqtRB.isSelected()) {
            davolanish.getPayment().setChecked(checkedBox.isSelected());
        } else if (plastikRB.isSelected()) {
            davolanish.getPayment().setChecked(terminalRB.isSelected());
        }

        setStringSumma();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Summa: " + decimalFormat(Double.parseDouble(summaTF.getText())));
        alert.setHeaderText("Payment");
        alert.setTitle("Payment");
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {

            davolanish.getPayment().setSale(fakeSale);
            System.out.println(davolanish.toString());

            new QabulConnection().insertToQabul(davolanish);

            analiseViewController.clearOrderList();
            davolanish.setBemor(null);

            fakeSale = 0;
            fakeSumma = 0;
            davolanish.setPayment(new Payment());
            davolanish.setRecommendVrach(new Vrach());
            stage.close();

        }
    }

    void setStage(Stage stage) {
        this.stage = stage;
    }


    @FXML
    private void saleCheckAction(ActionEvent event) {
        fakeSumma = davolanish.getPayment().getPaymentAmount();
        if (saleCheck.isSelected()) {
            saleTF.setDisable(false);
            saleTF.setText((davolanish.getPayment().getSale() * 100) + "");
            summaTF.setEditable(false);
        } else {
            saleTF.clear();
            saleTF.setDisable(true);
            summaTF.setEditable(true);
            davolanish.getPayment().setSale(0);
            fakeSale = 0;
        }

        summaTF.setText(fakeSumma + "");
    }

    @FXML
    private void setSale(KeyEvent event) {

        fakeSumma = davolanish.getPayment().getPaymentAmount();
        try {
            double v = Double.parseDouble(saleTF.getText());
            if (v > 100) {
                saleTF.setText("100");
                saleTF.positionCaret(saleTF.getText().length());
                v = 100;
            }
            fakeSale = (v / 100);
        } catch (NumberFormatException e) {
            fakeSale = 0;
            System.out.println(e.getMessage());
        }
        fakeSumma -= fakeSumma * fakeSale;
        summaTF.setText(fakeSumma + "");

        System.out.println("sale = " + fakeSale);
        System.out.println("summa = " + fakeSumma);
    }

    @FXML
    private void changeSumm(KeyEvent event) {
        try {
            fakeSumma = (Double.parseDouble(summaTF.getText()));
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
    }

    private String decimalFormat(double d) {
        return new DecimalFormat("#,##0.00").format(d);
    }

}
