
import java.awt.Graphics;
import java.awt.Rectangle;

public class Player extends Rectangle {

	private static final long serialVersionUID = 1L;

	public boolean right, left, up, down;
	private int speed = 4;

	private boolean rightCanMove, leftCanMove, upCanMove, downCanMove;// to record the boolean of last call of one
																		// direct canMove()
	/*
	 * supplement: default value of boolean is false
	 */

	private boolean hasTurned = true;// if hasTurned, then don't need to turn at next corner.
	private int turnCount;/////// for check player turn how many times.

	private int time = 0, targetTime = 10;
	public int imageIndexToken = 1;

	private int lastXOrY = 0;
	private int lastDir = 1;

	public Player(int x, int y) {
		setBounds(x, y, 32, 32);
	}

	public void tick() {
		if (right) {
			hasTurned = rightCanMove != canMove(x + speed, y) ? true : false;
			System.out.println(hasTurned + " " + canMove(x + speed, y));
			rightCanMove = canMove(x + speed, y);
			if (rightCanMove) {
				x += speed;
				lastXOrY = 0;
				lastDir = 1;
			}
		} else {
			System.out.println("right:" + right);
		}
		if (left) {
			hasTurned = leftCanMove != canMove(x - speed, y) ? true : false;
			leftCanMove = canMove(x - speed, y);
			if (leftCanMove) {
				x -= speed;
				lastXOrY = 0;
				lastDir = -1;
			}
		}
		if (up) {
			hasTurned = upCanMove != canMove(x, y - speed) ? true : false;
			upCanMove = canMove(x, y - speed);
			if (upCanMove) {
				y -= speed;
				lastXOrY = 1;
				lastDir = 1;
			}
		}
		if (down) {
			hasTurned = downCanMove != canMove(x, y + speed) ? true : false;
			downCanMove = canMove(x, y + speed);
			if (downCanMove) {
				y += speed;
				lastXOrY = 1;
				lastDir = -1;
			}
		}

		// these are used to resolved turning problem.
		if (hasTurned) {
			if (right && !rightCanMove) {
				right = false;
				turnCount++;
				System.out.println("turn right");
			}
			if (left && !leftCanMove) {
				left = false;
				turnCount++;
				System.out.println("turn left");
			}
			if (up && !upCanMove) {
				up = false;
				turnCount++;
				System.out.println("turn up");
			}
			if (down && !downCanMove) {
				down = false;
				turnCount++;
				System.out.println("turn down");
			}
			hasTurned = false;
			//printAll();
		}

		Level level = Game.level;
		for (int i = 0; i < level.apples.size(); i++) {
			if (this.intersects(level.apples.get(i))) {
				level.apples.remove(i);
				Timer.score++;
				break;
			}
		}

		for (int i = 0; i < Game.level.enemies.size(); i++) {
			Enemy en = Game.level.enemies.get(i);
			if (en.intersects(this)) {
				// Menu system
				Game.STATE = Game.DEFEAT;
				return;
			}
		}

		if (level.apples.size() == 0)

		{
			// Game over and the player win.
			Game.STATE = Game.VICTORY;
			return;
		}

		time++;
		if (lastXOrY == 0) {
			if (time == targetTime) {
				time = 0;
				imageIndexToken++;
			}
		} else if (lastXOrY == 1) {
			if (time == targetTime) {
				time = 0;
				imageIndexToken++;
			}
		}
	}

	private boolean canMove(int nextX, int nextY) {
		Rectangle bounds = new Rectangle(nextX, nextY, width, height);
		Level level = Game.level;

		for (int xx = 0; xx < level.tiles.length; xx++) {
			for (int yy = 0; yy < level.tiles[0].length; yy++) {
				if (level.tiles[xx][yy] != null) {
					if (bounds.intersects(level.tiles[xx][yy])) {
						return false;
					}
				}
				if (level.seed[xx][yy] != null) {
					if (bounds.intersects(level.seed[xx][yy])) {
						return false;
					}
				}
			}
		}
		return true;
	}

	public void render(Graphics g) {
		if (lastXOrY == 0) {
			if (lastDir == 1)
				g.drawImage(Texture.player[0][lastXOrY][imageIndexToken % 2], x, y, width, height, null);
			else if (lastDir == -1)
				g.drawImage(Texture.player[0][lastXOrY][imageIndexToken % 2], x + width, y, -width, height, null);
		} else if (lastXOrY == 1) {
			if (lastDir == 1)
				g.drawImage(Texture.player[0][lastXOrY][imageIndexToken % 2], x, y, width, height, null);
			else if (lastDir == -1)
				g.drawImage(Texture.player[0][lastXOrY][imageIndexToken % 2], x, y + height, width, -height, null);
		}
	}

	private void printAll() {
		System.out.println(turnCount);
		System.out.println("right: " + right);
		System.out.println("left: " + left);
		System.out.println("up: " + up);
		System.out.println("down: " + down);
		System.out.println("----------");
	}
}