package saebelma.algogeo;

import java.util.ArrayList;
import java.util.List;

public class RunnerCPPS extends Runner {
	
	// DATA
	private List<Point> points = new ArrayList<>();
	private List<Point> closestPair;
	private final int numberOfPoints = 10000;
	
	// LAUNCH APPLICATON
	public static void main(String[] args) {
		launch(args);
	}
	
	// IMPLEMENTATION
	
	@Override
	protected String getTitle() {
		return "Closest pair of points plane sweep";
	}	
	@Override
	protected void setInput() {
		points = Point.random(panelWidth, panelHeight, numberOfPoints);	
	}
	
	@Override
	protected void runAlgorithm() {
		closestPair = ClosestPair.planeSweep(points);
	}

	@Override
	protected void showOutput() {
		paintPoints(closestPair, outputColor, 10);
		paintPoints(points, inputColor);
	}
}
