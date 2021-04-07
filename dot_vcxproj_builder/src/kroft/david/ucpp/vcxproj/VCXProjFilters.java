package kroft.david.ucpp.vcxproj;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import kroft.david.ucpp.FileParser;
import kroft.david.xml.XMLElement;
import kroft.david.xml.XMLWriter;

public class VCXProjFilters {

	public static void generate(String solution, String project) {

		XMLWriter filters = null;
		try {
			filters = new XMLWriter(solution + '/' + project + '/' + project + ".vcxproj.filters");
		} catch (FileNotFoundException e) {
			System.exit(1);
		}

		XMLElement xml_project = new XMLElement("Project");
		xml_project.addAttrib("ToolsVersion", "4.0");
		xml_project.addAttrib("xmlns", "http://schemas.microsoft.com/developer/msbuild/2003");

		XMLElement xml_filterGroup = new XMLElement("ItemGroup");
		XMLElement xml_cppGroup = new XMLElement("ItemGroup");
		XMLElement xml_headerGroup = new XMLElement("ItemGroup");

		String rootDirAbs = new File(solution + '/' + project).getAbsolutePath();

		// Filters definition
		for (File f : FileParser.filters) {
			
			XMLElement filter = new XMLElement("Filter"); // Create filter
			filter.addAttrib("Include", f.getAbsolutePath().substring(rootDirAbs.length() + 1)); // Set include="path"
			XMLElement uuid = new XMLElement("UniqueIdentifier", "{" + UUID.randomUUID().toString() + "}"); // Add unique id
			filter.addChild(uuid); // Add to filter
			xml_filterGroup.addChild(filter); // Add to filter group
		}
		
		// Cpp definition
		for (File f : FileParser.cpps) {

			String path = f.getAbsolutePath().substring(rootDirAbs.length() + 1);

			XMLElement cppfile = new XMLElement("ClCompile"); // Create filter
			cppfile.addAttrib("Include", path); // Set include="path"
			XMLElement filter = new XMLElement("Filter", path.substring(0, path.length() - f.getName().length() - (path.contains("\\") ? 1 : 0))); //Set filter
			cppfile.addChild(filter); // Add to filter
			xml_cppGroup.addChild(cppfile); // Add to filter group
		}

		// Header definition
		List<File> fs = new ArrayList<File>();
		fs.addAll(FileParser.headers);
		fs.addAll(FileParser.inlines);
		fs.addAll(FileParser.asm);
		for (File f : fs) {

			String path = f.getAbsolutePath().substring(rootDirAbs.length() + 1);

			XMLElement hfile = new XMLElement("ClInclude"); // Create filter
			hfile.addAttrib("Include", path); // Set include="path"
			XMLElement filter = new XMLElement("Filter", path.substring(0, path.length() - f.getName().length() - (path.contains("\\") ? 1 : 0))); //Set filter
			hfile.addChild(filter); // Add to filter
			xml_headerGroup.addChild(hfile); // Add to filter group
		}
		
		xml_project.addChild(xml_filterGroup);
		xml_project.addChild(xml_cppGroup);
		xml_project.addChild(xml_headerGroup);

		filters.addElement(xml_project);
		filters.write();
		filters.close();
	}
}