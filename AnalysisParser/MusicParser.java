import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


/**
 * @author natasha-jahnke
 *
 * This class creates an abstract representation/execution path of a given source file.
 * It uses the xml code coverage output of cobetura to determine if a line was executed
 * and the number of times the line was executed.
 */
public class MusicParser {

	public ExecutionPath path;
	
	/**
	 * This method is used to parse the source file and using the coverage report build the
	 * execution path representation of the source file.
	 * @param xmlOutput
	 * @param sourceFile
	 * @return path - execution path
	 */
	public ExecutionPath parseFile(File xmlOutput, File sourceFile){
		
		path = new ExecutionPath();
		
		try (BufferedReader br = new BufferedReader(new FileReader(sourceFile)))
		{
			File coverage = new File("coverage.xml");
 
			String sCurrentLine;
			int lineNumber = 0;
			boolean dsFlag = false;
 
			while ((sCurrentLine = br.readLine()) != null) {
				lineNumber++;
				
				String[] tokens = new String[15];
				
				tokens = sCurrentLine.split(" ");
				
				String value = tokens[0];
				DataStructureNode node;
				ScopeNode scopeNode;
				int numIterations = 1;
				int nextLineIterations = 0;
				
				if(value.contains("for"))
					value = "for";
				else if(value.contains("while"))
					value = "while";
				else if(value.contains("if"))
					value = "if";
				else if(value.contains("else if"))
					value = "else if";
				else if(value.contains("else"))
					value = "else";
				else if(value.contains("do"))
					value = "do";
				else if(value.contains("switch"))
					value = "switch";
				
				//System.out.println(value);
				
				switch(value){				
// DATASTRUCTURES
					case "int":
						node = new DataStructureNode(null, DataStructureNodeType.INT, checkOperation(sCurrentLine));
						path.addNode(node);
					break;
					
					case "double":
						node = new DataStructureNode(null, DataStructureNodeType.DOUBLE, checkOperation(sCurrentLine));
						path.addNode(node);
					break;
					
					case "float":
						node = new DataStructureNode(null, DataStructureNodeType.FLOAT, checkOperation(sCurrentLine));
						path.addNode(node);
					break;
					
					case "String":
						node = new DataStructureNode(null, DataStructureNodeType.STRING, checkOperation(sCurrentLine));
						path.addNode(node);
					break;
					
					case "char":
						node = new DataStructureNode(null, DataStructureNodeType.CHAR, checkOperation(sCurrentLine));
						path.addNode(node);
					break;
					
					case "byte":
						node = new DataStructureNode(null, DataStructureNodeType.BYTE, checkOperation(sCurrentLine));
						path.addNode(node);
					break;
					
					case "boolean":
						node = new DataStructureNode(null, DataStructureNodeType.BOOLEAN, checkOperation(sCurrentLine));
						path.addNode(node);
					break;
					
// LOGIC CONSTRUCTORS
					
/*WHILE*/			case "while":
						numIterations = getLineNumberFromXML(lineNumber, coverage);
						scopeNode = new ScopeNode(null, numIterations);
						scopeNode.setType(ScopeNodeType.WHILE);
						
						if(checkBranchExecutions(lineNumber, coverage)){					
							dsFlag = true;
							
							while(dsFlag)
							{
								sCurrentLine = br.readLine();
								lineNumber++;
								
								if(sCurrentLine.contains("}"))
								{
									dsFlag = false;
								}
								else{
									tokens = sCurrentLine.split(" ");
									switchDataStructures(tokens, sCurrentLine, scopeNode);
								}
							}
						}
						path.addNode(scopeNode);
					break;
					
/*Do-While*/		case "do":
						numIterations = getLineNumberFromXML(lineNumber, coverage);
						scopeNode = new ScopeNode(null, numIterations);
						scopeNode.setType(ScopeNodeType.DOWHILE);
						
						dsFlag = true;
						
						while(dsFlag)
						{
							sCurrentLine = br.readLine();
							lineNumber++;
							
							if(sCurrentLine.contains("}"))
							{
								dsFlag = false;
								
							}
							else{
								tokens = sCurrentLine.split(" ");
								switchDataStructures(tokens, sCurrentLine, scopeNode);
							}
						}
																		
						path.addNode(scopeNode);		
					break;
					
/*Switch*/			case "switch":
						scopeNode = new ScopeNode(null, numIterations);
						scopeNode.setType(ScopeNodeType.SWITCHBODY);
						
						dsFlag = true;
						
						while(dsFlag){
							sCurrentLine = br.readLine();
							lineNumber++;
							
							if(sCurrentLine.contains("}"))
								dsFlag = false;
							else if(getLineNumberFromXML(lineNumber, coverage) == 1){
								tokens = sCurrentLine.split(" ");
								switchDataStructures(tokens, sCurrentLine, scopeNode);
							}
						}
						
						path.addNode(scopeNode);
						
					break;
					
/*For*/				case "for":
						numIterations = getLineNumberFromXML(lineNumber, coverage);
						scopeNode = new ScopeNode(null, numIterations);
						scopeNode.setType(ScopeNodeType.FOR);
												
						if(checkBranchExecutions(lineNumber, coverage)){
							dsFlag = true;
							
							while(dsFlag)
							{
								sCurrentLine = br.readLine();
								lineNumber++;
								
								if(sCurrentLine.contains("}"))
								{
									dsFlag = false;
								}
								else{
									tokens = sCurrentLine.split(" ");
									switchDataStructures(tokens, sCurrentLine, scopeNode);
								}
							}
						}
						
						path.addNode(scopeNode);
						
					break;
					
/*If Statement*/	case "if":
						numIterations = getLineNumberFromXML(lineNumber, coverage);
						scopeNode = new ScopeNode(null, numIterations);
						scopeNode.setType(ScopeNodeType.IF);
												
						if(checkBranchExecutions(lineNumber, coverage)){
							dsFlag = true;
							
							while(dsFlag)
							{
								sCurrentLine = br.readLine();
								lineNumber++;
								
								if(sCurrentLine.contains("}"))
								{
									dsFlag = false;
								}
								else{
									tokens = sCurrentLine.split(" ");
									switchDataStructures(tokens, sCurrentLine, scopeNode);
								}
							}
						}
						
						path.addNode(scopeNode);
						
					break;
					
					case "else if":
						numIterations = getLineNumberFromXML(lineNumber, coverage);
						scopeNode = new ScopeNode(null, numIterations);
						scopeNode.setType(ScopeNodeType.ELSEIF);
												
						if(checkBranchExecutions(lineNumber, coverage)){
							dsFlag = true;
							
							while(dsFlag)
							{
								sCurrentLine = br.readLine();
								lineNumber++;
								
								if(sCurrentLine.contains("}"))
								{
									dsFlag = false;
								}
								else{
									tokens = sCurrentLine.split(" ");
									switchDataStructures(tokens, sCurrentLine, scopeNode);
								}
							}
						}
						
						path.addNode(scopeNode);
					break;
					
					case "else":
						numIterations = getLineNumberFromXML(lineNumber, coverage);
						scopeNode = new ScopeNode(null, numIterations);
						scopeNode.setType(ScopeNodeType.ELSE);
												
						if(checkBranchExecutions(lineNumber, coverage)){
							dsFlag = true;
							
							while(dsFlag)
							{
								sCurrentLine = br.readLine();
								lineNumber++;
								
								if(sCurrentLine.contains("}"))
								{
									dsFlag = false;
								}
								else{
									tokens = sCurrentLine.split(" ");
									switchDataStructures(tokens, sCurrentLine, scopeNode);
								}
							}
						}
						
						path.addNode(scopeNode);
					break;

				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return path;
	}

	/**
	 * This method is used when a scope node is executed and determines and builds the data structure
	 * nodes contained within the scope node.
	 * @param tokens
	 * @param sCurrentLine
	 * @param parent
	 * @return dataStructureNode
	 */
	public DataStructureNode switchDataStructures(String[] tokens, String sCurrentLine, ScopeNode parent){
		
		String value = tokens[0];
		DataStructureNode node = null;		
		
		switch(value){
			case "int":
				node = new DataStructureNode(parent, DataStructureNodeType.INT, checkOperation(sCurrentLine));
				parent.addChild(node);
			break;
			
			case "double":
				node = new DataStructureNode(parent, DataStructureNodeType.DOUBLE, checkOperation(sCurrentLine));
				parent.addChild(node);
			break;
			
			case "float":
				node = new DataStructureNode(parent, DataStructureNodeType.FLOAT, checkOperation(sCurrentLine));
				parent.addChild(node);
			break;
			
			case "String":
				node = new DataStructureNode(parent, DataStructureNodeType.STRING, checkOperation(sCurrentLine));
				parent.addChild(node);
			break;
			
			case "char":
				node = new DataStructureNode(parent, DataStructureNodeType.CHAR, checkOperation(sCurrentLine));
				parent.addChild(node);
			break;
			
			case "byte":
				node = new DataStructureNode(parent, DataStructureNodeType.BYTE, checkOperation(sCurrentLine));
				parent.addChild(node);
			break;
			
			case "boolean":
				node = new DataStructureNode(parent, DataStructureNodeType.BOOLEAN, checkOperation(sCurrentLine));
				parent.addChild(node);
			break;
		}
		
		return node;

	}
	
	/**
	 * This method determines the operation completed on the line.  This method is only executed when 
	 * a data structure node is executed. 
	 * @param line
	 * @return operation
	 */
	public Operation checkOperation(String line){
		
		if (line.contains("+"))
			return Operation.ADD;
		else if (line.contains("-"))
			return Operation.SUBTRACT;
		else if (line.contains("*"))
			return Operation.MULTIPLY;
		else if (line.contains("/"))
			return Operation.DIVIDE;
		else
			return Operation.NONE;
					
	}
	
	/**
	 * This method used the coverage report to find the number of times a line was executed.
	 * @param lineNumber
	 * @param coverage
	 * @return numberOfTimes "lineNumber" was executed
	 */
	public static int getLineNumberFromXML(int lineNumber, File coverage){
		int hitsNumber = -1;
		try {
			//Pattern lineHits = Pattern.compile("<line number=\"" + lineNumber + "\"");
			BufferedReader xmlReader = new BufferedReader(new FileReader(coverage));
			String xmlLine = xmlReader.readLine();
			
			String pattern = "<line number=\"" + lineNumber +"\"";
			
			while(!xmlLine.contains(pattern)){
				xmlLine = xmlReader.readLine();
			}
			//Now xmlLine should have the correct line entry that we need to process
			
			String[] entries = xmlLine.split(" ");
			//Puts together an array with {"<line", "number="lineNumber"", "hits="hitsNumber"", etc(after this we don't care)
			
			entries[2] = entries[2].substring(6, entries[2].length() - 1);
			//This should get the substring between the "" in the hits entry
			
			hitsNumber = Integer.parseInt(entries[2]);
			
			xmlReader.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return hitsNumber;
	}
	
	/**
	 * This method uses the coverage report to check if a scope node's branch evaluates to true, if so the
	 * the statements contained within may be executed.
	 * @param lineNumber
	 * @param coverage
	 * @return boolean(true if branch was succesful, false otherwise)
	 */
	public static boolean checkBranchExecutions(int lineNumber, File coverage){
		boolean branchExecution = false;
		
		try {
			//Pattern lineHits = Pattern.compile("<line number=\"" + lineNumber + "\"");
			BufferedReader xmlReader = new BufferedReader(new FileReader(coverage));
			String xmlLine = xmlReader.readLine();
			
			String pattern = "<line number=\"" + lineNumber +"\"";
			
			while(!xmlLine.contains(pattern)){
				xmlLine = xmlReader.readLine();
			}
			//Now xmlLine should have the correct line entry that we need to process
			
			if(xmlLine.contains("true"))
				branchExecution = true;
			
			//System.out.println("lineNumber: " + lineNumber + "---branchExecution = " + branchExecution);
			
			xmlReader.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		//System.out.println("lineNumber: " + lineNumber + "---branchExecution = " + branchExecution);
		
		return branchExecution;
	}
	
}
