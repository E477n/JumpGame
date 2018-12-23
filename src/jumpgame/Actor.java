package jumpgame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Actor extends JPanel implements KeyListener{
	int flag=1;//flag用来判断游戏是否结束
	int x;
	int y;
//	double g=20;//重力加速度
//	long time = 900;//一次下落或上升的时间
//	double py=1;//y轴比例尺
	public Actor(){
		ImageIcon IIac=new ImageIcon(this.getClass().getResource("photo/actor.png"));
		JLabel JLac=new JLabel(IIac);
		JLac.setBounds(0, 0, 50, 50);
		setOpaque(false);
		setLayout(null);
		setBounds(150,350,50,50);
		add(JLac);
		x=235;y=400;
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_LEFT:
				x-=30;
				this.repaint();
				break;
			case KeyEvent.VK_RIGHT:
				x+=30;
				this.repaint();
				break;
		}
	}//左右控制Actor
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
//	public void run(){		
//		double yy=y;
//		long start=System.currentTimeMillis();
//		long end=start;
//		double v0=g*time/1000.0;
//		while(end-start<=time){
//		    end=System.currentTimeMillis();
//		    double t=(end - start)/1000.0;
//		    yy=(y+g*t*t/2)*py;
//		    y=(int)yy;
//		    this.setBounds(x,y,50,50);
//		    try {
//	            Thread.sleep(50);
//	        }
//	        catch (InterruptedException e){ 
//	            e.printStackTrace();		        
//	        }
//		    while(end-start>time){
//		    	start=System.currentTimeMillis();
//		    	end=start;
//		    	while(end-start<=time){
//		    		end=System.currentTimeMillis();
//		    		t=(end-start)/1000.0;
//			    	yy=(y-(v0*t+g*t*t/2))*py/1.5;
//			    	y=(int)yy;
//			    	this.setBounds(x,y,50,50);
//			    	try {
//			            Thread.sleep(50);
//			        }
//			        catch (InterruptedException e){ 
//			            e.printStackTrace();		        
//			        }
//		    	}
//		    	start=System.currentTimeMillis();
//		    	end=start;
//		    }//实现Actor的弹跳
//		}
//	}
}	