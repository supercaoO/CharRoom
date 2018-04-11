package com.zhr.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.zhr.closeable.CloseableUtils;

public class ClientThread implements Runnable {
	private DataInputStream is;
	private DataOutputStream os;
	private boolean error;
	private String userName;
	
	public DataOutputStream getOs() {
		return os;
	}

	public ClientThread(Socket client) {
		try {
			is = new DataInputStream(new BufferedInputStream(client.getInputStream()));
			os = new DataOutputStream(new BufferedOutputStream(client.getOutputStream()));
			userName = is.readUTF();
			sendOthers(this.userName + "进入了群聊");
		} catch (IOException e) {
			errDo();
		}
	}
	
	public String getUserName() {
		return userName;
	}

	private void errDo() {
		error = true;
		CloseableUtils.closeAll(os, is);
	}
	
	private void recAndSend() {
		String rec;
		try {
			rec = is.readUTF();
			System.out.println("服务器收到来自客户端的信息：" + rec);
			if(rec.startsWith("@") && rec.indexOf(":") > 1) {
				String recer = rec.substring(1, rec.indexOf(":"));
				if(Server.getClients().containsKey(recer));
					sendOne(rec.substring(rec.indexOf(":") + 1), Server.getClients().get(recer));
			}
			else
				sendOthers(userName + "说：" + rec);
		} catch (IOException e) {
			errDo();
		}
	}
	
	private void sendOne(String rec, ClientThread c) {
		try {
			c.getOs().writeUTF(this.userName + "单独对你说：" +rec);
			c.getOs().flush();
		} catch (IOException e) {
			errDo();
		}
	}
	
	private void sendOthers(String rec) {
		Server.getClients().forEach((k, v)->{
			try {
				if(k != this.getUserName()) {
					v.getOs().writeUTF(rec);
					v.getOs().flush();
				}
			} catch (IOException e) {
			errDo();
			}
		});
	}
	
	@Override
	public void run() {
		while(!error) {
			recAndSend();
		}
	}
	
}
