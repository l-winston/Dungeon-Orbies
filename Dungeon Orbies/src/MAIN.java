import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MAIN {
	public static final int X = 500; // window width
	public static final int Y = 750; // window height
	public static final int ORBS_ACROSS = 6;
	public static final int ORBS_HIGH = 5;
	public static final int ORB_BORDER = 20;
	public static final int ORB_RADIUS = (X - ORB_BORDER * (ORBS_ACROSS + 1)) / 12;

	public static final int ORB_BOX_UPPER_EDGE = Y - (ORBS_HIGH + 1) * ORB_BORDER - 2 * ORB_RADIUS * (ORBS_HIGH);

	public static JFrame frame = new JFrame("Dungeon Orbies");
	public static BufferedImage image = new BufferedImage(X, Y, BufferedImage.TYPE_INT_RGB);
	public static Graphics2D g2d;

	public static Orb[][] board = new Orb[ORBS_HIGH][ORBS_ACROSS];
	public static Orb dragging = null;
	public static Point lastOrb = null;
	public static Point lastMousePos = null;

	public static boolean isRunning = true;

	static Random rand = new Random();
	static Timer clicker;

	public static void main(String[] args) {
		MAIN m = new MAIN();
		m.run();
	}

	private void randomizeBoard() {
		for (int i = 0; i < ORBS_HIGH; i++) {
			for (int j = 0; j < ORBS_ACROSS; j++) {
				board[i][j] = new Orb(Orb.types[rand.nextInt(Orb.types.length)], i, j);
			}
		}
		frame.repaint();
	}

	private void drawBoard() {
		g2d.setColor(Color.black);
		g2d.fillRect(0, ORB_BOX_UPPER_EDGE, X, Y);
		for (int i = 0; i < ORBS_HIGH; i++) {
			for (int j = 0; j < ORBS_ACROSS; j++) {
				if (board[i][j] == dragging)
					continue;
				board[i][j].draw(ORB_BORDER + ORB_RADIUS + j * (2 * ORB_RADIUS + ORB_BORDER),
						ORB_BOX_UPPER_EDGE + ORB_BORDER + ORB_RADIUS + i * (2 * ORB_RADIUS + ORB_BORDER));
			}
		}
		frame.repaint();
	}

	private void run() {
		randomizeBoard();
		drawBoard();
	}

	public MAIN() {
		initFrame();
	}

	public void initFrame() {
		frame.setVisible(true);
		frame.setIgnoreRepaint(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new JLabel(new ImageIcon(image)));
		frame.pack();
		frame.setLocationRelativeTo(null);

		// Objects needed for rendering...
		g2d = image.createGraphics();

		frame.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				clicker = new Timer();
				clicker.schedule(new TimerTask() {

					@Override
					public void run() {
						Point currentMousePos = frame.getMousePosition();
						if (currentMousePos == null)
							return;
						if (currentMousePos.equals(lastMousePos))
							return;
						int orbx = (currentMousePos.x - ORB_BORDER / 2) / (2 * ORB_RADIUS + ORB_BORDER);
						int orby = (currentMousePos.y - ORB_BOX_UPPER_EDGE - ORB_BORDER / 2)
								/ (2 * ORB_RADIUS + ORB_BORDER);
						if (dragging == null) {
							if (orbx >= 0 && orbx < ORBS_ACROSS && orby >= 0 && orby < ORBS_HIGH) {
								dragging = board[orby][orbx];
								lastOrb = new Point(orbx, orby);
							}

						} else {
							if (orbx >= 0 && orbx < ORBS_ACROSS && orby >= 0 && orby < ORBS_HIGH) {
								if (orby != lastOrb.y || orbx != lastOrb.x) {
									Orb temp = board[orby][orbx];
									board[orby][orbx] = board[lastOrb.y][lastOrb.x];
									board[lastOrb.y][lastOrb.x] = temp;
									temp = null;
									lastOrb = new Point(orbx, orby);
								}
							}
							drawBoard();

							dragging.draw(currentMousePos.x, currentMousePos.y > ORB_BOX_UPPER_EDGE + ORB_RADIUS
									? currentMousePos.y : ORB_BOX_UPPER_EDGE + ORB_RADIUS);

							frame.repaint();
						}
						// board[orby][orbx].color =
						// Orb.types[rand.nextInt(Orb.types.length)];
						// drawBoard();

						System.out.println(orbx + ", " + orby);
						lastMousePos = currentMousePos;
					}
				}, 0, 50);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				clicker.cancel();
				clicker = new Timer();
				dragging = null;
				lastOrb = null;
				lastMousePos = null;
				drawBoard();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

		});
	}
}
