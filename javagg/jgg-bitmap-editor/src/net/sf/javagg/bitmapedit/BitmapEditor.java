package net.sf.javagg.bitmapedit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

/**
 * A simple Tile Editor for making game tiles and game icons, very much like a
 * simple paint program.
 * 
 * @author koala_man Wrote the primary source, and added flood fill.
 * @author Larry Gray added some feature enhancments, wide pencil, and option
 *         for setting default working location, and image size.
 * @version 1.1
 */
public class BitmapEditor extends JComponent implements MouseListener,
		MouseMotionListener, KeyListener {
	
	String underlayImageFileName="jupiter.jpg";
	
	/** Initial horizontal size of bit map. */
	public static final int TILEX = 100;

	/** Initial vertical size of bit map. */
	public static final int TILEY = 100;
	
	/** types of draw modes */
	final static int PENCIL = 53, LINE = 0x85, FATPENCIL = 25, FLOOD = 42, GRAB=1; // no

	// apparent
	// reason
	// for
	// this
	// numbering
	// scheme.
	

	public static BitmapController controller;

	/** Makes a new editor component of given size. */
	public JPanel createEditor(int xResolution, int yResolution) {
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());

		controller = new BitmapController(this);
		palette = new BitmapPalette(this);
		p.add(this, "Center");
		p.add(controller, "South");
		p.add(palette, "West");
		return p;
	}
    public BitmapPalette palette = null;
	public JFrame jf;

	/**
	 * Launch the editor as an application with given size as command line
	 * arguments
	 */
	public static void main(String[] args) {
		int x = TILEX, y = TILEY;
		if (args.length > 0)
			x = Integer.parseInt(args[0]);
		if (args.length > 1)
			y = Integer.parseInt(args[1]);
		new BitmapEditor(x, y);
	}

	public static JPanel editorPanel;

	/** A color */
	Color color;

	/** An Image */
	BufferedImage image;
	
	/** A bottom Image for drawing reference only */
	BufferedImage bottomImage;
	
	public Color grabUnderlayColor(int x, int y){
		int clr=  bottomImage.getRGB(x,y); 
		  int  red   = (clr & 0x00ff0000) >> 16;
		  int  green = (clr & 0x0000ff00) >> 8;
		  int  blue  =  clr & 0x000000ff;
		  Color aColor=new Color(red,green,blue);
		  return aColor;
		  
	}
	public Color grabColor(int x, int y){
		int clr=  image.getRGB(x,y); 
		  int  red   = (clr & 0x00ff0000) >> 16;
		  int  green = (clr & 0x0000ff00) >> 8;
		  int  blue  =  clr & 0x000000ff;
		  Color aColor=new Color(red,green,blue);
		  return aColor;
	}


	/** A Graphics object */
	Graphics offg;

	/** Display zoom size */
	public final int SIZE = 2;

	/** defaults for stretch mode and grid mode */
	boolean stretch = true, grid = false;

	/** default tool */
	int tool = PENCIL;

	/** size of the image */
	int width, height;

	/** not sure what this is for */
	int x1, y1, x2, y2;

	private BufferedImage convertImageIcon(ImageIcon anImageIcon){
		Image source = anImageIcon.getImage();
		int w = source.getWidth(null);
		int h = source.getHeight(null);
		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D)image.getGraphics();
		g2d.drawImage(source, 0, 0, null);
		g2d.dispose();
		return image;
	}
	/**
	 * Create a new BitmapEditor with the specified width and height.
	 */
	public BitmapEditor(int width, int height) {
		super();
		String javaggRoot = System.getProperty("javagg.root", ".");
		ImageIcon anImage = new ImageIcon(javaggRoot + "/images/underlay/" + underlayImageFileName);
		
		bottomImage = convertImageIcon(anImage);
		this.width = width;
		this.height = height;

		color = Color.black;
		makeNew(width, height);

		clear();

		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		jf = new JFrame("Bitmap Editor!");
		JMenu fileMenu = new JMenu("File");
		JMenuBar bitmapEditorMenuBar = new JMenuBar();
		JMenu optionsMenu = new JMenu("Options");
		JMenuItem workspace = new JMenuItem("Workspace...");
		JMenuItem imageSize = new JMenuItem("Image Size...");
		bitmapEditorMenuBar.add(fileMenu);
		bitmapEditorMenuBar.add(optionsMenu);
		fileMenu.add(workspace);
		optionsMenu.add(imageSize);
		jf.setJMenuBar(bitmapEditorMenuBar);
		workspace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {

				controller.defaultDir = JOptionPane.showInputDialog(null,
						"Working directory?");
			} // actionPerformed
		});// ActionListener, addActionListener
		imageSize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String sx = JOptionPane.showInputDialog(null,
						"Horizontal Size?");
				String sy = JOptionPane.showInputDialog(null, "Vertical Size?");
				int x = 0;
				int y = 0;
				try {
					x = Integer.parseInt(sx);
					y = Integer.parseInt(sy);
				} catch (NumberFormatException nfe) {
					nfe.printStackTrace();
				} // catch
				editorPanel = createEditor(x, y);
				jf.getContentPane().removeAll();
				jf.getContentPane().add(editorPanel);
				jf.getContentPane().validate();

				jf.getContentPane().repaint();
				makeNew(x, y);

			} // actionPerformed
		}); // ActionListener, addActionListener
		jf.setSize(300, 300);
		editorPanel = createEditor(width, height);
		jf.getContentPane().add(editorPanel);
		jf.setVisible(true);
	}

	/**
	 * Clear component.
	 */
	public void clear() {
		offg.setColor(Color.white);
		offg.fillRect(0, 0, width, height);
		offg.setColor(color);
		repaint();
	}

	// FloodFill from the given point with the selected color.
	// There is no fuzzyness in color matching, pixels sqrt(2) away
	// are not concidered adjacent.
	/**
	 * Flood Fill starting at a given point using current color.
	 * 
	 * @param Point
	 *            a starting point.
	 */
	public void floodFill(Point start) {
		try {
			int match = image.getRGB(start.x, start.y), paint = offg.getColor()
					.getRGB();
			if (match == paint)
				return;
			recurseFlood(start.x, start.y, match, paint);
		} catch (Error e) { // catch memory errors and other stuff
			e.printStackTrace(System.err);
		}
		repaint();
	}

	/**
	 * Gets the current pen color.
	 * 
	 * @return Color pen color.
	 * 
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Is the grid on?
	 * 
	 * @return boolean grid on/off.
	 */
	public boolean getGrid() {
		return grid;
	}

	/**
	 * Gets the Image that we are drawing on.
	 * 
	 * @return Image the drawing image.
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * Gets the size of this image for the layout.
	 */
	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}

	/**
	 * Are we in stretch mode?
	 * 
	 * @return boolean stretch on/off.
	 */
	public boolean getStretch() {
		return stretch;
	}

	/**
	 * Currently selected drawing tool.
	 * 
	 * @return int a tool ID.
	 */
	public int getTool() {
		return tool;
	}

	/**
	 * Handles keyPresses.
	 */
	public void keyPressed(KeyEvent k) {
	}

	/**
	 * Handles keyReleases.
	 */
	public void keyReleased(KeyEvent k) {
	}

	/** Handles keysTyped */
	public void keyTyped(KeyEvent k) {
		switch (k.getKeyCode()) {
		case KeyEvent.VK_P:
			setTool(PENCIL);
			break;
		case KeyEvent.VK_SPACE:
			repaint();
			break;
		}
	} // keyTyped

	/**
	 * Clears the image by making a new one.
	 * 
	 * @param x
	 *            resolution.
	 * @param y
	 *            resolution.
	 */
	public void makeNew(int x, int y) {
		width = x;
		height = y;
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		offg = image.getGraphics();
		x1 = 0;
		y1 = 0;
		x2 = 0;
		y2 = 0;
		clear();
	}

	/**
	 * Not used.
	 * 
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(MouseEvent e) {
	}

	/**
	 * This is where we do the drawing bit.
	 */
	public void mouseDragged(MouseEvent e) {
		switch (tool) {
		case FATPENCIL:
			Point p1 = translate(e.getX(), e.getY());
			offg.fillRect(p1.x - SIZE, p1.y - SIZE, SIZE * 2 + 1, SIZE * 2 + 1);
			x1 = p1.x;
			y1 = p1.y;
			repaint();
			break;
		case PENCIL:
			Point p2 = translate(e.getX(), e.getY());
			offg.drawLine(x1, y1, p2.x, p2.y);
			x1 = p2.x;
			y1 = p2.y;
			repaint();
			break;
		case LINE:
			x2 = e.getX();
			y2 = e.getY();
			repaint();
			getGraphics().drawLine(x1, y1, x2, y2);
			break;
		case GRAB:
			Point p3 = translate(e.getX(), e.getY());
			Color grabbedColor=null;
			if(this.topLayer)
			   grabbedColor = this.grabColor(p3.x, p3.y);
			else 
			   grabbedColor = this.grabUnderlayColor(p3.x, p3.y);
			palette.setSelectedColor(grabbedColor);
			break;
		}
	}

	/**
	 * Not used.
	 */
	public void mouseEntered(MouseEvent e) {
	}

	/**
	 * Not used.
	 */
	public void mouseExited(MouseEvent e) {
	}

	/**
	 * Not used.
	 */
	public void mouseMoved(MouseEvent e) {
	}

	/**
	 * We do some pixel plotting here.
	 */
	public void mousePressed(MouseEvent e) {
		switch (tool) {
		case PENCIL:
			Point p = translate(e.getX(), e.getY());
			x1 = p.x;
			y1 = p.y;
			break;
		case LINE:
			x1 = e.getX();
			y1 = e.getY();
			break;
		case FLOOD:
			floodFill(translate(e.getX(), e.getY()));
			break;
		}
		mouseDragged(e);
	}

	/**
	 * We do some drawing here.
	 */
	public void mouseReleased(MouseEvent e) {
		switch (tool) {
		case LINE:
			Point a = translate(x1, y1),
			b = translate(x2, y2);
			offg.drawLine(a.x, a.y, b.x, b.y);
			repaint();
			break;
		}
	}
    /** */
	public boolean topLayer=true;
	
	/**
	 * Here we paint the image stretched or with grid or both.
	 */
	public void paintComponent(Graphics g) {
		if (stretch) {
			
			if(topLayer)g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
			else g.drawImage(bottomImage, 0, 0, getWidth(), getHeight(), null);
			if (grid) {
				g.setColor(Color.black);
				double k = (double) getWidth() / width;
				for (int i = 0; i < width; i++)
					g.drawLine((int) (i * k), 0, (int) (i * k), getHeight());
				k = (double) getHeight() / height;
				for (int i = 0; i < height; i++)
					g.drawLine(0, (int) (i * k), getWidth(), (int) (i * k));
			}
		} else
			g.drawImage(image, 0, 0, width, height, null);

	}

	/**
	 * This may be used by the floodFill() method.
	 * 
	 * @param x
	 * @param y
	 * @param match
	 * @param paint
	 */
	private void recurseFlood(int x, int y, int match, int paint) {
		int lb, rb;
		image.setRGB(x, y, paint);
		lb = x - 1;
		rb = x + 1;
		while (lb >= 0 && image.getRGB(lb, y) == match) {
			image.setRGB(lb, y, paint);
			lb--;
		}
		while (rb < image.getWidth() && image.getRGB(rb, y) == match) {
			image.setRGB(rb, y, paint);
			rb++;
		}
		for (int i = lb + 1; i < rb; i++) {
			if (y > 0 && image.getRGB(i, y - 1) == match)
				recurseFlood(i, y - 1, match, paint);
			if (y < image.getHeight() - 1 && image.getRGB(i, y + 1) == match)
				recurseFlood(i, y + 1, match, paint);
		}
	}

	/**
	 * Set the color for drawing.
	 */
	public void setColor(Color color) {
		this.color = color;
		offg.setColor(color);
		getGraphics().setColor(color);
	}

	/**
	 * Set if a grid is to be displayed.
	 */
	public void setGrid(boolean b) {
		grid = b;
		repaint();
	}

	/** Loads an image */
	public void setImage(Image m) {
		makeNew(m.getWidth(null), m.getHeight(null));
		offg.drawImage(m, 0, 0, null);
		repaint();
	}

	/**
	 * Set if the component can stretch the image to fit.
	 */
	public void setStretch(boolean b) {
		stretch = b;
		repaint();
	}

	/**
	 * Set the tool used for drawing. PENCIL or LINE.
	 */
	public void setTool(int tool) {
		this.tool = tool;
	}

	/**
	 * Makes a point based on stretch or no stretch.
	 * 
	 * @param x
	 * @param y
	 * @return Point stretched or not.
	 */
	protected Point translate(int x, int y) {
		if (stretch)
			return new Point(x * width / getWidth(), y * height / getHeight());
		else
			return new Point(x, y);
	}

	/**
	 * updates the UI
	 */
	public void update(Graphics g) {
		paint(g);
	}
} // BitmapEditor

