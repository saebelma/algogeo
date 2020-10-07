package saebelma.algogeo;

import java.util.ArrayList;
import java.util.List;

public class RunnerCH extends Runner {
	
	// DATA
	private List<Point> points;
	private List<Point> upperLeftContour = new ArrayList<>();
	private List<Point> lowerLeftContour = new ArrayList<>();
	private List<Point> upperRightContour = new ArrayList<>();
	private List<Point> lowerRightContour = new ArrayList<>();
	private List<Point> upperLeftHull, lowerRightHull;
	private final int width = 1000;
	private final int height = 1000;
	private final int numberOfPoints = 1000;
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
		points = Point.randomCross(width, height, numberOfPoints);
	}
	
	@Override
	protected void runAlgorithm() {
		algorithm = new ConvexHull(points);
		algorithm.run();
		upperLeftContour = algorithm.getUpperLeftContour();
		lowerLeftContour = algorithm.getLowerLeftContour();
		upperRightContour = algorithm.getUpperRightContour();
		lowerRightContour = algorithm.getLowerRightContour();
		upperLeftHull = algorithm.getUpperLeftHull();
		lowerRightHull = algorithm.getLowerRightHull();

	}

	@Override
	protected void showOutput() {
		paintPoints(points, inputColor);
		paintLineSegments(LineSegment.segmentsFromPoints(upperLeftContour), intermediateColor);
		paintLineSegments(LineSegment.segmentsFromPoints(lowerLeftContour), intermediateColor);
		paintLineSegments(LineSegment.segmentsFromPoints(upperRightContour), intermediateColor);
		paintLineSegments(LineSegment.segmentsFromPoints(lowerRightContour), intermediateColor);
		paintLineSegments(LineSegment.segmentsFromPoints(upperLeftHull), outputColor);
		paintLineSegments(LineSegment.segmentsFromPoints(lowerRightHull), outputColor);
	}
}
