package midi.combiner;

import java.io.File;
import java.io.IOException;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Track;

public class MIDICombiner {
    public static void main(String[] args)
            throws Exception //Since this is a proof-of-concept program, we won't worry about robust error-handling
    {
        Sequencer mainSequencer = openSequencer();
    }
    
    public static Sequencer openSequencer() throws MidiUnavailableException {
        return MidiSystem.getSequencer();
    }
    
    public static Sequence openMidiFile(String fileName) throws IOException, InvalidMidiDataException {
        File file = new File(fileName);
        return MidiSystem.getSequence(file);
    }
    
    public static void copyEventsToTrack(Track sourceTrack, Track destinationTrack) {
        int numEvents = sourceTrack.size();
        
        for(int i = 0; i < numEvents; i++) {
            MidiEvent event = sourceTrack.get(i);
            destinationTrack.add(event);
        }
    }
}
