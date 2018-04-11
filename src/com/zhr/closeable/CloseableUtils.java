package com.zhr.closeable;

import java.io.Closeable;
import java.io.IOException;

public class CloseableUtils {
	public static void closeAll(Closeable...io) {
		try {
			for(Closeable c : io) {
				if(c != null)
					c.close();
			}
		} catch (IOException e) {
			System.out.println("×ÊÔ´¹Ø±ÕÊ§°Ü");
			e.printStackTrace();
		}
	}
}
