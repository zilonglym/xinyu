package com.store.check;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.json.JSONObject;

import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import serialException.ExceptionWriter;
import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

/**
 * 监测数据显示类
 * @author Zhong
 *
 */
public class DataViewNew extends Frame {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private StringBuffer commStr=new StringBuffer();

    
   
    
    
    Client client = null;
    private int index=0;
   // private String url="http://admin.wlpost.com/check/checkTrade_new";
    
    public static String url="http://admin.wlpost.com/";

    private List<String> commList = null;    //保存可用端口号
    private SerialPort serialPort = null;    //保存串口对象
    private List<String> msgList=new ArrayList<String>();//存放消息列表
    
    private Font font = new Font("宋体", Font.BOLD, 25);
    private Font statusFont = new Font("宋体", Font.BOLD, 35);
    
    
//    private Label display = new Label("",  Label.LEFT);    //显示
    private Label barCode = new Label("", Label.CENTER);    //商品条码
    private Label orderCode = new Label("", Label.CENTER);    //快递条码
    private Label status=new Label("验货成功",Label.CENTER);//货物状态
    private Choice commChoice = new Choice();    //串口选择（下拉框）
    private Choice bpsChoice = new Choice();    //波特率选择
    private String temp="打开串口";
    private Button openSerialButton = new Button();
    private TextArea txtDisplay=new TextArea();
    
    Image offScreen = null;    //重画时的画布
    
    //设置window的icon
    Toolkit toolKit = getToolkit();
    Image icon = toolKit.getImage(DataViewNew.class.getResource("computer.png"));
    private String successfile="D:\\sound\\success.wav";
    private String failfile="D:\\sound\\fail.wav";
    private String chongfile="D:\\sound\\chongfusaomiao.wav";
    private String lanjiefile="D:\\sound\\lanjiechuku.wav";
    
    
    InputStream successIn =null; 
	AudioStream successAs =null;
	InputStream lanjieIn = null; // 打 开 一 个 声 音 文 件 流 作 为 输 入
	AudioStream lanjieAs = null; 
	InputStream chongIn =null;
	AudioStream chongAs = null;
	InputStream failIn =null; 
	AudioStream failAs =null;  
    /**
     * 类的构造方法
     * @param client
     */
    public DataViewNew(Client client) {
        this.client = client;
        commList = SerialTool.findPort();    //程序初始化时就扫描一次有效串口
    }
    
    
    
    /**
     * 画出主界面组件元素
     */
    public void paint(Graphics g) {
        Color c = g.getColor();
        
        g.setColor(Color.black);
        g.setFont(new Font("微软雅黑", Font.BOLD, 25));
        g.drawString(" 商品条码： ", 45, 73);

        g.setColor(Color.black);
        g.setFont(new Font("微软雅黑", Font.BOLD, 25));
        g.drawString(" 快递条码： ", 525, 73);
      
        g.setColor(Color.gray);
        g.setFont(new Font("微软雅黑", Font.BOLD, 20));
        g.drawString(" 串口选择： ", 795, 290);
        
        g.setColor(Color.gray);
        g.setFont(new Font("微软雅黑", Font.BOLD, 20));
        g.drawString(" 波特率： ", 795, 365);
    }
    /**
     * 主菜单窗口显示；
     * 添加Label、按钮、下拉条及相关事件监听；
     */
    public void dataFrame() {
        this.setBounds(client.LOC_X, client.LOC_Y, client.WIDTH, client.HEIGHT);
        this.setTitle("湖南中仓验货系统");
        this.setIconImage(icon);
        this.setBackground(Color.white);
        this.setLayout(null);
       
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent arg0) {
                if (serialPort != null) {
                    //程序退出时关闭串口释放资源
                    SerialTool.closePort(serialPort);
                }
                System.exit(0);
            }
            
        });
        
        barCode.setBounds(180, 53, 295, 50);
        barCode.setBackground(Color.black);
        barCode.setFont(font);
        barCode.setForeground(Color.white);
        add(barCode);
        
        orderCode.setBounds(660, 53, 295, 60);
        orderCode.setBackground(Color.black);
        orderCode.setFont(font);
        orderCode.setForeground(Color.white);
        add(orderCode);
        
        status.setBounds(200, 123, 625, 80);
        status.setBackground(Color.green);
        status.setFont(statusFont);
        status.setForeground(Color.white);
        add(status);
       
        
        
        txtDisplay.setBounds(30, 213, 755, 450);
        txtDisplay.setBackground(Color.yellow);
        txtDisplay.setFont(new Font("宋体", Font.BOLD, 32));
        txtDisplay.setForeground(Color.red);
        txtDisplay.setEditable(false);
        add(txtDisplay);
        
        
        //添加串口选择选项
        commChoice.setBounds(930, 280, 200, 200);
        //检查是否有可用串口，有则加入选项中
        if (commList == null || commList.size()<1) {
            JOptionPane.showMessageDialog(null, "没有搜索到有效串口！", "错误", JOptionPane.INFORMATION_MESSAGE);
        }
        else {
            for (String s : commList) {
                commChoice.add(s);
            }
        }
        add(commChoice);
        
        //添加波特率选项
        bpsChoice.setBounds(930, 350, 200, 200);
        bpsChoice.add("115200");
        add(bpsChoice);
        //-Dfile.encoding=gbk
        //添加打开串口按钮
        
		openSerialButton.setLabel(temp);
        openSerialButton.setBounds(840, 480, 300, 50);
        openSerialButton.setBackground(Color.lightGray);
       // openSerialButton.setFont(new Font("微软雅黑", Font.BOLD, 20));
        openSerialButton.setForeground(Color.darkGray);
        add(openSerialButton);
        //添加打开串口按钮的事件监听
        openSerialButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                
                //获取串口名称
                String commName = commChoice.getSelectedItem();            
                //获取波特率
                String bpsStr = bpsChoice.getSelectedItem();
                
                //检查串口名称是否获取正确
                if (commName == null || commName.equals("")) {
                    JOptionPane.showMessageDialog(null, "没有搜索到有效串口！", "错误", JOptionPane.INFORMATION_MESSAGE);            
                }
                else {
                    //检查波特率是否获取正确
                    if (bpsStr == null || bpsStr.equals("")) {
                        JOptionPane.showMessageDialog(null, "波特率获取错误！", "错误", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else {
                        //串口名、波特率均获取正确时
                        int bps = Integer.parseInt(bpsStr);
                        try {
                            
                            //获取指定端口名及波特率的串口对象
                            serialPort = SerialTool.openPort(commName, bps);
                            //在该串口对象上添加监听器
                            SerialTool.addListener(serialPort, new SerialListener());
                            //监听成功进行提示
                            JOptionPane.showMessageDialog(null, "监听成功,稍后将显示监测数据！", "提示", JOptionPane.INFORMATION_MESSAGE);
                             
                        } catch (Exception e1) {
                        	e1.printStackTrace();
                            //发生错误时使用一个Dialog提示具体的错误信息
                            JOptionPane.showMessageDialog(null, e1, "错误", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
                
            }
        });
        this.setResizable(false);
//        this.getGraphicsConfiguration().getDevice().setFullScreenWindow(this);
        new Thread(new RepaintThread()).start();    //启动重画线程
        
    }
    
  
    
    /**
     * 双缓冲方式重画界面各元素组件
     */
    public void update(Graphics g) {
        if (offScreen == null)    offScreen = this.createImage(Client.WIDTH, Client.HEIGHT);
        Graphics gOffScreen = offScreen.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.white);
        gOffScreen.fillRect(0, 0, Client.WIDTH, Client.HEIGHT);    //重画背景画布
        this.paint(gOffScreen);    //重画界面元素
        gOffScreen.setColor(c);
        g.drawImage(offScreen, 0, 0, null);    //将新画好的画布“贴”在原画布上
    }
    
    /*
     * 重画线程（每隔30毫秒重画一次）
     */
    private class RepaintThread implements Runnable {
        public void run() {
            while(true) {
                //调用重画方法
                repaint();
                //扫描可用串口
                commList = SerialTool.findPort();
                if (commList != null && commList.size()>0) {
                    
                    //添加新扫描到的可用串口
                    for (String s : commList) {
                        
                        //该串口名是否已存在，初始默认为不存在（在commList里存在但在commChoice里不存在，则新添加）
                        boolean commExist = false;    
                        
                        for (int i=0; i<commChoice.getItemCount(); i++) {
                            if (s.equals(commChoice.getItem(i))) {
                                //当前扫描到的串口名已经在初始扫描时存在
                                commExist = true;
                                break;
                            }                    
                        }
                        
                        if (commExist) {
                            //当前扫描到的串口名已经在初始扫描时存在，直接进入下一次循环
                            continue;
                        }
                        else {
                            //若不存在则添加新串口名至可用串口下拉列表
                            commChoice.add(s);
                        }
                    }
                    
                    //移除已经不可用的串口
                    for (int i=0; i<commChoice.getItemCount(); i++) {
                        
                        //该串口是否已失效，初始默认为已经失效（在commChoice里存在但在commList里不存在，则已经失效）
                        boolean commNotExist = true;    
                        
                        for (String s : commList) {
                            if (s.equals(commChoice.getItem(i))) {
                                commNotExist = false;    
                                break;
                            }
                        }
                        
                        if (commNotExist) {
                            //System.out.println("remove" + commChoice.getItem(i));
                            commChoice.remove(i);
                        }
                        else {
                            continue;
                        }
                    }
                    
                }
                else {
                    //如果扫描到的commList为空，则移除所有已有串口
                    commChoice.removeAll();
                }
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    String err = ExceptionWriter.getErrorInfoFromException(e);
                    JOptionPane.showMessageDialog(null, err, "错误", JOptionPane.INFORMATION_MESSAGE);
                    System.exit(0);
                }
            }
        }
        
    }
    
    /**
     * 以内部类形式创建一个串口监听类
     * @author zhong
     *
     */
    private class SerialListener implements SerialPortEventListener {
        
        /**
         * 处理监控到的串口事件
         */
        public void serialEvent(SerialPortEvent serialPortEvent) {
            
            switch (serialPortEvent.getEventType()) {

                case SerialPortEvent.BI: // 10 通讯中断
                    JOptionPane.showMessageDialog(null, "与串口设备通讯中断", "错误", JOptionPane.INFORMATION_MESSAGE);
                    break;

                case SerialPortEvent.OE: // 7 溢位（溢出）错误

                case SerialPortEvent.FE: // 9 帧错误

                case SerialPortEvent.PE: // 8 奇偶校验错误

                case SerialPortEvent.CD: // 6 载波检测

                case SerialPortEvent.CTS: // 3 清除待发送数据

                case SerialPortEvent.DSR: // 4 待发送数据准备好了

                case SerialPortEvent.RI: // 5 振铃指示

                case SerialPortEvent.OUTPUT_BUFFER_EMPTY: // 2 输出缓冲区已清空
                    break;
                
                case SerialPortEvent.DATA_AVAILABLE: // 1 串口存在可用数据
                    
                    //System.out.println("found data");
                    byte[] data = null;

                    
                    try {
                        if (serialPort == null) {
                            JOptionPane.showMessageDialog(null, "串口对象为空！监听失败！", "错误", JOptionPane.INFORMATION_MESSAGE);
                        }
                        else {
                        	Thread.sleep(10);
                            data = SerialTool.readFromPort(serialPort);    //读取数据，存入字节数组
                            //System.out.println(new String(data));
                            
                       // 自定义解析过程，你在实际使用过程中可以按照自己的需求在接收到数据后对数据进行解析
                            if (data == null || data.length < 1) {    //检查数据是否读取正确    
                                JOptionPane.showMessageDialog(null, "读取数据过程中未获取到有效数据！请检查设备或程序！", "错误", JOptionPane.INFORMATION_MESSAGE);
                                System.exit(0);
                            }
                            else {
                            	
                                String dataOriginal = new String(data);    //将字节数组数据转换位为保存了原始数据的字符串
                                commStr.append(dataOriginal);//存放数据
                                String dataValid = "";    //有效数据（用来保存原始数据字符串去除最开头*号以后的字符串）
                                String[] elements = null;    //用来保存按空格拆分原始字符串后得到的字符串数组    
                                //解析数据
                                if (commStr.toString().indexOf("*")>=0 && commStr.toString().indexOf("@")>=0) {    //当数据的第一个字符是*号，并且字符串有还有@ 时表示数据接收完成，开始解析                            
                                    int start=commStr.indexOf("*");
                                    int len=commStr.indexOf("@");
                                    if(len>commStr.length()){
                                    	len=commStr.length();
                                    }
                                    if(start>=len){
                                    	start=0;
                                    }
                                    index++;
                                    if(commStr.length()<len){
                                    	dataValid=commStr.substring(start+1,commStr.length()-1);
                                    }else{
                                    	dataValid=commStr.substring(start+1,len);
                                    }
                                    commStr.replace(start, len+1, "");
                                    elements = dataValid.split("\\r");
                                    if (elements == null || elements.length < 1) {    //检查数据是否解析正确
                                        JOptionPane.showMessageDialog(null, "数据解析过程出错，请检查设备或程序！", "错误", JOptionPane.INFORMATION_MESSAGE);
                                        System.exit(0);
                                    }
                                    else {
                                        try {
                                            barCode.setText(elements[0] );
                                            orderCode.setText(elements[1] );
                                            if(elements[0].indexOf("NG")!=-1 || elements[1].indexOf("NG")!=-1){
                                            	 //失败
	                                        	   InputStream in = new FileInputStream (failfile); // 打 开 一 个 声 音 文 件 流 作 为 输 入
	                                        	   AudioStream as = new AudioStream (in); // 用 输 入 流 创 建 一 个AudioStream 对 象 
	                                        	   AudioPlayer.player.start (as); //“player” 是AudioPlayer 中 一 静 态 成 员 用 于 控 制 播 放 
	                                        	   status.setText("条码问题!");
	                                        	   status.setBackground(Color.red);
                                            }else{
                                            //取得数据成功，调用后台数据
	                                            String tempUrl=url+"check/checkTrade_new?stock=1&orderCode="+elements[1]+"&barCode="+elements[0];
	                                            int k=0;
	                                           String resultStr= HttpClientUtils.httpPost(tempUrl, "");
	                                           String result=new String(resultStr.getBytes(),"gbk");
	                                           System.err.println(tempUrl);
	                                           System.err.println(elements[0]+"|"+elements[1]);
	                                           System.err.println("result"+result);
	                                           JSONObject json=new JSONObject(resultStr);
	                                           String code=json.getString("code");
	                                           String message=json.getString("msg");
	                                           if(code!=null && code.equals("200")){
	                                        	   //验货成功
	                                        	   successIn =new FileInputStream (successfile); // 打 开 一 个 声 音 文 件 流 作 为 输 入
	                                          	 successAs =new AudioStream (successIn); // 用 输 入 流 创 建 一 个AudioStream 对 象 
	                                        	   status.setText("OK");
	                                        	   status.setBackground(Color.green);
	                                        	   AudioPlayer.player.start (successAs); //“player” 是AudioPlayer 中 一 静 态 成 员 用 于 控 制 播 放 
	                                        	   k=0;
	                                           }else if(code!=null && code.equals("406")){
	                                        	   //拦截出库
	                                        	   lanjieIn=new FileInputStream(lanjiefile);
	                                               lanjieAs=new AudioStream(lanjieIn);
	                                        	   AudioPlayer.player.start (lanjieAs); //“player” 是AudioPlayer 中 一 静 态 成 员 用 于 控 制 播 放 
	                                        	   status.setText(message);
	                                        	   status.setBackground(Color.red);
	                                        	   msgList.add(orderCode.getText()+" 拦截出库 \r\n");
	                                        	  
	                                        	   k=1;
	                                           }else if(code!=null && code.equals("405")){
	                                        	   //重复扫描
	                                        	   chongIn=new FileInputStream(chongfile);
	                                               chongAs=new AudioStream(chongIn);
	                                        	   AudioPlayer.player.start (chongAs); //“player” 是AudioPlayer 中 一 静 态 成 员 用 于 控 制 播 放 
	                                        	   status.setText(""+message);
	                                        	   status.setBackground(Color.red);
	                                        	   msgList.add(orderCode.getText()+" 重复扫描\r\n");
	                                        	   k=1;
	                                           }else{
	                                        	   //失败
	                                        	   failIn=new FileInputStream(failfile);
	                                               failAs=new AudioStream(failIn);
	                                        	   AudioPlayer.player.start (failAs); //“player” 是AudioPlayer 中 一 静 态 成 员 用 于 控 制 播 放 
	                                        	   status.setText(""+message);
	                                        	   status.setBackground(Color.red);
	                                        	   msgList.add(orderCode.getText()+" "+message.substring(message.length()-15)+"  \r\n");
	                                        	   k=1;
	                                           }
	                                           if(k==1){
	                                        	   StringBuffer msg=new StringBuffer();
	                                        	   txtDisplay.setText("");
	                                        	   for(int i=msgList.size()-1;i>0;i--){
	                                        		   msg.append(msgList.get(i));
	                                        		   k++;
	                                        		   if(k>6){
	                                        			   break;
	                                        		   }
	                                        	   }
	                                        	   txtDisplay.setText(msg.toString());
	                                           }
                                            }
                                        } catch (ArrayIndexOutOfBoundsException e) {
                                        	e.printStackTrace();
                                          //  JOptionPane.showMessageDialog(null, "数据解析过程出错，更新界面数据失败！请检查设备或程序！", "错误", JOptionPane.INFORMATION_MESSAGE);
                                            System.exit(0);
                                        }
                                    }    
                                }
                            }
                            
                        }                        
                        
                    } catch (Exception e) {
                    	e.printStackTrace();
                        JOptionPane.showMessageDialog(null, e, "错误", JOptionPane.INFORMATION_MESSAGE);
                        System.exit(0);    //发生读取错误时显示错误信息后退出系统
                    }    
                    
                    break;
    
            }

        }
    }    
    public static void main(String[] args) throws Exception {
    	 
    	InputStream in = new FileInputStream ("D:\\sound\\success.wav"); // 打 开 一 个 声 音 文 件 流 作 为 输 入
    	AudioStream as = new AudioStream (in); // 用 输 入 流 创 建 一 个AudioStream 对 象 
    	AudioData data = as.getData (); // 创 建AudioData 源 
    	ContinuousAudioDataStream cas = new ContinuousAudioDataStream (data);
    	for(int i=0;i<=10;i++){
    	
 	  
 		  AudioPlayer.player.start(cas); //“player” 是AudioPlayer 中 一 静 态 成 员 用 于 控 制 播 放
 		  Thread.sleep(1000);
 		 AudioPlayer.player.stop (cas);
 	  }
 	  // 
    	// String resultStr= HttpClientUtils.httpPost("http://localhost:9090/check/checkTrade_new"+"?orderCode=1212&barCode=223", "");
    	 //System.err.println(resultStr);
	
   
    }
}