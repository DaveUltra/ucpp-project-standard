package kroft.david.ucpp.vcxproj;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import kroft.david.ucpp.FileParser;
import kroft.david.ucpp.cfgtxt.ProjectConfig;
import kroft.david.ucpp.cfgtxt.ProjectType;
import kroft.david.xml.XMLElement;
import kroft.david.xml.XMLWriter;

public class VCXProj {
	
	public static final List<String> SUPPORTED_TOOLSETS = new ArrayList<String>();
	
	// Get the equivalent string used by VS for project type.
	public static String projectTypeToString(ProjectType prjType) {
		if (prjType == ProjectType.PROJTYPE_EXE)
			return "Application";
		else if (prjType == ProjectType.PROJTYPE_LIB)
			return "StaticLibrary";
		else
			return "INVALID PROJECT TYPE";
	}

	public static void init()
	{
		SUPPORTED_TOOLSETS.add("v110");
		SUPPORTED_TOOLSETS.add("v140");
		SUPPORTED_TOOLSETS.add("v141");
	}

	public static void generate(String platformToolset, String solution, String project, ProjectConfig cfg)
	{
		if (!SUPPORTED_TOOLSETS.contains(platformToolset)) return;

		XMLWriter proj = null;
		try {
			proj = new XMLWriter(solution + '/' + project + '/' + project + ".vcxproj");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}

		XMLElement xml_project = new XMLElement("Project");
		xml_project.addAttrib("DefaultTargets", "Build");
		xml_project.addAttrib("ToolsVersion", "4.0");
		xml_project.addAttrib("xmlns", "http://schemas.microsoft.com/developer/msbuild/2003");

		XMLElement xml_projConfigGroup = new XMLElement("ItemGroup");
		xml_projConfigGroup.addAttrib("Label", "ProjectConfigurations");

		XMLElement xml_projConfig_DebugWin32 = new XMLElement("ProjectConfiguration");
		xml_projConfig_DebugWin32.addAttrib("Include", "Debug|Win32");
		xml_projConfig_DebugWin32.addChild(new XMLElement("Configuration", "Debug"));
		xml_projConfig_DebugWin32.addChild(new XMLElement("Platform", "Win32"));
		XMLElement xml_projConfig_ReleaseWin32 = new XMLElement("ProjectConfiguration");
		xml_projConfig_ReleaseWin32.addAttrib("Include", "Release|Win32");
		xml_projConfig_ReleaseWin32.addChild(new XMLElement("Configuration", "Release"));
		xml_projConfig_ReleaseWin32.addChild(new XMLElement("Platform", "Win32"));

		xml_projConfigGroup.addChild(xml_projConfig_DebugWin32);
		xml_projConfigGroup.addChild(xml_projConfig_ReleaseWin32);
		xml_project.addChild(xml_projConfigGroup);

		XMLElement xml_propertyGroup = new XMLElement("PropertyGroup");
		xml_propertyGroup.addAttrib("Label", "Globals");
		xml_propertyGroup.addChild(new XMLElement("ProjectGuid", "{" + UUID.randomUUID().toString() + "}"));
		xml_propertyGroup.addChild(new XMLElement("RootNamesapce", project));

		xml_project.addChild(xml_propertyGroup);

		XMLElement import_0 = new XMLElement("Import");
		import_0.addAttrib("Project", "$(VCTargetsPath)\\Microsoft.Cpp.Default.props");
		xml_project.addChild(import_0);

		XMLElement xml_propertyGroup_Debug32 = new XMLElement("PropertyGroup");
		xml_propertyGroup_Debug32.addAttrib("Condition", "'$(Configuration)|$(Platform)'=='Debug|Win32'");
		xml_propertyGroup_Debug32.addAttrib("Label", "Configuration");
		xml_propertyGroup_Debug32.addChild(new XMLElement("ConfigurationType", projectTypeToString(cfg.getProjectType())));
		xml_propertyGroup_Debug32.addChild(new XMLElement("UseDebugLibraries", "true"));
		xml_propertyGroup_Debug32.addChild(new XMLElement("PlatformToolset", platformToolset));
		xml_propertyGroup_Debug32.addChild(new XMLElement("CharacterSet", "MultiByte"));
		XMLElement xml_propertyGroup_Release32 = new XMLElement("PropertyGroup");
		xml_propertyGroup_Release32.addAttrib("Condition", "'$(Configuration)|$(Platform)'=='Release|Win32'");
		xml_propertyGroup_Release32.addAttrib("Label", "Configuration");
		xml_propertyGroup_Release32.addChild(new XMLElement("ConfigurationType", projectTypeToString(cfg.getProjectType())));
		xml_propertyGroup_Release32.addChild(new XMLElement("UseDebugLibraries", "false"));
		xml_propertyGroup_Release32.addChild(new XMLElement("PlatformToolset", platformToolset));
		xml_propertyGroup_Release32.addChild(new XMLElement("WholeProgramOptimization", "false"));
		xml_propertyGroup_Release32.addChild(new XMLElement("CharacterSet", "MultiByte"));

		xml_project.addChild(xml_propertyGroup_Debug32);
		xml_project.addChild(xml_propertyGroup_Release32);

		XMLElement xml_import = new XMLElement("Import");
		xml_import.addAttrib("Project", "$(VCTargetsPath)\\Microsoft.Cpp.Default.props");
		xml_import.addAttrib("Project", "$(VCTargetsPath)\\Microsoft.Cpp.props");
		xml_project.addChild(xml_import);

		XMLElement xml_importGroup32 = new XMLElement("ImportGroup");
		xml_importGroup32.addAttrib("Label", "PropertySheets");
		xml_importGroup32.addAttrib("Condition", "'$(Configuration)|$(Platform)'=='Debug|Win32'");
		{
			XMLElement xml_importGroupIm = new XMLElement("Import");
			xml_importGroupIm.addAttrib("Project", "$(UserRootDir)\\Microsoft.Cpp.$(Platform).user.props");
			xml_importGroupIm.addAttrib("Condition", "exists('$(UserRootDir)\\Microsoft.Cpp.$(Platform).user.props')");
			xml_importGroupIm.addAttrib("Label", "LocalAppDataPlatform");
			xml_importGroup32.addChild(xml_importGroupIm);
		}
		XMLElement xml_importGroup64 = new XMLElement("ImportGroup");
		xml_importGroup64.addAttrib("Label", "PropertySheets");
		xml_importGroup64.addAttrib("Condition", "'$(Configuration)|$(Platform)'=='Release|Win32'");
		{
			XMLElement xml_importGroupIm = new XMLElement("Import");
			xml_importGroupIm.addAttrib("Project", "$(UserRootDir)\\Microsoft.Cpp.$(Platform).user.props");
			xml_importGroupIm.addAttrib("Condition", "exists('$(UserRootDir)\\Microsoft.Cpp.$(Platform).user.props')");
			xml_importGroupIm.addAttrib("Label", "LocalAppDataPlatform");
			xml_importGroup64.addChild(xml_importGroupIm);
		}

		xml_project.addChild(xml_importGroup32);
		xml_project.addChild(xml_importGroup64);

		XMLElement itemDefGroup_Debug32 = new XMLElement("ItemDefinitionGroup");
		itemDefGroup_Debug32.addAttrib("Condition", "'$(Configuration)|$(Platform)'=='Debug|Win32'");
		XMLElement itemDefGroup_Release32 = new XMLElement("ItemDefinitionGroup");
		itemDefGroup_Release32.addAttrib("Condition", "'$(Configuration)|$(Platform)'=='Release|Win32'");

		// Include paths.
		XMLElement includeDirsDebug = new XMLElement("ClCompile");
		XMLElement includeDirsRelease = new XMLElement("ClCompile");
		{
			// Basic configuration.
			includeDirsDebug.addChild(new XMLElement("WarningLevel", "Level3"));
			includeDirsDebug.addChild(new XMLElement("Optimization", "Disabled"));
			includeDirsDebug.addChild(new XMLElement("MultiProcessorCompilation", "true"));
			includeDirsDebug.addChild(new XMLElement("MinimalRebuild", "false"));
			includeDirsDebug.addChild(new XMLElement("PreprocessorDefinitions", "_MBCS;%(PreprocessorDefinitions)"));
			includeDirsRelease.addChild(new XMLElement("WarningLevel", "Level3"));
			includeDirsRelease.addChild(new XMLElement("Optimization", "Disabled"));
			includeDirsRelease.addChild(new XMLElement("MultiProcessorCompilation", "true"));
			includeDirsRelease.addChild(new XMLElement("MinimalRebuild", "false"));

			String addinc = "";
			
			// Self inclusion.
			addinc += "$(SolutionDir)" + project + ';';
			
			for (String s : cfg.getIncludePaths()) {
				addinc += "$(SolutionDir)" + s + ';';
			}
			addinc += "%(AdditionalIncludeDirectories)";
			includeDirsDebug.addChild(new XMLElement("AdditionalIncludeDirectories", addinc));
			includeDirsRelease.addChild(new XMLElement("AdditionalIncludeDirectories", addinc));
	
			itemDefGroup_Debug32.addChild(includeDirsDebug);
			itemDefGroup_Release32.addChild(includeDirsRelease);
		}

		XMLElement debug_libsLink = new XMLElement("Link");
		XMLElement debug_libsLib = new XMLElement("Lib");
		debug_libsLink.addChild(new XMLElement("GenerateDebugInformation", "true"));
		{
			// Debug library directories.
			String debug_addlibdirs = "";
			for (String s : cfg.getDebugLibraryPaths()) {
				debug_addlibdirs += "$(SolutionDir)" + s + ';';
			}
			
			debug_addlibdirs += "%(AdditionalLibraryDirectories)";
			debug_libsLink.addChild(new XMLElement("AdditionalLibraryDirectories", debug_addlibdirs));
			debug_libsLib.addChild(new XMLElement("AdditionalLibraryDirectories", debug_addlibdirs));
		}
		
		XMLElement release_libsLink = new XMLElement("Link");
		XMLElement release_libsLib = new XMLElement("Lib");
		release_libsLink.addChild(new XMLElement("GenerateDebugInformation", "false"));
		{
			// Release library directories.
			String release_addlibdirs = "";
			for (String s : cfg.getReleaseLibraryPaths()) {
				release_addlibdirs += "$(SolutionDir)" + s + ';';
			}
			
			release_addlibdirs += "%(AdditionalLibraryDirectories)";
			release_libsLink.addChild(new XMLElement("AdditionalLibraryDirectories", release_addlibdirs));
			release_libsLib.addChild(new XMLElement("AdditionalLibraryDirectories", release_addlibdirs));
		}

		// Libraries.
		{
			String addlibs = "";
			for (String s : cfg.getLibraries()) {
				addlibs += s + ".lib;";
			}
			addlibs += "%(AdditionalDependencies)";
			
			debug_libsLink.addChild(new XMLElement("AdditionalDependencies", addlibs));
			debug_libsLib.addChild(new XMLElement("AdditionalDependencies", addlibs));
			release_libsLink.addChild(new XMLElement("AdditionalDependencies", addlibs));
			release_libsLib.addChild(new XMLElement("AdditionalDependencies", addlibs));
		}
		
		itemDefGroup_Debug32.addChild(debug_libsLink);
		itemDefGroup_Debug32.addChild(debug_libsLib);
		itemDefGroup_Release32.addChild(release_libsLink);
		itemDefGroup_Release32.addChild(release_libsLib);
		xml_project.addChild(itemDefGroup_Debug32);
		xml_project.addChild(itemDefGroup_Release32);

		
		
		String rootDirAbs = new File(solution + '/' + project).getAbsolutePath();
		{
			// Which .cpps to compile.
			XMLElement cppGroup = new XMLElement("ItemGroup");
			
			for (File f : FileParser.cpps) {
				XMLElement cppel = new XMLElement("ClCompile");
				cppel.addAttrib("Include", f.getAbsolutePath().substring(rootDirAbs.length() + 1));
				cppGroup.addChild(cppel);
			}
			xml_project.addChild(cppGroup);
		}

		{
			// Same but with headers (why not, they're already specified in filters anyway).
			XMLElement hGroup = new XMLElement("ItemGroup");
			for (File f : FileParser.headers) {
				XMLElement hel = new XMLElement("ClInclude");
				hel.addAttrib("Include", f.getAbsolutePath().substring(rootDirAbs.length() + 1));
				hGroup.addChild(hel);
			}
			for (File f : FileParser.inlines) {
				XMLElement hel = new XMLElement("ClInclude");
				hel.addAttrib("Include", f.getAbsolutePath().substring(rootDirAbs.length() + 1));
				hGroup.addChild(hel);
			}
			for (File f : FileParser.asm) {
				XMLElement hel = new XMLElement("ClInclude");
				hel.addAttrib("Include", f.getAbsolutePath().substring(rootDirAbs.length() + 1));
				hGroup.addChild(hel);
			}
			xml_project.addChild(hGroup);
		}

		// Someone knows what this does?
		XMLElement some_import = new XMLElement("Import");
		some_import.addAttrib("Project", "$(VCTargetsPath)\\Microsoft.Cpp.targets");
		xml_project.addChild(some_import);

		// Done
		proj.addElement(xml_project);

		proj.write();
		proj.close();
	}
}