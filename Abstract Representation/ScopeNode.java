package musicplusplusinterfaces.lists;


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
                || type == ScopeNodeType.WHILE;
    }
    
    public final boolean equals(Object o) {
		if(o instanceof ScopeNode){
			return ((ScopeNode) o).children.equals(this.children) &&
					((ScopeNode) o).type.equals(this.type) &&
					((ScopeNode) o).numIterations == this.numIterations;
		}else{
			return false;
		}
	}
    
    @Override
   	public String toString() {
   		return "ScopeNode [children=" + children + ", numIterations="
   				+ numIterations + ", type=" + type + "]";
   	}
    
    public boolean playComparisonAtBeginning() {
        return type == ScopeNodeType.ELSEIF
                || type == ScopeNodeType.IF
                || type == ScopeNodeType.FOR
                || type == ScopeNodeType.SWITCHBODY
                || type == ScopeNodeType.WHILE;
     }
    
}