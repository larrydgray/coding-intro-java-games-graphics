package net.sf.javagg.bitmapedit;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.RenderedImage;
import java.io.File;
import java.util.Iterator;

/**
 * Contols all the action.
 * 
 * @author koala_man and Larry Gray
 */
public class BitmapController extends JPanel implements ActionListener {
	
	JToggleButton underlay;
	/** UI Buttons for various functions */
	JButton clear, load, save;
	/** a default starting working directory */
	public String defaultDir = "c:/java/eclipse/workspace/gsc/gsc/game/board/images"; // FIXME
	/** the editor being controlled */
	BitmapEditor editor;
	/** UI check boxes for toggling grid and stretch options */
	JCheckBox grid, stretch;
	/** UI Option buttons for various draw tools */
	JRadioButton pencil, line, fat, flood, grab;

	/** Builds a controller for controlling given editor */
	public BitmapController(BitmapEditor be) {
		editor = be;
		grid = new JCheckBox("Grid");
		stretch = new JCheckBox("Stretch");
		pencil = new JRadioButton("Pencil");
		line = new JRadioButton("Lines");
		flood = new JRadioButton("Flood");
		grab = new JRadioButton("Grab");
		clear = new JButton("Clear");
		load = new JButton("Load");
		save = new JButton("Save");
		underlay = new JToggleButton("Underlay");
		fat = new JRadioButton("Fat");

		grid.setSelected(be.getGrid());
		stretch.setSelected(be.getStretch());

		ButtonGroup bg = new ButtonGroup();
		bg.add(pencil);
		bg.add(line);
		bg.add(fat);
		bg.add(flood);
		bg.add(grab);
		pencil.setSelected(true);

		pencil.addActionListener(this);
		line.addActionListener(this);
		flood.addActionListener(this);
		grab.addActionListener(this);
		grid.addActionListener(this);
		stretch.addActionListener(this);
		clear.addActionListener(this);
		load.addActionListener(this);
		save.addActionListener(this);
		fat.addActionListener(this);
        underlay.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent ae){
        		AbstractButton abstractButton = (AbstractButton) ae.getSource();
                boolean selected = abstractButton.getModel().isSelected();
                
        		editor.topLayer=selected;
        		editor.repaint();
        		//System.out.println("selected:"+selected);
        	}
        });
        
		setLayout(new GridLayout(2, 4));
		add(pencil);
		add(line);
		add(fat);
		add(flood);
		add(grab);
		add(grid);
		add(stretch);
		add(clear);
		add(load);
		add(save);
		add(underlay);

	} // BitmapController(BitMapEditor) constructor

	/**
	 * Here we do the work.
	 */
	public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand();
		if (s.equals("Grid"))
			editor.setGrid(grid.isSelected());
		else if (s.equals("Stretch"))
			editor.setStretch(stretch.isSelected());
		else if (s.equals("Pencil"))
			editor.setTool(BitmapEditor.PENCIL);
		else if (s.equals("Fat"))
			editor.setTool(BitmapEditor.FATPENCIL);
		else if (s.equals("Flood"))
			editor.setTool(BitmapEditor.FLOOD);
		else if (s.equals("Grab"))
			editor.setTool(BitmapEditor.GRAB);
		else if (s.equals("Lines"))
			editor.setTool(BitmapEditor.LINE);
		else if (s.equals("Clear"))
			editor.clear();
		else if (s.equals("Load"))
			loadImg();
		else if (s.equals("Save"))
			saveImg();
		else
			System.err.println("Uh oh, leftover value: " + s);

	} // actionPerformed

	/** Here we get the image from the png file */
	protected void loadImg() {
		JFileChooser fc = new JFileChooser();
		File currentDir = new File(defaultDir);
		fc.setCurrentDirectory(currentDir);

		int ret = fc.showOpenDialog(this);
		if (ret == fc.APPROVE_OPTION) {
			java.io.File f = fc.getSelectedFile();
			try {
				MediaTracker mt = new MediaTracker(this);
				Image m = Toolkit.getDefaultToolkit().getImage(
						f.getAbsolutePath());
				mt.addImage(m, 0);
				mt.waitForAll();
				editor.setImage(m);
			} catch (Exception e) {
				e.printStackTrace(System.err);
			} // catch
		} // if
	} // loadImg

	/**
	 * Saves an image to a png file.
	 * 
	 */
	protected void saveImg() {
		JFileChooser fc = new JFileChooser();
		File currentDir = new File(defaultDir);
		fc.setCurrentDirectory(currentDir);
		int ret = fc.showSaveDialog(this);
		if (ret == fc.APPROVE_OPTION) {
			File f = fc.getSelectedFile();
			String ext = f.getName();
			int n = ext.indexOf(".");
			if (n == -1) {
				ext = "png"; // default
				f = new File(f.getParentFile(), f.getName() + "." + ext);
			} else
				ext = ext.substring(ext.indexOf(".") + 1);
			Iterator it = ImageIO.getImageWritersBySuffix(ext);
			try {
				if (!it.hasNext()) {
					String s = "Can't save, unknown format \"" + ext
							+ "\".\nSupported: ";
					String[] formats = ImageIO.getWriterFormatNames();
					for (int i = 0; i < formats.length; i++)
						s += formats[i] + ", ";
					JOptionPane.showMessageDialog(null, s, "Unknown format",
							JOptionPane.ERROR_MESSAGE);

				} else {
					ImageWriter iw = (ImageWriter) it.next();
					iw.setOutput(new FileImageOutputStream(f));
					iw.write((RenderedImage) editor.getImage());
				} // else
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
						"Can't save to file: " + e.toString()
								+ ". Check stack trace.", "Error saving",
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace(System.err);
			} // catch
		}// if approved
	} // saveImg
} // BitmapController

