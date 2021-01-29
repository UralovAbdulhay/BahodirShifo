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


public class Tekshirish extends Xizmat {

    private AnaliseViewController controller;

//    private JFXButton tekshirishBtn;

    public Tekshirish(String nomi, Double narx) {
        this(nomi, narx, null);
    }

    public Tekshirish(Tekshirish tekshirish) {
        super(tekshirish);
    }


    public Tekshirish(String nomi, Double narx, Vrach tekshirVrach) {
        super(nomi, narx, tekshirVrach);
        super.xizmatType=XizmatType.Tekshirish;

        super.xizmatBt = new JFXButton();

//        super.xizmatBt.setOnAction(event -> {
//            controller.addTekshirish();
//        });

        super.xizmatBt.setPrefWidth(60d);
        super.xizmatBt.setContentDisplay(ContentDisplay.CENTER);
        MaterialDesignIconView iconView = new MaterialDesignIconView(MaterialDesignIcon.PLUS);
        iconView.setGlyphSize(18);
        super.xizmatBt.setGraphic(iconView);
        super.xizmatBt.setStyle("-fx-background-color: #ff6100");
        super.xizmatBt.setRipplerFill(Paint.valueOf("#FFF600"));
        super.xizmatBt.setFocusTraversable(false);

    }

    public  AnaliseViewController getController() {
        return controller;
    }

    public  void setController(AnaliseViewController controller) {
        this.controller = controller;
    }

}

