package midi.combiner;

import java.io.File;
import java.io.IOException;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
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
        
        Track mainTrack1 = mainSequencer.getSequence().getTracks()[0];
        Track mainTrack2 = mainSequencer.getSequence().getTracks()[1];
        
        copyEventsToTrack(file1Track, mainTrack1, 0, 0, file1Sequence);
        copyEventsToTrack(file2Track, mainTrack2, 1, 1, file2Sequence);
        
//        setTrackInstrument(mainTrack1, 1, 0); //Set piano
//        setTrackInstrument(mainTrack2, 41, 1); //Set violin
        
        saveMidiFile(mainSequencer.getSequence());
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
    
    public static void copyEventsToTrack(Track sourceTrack, Track destinationTrack,
            int channel, int measure, Sequence sequenceWithTimingFormat)
            throws InvalidMidiDataException {
        int numEvents = sourceTrack.size();
        
        int ticksPerMeasure = sequenceWithTimingFormat.getResolution() * 4;
        int ticksToAdd = ticksPerMeasure * measure;
        
        System.out.println(numEvents);
        
        for(int i = 0; i < numEvents; i++) {
            MidiEvent event = sourceTrack.get(i);
            
            // Messages with status 1111xxxx don't have channel information
            if((event.getMessage().getStatus() & 0xF0) != 0xF0)
            {
                event = changeEventChannel(sourceTrack.get(i), channel);
            }
            
            //Move the position of the event to the proper measure
            event = new MidiEvent(event.getMessage(),
                    event.getTick() + ticksToAdd);

            destinationTrack.add(event);
        }
    }
    
    public static MidiEvent changeEventChannel(MidiEvent eventToChange, int channel)
            throws InvalidMidiDataException
    {
        MidiMessage messageToChange = eventToChange.getMessage();
        
        int channelBitmask = channel | 0xF0;
        System.out.println("Channel bitmask: " + Integer.toBinaryString(channelBitmask));
        
        int newMessageStatusBitmask = (messageToChange.getStatus() | 0x0F);
        System.out.println("Status bitmask: " + Integer.toBinaryString(newMessageStatusBitmask));
        System.out.println("Status: " + Integer.toBinaryString(messageToChange.getStatus()));

        int newMessageStatus = newMessageStatusBitmask & channelBitmask;
        
        MidiMessage newMessage;
        
        byte[] oldMessageBytes = messageToChange.getMessage();
        if(messageToChange.getLength() == 2) {
            newMessage = new ShortMessage(newMessageStatus, oldMessageBytes[1], 0);
        } else {
            newMessage = new ShortMessage(newMessageStatus, oldMessageBytes[1], oldMessageBytes[2]);
        }
        
        MidiEvent newEvent = new MidiEvent(newMessage, eventToChange.getTick());
        return newEvent;
    }
    
    public static void setTrackInstrument(Track track, int instrumentNumber,
            int channel)
            throws IllegalArgumentException
    {
        if(instrumentNumber > 128 || instrumentNumber <= 0) {
            throw new IllegalArgumentException("Instrument # "
                    + Integer.toString(instrumentNumber) +
                    " is greater than 127 / less than 1");
        }

        if(channel < 0|| channel >= 16) {
            throw new IllegalArgumentException("Channel # "
                    + Integer.toString(channel) +
                    " is greater than 16 / less than 0");
        }
        
        ShortMessage instChange;
        try
        {
            instChange = new ShortMessage(ShortMessage.PROGRAM_CHANGE,
                channel, instrumentNumber, 0);
            track.add(new MidiEvent(instChange, 0));
        } catch (InvalidMidiDataException ex)
        {
            // Since we make sure we have a valid instrumentNumber,
            // we shouldn't ever get an InvalidMidiDataException from
            // an exceptional runtime case, so we want to crash hard to
            // indicate a programmatic error.
            throw new RuntimeException(ex);
        }
    }
        
    public static void saveMidiFile(Sequence sequence) throws IOException {
        int[] fileTypes = MidiSystem.getMidiFileTypes(sequence);
        File file = new File("out.mid");
        MidiSystem.write(sequence, fileTypes[0], file);
    }
}
