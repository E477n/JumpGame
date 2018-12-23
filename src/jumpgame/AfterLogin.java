package jumpgame;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AfterLogin extends JFrame implements ActionListener{
	JButton btnstart;
	JButton btnio;
	JButton btnrec;
	JButton btnran;
	JButton btnadv;
	JButton btnqui;
	String u;
	public AfterLogin(){
	}
	public AfterLogin(String username){
		super("Jump Game");
		setBounds(450,100,305,530);
		ImageIcon IIbg=new ImageIcon(this.getClass().getResource("photo/afterlogin.jpg"));
		JLabel JLbg=new JLabel(IIbg);
		JLbg.setBounds(0, 0, IIbg.getIconWidth(),IIbg.getIconHeight());
		JPanel imagePanel=(JPanel)this.getContentPane();
		imagePanel.setOpaque(false);//窗格设置透明
		this.getLayeredPane().add(JLbg,new Integer(Integer.MIN_VALUE));//背景图片标签
		ImageIcon IIstart=new ImageIcon(this.getClass().getResource("photo/btnstart.png"));
		u=username;
		JLabel JLwelcome=new JLabel("Welcome,"+u);
		JLwelcome.setForeground(Color.white);
		JLwelcome.setBounds(110,120,120,20);
		btnstart=new JButton("",IIstart);
		btnstart.setBounds(110, 170, 80, 35);
		btnstart.setContentAreaFilled(false);//使按钮背景透明
		btnstart.addActionListener(this);//btnstart
		ImageIcon IIio=new ImageIcon(this.getClass().getResource("photo/btnOI.png"));
		btnio=new JButton("",IIio);
		btnio.setBounds(110, 220, 80, 35);
		btnio.setContentAreaFilled(false);//btnio
		btnio.addActionListener(this);
		ImageIcon IIrec=new ImageIcon(this.getClass().getResource("photo/btnrecord.png"));
		btnrec=new JButton("",IIrec);
		btnrec.setBounds(110, 270, 80, 35);
		btnrec.setContentAreaFilled(false);//btnrec
		btnrec.addActionListener(this);
		ImageIcon IIran=new ImageIcon(this.getClass().getResource("photo/btnranking.png"));
		btnran=new JButton("",IIran);
		btnran.setBounds(110, 320, 80, 35);
		btnran.setContentAreaFilled(false);
		btnran.addActionListener(this);//btnranking
		ImageIcon IIadv=new ImageIcon(this.getClass().getResource("photo/btnadvice.png"));
		btnadv=new JButton("",IIadv);
		btnadv.setBounds(110, 370, 80, 35);
		btnadv.setContentAreaFilled(false);//btnrec
		btnadv.addActionListener(this);
		ImageIcon IIqui=new ImageIcon(this.getClass().getResource("photo/btnquit2.png"));
		btnqui=new JButton("",IIqui);
		btnqui.setBounds(110, 420, 80, 35);
		btnqui.setContentAreaFilled(false);//btnquit
		btnqui.addActionListener(this);
		JPanel jp1=new JPanel();
		jp1.setLayout(null);
		jp1.setOpaque(false);//JPanel1 with btn
		jp1.add(JLwelcome);jp1.add(btnstart);jp1.add(btnio);
		jp1.add(btnrec);jp1.add(btnran);jp1.add(btnadv);jp1.add(btnqui);
		add(jp1);
		setResizable(false);//设置不可改变大小
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==btnstart){
			dispose();
			MainScreen ms=new MainScreen(u);
			Thread t=new Thread(ms);
			t.start();
		}
		if(e.getSource()==btnio){
			OI oi=new OI();
		}
		if(e.getSource()==btnrec){
			Record rc=new Record(u);
		}
		if(e.getSource()==btnran){
			Ranking rk=new Ranking();
		}
		if(e.getSource()==btnadv){
			Advice ad=new Advice(u);
		}
		if(e.getSource()==btnqui){
			dispose();
			Login lg=new Login();
		}
	}
}
