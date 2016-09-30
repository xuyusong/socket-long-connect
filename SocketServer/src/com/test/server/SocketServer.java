package com.test.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class SocketServer {

	BufferedWriter writer = null;
	BufferedReader bufferedReader = null;
	public static void main(String[] args) {
		SocketServer socketServer = new SocketServer();
		socketServer.run();
	}
	public void run(){
		ServerSocket serverSocket = null;
		Socket socket = null;
		try {
			serverSocket = new ServerSocket(9999);
			System.out.println("server start,waiting for a socket connect");
			//阻塞  直到客户端有链接进入 返回一个socket对象
			while (true) {
				socket =serverSocket.accept();
				connectManage(socket);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public void connectManage(final Socket socket){
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println("a socket connect :"+socket.hashCode());
					bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
					new Timer().schedule(new TimerTask() {
						@Override
						public void run() {
							System.out.println("heart beat once");
							try {
								writer.write("heart beat once\n");
								writer.flush();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}, 3000, 3000);
					String receivedMsg = null;
					while ((receivedMsg=bufferedReader.readLine())!=null) {
						System.out.println(receivedMsg);
						writer.write("server have received "+socket.hashCode()+" massge "+receivedMsg+"\n");
						writer.flush();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}finally{
					try {
						bufferedReader.close();
						socket.close();
						writer.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
}
