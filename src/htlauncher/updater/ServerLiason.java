package htlauncher.updater;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URLConnection;

public class ServerLiason {
	public static final int BUFFER_SIZE = 2048;
	public void downloadFile(URI source, URI destination){
		BufferedInputStream buffInput = null;
		BufferedOutputStream buffOut = null;
		File destFile = null;
		try {
			URLConnection connection = source.toURL().openConnection();
			buffInput = new BufferedInputStream(connection.getInputStream());
			
			destFile = new File(destination);
			destFile.createNewFile();
			buffOut = new BufferedOutputStream(new FileOutputStream(destFile));
			byte[] buff = new byte[ServerLiason.BUFFER_SIZE];
			int bytesRead = 0;
			int totalBytes = 0;
			while((bytesRead = buffInput.read(buff)) > 0){
				totalBytes += bytesRead;
				buffOut.write(buff, 0, bytesRead);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			
			try {
				if(buffInput != null){
					buffInput.close();
				}
				if(buffOut != null){
					buffOut.flush();
					buffOut.close();
				}
				
			} catch (IOException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();				
			}
			
		}
	}
}
