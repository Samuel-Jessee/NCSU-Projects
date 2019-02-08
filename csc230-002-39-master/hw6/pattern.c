/**
  @file pattern.c
  @author Samuel Jessee (sijessee)

  The pattern.c component provides an abstract interface for different types of
  patterns, along with concrete implementations for all matching individual symbols
  (SymbolPattern) and for matching concatenated patterns (BinaryPattern).
*/

#include "pattern.h"
#include <stdlib.h>
#include <stdio.h>

// Documented in the header.
void reportMarks( const char *str, const bool *marks )
{
  int i = 0;
  while ( str[ i ] ) {
    printf( "%c%c", marks[ i ] ? '*' : ' ', str[ i ] );
    i++;
  }
  printf( "%c\n", marks[ i ] ? '*' : ' ' );
}

/**
  A simple function that can be used to free the memory for any
  pattern that doesn't allocate any additional memory other than the
  struct used to represent it (e.g., if it doesn't contain any
  sub-patterns).

  @param pat The pattern to free memory for.
*/
static void destroySimplePattern( Pattern *pat )
{
  free( pat );
}

/**
  Type of pattern used to represent a single, ordinary symbol (sym) to be matched,
  like 'a' or '5'.
*/
typedef struct {
  void (*match)( Pattern *pat, int len, const char *str,
                 const bool *before, bool *after );
  void (*destroy)( Pattern *pat );
  char sym;
} SymbolPattern;

// Method used to match a SymbolPattern.
static void matchSymbolPattern( Pattern *pat, int len, const char *str,
                                const bool *before, bool *after )
{
  SymbolPattern *this = (SymbolPattern *) pat;
  after[ 0 ] = false;

  for ( int i = 0; i < len; i++ ) {
    after[ i + 1 ] = ( before[ i ] && str[ i ] == this->sym );
  }
}

// Documented in the header.
Pattern *makeSymbolPattern( char sym )
{
  SymbolPattern *this = (SymbolPattern *) malloc( sizeof( SymbolPattern ) );
  this->sym = sym;
  this->match = matchSymbolPattern;
  this->destroy = destroySimplePattern;
  return (Pattern *) this;
}

// Method used to match a PeriodPattern.
static void matchPeriodPattern( Pattern *pat, int len, const char *str,
                                const bool *before, bool *after )
{
  after[ 0 ] = false;

  for ( int i = 0; i < len; i++ ) {
    after[ i + 1 ] = ( before[ i ] && str[ i ] );
  }
}

// Documented in the header.
Pattern *makePeriodPattern( char sym )
{
  SymbolPattern *this = (SymbolPattern *) malloc( sizeof( SymbolPattern ) );
  this->sym = sym;
  this->match = matchPeriodPattern;
  this->destroy = destroySimplePattern;
  return (Pattern *) this;
}

// Method used to match a StartAnchorPattern.
static void matchStartAnchorPattern( Pattern *pat, int len, const char *str,
                                     const bool *before, bool *after )
{
  if ( !before[ 0 ] ) {
    for ( int i = 0; i < len + 1; i++ ) {
      after[ i ] = false;
    }
  }
}

// Documented in the header.
Pattern *makeStartAnchorPattern( char sym )
{
  SymbolPattern *this = (SymbolPattern *) malloc( sizeof( SymbolPattern ) );
  this->sym = sym;
  this->match = matchStartAnchorPattern;
  this->destroy = destroySimplePattern;
  return (Pattern *) this;
}

// Method used to match a EndAnchorPattern.
static void matchEndAnchorPattern( Pattern *pat, int len, const char *str,
                                   const bool *before, bool *after )
{
  for ( int i = 0; i < len + 1; i++ ) {
    after[ i ] = false;
  }

  if ( before[ len ] ) {
    after[ len ] = true;
  }
}

// Documented in the header.
Pattern *makeEndAnchorPattern( char sym )
{
  SymbolPattern *this = (SymbolPattern *) malloc( sizeof( SymbolPattern ) );
  this->sym = sym;
  this->match = matchEndAnchorPattern;
  this->destroy = destroySimplePattern;
  return (Pattern *) this;
}

/**
  Checks the character class for a specific character.  Returns true if found.

  @param characterCount The number of characters in the character class.
  @param characterClass The string of valid characters.
  @param ch The character to find.
  @return True if found in character class.
*/
static bool characterMatch( int characterCount, char *characterClass, char ch )
{
  for ( int j = 0; j < characterCount; j++ ) {
    if ( characterClass[ j ] == ch ) {
      return true;
    }
  }
  return false;
}

/**
  Type of pattern used to represent a character class.
*/
typedef struct {
  void (*match)( Pattern *pat, int len, const char *str,
                 const bool *before, bool *after );
  void (*destroy)( Pattern *pat );
  int characterCount;
  char *characterClass;
  bool (*charMatch)( int count, char *chclass, char ch );
} CharacterClassPattern;

// Method used to match a CharacterClassPattern.
static void matchCharacterClassPattern( Pattern *pat, int len, const char *str,
                                        const bool *before, bool *after )
{
  CharacterClassPattern *this = (CharacterClassPattern *) pat;
  after[ 0 ] = false;

  for ( int i = 0; i < len; i++ ) {
    after[ i + 1 ] = ( before[ i ] && this->charMatch( this->characterCount,
                                                       this->characterClass,
                                                       str[ i ] ) );
  }
}

// destroy function used for CharacterClassPattern
static void destroyCharacterClassPattern( Pattern *pat )
{
  CharacterClassPattern *this = (CharacterClassPattern *) pat;
  free( this->characterClass );
  free( this );
}

// Documented in the header.
Pattern *makeCharacterClassPattern( int characterCount, char *characterClass )
{
  CharacterClassPattern *this = (CharacterClassPattern *)
                                malloc( sizeof( CharacterClassPattern ) );
  this->characterCount = characterCount;
  this->characterClass = characterClass;
  this->match = matchCharacterClassPattern;
  this->destroy = destroyCharacterClassPattern;
  this->charMatch = characterMatch;
  return (Pattern *) this;
}

/**
  Type of pattern used to represent a repetition pattern.
*/
typedef struct {
  void (*match)( Pattern *pat, int len, const char *str,
                 const bool *before, bool *after );
  void (*destroy)( Pattern *pat );
  Pattern *p1;
} RepetitionPattern;

// destroy function used for RepetitionPattern
static void destroyRepetitionPattern( Pattern *pat )
{
  RepetitionPattern *this = (RepetitionPattern *) pat;
  this->p1->destroy( this->p1 );
  free( this );
}

// Method used to match a pattern in parentheses.
static void matchParenthesesPattern( Pattern *pat, int len, const char *str,
                                     const bool *before, bool *after )
{
  RepetitionPattern *this = (RepetitionPattern *) pat;
  this->p1->match( this->p1, len, str, before, after );
}

// Documented in the header.
Pattern *makeParenthesesPattern( Pattern *p1 )
{
  RepetitionPattern *this = (RepetitionPattern *)
                            malloc( sizeof( RepetitionPattern ) );
  this->p1 = p1;
  this->match = matchParenthesesPattern;
  this->destroy = destroyRepetitionPattern;
  return (Pattern *) this;
}

// Method used to match a pattern zero or more times.
static void matchZeroPlusPattern( Pattern *pat, int len, const char *str,
                                  const bool *before, bool *after )
{
  RepetitionPattern *this = (RepetitionPattern *) pat;
  bool temp[ len + 1 ];
  int count = 0;

  for ( int i = 0; i < len + 1; i++ ) {
    if ( ( temp[ i ] = before[ i ] ) ) {
      count++;
    }
  }

  while ( count ) {
    int n = 0;
    this->p1->match( this->p1, len, str, temp, after );

    for ( int i = 0; i < len + 1; i++ ) {
      if ( ( temp[ i ] = temp[ i ] || after[ i ] ) ) {
        n++;
      }
    }

    if ( n > count ) {
      count = n;
    } else {
      count = 0;
    }
  }

  for ( int i = 0; i < len + 1; i++ ) {
    after[ i ] = temp[ i ];
  }
}

// Documented in the header.
Pattern *makeZeroPlusPattern( Pattern *p1 )
{
  RepetitionPattern *this = (RepetitionPattern *)
                            malloc( sizeof( RepetitionPattern ) );
  this->p1 = p1;
  this->match = matchZeroPlusPattern;
  this->destroy = destroyRepetitionPattern;
  return (Pattern *) this;
}

// Method used to match a pattern one or more times.
static void matchOnePlusPattern( Pattern *pat, int len, const char *str,
                                 const bool *before, bool *after )
{
  RepetitionPattern *this = (RepetitionPattern *) pat;
  bool temp[ len + 1 ];
  this->p1->match( this->p1, len, str, before, temp );
  int count = 0;

  for ( int i = 0; i < len + 1; i++ ) {
    if ( temp[ i ] ) {
      count++;
    }
  }

  while ( count ) {
    int n = 0;
    this->p1->match( this->p1, len, str, temp, after );

    for ( int i = 0; i < len + 1; i++ ) {
      if ( ( temp[ i ] = temp[ i ] || after[ i ] ) ) {
        n++;
      }
    }

    if ( n > count ) {
      count = n;
    } else {
      count = 0;
    }
  }

  for ( int i = 0; i < len + 1; i++ ) {
    after[ i ] = temp[ i ];
  }
}

// Documented in the header.
Pattern *makeOnePlusPattern( Pattern *p1 )
{
  RepetitionPattern *this = (RepetitionPattern *)
                            malloc( sizeof( RepetitionPattern ) );
  this->p1 = p1;
  this->match = matchOnePlusPattern;
  this->destroy = destroyRepetitionPattern;
  return (Pattern *) this;
}

// Method used to match a pattern zero or one times.
static void matchZeroOnePattern( Pattern *pat, int len, const char *str,
                                     const bool *before, bool *after )
{
  RepetitionPattern *this = (RepetitionPattern *) pat;
  this->p1->match( this->p1, len, str, before, after );

  for ( int i = 0; i < len + 1; i++ ) {
    after[ i ] = before[ i ] || after[ i ];
  }
}

// Documented in the header.
Pattern *makeZeroOnePattern( Pattern *p1 )
{
  RepetitionPattern *this = (RepetitionPattern *)
                            malloc( sizeof( RepetitionPattern ) );
  this->p1 = p1;
  this->match = matchZeroOnePattern;
  this->destroy = destroyRepetitionPattern;
  return (Pattern *) this;
}

/**
  Representation for a type of pattern that just contains two
  sub-patterns (e.g., concatenation).
*/
typedef struct {
  void (*match)( Pattern *pat, int len, const char *str,
                 const bool *before, bool *after );
  void (*destroy)( Pattern *pat );
  Pattern *p1, *p2;
} BinaryPattern;

// destroy function used for BinaryPattern
static void destroyBinaryPattern( Pattern *pat )
{
  BinaryPattern *this = (BinaryPattern *) pat;
  this->p1->destroy( this->p1 );
  this->p2->destroy( this->p2 );
  free( this );
}

// match function for a BinaryPattern used to handle concatenation.
static void matchConcatenationPattern( Pattern *pat, int len, const char *str,
                                       const bool *before, bool *after )
{
  BinaryPattern *this = (BinaryPattern *) pat;
  bool midMarks[ len + 1 ];
  this->p1->match( this->p1, len, str, before, midMarks );
  this->p2->match( this->p2, len, str, midMarks, after );
}

// Documented in the header
Pattern *makeConcatenationPattern( Pattern *p1, Pattern *p2 )
{
  BinaryPattern *this = (BinaryPattern *) malloc( sizeof( BinaryPattern ) );
  this->p1 = p1;
  this->p2 = p2;
  this->match = matchConcatenationPattern;
  this->destroy = destroyBinaryPattern;
  return (Pattern *) this;
}

// match function for a BinaryPattern used to handle alternation.
static void matchAlternationPattern( Pattern *pat, int len, const char *str,
                                     const bool *before, bool *after )
{
  BinaryPattern *this = (BinaryPattern *) pat;
  bool temp1[ len + 1 ];
  bool temp2[ len + 1 ];
  this->p1->match( this->p1, len, str, before, temp1 );
  this->p2->match( this->p2, len, str, before, temp2 );

  for ( int i = 0; i < len + 1; i++) {
    after[ i ] = ( temp1[ i ] || temp2[ i ] );
  }
}

// Documented in the header
Pattern *makeAlternationPattern( Pattern *p1, Pattern *p2 )
{
  BinaryPattern *this = (BinaryPattern *) malloc( sizeof( BinaryPattern ) );
  this->p1 = p1;
  this->p2 = p2;
  this->match = matchAlternationPattern;
  this->destroy = destroyBinaryPattern;
  return (Pattern *) this;
}
