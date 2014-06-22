package htlauncher.launcher;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import htlauncher.updater.UpdateManager;
import htlauncher.utilities.Utilities;

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
		runUpdater();
		launchApp();
	}
	
	public void runUpdater(){
		updater.runUpdate();
	}
	
	public void launchApp(){
		URI launchPath = updater.getAppLaunchPath();
		String command = "java -jar " + launchPath.toString(); 
		try {
			Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			e.printStackTrace();
			Utilities.showFatalErrorDialog(e);
			System.exit(-1);
		}
	}
	
}
