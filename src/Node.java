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

	public final int hashCode() {
		return this.toString().hashCode();
	}
}