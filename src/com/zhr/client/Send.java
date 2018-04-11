package com.zhr.client;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import com.zhr.closeable.CloseableUtils;

public class Send implements Runnable {
	private BufferedReader console;
	private DataOutputStream os;
	private boolean error = false;
	private String userName;
	
	public Send() {
		console = new BufferedReader(new InputStreamReader(System.in));
	}
	
	public Send(Socket client, String userName) {
		this();
		try {
			os = new DataOutputStream(new BufferedOutputStream(client.getOutputStream()));
			this.userName = userName;
			sendSignMsg();
			send(userName);
		} catch (IOException e) {
			errDo();
		}
	}
	
	private void errDo() {
		error = true;
		CloseableUtils.closeAll(os, console);
	}
	
	private void sendSignMsg() {
		System.out.println("»¶Ó­µÇÂ¼");
	}
	
	private void send(String msg) {
		try {
			os.writeUTF(msg);
			os.flush();
		} catch (IOException e) {
			errDo();
		}
	}
	
	@Override
	public void run() {
		while(!error) {
			try {
				String msg = console.readLine();
				send(msg);
			} catch (IOException e) {
				errDo();
			}
		}
	}
	
}
