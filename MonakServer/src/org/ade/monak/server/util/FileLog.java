package org.ade.monak.server.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileLog {
	public static void writeLog(String log){
		File file = new File("/MonakServerLog.txt");
	    FileOutputStream fos;
		try {
			fos = new FileOutputStream(file, true);
			fos.write(log.getBytes());
			fos.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String readLog(){
		String log = "";
		File file = new File("/MonakServerLog.txt");
	    FileInputStream fis;
		try {
			fis = new FileInputStream(file);
			int read;
			while((read = fis.read())>-1){
				log += (char)read;
			}
			fis.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return log;
	}
	
	public static void clearLog(){
		File file = new File("/MonakServerLog.txt");
		file.delete();
	}
	
}
