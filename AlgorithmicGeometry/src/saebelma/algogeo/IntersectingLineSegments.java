package saebelma.algogeo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.NavigableSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.TreeSet;

public class IntersectingLineSegments {

	// Brute force algorithm for intersecting line segments
	// Cost is n * (n - 1) / 2 calculations plus reporting k intersections
	// => O(n^2)
	public static List<Point> bruteForce(List<LineSegment> lineSegments) {
		List<Point> intersections = new ArrayList<>();
		for (int i = 0; i < lineSegments.size(); i++) {
			for (int j = i + 1; j < lineSegments.size(); j++) {
				Point intersection = LineSegment.intersection(lineSegments.get(i), lineSegments.get(j));
				if (intersection != null)
					intersections.add(intersection);
			}
		}
		return intersections;
	}

	// Plane sweep algorithm for intersecting line segments
	// Cost should be O(n * log n) ...
	public static List<LineSegment> planeSweep(List<LineSegment> lineSegments) {

		// Sweep status structure with custom comparator
		SSSOrder sssOrder = new SSSOrder();
		NavigableSet<LineSegment> sss = new TreeSet<>(sssOrder);

		// Event queue
		Queue<Event> events = new PriorityQueue<>(new EventOrder());

		// List of sweep lines
		List<LineSegment> sweepLines = new ArrayList<>();

		// Add segment end point events
		int lineTagCounter = 0;
		for (LineSegment ls : lineSegments) {
			ls.setTag("LS" + lineTagCounter++);
			events.add(new Event(ls.start.x, EventType.LEFT, ls));
			events.add(new Event(ls.end.x, EventType.RIGHT, ls));
		}
		System.out.println("Initial ES");
		Event[] eventsArray = events.toArray(new Event[0]);
		Arrays.sort(eventsArray, new EventOrder());
		System.out.println(Arrays.toString(eventsArray));

		// Process event queue
		Event currentEvent;
		double currentTime, deltaTime;
		LineSegment currentSegment, lowerSegment, higherSegment;
		Point intersection, lowerIntersection, higherIntersection;
		

		while (!events.isEmpty()) {
			
			currentEvent = events.poll();
			
			currentTime = currentEvent.time;
			sssOrder.setTime(currentTime);
			
			sweepLines.add(new LineSegment(new Point(currentTime, 0), new Point(currentTime, 1000)));
			
			switch (currentEvent.type){
			
			case INTERSECTION:
				lowerSegment = currentEvent.ls1;
				higherSegment = currentEvent.ls2;
				sss.remove(lowerSegment);
				sss.remove(higherSegment);
								
				deltaTime = (events.peek().time - currentTime) / 2;
				
				sssOrder.setTime(currentTime + deltaTime);
				sss.add(lowerSegment);
				sss.add(higherSegment);
				sssOrder.setTime(currentTime);
				
				break;
				
			case LEFT:
				
				currentSegment = currentEvent.ls1;
				sss.add(currentSegment);
				
				lowerSegment = sss.lower(currentSegment);
				if (lowerSegment != null) {
					lowerIntersection = LineSegment.intersection(lowerSegment, currentSegment);
					if (lowerIntersection != null)
						events.add(new Event(lowerIntersection.x, EventType.INTERSECTION, lowerSegment,
								currentSegment));
				}
				
				higherSegment = sss.higher(currentSegment);
				if (higherSegment != null) {
					higherIntersection = LineSegment.intersection(higherSegment, currentSegment);
					if (higherIntersection != null)
						events.add(new Event(higherIntersection.x, EventType.INTERSECTION,
								currentSegment, higherSegment));
				}
				
				break;
				
			case RIGHT:
				currentSegment = currentEvent.ls1;
				
				lowerSegment = sss.lower(currentSegment);
				higherSegment = sss.higher(currentSegment);
				if (lowerSegment != null && higherSegment != null) {
					intersection = LineSegment.intersection(lowerSegment, higherSegment);
					if (intersection != null)
						events.add(new Event(intersection.x, EventType.INTERSECTION, lowerSegment, higherSegment));
				}
				boolean removalSuccessful = sss.remove(currentSegment);
				System.out.println("Removed " + currentSegment.tag + " successfully? " + removalSuccessful);
				
				break;
			}
			System.out.println("SSS at time " + Math.rint(currentTime));
			System.out.println(sss);
			System.out.println("ES at time " + Math.rint(currentTime));
			eventsArray = events.toArray(new Event[0]);
			Arrays.sort(eventsArray, new EventOrder());
			System.out.println(Arrays.toString(eventsArray));
		}
		
		return sweepLines;
	}

	private static class SSSOrder implements Comparator<LineSegment> {
		double time;

		@Override
		public int compare(LineSegment ls1, LineSegment ls2) {
			double y1 = LineSegment.getYAtX(ls1, time);
			double y2 = LineSegment.getYAtX(ls2, time);
			return Double.compare(y1, y2);
		}

		public void setTime(double x) {
			this.time = x;
		}
	}

	private static class Event {
		double time;
		EventType type;
		LineSegment ls1, ls2;

		private Event(double time, EventType type, LineSegment ls) {
			this.time = time;
			this.type = type;
			this.ls1 = ls;
		}

		private Event(double time, EventType type, LineSegment ls1, LineSegment ls2) {
			this(time, type, ls1);
			this.ls2 = ls2;
		}

		@Override
		public String toString() {
			return ls2 == null ? "[" + Math.rint(time) + ", " + type + ", " + ls1 + "]"
					: "[" + time + ", " + type + ", " + ls1 + ", " + ls2 + "]";
		}
	}

	private enum EventType {
		LEFT, RIGHT, INTERSECTION
	}

	private static class EventOrder implements Comparator<Event> {

		@Override
		public int compare(Event e1, Event e2) {
			return Double.compare(e1.time, e2.time);
		}

	}
}
