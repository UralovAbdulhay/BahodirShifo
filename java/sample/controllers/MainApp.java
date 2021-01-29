package sample.controllers;

import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;
import eu.hansolo.medusa.Clock;
import eu.hansolo.medusa.ClockBuilder;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import static javafx.application.Application.launch;


public class MainApp extends Application {

    private static final Logger LOGGER = Logger.getLogger(MainApp.class.getName());

    public static void main( String[] args ) {
        launch(args);
    }

    private Clock clock;
    private long lastTimerCall;
    private AnimationTimer timer;

    @Override
    public void init() throws Exception {
        System.out.println("init ishladi");

        clock = ClockBuilder.create()
                .skinType(Clock.ClockSkinType.SLIM)
                .backgroundPaint(Color.web("#a31d57"))
                .borderPaint(Color.web("#03af07"))
                .secondColor(Color.YELLOW)
                .borderWidth(4.7)
                .dateColor(Color.RED)
                .dateVisible(true)
                .discreteHours(false)
                .discreteMinutes(false)
                .discreteSeconds(false)
                .hourColor(Color.CORAL)
                .hourTickMarkColor(Color.AQUA)
                .hourTickMarksVisible(true)
                .knobColor(Color.MAGENTA)
                .running(true)
                .build();
        clock.setSecondsVisible(true);

        lastTimerCall = System.nanoTime();
        timer = new AnimationTimer() {
            @Override
            public void handle( long now ) {

                if ( now > lastTimerCall + 5_000_000_000L ) {

                    lastTimerCall = now;
                }
            }
        };

        timer.start();

    }

    @Override
    public void start( Stage stage ) throws Exception {

        StackPane pane = new StackPane(clock);

        pane.setPadding(new Insets(20));
        pane.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);

        Scene scene = new Scene(pane);

        stage.setTitle("Medusa Clock Test");
        stage.setScene(scene);
        stage.show();


    }

}