package saebelma.algogeo;

import java.util.ArrayList;
import java.util.List;

public class ComparisonCP {
	
	public static void main(String[] args) {
		
		// DATA
		List<Point> points = new ArrayList<>();
		int[] runs = {10, 30, 100, 300, 1000, 3000, 10_000, 30_000, 100_000};
		long startTime, elapsedTime;
		
		// Dry run to eliminate overhead for class loading etc.
		points = Point.random(1000, 1000, 10);
		ClosestPair.bruteForce(points);
		ClosestPair.planeSweep(points);
		
		for (int currentN : runs) {
			System.out.println("N = " + currentN);
			points = Point.random(1000, 1000, currentN);
			
			startTime = System.currentTimeMillis();
			ClosestPair.bruteForce(points);
			elapsedTime = System.currentTimeMillis() - startTime;
			System.out.println("Brute force = " + elapsedTime + " ms");
			
			startTime = System.currentTimeMillis();
			ClosestPair.planeSweep(points);
			elapsedTime = System.currentTimeMillis() - startTime;
			System.out.println("Plane sweep = " + elapsedTime + " ms");
		}
	}

}
