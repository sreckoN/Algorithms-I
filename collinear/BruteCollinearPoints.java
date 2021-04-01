/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private LineSegment[] lineSegments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (!isInputArrayOk(points)) throw new IllegalArgumentException();
        findSegments(Arrays.copyOf(points, points.length));
    }

    // check input array
    private boolean isInputArrayOk(Point[] points) {
        if (points == null) return false;
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) return false;
            for (int j = i + 1; j < points.length; j++) {
                if (points[j] == null || points[i].compareTo(points[j]) == 0) return false;
            }
        }
        return true;
    }

    // find and create the line segments
    private void findSegments(Point[] points) {
        ArrayList<LineSegment> segments = new ArrayList<>();
        int numberOfPoints = points.length;
        Arrays.sort(points);
        for (int i = 0; i < numberOfPoints - 3; i++) {
            for (int j = i + 1; j < numberOfPoints - 2; j++) {
                for (int k = j + 1; k < numberOfPoints - 1; k++) {
                    if (points[i].slopeTo(points[j]) != points[i].slopeTo(points[k]))
                        continue;
                    for (int t = k + 1; t < numberOfPoints; t++) {
                        if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[k]) &&
                                points[i].slopeTo(points[j]) == points[i].slopeTo(points[t]))
                            segments.add(new LineSegment(points[i], points[t]));
                    }
                }
            }
        }
        lineSegments = segments.toArray(new LineSegment[segments.size()]);
    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return lineSegments.clone();
    }
}