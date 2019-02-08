/**
  @file pattern.h
  @author Samuel Jessee (sijessee)

  Declares the basic pattern structure, the different pattern constructors,
  and multiple fuctions from pattern.c that are used in mygrep.c.
*/

#ifndef _PATTERN_H_
#define _PATTERN_H_

#include <stdbool.h>

/** A short name to use for the Pattern interface. */
typedef struct PatternTag Pattern;

/**
  Structure used as a superclass/interface for patterns.  It includes
  an override-able method for matching against a given string, and for
  destroying itself to free any allocated resources.
*/
struct PatternTag {
  /**
    Pointer to a function to match this pattern against the given
    string.  As described in the design, this function fills in
    locations in the after array to indicate places in the string
    that could be reached after this pattern is matched.  The before
    and after arrays must be one element longer than the length of
    the string being matched.

    @param pat The pattern that's supposed to match itself against the string.
    @param len Length of the string, we could compute it, but it's more efficient
               to pass it in.
    @param str The input string being matched against.
    @param before Marks for locations in the string that could be reached before
                  matching this pattern.
    @param after Marks for locations in the string that can be reached after
                 matching this pattern.
  */
  void (*match)( Pattern *pat, int len, const char *str,
                 const bool *before, bool *after );

  /**
    Free memory for this pattern, including any subpatterns it contains.

    @param pat pattern to free.
  */
  void (*destroy)( Pattern *pat );
};

/**
  Make a pattern for a single, non-special character, like `a` or `5`.  This
  is an atomic pattern, and is used as the parent for all other atomic patterns.

  @param sym The symbol this pattern is supposed to match.
  @return A dynamically allocated representation for this new pattern.
*/
Pattern *makeSymbolPattern( char sym );

/**
  Make a pattern that matches any single character.  This is an atomic pattern.

  @param sym The symbol, which should be '.'.
  @return A dynamically allocated representation for this new pattern.
*/
Pattern *makePeriodPattern( char sym );

/**
  Make a pattern that matches the start of a line.  This is an atomic pattern.

  @param sym The symbol, which should be '^'.
  @return A dynamically allocated representation for this new pattern.
*/
Pattern *makeStartAnchorPattern( char sym );

/**
  Make a pattern that matches the end of a line.  This is an atomic pattern.

  @param sym The symbol, which should be '$'.
  @return A dynamically allocated representation for this new pattern.
*/
Pattern *makeEndAnchorPattern( char sym );

/**
  Make a pattern that matches any character from a given set.  This is an
  atomic pattern, but is stored using a unique structure.

  @param characterCount The number of characters in the character class.
  @param characterClass The string of valid characters.
  @return A dynamically allocated representation for this new pattern.
*/
Pattern *makeCharacterClassPattern( int characterCount, char *characterClass );

/**
  Make a pattern that holds a subpattern.  Matches to whatever pattern is
  contained within the parentheses.  This is technically an atomic pattern,
  but it is stored as a repitition.

  @param p1 The pattern contained within parentheses.
  @return A dynamically allocated representation for this new pattern.
*/
Pattern *makeParenthesesPattern( Pattern *p1 );

/**
  Make a pattern that matches a subpattern zero or more times.  This
  is a repetiton.

  @param p1 The pattern to repeat.
  @return A dynamically allocated representation for this new pattern.
*/
Pattern *makeZeroPlusPattern( Pattern *p1 );

/**
  Make a pattern that matches a subpattern at least one or more times.  This
  is a repetition.

  @param p1 The pattern to repeat.
  @return A dynamically allocated representation for this new pattern.
*/
Pattern *makeOnePlusPattern( Pattern *p1 );

/**
  Make a pattern that matches a subpattern either zero or one times.  This
  is a repetiton.

  @param p1 The pattern to repeat.
  @return A dynamically allocated representation for this new pattern.
*/
Pattern *makeZeroOnePattern( Pattern *p1 );

/**
  Make a pattern for the concatenation of patterns p1 and p2.  It should match
  anything that can be broken into two substrings, s1 and s2, where the p1 matches
  the first part (s1) and p2 matches the second part (s2).  This is stored
  as a binary pattern.

  @param p1 Subpattern for matching the first part of the string.
  @param p2 Subpattern for matching the second part of the string.
  @return A dynamically allocated representation for this new pattern.
*/
Pattern *makeConcatenationPattern( Pattern *p1, Pattern *p2 );

/**
  Make a pattern for the alternation of patterns p1 and p2.  It should match
  anything that matches the first pattern (p1), or the second pattern (p2).
  This is stored as a binary pattern.

  @param p1 First possible subpattern.
  @param p2 Second possible subpattern.
  @return A dynamically allocated representation for this new pattern.
*/
Pattern *makeAlternationPattern( Pattern *p1, Pattern *p2 );

/**
  Helpful function to print out a string, with the marks shown
  between the characters as asterisks.

  @param str String to report.
  @param marks Array of marks between the characters of str, assumed
               to be one element longer than the length of str.
*/
void reportMarks( const char *str, const bool *marks );

#endif
