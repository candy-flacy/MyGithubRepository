package test;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;

public class TestSwing extends JFrame{
	/**程序入口 
     * @param args 
     */  
    public static void main(String[] args) {  
    	TestSwing q=new TestSwing();  
        q.initUI2();  
  
    }  
    /** 
     * 初始化界面方法 
     */  
    private void initUI() {  
        //设置窗体的属性值  
        this.setLocation(400, 200);//设置显示位置  
        this.setSize(378, 291);//设置大小  
        this.setTitle("QQ登陆界面");//设置标题  
        this.setDefaultCloseOperation(3);//设置关闭方式  
        this.setResizable(false);//设置窗体是否可以最大化  
          
        // 将背景图片添加到layeredPane  
        ImageIcon img = new ImageIcon("images/beijing.png");  
        JLabel imgLabel = new JLabel(img);  
        this.getLayeredPane().add(imgLabel, new Integer(Integer.MIN_VALUE));  
        imgLabel.setBounds(0,0,img.getIconWidth(), img.getIconHeight());  
        Container p4=this.getContentPane();    
          
        //实例化一个在北边的JLabel         
        JLabel jlb = new JLabel();  
        jlb .setPreferredSize(new Dimension(378,109));//设置标签的大小  
        //添加标签到窗体的北边  
        this.add(jlb,BorderLayout.NORTH);  
          
        //实例化一个在西边的JPanel  
        JPanel panelWest = new JPanel();  
        panelWest.setLayout(new FlowLayout(FlowLayout.RIGHT));  
        panelWest.setPreferredSize(new Dimension(105,0));  
        JLabel jlaImage2 = new JLabel(new ImageIcon("images/touxiang.png"));  
        panelWest.add(jlaImage2);  
        //添加panelWest到窗体的西边  
        this.add(panelWest,BorderLayout.WEST);  
          
        //实例化一个中央JPanel  
        JPanel panelCenter = new JPanel();  
        panelCenter.setLayout(new FlowLayout( FlowLayout.LEFT,10,5));  
        JComboBox jcb = new JComboBox();  
        jcb.setEditable(true);  
        jcb.setPreferredSize(new Dimension(170,25));//设置组件的大小  
        JLabel jbuReg = new JLabel("注册账号");  
        jbuReg.setFont(new Font("宋体",0,14));//设置显示的字体样式  
        JPasswordField jpf = new JPasswordField();  
        jpf.setPreferredSize(new Dimension(170,25));//设置组件的大小  
        JLabel jbu = new JLabel("找回密码");  
        jbu.setFont(new Font("宋体",0,14));  
        JCheckBox jcb1 = new JCheckBox("记住密码");       
        jcb1.setFont(new Font("宋体",0,13));  
        JCheckBox jcb2 = new JCheckBox("自动登录");  
        jcb2.setFont(new Font("宋体",0,13));  
        panelCenter.add(jcb);  
        panelCenter.add(jbuReg);  
        panelCenter.add(jpf);  
        panelCenter.add(jbu);  
        panelCenter.add(jcb1);  
        panelCenter.add(jcb2); 
        
        
        this.add(panelCenter,BorderLayout.CENTER);  
          
        //实例化一个放置在窗体南边的面板  
        JPanel panelSouth=new JPanel();  
        JButton btn = new JButton(new ImageIcon("images/denglu.png"));  
        btn.setPreferredSize(new Dimension(145,30));  
        panelSouth.add(btn);  
        this.add(panelSouth,BorderLayout.SOUTH);  
          
        //将个个组件设置透明  
        jcb1.setOpaque(false);  
        jcb2.setOpaque(false);  
        ((JPanel) p4).setOpaque(false);  
        panelWest.setOpaque(false);  
        panelCenter.setOpaque(false);  
        panelSouth.setOpaque(false);  
        //设置窗体显示  
        this.setVisible(true);  
          
    }  
    
    private void initUI2(){
    	
    	//设置窗体的属性值  
        this.setLocation(400, 200);//设置显示位置  
        this.setSize(378, 291);//设置大小  
        this.setTitle("测试");//设置标题  
        this.setDefaultCloseOperation(3);//设置关闭方式  
        this.setResizable(false);//设置窗体是否可以最大化  
    	
//        JMenuBar menubar=new JMenuBar();  
//        this.setJMenuBar(menubar);  
//        menubar.setOpaque(false);  
//        JMenu jm1=new JMenu("编辑(E)");  
//        menubar.add(jm1);  
//        JMenuItem jmt1=new JMenuItem("复制(C) (Ctrl+C)");  
//        jm1.add(jmt1);  
//        JMenuItem jmt2=new JMenuItem("粘贴(V) (Ctrl+V)");  
//        jm1.add(jmt2);  
//        JMenu jm2=new JMenu("查看(V)");  
//        menubar.add(jm2);  
//        JMenuItem jmt3=new JMenuItem("标准型 (T)");  
//        jm2.add(jmt3);  
//        JMenuItem jmt4=new JMenuItem("科学型(S)");  
//        jm2.add(jmt4);  
//        JMenuItem jmt5=new JMenuItem("数字分组(I)");  
//        jm2.add(jmt5);  
//        JMenu jm3=new JMenu("帮助(H)");  
//        JMenuItem jmt6=new JMenuItem("帮助主题(H)");  
//        jm3.add(jmt6);  
//        JMenuItem jmt7=new JMenuItem("关于计算器(A)");  
//        jm3.add(jmt7);  
//        menubar.add(jm3); 
        
        JPanel p = new JPanel();
        JPopupMenu titleJPopupMenu=new JPopupMenu("弹出式菜单");
        p.add(titleJPopupMenu);
        
    	this.add(p);
    	
    	this.setVisible(true);
    } 
}
