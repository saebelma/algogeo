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
}
