import java.io.File;
import java.util.List;

public class MusicParserRunner {

	public static void main(String[] args) {	
		System.out.println("----Beginning----");
		
		RepGenerator repGen = new RepGenerator();
		File file;
		ExecutionPath testPath, path;
		MusicParser parser = new MusicParser();
		
		String[] codeSnippets = new String[20];
		File[] sourceFiles = new File[20];
		File[] coverageReports = new File[20];
		ExecutionPath[] testPaths = new ExecutionPath[20];
		
		codeSnippets[0] = "IfCondition";
		sourceFiles[0] = new File(".\\bin\\IfCondition.java");
		coverageReports[0] = new File(".\\bin\\coverage IfCondition.xml");
		testPaths[0] = repGen.ifCondition();
		
				
		codeSnippets[1] = "ElseIfCondition";
		sourceFiles[1] = new File(".\\bin\\ElseIfCondition.java");
		coverageReports[1] = new File(".\\bin\\coverage ElseIfCondition.xml");
		testPaths[1] = repGen.elseIfCondition();
		
		
		codeSnippets[2] = "ElseCondition";
		sourceFiles[2] = new File(".\\bin\\ElseCondition.java");
		coverageReports[2] = new File(".\\bin\\coverage ElseCondition.xml");
		testPaths[2] = repGen.elseCondition();
		
		
		codeSnippets[3] = "NoCondition";
		sourceFiles[3] = new File(".\\bin\\NoCondition.java");
		coverageReports[3] = new File(".\\bin\\coverage NoCondition.xml");
		testPaths[3] = repGen.noCondition();
		
		
		codeSnippets[4] = "WhileAddition";
		sourceFiles[4] = new File(".\\bin\\WhileAddition.java");
		coverageReports[4] = new File(".\\bin\\coverage WhileAddition.xml");
		testPaths[4] = repGen.whileAddition();
		
				
		codeSnippets[5] = "StringTest";
		sourceFiles[5] = new File(".\\bin\\StringTest.java");
		coverageReports[5] = new File(".\\bin\\coverage StringTest.xml");
		testPaths[5] = repGen.stringTest();
		
		
		codeSnippets[6] = "WhileSubtraction";
		sourceFiles[6] = new File(".\\bin\\WhileSubtraction.java");
		coverageReports[6] = new File(".\\bin\\coverage WhileSubtraction.xml");
		testPaths[6] = repGen.whileSubtraction();
		
		
		codeSnippets[7] = "NoWhile";
		sourceFiles[7] = new File(".\\bin\\NoWhile.java");
		coverageReports[7] = new File(".\\bin\\coverage NoWhile.xml");
		testPaths[7] = repGen.noWhile();
		
		
		codeSnippets[8] = "StringTestDo";
		sourceFiles[8] = new File(".\\bin\\StringTestDo.java");
		coverageReports[8] = new File(".\\bin\\coverage StringTestDo.xml");
		testPaths[8] = repGen.stringTestDo();
		
				
		codeSnippets[9] = "DoWhileMultiplication";
		sourceFiles[9] = new File(".\\bin\\DoWhileMultiplication.java");
		coverageReports[9] = new File(".\\bin\\coverage DoWhileMultiplication.xml");
		testPaths[9] = repGen.doWhileMultiplication();
		
		
		codeSnippets[10] = "DoWhileDivision";
		sourceFiles[10] = new File(".\\bin\\DoWhileDivision.java");
		coverageReports[10] = new File(".\\bin\\coverage DoWhileDivision.xml");
		testPaths[10] = repGen.doWhileDivision();
		
		
		codeSnippets[11] = "NoDoWhile";
		sourceFiles[11] = new File(".\\bin\\NoDoWhile.java");
		coverageReports[11] = new File(".\\bin\\coverage NoDoWhile.xml");
		testPaths[11] = repGen.noDoWhile();
		
		
		codeSnippets[12] = "ForAddition";
		sourceFiles[12] = new File(".\\bin\\ForAddition.java");
		coverageReports[12] = new File(".\\bin\\coverage ForAddition.xml");
		testPaths[12] = repGen.forAddition();
		
		
		codeSnippets[13] = "ForSubtraction";
		sourceFiles[13] = new File(".\\bin\\ForSubtraction.java");
		coverageReports[13] = new File(".\\bin\\coverage ForSubtraction.xml");
		testPaths[13] = repGen.forSubtraction();
		
		codeSnippets[14] = "ForMultiplication";
		sourceFiles[14] = new File(".\\bin\\ForMultiplication.java");
		coverageReports[14] = new File(".\\bin\\coverage ForMultiplication.xml");
		testPaths[14] = repGen.forMultiplication();
		
		
		codeSnippets[15] = "NoFor";
		sourceFiles[15] = new File(".\\bin\\NoFor.java");
		coverageReports[15] = new File(".\\bin\\coverage NoFor.xml");
		testPaths[15] = repGen.noFor();
		
		
		codeSnippets[16] = "SwitchNoCascade";
		sourceFiles[16] = new File(".\\bin\\SwitchNoCascade.java");
		coverageReports[16] = new File(".\\bin\\coverage SwitchNoCascade.xml");
		testPaths[16] = repGen.switchNoCascade();
		
		
		codeSnippets[17] = "SwitchCascade";
		sourceFiles[17] = new File(".\\bin\\SwitchCascade.java");
		coverageReports[17] = new File(".\\bin\\coverage SwitchCascade.xml");
		testPaths[17] = repGen.switchCascade();
		
		
		codeSnippets[18] = "SwitchNoCases";
		sourceFiles[18] = new File(".\\bin\\SwitchNoCases.java");
		coverageReports[18] = new File(".\\bin\\coverage SwitchNoCases.xml");
		testPaths[18] = repGen.switchNoCases();
		
		
		codeSnippets[19] = "NoStructure";
		sourceFiles[19] = new File(".\\bin\\NoStructure.java");
		coverageReports[19] = new File(".\\bin\\coverage NoStructure.xml");
		testPaths[19] = repGen.noStructure();
		
		
		
		check(sourceFiles[0], coverageReports[0], testPaths[0], codeSnippets[0]);
		check(sourceFiles[1], coverageReports[1], testPaths[1], codeSnippets[1]);
		check(sourceFiles[2], coverageReports[2], testPaths[2], codeSnippets[2]);
		check(sourceFiles[3], coverageReports[3], testPaths[3], codeSnippets[3]);
		check(sourceFiles[4], coverageReports[4], testPaths[4], codeSnippets[4]);
		check(sourceFiles[5], coverageReports[5], testPaths[5], codeSnippets[5]);
		check(sourceFiles[6], coverageReports[6], testPaths[6], codeSnippets[6]);
		check(sourceFiles[7], coverageReports[7], testPaths[7], codeSnippets[7]);
		check(sourceFiles[8], coverageReports[8], testPaths[8], codeSnippets[8]);
		check(sourceFiles[9], coverageReports[9], testPaths[9], codeSnippets[9]);
		check(sourceFiles[10], coverageReports[10], testPaths[10], codeSnippets[10]);
		check(sourceFiles[11], coverageReports[11], testPaths[11], codeSnippets[11]);
		check(sourceFiles[12], coverageReports[12], testPaths[12], codeSnippets[12]);
		check(sourceFiles[13], coverageReports[13], testPaths[13], codeSnippets[13]);
		check(sourceFiles[14], coverageReports[14], testPaths[14], codeSnippets[14]);
		check(sourceFiles[15], coverageReports[15], testPaths[15], codeSnippets[15]);
		check(sourceFiles[16], coverageReports[16], testPaths[16], codeSnippets[16]);
		check(sourceFiles[17], coverageReports[17], testPaths[17], codeSnippets[17]);
		check(sourceFiles[18], coverageReports[18], testPaths[18], codeSnippets[18]);
		check(sourceFiles[19], coverageReports[19], testPaths[19], codeSnippets[19]);		
	}
	
	public static void check(File src, File cvg, ExecutionPath testPath, String code){
		
		ExecutionPath path;
		MusicParser parser = new MusicParser();
		
		System.out.println("---- Calling Parser on: " + code + " ----");
		
		path = parser.parseFile(cvg, src);
		System.out.println("Correct Execution path:  " + path.equals(testPath));
		
		if(!path.equals(testPath)){
			System.out.println();
			System.out.println("****** PATH FROM PARSER ******");
			for(Node n : path.getNodes())
			{
			System.out.println(n.toString());
			}
			System.out.println();
			System.out.println("****** PATH FROM REPGEN *******");
			for(Node n : testPath.getNodes())
			{
				System.out.println(n.toString());
			}
		}
		System.out.println();
		
	}
}