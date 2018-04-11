package com.zhr.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
	public static void main(String[] args) {
		Socket client;
		try {
			client = new Socket("localhost", 8888);
			String userName = signIn();
			Send s = new Send(client, userName);
			Rec r = new Rec(client, userName);
			new Thread(s).start();
			new Thread(r).start();
		} catch (UnknownHostException e) {
			System.out.println("找不到服务器");
		} catch (IOException e) {
			System.out.println("与服务器连接失败");
		} catch (Exception e) {
			System.out.println("登录名异常");
		}
	}
	
	public static String signIn() throws Exception {
		System.out.print("请输入登录名:");
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		String userName = in.nextLine();
		if(userName == null || userName.length() == 0)
			throw new Exception();
		return userName;
	}
}