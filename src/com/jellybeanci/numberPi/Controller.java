package com.jellybeanci.numberPi;

import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

import java.io.IOException;
import java.util.ArrayList;

public class Controller
{
    public static final int WIDTH = 590;
    public static final int HEIGHT = 548;
    //
    private static Timeline update;
    private static Color backcolor = Color.rgb(51, 51, 51);
    private static GraphicsContext gc;
    private static Point2D center;
    private static int piCount = 0;
    private static double hue = 0;

    @FXML
    Pane rootPane;

    @FXML
    Canvas canvas;

    protected static ArrayList<Arc> arcs = new ArrayList<>();
    protected static double x;
    protected static double y;
    protected static double arcR = 250;

    @FXML
    private void initialize()
    {
        try
        {
            Number pi = GetData.getFile("Numbers/Pi.txt");
            for (int i = 0; i < pi.count; i++)
            {
                System.out.println(pi.getDigits().get(i));
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


        //
        center = new Point2D(WIDTH / 2, HEIGHT / 2);
        gc = canvas.getGraphicsContext2D();
        gc.setLineWidth(0.55);
        gc.strokeLine(center.getX(),center.getY(),center.getX(),center.getY()-arcR);




        Circle cir = new Circle(10, Color.RED);
        x = canvas.getLayoutX() + canvas.getWidth() / 2;
        y = canvas.getLayoutY() + canvas.getHeight() / 2;
        cir.setCenterX(x);
        cir.setCenterY(y);
        //
        for (int i = 0; i < 10; i++)
        {
            //ARCS
            double angle = 90 + i * 36;
            Arc arc = new Arc(x, y, arcR, arcR, angle - (15.5), 31);
            arc.setFill(Color.TRANSPARENT);
            arc.setStroke(Color.hsb(angle, 1, 1));
            arc.setStrokeWidth(20);
            arc.setStrokeType(StrokeType.CENTERED);
            arcs.add(arc);
            rootPane.getChildren().addAll(arc);
        }
        //
        rootPane.getChildren().addAll(cir);
    }

    public static Point2D endPoint(double x, double y, double angle, double size)
    {
        x += (size * Math.cos(angleToRadian(angle)));
        y -= (size * Math.sin(angleToRadian(angle)));
        return new Point2D(x, y);
    }

    static double angleToRadian(double angle)
    {
        return (Math.PI / 180.0) * angle;
    }
}
