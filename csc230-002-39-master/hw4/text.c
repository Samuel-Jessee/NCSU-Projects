/**
  @file text.c
  @author Samuel Jessee (sijessee)

  Text is responsible for reading text from files into
  an array of pointers to strings, and freeing the array
  when it's no longer needed.  It will help with reading
  the dictionary and reading the text that needs to be
  spellchecked.
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <assert.h>
#include "text.h"

/**
  This function will read a line of text from the given
  file, returning it as a string stored in a dynamically
  allocated array.  It will stop reading when it encounters
  a newline or the end-of-file.  If it can't read anything
  before reaching the end-of-file, it will return NULL.
  Internally, this function will use a resizable array.
  It will allocate a small array to hold the line of text.
  As it reads characters, it will grow the array as
  necessary to be able to hold the whole line.

  @param fp Text file being read.
  @return Pointer to the first char in a string.
*/
char *readLine( FILE *fp )
{
  int capacity = 5;
  int len = 0;
  char *line = (char *)malloc( capacity + 1 );
  int ch;

  while ( ( ch = fgetc( fp ) ) != EOF && ch != '\n' )
  {
    if ( len >= capacity )
    {
      capacity *= 2;
      line = (char *)realloc( line, capacity + 1 );
    }
    assert( len < capacity );
    line[ len ] = ( char ) ch;
    len++;
  }

  if ( len == 0 )
  {
    return NULL;
  }
  line[ len ] = '\0';
  len++;
  line = (char *)realloc( line, len );
  return line;
}

/**
  This function will read the text of an entire file, storing
  it as a dynamically allocated array of pointers to dynamically
  allocated strings.  The function will open the file with the
  given name, read all of its lines, return a pointer to the array
  of char pointers holding the contents of the file's lines (thus
  the pointer-to-pointer-to-char return type) and fill in the
  pass-by-reference count parameter with the number of lines read
  from the file.
  Internally, this function will use a resizable array of char
  pointers to store the contents of the file.  As it reads additional
  lines from the file, it will need to be able to resize its array of
  pointers to be able to store pointers to all of the lines.  It can
  use the readLine() function to read the text of each line of the file.

  @param fileName Name of the text file.
  @param count Pointer to the number of strings in the file.
  @return Array of strings representing the lines from the file.
*/
char **readLines( const char *fileName, int *count ) {
  FILE *fp = fopen( fileName,  "r" );

  if ( !fp )
  {
    return NULL;
  }
  int capacity = 5;
  int len = 0;
  char **text = (char **)malloc( capacity * sizeof( char * ) );
  char *line;

  while ( ( line = readLine( fp ) ) )
  {
    if ( len >= capacity )
    {
      capacity *= 2;
      text = (char **)realloc( text, capacity * sizeof( char * ) );
    }
    assert( len < capacity );
    text[ len ] = line;
    len++;
  }

  if ( len == 0 )
  {
    return NULL;
  }
  text = (char **)realloc( text, len * sizeof( char * ) );
  *count = len;
  fclose( fp );
  return text;
}

/**
  This function will free the memory for the type of two-dimensional
  array returned from readLines().  Its parameters are an array of
  pointers to strings and a count of the number of strings in the array
  (just like what readLines gives you).  It will need to free the memory
  for each string, then free the array of pointers.

  @param lines Array of strings.
  @param count Number of strings in the array.
*/
void freeLines( char **lines, int count )
{
  for ( int i = 0; i < count; i++ )
  {
    free( lines[ i ] );
  }
  free( lines );
}
