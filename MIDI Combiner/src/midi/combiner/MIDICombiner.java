package midi.combiner;

import java.io.File;
import java.io.IOException;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;

public class MIDICombiner {
    public static void main(String[] args)
            throws Exception //Since this is a proof-of-concept program, we won't worry about robust error-handling
    {
        Sequencer sequencer = openSequencer();
    }
    
    public static Sequencer openSequencer() throws MidiUnavailableException {
        return MidiSystem.getSequencer();
    }
    
    public static void openMidiFile(String fileName, Sequencer sequencer) throws IOException, InvalidMidiDataException {
        File file = new File(fileName);
        Sequence seq = MidiSystem.getSequence(file);
        sequencer.setSequence(seq);
    }
}
