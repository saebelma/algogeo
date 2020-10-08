package saebelma.algogeo;

import java.util.ArrayList;
import java.util.List;

public class Point {

	public double x, y;
	
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public static double distance(Point a, Point b) {
		return Math.sqrt(Math.pow(b.x - a.x, 2) + Math.pow(b.y - a.y, 2));
	}
	
	public static List<Point> random(int width, int height, int number) {	
		List<Point> points = new ArrayList<>();
		
		for (int i = 0; i < number; i++) {
			double x = width * Math.random();
			double y = height * Math.random();
			
			points.add(new Point(x, y));
		}
		
		return points;
	}
	
	public static List<Point> randomCentered(int width, int height, int number) {	
		List<Point> points = new ArrayList<>();
		
		for (int i = 0; i < number; i++) {
			double x = 250 + (width - 500) * Math.random();
			double y = 250 + (height - 500) * Math.random();
			
			points.add(new Point(x, y));
		}
		
		return points;
	}
	
	public static List<Point> randomCross(int width, int height, int number) {
		List<Point> points = new ArrayList<>();

		for (int i = 0; i < number; i++) {
			double x = 100 + (width - 200) * Math.random();
			double y = 100 + (height - 200) * Math.random();

			if ((x > 400 && x < 600) || (y > 400 && y < 600))
				points.add(new Point(x, y));
		}

		return points;
	}
	
	public static List<Point> randomDiamond(int width, int height, int number) {
		List<Point> points = new ArrayList<>();
		
		for (int i = 0; i < number; i++) {
			double x = 100 + (width - 200) * Math.random();
			double y = 100 + (height - 200) * Math.random();
			
			if (Math.abs(x - 500) + Math.abs(y - 500) < 400)
				points.add(new Point(x, y));
		}
		
		return points;
	}
	
	public static List<Point> randomCircle(int width, int height, int number) {
		List<Point> points = new ArrayList<>();

		for (int i = 0; i < number; i++) {
			double x = 100 + (width - 200) * Math.random();
			double y = 100 + (height - 200) * Math.random();

			if (Math.sqrt(Math.pow(x - 500, 2) + Math.pow(y - 500, 2)) < 300)
				points.add(new Point(x, y));
		}

		return points;
	}
	
	@Override
	public String toString() {
		return "(" + IntersectingLineSegments.round(x) + ", " + IntersectingLineSegments.round(y) + ")";
	}
}
