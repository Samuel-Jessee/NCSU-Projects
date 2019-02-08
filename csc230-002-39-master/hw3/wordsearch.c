/**
    @file wordsearch.c
    @author Samuel Jessee (sijessee)

    The wordsearch component reads a wordsearch grid file, prompts the user
    for words to search for, and calls functions to perform the search.
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include "search.h"

/** The number of rows in a grid, and the number of characters in a row. */
#define N 9

/** The number of expected arguments. */
#define EXPECTED_ARGS 2

/** The minimum length of a word to search for. */
#define MIN_WORD_LENGTH 2

/**
    Prints the wordsearch grid and prompts the user for a word.
    @param grid the wordsearch grid
*/
void printGrid( unsigned char grid[N][N] )
{
    int ch;
    for ( int i = 0; i < N; i++ ) {
        for ( int j = 0; j < N; j++ ) {
            ch = grid[i][j];
            printf( "%c ", ch );
        }
        printf( "\n" );
    }
    printf( "Word (or ! to quit)? " );
}

/**
    Opens the given file, and creates a wordsearch grid. Prints an error message if the file is
    improperly formated, or cannot be found. Calls the search method with a user provided word.
    @param argc number of arguments provided
    @param argv array of arguments
    @return Exit status for the program.
*/
int main( int argc, char *argv[] )
{
    FILE *fp;
    char usageMessage[] = "usage: wordsearch <grid_file>";
    if ( argc != EXPECTED_ARGS ) {
        fprintf( stderr, "%s", usageMessage );
        exit( 1 );
    } else {
        fp = fopen( argv[1], "r" );
        if ( !fp ) {
            fprintf( stderr, "Can't open file: %s\n%s", argv[1], usageMessage );
            exit( 1 );
        }
    }

    //wordsearch grid
    unsigned char grid[N][N];

    //current character
    int ch;

    char formatMessage[] = "Improperly formatted file.\n";

    //reads through grid from top to bottom
    for ( int i = 0; i < N; i++ ) {
        //reads through a row
        for ( int j = 0; j < N; j++) {
            //retrieves what should be a letter
            ch = fgetc( fp );
            if ( ch >= 'a' && ch <= 'z' ) {
                grid[i][j] = ch;
            } else if ( ch >= 'A' && ch <= 'Z' ) {
                grid[i][j] = tolower( ch );
            } else {
                printf( "%s", formatMessage );
                exit( EXIT_FAILURE );
            }
            //retrieves what should be a space
            ch = fgetc( fp );
            if ( ch != ' ' ) {
                printf( "%s", formatMessage );
                exit( EXIT_FAILURE );
            }
        }

        //checks for \n at end of row
        ch = fgetc( fp );
        if ( ch != '\n' ) {
            printf( "%s", formatMessage );
            exit( EXIT_FAILURE );
        }
    }

    //retrieves what should be EOF
    ch = fgetc( fp );
    if ( ch != EOF ) {
        printf( "%s", formatMessage );
        exit( EXIT_FAILURE );
    }

    char input[N+1];

    printGrid( grid );

    while ( ( scanf( "%9s", input) ) != EOF ) {
        if ( input[0] == '!' ) {
            exit ( EXIT_SUCCESS );
        }
        if ( strlen( input ) >= MIN_WORD_LENGTH ) {
            find( input, grid );
        }
        printGrid( grid );
    }
    exit ( EXIT_SUCCESS );
}