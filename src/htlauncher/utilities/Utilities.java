package htlauncher.utilities;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Utilities {
	public static void showError(String title, String message){
		showDialogWindow(title, message, JOptionPane.ERROR_MESSAGE);
	}
	
	public static void showWarning(String title, String message){
		showDialogWindow(title, message, JOptionPane.WARNING_MESSAGE);
	}
	
	public static void showMessage(String title, String message){
		showDialogWindow(title, message, JOptionPane.PLAIN_MESSAGE);
	}
	
	private static void showDialogWindow(String title, String message, int dialogType){
		JFrame frame = new JFrame();
		JOptionPane.showMessageDialog(frame,
			    message,
			    title,
			    dialogType);
	}
	
	public static void showFatalErrorDialog(Exception e){
		Utilities.showError("Fatal Error", "A fatal error has occured. "
				+ e.getMessage()
				+ "Please contact the developers or check that a fix for the error is available");
	}
}
