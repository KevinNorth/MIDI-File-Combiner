import java.io.File;
import java.io.IOException;



public class CoberCaller {
	
	public static void Call(String fileIn) throws IOException, InterruptedException{
		
		//Get the Runtime instance so the command line can be called
		//This should work in the major OSs, and is confirmed to work in Windows
		Runtime run = Runtime.getRuntime();
		
		//Compile the .java file in the bin folder
		//Currently the program accomplishes this by compiling in Java 1.6
		run.exec("javac -target 1.6 -source 1.6 ./cobertura_bin/" + fileIn + ".java");
		
		//Wait until the class file is created
		File classfile = new File("cobertura_bin/" + fileIn + ".class");
		while (!classfile.exists()) {
			Thread.sleep(500);
		}
		
		//Run the command to instrument the class files found in .\bin and create cobertura.ser
		//<Directory of Cobertura>\cobertura-instrument.bat --destination <Destination of the Instrumented Class> <Location of Class to Instrument>
		//Instrumenting the classes formats them so that cobertura can properly collect data
		run.exec("./cobertura-1.9.4.1/cobertura-instrument.bat --destination ./instrument_bin ./cobertura_bin");
		
		//Wait until the instrumented file and data file are created
		File instrfile = new File("instrument_bin/" + fileIn + ".class");
		File datafile = new File("./cobertura.ser");
		while (!instrfile.exists() || !datafile.exists()) {
			Thread.sleep(500);
		}
		
//		//Back up the data file
//		//Note from Zhen:  if you don't backup, it will write to the datafile, and the code coverage would accumulate
//		run.exec("xcopy /y cobertura.ser ./backup/");
//		
//		//Wait until the data file is backed up
//		File bkpfile = new File("./backup/cobertura.ser");
//		while (!bkpfile.exists()) {
//			System.out.println("Wait");
//			Thread.sleep(500);
//		}
//		
//		//Copy back the empty datafile
//		run.exec("xcopy /y ./backup/cobertura.ser .");
//		
//		//Give the system time to copy the file back
//		Thread.sleep(700);
//		NOTE:  The backup file here may not be necessary since the CoberCaller clears out all of the 
//		files it creates
		
		//Run the tool and write to the datafile
		run.exec("java -cp ./cobertura-1.9.4.1/cobertura.jar;./instrument_bin;./cobertura_bin;./cobertura_bin " + 
								fileIn + " -Dnet.sourceforge.cobertura.datafile=./cobertura.ser ");
		
		//TODO:  Enhance this wait condition to allow for longer-running programs
		//Wait for the system to complete data collection
		Thread.sleep(2000);
		
		//Generate the report
		run.exec("./cobertura-1.9.4.1/cobertura-report.bat --format xml --datafile ./cobertura.ser --destination ./report ");
		
		//Wait until the report is generated
		File rptfile = new File("./report/coverage.xml");
		while (!rptfile.exists()) {
			Thread.sleep(500);
		}
		
		//Scrub the intermediate files produced so that only the report is left
		classfile.delete();
		instrfile.delete();
		datafile.delete();
//		bkpfile.delete();

	}//End Call


}//End CoberCaller