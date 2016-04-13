package mandelbrotProject;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.CanvasBuilder;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.IOException;


    /*************************************************
     * Created by Christian on 1-3-2016.             *
     *************************************************
     * set up a Canvas and create scene for Java FX  *
     * Consruct the Mandelbrot method                *
     * draw the Mandelbrot method on the stage       *                                              *
     *************************************************/

public class Mandelbrot extends Application {
        //Canvas values
        private static final int CanvasWidth = 1000;
        private static final int CanvasHeight = 800;

        //Mandelbrotset values
        private static double MandelbrotRealNoMin = -2;
        private static double MandelbrotRealNoMax = 1;
        private static double MandelbrotImmNoMin = -1.2;
        private static double MandelbrotImmNoMax = 1.2;

        @Override
        public void start(Stage primaryStage) throws IOException {
            //Construct Pane and Canvas
            Pane pane = new Pane();
            Canvas canvas = CanvasBuilder.create()
                    .height(CanvasHeight)
                    .width(CanvasWidth)
                    .build();


            //Colors for the Mandelbrotset
            colorSet(canvas.getGraphicsContext2D(),
                    MandelbrotRealNoMin,
                    MandelbrotRealNoMax,
                    MandelbrotImmNoMin,
                    MandelbrotImmNoMax);



            //Add canvas to root
            pane.getChildren().add(canvas);

            //SetScene and Show Stage
            Scene scene = new Scene(pane);
            scene.setFill(Color.BLACK);

            primaryStage.setTitle("Mandelbrotset");
            primaryStage.setScene(scene);
            primaryStage.show();
                }

    //The Math
    private void colorSet(GraphicsContext graphics,double realMin, double realMax, double immMin, double immMax) {
        double precision = Math.max((realMax - realMin) / CanvasWidth, (immMax - immMin) / CanvasHeight);
        int convergenceSteps = 50;
        for (double c = realMin, xR = 0; xR < CanvasWidth; c = c + precision, xR++) {
            for (double ci = immMin, yR = 0; yR < CanvasHeight; ci = ci + precision, yR++) {
                double convergenceValue = checkConvergence(ci, c, convergenceSteps);
                double t1 = convergenceValue / convergenceSteps;
                double c1 = Math.min(255 * 2 * t1, 255);
                double c2 = Math.max(255 * (2 * t1 - 1), 0);

                if (convergenceValue != convergenceSteps) {
                    graphics.setFill(Color.color(c2 / 255.0, c1 / 255.0, c2 / 255.0));
                } else {
                    graphics.setFill(Color.PURPLE); // Convergence Color
                }
                graphics.fillRect(xR, yR, 1, 1);
            }
        }
    }

    //Checks the convergence of a coordinate (c, ci) The convergence factor determines the color of the point.
    private int checkConvergence(double ci, double c, int convergenceSteps) {
        double z = 0;
        double zi = 0;
        for (int i = 0; i < convergenceSteps; i++) {
            double ziT = 2 * (z * zi);
            double zT = z * z - (zi * zi);
            z = zT + c;
            zi = ziT + ci;

            if (z * z + zi * zi >= 4.0) {
                return i;
            }
        }
        return convergenceSteps;
    }

          public static void main(String[] args) {
            launch(args);



        }
    }













