package htlauncher.updater;


import htlauncher.utilities.ComponentDescriptor;

import java.net.URI;
import java.net.URISyntaxException;

public class UpdateManager {
	
	private UpdateDataManager dataManager;
	private ServerLiason server;
	private URI appInfoURI;
	
	public UpdateManager(String appInfoPath){
		dataManager = new UpdateDataManager(appInfoPath);
		server = new ServerLiason();
		try {
			this.appInfoURI = new URI(appInfoPath);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
