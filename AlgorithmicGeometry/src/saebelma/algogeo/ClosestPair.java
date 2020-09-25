package saebelma.algogeo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ClosestPair {

	public static List<Point> bruteForce(List<Point> points) {

		List<Point> closestPair = new ArrayList<>();
		double minDistance = Double.MAX_VALUE;

		for (int i = 0; i < points.size(); i++) {
			for (int j = i + 1; j < points.size(); j++) {
				double distance = Point.distance(points.get(i), points.get(j));
				if (distance < minDistance) {
					minDistance = distance;
					closestPair.clear();
					closestPair.add(points.get(i));
					closestPair.add(points.get(j));
				}
			}
		}

		return closestPair;
	}

	public static List<Point> planeSweep(List<Point> points) {
		
		Collections.sort(points, (p, q) -> Double.compare(p.x, q.x));
		List<Point> closestPair = new ArrayList<>();
		TreeMap<Double, Point> sss = new TreeMap<>();
		sss.put(points.get(0).y, points.get(0));
		sss.put(points.get(1).y, points.get(1));
		double minSoFar = Point.distance(points.get(0), points.get(1));
		closestPair.add(points.get(0));
		closestPair.add(points.get(1));
		int left = 0;
		int right = 2;

		while (right < points.size()) {
			if (points.get(left).x + minSoFar <= points.get(right).x) {
				sss.remove(points.get(left).x);
				left++;
			} else {
				SSResult result = sweepStatusCheck(sss, points.get(right), minSoFar);
				if (result.minDist < minSoFar) {
					minSoFar = result.minDist;
					closestPair.clear();
					closestPair.add(result.minPair.get(0));
					closestPair.add(result.minPair.get(1));
				}
				sss.put(points.get(right).y, points.get(right));
				right++;
			}
		}

		return closestPair;
	}
	
	private static SSResult sweepStatusCheck(TreeMap<Double, Point> sss, Point newPoint, double minSoFar) {
		double rangeMin = newPoint.y - minSoFar;
		double rangeMax = newPoint.y + minSoFar;
		Map<Double, Point> range = sss.subMap(rangeMin, false, rangeMax, false);
		List<Point> closestPair = new ArrayList<>();
		for (Map.Entry<Double, Point> entry : range.entrySet()) {
			double distance = Point.distance(entry.getValue(), newPoint);
			if (distance < minSoFar) {
				minSoFar = distance;
				closestPair.clear();
				closestPair.add(entry.getValue());
				closestPair.add(newPoint);
			}
		}
		return new SSResult(minSoFar, closestPair);
	}
	
	private static class SSResult {
		private SSResult(double minDist, List<Point> minPair) {
			this.minDist = minDist;
			this.minPair = minPair;
		}
		
		public double minDist;
		public List<Point> minPair;
	}
	
}	

