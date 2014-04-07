package musicplusplusinterfaces.lists;

public class DataStructureNode extends Node {
    private DataStructureNodeType type;
    private Operation operation;

    public DataStructureNode(ScopeNode parent, DataStructureNodeType type,
            Operation operation) {
        super(parent);
        this.type = type;
        this.operation = operation;
    }

    public DataStructureNodeType getType() {
        return type;
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

	@Override
	public String toString() {
		return "DataStructureNode [type=" + type + ", operation=" + operation
				+ "]";
	}    
    
}