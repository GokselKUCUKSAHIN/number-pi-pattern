package com.jellybeanci.numberPi;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

import java.util.ArrayList;

public class Controller
{

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
            Arc arc = new Arc(x, y, arcR, arcR, angle-(15.5), 31);
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

    public  static Point2D endPoint(double x, double y, double angle, double size)
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
