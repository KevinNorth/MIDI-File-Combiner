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
        Sequence file1Sequence = openMidiFile("1.mid");
        Sequence file2Sequence = openMidiFile("2.mid");

        Sequencer mainSequencer = openSequencer(file1Sequence.getDivisionType(),
                file1Sequence.getResolution(), 2);

        
        Track file1Track = file1Sequence.getTracks()[0];
        Track file2Track = file2Sequence.getTracks()[0];
        
        Track mainTrack1 = mainSequencer.getSequence().createTrack();
        Track mainTrack2 = mainSequencer.getSequence().createTrack();
        
        copyEventsToTrack(file1Track, mainTrack1);
        copyEventsToTrack(file2Track, mainTrack2);
        
        
    }
    
    public static Sequencer openSequencer(float divisionType, int resolution,
            int numTracks) throws MidiUnavailableException, InvalidMidiDataException {
        Sequencer sequencer = MidiSystem.getSequencer();
        Sequence sequence = new Sequence(divisionType, resolution, numTracks);
        sequencer.setSequence(sequence);
        return sequencer;
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
