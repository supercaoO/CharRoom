package com.zhr.client;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import com.zhr.closeable.CloseableUtils;

public class Rec implements Runnable {
	private DataInputStream is;
	private boolean error;
	private String userName;
	
	public Rec(Socket client, String userName) {
		try {
			is = new DataInputStream(new BufferedInputStream(client.getInputStream()));
			this.userName = userName;
		} catch (IOException e) {
			errDo();
		}
	}
	
	private void errDo() {
		error = true;
		CloseableUtils.closeAll(is);
	}
	
	private void rec() {
		String rec;
		try {
			rec = is.readUTF();
			System.out.println(rec);
		} catch (IOException e) {
			errDo();
		}
	}
	
	@Override
	public void run() {
		while(!error) {
			rec();
		}
	}

}
