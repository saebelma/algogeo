package saebelma.algogeo;

import java.util.ArrayList;
import java.util.List;

public class DividePoints {

	private List<Point> points;
	private LineSegment dividingLine;
	private List<Point> leftOfLine = new ArrayList<>();
	private List<Point> rightOfLine = new ArrayList<>();
	
	
	public DividePoints(List<Point> points, LineSegment dividingLine) {
		this.points = points;
		this.dividingLine = dividingLine;
	}
	
	public void run() {
		for (Point point : points) {
			if (rightOfLine(point)) {
				rightOfLine.add(point);
			} else {
				leftOfLine.add(point);
			}
		}
	}
	
	public List<Point> getPoints() {
		return points;
	}

	public LineSegment getDividingLine() {
		return dividingLine;
	}

	public List<Point> getLeftOfLine() {
		return leftOfLine;
	}

	public List<Point> getRightOfLine() {
		return rightOfLine;
	}

	private boolean rightOfLine(Point point) {
		double d = (point.x - dividingLine.start.x) * (dividingLine.end.y - dividingLine.start.y)
				- (point.y - dividingLine.start.y) * (dividingLine.end.x - dividingLine.start.x);
		
		return d < 0;
	}
	
	public static boolean rightOfLine(Point point, LineSegment line) {
		double d = (point.x - line.start.x) * (line.end.y - line.start.y)
				- (point.y - line.start.y) * (line.end.x - line.start.x);

		return d < 0;
	}
	
}
