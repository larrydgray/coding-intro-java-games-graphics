package net.sf.javagg.mud.data;

import java.io.File;

import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 *  Open an XML file and parse the content
 *  
 */
/**
 * 
 * @author Larry Gray(caverdude)
 *
 */
public class ParserXml {
	public static String dataPath ="c:/java/workspace/jgg-mud/data/";
	
	/** */
	protected org.jdom2.Document doc;

	/**
	 * 
	 * @return
	 */
	public org.jdom2.Document getXmlDocument() {
		return doc;
	}

	/**
	 * 
	 * @param fileName
	 */
	public ParserXml(String fileName) {
		

		try {
			
			// SAXBuilder b = new SAXBuilder(false); // true -> validate
			SAXBuilder b = new SAXBuilder();
			
			// Create a JDOM document.
			doc = b.build(new File(fileName));
			if (doc == null)
				throw new NullPointerException(
						"doc null! SAXBuilder did not build docment from file!");
			
		} catch (JDOMException jex) {
			System.out.print("PARSE ERROR: " + jex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		

	} // constructor ParserXML(file)

	/**
	 * 
	 * @param args
	 * 
	 */

	public static void main(String args[]) {
		new ParserXml("c:/java/workspace/jgg-mud/data/worlds.xml");

	} // main boot strap testing

	
} // class ParserXml
