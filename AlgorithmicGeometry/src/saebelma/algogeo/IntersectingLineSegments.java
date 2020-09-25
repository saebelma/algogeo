package saebelma.algogeo;

import java.util.ArrayList;
import java.util.List;

public class IntersectingLineSegments {
	
	// Brute force algorithm for intersection of line segments
	// Cost is n * (n - 1) / 2 calculations plus reporting k intersections
	// => O(n^2)
	public static List<Point> bruteForce(List<LineSegment> lineSegments) {
		
		List<Point> intersections = new ArrayList<>();
		
		for (int i = 0; i < lineSegments.size(); i++) {
			for (int j = i + 1; j < lineSegments.size(); j++) {
				Point intersection = LineSegment.intersection(lineSegments.get(i), lineSegments.get(j));
				if (intersection != null)
					intersections.add(intersection);
			}
		}
		
		return intersections;
	}
}
