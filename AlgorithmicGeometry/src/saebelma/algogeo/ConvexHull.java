package saebelma.algogeo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ConvexHull {

	private List<Point> points;
	private List<Point> upperLeftContour = new ArrayList<>();
	private List<Point> lowerLeftContour = new ArrayList<>();
	private List<Point> upperRightContour = new ArrayList<>();
	private List<Point> lowerRightContour = new ArrayList<>();
	private List<Point> upperLeftHull, lowerRightHull, upperRightHull, lowerLeftHull;
	
	public ConvexHull(List<Point> points) {
		this.points = points;
	}

	public void run() {
		Collections.sort(points, new XOrder());
		calculateLeftContour();
		calculateRightContour();
		calculateUpperLeftHull();
		calculateLowerRightHull();
		calculateUpperRightHull();
		calculateLowerLeftHull();
	}
	
	private void calculateLeftContour() {
		
		Point firstPoint = points.get(0);
		double minY = firstPoint.y;
		double maxY = firstPoint.y;
		upperLeftContour.add(firstPoint);
		lowerLeftContour.add(firstPoint);
		
		for (int i = 1; i < points.size(); i++) {
			Point point = points.get(i);
			if (point.y < minY) {
				minY = point.y;
				upperLeftContour.add(point);
			} else if (point.y > maxY) {
				maxY = point.y;
				lowerLeftContour.add(point);
			}
		}
	}
	
	private void calculateUpperLeftHull() {
		System.out.println("Upper left hull");
		upperLeftHull = new ArrayList<>(upperLeftContour);
		int currentIndex = 0;
		while (true) {
			System.out.println("Outer loop current index = " + currentIndex);
			// If we've reached the penultimate point, we're done
			if (currentIndex >= upperLeftHull.size() - 2) break;
			
			// Check next point for convexity
			Point currentPoint = upperLeftHull.get(currentIndex);
			Point nextPoint = upperLeftHull.get(currentIndex + 1);
			Point pointAfterNext = upperLeftHull.get(currentIndex + 2);
			int indexAfterNext = currentIndex + 2;
			LineSegment testLine = new LineSegment(currentPoint, pointAfterNext);
			if (DividePoints.rightOfLine(nextPoint, testLine)) {
				
				// If it's concave, we have to retrace our steps
				System.out.println("Found point right of test line");
				Point precedingPoint;
				while(true) {
					System.out.println("Inner loop current index = " + currentIndex);
					if (currentIndex == 0) {
						break;
					} else {
						precedingPoint = upperLeftHull.get(currentIndex - 1); 
						testLine = new LineSegment(precedingPoint, currentPoint);
						if (DividePoints.rightOfLine(pointAfterNext, testLine)) {
							System.out.println("Done");
							break;
						} else {
							System.out.println("Not done");
							currentIndex--;
							currentPoint = upperLeftHull.get(currentIndex);
						}
					}
				}
				// Delete all points between current index and original point after next
				System.out.println("Deleteted " + (indexAfterNext - currentIndex - 1) + " points");
				upperLeftHull.subList(currentIndex + 1, indexAfterNext).clear();
			} else {
				currentIndex++;
			}
		}
	}
	
	private void calculateLowerRightHull() {
		System.out.println("Lower right hull");
		lowerRightHull = new ArrayList<>(lowerRightContour);
		int currentIndex = 0;
		while (true) {
			// If we've reached the penultimate point, we're done
			if (currentIndex >= lowerRightHull.size() - 2) break;
			
			// Check next point for convexity
			Point currentPoint = lowerRightHull.get(currentIndex);
			Point nextPoint = lowerRightHull.get(currentIndex + 1);
			Point pointAfterNext = lowerRightHull.get(currentIndex + 2);
			int indexAfterNext = currentIndex + 2;
			LineSegment testLine = new LineSegment(currentPoint, pointAfterNext);
			if (DividePoints.rightOfLine(nextPoint, testLine)) {
				
				// If it's concave, we have to retrace our steps
				Point precedingPoint;
				while(true) {
					if (currentIndex == 0) {
						break;
					} else {
						precedingPoint = lowerRightHull.get(currentIndex - 1); 
						testLine = new LineSegment(precedingPoint, currentPoint);
						if (DividePoints.rightOfLine(pointAfterNext, testLine)) {
							break;
						} else {
							currentIndex--;
							currentPoint = lowerRightHull.get(currentIndex);
						}
					}
				}
				// Delete all points between current index and original point after next
				lowerRightHull.subList(currentIndex + 1, indexAfterNext).clear();
			} else {
				currentIndex++;
			}
		}
	}

	private void calculateLowerLeftHull() {
		lowerLeftHull = new ArrayList<>(lowerLeftContour);
		Collections.reverse(lowerLeftHull);
		int currentIndex = 0;
		while (true) {
			// If we've reached the penultimate point, we're done
			if (currentIndex >= lowerLeftHull.size() - 2) break;
			
			// Check next point for convexity
			Point currentPoint = lowerLeftHull.get(currentIndex);
			Point nextPoint = lowerLeftHull.get(currentIndex + 1);
			Point pointAfterNext = lowerLeftHull.get(currentIndex + 2);
			int indexAfterNext = currentIndex + 2;
			LineSegment testLine = new LineSegment(currentPoint, pointAfterNext);
			if (DividePoints.rightOfLine(nextPoint, testLine)) {
				
				// If it's concave, we have to retrace our steps
				Point precedingPoint;
				while(true) {
					if (currentIndex == 0) {
						break;
					} else {
						precedingPoint = lowerLeftHull.get(currentIndex - 1); 
						testLine = new LineSegment(precedingPoint, currentPoint);
						if (DividePoints.rightOfLine(pointAfterNext, testLine)) {
							break;
						} else {
							currentIndex--;
							currentPoint = lowerLeftHull.get(currentIndex);
						}
					}
				}
				// Delete all points between current index and original point after next
				lowerLeftHull.subList(currentIndex + 1, indexAfterNext).clear();
			} else {
				currentIndex++;
			}
		}
	}
	private void calculateUpperRightHull() {
		upperRightHull = new ArrayList<>(upperRightContour);
		Collections.reverse(upperRightHull);
		int currentIndex = 0;
		while (true) {
			// If we've reached the penultimate point, we're done
			if (currentIndex >= upperRightHull.size() - 2) break;
			
			// Check next point for convexity
			Point currentPoint = upperRightHull.get(currentIndex);
			Point nextPoint = upperRightHull.get(currentIndex + 1);
			Point pointAfterNext = upperRightHull.get(currentIndex + 2);
			int indexAfterNext = currentIndex + 2;
			LineSegment testLine = new LineSegment(currentPoint, pointAfterNext);
			if (DividePoints.rightOfLine(nextPoint, testLine)) {
				
				// If it's concave, we have to retrace our steps
				Point precedingPoint;
				while(true) {
					if (currentIndex == 0) {
						break;
					} else {
						precedingPoint = upperRightHull.get(currentIndex - 1); 
						testLine = new LineSegment(precedingPoint, currentPoint);
						if (DividePoints.rightOfLine(pointAfterNext, testLine)) {
							break;
						} else {
							currentIndex--;
							currentPoint = upperRightHull.get(currentIndex);
						}
					}
				}
				// Delete all points between current index and original point after next
				upperRightHull.subList(currentIndex + 1, indexAfterNext).clear();
			} else {
				currentIndex++;
			}
		}
	}
	
	private void calculateRightContour() {
		
		Point lastPoint = points.get(points.size() - 1);
		double minY = lastPoint.y;
		double maxY = lastPoint.y;
		upperRightContour.add(lastPoint);
		lowerRightContour.add(lastPoint);
		
		for (int i = points.size() - 2; i >= 0 ; i--) {
			Point point = points.get(i);
			if (point.y < minY) {
				minY = point.y;
				upperRightContour.add(point);
			} else if (point.y > maxY) {
				maxY = point.y;
				lowerRightContour.add(point);
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
	
	public List<Point> getUpperLeftContour() {
		return upperLeftContour;
	}

	public List<Point> getLowerLeftContour() {
		return lowerLeftContour;
	}

	public List<Point> getUpperRightContour() {
		return upperRightContour;
	}

	public List<Point> getLowerRightContour() {
		return lowerRightContour;
	}

	public List<Point> getUpperLeftHull() {
		return upperLeftHull;
	}

	public List<Point> getLowerRightHull() {
		return lowerRightHull;
	}

	public List<Point> getUpperRightHull() {
		return upperRightHull;
	}

	public List<Point> getLowerLeftHull() {
		return lowerLeftHull;
	}
	
}
