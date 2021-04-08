package kroft.david.ucpp.cfgtxt;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProjectConfig {

	private ProjectType projectType;
	
	private List<String> includePaths;
	private List<String> libraries;
	private List<String> debugLibraryPaths;
	private List<String> releaseLibraryPaths;
	
	
	
	private ProjectConfig() {
		this.includePaths = new ArrayList<String>();
		this.libraries = new ArrayList<String>();
		this.debugLibraryPaths = new ArrayList<String>();
		this.releaseLibraryPaths = new ArrayList<String>();
	}
	
	/**
	 * Open and read the contents of a CFG.TXT file.
	 * @param projectRootPath The root path of the project the CFG.TXT we want to scan belongs to.
	 * @return A valid ProjectConfig object if the file is syntactically valid, null otherwise or if the file does not exist.
	 */
	@SuppressWarnings("deprecation")
	public static ProjectConfig open(String projectRootPath) throws IOException {
		
		File file = new File(projectRootPath + "/CFG.TXT");
		if (!file.exists()) {
			return null;
		}
		
		// Parse time.
		ProjectConfig cfg = new ProjectConfig();
		
		DataInputStream stream = new DataInputStream(new FileInputStream(file));
		
		String line;
		while ((line = stream.readLine()) != null)
		{
			if (line.length() > 0)
			{
				String val = line.substring(line.indexOf(' ') + 1);
				
				if (line.startsWith("projType"))
				{
					if      (val.equals("exe")) cfg.projectType = ProjectType.PROJTYPE_EXE;
					else if (val.equals("lib")) cfg.projectType = ProjectType.PROJTYPE_LIB;
					else                        cfg.projectType = ProjectType.PROJTYPE_INVALID;
				}
				else if (line.startsWith("include"))
					cfg.includePaths.add(val);
				
				else if (line.startsWith("libPathD"))
					cfg.debugLibraryPaths.add(val);
				
				else if (line.startsWith("libPathR"))
					cfg.releaseLibraryPaths.add(val);
				
				else if (line.startsWith("lib"))
					cfg.libraries.add(val);
			}
		}
		stream.close();
		
		return cfg;
	}


	
	public ProjectType getProjectType() {
		return projectType;
	}

	public List<String> getIncludePaths() {
		return includePaths;
	}
	
	public List<String> getLibraries() {
		return libraries;
	}

	public List<String> getDebugLibraryPaths() {
		return debugLibraryPaths;
	}
	
	public List<String> getReleaseLibraryPaths() {
		return releaseLibraryPaths;
	}
}