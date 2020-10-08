package saebelma.algogeo;

import java.util.ArrayList;
import java.util.List;

public class RunnerCH extends Runner {
	
	// DATA
	private List<Point> points;
	private List<List<Point>> contour = new ArrayList<>();
	private List<List<Point>> hull = new ArrayList<>();
	private final int width = 1000;
	private final int height = 1000;
	private final int numberOfPoints = 100;
	private ConvexHull algorithm;
	
	// LAUNCH APPLICATON
	public static void main(String[] args) {
		launch(args);
	}
	
	// IMPLEMENTATION
	
	@Override
	protected String getTitle() {
		return "Convex hull of set of points";
	}	
	@Override
	protected void setInput() {
		points = Point.randomCircle(width, height, numberOfPoints);
	}
	
	@Override
	protected void runAlgorithm() {
		algorithm = new ConvexHull(points);
		algorithm.run();
		contour = algorithm.getContour();
		hull = algorithm.getHull();
	}

	@Override
	protected void showOutput() {
		paintPoints(points, inputColor);
		for (List<Point> quadrant : contour) {
			paintLineSegments(LineSegment.segmentsFromPoints(quadrant), intermediateColor);
		}
		for (List<Point> quadrant : hull) {
			paintLineSegments(LineSegment.segmentsFromPoints(quadrant), outputColor);
		}
	}
}
