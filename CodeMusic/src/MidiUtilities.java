//package music.generator;

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

/**
 * Static functions for working with MIDI files.
 */
public class MidiUtilities {    
    /**
     * Opens a MIDI file.
     * 
     * @param fileName The path to the file to open. Relative filepaths are
     *      relative to the program's running directory.
     * @return A <code>Sequence</code> that contains the MIDI file's data
     * @throws IOException If there was a problem reading the file.
     * @throws InvalidMidiDataException If the specified file isn't a MIDI file
     *      or contains invalid MIDI data.
     */
    public static Sequence openMidiFile(String fileName) throws IOException, InvalidMidiDataException {
        File file = new File(fileName);
        return MidiSystem.getSequence(file);
    }
    
    /**
     * Creates a new Sequencer object that can represent a MIDI file.
     *
     * @param divisionType One of
     *      <ul><li><code>Sequence.PPQ</code></li>
     *          <li><code>Sequence.SMPTE_24</code></li>
     *          <li><code>Sequence.SMPTE_25</code></li>
     *          <li><code>Sequence.SMPTE_30</code></li>
     *          <li><code>Sequence.SMPTE_30DROP</code></li></ul>
     *      to indicate how time should be kept in the MIDI file. Sequence.PPQ
     *      is recommended to best work with the other functions in this
     *      utilities library.
     * @param resolution The number of ticks per beat (if
     *      <code>divisionType == Sequence.PPQ</code>) or frames (otherwise).
     *      It is recommended that you open one of the MIDI source files with
     *      <code>openMidiFile()</code>, then obtain its <code>resolution</code>
     *      to pass in here.
     * @param numTracks The number of track to initialize in the file. Remember
     *      that this number can be easily changed after this function returns
     *      by calling <code>sequencer.getSequence().createTrack()</code> and
     *      <code>sequencer.getSequence().deleteTrack()</code>.
     * @return A <code>Sequencer</code> that represents an empty MIDI file
     *      with the specified <code>divisionType</code>,
     *      <code>resolution</code>, and number of tracks.
     * @throws MidiUnavailableException If there is a problem creating the
     *      <code>Sequencer</code>.
     * @throws InvalidMidiDataException If there is a problem creating the
     *      <code>Sequencer</code>.
     */
    public static Sequencer openSequencer(float divisionType, int resolution,
            int numTracks) throws MidiUnavailableException, InvalidMidiDataException {
        Sequencer sequencer = MidiSystem.getSequencer();
        Sequence sequence = new Sequence(divisionType, resolution, numTracks);
        sequencer.setSequence(sequence);
        return sequencer;
    }
    
    /**
     * Copies <code>MidiEvent</code>s from one <code>Track</code> to another.
     * 
     * This function can be used to copy data from one MIDI file to another.
     * Pass the source MIDI file's track as <code>sourceTrack</code> and
     * the destination MIDI file's track as <code>destinationTrack</code>.
     * 
     * @param sourceTrack The <code>Track</code> to copy <code>MidiEvent</code>s
     *      from.
     * @param destinationTrack The <code>Track</code> to copy
     *      <code>MidiEvent</code>s to.
     * @param channel All of the <code>MidiEvent</code>s from the source track
     *      will have their channel bits set to this number before being
     *      copied to the destination track. This number should always be
     *      between 0 and 16 inclusive, and this function has undefined behavior
     *      for other values.
     * @param measure The number of measures to offset the copied
     *      <code>MidiEvent</code>s in the destination file. For example, to
     *      copy events so that everything from the first measure of the source
     *      file is copied to the second measure of the destination file,
     *      pass <code>1</code> to skip the first measure. Pass <code>0</code>
     *      to copy to the same measures.
     * @param sequenceWithTimingFormat A <code>Sequence</code> that has the same
     *      <code>divisionType</code> and <code>resolution</code> as both
     *      the source <code>Track</code>'s <code>Sequence</code> and the
     *      destination <code>Track</code>'s <code>Sequence</code>.
     * @throws InvalidMidiDataException If there is a problem copying MIDI data.
     */
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
    
    /**
     * Creates a copy of a <code>MidiEvent</code> with a different channel.
     * 
     * Java doesn't have a built-in channel property for
     * <code>MidiEvent</code>s, so unfortunately, we have to do some manual
     * bit twiddling.
     * 
     * @param eventToChange The <code>MidiEvent</code> to change the channel of
     * @param channel The channel to change the <code>MidiEvent<code>'s channel
     *      to. This number should always be between 0 and 16 inclusive, and
     *      this function has undefined behavior for other values.
     * @return A copy of <code>eventToChange<code> with its channel bits set
     *      to <code>channel</code>.
     * @throws InvalidMidiDataException If there was a problem creating the
     *      modified <code>MidiEvent</code>.
     */
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

    /**
     * Saves a <code>Sequence</code> to a file.
     * 
     * @param sequence The <code>Sequence</code> to save.
     * @param filename The path to the file to save to. Relative paths are
     *      relative to the program's running directory.
     * @throws IOException If there was a problem saving the file.
     */
    public static void saveMidiFile(Sequence sequence, String filename) throws IOException {
        int[] fileTypes = MidiSystem.getMidiFileTypes(sequence);
        File file = new File(filename);
        MidiSystem.write(sequence, fileTypes[0], file);
    }
}