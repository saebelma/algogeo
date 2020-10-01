package saebelma.algogeo;

import java.util.ArrayList;
import java.util.List;

public class LineSegment {

	public Point start, end;
	LineCoordinates coordinates;
	String tag;
	
	public LineSegment(Point start, Point end) {
		
		// We want the start point to be left of the end point
		this.start = start.x <= end.x ? start : end;
		this.end = start.x > end.x ? start : end;
		this.coordinates = coordinates(this);
	}
	
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public static class LineCoordinates {

		public double a, b, c;
		
		public LineCoordinates(double a, double b, double c) {
			this.a = a;
			this.b = b;
			this.c = c;
		}
	}
	
	public static Point intersection(LineSegment ls_1, LineSegment ls_2) {

		// Calculate coordinate forms of lines
		LineCoordinates lc_1 = ls_1.coordinates;
		LineCoordinates lc_2 = ls_2.coordinates;

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

	public static LineCoordinates coordinates(LineSegment ls) {
		double a = ls.start.y - ls.end.y;
		double b = ls.end.x - ls.start.x;
		double c = ls.end.x * ls.start.y - ls.start.x * ls.end.y;

		return new LineCoordinates(a, b, c);
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
	
	public static List<LineSegment> randomShort(int width, int height, int number) {	
		List<LineSegment> lineSegments = new ArrayList<>();
		
		for (int i = 0; i < number; i++) {
			double x = (width - 100) * Math.random() + 50;
			double y = (height - 100) * Math.random() + 50;
			double x_1 = x + (100 * Math.random()) - 50;
			double y_1 = y + (100 * Math.random()) - 50;
			double x_2 = x + (100 * Math.random()) - 50;
			double y_2 = y + (100 * Math.random()) - 50;
			
			lineSegments.add(new LineSegment(new Point(x_1, y_1), new Point(x_2, y_2)));
		}
		
		return lineSegments;
	}
	
	public static List<LineSegment> randomTiny(int width, int height, int number) {	
		List<LineSegment> lineSegments = new ArrayList<>();
		
		for (int i = 0; i < number; i++) {
			double x = (width - 10) * Math.random() + 5;
			double y = (height - 10) * Math.random() + 5;
			double x_1 = x + (10 * Math.random()) - 5;
			double y_1 = y + (10 * Math.random()) - 5;
			double x_2 = x + (10 * Math.random()) - 5;
			double y_2 = y + (10 * Math.random()) - 5;
			
			lineSegments.add(new LineSegment(new Point(x_1, y_1), new Point(x_2, y_2)));
		}
		
		return lineSegments;
	}
	
	public static double getYAtX(LineSegment ls, double x) {
		
		LineCoordinates lc = ls.coordinates;
		double y = (lc.c - lc.a * x) / lc.b;
		
		return y;
	}
	
//	@Override
//	public String toString() {
//		return "LineSegment[" + start + ", " + end + "]";
//	}
	
	@Override
	public String toString() {
		return tag;
	}
	
    // Line segments are identical if their tags are identical 
    @Override
    public boolean equals(Object o) { 
  
        // Check if it's a line segment at all
        if (!(o instanceof LineSegment)) { 
            return false; 
        } 
          
        // Type cast to line segment so we can compare tags 
        LineSegment ls = (LineSegment) o; 
          
        // Compare the tags  
        return this.tag.compareToIgnoreCase(ls.tag) == 0; 
    } 
}
