package music.generator;

import code.representation.DataStructureNode;
import code.representation.DataStructureNodeType;
import code.representation.ExecutionPath;
import code.representation.Node;
import code.representation.Operation;
import code.representation.ScopeNode;
import code.representation.ScopeNodeType;
import java.io.IOException;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Track;

public final class MusicGenerator {
    private static final int NUM_CHANNELS = 16;
    
    /*
     * These four maps keep track of the different input MIDI files that
     * our programs use and associate them to the Java structures they
     * represent.
     */
    /** Keeps track of data structure MIDI sounds. */
    private static Map<DataStructureNodeType, Track> DataStructureMap = null;
    /** Keeps track of while/for loop/is statement body MIDI sounds. */
    private static Map<ScopeNodeType, Track> ScopeBodyMap = null;
    /** Keeps track of while/for loop/is statement condition MIDI sounds. */
    private static Map<ScopeNodeType, Track> ScopeConditionMap = null;
    /** Keeps track of the sounds of adding/subtracting/dividing/multiplying. */
    private static Map<Operation, Track> OperationMap = null;

    /** Used by various MidiUtility functions to provide some default values. */
    private static Sequence ReferenceSequence = null;
    
    /** Prepares the MIDI sound maps for use.
     * 
     * Each of the sound maps is lazy-loaded by this function,
     * so if they've already been initialized, this function won't
     * change them.
     */
    private static void initMaps() throws IOException, InvalidMidiDataException {
        if(DataStructureMap == null) {
            DataStructureMap = new EnumMap<>(DataStructureNodeType.class);
            
            Sequence booleanSequence = MidiUtilities.openMidiFile("midi/data_structures/boolean.mid");
            Sequence byteSequence = MidiUtilities.openMidiFile("midi/data_structures/byte.mid");
            Sequence charSequence = MidiUtilities.openMidiFile("midi/data_structures/char.mid");
            Sequence doubleSequence = MidiUtilities.openMidiFile("midi/data_structures/double.mid");
            Sequence floatSequence = MidiUtilities.openMidiFile("midi/data_structures/float.mid");
            Sequence integerSequence = MidiUtilities.openMidiFile("midi/data_structures/integer.mid");
            Sequence stringSequence = MidiUtilities.openMidiFile("midi/data_structures/string.mid");

            Track booleanTrack = booleanSequence.getTracks()[0];
            Track byteTrack = byteSequence.getTracks()[0];
            Track charTrack = charSequence.getTracks()[0];
            Track doubleTrack = doubleSequence.getTracks()[0];
            Track floatTrack = floatSequence.getTracks()[0];
            Track integerTrack = integerSequence.getTracks()[0];
            Track stringTrack = stringSequence.getTracks()[0];
            
            DataStructureMap.put(DataStructureNodeType.BOOLEAN, booleanTrack);
            DataStructureMap.put(DataStructureNodeType.BYTE, byteTrack);
            DataStructureMap.put(DataStructureNodeType.CHAR, charTrack);
            DataStructureMap.put(DataStructureNodeType.DOUBLE, doubleTrack);
            DataStructureMap.put(DataStructureNodeType.FLOAT, floatTrack);
            DataStructureMap.put(DataStructureNodeType.INT, integerTrack);
            DataStructureMap.put(DataStructureNodeType.STRING, stringTrack);
        }

        if(ScopeBodyMap == null) {
            ScopeBodyMap = new EnumMap<>(ScopeNodeType.class);
            
            Sequence doWhileSequence = MidiUtilities.openMidiFile("midi/scope_bodies/do_while.mid");
            Sequence elseIfSequence = MidiUtilities.openMidiFile("midi/scope_bodies/else_if.mid");
            Sequence elseSequence = MidiUtilities.openMidiFile("midi/scope_bodies/else.mid");
            Sequence ifSequence = MidiUtilities.openMidiFile("midi/scope_bodies/if.mid");
            Sequence forSequence = MidiUtilities.openMidiFile("midi/scope_bodies/for.mid");
            Sequence whileSequence = MidiUtilities.openMidiFile("midi/scope_bodies/while.mid");
            
            Track doWhileTrack = doWhileSequence.getTracks()[0];
            Track elseIfTrack = elseIfSequence.getTracks()[0];
            Track elseTrack = elseSequence.getTracks()[0];
            Track ifTrack = ifSequence.getTracks()[0];
            Track forTrack = forSequence.getTracks()[0];
            Track whileTrack = whileSequence.getTracks()[0];
            
            ScopeBodyMap.put(ScopeNodeType.DOWHILE, doWhileTrack);
            ScopeBodyMap.put(ScopeNodeType.ELSEIF, elseIfTrack);
            ScopeBodyMap.put(ScopeNodeType.ELSE, elseTrack);
            ScopeBodyMap.put(ScopeNodeType.IF, ifTrack);
            ScopeBodyMap.put(ScopeNodeType.FOR, forTrack);
            ScopeBodyMap.put(ScopeNodeType.WHILE, whileTrack);
        }

        if(ScopeConditionMap == null) {
            ScopeConditionMap = new EnumMap<>(ScopeNodeType.class);

            Sequence forSequence = MidiUtilities.openMidiFile("midi/scope_conditions/for.mid");
            Sequence whileSequence = MidiUtilities.openMidiFile("midi/scope_conditions/while.mid");
            Sequence ifSequence = MidiUtilities.openMidiFile("midi/scope_conditions/if.mid");
            Sequence elseIfSequence = MidiUtilities.openMidiFile("midi/scope_conditions/else_if.mid");
            Sequence doWhileSequence = MidiUtilities.openMidiFile("midi/scope_conditions/do_while.mid");
            
            Track doWhileTrack = doWhileSequence.getTracks()[0];
            Track elseIfTrack = elseIfSequence.getTracks()[0];
            Track ifTrack = ifSequence.getTracks()[0];
            Track forTrack = forSequence.getTracks()[0];
            Track whileTrack = whileSequence.getTracks()[0];
            
            ScopeConditionMap.put(ScopeNodeType.DOWHILE, doWhileTrack);
            ScopeConditionMap.put(ScopeNodeType.ELSEIF, elseIfTrack);
            ScopeConditionMap.put(ScopeNodeType.IF, ifTrack);
            ScopeConditionMap.put(ScopeNodeType.FOR, forTrack);
            ScopeConditionMap.put(ScopeNodeType.WHILE, whileTrack);
        }

        if(OperationMap == null) {
            OperationMap = new EnumMap<>(Operation.class);
            
            Sequence plusSequence = MidiUtilities.openMidiFile("midi/operations/plus.mid");
            Sequence minusSequence = MidiUtilities.openMidiFile("midi/operations/minus.mid");
            Sequence multiplySequence = MidiUtilities.openMidiFile("midi/operations/multiply.mid");
            Sequence divideSequence = MidiUtilities.openMidiFile("midi/operations/divide.mid");
            
            Track plusTrack = plusSequence.getTracks()[0];
            Track minusTrack = minusSequence.getTracks()[0];
            Track multiplyTrack = multiplySequence.getTracks()[0];
            Track divideTrack = divideSequence.getTracks()[0];
            
            OperationMap.put(Operation.ADD, plusTrack);
            OperationMap.put(Operation.SUBTRACT, minusTrack);
            OperationMap.put(Operation.MULTIPLY, multiplyTrack);
            OperationMap.put(Operation.DIVIDE, divideTrack);
        }
        
        if(ReferenceSequence == null) {
            ReferenceSequence = MidiUtilities.openMidiFile("midi/data_structures/string.mid");
        }
    }

    /**
     * Takes the abstract representation of a Java program and creates a MIDI
     * file based on it.
     * 
     * @param rootNode The root of the abstract representation tree.
     * @param shouldSaveMidiFile <code>true</code> if the MIDI file should be
     *      saved after it's generated
     * @return A <code>Sequence</code> that plays the music demonstrating the
     *      execution path represented by <code>rootNode</code>
     * @throws IOException If there was a problem opening or saving any MIDI
     *      files
     * @throws InvalidMidiDataException If there was a problem processing MIDI
     *      information
     * @throws MidiUnavailableException If there was a problem processing MIDI
     *      information
     */
    public static Sequencer startParseNodes(ExecutionPath rootNode,
            boolean shouldSaveMidiFile) throws IOException,
            InvalidMidiDataException,
            MidiUnavailableException {
        initMaps();
        
        Sequencer midi = MidiUtilities.openSequencer(
                ReferenceSequence.getDivisionType(),
                ReferenceSequence.getResolution(), NUM_CHANNELS);
        
        int measureNumber = 0;
        
        for(Node node : rootNode.getNodes()) {
            measureNumber = parseNodes(new LinkedList<ScopeNode>(), node, midi,
                    measureNumber);
        }

        if(shouldSaveMidiFile) {
            MidiUtilities.saveMidiFile(midi.getSequence(), "out.mid");
        }
        
        return midi;
    }

    /**
     * Traverses a subtree of the input abstract representation and creates the
     * MIDI for it. Used to recursively walk the abstract representation and
     * create its MIDI file.
     * 
     * @param parentNodes All of <code>currentNode</code>'s ancestors, in order
     *      from the root of the abstract representation tree to
     *      <code>currentNode</code>'s direct parent.
     * @param currentNode The node at the root of the subtree to process.
     * @param midi The <code>Sequencer</code> to be modifying to create the
     *      associated MIDI file. This argument will be modified in place.
     * @param measureNumber The current measure number of the MIDI file. (Start
     *      from 0 for the first measure.)
     * @return The measure number immediately after this subtree's musical
     *      representation ends. The next measure of music should be appended
     *      at this measure. (Starts from 0 for the first measure.)
     * @throws InvalidMidiDataException If there's a problem creating the MIDI
     *      file
     */
    private static int parseNodes(List<ScopeNode> parentNodes, Node currentNode,
        Sequencer midi, int measureNumber) throws InvalidMidiDataException {
            
        if(currentNode instanceof DataStructureNode) {
            addLayers(parentNodes, currentNode, midi, measureNumber);
            return measureNumber + 1;
        } else {
            // Scope node
            ScopeNode currentScopeNode = (ScopeNode) currentNode;

            List<ScopeNode> newParentNodes = new LinkedList<>(parentNodes);
            newParentNodes.add(currentScopeNode);

            for(int i = 0; i < currentScopeNode.getNumIterations(); i++) {
                // Condition check at each iteration
                if(i != 0 || currentScopeNode.playComparisonAtBeginning()) {
                    addLayers(parentNodes, currentScopeNode, midi, measureNumber);
                }
                
                measureNumber++;
                
                // Recursively generate the music for the node's children
                for(Node child : currentScopeNode.getChildren())
                {
                    measureNumber =
                            parseNodes(newParentNodes, child, midi,
                                measureNumber);
                }
            }

            // For final condition check
            if(currentScopeNode.playComparisonAtEnd()) {
                addLayers(parentNodes, currentScopeNode, midi, measureNumber);
            }
            
            return measureNumber + 1;
        }
    }
   
    /**
     * Appends a new measure to a <code>Sequence</code> based on a particular
     * node in the abstract representation tree.
     * 
     * This implementation is incomplete; instead of modifying the MIDI file,
     * it outputs information about how it would modify the MIDI file to STDOUT.
     * This is for intermediate testing.
     * 
     * @param parentNodes All of <code>currentNode</code>'s ancestors, in order
     *      from the root of the abstract representation tree to
     *      <code>currentNode</code>'s direct parent.
     * @param currentNode The node to add a measure of music for.
     * @param midi The <code>Sequencer</code> to be modifying to create the
     *      associated MIDI file. This argument will be modified in place.
     * @param measureNumber The current measure number of the MIDI file. (Start
     *      from 0 for the first measure.)
     * @throws InvalidMidiDataException If there's a problem appending to the
     *      MIDI file
     */
    private static void addLayers(List<ScopeNode> parentNodes, Node currentNode,
            Sequencer midi, int measureNumber) throws InvalidMidiDataException {
        int channel = 0;
        int numExistingTracks = midi.getSequence().getTracks().length;
        Sequence sequence = midi.getSequence();
        
        // Add layers for loop/if statement bodies
        for(ScopeNode parentNode : parentNodes) {
            Track bodyTrack = ScopeBodyMap.get(parentNode.getType());
        
            // We want to make sure we have enough tracks,
            // but only add tracks when we need more of them
            if(channel >= numExistingTracks) {
                sequence.createTrack();
                numExistingTracks = sequence.getTracks().length;
            }
            
            MidiUtilities.copyEventsToTrack(bodyTrack,
                    sequence.getTracks()[channel], channel, measureNumber,
                    ReferenceSequence);
            
            channel++;
        }
        
        // Note that because we've been incrementing channel, it should
        // now be at the right value for the next part of the algorithm
        
        if(currentNode instanceof DataStructureNode) {
            /*
             * Add layer for the data structure 
             */
            DataStructureNode currentDataStructureNode =
                    (DataStructureNode) currentNode;

            Track structureTrack =
                    DataStructureMap.get(currentDataStructureNode.getType());
            
            // We want to make sure we have enough tracks,
            // but only add tracks when we need more of them
            if(channel >= numExistingTracks) {
                sequence.createTrack();
                numExistingTracks = sequence.getTracks().length;
            }
            
            MidiUtilities.copyEventsToTrack(structureTrack,
                    sequence.getTracks()[channel], channel, measureNumber,
                    ReferenceSequence);
            
            /*
             * Add layer for the operation (but skip if operation is NONE)
             */
            if(currentDataStructureNode.getOperation() == Operation.NONE) {
                return;
            }
            
            channel++;
            Track operationTrack =
                    OperationMap.get(currentDataStructureNode.getOperation());
            
            // We want to make sure we have enough tracks,
            // but only add tracks when we need more of them
            if(channel >= numExistingTracks) {
                sequence.createTrack();
            }

            MidiUtilities.copyEventsToTrack(operationTrack,
                    sequence.getTracks()[channel], channel, measureNumber,
                    ReferenceSequence);
        } else {
            ScopeNode currentScopeNode = (ScopeNode) currentNode;

            Track conditionTrack =
                    ScopeConditionMap.get(currentScopeNode.getType());
            
            // We want to make sure we have enough tracks,
            // but only add tracks when we need more of them
            if(channel >= numExistingTracks) {
                sequence.createTrack();
            }
            
            MidiUtilities.copyEventsToTrack(conditionTrack,
                    sequence.getTracks()[channel], channel, measureNumber,
                    ReferenceSequence);
        }
    }
}