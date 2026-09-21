package net.sf.javagg.demo;

import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import sourceforge.jgg.gsc.TileMap;

public class Checkers extends JFrame {
	public class OthelloPanel extends JPanel{
		TileMap checkersBoard = null;
		public OthelloPanel(){
			checkersBoard=new TileMap("checkersBoard1.xml",this);
		}
		public void paintComponent(Graphics g){
			g.drawImage(checkersBoard.getMapImage(),0,0,null);
		}
	}
    public Checkers(){
    	this.setSize(500,500);
    	this.add(new OthelloPanel());
    	this.setVisible(true);
    }
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Checkers();

	}

}
