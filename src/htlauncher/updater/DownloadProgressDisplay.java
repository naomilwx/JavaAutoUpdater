package htlauncher.updater;

import java.util.Timer;
import java.util.TimerTask;

import htlauncher.updater.ui.UpdateProgressWindow;

public class DownloadProgressDisplay {
	//TODO: show update by time interval instead 
	private static final String PROGRESS_DISPLAY_TEXT_FORMAT = "Downloading component: %1s";
	private static final long UPDATE_PERIOD = 1000;
	
	private UpdateProgressWindow progressWindow;
	private DownloadProgress progress = null;
	private Timer updateTimer;

	public DownloadProgressDisplay(){
		progressWindow = new UpdateProgressWindow();
	}
	
	public void showProgressWindow(){
		progressWindow.setVisible(true);
	}
	
	public void hideProgressWindow(){
		progressWindow.setVisible(false);
	}
	
	public void updateDownloadingComponent(String component){
		progressWindow.setDisplayedText(String.format(PROGRESS_DISPLAY_TEXT_FORMAT, component));
	}
	
	public void startProgressDisplay(DownloadProgress progress){
		this.progress = progress;
		startDisplayUpdate();
	}
	
	private void startDisplayUpdate(){
		if(updateTimer != null){
			stopDisplayUpdate();
		}
		updateTimer = new Timer();
		TimerTask updateTask = new TimerTask(){
			@Override
			public void run(){
				progressWindow.setProgress((int)progress.getDownloadedPercent());
				if(progress.getDownloadCompleted()){
					stopDisplayUpdate();
				}
			}
		};
		updateTimer.scheduleAtFixedRate(updateTask, 0, UPDATE_PERIOD);
	}
	
	private void stopDisplayUpdate(){
		updateTimer.cancel();
		updateTimer = null;
	}
}
