package saebelma.algogeo;

public class IntersectionOfLineSegmentsBruteForce extends IntersectionOfLineSegments {

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	protected String getTitle() {
		return "Intersection of line segments (brute force)";
	}	

	@Override
	protected void runAlgorithm() {
		
		// Brute force algorithm for intersection of line segments
		// Number of required calculations is n * (n - 1) / 2
		// Plus cost for reporting k intersections
		// => O(n^2)
		for (int i = 0; i < lineSegments.size(); i++) {
			for (int j = i + 1; j < lineSegments.size(); j++) {
				Point intersection = intersectionBetween(lineSegments.get(i), lineSegments.get(j));
				if (intersection != null)
					intersections.add(intersection);
			}
		}
	}
	
	private Point intersectionBetween(LineSegment ls_1, LineSegment ls_2) {

		// Calculate coordinate forms of lines
		LineCoordinates lc_1 = calculateLineCoordinates(ls_1);
		LineCoordinates lc_2 = calculateLineCoordinates(ls_2);

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
	
	private LineCoordinates calculateLineCoordinates(LineSegment ls) {
		double a = ls.start.y - ls.end.y;
		double b = ls.end.x - ls.start.x;
		double c = ls.end.x * ls.start.y - ls.start.x * ls.end.y;

		return new LineCoordinates(a, b, c);
	}
}
