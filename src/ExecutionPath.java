import java.util.LinkedList;
import java.util.List;

public class ExecutionPath {
    private List<Node> nodes;
    
    public ExecutionPath() {
        nodes = new LinkedList<Node>();
    }
    
    public void addNode(Node node) {
        nodes.add(node);
    }
    
    public List<Node> getNodes()
    {
        return nodes;
    }
    
    public final boolean equals(Object o) {
		if(o instanceof ExecutionPath){
			return ((ExecutionPath) o).nodes.equals(this.nodes);
		}else{
			return false;
		}
	}
}