package com.zhr.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {
	private static Map<String, ClientThread> clients = new HashMap<>();
	
	public static Map<String, ClientThread> getClients() {
		return clients;
	}

	public static void main(String[] args) {
		ServerSocket ser;
		try {
			ser = new ServerSocket(8888);
			System.out.println("服务器启动成功");
			while(true) {
				Socket client = ser.accept();
				ClientThread c = new ClientThread(client);
				clients.put(c.getUserName(), c);
				new Thread(c).start();
			}
		} catch (IOException e) {
			System.out.println("服务器启动失败");
			e.printStackTrace();
		}
		
	}
}