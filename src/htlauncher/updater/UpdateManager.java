package htlauncher.updater;


import htlauncher.utilities.ComponentDescriptor;

import java.net.URI;
import java.util.Set;

public class UpdateManager {
	
	private UpdateDataManager dataManager;
	
	public UpdateManager(String appInfoPath){
		dataManager = new UpdateDataManager(appInfoPath);
	}
	
	public void runUpdate(){
		updateAppDetails();
		updateAppComponents();
	}
	
	public void updateAppDetails(){
		URI serverURI = dataManager.serverAppInfoURI;
		if(serverURI != null){
			//overwrite with file from server
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
			
			//update success: update downloaded component version
			dataManager.updateDownloadedVersion(name, latestVer);
		}
	}
	
	public URI getAppLaunchPath(){
		return dataManager.getAppLaunchPath();
	}
	
}
