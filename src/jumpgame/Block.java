package jumpgame;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Block extends JPanel{//µ≤∞Â¿‡
	int x;
	int y;
	public Block(){
	}
	public Block(int h){
		x=(int)(240*Math.random());
		y=h;
		ImageIcon IIbl=new ImageIcon(this.getClass().getResource("photo/block.png"));
		JLabel JLbl=new JLabel(IIbl);
		JLbl.setBounds(0, 0, 60, 20);
		setLayout(null);
		setOpaque(false);
		setBounds(x, y, 60, 20);
		add(JLbl);
	}
	public void newPosition(int h){
		x=(int)(240*Math.random());
		y=h;
	}
}
