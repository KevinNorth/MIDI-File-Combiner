package main;

import code.representation.ExecutionPath;
import music.generator.MusicGenerator;

public class Main {
    public static void main(String[] args) throws Exception {
        ExecutionPath rootNode = new RepGenerator().whileFloatSubtraction();
        
        // scheduleInterruptInFuture(9000L);
        
        MusicGenerator.startParseNodes(rootNode, "out.mid", true);
    }
    
    /**
     * Creates a new thread that will interrupt the current one some time in
     * the future.
     * 
     * This can be used to test the functionality in
     * <code>MusicGenerator.parseStartNodes()</code> where interrupting the
     * thread that calls it causes it to stop playing its generated MIDI file
     * prematurely.
     * 
     * Notice that this function calls <code>Thread.sleep()</code> internally,
     * so it's not guaranteed to interrupt after exactly the specified time. In
     * fact, it is likely to be a few milliseconds later.
     * 
     * @param millisToWait The amount of time, in milliseconds, to wait until
     *      interrupting the thread.
     */
    public static void scheduleInterruptInFuture(final long millisToWait) {
        final Thread thisThread = Thread.currentThread();
        
        Runnable otherThreadFunction = new Runnable() {
            @Override
            public void run() {
                try
                { Thread.sleep(millisToWait); } catch (InterruptedException ex) {}
                thisThread.interrupt();
            }
        };
        
        (new Thread(otherThreadFunction)).start();        
    }
}