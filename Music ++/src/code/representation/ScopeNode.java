package code.representation;

import java.util.LinkedList;
import java.util.List;

public class ScopeNode extends Node {
    private List<Node> children;
    private int numIterations;
    private ScopeNodeType type;
    
    public ScopeNode(ScopeNode parent, int numIterations) {
        super(parent);
        this.numIterations = numIterations;
        children = new LinkedList<Node>();
    }

    public int getNumIterations() {
        return numIterations;
    }

    public void setNumIterations(int numIterations) {
        this.numIterations = numIterations;
    }
    
    public void addChild(Node child) {
        children.add(child);
    }
    
    public List<Node> getChildren() {
        return children;
    }

    public ScopeNodeType getType() {
        return type;
    }

    public void setType(ScopeNodeType type) {
        this.type = type;
    }
    
    public boolean playComparisonAtEnd() {
        return type == ScopeNodeType.FOR
                || type == ScopeNodeType.WHILE
                || type == ScopeNodeType.DOWHILE;
    }
    
    public boolean playComparisonAtBeginning() {
        return type == ScopeNodeType.ELSEIF
                || type == ScopeNodeType.IF
                || type == ScopeNodeType.FOR
                || type == ScopeNodeType.WHILE;
    }
}