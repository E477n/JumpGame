package jumpgame;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class MainScreen extends JFrame implements Runnable,ActionListener{
	JPanel jp;
	Actor ac;
	Block[] bl;
	String user;
	JLabel JLuser;
	JLabel JLscore;
	JLabel JLlevel;
	JMenuItem mi11,mi12,mi21;
	int moveDir=0;
	int btm=0;
	int dr=1;//指示方向
	boolean isLive=true;
	boolean isStart=true;
	boolean moveBg=false;
	boolean isMove = false;
	int base=200;
	int level=1;
	int score=0;
	public MainScreen(){
	}
	public MainScreen(String u){
		super("Jump Game");
		setBounds(450,100,305,555);
		ImageIcon bg=new ImageIcon(this.getClass().getResource("photo/mainbg.jpg"));
		JLabel label=new JLabel(bg);//背景图片标签
		label.setBounds(0, 0, bg.getIconWidth(),bg.getIconHeight()+53);
		JPanel imagePanel=(JPanel)this.getContentPane();
		imagePanel.setOpaque(false);//窗格设置透明
		jp=new JPanel();
		jp.setLayout(null);
		jp.setOpaque(false);
		bl =new Block[9];
		bl[0]=new Block(70);bl[1]=new Block(150);
		bl[2]=new Block(150);bl[3]=new Block(220);
		bl[4]=new Block(290);bl[5]=new Block(290);
		bl[6]=new Block(360);bl[7]=new Block(450);
		bl[8]=new Block(450);
		for(int i=0;i<9;i++){
			jp.add(bl[i]);
		}//加入block
		ac=new Actor();
		ac.x=bl[8].x;
		jp.add(ac);//加入actor
		addKeyListener(ac);
		user=u;
		JLuser=new JLabel("user:"+user);
		JLuser.setForeground(Color.white);
		JLuser.setBounds(10,10,100,20);
		JLscore=new JLabel("score:");//分数显示标签	
		JLscore.setForeground(Color.white);
		JLscore.setBounds(10, 30, 100, 20);
		JLlevel=new JLabel("level:");
		JLlevel.setForeground(Color.white);
		JLlevel.setBounds(10, 50, 50, 20);
		jp.add(JLuser);jp.add(JLscore);jp.add(JLlevel);
		this.getLayeredPane().add(label,new Integer(Integer.MIN_VALUE));//将图片置于最底层		
		JMenuBar menubar=new JMenuBar();
		JMenu menu1=new JMenu("menu");
		JMenu menu2=new JMenu("help");
		mi11=new JMenuItem("return");mi11.addActionListener(this);
		mi12=new JMenuItem("exit");mi12.addActionListener(this);
		mi21=new JMenuItem("operate instruction");mi21.addActionListener(this);
		menu1.add(mi11);menu1.add(mi12);
		menu2.add(mi21);
		menubar.add(menu1);menubar.add(menu2);
		add(jp);
		setJMenuBar(menubar);//菜单
		setResizable(false);//设置不可改变大小
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public int funT(int x){
		return x*x/50;
	}
	int r=1;
	int t=0;
	int bd_base[]=new int[9];
	
	public void run() {	
		for(int i=0;i<9;i++){
			bd_base[i]=bl[i].y;
		}
		int score_dr=10000;
		JLlevel.setText("level:"+level);
		while(true){
			if(isStart==true)
			{
			dis();
			ifJump();
			t+=r;
			if(ac.x<-30)
				ac.x=300;
			if(ac.x>300)
				ac.x=-30;
			if(moveBg==false){//人物 跳跃
				ac.y=base+funT(t);
				if(score>score_dr){
					level++;
					score_dr+=10000;
					JLlevel.setText("level:"+level);
				}
			}
			else{
				int aaa=200-(200+base-ac.y)-funT(t);
				for(int i=0;i<9;i++){
					bl[i].y=bd_base[i]+aaa;
				score++;				
				JLscore.setText("Score:"+score/10);
				}
			}
			ifIsDead();
			ac.setLocation(ac.x,ac.y);//刷新屏幕 
			for(int i=0;i<9;i++){
				bl[i].setLocation(bl[i].x, bl[i].y);
			}
			try {//停顿 20ms
				Thread.sleep(7);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(t<=-100){//t小于-100 t++
				r=-r;
				}
			if(t<0)moveDir=0;//t小于 0 上升状态 反之 下降状态。
			else {
				moveDir=1;//0为上升，1为下降
			}
			if(ac.y<170&&moveDir==0&&moveBg==false){//y小于一定程度的时候背景开始动，任务则在y的位置停止 
				moveBg=true;
			}
			else if(t==0&&moveBg==true)
				{
					moveBg=false;
					base=200;
					for(int i=0;i<9;i++){
						bd_base[i]=bl[i].y;
					}
				}
			}
		}
	}
	public int findMix(){//找出窗口中最高处block的位置
		int min=bl[0].y;
		for(int i=1;i<9;i++){
			if(bl[i].y<min)
				min=bl[i].y;
		}
		return min;
	}
	public void dis(){
		for(int i=0;i<9;i++){
			if(bl[i].y>450)
			{
				bl[i].newPosition(findMix()-140-level);
				bd_base[i]=bl[i].y;
			}
		}
	}
	public void ifIsDead(){
		if(ac.y>505&&isLive==true){
			isStart=false;
			isLive=false;
			GameOver go=new GameOver(user,level,score);
		}
	}
	public void ifJump(){
		for(int i=0;i<9;i++){
			if(moveDir==1&&(ac.x>bl[i].x-30&&ac.x<bl[i].x+60&&ac.y>bl[i].y-55&&ac.y<bl[i].y-45)){
				moveDir=0;
				t=-100;
				r=1;	
				base=ac.y-200;
			}
		}
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==mi11){
			isStart=false;
			dispose();
			AfterLogin al=new AfterLogin(user);
		}
		if(e.getSource()==mi12){
			System.exit(0);
		}
		if(e.getSource()==mi21){
			OI oi=new OI();
		}
	}
}