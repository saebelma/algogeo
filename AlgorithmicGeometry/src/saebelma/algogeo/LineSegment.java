package saebelma.algogeo;

import java.util.ArrayList;
import java.util.List;

public class LineSegment {

	public Point start, end;
	
	public LineSegment(Point start, Point end) {
		this.start = start;
		this.end = end;
	}
	
	public static class LineCoordinates {

		public double a, b, c;
		
		public LineCoordinates(double a, double b, double c) {
			this.a = a;
			this.b = b;
			this.c = c;
		}
	}
	
	public static List<LineSegment> random(int width, int height, int number) {	
		List<LineSegment> lineSegments = new ArrayList<>();
		
		for (int i = 0; i < number; i++) {
			double x_1 = width * Math.random();
			double y_1 = height * Math.random();
			double x_2 = width * Math.random();
			double y_2 = height * Math.random();
			
			lineSegments.add(new LineSegment(new Point(x_1, y_1), new Point(x_2, y_2)));
		}
		
		return lineSegments;
	}
	
	public static Point intersection(LineSegment ls_1, LineSegment ls_2) {

		// Calculate coordinate forms of lines
		LineCoordinates lc_1 = lineCoordinates(ls_1);
		LineCoordinates lc_2 = lineCoordinates(ls_2);

		// If the lines are parallel, there's no true intersection
		if (lc_1.a * lc_2.b - lc_2.a * lc_1.b == 0)
			return null;

		// Calculate intersection between lines
		Point intersection = new Point((lc_1.c * lc_2.b - lc_2.c * lc_1.b) / (lc_1.a * lc_2.b - lc_2.a * lc_1.b),
				(lc_1.a * lc_2.c - lc_2.a * lc_1.c) / (lc_1.a * lc_2.b - lc_2.a * lc_1.b));

		// Check if intersection between lines is on both segments
		if (intersection.x > Math.min(ls_1.start.x, ls_1.end.x) && intersection.x < Math.max(ls_1.start.x, ls_1.end.x)
				&& intersection.x > Math.min(ls_2.start.x, ls_2.end.x)
				&& intersection.x < Math.max(ls_2.start.x, ls_2.end.x)) {
			return intersection;
		} else
			return null;
	}

	public static LineCoordinates lineCoordinates(LineSegment ls) {
		double a = ls.start.y - ls.end.y;
		double b = ls.end.x - ls.start.x;
		double c = ls.end.x * ls.start.y - ls.start.x * ls.end.y;

		return new LineCoordinates(a, b, c);
	}
}
