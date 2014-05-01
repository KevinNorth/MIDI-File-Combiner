import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;


public class CodeMusic {

	public static void main(String[] args){
		
		try {
			CoberCaller.Call(args[0]);
			
			MusicParser parser = new MusicParser();
			File src = new File("./cobertura_bin/" + args[0] + ".java");
			File cvg = new File("./report/coverage.xml");
			while (!cvg.exists()) {
				Thread.sleep(500);
			}
			ExecutionPath path = parser.parseFile(cvg, src);
			//The following print line is for testing/debugging use
			//System.out.println(path.getNodes().toString());
			cvg.delete();
			
			String filePath = null;
			
			MusicGenerator.startParseNodes(path, filePath, true);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		} catch (MidiUnavailableException e) {
			e.printStackTrace();
		}
		
	}
	
}
