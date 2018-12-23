package jumpgame;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

import database.ConnectDB;

public class Ranking extends JFrame implements ActionListener{
	JButton btnreturn;
	JButton btnsave;
	JLabel hint;
	JTextField jtf;
	String[] columns={"ranking","username","score","time"};
	List<Object> rows=new ArrayList<Object>();
	Object[] obj;
	String[][] save=new String[15][3];
	AbstractTableModel model;
	JTable table;
	public Ranking(){
		super("Ranking");
		setBounds(480,130,705,530);
		setLayout(null);
		ImageIcon IIbg=new ImageIcon(this.getClass().getResource("photo/ranking.jpg"));
		JLabel JLbg=new JLabel(IIbg);
		JLbg.setBounds(0, 0, IIbg.getIconWidth(),IIbg.getIconHeight());
		JPanel imagePanel=(JPanel)this.getContentPane();
		imagePanel.setOpaque(false);//窗格设置透明
		this.getLayeredPane().add(JLbg,new Integer(Integer.MIN_VALUE));//背景图片标签
		ImageIcon IIreturn=new ImageIcon(this.getClass().getResource("photo/btnreturn.png"));
		btnreturn=new JButton("",IIreturn);
		btnreturn.setBounds(550, 430, 80, 35);
		btnreturn.setContentAreaFilled(false);
		btnreturn.addActionListener(this);//btnreturn
		ImageIcon IIsave=new ImageIcon(this.getClass().getResource("photo/btnsave.png"));
		btnsave=new JButton("",IIsave);
		btnsave.setBounds(450, 430, 80, 35);
		btnsave.setContentAreaFilled(false);
		btnsave.addActionListener(this);//btnsave
		jtf=new JTextField();
		jtf.setBounds(75, 430, 350, 35);
		model=new TableModel();
		table =new JTable(model);
		table.setGridColor(Color.LIGHT_GRAY);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		JScrollPane p1=new JScrollPane(table);
		p1.setOpaque(false);
		p1.setBounds(75, 120, 550, 265);
		hint=new JLabel("if you want to save these ranking messages,please enter the file name underneath");
		hint.setBounds(75,400,500,20);
		hint.setForeground(Color.white);
		add(p1);add(hint);
		add(jtf);add(btnsave);add(btnreturn);
		setResizable(false);//设置不可改变大小
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		rows.clear();
		try {
			sortRecord();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	class TableModel extends AbstractTableModel{
		public int getColumnCount() {
			return columns.length;
		}
		public int getRowCount() {
			return rows.size();
		}
		public Object getValueAt(int rowIndex, int columnIndex) {
			return ((Object[])rows.get(rowIndex))[columnIndex];
		}
		public String getColumnName(int column){
			return columns[column];			
		}
	}
	public void sortRecord() throws SQLException,IOException{
		Connection con=ConnectDB.getConnection();
		Statement stat=con.createStatement();
		ResultSet rs=stat.executeQuery("select * from table2_record order by score DESC");
		int i=1;
		while(rs.next()&&i<=15){//显示前15名
			obj=new Object[]{i,rs.getString("username"),
					rs.getInt("score"),rs.getTimestamp("time")
			};
			rows.add(obj);
			save[i-1][0]=obj[0].toString();
			save[i-1][1]=obj[1].toString();
			save[i-1][2]=obj[2].toString();					
			i++;
		}
		model.fireTableDataChanged();
	}//可以重写compareTo代替
	public void saveRanking() throws IOException{
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time=format.format(date);
		if(jtf.getText().equals("")){
			File file1=new File("rankingfile.txt");
			BufferedWriter bout1=new BufferedWriter(new FileWriter(file1,true));//追加文本到文件末尾
			bout1.write(time);
			bout1.newLine();
			for(int i=0;i<15;i++){
				bout1.write(save[i][0]+save[i][1]+save[i][2]);
				bout1.newLine();
			}
			bout1.newLine();
			bout1.close();
			hint.setText("save ranking message in rankingfile.txt successfully!");
		}
		else{
			File file2=new File(jtf.getText());
			if(!file2.exists()){
				hint.setText("file path error!please enter again.");
				return;
			}
			else{
				BufferedWriter bout2=new BufferedWriter(new FileWriter(file2,true));
				bout2.write(time);
				bout2.newLine();
				for(int i=0;i<15;i++){
					bout2.write(save[i][0]+save[i][1]+save[i][2]);
					bout2.newLine();
				}
				bout2.newLine();
				bout2.close();
				hint.setText("save ranking message in "+jtf.getText()+" successfully!");
			}
		}
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnreturn){
			dispose();
		}
		if(e.getSource()==btnsave){
			try {
				saveRanking();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
