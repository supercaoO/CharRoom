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
			System.out.println("�Ҳ���������");
		} catch (IOException e) {
			System.out.println("�����������ʧ��");
		} catch (Exception e) {
			System.out.println("��¼���쳣");
		}
	}
	
	public static String signIn() throws Exception {
		System.out.print("�������¼��:");
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		String userName = in.nextLine();
		if(userName == null || userName.length() == 0)
			throw new Exception();
		return userName;
	}
}