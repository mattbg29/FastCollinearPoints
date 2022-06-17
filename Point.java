/******************************************************************************
 *  This was completed as part of Princeton's Algorithms class, for more on the assignment,
 *  see this link:  https://coursera.cs.princeton.edu/algs4/assignments/collinear/specification.php
 *  The goal of the assignment is, given a collection of data points, identify those groups of
 *  4 or more points that are collinear, ie that form a line.
 *  Approximately half of the code below was supplied by the course, the rest written by me,
 *  to work in tandem with my own code in the other associated classes.
 ******************************************************************************/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw() {
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public double slopeTo(Point that) {
        if (this.y == that.y && this.x == that.x) {
            return Double.NEGATIVE_INFINITY;
        }
        if (this.y == that.y) {
            return 0.0;
        }
        if (this.x == that.x) {
            return Double.POSITIVE_INFINITY;
        }
        double slopeNow = (double) (that.y - this.y) / (that.x - this.x);
        return slopeNow;
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     */
    public int compareTo(Point that) {
        if (this.y < that.y) {
            return -1;
        }
        if (this.y == that.y && this.x < that.x) {
            return -1;
        }
        return 1;
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    private class SlopeOrder implements Comparator<Point> {
        public int compare(Point q1, Point q2) {
            if (q1.slopeTo(q2) == q2.slopeTo(Point.this)) {
                return 0;
            }
            if (q1.slopeTo(q2) < q2.slopeTo(Point.this)) {
                return -1;
            }
            return 1;
        }

    }

    public Comparator<Point> slopeOrder() {
        return new SlopeOrder();
    }


    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     */
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}
