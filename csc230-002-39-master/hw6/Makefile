CC = gcc
CFLAGS = -g -Wall -std=c99
LDLIBS = -lm

mygrep: pattern.o

mygrep.o: pattern.h

pattern.o: pattern.h

clean:
	rm -f mygrep mygrep.o pattern.o output.txt stderr.txt

test:
	chmod +x test.sh
	./test.sh
