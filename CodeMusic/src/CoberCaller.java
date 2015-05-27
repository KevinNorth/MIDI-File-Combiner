import java.io.File;
import java.io.IOException;
import org.apache.commons.lang3.SystemUtils;

public class CoberCaller {

	public static boolean Call(String fileIn) throws IOException, InterruptedException{

		//Get the Runtime instance so the command line can be called
		//This should work in the major OSs, and is confirmed to work in Windows
		Runtime run = Runtime.getRuntime();

		if(!fileIn.toLowerCase().endsWith(".java")) {
			fileIn += ".java";
		}

		System.out.println(1);
		System.out.flush();

		File inputFile = new File(fileIn);
		if(!inputFile.exists()) {
			System.out.println(fileIn + " does not exist.");
			return false;
		}

		System.out.println(2);
		System.out.flush();

		//Compile the .java file in the bin folder
		//Currently the program accomplishes this by compiling in Java 1.6
		run.exec("javac -target 1.6 -source 1.6 " + fileIn);

		//Wait until the class file is created
		File classfile = new File("cobertura_bin/" + fileIn + ".class");
		while (!classfile.exists()) {
			Thread.sleep(500);
		}

		System.out.println(3);
		System.out.flush();

		//Run the command to instrument the class files found in .\bin and create cobertura.ser
		//<Directory of Cobertura>\cobertura-instrument.bat --destination <Destination of the Instrumented Class> <Location of Class to Instrument>
		//Instrumenting the classes formats them so that cobertura can properly collect data
		if(SystemUtils.IS_OS_WINDOWS) {
			run.exec("./cobertura-1.9.4.1/cobertura-instrument.bat --destination ./instrument_bin ./cobertura_bin");
		} else {
			run.exec("./cobertura-1.9.4.1/cobertura-instrument.sh --destination ./instrument_bin ./cobertura_bin");
		}

		//Wait until the instrumented file and data file are created
		File instrfile = new File("instrument_bin/" + fileIn + ".class");
		File datafile = new File("./cobertura.ser");
		while (!instrfile.exists() || !datafile.exists()) {
			Thread.sleep(500);
		}

		System.out.println(4);
		System.out.flush();

		//Run the tool and write to the datafile
		//Running the tool on UNIX presents permission issues on the instrument_bin and cobertura_bin directories
		run.exec("java -cp ./cobertura-1.9.4.1/cobertura.jar;./instrument_bin;./cobertura_bin;./cobertura_bin " +
								fileIn + " -Dnet.sourceforge.cobertura.datafile=./cobertura.ser ");

		//TODO:  Enhance this wait condition to allow for longer-running programs
		//Wait for the system to complete data collection
		Thread.sleep(2000);

		//Generate the report
		if(SystemUtils.IS_OS_WINDOWS) {
			run.exec("./cobertura-1.9.4.1/cobertura-report.bat --format xml --datafile ./cobertura.ser --destination ./report ");
		} else {
			run.exec("./cobertura-1.9.4.1/cobertura-report.sh --format xml --datafile ./cobertura.ser --destination ./report ");
		}

		//Wait until the report is generated
		File rptfile = new File("./report/coverage.xml");
		while (!rptfile.exists()) {
			Thread.sleep(500);
		}

		System.out.println(5);
		System.out.flush();

		//Scrub the intermediate files produced so that only the report is left
		classfile.delete();
		instrfile.delete();
		datafile.delete();
//		bkpfile.delete();

		return true;
	}//End Call


}//End CoberCaller
