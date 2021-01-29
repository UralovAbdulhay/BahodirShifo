package sample.modules.davolanish;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.paint.Paint;
import sample.controllers.AnaliseViewController;
import sample.modules.Vrach;
import sample.modules.Xizmat;

import java.text.DecimalFormat;

public class Konsultatsiya extends Xizmat {

    private AnaliseViewController controller;
//    private JFXButton konsultatsiyaBtn;

    public Konsultatsiya(String nomi, Double narx) {
        this(nomi, narx, null);
    }

    public Konsultatsiya(Konsultatsiya konsultatsiya) {
        super(konsultatsiya);
    }

    public Konsultatsiya(String nomi, Double narx, Vrach konsultVrach) {
        super(nomi, narx, konsultVrach);
        super.xizmatType=XizmatType.Konsultatsiya;

        super.xizmatBt = new JFXButton("");
//        super.xizmatBt.setOnAction(event -> {
//            controller.addKonsultatsiya();
//        });
        super.xizmatBt.setPrefWidth(60d);
        super.xizmatBt.setContentDisplay(ContentDisplay.CENTER);
        MaterialDesignIconView iconView = new MaterialDesignIconView(MaterialDesignIcon.PLUS);
        iconView.setGlyphSize(18);
        super.xizmatBt.setGraphic(iconView);
        super.xizmatBt.setStyle("-fx-background-color: #b458a5");
        super.xizmatBt.setRipplerFill(Paint.valueOf("#FFF900"));
        super.xizmatBt.setFocusTraversable(false);

    }

    public void setController(AnaliseViewController controller){
        this.controller = controller;
    }

}
