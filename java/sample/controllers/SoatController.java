package sample.controllers;

import eu.hansolo.medusa.Clock;
import eu.hansolo.medusa.ClockBuilder;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class SoatController implements Initializable {


    @FXML
    private VBox vb;

    private Clock clock;
    private long lastTimerCall;
    private AnimationTimer timer;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        vb.setStyle("-fx-background-color: radial-gradient(" +
                "focus-angle 0.0deg, " +
                "focus-distance 10.0% , " +
                "center 50.0% 50.0%," +
                " radius 60%," +
                " #c8ffa5 50.0%," +
                " #ffffff 150.0%)"
        );

        clock = ClockBuilder.create()
                .skinType(Clock.ClockSkinType.SLIM)
                .backgroundPaint(Color.web("#a31d57"))
                .borderPaint(Color.web("#e09a16"))
                .borderWidth(4.7)
                .dateColor(Color.YELLOWGREEN)
                .secondColor(Color.web("#d2fa7d"))
                .dateVisible(true)
                .discreteHours(false)
                .discreteMinutes(false)
                .discreteSeconds(false)
                .hourColor(Color.CORAL)
                .minuteColor(Color.BLUE)
                .hourTickMarkColor(Color.AQUA)
                .hourTickMarksVisible(true)
                .knobColor(Color.MAGENTA)
                .running(true)
                .prefSize(400, 400)
                .locale(new Locale("ru", "RU"))
                .build();
        clock.setSecondsVisible(true);

        lastTimerCall = System.nanoTime();

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                if (now > lastTimerCall + 5_000_000_000L) {

                    clock.setSecondColor(Color.web("#87d1ff"));

                    if (now > lastTimerCall + 10_000_000_000L) {

                        clock.setSecondColor(Color.web("#d2fa7d"));
                        lastTimerCall = now;
                    }
                }
            }
        };


        vb.getChildren().clear();
        vb.getChildren().add(clock);


        timer.start();

    }


}
