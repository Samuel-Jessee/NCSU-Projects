/**
    @file venn.c
    @author Samuel Jessee (sijessee)

    This program draws a venn diagram with a red, green, and blue circle,
    given the center locations for the three circles.
*/

#include <math.h>
#include <stdlib.h>
#include <stdio.h>

/**
    Error constant.
*/
#define FAIL 100

/** Width and height of the image. */
#define ISIZE 100

/** Radius of the circles we're drawing. */
#define RADIUS 45

/** Brightest color intensity, for the center of a circle */
#define CMAX 255

/** Half the color intensity, for the edge of a circle */
#define CHALF 128

/**
    Required number of arguments.
*/
#define ARGUMENT_NUMBER 6

/**
    Value for no color.
*/
#define NO_COLOR 0

/**
    Computes the distance between two points and returns the calculated distance.
    @param x1 x coordinate of first point.
    @param y1 y coordinate of first point.
    @param x2 x coordinate of second point.
    @param y2 y coordinate of second point.
    @return distance between the two points.
*/
double computeDistance( int x1, int y1, int x2, int y2 )
{
    return sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
}

/**
    Given a distance between 0 and 45, this function returns an intensity value
    used to color the pixels inside a circle.
    @param dist distance from center of the circle.
    @return color intensity.
*/
int distanceToShade( double dist )
{
    if (dist > RADIUS) {
        return NO_COLOR;
    } else if (dist < 0) {
        return CMAX;
    } else {
        double a = CHALF - CMAX;
        double b = RADIUS;
        double slope = a/b;
        double shade = slope*dist + CMAX;
        return round(shade);
    }
}

/**
    Reads input and prints three colored circles in a venn diagram.
    @return Exit status for the program.
*/
int main()
{
    int rx;
    int ry;
    int gx;
    int gy;
    int bx;
    int by;
    int matched;
    matched = scanf("%d%d%d%d%d%d", &rx, &ry, &gx, &gy, &bx, &by);
    if (matched != ARGUMENT_NUMBER) {
        printf("Invalid input\n");
        return FAIL;
    }
    printf("P3\n%d %d\n%d\n", ISIZE, ISIZE, CMAX);
    for (int i = 0; i < ISIZE; i++) {
        for (int j = 0; j < ISIZE; j++) {
            printf("%3d %3d %3d ", distanceToShade(computeDistance(j, i, rx, ry)),
                                   distanceToShade(computeDistance(j, i, gx, gy)),
                                   distanceToShade(computeDistance(j, i, bx, by)));
        }
        printf("\n");
    }
  return EXIT_SUCCESS;
}