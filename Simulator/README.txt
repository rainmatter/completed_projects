README

Note: This project was built in Windows 8

How to build this project

Extract/unpack the tar file file to a folder.
Go to directory in command line.
Type:
		tar -xvf sim.tar
Append the folder directory the environment variable classpath. (If needed?)
Go to the folder in the command line.
Type:
	make -f makefile.mak
This should properly build the project.


How to run the program

Method 1- Run the compiled program
Perform the build instructions above.
Open config.txt and edit to the desired configurations.
single tabs should be between labels and input values.
Ensure you are still in the directory where the compiled program classes are located.
Type:
		java Simulator
	This should run the program.

Method 2- Run the jar file
Ensure the CacheSimulator.jar file is in the directory you are working from.
Type:
	java -jar CacheSimulator.jar
This should run the program.

Method 3- If none of the above work, IDE
Copy and paste files into the src folder of a new NetBeans or other IDE project.
Uncomment, delete or fix package declaration.
Compile and run from main class: Simulator.java
Change the configuration of the caches manually 
