CC = gcc
CFLAGS = -g -Wall -std=c99
LDLIBS = -lm

spellcheck: text.o

spellcheck.o: text.h

text.o: text.h

clean:
	rm -f spellcheck spellcheck.o text.o

test:
	chmod +x test.sh
	./test.sh
