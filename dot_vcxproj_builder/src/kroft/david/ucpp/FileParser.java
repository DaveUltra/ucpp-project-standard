package kroft.david.ucpp;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileParser {
	
	// Code
	public static List<File> headers = new ArrayList<File>();
	public static List<File> cpps = new ArrayList<File>();
	public static List<File> inlines = new ArrayList<File>();
	public static List<File> asm = new ArrayList<File>();
	
	// VS Wise
	public static List<File> filters = new ArrayList<File>();
	
	public static boolean parseAll(String sol, String proj) {
		
		headers.clear();
		cpps.clear();
		inlines.clear();
		filters.clear();
		return listAllFiles(sol + "\\" + proj);
	}
	
	private static boolean listAllFiles(String dir) {
		
		File d = new File(dir);
		if (d.exists())
		{
			for (File f : d.listFiles()) {
				if (f.isDirectory()) {
					if (!f.getName().equals("Debug") && !f.getName().equals("Release") && !f.getName().equals("x64"))
						filters.add(f);
					listAllFiles(f.getAbsolutePath());
				} else {
					String n = f.getName();
					if (n.endsWith(".cpp") || n.endsWith(".c")) cpps.add(f);
					else if (n.endsWith(".h")) headers.add(f);
					else if (n.endsWith(".inl")) inlines.add(f);
					else if (n.endsWith(".asm")) asm.add(f);
				}
			}
			return true;
		}
		else 
		{
			return false;
		}
	}
}