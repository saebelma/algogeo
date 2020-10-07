package saebelma.algogeo;

import java.util.ArrayList;
import java.util.List;

public class RunnerILSPS extends Runner {
	
	// DATA
	private List<LineSegment> lineSegments = new ArrayList<>();
//	private List<LineSegment> sweepLines = new ArrayList<>();
	private final int numberOfLineSegments = 1000;
	private List<Point> intersections = new ArrayList<>();
	
	// LAUNCH APPLICATON
	public static void main(String[] args) {
		launch(args);
	}
	
	// IMPLEMENTATION
	
	@Override
	protected String getTitle() {
		return "Intersecting line segments plane sweep";
	}	
	@Override
	protected void setInput() {
		lineSegments = LineSegment.randomShort(panelWidth, panelHeight, numberOfLineSegments);	
	}
	
	@Override
	protected void runAlgorithm() {
//		sweepLines = IntersectingLineSegments.planeSweep(lineSegments);
		intersections = IntersectingLineSegments.planeSweep(lineSegments);
		
	}

	@Override
	protected void showOutput() {
		paintLineSegments(lineSegments, inputColor);
		paintPoints(intersections, outputColor);
//		paintLineSegments(sweepLines, outputColor);
	}
}
