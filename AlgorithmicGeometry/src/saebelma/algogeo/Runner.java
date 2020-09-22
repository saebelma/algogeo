package saebelma.algogeo;

import java.util.List;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

abstract public class Runner extends Application {

	public static final int panelWidth = 1000;
	public static final int panelHeight = 1000;
	public static final int numberOfLineSegments = 100;
	
	public static final Color inputColor = Color.BLACK;
	public static final Color outputColor = Color.RED;
	public static final double pointDiameter = 3.0;
	
	GraphicsContext gc;

	@Override
	public void start(Stage stage) throws Exception {
		
		stage.setTitle(getTitle());
		
		Group root = new Group();
		Canvas canvas = new Canvas(panelWidth, panelHeight);
		gc = canvas.getGraphicsContext2D();
		
		getInput();
		paintInput();
		
		long startTime = System.currentTimeMillis();
		
		runAlgorithm();
		
		long elapsedTime = System.currentTimeMillis() - startTime;
		System.out.println("Elapsed time = " + elapsedTime + " ms");
		
		paintOutput();

		root.getChildren().add(canvas);
		stage.setScene(new Scene(root));
		stage.show();
	}

	protected abstract String getTitle();
	
	protected abstract void getInput();
	
	protected abstract void paintInput();
	
	protected abstract void runAlgorithm();
	
	protected abstract void paintOutput();

	protected void paintPoints(List<Point> points, Color color) {
		gc.setFill(color);
		for (Point point : points) {
			if (point != null)
				gc.fillOval(point.x - pointDiameter / 2,
						point.y - pointDiameter / 2, pointDiameter,
						pointDiameter);
		}
	}

	protected void paintLineSegments(List<LineSegment> lineSegments, Color color) {
		gc.setStroke(color);
		for (LineSegment lineSegment: lineSegments) {
			gc.strokeLine(lineSegment.start.x, lineSegment.start.y, lineSegment.end.x, lineSegment.end.y);
		}
	}
}
