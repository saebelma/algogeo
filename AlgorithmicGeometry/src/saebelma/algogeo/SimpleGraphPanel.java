package saebelma.algogeo;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SimpleGraphPanel extends Application {

	final int panelWidth = 1000;
	final int panelHeight = 1000;
	final int numberOfLineSegments = 100;
	
	final Color lineColor = Color.BLACK;
	final Color intersectionColor = Color.RED;
	final double intersectionImageDiameter = 3.0;
	
	GraphicsContext gc;
	LineSegment[] lineSegment = new LineSegment[numberOfLineSegments];
	List<Point> intersections = new ArrayList<>();

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("SimpleGraphPanel");
		Group root = new Group();
		Canvas canvas = new Canvas(panelWidth, panelHeight);
		gc = canvas.getGraphicsContext2D();

		createRandomLineSegmetns();
		paintLineSegments();

		long startTime = System.currentTimeMillis();
		calculateIntersections();
		System.out.println("Elapsed time = " + (System.currentTimeMillis() - startTime) + " ms");
		System.out.println("Number of intersections = " + intersections.size());

		paintIntersections();

		root.getChildren().add(canvas);
		stage.setScene(new Scene(root));
		stage.show();
	}

	private void paintIntersections() {
		gc.setFill(intersectionColor);
		for (Point intersection : intersections) {
			if (intersection != null)
				gc.fillOval(intersection.x - intersectionImageDiameter / 2,
						intersection.y - intersectionImageDiameter / 2, intersectionImageDiameter,
						intersectionImageDiameter);
		}
	}

	private void calculateIntersections() {
		for (int i = 0; i < numberOfLineSegments; i++) {
			for (int j = i + 1; j < numberOfLineSegments; j++) {
				Point intersection = intersectionBetween(lineSegment[i], lineSegment[j]);
				if (intersection != null)
					intersections.add(intersection);
			}
		}
	}

	private Point intersectionBetween(LineSegment ls_1, LineSegment ls_2) {

		// Calculate coordinate forms
		LineCoordinates lc_1 = calculateLineCoordinates(ls_1);
		LineCoordinates lc_2 = calculateLineCoordinates(ls_2);

		// If the lines are parallel, there's no true intersection
		if (lc_1.a * lc_2.b - lc_2.a * lc_1.b == 0)
			return null;

		// Calculate intersection
		Point intersection = new Point((lc_1.c * lc_2.b - lc_2.c * lc_1.b) / (lc_1.a * lc_2.b - lc_2.a * lc_1.b),
				(lc_1.a * lc_2.c - lc_2.a * lc_1.c) / (lc_1.a * lc_2.b - lc_2.a * lc_1.b));

		// Check if intersection is on both segments
		if (intersection.x > Math.min(ls_1.start.x, ls_1.end.x) && intersection.x < Math.max(ls_1.start.x, ls_1.end.x)
				&& intersection.x > Math.min(ls_2.start.x, ls_2.end.x)
				&& intersection.x < Math.max(ls_2.start.x, ls_2.end.x)) {
			return intersection;
		} else
			return null;
	}

	private LineCoordinates calculateLineCoordinates(LineSegment ls) {
		double a = ls.start.y - ls.end.y;
		double b = ls.end.x - ls.start.x;
		double c = ls.end.x * ls.start.y - ls.start.x * ls.end.y;

		return new LineCoordinates(a, b, c);
	}

	private void paintLineSegments() {
		gc.setStroke(lineColor);
		for (int i = 0; i < numberOfLineSegments; i++) {
			gc.strokeLine(lineSegment[i].start.x, lineSegment[i].start.y, lineSegment[i].end.x, lineSegment[i].end.y);
		}
	}

	private void createRandomLineSegmetns() {
		for (int i = 0; i < numberOfLineSegments; i++) {
			double x_1 = panelWidth * Math.random();
			double y_1 = panelHeight * Math.random();
			double x_2 = panelWidth * Math.random();
			double y_2 = panelHeight * Math.random();

			lineSegment[i] = new LineSegment(new Point(x_1, y_1), new Point(x_2, y_2));
		}
	}
}
