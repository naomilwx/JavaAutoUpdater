package htlauncher.launcher;

import htlauncher.updater.UpdateManager;
import htlauncher.utilities.Utilities;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class AppLauncher {
	public static final String APP_INFO_FILEPATH = "HubTurbo.xml";
	
	private UpdateManager updater;
	
	public static void main(String[] args) {
		AppLauncher launcher = new AppLauncher();
		launcher.run();
		System.exit(0);
	}
	
	public AppLauncher(){
		try {
			updater = new UpdateManager(AppLauncher.APP_INFO_FILEPATH);
		} catch (URISyntaxException e) {
			//Should not happen. Means APP_INFO_FILEPATH is set wrongly
			e.printStackTrace();
			Utilities.showFatalErrorDialog(e);
			System.exit(-1);
		}
	}
	
	public void run(){
		boolean running = launchAppIfPathExists();
		runUpdater(!running);
		if(!running){
			launchAppIfPathExists();
		}
	}
	
	public void runUpdater(boolean firstRun){
		updater.runUpdate(firstRun);
	}
	
	public boolean launchAppIfPathExists(){
		String launchPath = updater.getAppLaunchPath();
		File path = new File(launchPath);
		if(path.exists()){
			launchApp(launchPath);
			return true;
		}else{
			return false;
		}
	}
	private void launchApp(String launchPath){
		String command = "java -jar " + launchPath; 
		try {
			Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			e.printStackTrace();
			Utilities.showFatalErrorDialog(e);
			System.exit(-1);
		}
	}
	
}
