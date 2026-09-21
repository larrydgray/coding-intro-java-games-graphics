package net.sf.javagg.mud.data.universe;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class BasicContainer implements Container {
	
	private ArrayList<Exit> exits = new ArrayList<>();

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setID(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDescription(String description) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addExit(Exit exit) {
		// TODO Auto-generated method stub

	}

	@Override
	public Iterator<Exit> getExits() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getWeather() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setWeather(String weather) {
		// TODO Auto-generated method stub

	}

}
