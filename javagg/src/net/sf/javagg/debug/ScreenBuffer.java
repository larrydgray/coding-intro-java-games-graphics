package net.sf.javagg.debug;



import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class ScreenBuffer {
    private int rows;
    private int cols;
	private ArrayList<CharacterCell[]> buffer = new ArrayList<>();
	public Iterator iterator(){
		return buffer.iterator();
	}
	public int size(){
		return buffer.size();
	}
	public void addLine(CharacterCell[] line) throws RuntimeException{
		if(line.length!=cols)throw new RuntimeException("Line length does not equal number of columns of this screen buffer.");
		buffer.add(line);
	}
	private ScreenBufferViewer screenBufferViewer;
	public void initBufferViewer(){
		screenBufferViewer=new ScreenBufferViewer(this);
	}
	
	public CharacterCell[][] getScreen(int viewPos){
		
		if(viewPos<0)viewPos=0;
		if(viewPos>buffer.size()-rows)viewPos=buffer.size()-rows;
		if(buffer.size()<rows)viewPos=0;
		
		CharacterCell[][] screenView=new CharacterCell[rows][];
		for(int i=0;i<rows;i++){
			screenView[i]=this.getBlankLine();
		}
		
		if(buffer.size()<rows){
			for(int i=0;i<buffer.size()-1;i++){
				screenView[i]=buffer.get(i);
			}
		}
		if(buffer.size()>=rows){
			int a=0;
			for(int i=viewPos;i<=viewPos+rows-1;i++){
				screenView[a]=buffer.get(i);
				a++;
			}
		}
		return screenView;
	}
	private CharacterCell[] getBlankLine(){
		CharacterCell[] blankLine= new CharacterCell[cols];
		Arrays.fill(blankLine, new CharacterCell(' ',Color.BLACK,Color.WHITE));
		return blankLine;
	}
	/*
	public void setLastRow(CharacterCell[] row){
		this.buffer.remove(this.buffer.size());
		this.buffer.add(row);
	}*/
	public CharacterCell[] getLastRow() throws RuntimeException{
		System.out.println("buffer size:"+this.buffer.size());
		if(this.buffer.get(this.buffer.size()-1)==null) throw new RuntimeException("Buffer's last row is null! How can that be?");
		return this.buffer.get(this.buffer.size()-1);
	}
	
	
	CharacterCell[] blankLine;
	/**
	 * 
	 */
	
	
	public ScreenBuffer(int cols, int rows){
		this.cols=cols;
		this.rows=rows;
		blankLine= new CharacterCell[cols];
	    Arrays.fill(blankLine, new CharacterCell(' ',Color.BLACK,Color.WHITE));
		this.buffer.add(blankLine);
		
	}

}
