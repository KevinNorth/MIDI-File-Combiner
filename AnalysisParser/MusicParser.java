import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author natasha-jahnke
 *
 * This class creates an abstract representation/execution path of a given source file.
 * It uses the xml code coverage output of cobetura to determine if a line was executed
 * and the number of times the line was executed.
 */
public class MusicParser {

	public ExecutionPath path;
	public Map<String, String> variables;
	
	/**
	 * This method is used to parse the source file and using the coverage report build the
	 * execution path representation of the source file.
	 * @param xmlOutput
	 * @param sourceFile
	 * @return path - execution path
	 */
	public ExecutionPath parseFile(File xmlOutput, File sourceFile){
		try (BufferedReader br = new BufferedReader(new FileReader(sourceFile)))
		{
			path = new ExecutionPath();
			variables = new HashMap<>();
			
			File coverage = xmlOutput;
 
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
								
				if(value.contains("for"))
					value = "for";
				else if(value.contains("while"))
					value = "while";
				else if(sCurrentLine.contains("else if"))
					value = "else if";
				else if(value.contains("else"))
					value = "else";
				else if(value.contains("if"))
					value = "if";
				else if(value.contains("double"))
				{
					value = "double";
					variables.put(tokens[1], "double");
				}
				else if(value.contains("do"))
					value = "do";
				else if(value.contains("switch"))
					value = "switch";
				else if(sCurrentLine.contains("int"))
				{
					value = "int";
					variables.put(tokens[1], "int");
				}
				else if(value.contains("String"))
				{
					value = "String";
					variables.put(tokens[1], "String");
				}
				else if(value.contains("char"))
				{
					value = "char";
					variables.put(tokens[1], "char");
				}
				else if(value.contains("byte"))
				{
					value = "byte";
					variables.put(tokens[1], "byte");
				}
				else if(value.contains("boolean"))
				{
					value = "boolean";
					variables.put(tokens[1], "boolean");
				}
				else if(value.contains("float"))
				{
					value = "float";
					variables.put(tokens[1], value);
				}
				else
				{					
					if(value.contains("=") && !value.isEmpty())
					{
						value = variables.get(tokens[0].trim());
					}
				}
								
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
						numIterations = getNumberOfIterations(lineNumber, coverage) - 1;
						scopeNode = new ScopeNode(null, numIterations);
						scopeNode.setType(ScopeNodeType.WHILE);
						
						if(getNumberOfIterations(lineNumber + 1, coverage) > 0){					
							dsFlag = true;
							
							while(dsFlag)
							{
								sCurrentLine = br.readLine();
								lineNumber++;
								
								if(sCurrentLine.contains("}"))
									dsFlag = false;
								
								DataStructureNode childNode = switchDataStructures(sCurrentLine, scopeNode);
								if(childNode != null)
									scopeNode.addChild(childNode);
							}
						}
						path.addNode(scopeNode);
					break;
					
/*Do-While*/		case "do":
						numIterations = getNumberOfIterations(lineNumber + 1, coverage);
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
								
								DataStructureNode childNode = switchDataStructures(sCurrentLine, scopeNode);
								if(childNode != null)
									scopeNode.addChild(childNode);
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
							else if(getNumberOfIterations(lineNumber, coverage) == 1){
								
								DataStructureNode childNode = switchDataStructures(sCurrentLine, scopeNode);
								if(childNode != null)
									scopeNode.addChild(childNode);
							}
						}
						
						path.addNode(scopeNode);
						
					break;
					
/*For*/				case "for":
						numIterations = getNumberOfIterations(lineNumber, coverage) - 1;
						scopeNode = new ScopeNode(null, numIterations);
						scopeNode.setType(ScopeNodeType.FOR);
												
						if(getNumberOfIterations(lineNumber + 1, coverage) > 0){
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
									
									DataStructureNode childNode = switchDataStructures(sCurrentLine, scopeNode);
									if(childNode != null)
										scopeNode.addChild(childNode);
								}
							}
						}
						
						path.addNode(scopeNode);
						
					break;
					
/*If Statement*/	case "if":
						if(getNumberOfIterations(lineNumber, coverage) > 0){
							numIterations = getNumberOfIterations(lineNumber, coverage);
							scopeNode = new ScopeNode(null, numIterations);
							scopeNode.setType(ScopeNodeType.IF);
						
							dsFlag = true;
							
							if(getNumberOfIterations(lineNumber + 1, coverage) > 0)
							{
								while(dsFlag)
								{
									sCurrentLine = br.readLine();
									lineNumber++;
																		
									if(sCurrentLine.contains("}"))
									{
										dsFlag = false;
									}
									
									if(getNumberOfIterations(lineNumber, coverage) > 0){
										DataStructureNode childNode = switchDataStructures(sCurrentLine, scopeNode);
										if(childNode != null)
											scopeNode.addChild(childNode);
									}
								}
							}
							path.addNode(scopeNode);
						}
						
					break;
					
					case "else if":
						if(getNumberOfIterations(lineNumber, coverage) > 0){
							numIterations = getNumberOfIterations(lineNumber, coverage);
							scopeNode = new ScopeNode(null, numIterations);
							scopeNode.setType(ScopeNodeType.ELSEIF);
												
							dsFlag = true;
							
							if(getNumberOfIterations(lineNumber + 1, coverage) > 0){
								while(dsFlag)
								{
									sCurrentLine = br.readLine();
									lineNumber++;
									
									if(sCurrentLine.contains("}"))
									{
										dsFlag = false;
									}
									
									if(getNumberOfIterations(lineNumber, coverage) > 0){
										DataStructureNode childNode = switchDataStructures(sCurrentLine, scopeNode);
										if(childNode != null)
											scopeNode.addChild(childNode);
									}
								}
							}
						
							path.addNode(scopeNode);
						}
						
					break;
					
					case "else":
						if(getNumberOfIterations(lineNumber+1, coverage) > 0){
							numIterations = getNumberOfIterations(lineNumber+1, coverage);
							scopeNode = new ScopeNode(null, numIterations);
							scopeNode.setType(ScopeNodeType.ELSE);
														
							dsFlag = true;
							if(getNumberOfIterations(lineNumber+1, coverage) > 0){
								while(dsFlag)
								{
									sCurrentLine = br.readLine();
									lineNumber++;
									
									if(sCurrentLine.contains("}"))
									{
										dsFlag = false;
									}
									
									if(getNumberOfIterations(lineNumber, coverage) > 0) {
										DataStructureNode childNode = switchDataStructures(sCurrentLine, scopeNode);
										if(childNode != null)
											scopeNode.addChild(childNode);
									}
								}
							}
							
							path.addNode(scopeNode);
						}
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
	public DataStructureNode switchDataStructures(String sCurrentLine, ScopeNode parent){
		String[] tokens = new String[15];
		tokens = sCurrentLine.split(" ");

		
		
		String value = tokens[0];
		DataStructureNode node = null;
		
		//System.out.println(sCurrentLine);
		
		if(sCurrentLine.contains("int"))
		{
			value = "int";
			variables.put(tokens[1], value);
		}
		else if(sCurrentLine.contains("double"))
		{
			value = "double";
			variables.put(tokens[1], value);
		}
		else if(sCurrentLine.contains("String"))
		{
			value = "String";
			variables.put(tokens[1], value);
		}
		else if(sCurrentLine.contains("char"))
		{
			value = "char";
			variables.put(tokens[1], value);
		}
		else if(sCurrentLine.contains("byte"))
		{
			value = "byte";
			variables.put(tokens[1], value);
		}
		else if(sCurrentLine.contains("boolean"))
		{
			value = "boolean";
			variables.put(tokens[1], value);
		}
		else if(value.contains("float"))
		{
			value = "float";
			variables.put(tokens[1], value);
		}
		else
		{
			if(!sCurrentLine.isEmpty() && sCurrentLine.contains("="))
			{
				value = variables.get(tokens[0].trim());
			}
		}
		
		switch(value){
			case "int":
				node = new DataStructureNode(parent, DataStructureNodeType.INT, checkOperation(sCurrentLine));
			break;
			
			case "double":
				node = new DataStructureNode(parent, DataStructureNodeType.DOUBLE, checkOperation(sCurrentLine));
			break;
			
			case "float":
				node = new DataStructureNode(parent, DataStructureNodeType.FLOAT, checkOperation(sCurrentLine));
			break;
			
			case "String":
				node = new DataStructureNode(parent, DataStructureNodeType.STRING, checkOperation(sCurrentLine));
			break;
			
			case "char":
				node = new DataStructureNode(parent, DataStructureNodeType.CHAR, checkOperation(sCurrentLine));
			break;
			
			case "byte":
				node = new DataStructureNode(parent, DataStructureNodeType.BYTE, checkOperation(sCurrentLine));
			break;
			
			case "boolean":
				node = new DataStructureNode(parent, DataStructureNodeType.BOOLEAN, checkOperation(sCurrentLine));
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
	public static int getNumberOfIterations(int lineNumber, File coverage){
		int hitsNumber = -1;
		
		try {
			//Pattern lineHits = Pattern.compile("<line number=\"" + lineNumber + "\"");
			BufferedReader xmlReader = new BufferedReader(new FileReader(coverage));
			String xmlLine = xmlReader.readLine();
			
			String pattern = "<line number=\"" + lineNumber +"\"";
			
			while(!xmlLine.contains(pattern)){
				xmlLine = xmlReader.readLine();
				if(xmlLine == null)
					break;
			}
			//Now xmlLine should have the correct line entry that we need to process
			
			if(xmlLine != null){
				String[] entries = xmlLine.split(" ");
				//Puts together an array with {"<line", "number="lineNumber"", "hits="hitsNumber"", etc(after this we don't care)
				
				entries[2] = entries[2].substring(6, entries[2].length() - 1);
				//This should get the substring between the "" in the hits entry
				
				hitsNumber = Integer.parseInt(entries[2]);
			}
			
			xmlReader.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return hitsNumber;
	}
	
//	/**
//	 * This method uses the coverage report to check if a scope node's branch evaluates to true, if so the
//	 * the statements contained within may be executed.
//	 * @param lineNumber
//	 * @param coverage
//	 * @return boolean(true if branch was successful, false otherwise)
//	 */
//	public static boolean checkBranchExecutions(int lineNumber, File coverage){
//		boolean branchExecution = false;
//		
//		
//		try {
//			//Pattern lineHits = Pattern.compile("<line number=\"" + lineNumber + "\"");
//			BufferedReader xmlReader = new BufferedReader(new FileReader(coverage));
//			String xmlLine = xmlReader.readLine();
//			
//			String pattern = "<line number=\"" + lineNumber +"\"";
//			
//			while(!xmlLine.contains(pattern)){
//				xmlLine = xmlReader.readLine();
//				if(xmlLine == null)
//					break;
//			}
//			//Now xmlLine should have the correct line entry that we need to process
//			
//			if(xmlLine != null && xmlLine.contains("true"))
//				branchExecution = true;
//						
//			xmlReader.close();
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//				
//		return branchExecution;
//	}
	
}
