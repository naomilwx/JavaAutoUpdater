package htlauncher.updater;

public class DownloadProgress {
	private long totalDownloadBytes;
	private long bytesDownloaded;
	private boolean downloadCompleted;
	private boolean downloadSuccess;
	
	public DownloadProgress(){
		downloadSuccess = true;
		downloadCompleted = false;
	}
	
	public long getTotalDownloadBytes(){
		return totalDownloadBytes;
	}
	
	public void setTotalDownloadBytes(long bytes){
		this.totalDownloadBytes = bytes;
	}
	
	public long getBytesDownloaded(){
		return bytesDownloaded;
	}
	
	public void setBytesDownloaded(long bytes){
		this.bytesDownloaded = bytes;
	}
	
	public boolean getDownloadCompleted(){
		return downloadCompleted;
	}
	
	public void setDownloadCompleted(boolean completed){
		this.downloadCompleted = completed;
	}
	
	public boolean getDownloadSuccess(){
		return downloadSuccess;
	}
	
	public void setDownloadSuccess(boolean success){
		this.downloadSuccess = success;
	}
	
	public double getDownloadedPercent(){
		return 100.0 * bytesDownloaded /totalDownloadBytes;
	}
}
