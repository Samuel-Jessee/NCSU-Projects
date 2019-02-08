/**
  @file crypt.c
  @author Samuel Jessee (sijessee)
  The crypt.c component will contain the main() function and any other supporting
  functions you decide to use.  It will be responsible for handling the command-line
  arguments and the file opening/closing.
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <assert.h>
#include "des.h"

/** Expected number of arguments. */
#define EXPECTED_ARGS 5

/** Index of the mode (e/d) argument. */
#define MODE 1

/** Index of the key argument. */
#define KEY 2

/** Index of the inputfile argument. */
#define INPUTFILE 3

/** Index of the outputfile argument. */
#define OUTPUTFILE 4

/** Number of bytes in a block. */
#define BLOCK_SIZE 8

/**
  Checks a key to make sure it is exactly 8 bytes, and that it contains only letters
  and digits.  Returns 1 if the key is valid, or 0 if not.
  @param key The key to check.
  @return 1 if valid, 0 if not.
*/
int isValidKey( char *key )
{
  if ( strlen( key ) != BLOCK_SIZE ) {
    return 0;
  }
  for ( int i = 0; i < BLOCK_SIZE; i++ ) {
    if ( !isalnum( (int)key[ i ] ) ) {
      return 0;
    }
  }
  return 1;
}

/**
  Checks the given mode and determines if it is valid.  Returns 1 if the mode is "e"
  or "d".  Otherwise, 0 is returned.
  @param mode The mode to check.
  @return 1 if mode is valid, or 0 if not.
*/
int isValidMode( char *mode )
{
  if ( strcmp( mode, "e" ) != 0 ) {
    if ( strcmp( mode, "d" ) != 0 ) {
      return 0;
    }
  }
  return 1;
}

/**
  Starts the program, checks the arguments, and calls other functions needed to
  perform the tasks of the program.
  @param argc The number of arguments.
  @param argv Array of arguments.
  @return The exit status of the program.
*/
int main ( int argc, char *argv[] )
{
  if ( argc != EXPECTED_ARGS ) {
    fprintf( stderr, "usage: crypt <e|d> <key> <inputfile> <outputfile>\n" );
    exit( EXIT_FAILURE );
  } else if ( !isValidMode( argv[ MODE ] ) ) {
    fprintf( stderr, "Incorrect mode\n" );
    exit( EXIT_FAILURE );
  } else if ( !isValidKey( argv[ KEY ] ) ) {
    fprintf( stderr, "Key must be exactly 8 bytes\n" );
    exit( EXIT_FAILURE );
  }
  FILE *in = fopen( argv[ INPUTFILE ], "r");
  if ( !in ) {
    fprintf( stderr, "Can't open file: %s\n", argv[ INPUTFILE ] );
    exit( EXIT_FAILURE );
  }
  FILE *out = fopen( argv[ OUTPUTFILE ], "w");
  if ( !out ) {
    fprintf( stderr, "Can't open file: %s\n", argv[ OUTPUTFILE ] );
    exit( EXIT_FAILURE );
  }
  fseek( in, 0, SEEK_END );
  long int size = ftell( in );
  fseek( in, 0, SEEK_SET );
  int extra = 0;
  if ( size % BLOCK_SIZE != 0 ) {
    extra = BLOCK_SIZE - ( size % BLOCK_SIZE );
  }
  unsigned char *text = (unsigned char *)calloc( size + extra,
                                                 sizeof( unsigned char ) );
  size_t numberRead = fread( text, sizeof( unsigned char ), size, in );
  assert( size == numberRead );
  if ( argv[MODE][0] == 'e' ) {
    for ( int i = 0; i < size + extra; i += BLOCK_SIZE ) {
      encrypt_block( text + i, (unsigned char *)argv[ KEY ] );
    }
    for ( int i = 0; i < size + extra; i++) {
      fputc( text[ i ], out );
    }
  } else if ( argv[MODE][0] == 'd' ) {
    for ( int i = 0; i < size + extra; i += BLOCK_SIZE ) {
      decrypt_block( text + i, (unsigned char *)argv[ KEY ] );
    }
    for ( int i = 0; i < size + extra; i++) {
      fputc( text[ i ], out );
    }
  }
  free( text );
  fclose( in );
  fclose( out );
  exit( 0 );
}
