package htlauncher.updater;

import htlauncher.utilities.AppDescriptor;

import java.io.File;
import java.net.URI;
import java.util.HashMap;

public class UpdateManager {
	
	private AppDescriptor appDescriptor;
	private UpdateDataManager dataManager;
	
	public UpdateManager(String appInfoPath){
		dataManager = new UpdateDataManager(appInfoPath);
	}
	
	public URI getAppLaunchPath(){
		return appDescriptor.getLaunchPath();
	}
	
	public void runUpdate(){
		
	}
	
	public void updateAppDetails(){
		
	}
	
	public void updateAppComponents(){
		
	}
	
	
}
