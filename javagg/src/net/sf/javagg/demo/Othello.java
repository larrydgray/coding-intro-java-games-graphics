package net.sf.javagg.demo;

import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import sourceforge.jgg.gsc.TileMap;

public class Othello extends JFrame {
	public class OthelloPanel extends JPanel{
		TileMap othelloBoard = null;
		public OthelloPanel(){
			othelloBoard=new TileMap("othelloBoard.xml",this);
			
			
		}
		public void paintComponent(Graphics g){
			g.drawImage(othelloBoard.getMapImage(),0,0,null);
		}
	}
    public Othello(){
    	this.setSize(500,500);
    	this.add(new OthelloPanel());
    	this.setVisible(true);
    }
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Othello();

	}

}
