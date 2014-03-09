import java.io.IOException;



public class CoberCaller {

	public static void Call(String fileIn) throws IOException{
		
		//Get the Runtime instance so the command line can be called
		Runtime run = Runtime.getRuntime();
		
		//TODO: Need to copy the class file that we are running Music++ on to the bin folder
		//Not sure of the most efficient way to do this
		
		//TODO: Decide whether to use try/catch or to throw the IOExceptions upwards
		
		//Run the command to instrument the class files found in .\bin
		
		run.exec("..\\cobertura-1.9.4.1\\cobertura-instrument.bat --destination .\\instrument_bin .\\bin");
		
		
		//Back up the datafile
		//Note from Zhen:  if you don't backup, it will write to the datafile, and the code coverage would accumulate
		run.exec("mkdir backup");
		run.exec("xcopy /y cobertura.ser .\\backup");
		
		//Copy back the empty datafile
		run.exec("xcopy /y .\\backup\\cobertura.ser .");
		
		//Run the tool and write to the datafile
		run.exec("java -cp ..\\cobertura-1.9.4.1\\cobertura.jar;.\\instrument_bin;.\\bin;.\\bin " + 
								fileIn + "-Dnet.sourceforge.cobertura.datafile=.\\cobertura.ser ");
		
		//Generate the report
		run.exec("..\\cobertura-1.9.4.1\\cobertura-report.bat --format xml --datafile .\\cobertura.ser --destination . ");
		

	}//End Call


}//End CoberCaller