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
        Sequence forBodySquence = openMidiFile("for_-_body.mid");
        Sequence forConditionSquence = openMidiFile("for_-_condition.mid");
        
        Track stringTrack = stringSequence.getTracks()[0];
        Track integerTrack = integerSequence.getTracks()[0];
        Track plusTrack = plusSequence.getTracks()[0];
        Track whileBodyTrack = whileBodySequence.getTracks()[0];
        Track whileConditionTrack = whileConditionSequence.getTracks()[0];
        Track forBodyTrack = forBodySquence.getTracks()[0];
        Track forConditionTrack = forConditionSquence.getTracks()[0];
        
        generateWhileDemo(stringTrack, integerTrack, plusTrack, whileBodyTrack,
                whileConditionTrack, stringSequence);
        generateForDemo(stringTrack, plusTrack, forBodyTrack, forConditionTrack,
                stringSequence);
        generateLooplessDemo(stringTrack, plusTrack, stringSequence);
    }
    
    public static void generateWhileDemo(Track stringTrack, Track integerTrack,
            Track plusTrack, Track whileBodyTrack, Track whileConditionTrack,
            Sequence referenceSequence) throws MidiUnavailableException,
            InvalidMidiDataException, IOException
    {
        Sequencer outSequencer = openSequencer(referenceSequence.getDivisionType(),
                referenceSequence.getResolution(), 3);
        
        Track mainTrack1 = outSequencer.getSequence().getTracks()[0];
        Track mainTrack2 = outSequencer.getSequence().getTracks()[1];
        Track mainTrack3 = outSequencer.getSequence().getTracks()[2];
        
        //First measure: Just the string
        copyEventsToTrack(stringTrack, mainTrack1, 0, 0, referenceSequence);

        //Second meausre: Just the int        
        copyEventsToTrack(integerTrack, mainTrack1, 0, 1, referenceSequence);
        
        //Third measure: Just the condition
        copyEventsToTrack(whileConditionTrack, mainTrack1, 0, 2, referenceSequence);
        
        //Fourth measure: While body, string, and add
        copyEventsToTrack(whileBodyTrack, mainTrack1, 0, 3, referenceSequence);
        copyEventsToTrack(stringTrack, mainTrack2, 1, 3, referenceSequence);
        copyEventsToTrack(plusTrack, mainTrack3, 2, 3, referenceSequence);

        // Fifth measure: While body, integer, and add
        copyEventsToTrack(whileBodyTrack, mainTrack1, 0, 4, referenceSequence);
        copyEventsToTrack(integerTrack, mainTrack2, 1, 4, referenceSequence);
        copyEventsToTrack(plusTrack, mainTrack3, 2, 4, referenceSequence);

        // Sixth measure: Just the condition
        copyEventsToTrack(whileConditionTrack, mainTrack1, 0, 5, referenceSequence);
        
        //Seventh measure: While body, string, and add
        copyEventsToTrack(whileBodyTrack, mainTrack1, 0, 6, referenceSequence);
        copyEventsToTrack(stringTrack, mainTrack2, 1, 6, referenceSequence);
        copyEventsToTrack(plusTrack, mainTrack3, 2, 6, referenceSequence);

        // Eighth measure: While body, integer, and add
        copyEventsToTrack(whileBodyTrack, mainTrack1, 0, 7, referenceSequence);
        copyEventsToTrack(integerTrack, mainTrack2, 1, 7, referenceSequence);
        copyEventsToTrack(plusTrack, mainTrack3, 2, 7, referenceSequence);
        
        // Ninth measure: Just the condition one last time
        copyEventsToTrack(whileConditionTrack, mainTrack1, 0, 8, referenceSequence);
        
        saveMidiFile(outSequencer.getSequence(), "while_demo.mid");
    }

    public static void generateForDemo(Track stringTrack, Track plusTrack,
            Track forBodyTrack, Track forConditionTrack, Sequence referenceSequence)
            throws MidiUnavailableException, InvalidMidiDataException, IOException
    {
        Sequencer outSequencer = openSequencer(referenceSequence.getDivisionType(),
                referenceSequence.getResolution(), 3);
        
        Track mainTrack1 = outSequencer.getSequence().getTracks()[0];
        Track mainTrack2 = outSequencer.getSequence().getTracks()[1];
        Track mainTrack3 = outSequencer.getSequence().getTracks()[1];
        
        //First measure: Just the string
        copyEventsToTrack(stringTrack, mainTrack1, 0, 0, referenceSequence);
        
        //Second measure: For condition
        copyEventsToTrack(forConditionTrack, mainTrack1, 0, 1, referenceSequence);

        //Third measure: Concatenate the string
        copyEventsToTrack(stringTrack, mainTrack1, 0, 2, referenceSequence);
        copyEventsToTrack(plusTrack, mainTrack2, 1, 2, referenceSequence);
        copyEventsToTrack(forBodyTrack, mainTrack3, 2, 2, referenceSequence);
        
        //Fourth measure: For condition
        copyEventsToTrack(forConditionTrack, mainTrack1, 0, 3, referenceSequence);

        //Fifth measure: Concatenate the string
        copyEventsToTrack(stringTrack, mainTrack1, 0, 4, referenceSequence);
        copyEventsToTrack(plusTrack, mainTrack2, 1, 4, referenceSequence);
        copyEventsToTrack(forBodyTrack, mainTrack3, 2, 4, referenceSequence);

        //Sixth measure: For condition (the check that evaluates to false)
        copyEventsToTrack(forConditionTrack, mainTrack1, 0, 5, referenceSequence);
        
        saveMidiFile(outSequencer.getSequence(), "for_demo.mid");
    }
    
    public static void generateLooplessDemo(Track stringTrack,
            Track plusTrack, Sequence referenceSequence)
            throws MidiUnavailableException, InvalidMidiDataException, IOException
    {
        Sequencer outSequencer = openSequencer(referenceSequence.getDivisionType(),
                referenceSequence.getResolution(), 2);
        
        Track mainTrack1 = outSequencer.getSequence().getTracks()[0];
        Track mainTrack2 = outSequencer.getSequence().getTracks()[1];
        
        //First measure: Just the string
        copyEventsToTrack(stringTrack, mainTrack1, 0, 0, referenceSequence);

        //Second meausre: Concatenate the string
        copyEventsToTrack(stringTrack, mainTrack1, 0, 1, referenceSequence);
        copyEventsToTrack(plusTrack, mainTrack2, 1, 1, referenceSequence);
        
        //Third meausre: Concatenate the string again
        copyEventsToTrack(stringTrack, mainTrack1, 0, 2, referenceSequence);
        copyEventsToTrack(plusTrack, mainTrack2, 1, 2, referenceSequence);
                
        saveMidiFile(outSequencer.getSequence(), "loopless_demo.mid");
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
        
    public static void saveMidiFile(Sequence sequence, String filename) throws IOException {
        int[] fileTypes = MidiSystem.getMidiFileTypes(sequence);
        File file = new File(filename);
        MidiSystem.write(sequence, fileTypes[0], file);
    }
}
