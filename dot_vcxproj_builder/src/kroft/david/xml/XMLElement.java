package kroft.david.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class XMLElement {
	
	public String name;
	public String value;
	public Map<String, String> attribs;
	public List<XMLElement> children;
	public boolean isValue;
	
	public XMLElement(String name, String value) {
		this.name = name;
		this.value = value;
		this.attribs = null;
		this.children = null;
		this.isValue = true;
	}
	public XMLElement(String name) {
		this.name = name;
		this.value = null;
		this.attribs = new HashMap<String, String>();
		this.children = new ArrayList<XMLElement>();
		this.isValue = false;
	}
	
	public String build() {
		
		StringBuilder sb = new StringBuilder();
		sb.append("<" + name + " ");
		
		if (attribs != null && !attribs.isEmpty()) for (Entry<String, String> s : attribs.entrySet()) {
			sb.append(s.getKey() + "=\"" + s.getValue() + "\" ");
		}
		
		sb.append(" >" + (isValue ? "" : System.lineSeparator()));
		
		if (!isValue) for (XMLElement e : children) sb.append(e.build());
		else sb.append(value);
		
		sb.append("</" + name + ">" + System.lineSeparator());
		
		return sb.toString();
	}
	
	public void addAttrib(String name, String value) {
		attribs.put(name, value);
	}
	
	public void addChild(XMLElement e) {
		children.add(e);
	}
}