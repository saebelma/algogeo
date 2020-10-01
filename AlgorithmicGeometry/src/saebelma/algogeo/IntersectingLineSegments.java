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
	public static List<Point> planeSweep(List<LineSegment> lineSegments) {

		// Sweep status structure with dynamic line segment comparator (intersection
		// with sweep line)
		SSSOrder sssOrder = new SSSOrder();
		TreeSet<LineSegment> sss = new TreeSet<>(sssOrder);

		// Event queue with event comparator
		TreeSet<Event> events = new TreeSet<>(new EventOrder());

		// // List of sweep lines (for visualization)
		// List<LineSegment> sweepLines = new ArrayList<>();

		// List of intersections
		List<Point> intersections = new ArrayList<>();

		// Add segment end point events
		int lineTagCounter = 0;
		for (LineSegment ls : lineSegments) {
			ls.setTag("LS" + lineTagCounter++);
			events.add(new Event(ls.start.x, EventType.LEFT, ls));
			events.add(new Event(ls.end.x, EventType.RIGHT, ls));
		}
		// System.out.println("Initial ES" + eventsToString(events));

		// Process event queue
		Event currentEvent;
		double currentTime = 0, lastTime, nextTime;
		LineSegment currentSegment1, currentSegment2, neighboringSegment1, neighboringSegment2;
		Point intersection1, intersection2, higherIntersection;

		while (!events.isEmpty()) {

			currentEvent = events.pollFirst();

			// System.out.println("----------------------------");
			// System.out.println("Processing event "+ currentEvent);

			lastTime = currentTime;
			currentTime = currentEvent.time;

			// sweepLines.add(new LineSegment(new Point(currentTime, 0), new
			// Point(currentTime, 1000)));

			switch (currentEvent.type) {

			case INTERSECTION:

				// Get segments and report intersection
				currentSegment1 = currentEvent.ls1;
				currentSegment2 = currentEvent.ls2;
				intersections.add(LineSegment.intersection(currentSegment1, currentSegment2));

				// Set time to point before intersection
				double beforeIntersection = (lastTime + currentTime) / 2;
				sssOrder.setTime(beforeIntersection);

				// Get neighboring segments, if any
				if (sssOrder.compare(currentSegment1, currentSegment2) < 0) {
					neighboringSegment1 = sss.lower(currentSegment1);
					neighboringSegment2 = sss.higher(currentSegment2);
				} else {
					neighboringSegment1 = sss.higher(currentSegment1);
					neighboringSegment2 = sss.lower(currentSegment2);
				}

				// Check for intersection in switched order (= after intersection), add
				// intersection events
				if (neighboringSegment2 != null) {
					intersection1 = LineSegment.intersection(currentSegment1, neighboringSegment2);
					if (intersection1 != null && intersection1.x > currentTime)
						events.add(new Event(intersection1.x, EventType.INTERSECTION, currentSegment1,
								neighboringSegment2));
				}
				if (neighboringSegment1 != null) {
					intersection2 = LineSegment.intersection(currentSegment2, neighboringSegment1);
					if (intersection2 != null && intersection2.x > currentTime)
						events.add(new Event(intersection2.x, EventType.INTERSECTION, currentSegment2,
								neighboringSegment1));
				}

				// Remove segments
				sss.remove(currentSegment1);
				sss.remove(currentSegment2);

				// Get time of next event and set time to point after intersection
				nextTime = events.first().time;
				double afterIntersection = (currentTime + nextTime) / 2;
				sssOrder.setTime(afterIntersection);

				// Re-add segments
				sss.add(currentSegment1);
				sss.add(currentSegment2);

				break;

			case LEFT:

				// Get current segment, set event time
				currentSegment1 = currentEvent.ls1;
				sssOrder.setTime(currentTime);

				// Add segment to sss
				sss.add(currentSegment1);

				// Get neighboring segments, add intersection events
				neighboringSegment1 = sss.lower(currentSegment1);
				if (neighboringSegment1 != null) {
					intersection1 = LineSegment.intersection(neighboringSegment1, currentSegment1);
					if (intersection1 != null && intersection1.x > currentTime)
						events.add(new Event(intersection1.x, EventType.INTERSECTION, neighboringSegment1,
								currentSegment1));
				}
				neighboringSegment2 = sss.higher(currentSegment1);
				if (neighboringSegment2 != null) {
					higherIntersection = LineSegment.intersection(neighboringSegment2, currentSegment1);
					if (higherIntersection != null && higherIntersection.x > currentTime)
						events.add(new Event(higherIntersection.x, EventType.INTERSECTION, currentSegment1,
								neighboringSegment2));
				}

				break;

			case RIGHT:

				// Get current segment, set event time
				currentSegment1 = currentEvent.ls1;
				sssOrder.setTime(currentTime);

				// Get neighboring segments, add intersection event
				neighboringSegment1 = sss.lower(currentSegment1);
				neighboringSegment2 = sss.higher(currentSegment1);
				if (neighboringSegment1 != null && neighboringSegment2 != null) {
					intersection1 = LineSegment.intersection(neighboringSegment1, neighboringSegment2);
					if (intersection1 != null && intersection1.x > currentTime)
						events.add(new Event(intersection1.x, EventType.INTERSECTION, neighboringSegment1,
								neighboringSegment2));
				}

				// Remove segment from sss
				sss.remove(currentSegment1);

				break;
			}
			// System.out.println("SSS after processing " + sss);
			// System.out.println("ES after processing " + eventsToString(events));
		}

		return intersections;
	}

	private static class SSSOrder implements Comparator<LineSegment> {
		double time;

		@Override
		public int compare(LineSegment ls1, LineSegment ls2) {
			if (ls1.equals(ls2)) {
				return 0;
			} else {
				double y1 = LineSegment.getYAtX(ls1, time);
				double y2 = LineSegment.getYAtX(ls2, time);
				return Double.compare(y1, y2);
			}
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
			return ls2 == null ? "[" + round(time) + ", " + type + ", " + ls1 + "]"
					: "[" + round(time) + ", " + type + ", " + ls1 + ", " + ls2 + "]";
		}

		// Events are identical if they have the same type and same participating
		// segment(s)
		@Override
		public boolean equals(Object o) {

			// Check if it's an event at all
			if (!(o instanceof Event)) {
				return false;
			}

			// Type cast to event so we can compare structure
			Event e = (Event) o;

			// Compare the tags
			if (this.type == e.type
					&& ((this.ls1 == e.ls1 && this.ls2 == e.ls2) || (this.ls1 == e.ls2 && this.ls2 == e.ls1))) {
				return true;
			} else {
				return false;
			}
		}
	}

	private enum EventType {
		LEFT, RIGHT, INTERSECTION
	}

	private static class EventOrder implements Comparator<Event> {

		@Override
		public int compare(Event e1, Event e2) {
			if (e1.equals(e2)) {
				return 0;
			} else {
				return Double.compare(e1.time, e2.time);
			}
		}

	}

	public static double round(double number) {
		return (double) ((int) ((number * 100) + .5)) / 100;
	}

	public static String eventsToString(TreeSet<Event> events) {
		Event[] eventsArray = events.toArray(new Event[0]);
		Arrays.sort(eventsArray, new EventOrder());
		return Arrays.toString(eventsArray);
	}
}
