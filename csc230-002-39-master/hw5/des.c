/**
  @file des.c
  @author Samuel Jessee (sijessee)
  The des.c component will be responsible for encrypting plain text to cipher text
  and decrypting cipher text to plain text.
*/

#include <stdlib.h>
#include <stdio.h>
#include <stdbool.h>
#include "des.h"

/** Number of bytes in a block. */
#define BLOCK_SIZE 8

/** Size of a key in bits. */
#define KEY_SIZE 56

/** Half of a key in bits. */
#define HALF_KEY 28

/** Size of a subkey in bits. */
#define SUBKEY_SIZE 48

/** Number of subkeys. */
#define NUMBER_OF_SUBKEYS 16

/** Number of bits in a block. */
#define NUMBER_OF_BITS 64

/** Half number of bits in a block. */
#define HALF_BITS 32

/** Size of the shifts table. */
#define SHIFTS_SIZE 16

/** Size of expanded block. */
#define EXPANDED_SIZE 48

/** Number of columns in S tables. */
#define S_COLS 16

/** Number of rows in S tables. */
#define S_ROWS 4

/** Number 16, used as constant. */
#define SIXTEEN 16

/** Number 8, used as a constant. */
#define EIGHT 8

/** Number 4, used as a constant. */
#define FOUR 4

/** Number 2, used as a constant. */
#define TWO 2

/** Number of bits in a single hex place. */
#define HEX 4

int PC1[ KEY_SIZE ] = {
  57, 49, 41, 33, 25, 17,  9,
   1, 58, 50, 42, 34, 26, 18,
  10,  2, 59, 51, 43, 35, 27,
  19, 11,  3, 60, 52, 44, 36,
  63, 55, 47, 39, 31, 23, 15,
   7, 62, 54, 46, 38, 30, 22,
  14,  6, 61, 53, 45, 37, 29,
  21, 13,  5, 28, 20, 12,  4 };

int shifts[ SHIFTS_SIZE ] = {
   1,  1,  2,  2,  2,  2,  2,  2,  1,  2,  2,  2,  2,  2,  2,  1 };

int PC2[ SUBKEY_SIZE ] = {
  14, 17, 11, 24,  1,  5,
   3, 28, 15,  6, 21, 10,
  23, 19, 12,  4, 26,  8,
  16,  7, 27, 20, 13,  2,
  41, 52, 31, 37, 47, 55,
  30, 40, 51, 45, 33, 48,
  44, 49, 39, 56, 34, 53,
  46, 42, 50, 36, 29, 32 };

int IP[ NUMBER_OF_BITS ] = {
  58, 50, 42, 34, 26, 18, 10,  2,
  60, 52, 44, 36, 28, 20, 12,  4,
  62, 54, 46, 38, 30, 22, 14,  6,
  64, 56, 48, 40, 32, 24, 16,  8,
  57, 49, 41, 33, 25, 17,  9,  1,
  59, 51, 43, 35, 27, 19, 11,  3,
  61, 53, 45, 37, 29, 21, 13,  5,
  63, 55, 47, 39, 31, 23, 15,  7 };

int EBIT[ SUBKEY_SIZE ] = {
  32,  1,  2,  3,  4,  5,  4,  5,
   6,  7,  8,  9,  8,  9, 10, 11,
  12, 13, 12, 13, 14, 15, 16, 17,
  16, 17, 18, 19, 20, 21, 20, 21,
  22, 23, 24, 25, 24, 25, 26, 27,
  28, 29, 28, 29, 30, 31, 32,  1 };

int P[ HALF_BITS ] = {
  16,  7, 20, 21,
  29, 12, 28, 17,
   1, 15, 23, 26,
   5, 18, 31, 10,
   2,  8, 24, 14,
  32, 27,  3,  9,
  19, 13, 30,  6,
  22, 11,  4, 25 };

int IPinv[ NUMBER_OF_BITS ] = {
  40,  8, 48, 16, 56, 24, 64, 32,
  39,  7, 47, 15, 55, 23, 63, 31,
  38,  6, 46, 14, 54, 22, 62, 30,
  37,  5, 45, 13, 53, 21, 61, 29,
  36,  4, 44, 12, 52, 20, 60, 28,
  35,  3, 43, 11, 51, 19, 59, 27,
  34,  2, 42, 10, 50, 18, 58, 26,
  33,  1, 41,  9, 49, 17, 57, 25 };

int s1[ S_ROWS ][ S_COLS ] = {
{ 14,  4, 13,  1,  2, 15, 11,  8,  3, 10,  6, 12,  5,  9,  0,  7 },
{  0, 15,  7,  4, 14,  2, 13,  1, 10,  6, 12, 11,  9,  5,  3,  8 },
{  4,  1, 14,  8, 13,  6,  2, 11, 15, 12,  9,  7,  3, 10,  5,  0 },
{ 15, 12,  8,  2,  4,  9,  1,  7,  5, 11,  3, 14, 10,  0,  6, 13 } };

int s2[ S_ROWS ][ S_COLS ] = {
{ 15,  1,  8, 14,  6, 11,  3,  4,  9,  7,  2, 13, 12,  0,  5, 10 },
{  3, 13,  4,  7, 15,  2,  8, 14, 12,  0,  1, 10,  6,  9, 11,  5 },
{  0, 14,  7, 11, 10,  4, 13,  1,  5,  8, 12,  6,  9,  3,  2, 15 },
{ 13,  8, 10,  1,  3, 15,  4,  2, 11,  6,  7, 12,  0,  5, 14,  9 } };

int s3[ S_ROWS ][ S_COLS ] = {
{ 10,  0,  9, 14,  6,  3, 15,  5,  1, 13, 12,  7, 11,  4,  2,  8 },
{ 13,  7,  0,  9,  3,  4,  6, 10,  2,  8,  5, 14, 12, 11, 15,  1 },
{ 13,  6,  4,  9,  8, 15,  3,  0, 11,  1,  2, 12,  5, 10, 14,  7 },
{  1, 10, 13,  0,  6,  9,  8,  7,  4, 15, 14,  3, 11,  5,  2, 12 } };

int s4[ S_ROWS ][ S_COLS ] = {
{  7, 13, 14,  3,  0,  6,  9, 10,  1,  2,  8,  5, 11, 12,  4, 15 },
{ 13,  8, 11,  5,  6, 15,  0,  3,  4,  7,  2, 12,  1, 10, 14,  9 },
{ 10,  6,  9,  0, 12, 11,  7, 13, 15,  1,  3, 14,  5,  2,  8,  4 },
{  3, 15,  0,  6, 10,  1, 13,  8,  9,  4,  5, 11, 12,  7,  2, 14 } };

int s5[ S_ROWS ][ S_COLS ] = {
{  2, 12,  4,  1,  7, 10, 11,  6,  8,  5,  3, 15, 13,  0, 14,  9 },
{ 14, 11,  2, 12,  4,  7, 13,  1,  5,  0, 15, 10,  3,  9,  8,  6 },
{  4,  2,  1, 11, 10, 13,  7,  8, 15,  9, 12,  5,  6,  3,  0, 14 },
{ 11,  8, 12,  7,  1, 14,  2, 13,  6, 15,  0,  9, 10,  4,  5,  3 } };

int s6[ S_ROWS ][ S_COLS ] = {
{ 12,  1, 10, 15,  9,  2,  6,  8,  0, 13,  3,  4, 14,  7,  5, 11 },
{ 10, 15,  4,  2,  7, 12,  9,  5,  6,  1, 13, 14,  0, 11,  3,  8 },
{  9, 14, 15,  5,  2,  8, 12,  3,  7,  0,  4, 10,  1, 13, 11,  6 },
{  4,  3,  2, 12,  9,  5, 15, 10, 11, 14,  1,  7,  6,  0,  8, 13 } };

int s7[ S_ROWS ][ S_COLS ] = {
{  4, 11,  2, 14, 15,  0,  8, 13,  3, 12,  9,  7,  5, 10,  6,  1 },
{ 13,  0, 11,  7,  4,  9,  1, 10, 14,  3,  5, 12,  2, 15,  8,  6 },
{  1,  4, 11, 13, 12,  3,  7, 14, 10, 15,  6,  8,  0,  5,  9,  2 },
{  6, 11, 13,  8,  1,  4, 10,  7,  9,  5,  0, 15, 14,  2,  3, 12 } };

int s8[ S_ROWS ][ S_COLS ] = {
{ 13,  2,  8,  4,  6, 15, 11,  1, 10,  9,  3, 14,  5,  0, 12,  7 },
{  1, 15, 13,  8, 10,  3,  7,  4, 12,  5,  6, 11,  0, 14,  9,  2 },
{  7, 11,  4,  1,  9, 12, 14,  2,  0,  6, 10, 13, 15,  3,  5,  8 },
{  2,  1, 14,  7,  4, 10,  8, 13, 15, 12,  9,  0,  3,  5,  6, 11 } };

/**
  Converts an array of 8 bools to a char.
  @param array An array of 8 bools.
  @return The char.
*/
unsigned char arrayToChar( bool *array )
{
  unsigned char ch = 0x00;
  for ( int i = 0; i < BLOCK_SIZE; i++ ) {
    ch += array[ i ];
    if ( i < BLOCK_SIZE - 1 ) {
      ch <<= 1;
    }
  }
  return ch;
}

/**
  Converts an array of 64 bools to an array of chars.
  @param array The array of bools.
  @return The array of chars.
*/
unsigned char *arrayToBlock( bool *array )
{
  unsigned char *block = (unsigned char *)calloc( NUMBER_OF_BITS, sizeof( unsigned char ) );
  for ( int i = 0; i < BLOCK_SIZE; i++ ) {
    unsigned char temp = arrayToChar( array + ( BLOCK_SIZE * i ) );
    block[ i ] = temp;
  }
  return block;
}

/**
  Permutes a block of bits according to a permutation table.  Returns a new block.
  @param table The permutation table to use.
  @param block The block to permute.
  @param tablesize The number of bits the table permutes.
  @return The permuted block.
*/
bool *permute( int *table, bool *block, int tablesize )
{
  bool *pblock = (bool *)malloc( tablesize * sizeof( bool ) );
  for ( int i = 0; i < tablesize; i++ ) {
    pblock[ i ] = block[ table[ i ] - 1 ];
  }
  return pblock;
}

/**
  Converts a hex digit to an array of 4 bools representing the digit in binary.
  @param ch The hex digit to convert.
  @return An array of bools.
*/
bool *hexToArray( unsigned char ch )
{
  unsigned char mask = 0x08;
  bool *array = (bool *)calloc( HEX, sizeof( bool ) );
  for ( int i = 0; i < HEX; i++ ) {
    array[ i ] += ( ch & mask );
    mask >>= 1;
  }
  return array;
}

/**
  Shortens an array of bools for the f function.
  @param array The array.
  @return Shortened array.
*/
bool *shorten( bool *array )
{
  bool *halfArray = (bool *)malloc( HALF_BITS * sizeof( bool ) );
  unsigned char row = TWO * array[ 0 ] + array[ 5 ];
  unsigned char col = EIGHT * array[ 1 ] + FOUR * array[ 2 ] +
                      TWO * array[ 3 ] + array[ 4 ];
  unsigned char sValue = s1[ row ][ col ];
  bool *sArray = hexToArray( sValue );
  int j = 0;
  for ( int i = 0; i < FOUR; i++ ) {
    halfArray[ i ] = sArray[ j++ ];
  }
  free( sArray );

  row = TWO * array[ 6 ] + array[ 11 ];
  col = EIGHT * array[ 7 ] + FOUR * array[ 8 ] + TWO * array[ 9 ] + array[ 10 ];
  sValue = s2[ row ][ col ];
  sArray = hexToArray( sValue );
  j = 0;
  for ( int i = FOUR; i < EIGHT; i++ ) {
    halfArray[ i ] = sArray[ j++ ];
  }
  free( sArray );

  row = TWO * array[ 12 ] + array[ 17 ];
  col = EIGHT * array[ 13 ] + FOUR * array[ 14 ] + TWO * array[ 15 ] + array[ 16 ];
  sValue = s3[ row ][ col ];
  j = 0;
  sArray = hexToArray( sValue );
  for ( int i = EIGHT; i < EIGHT + FOUR; i++ ) {
    halfArray[ i ] = sArray[ j++ ];
  }
  free( sArray );

  row = TWO * array[ 18 ] + array[ 23 ];
  col = EIGHT * array[ 19 ] + FOUR * array[ 20 ] + TWO * array[ 21 ] + array[ 22 ];
  sValue = s4[ row ][ col ];
  sArray = hexToArray( sValue );
  j = 0;
  for ( int i = EIGHT + FOUR; i < SIXTEEN; i++ ) {
    halfArray[ i ] = sArray[ j++ ];
  }
  free( sArray );

  row = TWO * array[ 24 ] + array[ 29 ];
  col = EIGHT * array[ 25 ] + FOUR * array[ 26 ] + TWO * array[ 27 ] + array[ 28 ];
  sValue = s5[ row ][ col ];
  sArray = hexToArray( sValue );
  j = 0;
  for ( int i = SIXTEEN; i < SIXTEEN + FOUR; i++ ) {
    halfArray[ i ] = sArray[ j++ ];
  }
  free( sArray );

  row = TWO * array[ 30 ] + array[ 35 ];
  col = EIGHT * array[ 31 ] + FOUR * array[ 32 ] + TWO * array[ 33 ] + array[ 34 ];
  sValue = s6[ row ][ col ];
  sArray = hexToArray( sValue );
  j = 0;
  for ( int i = SIXTEEN + FOUR; i < SIXTEEN + EIGHT; i++ ) {
    halfArray[ i ] = sArray[ j++ ];
  }
  free( sArray );

  row = TWO * array[ 36 ] + array[ 41 ];
  col = EIGHT * array[ 37 ] + FOUR * array[ 38 ] + TWO * array[ 39 ] + array[ 40 ];
  sValue = s7[ row ][ col ];
  sArray = hexToArray( sValue );
  j = 0;
  for ( int i = SIXTEEN + EIGHT; i < SIXTEEN + EIGHT + FOUR; i++ ) {
    halfArray[ i ] = sArray[ j++ ];
  }
  free( sArray );

  row = TWO * array[ 42 ] + array[ 47 ];
  col = EIGHT * array[ 43 ] + FOUR * array[ 44 ] + TWO * array[ 45 ] + array[ 46 ];
  sValue = s8[ row ][ col ];
  sArray = hexToArray( sValue );
  j = 0;
  for ( int i = SIXTEEN + EIGHT + FOUR; i < SIXTEEN + EIGHT + EIGHT; i++ ) {
    halfArray[ i ] = sArray[ j++ ];
  }
  free( sArray );

  return halfArray;
}

/**
  Takes two arrays of bools of the same size, and performs an XOR operation on them,
  returning the resulting bool array.
  @param first The first array.
  @param second The second array.
  @param size The size of the arrays.
  @return The XOR array.
*/
bool *xor( bool *first, bool *second, int size )
{
  bool *array = (bool *)malloc( size * sizeof( bool ) );
  for ( int i = 0; i < size; i++ ) {
    if ( ( first[ i ] || second[ i ] ) && !( first[ i ] && second[ i ] ) ) {
      array[ i ] = 1;
    } else {
      array[ i ] = 0;
    }
  }
  return array;
}

/**
  Performs various encryption operations described in the algorithm.  Operates on a
  32 bit data block, and a key of 48 bits.  Produces a block of 32 bits.
  @param array The array of data blocks.
  @param keyArray Array of keys.
  @return New bool array.
*/
bool *f( bool *array, bool *keyArray )
{
  bool *expandedArray = (bool *)malloc( EXPANDED_SIZE * sizeof( bool ) );
  for ( int i = 0; i < EXPANDED_SIZE; i++ ) {
    expandedArray[ i ] = array[ EBIT[ i ] - 1 ];
  }
  bool *xorArray = xor( expandedArray, keyArray, EXPANDED_SIZE );
  bool *halfArray = shorten( xorArray );
  bool *pArray = permute( P, halfArray, HALF_BITS );
  free( expandedArray );
  free( xorArray );
  free( halfArray );
  return pArray;
}

/**
  Performs a leftshift operation on an array according to the shifts table.
  @param shiftValue The number from the shifts table.
  @param array The array to shift.
  @param size The size of the array.
  @return The shifted array.
*/
bool *leftshift( int shiftValue, bool *array, int size )
{
  bool *shiftArray = (bool *)malloc( size * sizeof( bool ) );
  for ( int i = 0; i < size; i++ ) {
    shiftArray[ i ] = array[ i ];
  }
  bool temp = shiftArray[ 0 ];
  for ( int i = 1; i  < size; i++ ) {
    shiftArray[ i - 1 ] = array[ i ];
  }
  shiftArray[ size - 1 ] = temp;
  if ( shiftValue == 2 ) {
    temp = shiftArray[ 0 ];
    for ( int i = 1; i  < size; i++ ) {
      shiftArray[ i - 1 ] = shiftArray[ i ];
    }
    shiftArray[ size - 1 ] = temp;
  }
  return shiftArray;
}

/**
  Converts a char to an array of bools representing the char in binary.
  @param ch The char to convert.
  @return Array of bools.
*/
bool *charToArray( const unsigned char ch )
{
  unsigned char mask = 0x80;
  bool *array = (bool *)calloc( BLOCK_SIZE, sizeof( bool ) );
  for ( int i = 0; i < BLOCK_SIZE; i++ ) {
    array[ i ] += ch & mask;
    mask >>= 1;
  }
  return array;
}

/**
  Converts a block of chars to bool arrays representing the same data in binary.
  @param block The block to convert.
  @return The array of bools.
*/
bool *blockToArray( const unsigned char *block )
{
  bool *array = (bool *)calloc( NUMBER_OF_BITS, sizeof( bool ) );
  for ( int i = 0; i < BLOCK_SIZE; i++ ) {
    bool *temp = charToArray( block[ i ] );
    for ( int j = 0; j < BLOCK_SIZE; j++ ) {
      array[ BLOCK_SIZE * i + j ] = temp[ j ];
    }
    free( temp );
  }
  return array;
}

/**
  This function takes a pointer, block, to a 8-byte block of bytes it's expected to
  encrypt.  It encrypts it using the 8-byte key given as the second parameter.  When
  it's done, it stores the resulting, encrypted block back in the 8-byte block passed
  as the first parameter.  The second parameter is const, since the function won't need
  to change the key, but the first isn't, since the function uses it to store the
  encrypted block.
  @param block The block to encrypt.
  @param key The key used to encrypt.
*/
void encrypt_block ( unsigned char *block, const unsigned char *key )
{
  unsigned char *cryptedArray = (unsigned char *)malloc( BLOCK_SIZE * sizeof( char ) );
  for ( int i = 0; i < BLOCK_SIZE; i++ ) {
    cryptedArray[ i ] = block[ i ];
  }
  bool *bBlock = blockToArray( cryptedArray );
  bool *bKey = blockToArray( key );
  bool *pKey = permute( PC1, bKey, KEY_SIZE );
  free( bKey );
  bool c0[ HALF_KEY ];
  bool d0[ HALF_KEY ];
  bool *cArray[ NUMBER_OF_SUBKEYS ];
  bool *dArray[ NUMBER_OF_SUBKEYS ];
  for ( int i = 0; i < HALF_KEY; i++ ) {
    c0[ i ] = pKey[ i ];
    d0[ i ] = pKey[ i + HALF_KEY ];
  }
  free(pKey);
  cArray[ 0 ] = leftshift( shifts[ 0 ], c0, HALF_KEY );
  dArray[ 0 ] = leftshift( shifts[ 0 ], d0, HALF_KEY );
  for ( int i = 1; i < NUMBER_OF_SUBKEYS; i++ ) {
    cArray[ i ] = leftshift( shifts[ i ], cArray[ i - 1 ], HALF_KEY );
    dArray[ i ] = leftshift( shifts[ i ], dArray[ i - 1 ], HALF_KEY );
  }
  bool *cdArray[ NUMBER_OF_SUBKEYS ];
  for ( int i = 0; i < NUMBER_OF_SUBKEYS; i++ ) {
    cdArray[ i ] = (bool *)malloc( KEY_SIZE * sizeof( bool ) );
    for ( int j = 0; j < HALF_KEY; j++ ) {
      cdArray[ i ][ j ] = cArray[ i ][ j ];
      cdArray[ i ][ j + HALF_KEY ] = dArray[ i ][ j ];
    }
  }
  for ( int i = 0; i < NUMBER_OF_SUBKEYS; i++ ) {
      free( cArray[ i ] );
      free( dArray[ i ] );
  }
  bool *keyArray[ NUMBER_OF_SUBKEYS ];
  for ( int i = 0; i < NUMBER_OF_SUBKEYS; i++ ) {
    keyArray[ i ] = permute( PC2, cdArray[ i ], SUBKEY_SIZE );
  }
  for ( int i = 0; i < NUMBER_OF_SUBKEYS; i++ ) {
    free( cdArray[ i ] );
  }
  // Step 2
  bool *pBlock = permute( IP, bBlock, NUMBER_OF_BITS );
  bool *l0;
  bool *r0;
  bool *lArray[ NUMBER_OF_SUBKEYS ];
  bool *rArray[ NUMBER_OF_SUBKEYS ];
  for ( int i = 0; i < NUMBER_OF_SUBKEYS; i++ ) {
    lArray[ i ] = (bool *)malloc( HALF_BITS * sizeof( bool ) );
    rArray[ i ] = (bool *)malloc( HALF_BITS * sizeof( bool ) );
  }
  l0 = (bool *)malloc( HALF_BITS * sizeof( bool ) );
  r0 = (bool *)malloc( HALF_BITS * sizeof( bool ) );
  for ( int i = 0; i < HALF_BITS; i++ ) {
    l0[ i ] = pBlock[ i ];
    r0[ i ] = pBlock[ HALF_BITS + i ];
  }
  bool *f0 = f( r0, keyArray[ 0 ] );
  free( pBlock );
  for ( int i = 0; i < HALF_BITS - 1; i++ ) {
    lArray[ 0 ][ i ] = r0[ i ];
  }
  bool *xorArray = xor( l0, f0, HALF_BITS );
  for ( int i = 0; i < HALF_BITS; i++ ) {
    rArray[ 0 ][ i ] = xorArray[ i ];
  }
  free( l0 );
  free( r0 );
  free( f0 );
  free( xorArray );
  for ( int i = 1; i < NUMBER_OF_SUBKEYS; i++ ) {
    for ( int j = 0; j < HALF_BITS; j++ ) {
      lArray[ i ][ j ] = rArray[ i - 1 ][ j ];
    }
    bool *fArray = f( rArray[ i - 1 ], keyArray[ i ] );
    bool *xorArray = xor( lArray[ i - 1 ], fArray, HALF_BITS );
    for ( int j = 0; j < HALF_BITS; j++ ) {
      rArray[ i ][ j ] = xorArray[ j ];
    }
    free( fArray );
    free( xorArray );
  }
  for ( int i = 0; i < NUMBER_OF_SUBKEYS; i++ ) {
    free( keyArray[ i ] );
  }
  bool *fullArray = (bool *)malloc( NUMBER_OF_BITS * sizeof( bool ) );
  for ( int i = 0; i < HALF_BITS; i++ ) {
    fullArray[ i ] = rArray[ SIXTEEN - 1 ][ i ];
    fullArray[ i + HALF_BITS ] = lArray[ SIXTEEN - 1 ][ i ];
  }
  bool *finalArray = permute( IPinv, fullArray, NUMBER_OF_BITS );
  free ( cryptedArray );
  cryptedArray = arrayToBlock( finalArray );
  for ( int i = 0; i < BLOCK_SIZE; i++ ) {
    block[ i ] = cryptedArray[ i ];
  }
  free( cryptedArray );
  free( bBlock );
  free( finalArray );
  free( fullArray );
  for ( int i = 0; i < NUMBER_OF_SUBKEYS; i++ ) {
    free( rArray[ i ] );
    free( lArray[ i ] );
  }
}

/**
  This function takes a pointer, block, to a 8-byte block of bytes it's expected to
  decrypt.  It decrypts it using the 8-byte key given as the second parameter.  When
  it's done, it stores the resulting, decrypted block back in the 8-byte block passed
  as the first parameter.  The second parameter is const, since the function won't need
  to change the key, but the first isn't, since the function uses it to store the
  decrypted block.
  @param block The block to decrypt.
  @param key The key used to decrypt.
*/
void decrypt_block ( unsigned char *block, const unsigned char *key )
{
  //
}
