package htlauncher.updater;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URLConnection;

public class FileDownloader {
	public static final int BUFFER_SIZE = 2048;
	
	private BufferedInputStream setupStreamFromSource(URI source, DownloadProgress progress){
		BufferedInputStream buffInput = null;
		URLConnection connection;
		try {
			connection = source.toURL().openConnection();
			progress.setTotalDownloadBytes(connection.getContentLength());
			buffInput = new BufferedInputStream(connection.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			progress.setDownloadSuccess(false);
		}
		return buffInput;
	}
	
	private BufferedOutputStream setupStreamToDestination(URI destination, DownloadProgress progress){
		BufferedOutputStream buffOut = null;
		try {
			File destFile = new File(destination.toString());
			destFile.createNewFile();
			buffOut = new BufferedOutputStream(new FileOutputStream(destFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			progress.setDownloadSuccess(false);
		}
		return buffOut;
	}
	
	private void download(BufferedInputStream buffInput, BufferedOutputStream buffOut, DownloadProgress progress) throws IOException{
		byte[] buff = new byte[FileDownloader.BUFFER_SIZE];
		
		int bytesRead = 0;
		int totalBytesRead = 0;
		while((bytesRead = buffInput.read(buff)) > 0){
			totalBytesRead += bytesRead;
			progress.setBytesDownloaded(totalBytesRead);
			buffOut.write(buff, 0, bytesRead);
		}
	}
	
	
	private void closeIOStreams(BufferedInputStream buffInput, BufferedOutputStream buffOut){
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
	
	public void downloadFile(URI source, URI destination, DownloadProgress progress){
		BufferedInputStream buffInput = null;
		BufferedOutputStream buffOut = null;
		try {
			buffInput = setupStreamFromSource(source, progress);
			buffOut = setupStreamToDestination(destination, progress);
			
			download(buffInput, buffOut, progress);
			progress.setDownloadCompleted(true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			progress.setDownloadSuccess(false);
		} finally{
			closeIOStreams(buffInput, buffOut);
		}
	}
	
}
