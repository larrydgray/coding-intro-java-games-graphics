package net.sf.javagg.mud.data.pc;

import net.sf.javagg.mud.data.universe.Container;
/**
 * PC is the entire players perspective of his game world.
 * This is where you totally control the game. The PC 
 * will move from Room to Room and From Realm to Realm.
 * PC can contain Items by packing them, wearing them
 * weilding them, holding them. PC can have skills
 * which are required to open locked exits and locked paths.
 * PC can have items for the same purpose.
 * A PC can control a larger item that it is contained 
 * within.  PC can interact with NPC and fight them.
 * 
 * Basically you might have  some levels of context such as
 * World-->Realm-->Room-->PC-->Item or
 * World-->Realm-->SubRealm-->Room-->PC--Item or
 * World-->Realm-->Room-->Item-->Room-->PC-->Item or
 * World-->Realm-->Room-->Item--SubRelam-->Room-->PC-->Item-->Item or
 * World-->Realm-->SubRealm-->Room-->Item-->SubRealm-->Room-->PC  
 * 
 *
 * 
 * 
 * 
 * @author XP
 *
 */
public class PC extends BaseCharacter {

	
}
