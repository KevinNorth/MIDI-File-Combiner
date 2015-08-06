import java.util.ArrayList;
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

	public List<Node> getNodes() {
		return nodes;
	}

	public List<Integer> getLineOrder() {
		List<Integer> place = new ArrayList<Integer>();
		for (Node node : nodes) {
			if (node.isScopeNode()) {
				ScopeNode sn = (ScopeNode) node;
				int numLoops = sn.getNumIterations();
				if (numLoops == 0) {
					place.add(sn.getLineNumber());
				}
				for (int i = 0; i < numLoops; i++) {
					place.add(sn.getLineNumber());
					for (Node child : sn.getChildren()) {
						DataStructureNode dsn = (DataStructureNode) child;
						place.add(dsn.getLineNumber());
					}
					if (numLoops - 1 == i) {
						place.add(sn.getLineNumber());
					}
				}

			} else {
				DataStructureNode dsn = (DataStructureNode) node;
				place.add(dsn.getLineNumber());
			}
		}
		return place;
	}

	public final boolean equals(Object o) {
		if (o instanceof ExecutionPath) {
			return ((ExecutionPath) o).nodes.equals(this.nodes);
		} else {
			return false;
		}
	}
}