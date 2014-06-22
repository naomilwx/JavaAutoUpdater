package htlauncher.updater;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map.Entry;

public class FileDownloader {
	public static final int BUFFER_SIZE = 2048;
	public static final int CONNECTION_TIMEOUT = 15000;
	public static final int READ_CONNECTION_TIMEOUT = 30000;
	
	private HashMap<String, String> backups;
	
	public FileDownloader(){
		backups = new HashMap<String, String>();
	}
	
	private BufferedInputStream setupStreamFromSource(URI source, DownloadProgress progress){
		BufferedInputStream buffInput = null;
		URLConnection connection;
		try {
			connection = source.toURL().openConnection();
			connection.setConnectTimeout(CONNECTION_TIMEOUT);
			connection.setReadTimeout(READ_CONNECTION_TIMEOUT);
			progress.setTotalDownloadBytes(connection.getContentLengthLong());
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
			if(destFile.exists()){
				createBackUp(destFile);
			}
			destFile.createNewFile();
			buffOut = new BufferedOutputStream(new FileOutputStream(destFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			progress.setDownloadSuccess(false);
		}
		return buffOut;
	}
	
	protected void rollBack(){
		for(Entry<String, String> entry: backups.entrySet()){
			String backupPath = entry.getValue();
			String originalPath = entry.getKey();
			moveFile(backupPath, originalPath);
		}
	}
	
	protected void removeBackups(){
		for(String backupPath: backups.values()){
			File backupFile = new File(backupPath);
			if(backupFile.exists()){
				backupFile.delete();
			}
		}
		backups = new HashMap<String, String>();
	}
	
	private void moveFile(String source, String dest){
		try {
			Files.move(Paths.get(source), Paths.get(dest), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void createBackUp(File original){
		String originalPath = original.getPath();
		String backupPath = originalPath + getBackupSuffix();
		moveFile(originalPath, backupPath);
		backups.put(originalPath, backupPath);
	}
	
	//Generates new sufix for backup file
	private String getBackupSuffix(){
		return "backup" + System.currentTimeMillis() ;
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
