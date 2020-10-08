package saebelma.algogeo;

import java.util.ArrayList;
import java.util.List;

public class HullCalculator {

	List<Point> contour, hull;
	
	public HullCalculator(List<Point> contour) {
		this.contour = contour;
	}
	
	public void run() {
		calculateUpperLeftHull();
	}
	
	private void calculateUpperLeftHull() {
		hull = new ArrayList<>(contour);
		int currentIndex = 0;
		while (true) {
			// If we've reached the penultimate point, we're done
			if (currentIndex >= hull.size() - 2) break;
			
			// Check next point for convexity
			Point currentPoint = hull.get(currentIndex);
			Point nextPoint = hull.get(currentIndex + 1);
			Point pointAfterNext = hull.get(currentIndex + 2);
			int indexAfterNext = currentIndex + 2;
			LineSegment testLine = new LineSegment(currentPoint, pointAfterNext);
			if (DividePoints.rightOfLine(nextPoint, testLine)) {
				
				// If it's concave, we have to retrace our steps
				Point precedingPoint;
				while(true) {
					if (currentIndex == 0) {
						break;
					} else {
						precedingPoint = hull.get(currentIndex - 1); 
						testLine = new LineSegment(precedingPoint, currentPoint);
						if (DividePoints.rightOfLine(pointAfterNext, testLine)) {
							break;
						} else {
							currentIndex--;
							currentPoint = hull.get(currentIndex);
						}
					}
				}
				// Delete all points between current index and original point after next
				hull.subList(currentIndex + 1, indexAfterNext).clear();
			} else {
				currentIndex++;
			}
		}
	}

	public List<Point> getHull() {
		return hull;
	}
	
	
}
