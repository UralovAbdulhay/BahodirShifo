package sample.modules;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ContentDisplay;
import javafx.scene.paint.Paint;
import sample.modules.davolanish.Analise;
import sample.modules.davolanish.Konsultatsiya;
import sample.modules.davolanish.Tekshirish;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Comparator;

public class Davolanish {


    private int id;
//    private double summa;
    private LocalDate qabulDate;

    private ObservableList<Xizmat> xizmats;
    private Vrach recommendVrach;
    private Bemor bemor;
    private Payment payment;


    public Davolanish() {
        xizmats = FXCollections.observableArrayList();
        recommendVrach = new Vrach();
        payment = new Payment();
    }

    private void creatButton(Xizmat xizmat) {

        JFXButton davolanishCanButton = xizmat.getXizmatBt();

        davolanishCanButton.setPrefWidth(60d);
        davolanishCanButton.setContentDisplay(ContentDisplay.CENTER);
        MaterialDesignIconView icon = new MaterialDesignIconView(MaterialDesignIcon.MINUS);
        icon.setGlyphSize(18);
        davolanishCanButton.setGraphic(icon);
        davolanishCanButton.setFocusTraversable(false);


        if (xizmat.getXizmatType().equals(Xizmat.XizmatType.Analise)) {
            davolanishCanButton.setStyle("-fx-background-color: #00ff4c");
            davolanishCanButton.setRipplerFill(Paint.valueOf("#D400A4"));
        } else if (xizmat.getXizmatType().equals(Xizmat.XizmatType.Konsultatsiya)) {
            davolanishCanButton.setStyle("-fx-background-color: #b458a5");
            davolanishCanButton.setRipplerFill(Paint.valueOf("#FFF900"));
        } else if (xizmat.getXizmatType().equals(Xizmat.XizmatType.Tekshirish)) {
            davolanishCanButton.setStyle("-fx-background-color: #ff6100");
            davolanishCanButton.setRipplerFill(Paint.valueOf("#FFF600"));
        }
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    private void setSumma() {
        this.payment.setPaymentAmount(this.getXizmats().stream().mapToDouble(Xizmat::getCost).sum());
    }

    public ObservableList<Xizmat> getXizmats() {
        return xizmats;
    }


    public void addAnaliseLists(Analise analise) {
        this.xizmats.add(analise);
        creatButton(analise);
        paketListSort();
        setSumma();
    }

    public void addAnaliseLists(Tekshirish tekshirish) {
        this.xizmats.add(tekshirish);
        creatButton(tekshirish);
        paketListSort();
        setSumma();
    }

    public void addAnaliseLists(Konsultatsiya konsultatsiya) {
        this.xizmats.add(konsultatsiya);
        creatButton(konsultatsiya);
        paketListSort();
        setSumma();
    }

    public void addAnaliseLists(ObservableList<Xizmat> list) {
        this.xizmats.addAll(list);
        list.forEach(this::creatButton);
        paketListSort();
        setSumma();
    }


    public Vrach getRecommendVrach() {
        return recommendVrach;
    }

    public void setRecommendVrach(Vrach recommendVrach) {
        this.recommendVrach = recommendVrach;
    }

    public Bemor getBemor() {
        return bemor;
    }

    public void setBemor(Bemor bemor) {
        this.bemor = bemor;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public LocalDate getQabulDate() {
        return qabulDate;
    }

    public void setQabulDate(LocalDate qabulDate) {
        this.qabulDate = qabulDate;
    }

    public void paketListSort() {

        xizmats.sort(new Comparator<Xizmat>() {
            @Override
            public int compare(Xizmat o1, Xizmat o2) {
                return o1.getXizmatType().compareTo(o2.getXizmatType());
            }
        });

        for (int i = 0; i < xizmats.size(); i++) {
            xizmats.get(i).setId(i + 1);
        }
    }


    private String decimalFormat(double d) {
        return new DecimalFormat("#,##0.00").format(d);
    }


    @Override
    public String toString() {
        return "Davolanish{" +
                "\nid=" + id +
                ", \nxizmats=" + xizmats +
                ", \nrecommendVrach=" + recommendVrach +
                ", \nbemor=" + bemor +
                ", \npayment=" + payment +
                "\n}";
    }
}
