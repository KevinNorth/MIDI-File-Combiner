Music++
==================

Takes in an input Java program, analyzes it to get its runtime path, and outputs a MIDI file based on that analysis.

# Installation Instructions

## How to set Environment Variables

If the Environment Variables are not set correctly, the Music++
system may not be able to run, returning an error regarding
javac.  This is due to some of the calls that CoberCaller must
make in order to run cobertura properly.

First, ensure that the machine has the Java Developer's Kit installed.
Once it is installed, the environment variables need to be set to use
the path to the JDK in order to find the javac command.

### Setting Environment Variables Through the Control Panel

 - Control Panel > System > Advanced System Settings > Environment Variables
 - Click Path > Click Edit > Add Path to JDK to the FRONT of the path variable, separating it with a semicolon.  Do NOT  delete the existing value in the variable; this is not necessary and may cause issues with your environment.
 - Click OK
 - Click CLASSPATH > Click Edit > Add path to JDK to the front like with Path.
 - The Path should be something like
  `C:\Program Files\Java\jdk1.x.x_xx\bin`
  with the x's replaced by the version of the jdk installed.

### Setting Environment Variables Through the Command Line

The environment variables can be set using the command line, although this option only temporarily changes the variable (it reverts once the cmd session closes).

    \CodeMusic > set PATH=C:\Program Files\Java\jdk1.x.x_xx\bin;%PATH%
    \CodeMusic > set CLASSPATH=C:\Program Files\Java\jdk1.x.x_xx\bin;%CLASSPATH%

#To Run CodeMusic

To run the program from the windows cmd box, use the following command:

    \CodeMusic > java CodeMusic <file-name>

`file-name` should be the name of a java program in the `cobertura_bin` directory.  It should be a correct java program — CodeMusic has no checks for input program correctness.  The argument does not need the .java file extension, only the file name.

To run the program from Eclipse, be sure the run configuration has `file-name` as its argument.  The source code in eclipse will compile the .class files into a directory called bin, rather than the root CodeMusic directory.  Otherwise, the directory structure is the same.

# CodeMusic Directory Structure

    Directory . . . . . . . Contains
    /CodeMusic
     	/bin                Class files
     	/cobertura_bin      Input java files
     	/cobertura-1.9.4.1  Cobertura tool
     	/INSTRUCTIONS       Information on how to run Music++
     	/instrument_bin     Intermediate cobertura files
     	/midi               Midi clips
     	/report             Intermediate cobertura files
        /src                Music++ system source code
        *.class             Class files
