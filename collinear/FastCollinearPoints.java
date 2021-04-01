/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class FastCollinearPoints {

    private LineSegment[] lineSegments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (!isInputArrayOk(points)) throw new IllegalArgumentException();
        findSegments(Arrays.copyOf(points, points.length));
    }

    // checks the input array
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

    // finds and creates line segments
    private void findSegments(Point[] points) {
            Arrays.sort(points);
            final List<LineSegment> segments = new LinkedList<>();
            final int numOfPoints = points.length;
            for (int i = 0; i < numOfPoints; i++) {
                Point point = points[i];
                Point[] pointsBySlope = points.clone();
                Arrays.sort(pointsBySlope, point.slopeOrder());
                int x = 1;
                while (x < numOfPoints) {
                    LinkedList<Point> collinear = new LinkedList<>();
                    double slope = point.slopeTo(pointsBySlope[x]);
                    do {
                        collinear.add(pointsBySlope[x++]);
                    } while (x < numOfPoints && point.slopeTo(pointsBySlope[x]) == slope);
                    if (collinear.size() >= 3 && point.compareTo(collinear.peek()) < 0) {
                        segments.add(new LineSegment(point, collinear.removeLast()));
                    }
                }
            }
            lineSegments = segments.toArray(new LineSegment[0]);
    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return lineSegments.clone();
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}