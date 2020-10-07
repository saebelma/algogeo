package saebelma.algogeo;

import java.util.ArrayList;
import java.util.List;

public class RunnerDP extends Runner {
	
	// DATA
	private List<Point> points;
	private LineSegment dividingLine;
	private List<Point> leftOfLine = new ArrayList<>();
	private List<Point> rightOfLine = new ArrayList<>();
	private final int width = 1000;
	private final int height = 1000;
	private final int numberOfPoints = 1000;
	private DividePoints algorithm;
	
	// LAUNCH APPLICATON
	public static void main(String[] args) {
		launch(args);
	}
	
	// IMPLEMENTATION
	
	@Override
	protected String getTitle() {
		return "Dividing point according to line";
	}	
	@Override
	protected void setInput() {
		points = Point.random(width, height, numberOfPoints);
		dividingLine = LineSegment.dividingLine(width, height);
	}
	
	@Override
	protected void runAlgorithm() {
		algorithm = new DividePoints(points, dividingLine);
		algorithm.run();
		leftOfLine = algorithm.getLeftOfLine();
		rightOfLine = algorithm.getRightOfLine();
	}

	@Override
	protected void showOutput() {
		paintLineSegment(dividingLine, inputColor);
		paintPoints(rightOfLine, outputColor);
		paintPoints(leftOfLine, inputColor);
		paintPoint(dividingLine.end, intermediateColor, 10);
	}
}
