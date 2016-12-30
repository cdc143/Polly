JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $*.java

CLASSES = \
        PolyParser.java \
        Driver.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
