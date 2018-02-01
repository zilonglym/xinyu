package com.store.check;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.synth.SynthSeparatorUI;

import org.json.JSONObject;

import serialException.ExceptionWriter;


/**
 * 主程序
 *
 */
public class Client extends JFrame{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    public static int personId=0;
    /**
     * 程序界面宽度
     */
    public static final int WIDTH = 1280;
    
    /**
     * 程序界面高度
     */
    public static final int HEIGHT = 780;
    
    /**
     * 程序界面出现位置（横坐标�?
     */
    public static final int LOC_X = 200;
    
    /**
     * 程序界面出现位置（纵坐标�?
     */
    public static final int LOC_Y = 70;

    Color color = Color.WHITE; 
    Image offScreen = null;    //用于双缓�?
    
    //设置window的icon
    Toolkit toolKit = getToolkit();
    Image icon = toolKit.getImage(Client.class.getResource("computer.png"));
    private JTextField userName=new JTextField();    //帐号
    private JPasswordField password=new JPasswordField();    //密码
    JPanel contentPane=new JPanel();  
   
    //持有其他�?
    DataView dataview = new DataView(this);    //主界面类（显示监控数据主面板�?
    private JButton bu1;
    // 小容器
    private JLabel jl1;
 
    /**
     * 主方�?
     * @param args    //
     */
    public static void main(String[] args) {
        new Client().launchFrame();    
    }
    
    /**
     * 显示主界�?
     */
    public void launchFrame() {
        this.setBounds(LOC_X, LOC_Y, WIDTH, HEIGHT);    //设定程序在桌面出现的位置
        this.setTitle("湖南中仓验货程序");    //设置程序标题
        this.setIconImage(icon);
        this.setBackground(Color.white);    //设置背景�?
        
       initLoginFrm();
        
        this.addWindowListener(new WindowAdapter() {
            //添加对窗口状态的监听
            public void windowClosing(WindowEvent arg0) {
                //当窗口关闭时
                System.exit(0);    //�?��程序
            }
            
        });
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 设置布局方式为绝对定位
        this.setLayout(null);
        this.addKeyListener(new KeyMonitor());    //添加键盘监听�?
        this.setResizable(false);    //窗口大小不可更改
        this.setVisible(true);    //显示窗口
        this.setLocationRelativeTo(null);
        
        // 窗体可见
        new Thread(new RepaintThread()).start();    //�?��重画线程
    }
    
    /**
     * 画出程序界面各组件元�?
     */
    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setFont(new Font("微软雅黑", Font.BOLD, 40));
        g.setColor(Color.black);
        g.drawString("欢迎进行湖南中仓验货系统", 45, 190);
        g.setFont(new Font("微软雅黑", Font.ITALIC, 26));
        g.setColor(Color.BLACK);
        g.drawString("Version1.0  Author：yangmin", 520, 660);
        g.setFont(new Font("微软雅黑", Font.BOLD, 30));
        g.setColor(color);
        
        
        g.setColor(Color.black);
        g.setFont(new Font("微软雅黑", Font.BOLD, 25));
        g.drawString("用户名： ", 320, 390);
        
        g.setColor(Color.black);
        g.setFont(new Font("微软雅黑", Font.BOLD, 25));
        g.drawString("密码： ", 320, 450);
        
        g.drawString("---点击Enter键登录主界面---", 260, 550);
      
        
        
    }
    
    /**
     * 双缓冲方式重画界面各元素组件
     */
    public void update(Graphics g) {
    	
        if (offScreen == null)    offScreen = this.createImage(WIDTH, HEIGHT);
        Graphics gOffScreen = offScreen.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.white);
        gOffScreen.fillRect(0, 0, WIDTH, HEIGHT);    //重画背景画布
        this.paint(gOffScreen);    //重画界面元素
        gOffScreen.setColor(c);
        g.drawImage(offScreen, 0, 0, null);    //将新画好的画布�?贴�?在原画布�?
       
    }
    
    /*
     * 内部类形式实现对键盘事件的监�?
     */
    private class KeyMonitor extends KeyAdapter {
    	
        public void keyReleased(KeyEvent e) {
            int keyCode = e.getKeyCode();
            System.err.println("11111111111111");
            if (keyCode == KeyEvent.VK_ENTER) {    //当监听到用户敲击键盘enter键后执行下面的操�?
            	showDataView();            	
            }
        }
        
    }
    
    private void showDataView(){
    	//进行登录判断
    	String userName=this.userName.getText();
    	String password=this.password.getText();
    	String url=DataView.url+"check/submitLogin?userName="+userName+"&password="+password;
    	String result=HttpClientUtils.httpPost(url, "");
    	 JSONObject json=new JSONObject(result);
    	 String code=json.getString("code");
    	 if(code!=null && code.equals("200")){
    		 JSONObject person=json.getJSONObject("person");
    		 Client.personId=person.getInt("id");
	        setVisible(false);    //隐去欢迎界面
	        dataview.setVisible(true);    //显示监测界面
	        dataview.dataFrame();    //初始化监测界�?
    	 }else{
    		 JOptionPane.showMessageDialog(null, "帐号密码不正确，请重新输入！", "错误", JOptionPane.INFORMATION_MESSAGE);
    		 this.password.setText("");
    		 this.userName.setText("");
    		 this.password.setFocusable(false);
    		 this.userName.setFocusable(true);
    	 }
    }
    /*
     * 重画线程（每�?50毫秒重画�?���?
     */
    private class RepaintThread implements Runnable {
        public void run() {
            while(true) {
                repaint();
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    //重画线程出错抛出异常时创建一个Dialog并显示异常详细信�?
                    String err = ExceptionWriter.getErrorInfoFromException(e);
                    JOptionPane.showMessageDialog(null, err, "错误", JOptionPane.INFORMATION_MESSAGE);
                    System.exit(0);
                }
            }
        }
        
    }
    
    
    /*
     * 初始化方法
     */
    public void initLoginFrm() {
        // 创建一个容器
        Container con = this.getContentPane();
        jl1 = new JLabel();
        // 设置背景图片
       
        jl1.setText("用户名:");
        jl1.setBounds(120, 390, 355, 265);
         
        // 用户号码登录输入框
        this.userName = new JTextField();
        this.userName.setFont(new Font("微软雅黑", Font.ITALIC, 24));
        this.userName.setText("");
        this.userName.setBounds(420, 330, 150, 40);
        this.userName.setFocusable(true);
        this.userName.addKeyListener(new KeyAdapter(){
        	  public void keyReleased(KeyEvent e) {
                  int keyCode = e.getKeyCode();
                //  JOptionPane.showMessageDialog(null, keyCode, "错误", JOptionPane.INFORMATION_MESSAGE);
                  if (keyCode == KeyEvent.VK_ENTER) {    //当监听到用户敲击键盘enter键后执行下面的操�?
                	  String userText=userName.getText();
                	  String passText=password.getText();
                	  if(userText.trim().length()==0 ){
                		  //JOptionPane.showMessageDialog(null, 1, "错误", JOptionPane.INFORMATION_MESSAGE);
                		  userName.setFocusable(true);
                		  password.setFocusable(false);
                	  }else if(passText.trim().length()==0){
                		 // JOptionPane.showMessageDialog(null, 2, "错误", JOptionPane.INFORMATION_MESSAGE);
                		  userName.setFocusable(false);
                		  password.setFocusable(true);
                	  }else{
                		 // JOptionPane.showMessageDialog(null, 3, "错误", JOptionPane.INFORMATION_MESSAGE);
                		  showDataView();
                	  }

                  }
              }
        });
        this.password.setFont(new Font("微软雅黑", Font.ITALIC, 24));
        this.password.setText("");
        this.password.setBounds(420, 390,150, 40);
        this.password.addKeyListener(new KeyAdapter(){
        	  public void keyReleased(KeyEvent e) {
                  int keyCode = e.getKeyCode();
                 
                  if (keyCode == KeyEvent.VK_ENTER) {    //当监听到用户敲击键盘enter键后执行下面的操�?
                	  String userText=userName.getText();
                	  String passText=password.getText();
                	  if(userText.trim().length()==0 ){
                		 // JOptionPane.showMessageDialog(null, 1, "错误", JOptionPane.INFORMATION_MESSAGE);
                		  userName.setFocusable(true);
                		  password.setFocusable(false);
                	  }else if(passText.trim().length()==0){
                		 // JOptionPane.showMessageDialog(null, 2, "错误", JOptionPane.INFORMATION_MESSAGE);
                		  userName.setFocusable(false);
                		  password.setFocusable(true);
                	  }else{
                		 // JOptionPane.showMessageDialog(null, 3, "错误", JOptionPane.INFORMATION_MESSAGE);
                		  showDataView();
                	  }

                  }
              
              }
        });
        // 所有组件用容器装载
        con.setBounds(200, 200, 500, 300);
        con.add(jl1);
        con.add(userName);
        con.add(password);
 
    }
    
}