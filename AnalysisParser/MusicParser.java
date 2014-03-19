import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

import javax.lang.model.element.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/*import musicplusplusinterfaces.lists.DataStructureNode;
import musicplusplusinterfaces.lists.DataStructureNodeType;
import musicplusplusinterfaces.lists.ExecutionPath;
import musicplusplusinterfaces.lists.Operation;
import musicplusplusinterfaces.lists.ScopeNode;
import musicplusplusinterfaces.lists.ScopeNodeType;*/

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class MusicParser {

	ExecutionPath path;
	//boolean dsFlag;
	//int lineNumber;
	
	public static void main(String[] args) {
		
		File coverage = new File("coverage.xml");
		int num = getLineNumberFromXML(9, coverage);
		
		System.out.println("Num: " + num);
	}
	
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
						//TODO: logic to find number of iterations in XML file and of next line
						scopeNode = new ScopeNode(null, numIterations);
						scopeNode.setType(ScopeNodeType.WHILE);
						
						nextLineIterations = 0;
						
						if(numIterations <= 1 && nextLineIterations == 0){
							path.addNode(scopeNode);
							break;
						}
						
						
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
					
/*Do-While*/		case "do":
						//TODO: logic to find number of iterations in XML file
						scopeNode = new ScopeNode(null, numIterations);
						scopeNode.setType(ScopeNodeType.DOWHILE);
						
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
						
						dsFlag = true;
																		
						path.addNode(scopeNode);		
					break;
					
/*Switch*/			case "switch":
						//TODO: logic to find number of iterations in XML file
						//TODO: Handle anything inside of scope node
						scopeNode = new ScopeNode(null, numIterations);
						scopeNode.setType(ScopeNodeType.SWITCHBODY);
						
						
						path.addNode(scopeNode);
						
					break;
					
/*For*/				case "for":
						//TODO: logic to find number of iterations in XML file and nextLineIterations
						scopeNode = new ScopeNode(null, numIterations);
						scopeNode.setType(ScopeNodeType.FOR);
						
						nextLineIterations = 0;
						
						if(numIterations <= 1 && nextLineIterations == 0){
							path.addNode(scopeNode);
							break;
						}
						
						
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

						
					break;
					
/*If Statement*/	case "if":
						//TODO: logic to find number of iterations in XML file
						//TODO: Handle anything inside of scope node
						scopeNode = new ScopeNode(null, numIterations);
						scopeNode.setType(ScopeNodeType.FOR);
						
					break;
				}
				

//				System.out.println(sCurrentLine);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	
		
		return path;
		
	}

	public DataStructureNode switchDataStructures(String[] tokens, String sCurrentLine, ScopeNode parent){
		
		String value = tokens[0];
		DataStructureNode node = null;		
		
		switch(value){
			case "int":
				node = new DataStructureNode(parent, DataStructureNodeType.INT, checkOperation(sCurrentLine));
				path.addNode(node);
			break;
			
			case "double":
				node = new DataStructureNode(parent, DataStructureNodeType.DOUBLE, checkOperation(sCurrentLine));
				path.addNode(node);
			break;
			
			case "float":
				node = new DataStructureNode(parent, DataStructureNodeType.FLOAT, checkOperation(sCurrentLine));
				path.addNode(node);
			break;
			
			case "String":
				node = new DataStructureNode(parent, DataStructureNodeType.STRING, checkOperation(sCurrentLine));
				path.addNode(node);
			break;
			
			case "char":
				node = new DataStructureNode(parent, DataStructureNodeType.CHAR, checkOperation(sCurrentLine));
				path.addNode(node);
			break;
			
			case "byte":
				node = new DataStructureNode(parent, DataStructureNodeType.BYTE, checkOperation(sCurrentLine));
				path.addNode(node);
			break;
			
			case "boolean":
				node = new DataStructureNode(parent, DataStructureNodeType.BOOLEAN, checkOperation(sCurrentLine));
				path.addNode(node);
			break;
		}
		
		return node;

	}
	
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
	
	//TODO: Find number of iterations in XML file for a particular line number
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
			
			/*DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder;
			dBuilder = dbFactory.newDocumentBuilder();
			
			org.w3c.dom.Document doc = dBuilder.parse(coverage);
			doc.getDocumentElement().normalize();
			
			NodeList nodes = doc.getElementsByTagName("lines");

			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
		
					Element element = (Element) node;
					System.out.println("Line Number: " + getValue("number", element));
//					System.out.println("Stock Price: " + getValue("price", element));
//					System.out.println("Stock Quantity: " + getValue("quantity", element));
				}
			}*/
			
			xmlReader.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return hitsNumber;
	}
	
	private static String getValue(String tag, Element element) {
		NodeList nodes = ((org.w3c.dom.Document) element).getElementsByTagName(tag).item(0).getChildNodes();
		Node node = (Node) nodes.item(0);
		return node.getNodeValue();
	}
		
	
}
