public class DataStructureNode extends Node {
    private DataStructureNodeType type;
    private Operation operation;
    private int lineNumber;

    public DataStructureNode(ScopeNode parent, DataStructureNodeType type,
            Operation operation, int lineNumber) {
        super(parent);
        this.type = type;
        this.operation = operation;
        this.lineNumber = lineNumber;
    }

    public DataStructureNodeType getType() {
        return type;
    }
    
    public int getLineNumber(){
    	return lineNumber;
    }

    public void setType(DataStructureNodeType type) {
        this.type = type;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public final boolean equals(Object o) {
		if(o instanceof DataStructureNode){
			return o.hashCode() == this.hashCode();
		}else{
			return false;
		}
	}
    
	@Override
	public String toString() {
		return "DataStructureNode [type=" + type + ", operation=" + operation
				+ "]";
	}    
    
}