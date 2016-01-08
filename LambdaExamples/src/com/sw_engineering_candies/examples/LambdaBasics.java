/*
 * Copyright (C) 2016, Markus Sprunck <sprunck.markus@gmail.com>
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * - Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * - Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * - The name of its contributor may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package com.sw_engineering_candies.examples;


import java.awt.*;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LambdaBasics {

    private static final List<Point> points = createPoints();

    private static final List<Point> pointsEmpty = new ArrayList<>();

    private static List<Point> createPoints() {
        List<Point> result = new ArrayList<>();
        result.add(new Point(-4, -8));
        result.add(new Point(-2, 9));
        result.add(new Point(-1, -8));
        result.add(new Point(0, -7));
        result.add(new Point(1, 1));
        result.add(new Point(2, 3));
        result.add(new Point(2, 3));
        result.add(new Point(2, -2));
        result.add(new Point(4, -1));
        return result;
    }

    /*
     * Helper method that formats the output
     */
    private static void printFormated(Point p) {
        System.out.print("[" + p.x + ", " + p.y + "] ");
    }

    private static void calculateSum() {

        System.out.println("\nCalculate sum of all x-coordinates with reduce method");
        int result = points.stream() //
                .mapToInt(p -> p.x) // map the x value of the point to IntStream
                .peek(x -> System.out.print(x + " ")) // trace the values
                .reduce(0, (x1, x2) -> x1 + x2); // initial value is needed
        System.out.print("\nsum=" + result);

        System.out.println("\n\nCalculate sum of all x-coordinates with reduce and ifPresent method");
        points.stream() //
                .mapToInt(p -> p.x) // map the x value of the point to IntStream
                .peek(x -> System.out.print(x + " ")) // trace the values
                .reduce((x1, x2) -> x1 + x2) // no initial value is used
                .ifPresent(s -> System.out.print("\nsum=" + s)); // in the case there is no empty list

        System.out.println("\n\nCalculate sum of all x-coordinates with steam method sum");
        result = points.stream() //
                .mapToInt(p -> p.x) // map the x value of the point to IntStream
                .peek(x -> System.out.print(x + " ")) // trace the values
                .sum(); // standard IntStream method (like min, max, average, count)
        System.out.print("\nsum=" + result);

        System.out.println("\n\nCalculate sum of all x-coordinates with reduce method (empty list)");
        result = pointsEmpty.stream() //
                .mapToInt(p -> p.x) // map the x value of the point to IntStream
                .peek(x -> System.out.print(x + " ")) // trace the values
                .reduce(0, (x1, x2) -> x1 + x2); // initial value is needed
        System.out.print("\nsum=" + result);

        System.out.println("\n\nCalculate sum of all x-coordinates with reduce and ifPresent method (empty list)");
        pointsEmpty.stream() //
                .mapToInt(p -> p.x) // map the x value of the point to IntStream
                .peek(x -> System.out.print(x + " ")) // trace the values
                .reduce((x1, x2) -> x1 + x2) // no initial value is used
                .ifPresent(s -> System.out.print("\nsum=" + s)); // in the case there is no empty list

        System.out.println("\n\nCalculate sum of all x-coordinates with steam method sum (empty list)");
        result = pointsEmpty.stream() //
                .mapToInt(p -> p.x) // map the x value of the point to IntStream
                .peek(x -> System.out.print(x + " ")) // trace the values
                .sum(); // standard IntStream method (like min, max, average, count)
        System.out.print("\nsum=" + result);
    }

    private static void printFormated() {
        
        System.out.println("\nStandard in Java to print all elements of a list.");
        for (Point point : points) {
            System.out.print(point);
        }

        System.out.println("\n\nForEach and Method Reference to print all elements of a list.");
        points.forEach(System.out::print);

        System.out.println("\n\nForEach and Lambda Expression to print all elements of a list with special format.");
        points.forEach(p -> printFormated(p));

        System.out.println("\n\nForEach and Method Reference to print all elements of a list with special format.");
        points.forEach(LambdaBasics::printFormated);

    }

    private static void filter() {
        
        System.out.println("\nFilter all points positive in x with Lambda Expression.");
        points.forEach(p -> {
            if (p.x > 0) {
                printFormated(p);
            }
        });

        System.out.println("\n\nFilter all points positive in x with filter method.");
        points.stream().filter(p -> p.x > 0).forEach(LambdaBasics::printFormated);

        System.out.println("\n\nFilter distinct points positive in x with filter method.");
        points.stream().filter(p -> p.x > 0).distinct().forEach(LambdaBasics::printFormated);
    }

    private static void addPoint() {

        System.out.println("\nPrint original list.");
        List<Point> points = createPoints();
        points.stream().forEach(LambdaBasics::printFormated);

        System.out.println(
                "\n\nAdd point to original list in the case the x value is equals two. This implemenation is wrong.");
        points = createPoints();
        try {
            points.stream() // modify original list
                    .filter(p -> p.x == 2) // just some points
                    .map(p -> new Point(100 * p.x, 10 * p.y)) // create a new point
                    .forEach(points::add); // add new point to list

            points.forEach(LambdaBasics::printFormated); // print results

        } catch (ConcurrentModificationException e) {
            System.out.println(e.toString());
        }

        System.out.println(
                "\nAdd point to original list in the case the x value is equals two. This implemenation is correct.");
        points = createPoints();
        Stream<Point> pointsResults = Stream.concat(//
                points.stream(), // copy original stream
                points.stream() // add new points to list
                        .filter(p -> p.x == 2) // just some points
                        .map(p -> new Point(100 * p.x, 10 * p.y))); // create a new point

        pointsResults.forEach(LambdaBasics::printFormated); // print results

    }

    private static void collect() {

        System.out.println("\nPrint original list.");
        points.stream().forEach(LambdaBasics::printFormated);

        System.out.println("\n\nUse Collector to store all points with positive x into a new List.");
        List<Point> result = points.stream() //
                .filter(p -> p.getX() > 0) //
                .collect(Collectors.toCollection(ArrayList::new));
        result.forEach(LambdaBasics::printFormated); // print results

        System.out.println("\n\nUse Collector to create comma separated string");
        String resultString = points.stream() //
                .mapToInt(p -> p.x) // extract x value
                .mapToObj(Integer::toString) // create String with integer value
                .collect(Collectors.joining(", ")); // create string
        System.out.println(resultString);
    }

    public static void main(String... args) {
        System.out.println("### LambdaBasics ######################################### | START");
        printFormated();

        System.out.println("\n\n### calculate sum ### ");
        calculateSum();

        System.out.println("\n\n### filter ###");
        filter();

        System.out.println("\n\n### add point ###");
        addPoint();

        System.out.println("\n\n### collect ###");
        collect();

        System.out.println("\n### LambdaBasics ######################################### | END");

    }
}
