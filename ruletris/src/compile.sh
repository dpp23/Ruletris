rm net/sourceforge/jetris/*.class
rm net/sourceforge/jetris/io/*.class
rm ruletris/*.class
javac -cp ../../ExternalLibraries/bsh-2.0b4.jar:../../ExternalLibraries/miglayout-4.0-swing.jar:. net/sourceforge/jetris/*.java
javac -cp ../../ExternalLibraries/bsh-2.0b4.jar:../../ExternalLibraries/miglayout-4.0-swing.jar:. net/sourceforge/jetris/io/*.java 
javac -cp ../../ExternalLibraries/bsh-2.0b4.jar:../../ExternalLibraries/miglayout-4.0-swing.jar:. ruletris/*.java

