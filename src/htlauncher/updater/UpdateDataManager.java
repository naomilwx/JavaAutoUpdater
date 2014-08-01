package htlauncher.updater;

import htlauncher.utilities.AppDescriptor;
import htlauncher.utilities.ComponentDescriptor;
import htlauncher.utilities.Utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class UpdateDataManager {
	protected static final String DEFAULT_XML_PATH = "https://raw.githubusercontent.com/naomilwx/JavaAutoUpdater/master/HubTurbo.xml";
	protected static final String UPDATER_INFO_FILEPATH = "updater_data";
	private static final String SPLIT_MARKER = "<-sp->";
	
	private File updaterInfoFile;
	private File appInfoFile;
	private HashMap<String, Double> downloadedVersions = new HashMap<String, Double>();
	private String appInfoFilepath;
	private AppDescriptor appDescriptor;
	private URI serverAppInfoURI;
	
	public UpdateDataManager(String appInfoPath){
		this.appInfoFilepath = appInfoPath;
		updaterInfoFile = new File(UPDATER_INFO_FILEPATH);
		appInfoFile = new File(appInfoFilepath);
		loadUpdaterData();
	}
	
	protected void updateDownloadedVersion(String name, double version){
		downloadedVersions.put(name, version);
	}
	
	protected double getDownloadedVersion(String name){
		Double ver = downloadedVersions.get(name);
		if(ver == null){
			return -1;
		}
		return ver;
	}
	
	private void loadUpdaterData(){
		try{
			if(!updaterInfoFile.exists()){
				updaterInfoFile.createNewFile();
			}else{
				loadUpdaterDataFromFile();
			}
		}catch(IOException e){
			e.printStackTrace();
			Utilities.showWarning("Launcher cache file creation failed", 
					"Cannot create or open application launcher cache file. Check directory permissions");
		}
	}
	
	
	private void loadUpdaterDataFromFile(){
		try {
			BufferedReader fileReader = new BufferedReader(new FileReader(updaterInfoFile));
			String storedPath = fileReader.readLine();
			if(storedPath != null){
				serverAppInfoURI = new URI(storedPath);
			}
			
			String line;
			while((line = fileReader.readLine()) != null){
				String[] lineArr = line.split(SPLIT_MARKER);
				if(lineArr.length == 2){
					String name = lineArr[0];
					Double ver = Double.parseDouble(lineArr[1]);
					downloadedVersions.put(name, ver);
				}
			}
			fileReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			Utilities.showWarning("Launcher cache read failed.", "Cannot read application launcher data. Check directory permissions.");
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	protected void saveUpdaterData(){
		try {
			BufferedWriter fileWriter = new BufferedWriter(new FileWriter(updaterInfoFile));
			fileWriter.write(serverAppInfoURI.toString());
			fileWriter.write("\n");
			
			for(Entry<String, Double> entry : downloadedVersions.entrySet()){
				String entryString = entry.getKey();
				entryString += SPLIT_MARKER + entry.getValue();
				fileWriter.write(entryString);
				fileWriter.write("\n");
			}
			
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
			Utilities.showWarning("Launcher data save failed",
					"Cannot save application launcher data to disk. Check directory permissions.");
		}
	}
	
	protected URI getAppLaunchPath(){
		if(appDescriptor == null){
			loadAppData();
		}
		return appDescriptor.getLaunchPath();
	}
	
	protected void loadAppData(){
		appDescriptor = AppDescriptor.unserialiseFromXMLFile(appInfoFile);
		serverAppInfoURI = appDescriptor.getserverAppDescriptorURI();
	}
	
	protected ArrayList<ComponentDescriptor> getAppComponents(){
		if(appDescriptor == null){
			loadAppData();
		}
		return appDescriptor.getComponents();
	}
	
	protected URI getServerAppInfoURI(){
		if(serverAppInfoURI == null){
			try {
				serverAppInfoURI = new URI(DEFAULT_XML_PATH);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
		return serverAppInfoURI;
	}
}
