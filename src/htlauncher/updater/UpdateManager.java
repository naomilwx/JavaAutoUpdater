package htlauncher.updater;

import htlauncher.utilities.AppDescriptor;

import java.net.URI;
import java.util.HashMap;

public class UpdateManager {
	private static final String UPDATER_INFO_FILE = "updater_data";
	
	private URI serverAppInfoURL;
	private AppDescriptor appDescriptor;
	private HashMap<String, Double> downloadedVersions;
	
	protected void updateDownloadedVersion(String name, double version){
		downloadedVersions.put(name, version);
	}
	
	protected double getDownloadedVersion(String name){
		return downloadedVersions.get(name);
	}
}
