package jumpgame;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
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
import javax.swing.table.TableColumn;

import database.ConnectDB;

public class Record extends JFrame implements ActionListener{
	JTextField jtf;
	JButton btnsearch,btndelete,btnok,btnclear,btndr;
	JLabel hint;
	String[] columns={"id","username","level","score","time"};
	int[] width={20,50,50,50,120};//�����п����Ƕ������ݺ��п����ʧЧ
	List<Object> rows=new ArrayList<Object>();
	Object[] obj;
	AbstractTableModel model;
	JTable table;
	String username;
	public Record(){
	}
	public Record(String u){
		super("Record");
		username=u;
		setBounds(480,130,455,530);
		setLayout(null);
		ImageIcon IIbg=new ImageIcon(this.getClass().getResource("photo/record.jpg"));
		JLabel JLbg=new JLabel(IIbg);
		JLbg.setBounds(0, 0, IIbg.getIconWidth(),IIbg.getIconHeight());
		JPanel imagePanel=(JPanel)this.getContentPane();
		imagePanel.setOpaque(false);//��������͸��
		this.getLayeredPane().add(JLbg,new Integer(Integer.MIN_VALUE));//����ͼƬ��ǩ
		jtf=new JTextField();
		jtf.setBackground(Color.LIGHT_GRAY);
		jtf.setBounds(15, 85, 340, 30);
		ImageIcon IIsearch=new ImageIcon(this.getClass().getResource("photo/btnsearch.png"));
		btnsearch=new JButton("",IIsearch);
		btnsearch.setBounds(365, 85, 70, 30);
		btnsearch.setContentAreaFilled(false);//���ð�ť����Ϊ͸��
		btnsearch.addActionListener(this);
		hint=new JLabel("");
		hint.setBounds(20,415,400,20);
		hint.setForeground(Color.white);
		ImageIcon IIdelete=new ImageIcon(this.getClass().getResource("photo/btndelete.png"));
		btndelete=new JButton("",IIdelete);
		btndelete.setBounds(20, 445, 70, 30);
		btndelete.setContentAreaFilled(false);//���ð�ť����Ϊ͸��
		btndelete.addActionListener(this);
		ImageIcon IIok=new ImageIcon(this.getClass().getResource("photo/btnok.png"));
		btnok=new JButton("",IIok);
		btnok.setBounds(130, 445, 70, 30);
		btnok.setContentAreaFilled(false);//���ð�ť����Ϊ͸��
		btnok.addActionListener(this);
		ImageIcon IIclear=new ImageIcon(this.getClass().getResource("photo/btnclear.png"));
		btnclear=new JButton("",IIclear);
		btnclear.setBounds(230, 445, 70, 30);
		btnclear.setContentAreaFilled(false);//���ð�ť����Ϊ͸��
		btnclear.addActionListener(this);
		ImageIcon IIdr=new ImageIcon(this.getClass().getResource("photo/btndeletedrecord.png"));
		btndr=new JButton("",IIdr);
		btndr.setBounds(330, 445, 70, 30);
		btndr.setContentAreaFilled(false);//���ð�ť����Ϊ͸��
		btndr.addActionListener(this);
		model=new TableModel();
		table =new JTable(model);
		table.setGridColor(Color.LIGHT_GRAY);
		TableColumn[] column=new TableColumn[5];
		for(int i=0;i<5;i++){
			column[i]=table.getColumnModel().getColumn(i);
			column[i].setPreferredWidth(width[i]);
		}//�����п�
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		JScrollPane jsp=new JScrollPane(table);//��JTable������������
		jsp.setOpaque(false);
		jsp.setBounds(15, 120, 420, 290);
		add(jtf);add(hint);
		add(btnsearch);add(btndelete);add(btnok);add(btnclear);add(btndr);
		add(jsp);
		setResizable(false);//���ò��ɸı��С
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//��С��������
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
		public void deleteRow(int selectRow){
			rows.remove(selectRow);
			model.fireTableDataChanged();
		}
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnsearch){
			rows.clear();
			ResultSet rs1=null;
			int i=0;
			try {
				rs1=search();
				while(rs1.next()){
					Object[] obj=new Object[]{rs1.getInt("id"),
							rs1.getString("username"),rs1.getInt("level"),
							rs1.getInt("score"),rs1.getTimestamp("time")
					};
					rows.add(obj);
					i++;
					//System.out.println("add successfully!");
				}
				model.fireTableChanged(null);//ˢ�±������
				if(i<1){
					hint.setText("no record found!");
				}
				if(i==1){
					hint.setText("find "+i+" record");
				}
				if(i>1){
					hint.setText("find "+i+" records");
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if(e.getSource()==btndelete){
			try {
				deleteRow();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if(e.getSource()==btnok){
			dispose();
		}
		if(e.getSource()==btnclear){
			rows.clear();
			model.fireTableChanged(null);
			jtf.setText("");
			hint.setText("");
		}
		if(e.getSource()==btndr){
			try {
				readDeletedRecord();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	public ResultSet search() throws SQLException,IOException{
		Connection con=ConnectDB.getConnection();
		Statement stat=con.createStatement();
		ResultSet rs=null;
		String s=jtf.getText();
		if(s==null){
			rs=stat.executeQuery("select * from table2_record");
			//System.out.println("search * successfully!");
		}
		else{
			rs=stat.executeQuery("select * from table2_record where username like '%"+s+"%'");
			//System.out.println("search "+s+" successfully!");
		}
		return rs;
	}
	public void deleteRow() throws SQLException,IOException{
		Connection con=ConnectDB.getConnection();
		Statement stat=con.createStatement();
		int selectRow=table.getSelectedRow();
		Object selectValue=table.getModel().getValueAt(selectRow, 0);
		if(selectRow==-1){
			hint.setText("please choose one row!");
		}
		if(!((String)table.getModel().getValueAt(selectRow, 1)).equals(username)){
			hint.setText("sorry,you have no rights to delete this record!");
		}
		else{
			File file=new File("deletedrecord.txt");//�����			
			BufferedWriter bout1=new BufferedWriter(new FileWriter(file,true));//׷���ı����ļ�ĩβ
			bout1.write(model.getValueAt(selectRow, 0).toString()+" "+model.getValueAt(selectRow, 1)
			+" "+model.getValueAt(selectRow, 2)+" "+model.getValueAt(selectRow, 3)+" "+model.getValueAt(selectRow, 4));
			bout1.newLine();
			bout1.flush();
			((TableModel) model).deleteRow(selectRow);
			stat.executeUpdate("delete from table2_record where id="+selectValue);
			hint.setText("delete successfully!");
		}
	}
	public void readDeletedRecord() throws IOException{//������
		File file=new File("deletedrecord.txt");
		BufferedReader bin=new BufferedReader(new FileReader(file));
		String line=bin.readLine();
		List<String[]> list=new ArrayList<String[]>();
		rows.clear();
		while(line!= null){
			String[] str=line.split(" ");
			list.add(str);
			line=bin.readLine();
		}
		for(Iterator it=list.iterator();it.hasNext();){//arraylist����
			rows.add(it.next());
		}
		model.fireTableDataChanged();
	}
}