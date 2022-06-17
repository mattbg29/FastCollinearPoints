/* *****************************************************************************
 *  This was completed as part of Princeton's Algorithms class, for more on the assignment,
 *  see this link:  https://coursera.cs.princeton.edu/algs4/assignments/collinear/specification.php
 *  The goal of the assignment is, given a collection of data points, identify those groups of
 *  4 or more points that are collinear, ie that form a line.
 *  Nearly all of the code below is my own.
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {
    // for storing the sets of 4 or more points that are collinear.  We use ArrayLists since we do not know ahead of time how many sets there will be.
    private List<Point[]> collinear;
    // for counting how many unique sets of 4 or more collinear points exist
    private int uniqueCount;

    // we'll iterate through each point in the argument set of points, and for each point, we'll sort
    // by slopeOrder, since any points with the same slope to a specific point will form a line with that point
    // we then need to check each slope in the ordered list and, if 3 or more slopes match, then that is
    // indicative of 4 or more points on a line.  Once we reach the point at which the slope is
    // no longer equal, or the array ends, we store the set of points on the line in the collinear List
    // and repeat the process
    public FastCollinearPoints(Point[] points) {
        collinear = new ArrayList<Point[]>();
        uniqueCount = 0;
        // we'll store the slope of any set of points added to collinear to ensure we don't add the same set more than once.
        List<Double> added = new ArrayList<>();
        for (int i = 0; i < points.length; i++) {
            // we use Arrays.sort to sort array points by the method slopeOrder, defined in Point, relative to the ith point.
            Arrays.sort(points, 0, points.length, points[i].slopeOrder());
            for (int j = 0; j < points.length; j++) {
                int startLine = j;
                if (startLine + 2 < points.length) {
                    while (points[startLine + 1].slopeTo(points[startLine]) != points[startLine + 2]
                            .slopeTo(points[startLine]) || added
                            .contains(points[startLine + 1].slopeTo(points[startLine]))) {
                        if (startLine + 3 < points.length) {
                            startLine++;
                        }
                        else {
                            // need to explicitly break or else the above could attempt to check an element outside of the array
                            break;
                        }
                    }
                    int endLine = startLine + 2;
                    if (endLine + 1 < points.length) {
                        while (points[endLine].slopeTo(points[startLine]) == points[endLine + 1]
                                .slopeTo(points[startLine]) && endLine < points.length - 1) {
                            endLine++;
                            if (endLine + 1 >= points.length) {
                                break;
                            }
                        }
                    }
                    if (endLine - startLine >= 3) {
                        Point[] lineNow = new Point[endLine - startLine + 1];
                        for (int a = 0; a < lineNow.length; a++) {
                            lineNow[a] = points[startLine + a];
                        }
                        added.add(points[endLine].slopeTo(points[startLine]));
                        collinear.add(lineNow);
                        uniqueCount++;
                        j = points.length;
                    }
                }
            }
        }
    }

    // this enables outside classes to access the private variable uniqueCount
    public int numberOfSegments() {
        return uniqueCount;
    }

    // for each set of points in collinear, as determined by the constructor, we identify the
    // min and max point using the compareTo method defined in Point.  We then create a LineSegment
    // with said min and max and return an array of all such line segments.
    public LineSegment[] segments() {
        int numSegments = numberOfSegments();
        LineSegment[] resultArray = new LineSegment[numSegments];
        for (int segment = 0; segment < numSegments; segment++) {
            Point[] arrNow = collinear.get(segment);
            Point minPoint = arrNow[0];
            Point maxPoint = arrNow[0];
            for (int i = 1; i < arrNow.length; i++) {
                if (arrNow[i].compareTo(minPoint) < 0) {
                    minPoint = arrNow[i];
                }
                if (arrNow[i].compareTo(maxPoint) > 0) {
                    maxPoint = arrNow[i];
                }
            }
            LineSegment result = new LineSegment(minPoint, maxPoint);
            resultArray[segment] = result;
        }

        return resultArray;
    }

}
