package jumpgame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import database.ConnectDB;

public class GameOver extends JFrame implements ActionListener{
	JLabel JLrecord;
	JButton btnsave;
	JButton btncancel;
	String u;
	int l,s;
	public GameOver(){
	}
	public GameOver(String user,int level,int score){
		super("Game Over");
		setBounds(480,210,255,280);
		ImageIcon lg=new ImageIcon(this.getClass().getResource("photo/gameover.jpg"));
		JLabel label=new JLabel(lg);
		label.setBounds(0,0,lg.getIconWidth(),lg.getIconHeight());
		JPanel imagePanel=(JPanel)this.getContentPane();
		imagePanel.setOpaque(false);//¥∞∏Ò…Ë÷√Õ∏√˜
		this.getLayeredPane().add(label,new Integer(Integer.MIN_VALUE));//±≥æ∞Õº∆¨…Ë÷√
		JPanel jp1=new JPanel();
		jp1.setOpaque(false);
		jp1.setLayout(null);
		u=user;l=level;s=score/10;
		JLrecord=new JLabel();
		JLrecord.setText(user+",your record:"+"\nlevel:"+level+"   score:"+score/10);
		JLrecord.setBounds(10, 140, 240, 40);
		ImageIcon btnsaveim=new ImageIcon(this.getClass().getResource("photo/btnsave.png"));
		btnsave=new JButton("",btnsaveim);
		btnsave.setBounds(40, 200, 70, 30);
		btnsave.setContentAreaFilled(false);//…Ë÷√∞¥≈•±≥æ∞Œ™Õ∏√˜
		btnsave.addActionListener(this);
		ImageIcon btncancelim=new ImageIcon(this.getClass().getResource("photo/btncancel.png"));	
		btncancel=new JButton("",btncancelim);
		btncancel.setBounds(140, 200, 70, 30);
        btncancel.setContentAreaFilled(false);
        btncancel.addActionListener(this);
        jp1.add(JLrecord);
        jp1.add(btnsave);jp1.add(btncancel);
        add(jp1);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==btnsave){
			try {
				insertRecord(u,l,s);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			dispose();
		}
		if(e.getSource()==btncancel){
			dispose();
		}
	}
	public void insertRecord(String username,int level,int score) throws SQLException,IOException{
		Connection con=ConnectDB.getConnection();
		Statement stat=con.createStatement();
		stat.executeUpdate("insert into table2_record values(null,'"+username+"',"+level+","+score+",null)");
	}
}
