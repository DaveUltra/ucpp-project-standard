package kroft.david.xml;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class XMLWriter {
	
	public PrintWriter file;
	public List<XMLElement> elements;
	
	public XMLWriter(String filePath) throws FileNotFoundException {
		file = new PrintWriter(filePath);
		elements = new ArrayList<XMLElement>();
	}
	
	public void write() {
		file.println("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		for (XMLElement e : elements) {
			file.print(e.build());
		}
	}
	
	public void close() {
		file.flush();
		file.close();
	}
	
	public void addElement(XMLElement e) {
		elements.add(e);
	}
}