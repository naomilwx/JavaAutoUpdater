package htlauncher.utilities;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Utilities {
	public static void showErrorDialog(String title, String message){
		JFrame frame = new JFrame();
		JOptionPane.showMessageDialog(frame,
			    message,
			    title,
			    JOptionPane.ERROR_MESSAGE);
	}
	
	public static void showFatalErrorDialog(Exception e){
		Utilities.showErrorDialog("Fatal Error", "A fatal error has occured. "
				+ e.getMessage()
				+ "Please contact the developers or check that a fix for the error is available");
	}
}
