## Setup
	> In order to run tests, environment variable for the application should be set as TONS_HOME
	> Environment variable must point to folder where the input JSON files will be located
	> For the tests, they are located inside "files" under the application folder, so TONS_HOME should look like <PATH_TO_YOUR_APPLICATION_FOLDER>/files

	Mac OS X example:
		run:
			edit ~/.bash_profile
		add lines:
			export TONS_HOME=/Users/bgvoka/dev/tons/files
			export PATH=$PATH:TONS_HOME
		run:
			source ~/.bash_profile
		restart system so it should load the variable

## How To Run "tons" application
	> First a jar file must be created via "mvn clean install or mvn package" command within the application directory. (If TONS_HOME is not set, run "mvn clean install -DskipTests" to skip the tests.)
	> After the jar is built, go into target/ folder
	> Run the application with command "java -jar tons-0.0.1-SNAPSHOT.jar <PATH_TO_INPUT_FILE>/<FILE_NAME>.json <PATH_TO_OUTPUT_FILE>/<FILE_NAME>.json"
	> Output file should be created on specified output path as 2nd parameter

## Example
	> In tests folder, under rs/tons/AppTest.java there is testMe() method that runs the example with the json file from pdf as if it was ran from command line with specified arguments.
	> In tests folder, under rs/tons/AlgorithmTest.java are the tests for the algorithm for given example with A->B...

This was a way for me to utilize the JAVA 8 API in order to test how it deals with structural recursion but it still doesn't bring any cool experience