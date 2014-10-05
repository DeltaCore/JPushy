package net.ccmob.xml;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class XMLConfig {
	
	/**
	 * This File is licensed under the MIT License (http://opensource.org/licenses/MIT)
	 * 
	 * @author Marcel Benning
	 * @email marcel@ccmob.net
	 * @website https://ccmob.net
	 * 
	 */

	/**
	 * Here is the config saved to and can be modified at runtime. After
	 * modification, call the save method.
	 */
	private XMLNode rootNode = new XMLNode("XMLConfig");

	/**
	 * Stores the fileName if given;
	 */
	private String fileName = null;

	/**
	 * @param filename
	 *            the file to parse from
	 */
	public XMLConfig(String filename) {
		File f = new File(filename);
		this.setFileName(f.getAbsolutePath());
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			parse();
		}
	}

	/**
	 * @param f
	 *            the file to parse from
	 */
	public XMLConfig(File f) {
		this.setFileName(f.getAbsolutePath());
		parse();
	}

	/**
	 * @param lines
	 *            - the file content to parse from
	 */
	public XMLConfig(ArrayList<String> lines) {
		parseLines(lines);
	}

	/**
	 * @param lines
	 *            the file content to parse from
	 */
	public XMLConfig(String[] lines) {
		ArrayList<String> arr = new ArrayList<String>();
		for (int i = 0; i < lines.length; i++) {
			arr.add(lines[i]);
		}
		parseLines(arr);
		arr.clear();
	}

	/**
	 * This function converts the content of the file - specified at the
	 * constructor - to a XMLNode tree, saved in the rootNode varibale.
	 */
	public void parse() {
		try {
			this.setRootNode(XMLParser.parseFile(this.getFileName()));
		} catch (EOFException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This function converts the content of the ArrayList (the content of a
	 * file or a string in the first object) to a XMLNode tree, saved in the
	 * rootNode varibale.
	 */
	public void parseLines(ArrayList<String> lines) {
		try {
			this.setRootNode(XMLParser.parseFileLines(lines));
		} catch (EOFException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This function saves the rootNode to a xmlFile ( @param filename ) with
	 * proper format
	 * 
	 * @param filename
	 */
	public void save(String filename) {
		try {
			File f = new File(filename);
			if (f.exists()) {
				f.delete();
			}
			FileWriter writer = new FileWriter(f);
			writeNode(getRootNode(), 0, writer);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * formats a XMLAttribute
	 * 
	 * @param attr
	 * @return formated attribute string
	 */
	private String formAttribute(XMLAttribute attr) {
		return attr.getAttributeName() + "=\"" + attr.getAttributeValue()
				+ "\"";
	}

	/**
	 * Creates a string with tabs for saving
	 * 
	 * @param tabIndex
	 * @return
	 */
	private String formTabs(int tabIndex) {
		String tabs = "";
		for (int i = 0; i < tabIndex; i++) {
			tabs += "\t";
		}
		return tabs;
	}

	/**
	 * This function writes an XMLNode ( @param node ) to a FileWriter ( @param
	 * writer ) with the proper format. The @param tabIndex specifies the shift
	 * to the right in the file for proper reading.
	 * 
	 * @param node
	 * @param tabIndex
	 * @param writer
	 * @throws IOException
	 */
	private void writeNode(XMLNode node, int tabIndex, FileWriter writer)
			throws IOException {
		String line = formTabs(tabIndex) + "<" + node.getName();
		if (node.getAttributes().size() > 0) {
			for (int i = 0; i < node.getAttributes().size(); i++) {
				line += " " + formAttribute(node.getAttributes().get(i));
			}
		}
		if (node.getChilds().size() == 0) {
			line += "/>";
			writer.write(line + "\n");
		} else {
			line += ">";
			writer.write(line + "\n");
			for (XMLNode child : node.getChilds()) {
				writeNode(child, tabIndex + 1, writer);
			}
			writer.write(formTabs(tabIndex) + "</" + node.getName() + ">\n");
		}
	}

	/**
	 * @return the rootNode
	 */
	public XMLNode getRootNode() {
		return rootNode;
	}

	/**
	 * @param rootNode
	 *            the rootNode to set
	 */
	public void setRootNode(XMLNode rootNode) {
		this.rootNode = rootNode;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName
	 *            the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public static class XMLAttribute {
		/**
		 * Stores the name of the Attribute
		 */
		private String attributeName = "";

		/**
		 * Sores the value of the Attribute
		 */
		private Object attributeValue = "";

		/**
		 * @param name
		 *            Attribute name
		 * @param value
		 *            Attribute value
		 */
		public XMLAttribute(String name, Object value) {
			this.setAttributeName(name);
			this.setAttributeValue(value);
		}

		/**
		 * Just without the value
		 * 
		 * @param name
		 *            Attribute name
		 */
		public XMLAttribute(String name) {
			this(name, null);
		}

		/**
		 * @return the attributeName
		 */
		public String getAttributeName() {
			return attributeName;
		}

		/**
		 * @param attributeName
		 *            the attributeName to set
		 */
		public void setAttributeName(String attributeName) {
			this.attributeName = attributeName;
		}

		/**
		 * @return the attributeValue
		 */
		public Object getAttributeValue() {
			return attributeValue;
		}

		/**
		 * @param attributeValue
		 *            the attributeValue to set
		 */
		public void setAttributeValue(Object attributeValue) {
			this.attributeValue = attributeValue;
		}

	}

	public static class XMLNode {

		/**
		 * Node name
		 */
		private String name;

		/**
		 * Node childs
		 */
		private ArrayList<XMLNode> childs;

		/**
		 * Node attributes
		 */
		private ArrayList<XMLAttribute> attributes;

		/**
		 * The parent of this node
		 */
		private XMLNode parent;

		public XMLNode() {
			this.childs = new ArrayList<XMLNode>();
			this.attributes = new ArrayList<XMLAttribute>();
		}

		/**
		 * The name of the node:
		 * 
		 * @param name
		 */
		public XMLNode(String name) {
			this();
			this.setName(name);
		}

		/**
		 * Internal add function
		 */
		private void addAttributePrivate(XMLAttribute attr) {
			for (XMLAttribute at : this.getAttributes()) {
				if (at.getAttributeName().equals(attr.getAttributeName())) {
					at.setAttributeValue(attr.getAttributeValue());
					return;
				}
			}
			this.getAttributes().add(attr);
		}

		/**
		 * Adds a attribute to the node
		 * 
		 * @param key
		 * @param value
		 */
		public void addAttribute(String key, String value) {
			this.addAttributePrivate(new XMLAttribute(key, value));
		}

		/**
		 * Adds a attribute to the node
		 * 
		 * @param key
		 * @param value
		 */
		public void addAttribute(XMLAttribute attr) {
			this.addAttributePrivate(attr);
		}

		/**
		 * Adds a child to the node
		 * 
		 * @param child
		 */
		public void addChild(XMLNode child) {
			this.childs.add(child);
			child.setParent(this);
		}

		/**
		 * Returns a child
		 * 
		 * @param index
		 * @return
		 */
		public XMLNode getChild(int index) {
			return (XMLNode) this.childs.get(index);
		}

		/**
		 * Returns a child given by the name of the node
		 * 
		 * @param index
		 * @return
		 */
		public XMLNode getChild(String nodeName) {
			for (XMLNode node : this.getChilds()) {
				if (node.getName().equals(nodeName)) {
					return node;
				}
			}
			return null;
		}

		/**
		 * Checks whether a child node exists or not
		 * 
		 * @param nodeName
		 * @return
		 */
		public boolean nodeExists(String nodeName) {
			for (XMLNode node : this.getChilds()) {
				if (node.getName().equals(nodeName))
					return true;
			}
			return false;
		}

		/**
		 * @return The childs array size
		 */
		public int getNumChilds() {
			return this.childs.size();
		}

		/**
		 * Set's the parent for this node
		 * 
		 * @param parent
		 */
		public void setParent(XMLNode parent) {
			this.parent = parent;
		}

		/**
		 * Return the parent of this node
		 * 
		 * @return
		 */
		public XMLNode getParent() {
			return this.parent;
		}

		/**
		 * Return the name of this node
		 * 
		 * @return
		 */
		public String getName() {
			return this.name;
		}

		/**
		 * Set's the name of this node
		 * 
		 * @param name
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * Returns a formated XMLString
		 * 
		 * @return
		 */
		public String getXMLString() {
			return getXMLString(0);
		}

		private String getXMLString(int height) {
			String s = "";
			for (int j = 0; j < height; j++)
				s = s + "\t";
			s = s + "<" + this.name + getAttributeString()
					+ (getNumChilds() == 0 ? " /" : "") + ">\n";
			if (this.childs.size() != 0) {
				for (int i = 0; i < this.childs.size(); i++) {
					s = s
							+ ((XMLNode) this.childs.get(i))
									.getXMLString(height + 1);
				}
				for (int j = 0; j < height; j++)
					s = s + "\t";
				s = s + "</" + this.name + ">\n";
			}
			return s;
		}

		/**
		 * Creates an String with the attributes of this node
		 * 
		 * @return
		 */
		private String getAttributeString() {
			String s = "";
			for (int i = 0; i < this.attributes.size(); i++) {
				s = s + this.attributes.get(i).getAttributeName() + "=\""
						+ this.attributes.get(i).getAttributeValue() + "\" ";
			}
			s = s.trim();
			if (this.attributes.size() > 0)
				s = " " + s;
			return s;
		}

		/**
		 * @return the childs
		 */
		public ArrayList<XMLNode> getChilds() {
			return childs;
		}

		/**
		 * @param childs
		 *            the childs to set
		 */
		public void setChilds(ArrayList<XMLNode> childs) {
			this.childs = childs;
		}

		/**
		 * @return the attributes
		 */
		public ArrayList<XMLAttribute> getAttributes() {
			return attributes;
		}

		/**
		 * @param attributes
		 *            the attributes to set
		 */
		public void setAttributes(ArrayList<XMLAttribute> attributes) {
			this.attributes = attributes;
		}

		/**
		 * Wrapper Method to print an XMLNode
		 * 
		 * @param node
		 */
		public static void printNode(XMLNode node) {
			pNode(node, 0);
		}

		/**
		 * Prints an XMLNode with tabIndex as right shift amount
		 * 
		 * @param node
		 * @param tabIndex
		 */
		private static void pNode(XMLNode node, int tabIndex) {
			String tabs = "";
			for (int i = 0; i < tabIndex; i++) {
				tabs += "  ";
			}
			String tabs2 = tabs + "  ";
			System.out.println(tabs + "[" + node.getName() + "] {");
			for (XMLAttribute attr : node.getAttributes()) {
				System.out.println(tabs2 + attr.getAttributeName() + " - "
						+ attr.getAttributeValue());
			}
			for (int i = 0; i < node.getChilds().size(); i++) {
				pNode(node.childs.get(i), tabIndex + 1);
			}
			System.out.println(tabs + "}");
		}

	}

	public static class XMLParser {

		/**
		 * @Author PikajuTheBoss
		 */

		/**
		 * Parses a XMLString @param text
		 * 
		 * @param text
		 * @return
		 * @throws EOFException
		 */
		public static XMLNode parseText(String text) throws EOFException {
			XMLNode currentNode = new XMLNode();
			char[] c = text.toCharArray();
			for (int i = 0; i < c.length; i++) {
				if (c[i] == '/') {
					if (currentNode.getParent().getName() == null)
						return currentNode;
					currentNode = currentNode.getParent();
				}
				if ((c[i] == '<') && (c[(i + 1)] != '/')) {
					String tagText = text.substring(i + 1);
					XMLNode newNode = new XMLNode();
					currentNode.addChild(newNode);
					currentNode = newNode;
					currentNode.setName(tagText.substring(0,
							tagText.indexOf(">")).split(" ")[0]);
					int tagEnd = min(tagText.indexOf(">"), tagText.indexOf("/"));

					String attribText = tagText.substring(0, tagEnd);
					for (String currentAttribute : attribText.split(" ")) {
						if (currentAttribute.split("=").length >= 2)
							currentNode.addAttribute(currentAttribute
									.split("=")[0],
									currentAttribute.split("=")[1].replace(
											"\"", ""));
					}
					i = i + tagEnd - 1;
				}
			}

			throw new EOFException("Nodes aren't closed properly");
		}

		/**
		 * Reads a file and passes it to parseText
		 * 
		 * @param filePath
		 * @return
		 * @throws EOFException
		 */
		public static XMLNode parseFile(String filePath) throws EOFException {
			StringBuilder text = new StringBuilder();
			String line = "";
			try {
				BufferedReader reader = new BufferedReader(new FileReader(
						new File(filePath)));

				while ((line = reader.readLine()) != null)
					text.append(line);
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return parseText(text.toString());
		}

		/**
		 * Passes @param lines as single string to parseText
		 * 
		 * @param lines
		 * @return
		 * @throws EOFException
		 */
		public static XMLNode parseFileLines(ArrayList<String> lines)
				throws EOFException {
			StringBuilder text = new StringBuilder();
			for (int i = 0; i < lines.size(); i++) {
				text.append(lines.get(i));
			}
			return parseText(text.toString());
		}

		/**
		 * A minimal function for two integers
		 * 
		 * @param a
		 * @param b
		 * @return
		 */
		private static int min(int a, int b) {
			return a < b ? a : b;
		}
	}

}