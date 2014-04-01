package main;

import code.representation.ExecutionPath;
import music.generator.MusicGenerator;

public class Main {
    public static void main(String[] args) throws Exception {
        ExecutionPath rootNode = new RepGenerator().whileFloatSubtraction();
        
        MusicGenerator.startParseNodes(rootNode, true);
    }
}