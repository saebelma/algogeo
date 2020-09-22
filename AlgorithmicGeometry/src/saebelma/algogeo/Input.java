package saebelma.algogeo;

import java.util.ArrayList;
import java.util.List;

public class Input {

	public static List<LineSegment> getRandomLineSegments() {
		
		List<LineSegment> lineSegments = new ArrayList<>();
		
		for (int i = 0; i < Runner.numberOfLineSegments; i++) {
			double x_1 = Runner.panelWidth * Math.random();
			double y_1 = Runner.panelHeight * Math.random();
			double x_2 = Runner.panelWidth * Math.random();
			double y_2 = Runner.panelHeight * Math.random();
			
			lineSegments.add(new LineSegment(new Point(x_1, y_1), new Point(x_2, y_2)));
		}
		
		return lineSegments;
	}
}
