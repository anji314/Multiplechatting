package multichat_1;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;


public class multichatS extends Frame{
	TextArea display;
	Label info;
	String clientdata="";
	String serverdata="";
	List<ServerThread> list;
	public ServerThread SThread;
	
	public multichatS() {
		super("서버");
		info=new Label();
		add(info,BorderLayout.CENTER);
		display= new TextArea("",0,0,TextArea.SCROLLBARS_VERTICAL_ONLY);
		display.setEditable(false);
		add(display,BorderLayout.SOUTH);
		addWindowListener(new WinListener());
		setSize(500,250);
		setVisible(true);
		
	}
	
	class WinListener extends WindowAdapter{
		public void windowClosing(WindowEvent e) {
			System.exit(0);
		}
	}
	public void runServer() {
		ServerSocket server;
		Socket sock;
		ServerThread SThread;
		try {
			list= new ArrayList<ServerThread>();
			server =new ServerSocket(5000,100);
			
			try {
				while(true) {
					sock=server.accept();
					SThread =new ServerThread(this,sock,display,info);
					SThread.start();
					info.setText(sock.getInetAddress().getHostName()+" 서버는 클라이언트와 연결됨.");
					
				}
			}catch(IOException ioe) {
				server.close();
				ioe.printStackTrace();
			}
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
		
	}
	
	public static void main(String args[]) {
		multichatS s=new multichatS();
		s.runServer();
	}
	
	
}

class ServerThread extends Thread{
	Socket sock;
	//InputStream is;
	//InputStreamReader isr;
	BufferedReader input;
	//OutputStream os;
	//OutputStreamWriter osw;
	BufferedWriter output;
	TextArea display;
	Label info;
	TextField text;
	String serverdata="";
	multichatS cs;
	
	private static final String SEPARATOR="|";
	private static final int REQ_LOGON=1001;
	private static final int REQ_SENDWORDS=1021;
	
	
	public ServerThread(multichatS c,Socket s,TextArea ta,Label l) {
		sock= s;
		display=ta;
		info=l;
		cs=c;
		try {
			//is =sock.getInputStream();
			//isr= new InputStreamReader(is);
			input =new BufferedReader(new InputStreamReader(sock.getInputStream()));
			//os=sock.getOutputStream();
			//osw=new OutputStreamWriter(os);
			output =new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
			
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public void run() {
		cs.list.add(this);
		String clientdata;
		try {
			while((clientdata=input.readLine())!=null) {
				StringTokenizer st=new StringTokenizer(clientdata,SEPARATOR);
				int command= Integer.parseInt(st.nextToken());
				int cnt=cs.list.size();
				switch (command) {
				case REQ_LOGON:// 이부분이 사용자 닉네임 받는 기능
					String ID1=st.nextToken();
					display.append("클라이언트가 "+ID1+" (으)로 로그인 하였습니다.\r\n");
					break;
				case REQ_SENDWORDS:
					String ID2=st.nextToken();
					String massage=st.nextToken();
					display.append(ID2+" : "+massage+"\r\n");
					for(int i=0;i<cnt;i++) {
						ServerThread SThread =(ServerThread)cs.list.get(i);
						SThread.output.write(ID2+" : "+massage+"\r\n");
						SThread.output.flush();
					}
					break;

				default:
					break;
				}				
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
		cs.list.remove(this);
		try {
			sock.close();
		}catch(IOException ea) {
			ea.printStackTrace();
		}
	}
	
}
