package jumpgame;

import java.awt.Color;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class OI extends JFrame implements ActionListener{
	JButton btnok;
	public OI(){
		super("operate instruction");
		setBounds(480,130,307,530);
		setLayout(null);
		ImageIcon IIbg=new ImageIcon(this.getClass().getResource("photo/OI.jpg"));
		JLabel JLbg=new JLabel(IIbg);
		JLbg.setBounds(0, 0, IIbg.getIconWidth(),IIbg.getIconHeight());
		JPanel imagePanel=(JPanel)this.getContentPane();
		imagePanel.setOpaque(false);//窗格设置透明
		this.getLayeredPane().add(JLbg,new Integer(Integer.MIN_VALUE));//背景图片标签
		ImageIcon IIoi=new ImageIcon(this.getClass().getResource("photo/OIi.jpg"));
		JLabel JLoi=new JLabel(IIoi);
		JLoi.setSize(270, 1000);
		JScrollPane p1=new JScrollPane();
		p1.setOpaque(false);
		p1.setBounds(10, 115, 280, 330);
		ImageIcon IIok=new ImageIcon(this.getClass().getResource("photo/btnok.png"));
		btnok=new JButton("",IIok);
		btnok.setBounds(110, 455, 70, 30);
		btnok.setContentAreaFilled(false);
		btnok.addActionListener(this);
		p1.setViewportView(JLoi);
		add(p1);add(btnok);
		setResizable(false);//设置不可改变大小
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnok){
			dispose();
		}
	}
}
