MAVEN_CLEAN = mvn clean
MAVEN_CLEAN_COMPILE = mvn clean compile
RM_RF = rm -rf
TRASH = html latex hs*

all:
	$(MAVEN_CLEAN_COMPILE) javafx:run

install:
	$(MAVEN_CLEAN_COMPILE) javafx:jlink

dist: install

tests:
	$(MAVEN_CLEAN_COMPILE) test

dvi:
	doxygen doxyfile

clean:
	$(RM_RF) $(TRASH) 
	$(MAVEN_CLEAN)

