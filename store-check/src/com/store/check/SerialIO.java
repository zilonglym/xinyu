package com.store.check;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

public class SerialIO
{
	private SerialPort serialPort = null;	//串口对象
	private InputStream is = null;	//输入�?
	private OutputStream os = null;	//输出�?
	
	private int datebits = DATABITS_8;	//默认8位数据位
	private int stopbits = STOPBITS_1;	//默认1位停止位
	private int parity = PARITY_NONE;	//默认无校验位
	
	public final static int DATABITS_5 = SerialPort.DATABITS_5;
	public final static int DATABITS_6 = SerialPort.DATABITS_6;
	public final static int DATABITS_7 = SerialPort.DATABITS_7;
	public final static int DATABITS_8 = SerialPort.DATABITS_8;
	
	public final static int STOPBITS_1 = SerialPort.STOPBITS_1;
	public final static int STOPBITS_1_5 = SerialPort.STOPBITS_1_5;
	public final static int STOPBITS_2 = SerialPort.STOPBITS_2;
	
	public final static int PARITY_EVEN = SerialPort.PARITY_EVEN;
	public final static int PARITY_MARK = SerialPort.PARITY_MARK;
	public final static int PARITY_NONE = SerialPort.PARITY_NONE;
	public final static int PARITY_ODD = SerialPort.PARITY_ODD;
	public final static int PARITY_SPACE = SerialPort.PARITY_SPACE;
	
	/**
	 * 设置数据�?
	 * @param databits
	 * DATABITS_5<br>
	 * DATABITS_6<br>
	 * DATABITS_7<br>
	 * DATABITS_8
	 */
	public void setDatabits(int databits)
	{
		this.datebits = databits;
	}
	
	/**
	 * 设置停止�?
	 * @param stopbits
	 * STOPBITS_1<br>
	 * STOPBITS_1_5<br>
	 * STOPBITS_2
	 */
	public void setStopbits(int stopbits)
	{
		this.stopbits = stopbits;
	}
	
	/**
	 * 设置校验�?
	 * @param parity
	 * PARITY_EVEN<br>
	 * PARITY_MARK<br>
	 * PARITY_NONE<br>
	 * PARITY_ODD<br>
	 * PARITY_SPACE
	 */
	public void setParity(int parity)
	{
		this.parity = parity;
	}
	
	/**
	 * 打开串口
	 * @param com	串口�?
	 * @param baud	波特�?
	 * @return
	 * true - 成功<br>
	 * false - 失败
	 */
	public boolean open(int com, int baud)
	{
		if ( !isPortBaudSupported(baud) )
		{
			System.out.println("暂不支持的波特率");
			return false;
		}
		
		try
		{
			CommPortIdentifier portId = CommPortIdentifier.getPortIdentifier("COM" + com);
			serialPort = (SerialPort)portId.open("DeviceTestSystem", 0);	 //立即返回
			serialPort.setSerialPortParams(baud, datebits, stopbits, parity);//设置串口波特�?数据�?停止�?校验�?
			is = serialPort.getInputStream();
			os = serialPort.getOutputStream();
		}
		catch(NoSuchPortException nspe)
		{
			nspe.printStackTrace();
			return false;
		}
		catch(PortInUseException piue)
		{
			piue.printStackTrace();
			return false;
		}
		catch(UnsupportedCommOperationException ucoe)
		{
			ucoe.printStackTrace();
			return false;
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	/**
	 * 设置串口超时时间
	 * @param timeout	超时时间（秒），若为-1则永不超�?
	 * @return
	 * true - 成功<br>
	 * false - 失败
	 */
	public boolean setReceiveTimeout(int timeout)
	{
		if ( serialPort == null )
		{
			System.out.println("尚未初始化SerialPort");
			return false;
		}
		
		try
		{
			if ( timeout < -1 )
			{
				return false;
			}
			else if ( timeout == -1 )
			{
				serialPort.disableReceiveTimeout();
			}
			else
			{
				serialPort.enableReceiveTimeout(1000*timeout);
			}
		}
		catch(UnsupportedCommOperationException ucoe)
		{
			ucoe.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	/**
	 * 读数�?
	 * @return	当前的字节，如果�?1，代表已读到末尾或�?超时，如果为-2，代表读取异�?
	 */
	public int read()
	{
		if ( is == null )
		{
			System.out.println("尚未初始化InputStream");
			return -2;
		}
		
		try
		{
			return is.read();
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
			return -2;
		}
	}
	
	/**
	 * 读数�?
	 * @param readByte 存放读取数据的字节数�?
	 * @return	总共读取的字节数，如果为0，代表超时，如果�?1，代表已读到末尾，如果为-2，代表读取异�?
	 */
	public int read(byte[] readByte)
	{
		if ( is == null )
		{
			System.out.println("尚未初始化InputStream");
			return -2;
		}
		
		try
		{
			return is.read(readByte);
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
			return -2;
		}
	}
	
	/**
	 * 写数�?
	 * @param writeByte	存放写入数据的字节数�?
	 * @return
	 * true - 成功<br>
	 * false - 失败
	 */
	public boolean write(byte[] writeByte)
	{
		if ( os == null )
		{
			System.out.println("尚未初始化OutputStream");
			return false;
		}
		
		try
		{
			os.write(writeByte);
			os.flush();
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	/**
	 * 关闭串口
	 * @return
	 * true - 成功<br>
	 * false - 失败
	 */
	public boolean close()
	{
		if ( serialPort != null )
		{
			if ( is != null )
			{
				try
				{
					is.close();
				}
				catch(IOException ioe)
				{
					ioe.printStackTrace();
					return false;
				}
			}
			
			if ( os != null )
			{
				try
				{
					os.close();
				}
				catch(IOException ioe)
				{
					ioe.printStackTrace();
					return false;
				}
			}
			
			serialPort.close();
			serialPort = null;
			return true;
		}
		
		return true;
	}
	
	/*
	 * �?��波特率是否支�?
	 * @param portBaud	波特�?
	 */
	private boolean isPortBaudSupported(int baud)
	{
		int[] supportedPortBaudList = {300, 600, 1200, 2400, 4800, 9600, 19200, 38400, 57600, 115200};	//支持的波特率
		for ( int i = 0; i < supportedPortBaudList.length; i++ )
		{
			if ( baud == supportedPortBaudList[i] )
			{
				return true;
			}
		}
		
		return false;
	}
}