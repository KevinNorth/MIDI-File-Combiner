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
            
            Sequence stringSequence = MidiUtilities.openMidiFile("midi/string.mid");
            
        }

        if(ScopeBodyMap == null) {
            //TODO
        }

        if(ScopeConditionMap == null) {
            //TODO
        }

        if(OperationMap == null) {
            //TODO
        }
        
        if(ReferenceSequence == null) {
            ReferenceSequence = MidiUtilities.openMidiFile("midi/string.mid");
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
     */
    private static int parseNodes(List<ScopeNode> parentNodes, Node currentNode,
        Sequencer midi, int measureNumber) {
            
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
     */
    private static void addLayers(List<ScopeNode> parentNodes, Node currentNode,
            Sequencer midi, int measureNumber) {
        System.out.println("---------");
        System.out.println("Measure " + measureNumber + ":");
        
        for(ScopeNode parentNode : parentNodes) {
            switch(parentNode.getType()) {
                case FOR:
                    System.out.println("For body");
                    break;
                case WHILE:
                    System.out.println("While body");
                    break;
                case DOWHILE:
                    System.out.println("Do/While body");
                    break;
                case IF:
                    System.out.println("If body");
                    break;
                case ELSEIF:
                    System.out.println("Else if body");
                    break;
                case ELSE:
                    System.out.println("Else body");
                    break;
                case SWITCHBODY:
                    System.out.println("Switch body");
                    break;
                default:
                    throw new RuntimeException("The switch statement for scope "
                            + "bodies in MusicGenerator.addLayers() fell through");
            }
        }
            
        if(currentNode instanceof DataStructureNode) {
            DataStructureNode currentDataStructureNode =
                    (DataStructureNode) currentNode;
            switch(currentDataStructureNode.getType()) {
                case BOOLEAN:
                    System.out.println("Boolean");
                    break;
                case BYTE:
                    System.err.println("Byte");
                    break;
                case CHAR:
                    System.err.println("Char");
                    break;
                case DOUBLE:
                    System.out.println("Double");
                    break;
                case FLOAT:
                    System.out.println("Float");
                    break;
                case INT:
                    System.out.println("Integer");
                    break;
                case STRING:
                    System.out.println("String");
                    break;
                default:
                    throw new RuntimeException("The switch statement for data "
                        + "structure types in MusicGenerator.addLayers() "
                        + "fell through");
            }

            switch(currentDataStructureNode.getOperation()) {
                case ADD:
                    System.out.println("Addition");
                    break;
                case COMPARE:
                    System.out.println("Comparison");
                    break;
                case DIVIDE:
                    System.out.println("Divide");
                    break;
                case MULTIPLY:
                    System.out.println("Multiply");
                    break;
                case SUBTRACT:
                    System.out.println("Subtraction");
                    break;
                case NONE: break;
                default:
                    throw new RuntimeException("The switch statement for data "
                        + "structure operations in MusicGenerator.addLayers() "
                        + "fell through");
            }
        } else {
            ScopeNode currentScopeNode = (ScopeNode) currentNode;

            switch(currentScopeNode.getType()) {
                case FOR:
                    System.out.println("For condition");
                    break;
                case WHILE:
                    System.out.println("While condition");
                    break;
                case DOWHILE:
                    System.out.println("Do/While condition");
                    break;
                case IF:
                    System.out.println("If condition");
                    break;
                case ELSEIF:
                    System.out.println("Else if condition");
                    break;
                case ELSE:
                    System.out.println("Else condition");
                    break;
                case SWITCHBODY:
                    System.out.println("Switch condition");
                    break;
                default:
                    throw new RuntimeException("The switch statement for scope "
                        + "conditions in MusicGenerator.addLayers() fell through");
            }
        }
    }
}