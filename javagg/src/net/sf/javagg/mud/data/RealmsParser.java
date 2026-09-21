package net.sf.javagg.mud.data;

import net.sourceforge.javagg.mud.data.universe.Realm;

import org.jdom2.Element;

public class RealmsParser extends ParserXml {

	public RealmsParser(String fileName) {
		super(fileName);
		Element realmsElement = (Element)doc.getContent(0);
		Element realmElement = (Element)realmsElement.getChild("realm");
		Element descript = (Element)realmElement.getChild("descript");
		String name=realmElement.getAttributeValue("name");
		String id=realmElement.getAttributeValue("id");
		String world = realmElement.getAttributeValue("world");
		String description=descript.getTextTrim();
		realm.setName(name);
		realm.setID(id);
		realm.setDescription(description);
		realm.setWorld(world);
	}
	private Realm realm = new Realm();

	public Realm getRealm() {
		return realm;
	}
	public static void main(String args[]){
		RealmsParser parser = new RealmsParser(dataPath+"realms.xml");
		
	}

}
