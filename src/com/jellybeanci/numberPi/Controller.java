package com.jellybeanci.numberPi;

import javafx.animation.KeyFrame;
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
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;

public class Controller
{

    public static final int WIDTH = 590;
    public static final int HEIGHT = 548;
    //
    public static Timeline update;
    private static Color backcolor = Color.rgb(51, 51, 51);
    private static Color forecolor;
    private static GraphicsContext gc;
    private static Point2D center;
    private static double hue = 0;
    private Point2D prev;

    @FXML
    Pane rootPane;

    @FXML
    Canvas canvas;

    @FXML
    Text numPi;
    @FXML
    Text num;

    protected static ArrayList<Arc> arcs = new ArrayList<>();
    protected static final int[] angleArray = new int[]{90, 54, 18, 342, 306, 270, 234, 198, 162, 126};
    protected static double x;
    protected static double y;
    protected static double arcR = 250;
    protected double r = 0.95;
    protected static Number number;
    private static int piCount = 0;

    @FXML
    private void initialize()
    {
        //
        try
        {
            // Load Numbers on to Number Object
            number = GetData.getFile("Numbers/number_Pi.txt");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        //
        update = new Timeline(new KeyFrame(Duration.millis(64), e -> {
            frame();
        }));
        update.setCycleCount(Timeline.INDEFINITE);
        update.setRate(10);
        update.setAutoReverse(false);
        //
        //
        center = new Point2D(WIDTH / 2, HEIGHT / 2);

        gc = canvas.getGraphicsContext2D();
        /*
        gc.setLineWidth(0.55);
        Point2D next = endPoint(center.getX(), center.getY(), 72 + 90, arcR / 2);
        gc.strokeLine(center.getX(), center.getY(), next.getX(), next.getY());*/
        //
        x = canvas.getLayoutX() + canvas.getWidth() / 2;
        y = canvas.getLayoutY() + canvas.getHeight() / 2;
        drawArcs();
        //
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

    protected void drawArcs()
    {
        for (int i = 0; i < 10; i++)
        {
            //ARCS
            double angle = 90 + i * 36;
            //Arc arc = new Arc(x, y, arcR, arcR, angle - (15.5), 31); //seperated
            Arc arc = new Arc(x, y, arcR, arcR, angle - (16), 32);
            arc.setFill(Color.TRANSPARENT);
            arc.setStroke(Color.hsb(angle, 1, 1));
            arc.setStrokeWidth(20);
            arc.setStrokeType(StrokeType.CENTERED);
            arcs.add(arc);
            rootPane.getChildren().addAll(arc);
        }
    }

    private static void drawLine(Point2D startPoint, Point2D endPoint, Color color)
    {
        gc.setStroke(color);
        gc.setLineWidth(0.5);
        gc.strokeLine(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());
    }

    private static void drawCurve(Point2D startPoint, Point2D endPoint, Color color)
    {
        gc.setStroke(color);
        gc.setLineWidth(0.5);
        //
        Point2D midPoint = new Point2D(Math.abs(startPoint.getX() + endPoint.getX()) * 0.5, Math.abs(startPoint.getY() + endPoint.getY()) * 0.5);
        double ang = Utils.calculateAngle(center, midPoint);
        Point2D tangent = endPoint(center.getX(), center.getY(), ang, arcR * 0.7);
        //
        gc.beginPath();
        gc.bezierCurveTo(startPoint.getX(), startPoint.getY(), tangent.getX(), tangent.getY(), endPoint.getX(), endPoint.getY());
        gc.stroke();
        //gc.strokeLine(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());
    }

    private static void drawDot(double x, double y, Color color)
    {
        gc.setLineWidth(1); //1
        //gc.strokeRect(x + 0.5, y + 0.5, 0.5, 0.5);
        gc.strokeOval(x + 0.5, y + 0.5, 0.3, 0.3);
    }

    protected void frame()
    {
        if (piCount < number.count)
        {
            numPi.setVisible(true);
            num.setVisible(false);
            numPi.setText(number.getDigits().get(piCount) + "");
            //
            if (piCount == 0)
            {
                prev = getPoint(0);
            }
            Point2D current = getPoint(piCount);
            // TODO ADD QUBIC SPLINES
            //drawDot(current.getX(), current.getY(), Color.color(Math.random(), Math.random(), Math.random()));
            //drawLine(prev, current, Color.color(Math.random(), Math.random(), Math.random()));
            drawCurve(prev, current, forecolor);
            prev = current;
            piCount++;
        }
    }

    protected Point2D getPoint(int count)
    {
        int dig = Integer.parseInt(number.getDigits().get(count) + "");
        forecolor = Color.hsb(angleArray[dig], 1, 1);
        return endPoint(center.getX(), center.getY(), angleArray[dig] + Rand.getDouble(-14, 14), arcR * r);
    }
}