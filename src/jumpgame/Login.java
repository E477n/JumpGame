package jumpgame;

import java.awt.Color;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import database.ConnectDB;

public class Login extends JFrame implements ActionListener{
	JLabel hint;
	JTextField username;
	TextField password;
	JButton btnlog;
	JButton btnexi;
	public Login(){
		super("Game Login");
		setBounds(450,100,255,280);
		hint=new JLabel("");
		hint.setBounds(30, 85, 200, 10);
		ImageIcon lg=new ImageIcon(this.getClass().getResource("photo/login.jpg"));
		JLabel label=new JLabel(lg);
		label.setBounds(0,0,lg.getIconWidth(),lg.getIconHeight());
		JPanel imagePanel=(JPanel)this.getContentPane();
		imagePanel.setOpaque(false);//窗格设置透明
		this.getLayeredPane().add(label,new Integer(Integer.MIN_VALUE));//背景图片设置
		JPanel jp1=new JPanel();
		username=new JTextField(8);
		username.setBackground(Color.LIGHT_GRAY);
		username.setBounds(120, 105, 110, 25);
		password=new TextField(8);
		password.setEchoChar('*');
		password.setBackground(Color.LIGHT_GRAY);
		password.setBounds(120, 160, 110, 25);
		ImageIcon btnlogim=new ImageIcon(this.getClass().getResource("photo/btnlog.png"));
		btnlog=new JButton("",btnlogim);
		btnlog.setBounds(40, 200, 70, 30);
		btnlog.setContentAreaFilled(false);//设置按钮背景为透明
		btnlog.addActionListener(this);
		ImageIcon btnexiim=new ImageIcon(this.getClass().getResource("photo/btnquit.png"));
		btnexi=new JButton("",btnexiim);
		btnexi.setBounds(140, 200, 70, 30);
        btnexi.setContentAreaFilled(false);
        btnexi.addActionListener(this);
        jp1.add(hint);
		jp1.add(username);
		jp1.add(password);
		jp1.add(btnlog);jp1.add(btnexi);
		jp1.setOpaque(false);
		jp1.setLayout(null);;
		add(jp1);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==btnlog){
			String p=password.getText();
			String u=username.getText();
			try {
				checkUser(u,p);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if(e.getSource()==btnexi){
			dispose();
		}
	}
	public void checkUser(String u,String p) throws SQLException,IOException{
		Connection con=ConnectDB.getConnection();
		Statement stat=con.createStatement();
		ResultSet rs=stat.executeQuery("select * from table1_login");
		int flag=0;//flag为0，则用户名不存在，添加新用户进入；flag为1则用户名存在，需要输入正确密码
		while(rs.next()){
			if(rs.getString(1).equals(u)){
				flag=1;
				if(rs.getString(2).equals(p)){
					dispose();//以  String 的形式获取此 ResultSet 对象的当前行中指定列的值。
				    AfterLogin al=new AfterLogin(u);				    
				}
				else if(!rs.getString(2).equals(p)){
					hint.setText("Sorry,the password is wrong.Please enter again.");
					username.setText("");
					password.setText("");
				}				
			}
		}
		if(flag==0){
			insertUser(u,p);
		}
	}
	public void insertUser(String u,String p) throws SQLException,IOException{
		Connection con=ConnectDB.getConnection();
		Statement stat=con.createStatement();
		stat.executeUpdate("insert into table1_login values('"+u+"','"+p+"')");
		dispose();
		AfterLogin al=new AfterLogin(u);
	}
	public static void main(String[] args) {
		Login lg=new Login();
	}
}