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
        Sequence stringSequence = openMidiFile("String.mid");
        Sequence integerSequence = openMidiFile("Integer.mid");
        Sequence plusSequence = openMidiFile("Plus.mid");
        Sequence whileBodySequence = openMidiFile("While_Body.mid");
        Sequence whileConditionSequence = openMidiFile("whileCondition.mid");
        
        Sequencer outSequencer = openSequencer(stringSequence.getDivisionType(),
                stringSequence.getResolution(), 3);

        Track stringTrack = stringSequence.getTracks()[0];
        Track integerTrack = integerSequence.getTracks()[0];
        Track plusTrack = plusSequence.getTracks()[0];
        Track whileBodyTrack = whileBodySequence.getTracks()[0];
        Track whileConditionTrack = whileConditionSequence.getTracks()[0];
        
        Track mainTrack1 = outSequencer.getSequence().getTracks()[0];
        Track mainTrack2 = outSequencer.getSequence().getTracks()[1];
        Track mainTrack3 = outSequencer.getSequence().getTracks()[2];
        
        //First measure: Just the string
        copyEventsToTrack(stringTrack, mainTrack1, 0, 0, stringSequence);

        //Second meausre: Just the int        
        copyEventsToTrack(integerTrack, mainTrack1, 0, 1, stringSequence);
        
        //Third measure: Just the condition
        copyEventsToTrack(whileConditionTrack, mainTrack1, 0, 2, whileConditionSequence);
        
        //Fourth measure: While body, string, and add
        copyEventsToTrack(whileBodyTrack, mainTrack1, 0, 3, whileBodySequence);
        copyEventsToTrack(stringTrack, mainTrack2, 1, 3, stringSequence);
        copyEventsToTrack(plusTrack, mainTrack3, 2, 3, plusSequence);

        // Fifth measure: While body, integer, and add
        copyEventsToTrack(whileBodyTrack, mainTrack1, 0, 4, whileBodySequence);
        copyEventsToTrack(integerTrack, mainTrack2, 1, 4, integerSequence);
        copyEventsToTrack(plusTrack, mainTrack3, 2, 4, plusSequence);

        // Sixth measure: Just the condition
        copyEventsToTrack(whileConditionTrack, mainTrack1, 0, 5, whileConditionSequence);
        
        //Seventh measure: While body, string, and add
        copyEventsToTrack(whileBodyTrack, mainTrack1, 0, 6, whileBodySequence);
        copyEventsToTrack(stringTrack, mainTrack2, 1, 6, stringSequence);
        copyEventsToTrack(plusTrack, mainTrack3, 2, 6, plusSequence);

        // Eighth measure: While body, integer, and add
        copyEventsToTrack(whileBodyTrack, mainTrack1, 0, 7, whileBodySequence);
        copyEventsToTrack(integerTrack, mainTrack2, 1, 7, integerSequence);
        copyEventsToTrack(plusTrack, mainTrack3, 2, 7, plusSequence);
        
        // Ninth measure: Just the condition one last time
        copyEventsToTrack(whileConditionTrack, mainTrack1, 0, 8, whileConditionSequence);
        
        saveMidiFile(outSequencer.getSequence());
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
        int newMessageStatusBitmask = (messageToChange.getStatus() | 0x0F);
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
