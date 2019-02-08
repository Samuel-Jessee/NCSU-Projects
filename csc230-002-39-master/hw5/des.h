/**
  @file des.h
  @author Samuel Jessee (sijessee)
  This header file contains prototypes for the non-static functions in des.c that are
  needed by the main program.
*/

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
void encrypt_block( unsigned char *block, const unsigned char *key );

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
void decrypt_block( unsigned char *block, const unsigned char *key );
