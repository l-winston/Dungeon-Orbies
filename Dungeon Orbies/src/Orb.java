import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Orb {
	public static final String[] types = {"fire", "grass", "water", "light", "dark", "heart"};
	public String type;
	int i;
	int j;
	
	public Orb(String type, int i, int j){
		this.type = type;
		this.i = i;
		this.j = j;
	}
	
	public void draw(int x, int y){
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("res/" + type + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		MAIN.g2d.drawImage(img, x-MAIN.ORB_RADIUS, y-MAIN.ORB_RADIUS, x+MAIN.ORB_RADIUS, y+MAIN.ORB_RADIUS, 0, 0, 100, 100, MAIN.frame);
	}
}
