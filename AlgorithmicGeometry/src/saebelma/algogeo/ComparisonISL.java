package saebelma.algogeo;

import java.util.ArrayList;
import java.util.List;

public class ComparisonISL {
	
	public static void main(String[] args) {
		
		// DATA
		List<LineSegment> points = new ArrayList<>();
		int[] runs = {10, 30, 100, 300, 1000, 3000, 10_000, 30_000, 100_000};
		long startTime, elapsedTime;
		boolean overrun = false;
		
		// Dry run to eliminate overhead for class loading etc.
		points = LineSegment.random(1000, 1000, 10);
		IntersectingLineSegments.bruteForce(points);
		IntersectingLineSegments.planeSweep(points);
		
		System.out.println("-------------");
		System.out.println("Long segments");
		System.out.println("-------------");
		
		for (int currentN : runs) {
			System.out.println("N = " + currentN);
			points = LineSegment.random(1000, 1000, currentN);
			
			startTime = System.currentTimeMillis();
			IntersectingLineSegments.bruteForce(points);
			elapsedTime = System.currentTimeMillis() - startTime;
			System.out.println("Brute force = " + elapsedTime + " ms");
			if (elapsedTime > 1000) overrun = true;
			
			startTime = System.currentTimeMillis();
			IntersectingLineSegments.planeSweep(points);
			elapsedTime = System.currentTimeMillis() - startTime;
			System.out.println("Plane sweep = " + elapsedTime + " ms");
			if (elapsedTime > 1000) overrun = true;
			
			if (overrun) break;
		}
		
		System.out.println("--------------");
		System.out.println("Short segments");
		System.out.println("--------------");
		
		overrun = false;
		
		for (int currentN : runs) {
			System.out.println("N = " + currentN);
			points = LineSegment.randomShort(1000, 1000, currentN);
			
			startTime = System.currentTimeMillis();
			IntersectingLineSegments.bruteForce(points);
			elapsedTime = System.currentTimeMillis() - startTime;
			System.out.println("Brute force = " + elapsedTime + " ms");
			if (elapsedTime > 1000) overrun = true;
			
			startTime = System.currentTimeMillis();
			IntersectingLineSegments.planeSweep(points);
			elapsedTime = System.currentTimeMillis() - startTime;
			System.out.println("Plane sweep = " + elapsedTime + " ms");
			if (elapsedTime > 1000) overrun = true;
			
			if (overrun) break;
		}
		
		System.out.println("-------------");
		System.out.println("Tiny segments");
		System.out.println("-------------");
		
		overrun = false;
		
		for (int currentN : runs) {
			System.out.println("N = " + currentN);
			points = LineSegment.randomTiny(1000, 1000, currentN);
			
			startTime = System.currentTimeMillis();
			IntersectingLineSegments.bruteForce(points);
			elapsedTime = System.currentTimeMillis() - startTime;
			System.out.println("Brute force = " + elapsedTime + " ms");
			if (elapsedTime > 1000) overrun = true;
			
			startTime = System.currentTimeMillis();
			IntersectingLineSegments.planeSweep(points);
			elapsedTime = System.currentTimeMillis() - startTime;
			System.out.println("Plane sweep = " + elapsedTime + " ms");
			if (elapsedTime > 1000) overrun = true;
			
			if (overrun) break;
		}
	}

}
