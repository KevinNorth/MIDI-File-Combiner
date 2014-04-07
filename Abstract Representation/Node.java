package musicplusplusinterfaces.lists;


public abstract class Node {
    private ScopeNode parent;
    
    public Node(ScopeNode parent) {
        this.parent = parent;
    }

    public ScopeNode getParent() {
        return parent;
    }    
    
    public boolean isScopeNode() {
        return this instanceof ScopeNode;
    }
    
    public final boolean equals(Object o) {
		if(o instanceof Node){
			return o.hashCode() == this.hashCode();
		}else{
			return false;
		}
	}

	public final int hashCode() {
		return this.toString().hashCode();
	}
}