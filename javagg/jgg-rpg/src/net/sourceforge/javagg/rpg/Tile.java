
package net.sourceforge.javagg.rpg;
import java.awt.*;
import java.awt.image.*;
import java.util.*;
public class Tile {
    
    Image image;
    boolean transparent; 
    String name;
    String mapCode;
    public void setTransparent(){ 
        if (image!=null) image = this.makeColorTransparent(image,Color.white);
        else System.out.println("null pointer for image in Tile!");
        transparent = true;
    }
    public void setImage(Image i){
        image=i;
    }
    public void setName(String n){
        name=n;
    }
    public void setMapCode(String mc){
        mapCode=mc;
    }
    public String getMapCode(){ 
        return mapCode; 
    }
    public String getName(){ 
        return name; 
    }
    public Image getImage(){ 
        return image; 
    }
    
    public static Image makeColorTransparent (Image im, final Color color){
        ImageFilter filter = new RGBImageFilter() {
	    public int markerRGB = color.getRGB() | 0xff000000;
	    public final int filterRGB(int x, int y, int rgb) {
	        if ((rgb | 0xff000000 ) == markerRGB ) {
	            return 0x00FFFFFF & rgb;
	        }
	        else {
	            return rgb;
	        }
	    }
	};
	ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
	return Toolkit.getDefaultToolkit().createImage(ip);
    }
   
}
