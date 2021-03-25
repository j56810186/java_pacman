
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Color;

public class Tile extends Rectangle {
	
	private static final long serialVersionUID = 1L;
	private String color;
	
	public Tile(int x, int y, String color) {
		setBounds(x, y, 32, 32);
		this.color = color;
	}
	
	public void render(Graphics g) {
		g.setColor(Color.decode(color));
		g.fillRect(x, y, width, height);
	}
	
	
	
	
}
