/**
    @file search.c
    @author Samuel Jessee (sijessee)

    The search component is responsible for finding words in the wordsearch
    grid, and updating the grid if a word is found.
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include "search.h"

/** number representing a direction */
#define R 0

/** number representing a direction */
#define UR 1

/** number representing a direction */
#define U 2

/** number representing a direction */
#define UL 3

/** number representing a direction */
#define L 4

/** number representing a direction */
#define DL 5

/** number representing a direction */
#define D 6

/** number representing a direction */
#define DR 7

/** Max length of a word */
#define MAX_SIZE 9

/**
    This function starts with the character at the intersection of row and col
    and begins looking in the specified direction for the word.
    @param word word to search for
    @param grid wordsearch grid
    @param row row to start from
    @param col column to start from
    @param direction direction to search in
    @return 1 if word found, 0 if not, -1 if error
*/
int matches( char word[], unsigned char grid[MAX_SIZE][MAX_SIZE],
             int row, int col, int direction )
{
    if ( direction < R || direction > DR ) {
        return -1;
    }
    size_t length = strlen(word);
    if ( direction == U || direction == UR || direction == UL ) {
        if ( length > row + 1) {
            return -1;
        }
    }
    if ( direction == D || direction == DR || direction == DL ) {
        if ( length > MAX_SIZE - row ) {
            return -1;
        }
    }
    if ( direction == R || direction == UR || direction == DR ) {
        if ( length > MAX_SIZE - col ) {
            return -1;
        }
    }
    if ( direction == L || direction == UL || direction == DL ) {
        if ( length > col + 1) {
            return -1;
        }
    }

    //search direction
    if ( direction == R ) {
        for ( int i = 0; i < length; i++ ) {
            int x = col + i;
            if ( tolower( word[i] ) != tolower( grid[row][x] ) ) {
                return 0;
            }
        }
        for ( int i = 0; i < length; i++ ) {
            int x = col + i;
            grid[row][x] = toupper( grid[row][x] );
        }
        return 1;
    }

    if ( direction == UR ) {
        for ( int i = 0; i < length; i++ ) {
            int x = col + i;
            int y = row - i;
            if ( tolower( word[i] ) != tolower( grid[y][x] ) ) {
                return 0;
            }
        }
        for ( int i = 0; i < length; i++ ) {
            int x = col + i;
            int y = row - i;
            grid[y][x] = toupper( grid[y][x] );
        }
        return 1;
    }

    if ( direction == U ) {
        for ( int i = 0; i < length; i++ ) {
            int y = row - i;
            if ( tolower( word[i] ) != tolower( grid[y][col] ) ) {
                return 0;
            }
        }
        for ( int i = 0; i < length; i++ ) {
            int y = row - i;
            grid[y][col] = toupper( grid[y][col] );
        }
        return 1;
    }

    if ( direction == UL ) {
        for ( int i = 0; i < length; i++ ) {
            int x = col - i;
            int y = row - i;
            if ( tolower( word[i] ) != tolower( grid[y][x] ) ) {
                return 0;
            }
        }
        for ( int i = 0; i < length; i++ ) {
            int x = col - i;
            int y = row - i;
            grid[y][x] = toupper( grid[y][x] );
        }
        return 1;
    }

    if ( direction == L ) {
        for ( int i = 0; i < length; i++ ) {
            int x = col - i;
            if ( tolower( word[i] ) != tolower( grid[row][x] ) ) {
                return 0;
            }
        }
        for ( int i = 0; i < length; i++ ) {
            int x = col - i;
            grid[row][x] = toupper( grid[row][x] );
        }
        return 1;
    }

    if ( direction == DL ) {
        for ( int i = 0; i < length; i++ ) {
            int x = col - i;
            int y = row + i;
            if ( tolower( word[i] ) != tolower( grid[y][x] ) ) {
                return 0;
            }
        }
        for ( int i = 0; i < length; i++ ) {
            int x = col - i;
            int y = row + i;
            grid[y][x] = toupper( grid[y][x] );
        }
        return 1;
    }

    if ( direction == D ) {
        for ( int i = 0; i < length; i++ ) {
            int y = row + i;
            if ( tolower( word[i] ) != tolower( grid[y][col] ) ) {
                return 0;
            }
        }
        for ( int i = 0; i < length; i++ ) {
            int y = row + i;
            grid[y][col] = toupper( grid[y][col] );
        }
        return 1;
    }

    if ( direction == DR ) {
        for ( int i = 0; i < length; i++ ) {
            int x = col + i;
            int y = row + i;
            if ( tolower( word[i] ) != tolower( grid[y][x] ) ) {
                return 0;
            }
        }
        for ( int i = 0; i < length; i++ ) {
            int x = col + i;
            int y = row + i;
            grid[y][x] = toupper( grid[y][x] );
        }
        return 1;
    }
    return -1;
}

/**
    This function finds the target word in the wordsearch. If the word is found,
    the function updates the letters in the grid to uppercase for the word and
    returns 1. If not found, the function returns 0.
    @param word null terminated string of at most 9 characters
    @param grid wordsearch grid
    @return 1 if word is found, 0 if not
*/
int find( char word[], unsigned char grid[MAX_SIZE][MAX_SIZE] )
{
    int found = 0;
    for ( int i = 0; i < MAX_SIZE; i++ ) {
        for ( int j = 0; j < MAX_SIZE; j++ ) {
            for ( int k = 0; k <= DR; k++ ) {
                if ( matches(word, grid, i, j, k) == 1 ) {
                    found = 1;
                }
            }
        }
    }
    return found;
}