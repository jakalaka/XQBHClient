package XQBHClient.ClientUI;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

public class MyCanvas extends Canvas {
    public int step;
    public GraphicsContext gc;

    public MyCanvas(double width, double height) {
        super(width, height);
        step = 1;
        gc = getGraphicsContext2D();
        draw(gc);
    }

    public void draw(GraphicsContext gc) {
        if (step == 1)
            gc.fillText("…®√Ë~~~~~~~~~", 300, 300);
        else if (step == 2)
            gc.fillText("Õ®—∂~~~~~~~~~", 300, 300);

    }
}