package jumpgame;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import database.ConnectDB;
import jumpgame.Record.TableModel;

public class Advice extends JFrame implements ActionListener,TableModelListener,MouseListener{
	JTextField jtf;
	JButton btnreturn,btnadd;
	JLabel hint;
	String username;
	String[] column={"","username","advice","time"};
	Vector<String> columns = buildTableModel(column); 
	Vector<Vector<Object>> rows = new Vector<Vector<Object>>();
	DefaultTableModel model;
	JTable table;
	int[] width={30,70,390,130};
	String oldvalue;
	public Advice(){
	}
	public Advice(String u){
		super("advice");
		username=u;
		setBounds(480,130,705,530);
		setLayout(null);
		ImageIcon IIbg=new ImageIcon(this.getClass().getResource("photo/advice.jpg"));
		JLabel JLbg=new JLabel(IIbg);
		JLbg.setBounds(0, 0, IIbg.getIconWidth(),IIbg.getIconHeight());
		JPanel imagePanel=(JPanel)this.getContentPane();
		imagePanel.setOpaque(false);//窗格设置透明
		this.getLayeredPane().add(JLbg,new Integer(Integer.MIN_VALUE));//背景图片标签
		model=new DefaultTableModel(rows,columns);
		model.addTableModelListener(this);
		table=new JTable(model);
		table.setEnabled(true);
		table.addMouseListener(this);
		TableColumn column1=table.getColumnModel().getColumn(0);
		column1.setMaxWidth(30);
		TableColumn column2=table.getColumnModel().getColumn(1);
		column2.setMaxWidth(70);
		TableColumn column4=table.getColumnModel().getColumn(3);
		column4.setMaxWidth(130);column4.setMinWidth(130);
		JScrollPane p1=new JScrollPane(table);
		p1.setOpaque(false);
		p1.setBounds(40, 115, 620, 240);
		hint=new JLabel("");
		hint.setBounds(220, 360, 500, 20);
		hint.setForeground(Color.white);
		jtf=new JTextField();
		jtf.setBounds(220, 390, 440, 35);
		ImageIcon IIreturn=new ImageIcon(this.getClass().getResource("photo/btnreturn.png"));
		btnreturn=new JButton("",IIreturn);
		btnreturn.setBounds(40, 440, 80, 35);
		btnreturn.setContentAreaFilled(false);
		btnreturn.addActionListener(this);
		ImageIcon IIadd=new ImageIcon(this.getClass().getResource("photo/btnadd.png"));
		btnadd=new JButton("",IIadd);
		btnadd.setBounds(580, 440, 80, 35);
		btnadd.setContentAreaFilled(false);
		btnadd.addActionListener(this);
		add(p1);add(hint);add(jtf);
		add(btnreturn);add(btnadd);
		setResizable(false);//设置不可改变大小
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		try {
			readAdvice();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Vector<String> buildTableModel(String[] type) {  
	    Vector<String> vector = new Vector<String>();  
	    for (int i = 0; i < type.length; i++)  
	        vector.add(type[i]);  
	    return vector;  
	}
	public void readAdvice() throws SQLException, IOException{
		Connection con=ConnectDB.getConnection();
		Statement stat=con.createStatement();
		ResultSet rs;
		rs=stat.executeQuery("select * from table3_advice");
		int i=1;
		while(rs.next()){
			Vector<Object> vec=new Vector<Object>();
			vec.add(i);vec.addElement(rs.getString("username"));
			vec.add(rs.getString("advice"));vec.add(rs.getTimestamp("time"));
			rows.add(vec);
			i++;
		}
		model.fireTableDataChanged();		
	}
	public void insert() throws SQLException, IOException{//将advice插入table中并且添加进table3_advice
		Connection con=ConnectDB.getConnection();
		Statement stat=con.createStatement();
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time=format.format(date);
		if(jtf.getText().equals("")){
			hint.setText("please enter your advice");
		}
		else{
			Vector<Object> vec=new Vector<Object>();
			vec.add(rows.size()+1);vec.add(username);
			vec.add(jtf.getText());vec.add(time);
			rows.add(vec);
			model.fireTableDataChanged();
			stat.executeUpdate("insert into table3_advice values(null,'"+username
					+"','"+jtf.getText()+"','"+time+"')");
			hint.setText("add one advice successfully!");
			jtf.setText("");
		}
	}
	public void actionPerformed(ActionEvent e) {
		int a = 0;
		if(e.getSource()==btnadd){
			try {
				insert();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if(e.getSource()==btnreturn){
			dispose();
		}
	}
	public void tableChanged(TableModelEvent e) {		
		try {
			Connection con=ConnectDB.getConnection();
			Statement stat=con.createStatement();
			if(e.getType() == TableModelEvent.UPDATE){
				if(table.getSelectedRow()==-1){
					return;
				}
		        String newvalue = table.getValueAt(table.getSelectedRow(),table.getSelectedColumn()).toString();
		        if((!newvalue.equals(oldvalue))&&table.getValueAt(table.getSelectedRow(),1).equals(username)&&table.getSelectedColumn()==2){
		        	stat.executeUpdate("update table3_advice set advice='"+newvalue+"' where id="+(table.getSelectedRow()+1));
		        	hint.setText("edit one record successfully!");
		        }
		        else{
		        	hint.setText("sorry,this operation is invalid");
		        }
		}
		} catch (SQLException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	public void mouseClicked(MouseEvent e) {//鼠标点击事件， 
		oldvalue = table.getValueAt(table.getSelectedRow(),table.getSelectedColumn()).toString();
	}
	public void mouseEntered(MouseEvent e) {
	}
	public void mouseExited(MouseEvent e) {
	}
	public void mousePressed(MouseEvent e) {
	}
	public void mouseReleased(MouseEvent e) {		
	}
}
