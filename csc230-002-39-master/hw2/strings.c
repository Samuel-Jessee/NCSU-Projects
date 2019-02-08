/**
    @file strings.c
    @author Samuel Jessee (sijessee)

    This program reads text from a standard input, looking for single-quoted and
    double-quoted strings. It will print out a copy of the input text, with the strings
    highlighted in color.
*/

#include <stdlib.h>
#include <stdio.h>

/**
    The ESC constant.
*/
#define ESC 27

/**
    The failed exit constant.
*/
#define FAIL 100

/**
    The current character in the input.
*/
int ch;

/**
    Prints single-quote strings of characters in blue.
*/
void markSingle()
{
    putchar(ESC);
    putchar('[');
    putchar('3');
    putchar('4');
    putchar('m');
    putchar(ch);
    while ( (ch = getchar()) != '\'') {
        if ( ch == EOF) {
            putchar(ESC);
            putchar('[');
            putchar('0');
            putchar('m');
            exit(FAIL);
        } else {
            putchar(ch);
        }
    }
    putchar(ch);
    putchar(ESC);
    putchar('[');
    putchar('0');
    putchar('m');
    return;
}

/**
    Prints double-quote strings of characters in red.
*/
void markDouble()
{
    putchar(ESC);
    putchar('[');
    putchar('3');
    putchar('1');
    putchar('m');
    putchar(ch);
    while ( (ch = getchar()) != '\"') {
        if ( ch == EOF) {
            putchar(ESC);
            putchar('[');
            putchar('0');
            putchar('m');
            exit(FAIL);
        } else {
            putchar(ch);
        }
    }
    putchar(ch);
    putchar(ESC);
    putchar('[');
    putchar('0');
    putchar('m');
    return;
}

/**
    Reads and prints each character in the input until it reaches a single
    or double quote, at which point it calls on another function.
    @return Exit status for the program.
*/
int main()
{
    while ( (ch = getchar()) != EOF ) {
        if (ch == '\'') {
            markSingle();
        } else if (ch == '\"') {
            markDouble();
        } else {
            putchar(ch);
        }
    }
    return EXIT_SUCCESS;
}