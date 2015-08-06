/**
 * RepGenerator
 * This class generates some examples of an abstract representation of code.
 * It can be used to make sample input to test with the music engine,
 * or to make samples against which the code analysis engine can compare output.
 *
 */
public class RepGenerator {

//	public ExecutionPath ifCondition(){
//		/*	int x = 5;
//			if (x < 7) {
//				x = x + 4;
//			}else if(x > 7) {
//				x = x + 8;
//			}else {
//				x = x + 1;
//			}
//		*/
//		
//		ExecutionPath path = new ExecutionPath();
//		
//		DataStructureNode d1 = new DataStructureNode(null, DataStructureNodeType.INT, Operation.NONE);
//		path.addNode(d1);
//		
//		ScopeNode s1 = new ScopeNode(null, 1);
//		s1.setType(ScopeNodeType.IF);		
//			DataStructureNode s1d1 = new DataStructureNode(s1, DataStructureNodeType.INT, Operation.ADD);	
//		s1.addChild(s1d1);
//		path.addNode(s1);	
//		
//		return path;
//	}
//	
//	public ExecutionPath elseIfCondition(){
//		/*	int x = 10;
//			if (x < 7) {
//				x = x + 4;
//			}else if(x > 7) {
//				x = x + 8;
//			}else {
//				x = x + 1;
//			}
//		*/
//		
//		ExecutionPath path = new ExecutionPath();
//		
//		DataStructureNode d1 = new DataStructureNode(null, DataStructureNodeType.INT, Operation.NONE);
//		path.addNode(d1);
//		
//		ScopeNode s2 = new ScopeNode(null,1);
//		s2.setType(ScopeNodeType.IF);
//		path.addNode(s2);
//		
//		ScopeNode s1 = new ScopeNode(null, 1);
//		s1.setType(ScopeNodeType.ELSEIF);		
//			DataStructureNode s1d1 = new DataStructureNode(s1, DataStructureNodeType.INT, Operation.ADD);	
//		s1.addChild(s1d1);
//		path.addNode(s1);	
//		
//		return path;
//	}
//	
//	public ExecutionPath elseCondition(){
//		/*	int x = 7;
//			if (x < 7) {
//				x = x + 4;
//			}else if(x > 7) {
//				x = x + 8;
//			}else {
//				x = x + 1;
//			}		 
//		*/
//		
//		ExecutionPath path = new ExecutionPath();
//		
//		DataStructureNode d1 = new DataStructureNode(null, DataStructureNodeType.INT, Operation.NONE);
//		path.addNode(d1);
//		
//		ScopeNode s2 = new ScopeNode(null,1);
//		s2.setType(ScopeNodeType.IF);
//		path.addNode(s2);
//		
//		ScopeNode s3 = new ScopeNode(null,1);
//		s3.setType(ScopeNodeType.ELSEIF);
//		path.addNode(s3);
//		
//		ScopeNode s1 = new ScopeNode(null, 1);
//		s1.setType(ScopeNodeType.ELSE);		
//			DataStructureNode s1d1 = new DataStructureNode(s1, DataStructureNodeType.INT, Operation.ADD);	
//		s1.addChild(s1d1);
//		path.addNode(s1);	
//		
//		return path;
//	}
//	
//	public ExecutionPath noCondition(){
//		/*	int x = 7;
//			if (x < 7) {
//				x = x + 4;
//			}else if(x > 7) {
//				x = x + 8;
//			}
//		*/
//
//		ExecutionPath path = new ExecutionPath();
//		
//		DataStructureNode d1 = new DataStructureNode(null, DataStructureNodeType.INT, Operation.NONE);
//		path.addNode(d1);	
//		
//		ScopeNode s2 = new ScopeNode(null,1);
//		s2.setType(ScopeNodeType.IF);
//		path.addNode(s2);
//		
//		ScopeNode s3 = new ScopeNode(null,1);
//		s3.setType(ScopeNodeType.ELSEIF);
//		path.addNode(s3);
//		
//		return path;
//	}
//	
//	public ExecutionPath switchCascade(){
//		/*	int x = 4;
//			switch(x){
//		
//				case 2:
//					int d = 7 - 3;
//				case 4:
//					int a = 1 + 2;
//				case 1:
//					int b = 3 + x;
//					break;
//				case 23:
//					int c = 5;
//				default:
//					int e = 4;
//					int f = 6;
//				
//			}		
//		*/
//		
//		ExecutionPath path = new ExecutionPath();
//		
//		DataStructureNode d1 = new DataStructureNode(null, DataStructureNodeType.INT, Operation.NONE);
//		path.addNode(d1);
//		
//		ScopeNode s1 = new ScopeNode(null, 1);
//		s1.setType(ScopeNodeType.SWITCHBODY);		
//			DataStructureNode s1d1 = new DataStructureNode(s1, DataStructureNodeType.INT, Operation.ADD);	
//			DataStructureNode s1d2 = new DataStructureNode(s1, DataStructureNodeType.INT, Operation.ADD);	
//		s1.addChild(s1d1);
//		s1.addChild(s1d2);
//		path.addNode(s1);	
//		
//		return path;
//	}
//	
//	public ExecutionPath switchNoCascade(){
//		/*	int x = 4;
//			switch(x){
//	
//				case 2:
//					int d = 7 - 3;
//				case 4:
//					int a = 1 + 2;
//					break;
//				case 1:
//					int b = 3 + x;
//					break;
//				case 23:
//					int c = 5;
//					break;
//				default:
//					int e = 4;
//					int f = 6;
//			
//			}		
//		 */
//		
//		ExecutionPath path = new ExecutionPath();
//		
//		DataStructureNode d1 = new DataStructureNode(null, DataStructureNodeType.INT, Operation.NONE);
//		path.addNode(d1);
//		
//		ScopeNode s1 = new ScopeNode(null, 1);
//		s1.setType(ScopeNodeType.SWITCHBODY);		
//			DataStructureNode s1d1 = new DataStructureNode(s1, DataStructureNodeType.INT, Operation.ADD);	
//		s1.addChild(s1d1);
//		path.addNode(s1);	
//		
//		return path;
//	}
//	
//	public ExecutionPath switchNoCases(){
//		/*	int x = 5;
//			switch(x){
//		
//				case 2:
//					int d = 7 - 3;
//					break;
//				case 4:
//					int a = 1 + 2;
//					break;
//				case 1:
//					int b = 3 + x;
//					break;
//				case 23:
//					int c = 5;
//					break;
//			}
//		*/
//		
//		ExecutionPath path = new ExecutionPath();
//		
//		DataStructureNode d1 = new DataStructureNode(null, DataStructureNodeType.INT, Operation.NONE);
//		path.addNode(d1);
//		
//		ScopeNode s1 = new ScopeNode(null, 1);
//		s1.setType(ScopeNodeType.SWITCHBODY);
//		path.addNode(s1);	
//		
//		return path;
//	}
//	
//	public ExecutionPath whileAddition(){
//		/*	int i = 0;
//			int x = 1;
//			while (i < 5) {
//				x = x + i;
//				i = i + 1;
//			}
//		*/
//		
//		ExecutionPath path = new ExecutionPath();
//		
//		DataStructureNode d1 = new DataStructureNode(null, DataStructureNodeType.INT, Operation.NONE);
//		path.addNode(d1);
//		
//		DataStructureNode d2 = new DataStructureNode(null, DataStructureNodeType.INT, Operation.NONE);
//		path.addNode(d2);
//		
//		ScopeNode s1 = new ScopeNode(null, 5);
//		s1.setType(ScopeNodeType.WHILE);		
//			DataStructureNode s1d1 = new DataStructureNode(s1, DataStructureNodeType.INT, Operation.ADD);
//			DataStructureNode s1d2 = new DataStructureNode(s1, DataStructureNodeType.INT, Operation.ADD);	
//		s1.addChild(s1d1);
//		s1.addChild(s1d2);
//		path.addNode(s1);	
//		
//		return path;
//	}
//	
//	public ExecutionPath whileSubtraction(){
//		/*	int i = 0;
//			double x = 20;
//			while (i < 5) {
//				x = x - i;
//				i = i + 1;
//			}
//		*/
//		
//		ExecutionPath path = new ExecutionPath();
//		
//		DataStructureNode d1 = new DataStructureNode(null, DataStructureNodeType.INT, Operation.NONE);
//		path.addNode(d1);
//		
//		DataStructureNode d2 = new DataStructureNode(null, DataStructureNodeType.DOUBLE, Operation.NONE);
//		path.addNode(d2);
//		
//		ScopeNode s1 = new ScopeNode(null, 5);
//		s1.setType(ScopeNodeType.WHILE);		
//			DataStructureNode s1d1 = new DataStructureNode(s1, DataStructureNodeType.DOUBLE, Operation.SUBTRACT);
//			DataStructureNode s1d2 = new DataStructureNode(s1, DataStructureNodeType.INT, Operation.ADD);	
//		s1.addChild(s1d1);
//		s1.addChild(s1d2);
//		path.addNode(s1);	
//		
//		return path;
//	}
//
//	public ExecutionPath stringTest(){
//		/*	int i = 0;
//			String s = "a";
//			while (i < 5) {
//				s = s + "a";
//				i = i + 1;
//			}
//		*/
//		
//		ExecutionPath path = new ExecutionPath();
//		
//		DataStructureNode d1 = new DataStructureNode(null, DataStructureNodeType.INT, Operation.NONE);
//		path.addNode(d1);
//		
//		DataStructureNode d2 = new DataStructureNode(null, DataStructureNodeType.STRING, Operation.NONE);
//		path.addNode(d2);
//		
//		ScopeNode s1 = new ScopeNode(null, 5);
//		s1.setType(ScopeNodeType.WHILE);		
//			DataStructureNode s1d1 = new DataStructureNode(s1, DataStructureNodeType.STRING, Operation.ADD);
//			DataStructureNode s1d2 = new DataStructureNode(s1, DataStructureNodeType.INT, Operation.ADD);	
//		s1.addChild(s1d1);
//		s1.addChild(s1d2);
//		path.addNode(s1);	
//		
//		return path;
//	}
//		
//	public ExecutionPath noWhile(){
//		/*	int i = 0;
//			int x = 20;
//			while (i > 5) {
//				x = x - i;
//				i = i + 1;
//			}
//		*/
//		
//		ExecutionPath path = new ExecutionPath();
//		
//		DataStructureNode d1 = new DataStructureNode(null, DataStructureNodeType.INT, Operation.NONE);
//		path.addNode(d1);
//		
//		DataStructureNode d2 = new DataStructureNode(null, DataStructureNodeType.INT, Operation.NONE);
//		path.addNode(d2);
//		
//		ScopeNode s1 = new ScopeNode(null, 0);
//		s1.setType(ScopeNodeType.WHILE);		
//		
//		path.addNode(s1);	
//		
//		return path;
//	}
//		
//	public ExecutionPath doWhileDivision(){
//		/*	int i = 0;
//			float x = 32;
//			do {
//				x = x / 2;
//				i = i + 1;
//			} while (i < 5);
//		*/
//		
//		ExecutionPath path = new ExecutionPath();
//		
//		DataStructureNode d1 = new DataStructureNode(null, DataStructureNodeType.INT, Operation.NONE);
//		path.addNode(d1);
//		DataStructureNode d2 = new DataStructureNode(null, DataStructureNodeType.FLOAT, Operation.NONE);
//		path.addNode(d2);
//		
//		ScopeNode s1 = new ScopeNode(null, 5);
//		s1.setType(ScopeNodeType.DOWHILE);	
//			DataStructureNode s1d1 = new DataStructureNode(s1, DataStructureNodeType.FLOAT, Operation.DIVIDE);
//			DataStructureNode s1d2 = new DataStructureNode(s1, DataStructureNodeType.INT, Operation.ADD);	
//		s1.addChild(s1d1);
//		s1.addChild(s1d2);
//		path.addNode(s1);	
//		
//		return path;
//	}
//		
//	public ExecutionPath doWhileMultiplication(){
//		/*	int i = 0;
//			byte x = 1;
//			do {
//				x = (byte) (2 * x);
//				i = i + 1;
//			} while (i < 5);
//		*/
//		
//		ExecutionPath path = new ExecutionPath();
//		
//		DataStructureNode d1 = new DataStructureNode(null, DataStructureNodeType.INT, Operation.NONE);
//		path.addNode(d1);
//		DataStructureNode d2 = new DataStructureNode(null, DataStructureNodeType.BYTE, Operation.NONE);
//		path.addNode(d2);
//		
//		ScopeNode s1 = new ScopeNode(null, 5);
//		s1.setType(ScopeNodeType.DOWHILE);	
//			DataStructureNode s1d1 = new DataStructureNode(s1, DataStructureNodeType.BYTE, Operation.MULTIPLY);
//			DataStructureNode s1d2 = new DataStructureNode(s1, DataStructureNodeType.INT, Operation.ADD);	
//		s1.addChild(s1d1);
//		s1.addChild(s1d2);
//		path.addNode(s1);	
//		
//		return path;
//	}
//	
//	public ExecutionPath stringTestDo(){
//		/*	int i = 0;
//			String s = "a";
//			do {
//				s = s + "a";
//				i = i + 1;
//			} while (i < 5);
//		*/
//		
//		ExecutionPath path = new ExecutionPath();
//		
//		DataStructureNode d1 = new DataStructureNode(null, DataStructureNodeType.INT, Operation.NONE);
//		path.addNode(d1);
//		DataStructureNode d2 = new DataStructureNode(null, DataStructureNodeType.STRING, Operation.NONE);
//		path.addNode(d2);
//		
//		ScopeNode s1 = new ScopeNode(null, 5);
//		s1.setType(ScopeNodeType.DOWHILE);	
//			DataStructureNode s1d1 = new DataStructureNode(s1, DataStructureNodeType.STRING, Operation.ADD);
//			DataStructureNode s1d2 = new DataStructureNode(s1, DataStructureNodeType.INT, Operation.ADD);	
//		s1.addChild(s1d1);
//		s1.addChild(s1d2);
//		path.addNode(s1);	
//		
//		return path;
//	}
//	
//	public ExecutionPath noDoWhile(){
//		/*	int i = 0;
//			int x = 20;
//			do {
//				x = x - i;
//				i = i + 1;
//			} while (i > 5);
//		*/
//		
//		ExecutionPath path = new ExecutionPath();
//		
//		DataStructureNode d1 = new DataStructureNode(null, DataStructureNodeType.INT, Operation.NONE);
//		path.addNode(d1);
//		DataStructureNode d2 = new DataStructureNode(null, DataStructureNodeType.INT, Operation.NONE);
//		path.addNode(d2);
//		
//		ScopeNode s1 = new ScopeNode(null, 1);
//		s1.setType(ScopeNodeType.DOWHILE);	
//			DataStructureNode s1d1 = new DataStructureNode(s1, DataStructureNodeType.INT, Operation.SUBTRACT);
//			DataStructureNode s1d2 = new DataStructureNode(s1, DataStructureNodeType.INT, Operation.ADD);	
//		s1.addChild(s1d1);
//		s1.addChild(s1d2);
//		path.addNode(s1);	
//		
//		return path;
//	}
//	
//	public ExecutionPath forAddition(){
//		/*	int x = 1;
//			for (int i = 0; i < 5; i++) {
//				x = x + i;
//			}
//		*/
//		
//		ExecutionPath path = new ExecutionPath();
//		
//		DataStructureNode d1 = new DataStructureNode(null, DataStructureNodeType.INT, Operation.NONE);
//		path.addNode(d1);
//		
//		ScopeNode s1 = new ScopeNode(null, 5);
//		s1.setType(ScopeNodeType.FOR);		
//			DataStructureNode s1d1 = new DataStructureNode(s1, DataStructureNodeType.INT, Operation.ADD);	
//		s1.addChild(s1d1);
//		path.addNode(s1);	
//		
//		return path;
//	}
//	
//	public ExecutionPath forMultiplication(){
//		/*	byte x = 1;
//			for(int i = 0; i < 5; i++) {
//				x = (byte ) (x * 2);
//			}
//		*/
//		
//		ExecutionPath path = new ExecutionPath();
//		
//		DataStructureNode d1 = new DataStructureNode(null, DataStructureNodeType.BYTE, Operation.NONE);
//		path.addNode(d1);
//		
//		ScopeNode s1 = new ScopeNode(null, 5);
//		s1.setType(ScopeNodeType.FOR);		
//			DataStructureNode s1d1 = new DataStructureNode(s1, DataStructureNodeType.BYTE, Operation.MULTIPLY);	
//		s1.addChild(s1d1);
//		path.addNode(s1);	
//		
//		return path;
//	}
//	
//	public ExecutionPath forSubtraction(){
//		/*	double x = 20;
//			for (int i = 0; i < 5; i++) {
//				x = x - i;
//			}
//		*/
//
//		ExecutionPath path = new ExecutionPath();
//		
//		DataStructureNode d1 = new DataStructureNode(null, DataStructureNodeType.DOUBLE, Operation.NONE);
//		path.addNode(d1);
//		
//		ScopeNode s1 = new ScopeNode(null, 5);
//		s1.setType(ScopeNodeType.FOR);		
//			DataStructureNode s1d1 = new DataStructureNode(s1, DataStructureNodeType.DOUBLE, Operation.SUBTRACT);	
//		s1.addChild(s1d1);
//		path.addNode(s1);	
//		
//		return path;
//	}
//	
//	public ExecutionPath noFor(){
//		/*	int x = 20;
//			for (int i = 0; i > 5; i++) {
//				x = x - i;
//			}
//		*/
//		
//		ExecutionPath path = new ExecutionPath();
//		
//		DataStructureNode d1 = new DataStructureNode(null, DataStructureNodeType.INT, Operation.NONE);
//		path.addNode(d1);
//		
//		ScopeNode s1 = new ScopeNode(null, 0);
//		s1.setType(ScopeNodeType.FOR);		
//		path.addNode(s1);	
//		
//		return path;
//	}
//	
//	public ExecutionPath noStructure(){
//		/*	int x = 1;
//			x = x + 0;
//			x = x + 1;
//			x = x + 2;
//			x = x + 3;
//			x = x + 4;
//		*/
//		
//		ExecutionPath path = new ExecutionPath();
//		
//		DataStructureNode d1 = new DataStructureNode(null, DataStructureNodeType.INT, Operation.NONE);
//		path.addNode(d1);
//		DataStructureNode d2 = new DataStructureNode(null, DataStructureNodeType.INT, Operation.ADD);
//		path.addNode(d2);
//		DataStructureNode d3 = new DataStructureNode(null, DataStructureNodeType.INT, Operation.ADD);
//		path.addNode(d3);
//		DataStructureNode d4 = new DataStructureNode(null, DataStructureNodeType.INT, Operation.ADD);
//		path.addNode(d4);
//		DataStructureNode d5 = new DataStructureNode(null, DataStructureNodeType.INT, Operation.ADD);
//		path.addNode(d5);
//		DataStructureNode d6 = new DataStructureNode(null, DataStructureNodeType.INT, Operation.ADD);
//		path.addNode(d6);
//		
//		return path;
//	}
	
}
