package htlauncher.updater;

import htlauncher.descriptors.AppDescriptor;

import java.net.URI;
import java.util.HashMap;

public class UpdateManager {
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
