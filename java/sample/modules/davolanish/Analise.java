package sample.modules.davolanish;

import com.jfoenix.controls.JFXButton;
import com.sun.istack.internal.Nullable;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.paint.Paint;
import sample.controllers.AnaliseViewController;
import sample.modules.Vrach;
import sample.modules.Xizmat;

import java.text.DecimalFormat;

public class Analise extends Xizmat {

    private AnaliseViewController controller;

//    private JFXButton analiseBtn;


    public Analise(String nomi, Double narx) {
        this(nomi, narx, null);
    }

    public Analise(Analise analise) {
        super(analise);
    }

    public Analise(String nomi, Double narx, @Nullable Vrach analiseVrach) {
        super(nomi, narx, analiseVrach);
        super.xizmatType = XizmatType.Analise;


        super.xizmatBt = new JFXButton("");

//        super.xizmatBt.setOnAction(event -> {
//            controller.addAnalise();
//        });

        super.xizmatBt.setPrefWidth(60d);
        super.xizmatBt.setContentDisplay(ContentDisplay.CENTER);
        MaterialDesignIconView iconView = new MaterialDesignIconView(MaterialDesignIcon.PLUS);
        iconView.setGlyphSize(18);
        super.xizmatBt.setGraphic(iconView);
        super.xizmatBt.setStyle("-fx-background-color: #00ff4c");
        super.xizmatBt.setRipplerFill(Paint.valueOf("#D400A4"));
        super.xizmatBt.setFocusTraversable(false);


    }

    public void setController(AnaliseViewController controller) {
        this.controller = controller;
    }

    @Override
    public Vrach getVrach() {
        return super.vrach;
    }

    @Override
    public void setVrach(Vrach vrach) {
        super.vrach = vrach;
    }

}
