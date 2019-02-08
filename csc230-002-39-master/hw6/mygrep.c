/**
  @file mygrep.c
  @author Samuel Jessee (sijessee)

  The mygrep.c component contains the main() function.  It's responsible for
  handling command-line arguments, parsing the regular expression and matching
  it against lines from the input.
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "pattern.h"

#include <assert.h>

/** The first possible number of arguments. */
#define ARGS1 2

/** The second possible number of arguments. */
#define ARGS2 3

/** Index of the pattern argument. */
#define PATTERN 1

/** Index of the filename argument. */
#define FILENAME 2

/** Starting capacity to use for memory allocation. */
#define CAP 50

/**
  Return true if the given character is ordinary, if it should just
  match occurrences of itself.  This returns false for metacharacters
  like '*' that control how patterns are matched.

  @param c Character that should be evaluated as ordinary or special.
  @return True if c is not special.
*/
static bool ordinary( char c )
{
  if ( strchr( ".^$*?+|()[{", c ) ) {
    return false;
  }
  return true;
}

/**
  Print the appropriate error message for an invalid pattern and
  exit unsuccessfully.
*/
static void invalidPattern()
{
  fprintf( stderr, "Invalid pattern\n" );
  exit( EXIT_FAILURE );
}

/**
  Print the appropriate error message for invalid arguments and
  exit unsuccessfully.
*/
static void invalidArguments()
{
  fprintf( stderr, "usage: mygrep <pattern> [input-file.txt]\n" );
  exit( EXIT_FAILURE );
}

/**
  Print the appropriate error message for an invalid filename and
  exit unsuccessfully.  The filename is checked before the pattern.

  @param filename The provided filename.
*/
static void invalidFilename( char *filename )
{
  fprintf( stderr, "Can't open input file: %s\n", filename );
  exit( EXIT_FAILURE );
}

// Documented later in this code.
static Pattern *parseAlternation( char *str, int *pos );

/**
  Parse regular expression syntax with the highest precedence,
  individual, ordinary symbols, start and end anchors, character
  classes and patterns surrounded by parentheses.

  @param str The string being parsed.
  @param pos A pass-by-reference value for the location in str being parsed,
             increased as characters from str are parsed.
  @return a dynamically allocated representation of the pattern for the next
          portion of str.
*/
static Pattern *parseAtomicPattern( char *str, int *pos )
{
  if ( ordinary( str[ *pos ] ) ) {
    return makeSymbolPattern( str[ (*pos)++ ] );
  } else if ( str[ *pos ] == '.' ) {
    return makePeriodPattern( str[ (*pos)++ ] );
  } else if ( str[ *pos ] == '^' ) {
    return makeStartAnchorPattern( str[ (*pos)++ ] );
  } else if ( str[ *pos ] == '$' ) {
    return makeEndAnchorPattern( str[ (*pos)++ ] );
  } else if ( str[ *pos ] == '[' ) {
    (*pos)++;
    char ch;
    int capacity = CAP;
    int size = 0;
    char *line = (char *)malloc( capacity + 1 );

    while ( ( ch = str[ *pos ] ) != ']' ) {
      if ( !str[ *pos ] ) {
        invalidPattern();
        return NULL;
      }
      if ( size >= capacity ) {
        capacity *= 2;
        line = (char *)realloc( line, capacity + 1 );
      }
      line[ size ] = ch;
      size++;
      (*pos)++;
    }
    line[ size ] = '\0';
    (*pos)++;
    return makeCharacterClassPattern( size, line );
  } else if ( str[ *pos ] == '(' ) {
    (*pos)++;
    Pattern *p = parseAlternation( str, pos );
    if ( str[ *pos ] == ')' ) {
      (*pos)++;
      return p;
    }
  }
  invalidPattern();
  return NULL;
}

/**
  Parse regular expression syntax with the second-highest precedence,
  a pattern, p, optionally followed by one or more repetition syntax like
  '*' or '+'.  If there's no repetition syntax, it just returns the pattern
  object for p.

  @param str The string being parsed.
  @param pos A pass-by-reference value for the location in str being parsed,
             increased as characters from str are parsed.
  @return a dynamically allocated representation of the pattern for the next
          portion of str.
*/
static Pattern *parseRepetition( char *str, int *pos )
{
  Pattern *p = parseAtomicPattern( str, pos );

  if ( str[ *pos ] == '*' ) {
    p = makeZeroPlusPattern( p );
    (*pos)++;
  } else if ( str[ *pos ] == '+' ) {
    p = makeOnePlusPattern( p );
    (*pos)++;
  } else if ( str[ *pos ] == '?' ) {
    p = makeZeroOnePattern( p );
    (*pos)++;
  }
  return p;
}

/**
  Parse regular expression syntax with the third-highest precedence,
  one pattern, p, (optionally) followed by additional patterns
  (concatenation).  If there are no additional patterns, it just
  returns the pattern object for p.

  @param str The string being parsed.
  @param pos A pass-by-reference value for the location in str being parsed,
             increased as characters from str are parsed.
  @return a dynamically allocated representation of the pattern for the next
          portion of str.
*/
static Pattern *parseConcatenation( char *str, int *pos )
{
  Pattern *p1 = parseRepetition( str, pos );
  while ( str[ *pos ] && str[ *pos ] != '|' && str[ *pos ] != ')' ) {
    Pattern *p2 = parseRepetition( str, pos );
    p1 = makeConcatenationPattern( p1, p2 );
  }
  return p1;
}

/**
  Parse regular expression syntax with the lowest precedence,
  one pattern, p, (optionally) followed by additional patterns
  separated by | (alternation).  If there are no additional patterns, it just
  returns the pattern object for p.

  @param str The string being parsed.
  @param pos A pass-by-reference value for the location in str being parsed,
             increased as characters from str are parsed.
  @return a dynamically allocated representation of the pattern for the next
          portion of str.
*/
static Pattern *parseAlternation( char *str, int *pos )
{
  Pattern *p1 = parseConcatenation( str, pos );

  while ( str[ *pos ] == '|' ) {
    (*pos)++;
    Pattern *p2 = parseConcatenation( str, pos );
    p1 = makeAlternationPattern( p2, p1 );
  }
  return p1;
}

/**
  Processes a single line by creating before and after arrays, searching the line
  for the pattern, and printing results.

  @param line The line to process.
  @param pat The pattern.
  @param size The size of the line.
*/
static void processLine( char *line, Pattern *pat, int size )
{
  bool before[ size + 1 ];

  for ( int i = 0; i < size + 1; i++ ) {
    before[ i ] = true;
  }
  bool after[ sizeof( before ) / sizeof( before[ 0 ] ) ];
  pat->match( pat, size, line, before, after );
  bool hasPattern = false;
  for ( int i = 0; i < size + 1; i++ ) {
    if ( after[ i ] ) {
      hasPattern = true;
    }
  }
  if ( hasPattern ) {
    printf( "%s\n", line );
  }
}

/**
  Starts the program, checks arguments, opens the input file, and calls functions
  to create a pattern and process the input file with the pattern.

  @param argc The number of arguments given.
  @param argv The array of arguments.
  @return The exit status of the program.
*/
int main( int argc, char *argv[] )
{
  FILE *input;
  if ( argc == ARGS1 ) {
    input = stdin;
  } else if ( argc == ARGS2 ) {
    input = fopen( argv[ FILENAME ], "r" );

    if ( !input ) {
      invalidFilename( argv[ FILENAME ] );
    }
  } else {
    invalidArguments();
  }
  Pattern *pat;
  int pos = 0;
  pat = parseAlternation( argv[ PATTERN ], &pos );
  int ch;

  while ( ( ch = fgetc( input ) ) != EOF ) {
    int capacity = CAP;
    int size = 0;
    assert( sizeof( char ) == 1 );
    char *line = (char *)malloc( capacity + 1 );

    while ( ch != '\n' && ch != EOF) {
      if ( size >= capacity ) {
        capacity *= 2;
        line = (char *)realloc( line, capacity + 1 );
      }
      line[ size ] = (char)ch;
      size++;
      ch = fgetc( input );
    }
    line[ size ] = '\0';
    processLine( line, pat, size );
    free( line );

    if ( ch == EOF ) {
      break;
    }
  }
  pat->destroy( pat );
  fclose( input );
  fclose( stdout );
  fclose( stdin );
  fclose( stderr );
  return EXIT_SUCCESS;
}
