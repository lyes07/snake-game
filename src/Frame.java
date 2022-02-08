import javax.swing.*;

public class Frame extends JFrame{
	private static final long serialVersionUID = 1L;

	public Frame() {
		this.add(new Panel());
		this.setTitle("Snake Game");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
	
}