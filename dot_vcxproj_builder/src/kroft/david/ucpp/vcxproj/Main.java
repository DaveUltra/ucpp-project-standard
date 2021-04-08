package kroft.david.ucpp.vcxproj;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import kroft.david.ucpp.FileParser;
import kroft.david.ucpp.cfgtxt.ProjectConfig;

public class Main {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		
		// What? Yeah, automatic copyright.
		System.out.println("Visual Studio C++ project files builder");
		System.out.println("(C) " + Integer.toString(new Date().getYear() + 1900) + " David Kroft.\n");

		
		
		VCXProj.init();

		Scanner scanner = new Scanner(System.in);

		String solution = "null";
		
		if (args.length == 1) {
			solution = args[0];
			System.out.println("Started in solution \"" + solution + "\".");
		}
		else if (args.length == 0) {
			System.out.print("Enter the solution : ");
			solution = scanner.nextLine();
		}
		else {
			System.out.println("[ERROR]: Too many arguments! Give only a solution name, or nothing.");
		}
		
		
		
		// Display a list of directories containing a CFG.TXT file (supposedly valid).
		// "projects" is the final list of valid project names in the current solution,
		// while "prj" denotes a temporary array to test for valid projects.
		List<String> projects = new ArrayList<String>();
		
		File sol = new File(solution);
		File[] prj = sol.listFiles();
		
		System.out.println("-1) Do nothing.");
		
		int i = 0;
		for (File p : prj)
		{
			if (p.isDirectory() && Arrays.asList(p.list()).contains("CFG.TXT"))
			{
				projects.add(p.getName());
				System.out.println(Integer.toString(i) + ") " + projects.get(i));
				
				i++;
			}
		}
		
		// Ask project.
		System.out.print("Project to rebuild : ");
		int choice = scanner.nextInt();
		
		if (choice < 0 || choice >= projects.size()) {
			scanner.close();
			return;
		}
		
		// List files and regen project files.
		Main.gen(solution, projects.get(choice));
		
		scanner.close();
	}
	
	
	
	// Build project files.
	public static void gen(String solution, String project) {
		
		ProjectConfig cfg = null;
		try {
			cfg = ProjectConfig.open(solution + '/' + project);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (FileParser.parseAll(solution, project))
		{
			VCXProjFilters.generate(solution, project);
			VCXProj.generate("v110", solution, project, cfg);
		}
	}
}