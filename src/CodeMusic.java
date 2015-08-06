import processing.core.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.*;

;

public class CodeMusic extends PApplet {

	private static List<String> lines;
	private static String filePath;
	private static Sequence sequence;
	private static Sequencer sequencer;
	private static ExecutionPath path;
	private static List<Integer> place;

	public static void main(String[] args) {

		try {

			boolean success = CoberCaller.Call(args[0]);
			if (!success) {
				return;
			}

			MusicParser parser = new MusicParser();
			File src = new File(".\\cobertura_bin\\" + args[0] + ".java");
			File cvg = new File(".\\report\\coverage.xml");
			while (!cvg.exists()) {
				Thread.sleep(500);
			}
			path = parser.parseFile(cvg, src);
			//System.out.println(path.getNodes().toString());
			cvg.delete();

			filePath = "midiOut/" + args[0] + ".midi";

			MusicGenerator.startParseNodes(path, filePath, false);

			lines = Files.readAllLines(src.toPath(), Charset.forName("UTF-8"));

			PApplet.main(new String[] { "--present", "CodeMusic" });

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

	public void setup() {
		size(1600, 900);
		try {
			sequence = MidiSystem.getSequence(new File(filePath));
			sequencer = MidiSystem.getSequencer();
			sequencer.open();
			sequencer.setSequence(sequence);
		} catch (MidiUnavailableException | InvalidMidiDataException
				| IOException e) {
			e.printStackTrace();
		}
		place = path.getLineOrder();
	}

	public void draw() {
		background(255);
		int songProgression = -1;
		if (sequencer.isRunning()) {
			if (sequencer.getMicrosecondPosition() != sequencer
					.getMicrosecondLength()) {
				songProgression = place.get((int) (sequencer
						.getMicrosecondPosition() / 2000000));
			} else {
				songProgression = -1;

				sequencer.setMicrosecondPosition(0);
				sequencer.stop();
			}
		}
		int inc = 1;
		int numTabs = 1;
		int currentLineNum = 0;
		for (String line : lines) {
			currentLineNum++;
			if (!line.replaceAll("\\t", "").contentEquals("")) {
				if (line.charAt(line.length() - 1) == '}') {
					numTabs--;
				}

				if (currentLineNum == songProgression) {
					textSize(20);
					fill(0, 0, 200);
				} else {
					textSize(18);
					fill(0, 0, 0);
				}
				text(line, numTabs * 30, inc * 25);
				inc++;

				if (line.charAt(line.length() - 1) == '{') {
					numTabs++;
				}
			}
		}
	}

	public void keyPressed() {
		sequencer.setMicrosecondPosition(0);
		sequencer.start();
	}

}
