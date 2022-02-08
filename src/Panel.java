import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.Random;

public class Panel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	static final int WIDTH=600, HEIGHT=600,  UNIT_SIZE=30, 
					 DELAY=150;
	static final int GAME_UNITS=(WIDTH*HEIGHT)/UNIT_SIZE;
	final int[] x= new int[GAME_UNITS];
	final int[]	y= new int[GAME_UNITS];
	int bodyParts=6;
	int applesEaten=0, appleX, appleY;
	char direction	= 'R';
	boolean running=false;
	Timer timer;
	Random random;
	void init() {
		bodyParts=6;
		applesEaten=0;
		direction	= 'R';
		running=false;
	}
	public Panel() {
		init();
		random = new Random();
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new Key());
		startG();
	}
	public void startG() {
		newApple();
		running = true;
		timer = new Timer(DELAY, this);
		timer.start();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	public void draw(Graphics g) {
		if(running) {
			g.setColor(new Color(0x00ffff));
			g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
			for(int i=0; i<bodyParts;i++) {
				if(i==0) {
					g.setColor(Color.blue);
					g.fillOval(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
				else {
					g.setColor(new Color(0x191970));
					g.fillOval(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
			}
			g.setColor(Color.blue);
			g.setFont(new Font("MV Boli", Font.BOLD, 30));
			FontMetrics mat = getFontMetrics(g.getFont());
			g.drawString(""+applesEaten, (WIDTH - mat.stringWidth(""+applesEaten))/2, g.getFont().getSize());
		}
		else { 
			gameOver(g);
		}
	}
	public void newApple() {
		appleX = random.nextInt((int)WIDTH/UNIT_SIZE)*UNIT_SIZE;
		appleY = random.nextInt((int)HEIGHT/UNIT_SIZE)*UNIT_SIZE;
		
	}
	public void move() {
		for(int i=bodyParts; i>0;i--) {
			x[i]= x[i-1];
			y[i]= y[i-1];
		}
		switch(direction) {
			case 'U':y[0]= y[0] - UNIT_SIZE; break;
			case 'R':x[0]= x[0] + UNIT_SIZE; break;
			case 'L':x[0]= x[0] - UNIT_SIZE; break;
			case 'D':y[0]= y[0] + UNIT_SIZE; break;
		}
	}
	public void checkApple() {
		if((x[0]==appleX)&&(y[0]==appleY)){
			bodyParts++;
			applesEaten++;
			newApple();
		}
	}
	public void checkCollisions() {
		for(int i = bodyParts; i >0 ; i--) {
			if(((x[0]==x[i]) && (y[0]==y[i]))||(x[0]<0) || (x[0]>WIDTH) ||(y[0]<0) || (y[0]>HEIGHT) ) {
				running = false;
			}
			if(!running) {
				timer.stop();
			}
		}
	}
	public int gameOver(Graphics g) {
		g.setColor(Color.BLUE);
		g.setFont(new Font("MV Boli", Font.PLAIN, 100));
		FontMetrics mat = getFontMetrics(g.getFont());
		g.drawString("Game Over", (WIDTH - mat.stringWidth("Game Over"))/2, HEIGHT/2);
		g.setColor(Color.BLUE);
		g.setFont(new Font("MV Boli", Font.PLAIN, 40));
		mat = getFontMetrics(g.getFont());
		g.drawString("Your Score is : "+applesEaten, (WIDTH - mat.stringWidth("Your Score is : "+applesEaten))/2, g.getFont().getSize()+HEIGHT/2);
		g.setColor(Color.gray);
		g.setFont(new Font("MV Boli", Font.PLAIN, 40));
		mat = getFontMetrics(g.getFont());
		g.drawString("-Lyes grine-", (WIDTH - mat.stringWidth("-Lyes grine-"))/2, g.getFont().getSize());
		
		return 0;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(running) {
			move();
			checkApple();
			checkCollisions();
		}
		repaint();
	}
	
	public class Key extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction != 'R') {
					direction = 'L';
				}break;
			case KeyEvent.VK_RIGHT:
				if(direction != 'L') {
					direction = 'R';
				}break;
			case KeyEvent.VK_UP:
				if(direction != 'D') {
					direction = 'U';
				}break;
			case KeyEvent.VK_DOWN:
				if(direction != 'U') {
					direction = 'D';
				}break;
			}
		}
	}
}

