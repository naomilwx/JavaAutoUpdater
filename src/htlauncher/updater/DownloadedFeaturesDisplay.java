package htlauncher.updater;

import htlauncher.utilities.FeatureDescriptor;
import htlauncher.utilities.FeatureDescriptorList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.function.Predicate;

public class DownloadedFeaturesDisplay {
	protected static final String FEATURE_INFO_FILEPATH = "feature_ver_data";
	public static final int READ_CONNECTION_TIMEOUT = 15000;
	
	private double lastDisplayedVersion = -1;
	private File featureStore;
	private URI featureDescriptorURI;
	
	public DownloadedFeaturesDisplay(URI uri){
		featureStore = new File(FEATURE_INFO_FILEPATH);
		featureDescriptorURI = uri;
		loadVersionData();
	}
	
	private void loadVersionData(){
		try {
			if(!featureStore.createNewFile()){
				loadFeatureVersionFromFile();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void loadFeatureVersionFromFile(){
		try {
			BufferedReader reader = new BufferedReader(new FileReader(featureStore));
			lastDisplayedVersion = Double.parseDouble(reader.readLine());
			reader.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected double getLastDisplayedVersion(){
		return lastDisplayedVersion;
	}
	
	protected void displayDownloadedFeatures(){
		ArrayList<FeatureDescriptor> features = getNewFeatureDescriptors();
		StringBuffer featureContents = getFeatureDescriptionDisplay(features);
		
		updateStoredVersionData();
	}
	
	private ArrayList<FeatureDescriptor> getNewFeatureDescriptors(){
		ArrayList<FeatureDescriptor> list = FeatureDescriptorList.getDescriptorListFromURI(featureDescriptorURI);
		Predicate<FeatureDescriptor> cond = new Predicate<FeatureDescriptor>(){
			@Override
			public boolean test(FeatureDescriptor t) {
				return t.getVersion() <= lastDisplayedVersion;
			}
  
		};
		list.removeIf(cond);
		return list;
	}
	
	private StringBuffer getFeatureDescriptionDisplay(ArrayList<FeatureDescriptor> list){
		StringBuffer contents = new StringBuffer();
		for(FeatureDescriptor feature : list){
			double version = feature.getVersion();
			if(version > lastDisplayedVersion){
				lastDisplayedVersion = version;
			}
			contents.append("Version :"+ version);
			contents.append("\n");
			contents.append(getFeatureDescription(feature.getPathToDescriptor()));
			contents.append("\n");
		}
		return contents;
	}
	
	private StringBuffer getFeatureDescription(URI featureURI){
		StringBuffer contents = new StringBuffer();
		try {
			URLConnection connection = featureURI.toURL().openConnection();
			connection.setReadTimeout(READ_CONNECTION_TIMEOUT);
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			
			String line;
			while((line = reader.readLine()) != null){
				contents.append(line);
				contents.append("\n");
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return contents;
	}
	
	private void updateStoredVersionData(){
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(featureStore));
			writer.write(""+lastDisplayedVersion);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
