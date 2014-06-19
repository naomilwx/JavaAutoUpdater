package htlauncher.updater;

import htlauncher.utilities.AppDescriptor;
import htlauncher.utilities.ComponentDescriptor;

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
import java.util.Set;


public class UpdateDataManager {
	private static final String UPDATER_INFO_FILEPATH = "updater_data";
	
	private File updaterInfoFile;
	private File appInfoFile;
	private HashMap<String, Double> downloadedVersions;
	
	private String appInfoFilepath;
	private AppDescriptor appDescriptor;
	
	protected URI serverAppInfoURI;
	
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
			return 0;
		}
		return ver;
	}
	
	private void loadUpdaterData(){
		downloadedVersions = new HashMap<String, Double>();
		try{
			if(!updaterInfoFile.exists()){
				updaterInfoFile.createNewFile();
			}else{
				loadUpdaterDataFromFile();
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}
	
	
	private void loadUpdaterDataFromFile(){
		try {
			BufferedReader fileReader = new BufferedReader(new FileReader(updaterInfoFile));
			String storedPath = fileReader.readLine();
			serverAppInfoURI = new URI(storedPath);
			
			String line;
			while((line = fileReader.readLine()) != null){
				String[] lineArr = line.split("\\s+");
				if(lineArr.length == 2){
					String name = lineArr[0];
					Double ver = Double.parseDouble(lineArr[1]);
					downloadedVersions.put(name, ver);
				}
			}
			fileReader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
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
				entryString += " " + entry.getValue();
				fileWriter.write(entryString);
				fileWriter.write("\n");
			}
			
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	
}
