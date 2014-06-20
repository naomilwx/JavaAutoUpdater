package htlauncher.updater;


import htlauncher.updater.ui.UpdateProgressWindow;
import htlauncher.utilities.ComponentDescriptor;

import java.net.URI;
import java.net.URISyntaxException;

public class UpdateManager {
	private static final String PROGRESS_DISPLAY_TEXT_FORMAT = "Downloading component: %1s";
	
	private UpdateDataManager dataManager;
	private FileDownloader server;
	private URI appInfoURI;
	private UpdateProgressWindow progressWindow;
	
	public UpdateManager(String appInfoPath){
		try {
			setupComponents(appInfoPath);
			this.appInfoURI = new URI(appInfoPath);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setupComponents(String appPath){
		progressWindow = new UpdateProgressWindow();
		progressWindow.setWindowVisibility(true);
		dataManager = new UpdateDataManager(appPath);
		server = new FileDownloader();
	}
	
	public void runUpdate(){
		updateAppDetails();
		updateAppComponents();
	}
	
	public void updateAppDetails(){
		URI serverURI = dataManager.serverAppInfoURI;
		if(serverURI != null && appInfoURI != null){
			//overwrite with file from server
			server.downloadFile(serverURI, appInfoURI);
		}
		dataManager.loadAppData();
	}
	
	public void updateAppComponents(){
		for(ComponentDescriptor component: dataManager.getAppComponents()){
			updateComponent(component);
		}
		dataManager.saveUpdaterData();
	}
	
	public void updateComponent(ComponentDescriptor component){
		String name = component.getComponentName();
		double latestVer = component.getVersion();
		double currentVer = dataManager.getDownloadedVersion(name);
		if(latestVer > currentVer){
			progressWindow.setDisplayedText(String.format(PROGRESS_DISPLAY_TEXT_FORMAT, component.getComponentName()));
			//update jar for component from server
			server.downloadFile(component.getServerURI(), component.getLocalURI());
			//update success: update downloaded component version
			dataManager.updateDownloadedVersion(name, latestVer);
		}
	}
	
	public URI getAppLaunchPath(){
		return dataManager.getAppLaunchPath();
	}
	
}
