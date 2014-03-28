package main;

import code.representation.DataStructureNode;
import code.representation.DataStructureNodeType;
import code.representation.ExecutionPath;
import code.representation.Operation;
import code.representation.ScopeNode;
import code.representation.ScopeNodeType;

/**
 * RepGenerator
 * This class generates some examples of an abstract representation of code.
 * It can be used to make sample input to test with the music engine,
 * or to make samples against which the code analysis engine can compare output.
 *
 */
public class RepGenerator {

	public ExecutionPath whileIntAddition(){
		/*	int i = 0;
			int x = 1;
			while (i < 5) {
				x = x + i;
				i = i + 1;
			}
		*/
		
		ExecutionPath path = new ExecutionPath();
		
		DataStructureNode d1 = new DataStructureNode(null, DataStructureNodeType.INT, Operation.NONE);
		path.addNode(d1);
		
		DataStructureNode d2 = new DataStructureNode(null, DataStructureNodeType.INT, Operation.NONE);
		path.addNode(d2);
		
		ScopeNode s1 = new ScopeNode(null, 5);
		s1.setType(ScopeNodeType.WHILE);		
			DataStructureNode s1d1 = new DataStructureNode(s1, DataStructureNodeType.INT, Operation.ADD);
			DataStructureNode s1d2 = new DataStructureNode(s1, DataStructureNodeType.INT, Operation.ADD);	
		s1.addChild(s1d1);
		s1.addChild(s1d2);
		path.addNode(s1);	
		
		return path;
	}
	
	public ExecutionPath whileDoubleAddition(){
		/*	int i = 0;
			double x = 1;
			while (i < 5) {
				x = x + i;
				i = i + 1;
			}
		*/
		
		ExecutionPath path = new ExecutionPath();
		
		DataStructureNode d1 = new DataStructureNode(null, DataStructureNodeType.INT, Operation.NONE);
		path.addNode(d1);
		
		DataStructureNode d2 = new DataStructureNode(null, DataStructureNodeType.DOUBLE, Operation.NONE);
		path.addNode(d2);
		
		ScopeNode s1 = new ScopeNode(null, 5);
		s1.setType(ScopeNodeType.WHILE);		
			DataStructureNode s1d1 = new DataStructureNode(s1, DataStructureNodeType.DOUBLE, Operation.ADD);
			DataStructureNode s1d2 = new DataStructureNode(s1, DataStructureNodeType.INT, Operation.ADD);	
		s1.addChild(s1d1);
		s1.addChild(s1d2);
		path.addNode(s1);	
		
		return path;
	}
	
	public ExecutionPath whileFloatSubtraction(){
		/*	int i = 0;
			float x = 20;
			while (i < 5) {
				x = x - i;
				i = i + 1;
			}
		*/
		
		ExecutionPath path = new ExecutionPath();
		
		DataStructureNode d1 = new DataStructureNode(null, DataStructureNodeType.INT, Operation.NONE);
		path.addNode(d1);
		
		DataStructureNode d2 = new DataStructureNode(null, DataStructureNodeType.FLOAT, Operation.NONE);
		path.addNode(d2);
		
		ScopeNode s1 = new ScopeNode(null, 5);
		s1.setType(ScopeNodeType.WHILE);		
			DataStructureNode s1d1 = new DataStructureNode(s1, DataStructureNodeType.FLOAT, Operation.SUBTRACT);
			DataStructureNode s1d2 = new DataStructureNode(s1, DataStructureNodeType.INT, Operation.ADD);	
		s1.addChild(s1d1);
		s1.addChild(s1d2);
		path.addNode(s1);	
		
		return path;
	}
	
	public ExecutionPath ifStringConcatenation(){
		/*	String x = "abc";
		  	int y = 4;
		  	int z = 7;
		  	if (y+z < 9) {
		  		x = x + "def";
		  	}else {
		  		x = x + "ghi";
		  	}			
		*/
		
		ExecutionPath path = new ExecutionPath();
		
		DataStructureNode d1 = new DataStructureNode(null, DataStructureNodeType.STRING, Operation.NONE);
		path.addNode(d1);
		
		DataStructureNode d2 = new DataStructureNode(null, DataStructureNodeType.INT, Operation.NONE);
		path.addNode(d2);
		
		DataStructureNode d3 = new DataStructureNode(null, DataStructureNodeType.INT, Operation.NONE);
		path.addNode(d3);
		
		ScopeNode s1 = new ScopeNode(null, 1);
		s1.setType(ScopeNodeType.ELSE);		
			DataStructureNode s1d1 = new DataStructureNode(s1, DataStructureNodeType.STRING, Operation.ADD);
		s1.addChild(s1d1);
		path.addNode(s1);	
		
		return path;
	}
	
}
