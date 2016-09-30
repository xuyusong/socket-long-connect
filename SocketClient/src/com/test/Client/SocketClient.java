package com.test.Client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class SocketClient {

	public static void main(String[] args) {
		SocketClient socket = new SocketClient();
		socket.run();
	}
	public void run(){
		BufferedReader reader =null;
		BufferedWriter writer = null;
		BufferedReader inputReader =null;
		Socket socket = null;
		try {
			socket = new Socket("127.0.0.1",9999);
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			reader= new BufferedReader(new InputStreamReader(System.in));
			inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			startServerReply(inputReader);
			String content;
			String receivedMsg ;
			while(!(content = reader.readLine()).equals("bye")){
				writer.write(content+"\n");
				//System.out.println(content);
				writer.flush();
//				receivedMsg=inputReader.readLine();
//				System.out.println(receivedMsg);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				writer.close();
				reader.close();
				socket.close();
				inputReader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	public void startServerReply(final BufferedReader reader){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				String response;
				try {
//					response = reader.readLine();
//					System.out.println("long connect:"+response);
					while((response = reader.readLine())!=null){
						System.out.println("long connect:"+response);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}finally{
					try {
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
		
	}

}
