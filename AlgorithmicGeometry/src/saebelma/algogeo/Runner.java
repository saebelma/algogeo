package saebelma.algogeo;

import java.util.List;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

// Template for running an algorithm a single time and displaying the result

abstract public class Runner extends Application {

	public static final int panelWidth = 1000;
	public static final int panelHeight = 1000;

	public static final Color inputColor = Color.BLACK;
	public static final Color outputColor = Color.RED;
	public static final Color intermediateColor = Color.BLUE;
	public static final double pointDiameter = 3.0;

	GraphicsContext gc;

	@Override
	public void start(Stage stage) throws Exception {

		setInput();

		long startTime = System.currentTimeMillis();
		runAlgorithm();
		long elapsedTime = System.currentTimeMillis() - startTime;

		Group root = new Group();
		Canvas canvas = new Canvas(panelWidth, panelHeight);
		gc = canvas.getGraphicsContext2D();

		stage.setTitle(getTitle() + " in " + elapsedTime + " ms");
		showOutput();

		root.getChildren().add(canvas);
		stage.setScene(new Scene(root));
		stage.show();
	}

	// ABSTRACT METHODS TO BE IMPLEMENTED BY CONCRETE SUBCLASS

	protected abstract String getTitle();

	protected abstract void setInput();

	protected abstract void runAlgorithm();

	protected abstract void showOutput();

	// UTILITY METHODS FOR PAINTING OUTPUT

	protected void paintPoints(List<Point> points, Color color) {
		paintPoints(points, color, pointDiameter);
	}

	protected void paintPoints(List<Point> points, Color color, double diameter) {
		gc.setFill(color);
		for (Point point : points) {
			if (point != null)
				gc.fillOval(point.x - diameter / 2, point.y - diameter / 2, diameter, diameter);
		}
	}

	protected void paintLineSegments(List<LineSegment> lineSegments, Color color) {
		gc.setStroke(color);
		for (LineSegment lineSegment : lineSegments) {
			gc.strokeLine(lineSegment.start.x, lineSegment.start.y, lineSegment.end.x, lineSegment.end.y);
		}
	}

	protected void paintLineSegment(LineSegment lineSegment, Color color) {
		gc.setStroke(color);
		gc.strokeLine(lineSegment.start.x, lineSegment.start.y, lineSegment.end.x, lineSegment.end.y);
	}
	
	protected void paintPoint(Point point, Color color, double diameter) {
		gc.setFill(color);
		gc.fillOval(point.x - diameter / 2, point.y - diameter / 2, diameter, diameter);
	}
}
