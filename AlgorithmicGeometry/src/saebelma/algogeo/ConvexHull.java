package saebelma.algogeo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ConvexHull {

	private List<Point> points;
	private List<List<Point>> contour = new ArrayList<>();
	private List<List<Point>> hull = new ArrayList<>();
	
	public ConvexHull(List<Point> points) {
		this.points = points;
	}

	public void run() {
		Collections.sort(points, new XOrder());
		calculateContour();
		calculateHull();
	}
	
	private void calculateContour() {
		
		// First run calculates left contour in quadrants 0 and 1,
		// second run right contour in quadrants 2 and 3
		for (int run = 0; run < 2; run++) {
			contour.add(new ArrayList<>());
			contour.add(new ArrayList<>());
			
			// Add first point from the left or right, initialize
			// min and may y coordinate
			Point firstPoint = run == 0 ? points.get(0) : points.get(points.size() - 1);
			double minY = firstPoint.y;
			double maxY = firstPoint.y;
			contour.get(2 * run).add(firstPoint);
			contour.get(2 * run + 1).add(firstPoint);
			
			// Traverse points from left or right, updating min and max y,
			// adding points to contour
			for (int i = run == 0 ? 1 : points.size() - 2; run == 0 ? i < points.size() : i >= 0; i = i + 1 - (run * 2)) {
				Point point = points.get(i);
				if (point.y < minY) {
					minY = point.y;
					contour.get(2 * run).add(point);
				} else if (point.y > maxY) {
					maxY = point.y;
					contour.get(2 * run + 1).add(point);
				}
			}
		}
	}
	
	private void calculateHull() {
		
		for (int run = 0; run < 4; run++) {
			hull.add(new ArrayList<>(contour.get(run)));
			if (run == 1 || run == 2) Collections.reverse(hull.get(run));
			
			int currentIndex = 0;
			while (true) {
				// If we've reached the penultimate point, we're done
				if (currentIndex >= hull.get(run).size() - 2) break;
				
				// Check next point for convexity
				Point currentPoint = hull.get(run).get(currentIndex);
				Point nextPoint = hull.get(run).get(currentIndex + 1);
				Point pointAfterNext = hull.get(run).get(currentIndex + 2);
				int indexAfterNext = currentIndex + 2;
				LineSegment testLine = new LineSegment(currentPoint, pointAfterNext);
				if (DividePoints.rightOfLine(nextPoint, testLine)) {
					
					// If it's concave, we have to retrace our steps
					Point precedingPoint;
					while(true) {
						if (currentIndex == 0) {
							break;
						} else {
							precedingPoint = hull.get(run).get(currentIndex - 1); 
							testLine = new LineSegment(precedingPoint, currentPoint);
							if (DividePoints.rightOfLine(pointAfterNext, testLine)) {
								break;
							} else {
								currentIndex--;
								currentPoint = hull.get(run).get(currentIndex);
							}
						}
					}
					// Delete all points between current index and original point after next
					hull.get(run).subList(currentIndex + 1, indexAfterNext).clear();
				} else {
					currentIndex++;
				}
			}
		}
	}
	
	private static class XOrder implements Comparator<Point> {

		@Override
		public int compare(Point p1, Point p2) {
			if (p1.equals(p2)) {
				return 0;
			} else {
				return Double.compare(p1.x, p2.x);
			}
		}

	}
	

	public List<List<Point>> getContour() {
		return contour;
	}
	

	public List<List<Point>> getHull() {
		return hull;
	}
	
	
}
