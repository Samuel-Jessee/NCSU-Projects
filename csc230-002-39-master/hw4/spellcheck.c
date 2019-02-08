/**
  @file spellcheck.c
  @author Samuel Jessee (sijessee)

  Spellcheck is responsible for handling the command-line arguments,
  finding words in the text, checking to see if they're in the
  dictionary, and responding to user commands.
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <assert.h>
#include "text.h"

/** First valid number of arguments. */
#define EXPECTED_ARGS_1 2

/** Second valid number of arguments. */
#define EXPECTED_ARGS_2 3

/** The ESC constant. */
#define ESC 27

/** Number of lines in the text. */
int linecount = 0;

/** Array holding the lines of text. */
char **text;

/** Number of words in the dictionary. */
int wordcount = 0;

/** Array of words. */
char **dictionary;

int matches( char *word1, char *word2 );
int validDictionary();
int checkDictionary( char *word );
void checkLine( int linenum, int start );
void promptAction( int linenum, int wordlen, int index, char *word );

/**
  Checks if the dictionary violates the formatting requirements.

  @return Line number containing violation.
*/
int validDictionary()
{
  size_t len;

  for ( int i = 0; i < wordcount; i++ )
  {
    char *word = dictionary[ i ];
    len = strlen( word );

    for ( int j = 0; j < len; j++ )
    {
      char ch = word[ j ];

      if ( ch < 'a' || ch > 'z' )
      {
        return i + 1;
      }
    }
  }
  return 0;
}

/**
  Searches the dictionary for a particular word.  Returns
  true (1) if the word is found, or false (0) if not.  Before
  checking the dictionary, the parameter word is changed to
  lowercase, making the search case-independent.  Also returns
  1 if the word contains a special character.

  @param word The word to search for.
  @return 1 if found, or 0 if not.
*/
int checkDictionary( char *word )
{
  size_t len = strlen( word );

  for ( int i = 0; i < len; i++ )
  {
    int ch = ( int )word[ i ];

    if ( !isalpha( ch ) ) {
      return 1;
    }
  }

  for ( int i = 0; i < wordcount; i++ )
  {
    if ( matches( word, dictionary[ i ] ) )
    {
      return 1;
    }
  }
  return 0;
}

/**
  Checks if two words are the same, ignoring case.

  @param word1 First word.
  @param word2 Second word.
  @return 1 if they match, 0 if not.
*/
int matches( char *word1, char *word2 )
{
  size_t len1 = strlen( word1 );
  size_t len2 = strlen( word2 );

  if ( len1 != len2 )
  {
    return 0;
  }
  int ch1;
  int ch2;

  for ( int i = 0; i < len1; i++ )
  {
    ch1 = tolower( ( int )word1[ i ] );
    ch2 = tolower( ( int )word2[ i ] );

    if ( ch1 != ch2 )
    {
      return 0;
    }
  }
  return 1;
}

/**
  Reads through a line of text and spellchecks each word.

  @param linenum Number of the line being checked.
  @param start Index to start at.
*/
void checkLine( int linenum, int start )
{
  char *line = text[ linenum ];
  size_t linelen = strlen( line );
  int wordlen = 0;
  int capacity = 1000;
  char *word = (char *)malloc( capacity + 1 );

  for ( int i = start; i < linelen; i++ )
  {
    char ch = line[ i ];

    if ( isalpha( ( int )ch ) )
    {
      if ( wordlen >= capacity )
      {
        capacity *= 2;
        word = (char *)realloc( word, capacity + 1 );
      }
      word[ wordlen ] = ch;
      wordlen++;
    } else if ( wordlen != 0 )
    {
      word = (char *)realloc( word, wordlen + 1 );
      word[ wordlen ] = '\0';

      if ( !checkDictionary( word ) )
      {
        promptAction( linenum, wordlen, i, word );
      }
      wordlen = 0;
      word = (char *)realloc( word, capacity + 1 );
    }
  }

  if ( wordlen != 0 )
  {
    word = (char *)realloc( word, wordlen + 1 );
    word[ wordlen ] = '\0';

    if ( !checkDictionary( word ) )
    {
      promptAction( linenum, wordlen, linelen - wordlen, word );
    }
    wordlen = 0;
    word = (char *)realloc( word, capacity + 1 );
  }
  free( word );
}

/**
  Prompts the user in response to finding a target word.

  @param linenum Number of the line containing target word.
  @param wordlen Length of target word.
  @param index First char of target word.
  @param word Target word.
*/
void promptAction( int linenum, int wordlen, int index, char *word )
{
  printf( "%c", '\n' );
  char *line = text[ linenum ];
  size_t linelen = strlen( line );

  if ( linenum > 0 )
  {
    printf("%s\n", text[ linenum - 1 ] );
  }

  for ( int i = 0; i < index; i++ )
  {
    printf("%c", line[ i ] );
  }
  putchar(ESC);
  putchar('[');
  putchar('3');
  putchar('1');
  putchar('m');
  printf("%s", word );
  putchar(ESC);
  putchar('[');
  putchar('0');
  putchar('m');

  for ( int i = index + wordlen; i < linelen; i++ )
  {
    printf("%c", line[ i ] );
  }
  printf("%c", '\n' );

  if ( linenum < linecount - 1)
  {
    printf("%s\n", text[ linenum + 1 ] );
  }
  char prompt[] = "(r)eplace, (a)dd, (n)ext or (q)uit: ";
  printf( "%s", prompt );
  char input1[ 1 ];

  while ( scanf( "%c", input1) != EOF )
  {
    if ( *input1 == 'q' || *input1 == 'Q' )
    {
      freeLines( dictionary, wordcount );
      freeLines( text, linecount );
      fprintf( stderr, "Discarding changes\n" );
      exit ( 1 );
    } else if ( *input1 == 'r' || *input1 == 'R' )
    {
      int ch;
      int addlen = 0;
      int tcap = 1000;
      char *temp = (char *)malloc( tcap + 1 );
      int rcap = linelen - ( index + wordlen );
      char *restofline = (char *)malloc( rcap + 1 );

      while ( (ch = getchar()) != EOF )
      {
        if ( addlen >= tcap )
        {
          tcap *= 2;
          temp = (char *)realloc( temp, tcap + 1 );
        }
        temp[ addlen ] = ( char )ch;
        addlen++;
      }
      temp[ addlen ] = '\0';
      strcpy( restofline, &line[ index + wordlen ] );
      line = (char *)realloc( line, linelen + addlen + 1 );
      strcpy( &line[ index ], temp );
      strcpy( &line[ index + addlen ], restofline );
      free( temp );
      free( restofline );
      checkLine( linenum, index );
    } else if ( *input1 == 'a' || *input1 == 'A' )
    {
      wordcount++;
      char *lowerword = (char *)malloc( wordlen + 1 );

      for ( int i = 0; i < wordlen; i++ )
      {
        lowerword[ i ] = ( char )tolower( ( int )word[ i ] );
      }
      lowerword[ wordlen ] = '\0';
      dictionary = (char **)realloc( dictionary, wordcount * sizeof( char * ) );
      dictionary[ wordcount - 1 ] = lowerword;
    } else if ( *input1 == 'n' || *input1 == 'N' )
    {
      break;
    } else
    {
      printf("Unknown command\n");
      promptAction( linenum, wordlen, index, word );
    }
  }
}

/**
  Starts the program, calls functions to read and spellcheck files,
  and prompts user for spellcheck options.

  @param argc Number of arguments provided.
  @param argv Array of arguments.
  @return Exit status for the program.
*/
int main( int argc, char *argv[] )
{
  char *tname;
  char *dname;

  if ( argc == EXPECTED_ARGS_1 )
  {
    tname = argv[ 1 ];
    dname = "words.txt";
  } else if ( argc == EXPECTED_ARGS_2 )
  {
    tname = argv[ 1 ];
    dname = argv[ 2 ];
  } else
  {
    fprintf( stderr, "usage: spellcheck <textfile.txt> [words.txt]\n" );
    exit( EXIT_FAILURE );
  }
  linecount = 0;
  text = readLines( tname, &linecount );

  if ( !text )
  {
    freeLines( text, linecount );
    fprintf( stderr, "Can't open file: %s\n", tname );
    exit( EXIT_FAILURE );
  }
  wordcount = 0;
  dictionary = readLines( dname, &wordcount );

  if ( !dictionary )
  {
    freeLines( dictionary, wordcount );
    freeLines( text, linecount );
    fprintf( stderr, "Can't open file: %s\n", dname );
    exit( EXIT_FAILURE );
  }
  int invalidline = validDictionary( dictionary, wordcount );

  if ( invalidline != 0 )
  {
    freeLines( dictionary, wordcount );
    freeLines( text, linecount );
    fprintf( stderr, "Invalid word, line: %d\n", invalidline );
    exit( EXIT_FAILURE );
  }

  for (int i = 0; i < linecount; i++ )
  {
    checkLine( i, 0 );
  }
  printf("Spellcheck complete.\n");
  char *bak = ".bak";
  char bakfile[ strlen( tname ) + strlen( bak ) + 1 ];
  strcpy( bakfile, tname );
  strcat( bakfile, bak );
  printf( "Backing up %s to %s\n", tname, bakfile );
  rename( tname, bakfile );
  printf( "Writing updated %s\n", tname );
  FILE *fp = fopen( tname, "w");

  for ( int i = 0; i < linecount; i++ )
  {
    fprintf( fp, "%s\n", text[ i ] );
  }
  freeLines( dictionary, wordcount );
  freeLines( text, linecount );
  fclose( fp );
  exit( 0 );
}
