package htlauncher.updater;


import htlauncher.utilities.ComponentDescriptor;
import htlauncher.utilities.Utilities;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class UpdateManager {	
	private UpdateDataManager dataManager;
	private FileDownloader downloader;
	private URI appInfoURI;
	private DownloadProgressDisplay downloadProgress;
	private boolean applicationUpdated = false;
	
	public UpdateManager(String appInfoPath) throws URISyntaxException{
		setupComponents(appInfoPath);
		this.appInfoURI = new URI(appInfoPath);
	}
	
	public void setupComponents(String appPath){
		downloadProgress = new DownloadProgressDisplay();
		dataManager = new UpdateDataManager(appPath);
		downloader = new FileDownloader();
	}
	
	public void runUpdate(boolean firstRun){
		applicationUpdated = false;
		if(checkServerConnection()){
			if(firstRun){
				downloadProgress.showProgressWindow();
			}
			runRequiredUpdate();
			if(firstRun){
				downloadProgress.hideProgressWindow();
				dataManager.moveLastDownload();
			}else{
				if(applicationUpdated == true){
					Utilities.showMessage("Application updated", 
							dataManager.getAppName() +" has been successfully updated. Restart application to get the latest update.");
				}
			}
		}
	}
	
	private void runRequiredUpdate(){
		updateAppDetails();
		updateAppComponents();
	}
	
	public void updateAppDetails(){
		URI serverURI = dataManager.getServerAppInfoURI();
		//overwrite with file from server
		boolean success = startDownload(serverURI, appInfoURI, true);
		if(success){
			dataManager.loadAppData();
		}else{
			downloader.rollBack();
		}
	}
	
	public void updateAppComponents(){
		boolean success = true;
		for(ComponentDescriptor component: dataManager.getAppComponents()){
			success = updateComponent(component);
			if(!success){
				break;
			}
		}
		if(success){
			dataManager.saveUpdaterData();
			downloader.removeBackups();
		}else{
			downloader.rollBack();
			applicationUpdated = false;
		}
	}
	
	public boolean updateComponent(ComponentDescriptor component){
		String name = component.getComponentName();
		double latestVer = component.getVersion();
		double currentVer = dataManager.getDownloadedVersion(name);
		boolean success = true;
		if(latestVer > currentVer){
			downloadProgress.updateDownloadingComponent(component.getComponentName());
			//update jar for component from server
			String compath = UpdateDataManager.UPDATE_FOLDER + component.getLocalURI().toString();
			URI dlURI;
			try {
				dlURI = new URI(compath);
			} catch (URISyntaxException e) {
				e.printStackTrace();
				return false;
			}
			success = startDownload(component.getServerURI(), dlURI, true);
			if(success){
				//update success: update downloaded component version
				dataManager.updateDownloadedVersion(name, latestVer);
				applicationUpdated = true;
			}
		}
		return success;
	}
	
	public String getAppLaunchPath(){
		return dataManager.getAppLaunchPath();
	}
	
	
	private boolean startDownload(URI source, URI dest, boolean showProgress){
		DownloadProgress progress = new DownloadProgress();
		if(showProgress){
			downloadProgress.startProgressDisplay(progress);
		}
		downloader.downloadFile(source, dest, progress);
		return progress.getDownloadSuccess();
	}
	
	private boolean checkServerConnection(){
		try {
			String serverPath = dataManager.getServerAppInfoURI().getHost();
			if(serverPath == null){
				throw new MalformedURLException();
			}
			URL serverURL = new URL ("http", serverPath, 80, "");
			serverURL.openConnection().connect();
			return true;
		} catch (MalformedURLException e) {
			e.printStackTrace();
			Utilities.showError("Cache Corrupted", "The application launcher's cache has been corrupted! Please delete "+ UpdateDataManager.UPDATER_INFO_FILEPATH);
			return false;
		} catch (IOException e) {
			return false;
		}
		
	}
	
}
