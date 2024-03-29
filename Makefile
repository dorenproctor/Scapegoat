 
JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
		$(JC) $(JFLAGS) $*.java

CLASSES = \
		Scapegoat.java \
		Node.java \
		TreePrinter.java \
		Main.java 

default: classes

classes: $(CLASSES:.java=.class)

clean:
		$(RM) *.class output.svg