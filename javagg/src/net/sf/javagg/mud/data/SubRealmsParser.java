package net.sf.javagg.mud.data;

import net.sourceforge.javagg.mud.data.universe.SubRealm;

import org.jdom2.Element;

public class SubRealmsParser extends ParserXml {

	public SubRealmsParser(String fileName) {
		super(fileName);
		Element subrealmsElement = (Element)doc.getContent(0);
		Element subrealmElement = (Element)subrealmsElement.getChild("realm");
		Element descript = (Element)subrealmElement.getChild("descript");
		String realm=subrealmElement.getAttributeValue("realm");
		String name=subrealmElement.getAttributeValue("name");
		String id=subrealmElement.getAttributeValue("id");
		String description=descript.getTextTrim();
		subrealm.setName(name);
		subrealm.setID(id);
		subrealm.setDescription(description);
		subrealm.setRealm(realm);
		
	}
	
	private SubRealm subrealm = new SubRealm();

	public SubRealm getSubRealm() {
		return subrealm;
	}
	public static void main(String args[]){
		SubRealmsParser parser = new SubRealmsParser(dataPath+"subrealms.xml");
		
	}
	

}
