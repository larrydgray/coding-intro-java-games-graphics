package net.sf.javagg.mud.data.pc;
/**
 * In this puzzle text adventure characters are
 * not the super hero's as they are in others. They have
 * a single health attribute which is similar and covers both physical, mental both
 * injury and illness as in most RPG's. But it this game
 * survival is a primary goal. So in addition to health, other states
 * will be tracked. Breath is the measure of how much breath a character has left when 
 * holding breath. A character should be able to hold breath 1 to 3 minutes but could go longer 
 * under certain circumstances. 
 * 
 * Mental and Physical fatigue. When mental fatigue becomes too great only sleep may
 * bring it back to normal. When physical fatigue is too great a short rest stop is needed to 
 * catch breath and rest muscles. Yes in this rpg when your character is at a rest state you may do
 * very little and can't move about the game world. You may switch to an alt and play the alt. You may
 * read back logs of text for the toon at rest. You may assist in the construction of some part of the game world.
 * You may take control of one or more NPC if allowed to add a human element against a pc or group of pc's. So 
 * you see there is a lot that one can do while a toon is at rest. 
 * 
 * Hunger and Thirst should seem obvious. In this gameworld a toon may literally starve to death. 3 days with no water and dead. 30 days with no food and
 * dead. Though certain circumstances can extend this time frame. One of the skills
 * a toon will need to learn is Gathering. The game world will contain food items which may be picked and consumed.
 * 
 * Thermal Comfort. This is a factor where 0 is best. Too low in the negative and the toon either dies of hypothermia or 
 * freezes to death. Too hot and he dies of heat stroke.
 * Proper clothing and other circumstances offset environmental extremes.
 * 
 * Characters will also have attributes and a skills set.  I have not decided what attributes as of yet will
 * be needed.
 * 
 * Characters will have layers of clothing and armor for  feet, hands, head, chest, back, waist, legs. I have  not yet
 * considered if weight of items worn will be a consideration.
 * 
 * @author Larry
 *
 */
public interface Character {

	public int getHealth();
	public void setHealth();
	public void incHealth(int amount);
	public void decHealth(int amount);
	public int getBreath();
	public void setBreath();
	public void incBreath(int amount);
	public void decBreath(int amount);
	public int getMentalFatigue();
	public void setMentalFatigue(int amount);
	public void incMentalFatigue(int amount);
	public void decMentalFatigue(int amount);
	public int getPhysicalFatigue();
	public int setPhysicalFatigue(int amount);
	public int incPhysicalFatigue(int amount);
	public int decPhysicalFatigue(int amount);
	public int getHunger();
	public void setHunger(int amount);
	public void incHunger(int amount);
	public void decHunger(int amount);
	public int getThirst();
	public void setThirst(int amount);
	public void incThirst(int amount);
	public void decThirst(int amount);
	public int getThermalComfort();
	public void setThermalComfort(int amount);
	public void incThermalComfort(int amount);
	public void decThermalComfort(int amount);
}
