package saebelma.algogeo;

import java.util.ArrayList;
import java.util.List;

public class RunnerHC extends Runner {
	
	// DATA
	private List<Point> contour = new ArrayList<>(), hull;

	
	// LAUNCH APPLICATON
	public static void main(String[] args) {
		launch(args);
	}
	
	// IMPLEMENTATION
	
	@Override
	protected String getTitle() {
		return "Calculate hull from contour";
	}	
	@Override
	protected void setInput() {
		contour.add(new Point(101.36, 583.2));
		contour.add(new Point(107.3, 579.11));
		contour.add(new Point(112.18, 485.11));
		contour.add(new Point(115.68, 463.1));
		contour.add(new Point(123.39, 418.37));
		contour.add(new Point(146.33, 416.2));
		contour.add(new Point(158.01, 408.68));
		contour.add(new Point(160.89, 406.62));
		contour.add(new Point(233.37, 400.75));
		contour.add(new Point(405.67, 153.46));
		contour.add(new Point(406.1, 130.11));
		contour.add(new Point(411.2, 123.3));
		contour.add(new Point(412.74, 113.52));
		contour.add(new Point(416.17, 111.03));
		contour.add(new Point(438.55, 101.81));
	}
	
	@Override
	protected void runAlgorithm() {
		HullCalculator algo = new HullCalculator(contour);
		algo.run();
		hull = algo.getHull();
	}

	@Override
	protected void showOutput() {
		paintLineSegments(LineSegment.segmentsFromPoints(contour), intermediateColor);
		paintLineSegments(LineSegment.segmentsFromPoints(hull), outputColor);
	}
}
