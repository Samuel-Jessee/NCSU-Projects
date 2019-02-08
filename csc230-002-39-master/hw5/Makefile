CC = gcc
CFLAGS = -g -Wall -std=c99
LDLIBS = -lm

crypt: des.o

crypt.o: des.h

des.o: des.h

des:

clean:
	rm -f crypt des crypt.o des.o

test:
	chmod +x test.sh
	./test.sh
