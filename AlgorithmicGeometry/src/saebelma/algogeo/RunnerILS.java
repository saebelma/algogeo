package saebelma.algogeo;

import java.util.ArrayList;
import java.util.List;

abstract public class RunnerILS extends Runner {
	
	// DATA
	private List<LineSegment> lineSegments = new ArrayList<>();
	private List<Point> intersections = new ArrayList<>();
	private final int numberOfLineSegments = 1000;
	
	// LAUNCH APPLICATON
	public static void main(String[] args) {
		launch(args);
	}
	
	// IMPLEMENTATION
	
	@Override
	protected String getTitle() {
		return "Intersecting line segments brute force";
	}	
	@Override
	protected void setInput() {
		lineSegments = LineSegment.random(panelWidth, panelHeight, numberOfLineSegments);	
	}
	
	@Override
	protected void runAlgorithm() {
		intersections = IntersectingLineSegments.bruteForce(lineSegments);
	}

	@Override
	protected void showOutput() {
		paintLineSegments(lineSegments, inputColor);
		paintPoints(intersections, outputColor);
	}
}
