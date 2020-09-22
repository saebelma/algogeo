package saebelma.algogeo;

import java.util.ArrayList;
import java.util.List;

abstract public class IntersectionOfLineSegments extends Runner {
	
	protected List<LineSegment> lineSegments = new ArrayList<>();
	protected List<Point> intersections = new ArrayList<>();
	
	@Override
	protected void getInput() {
		lineSegments = Input.getRandomLineSegments();	
	}

	@Override
	protected void paintInput() {
		paintLineSegments(lineSegments, inputColor);
	}

	@Override
	protected void paintOutput() {
		paintPoints(intersections, outputColor);
	}
	




}
