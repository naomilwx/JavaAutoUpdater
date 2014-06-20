package htlauncher.updater.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class UpdateProgressWindow extends JFrame {

	private JPanel contentPane;
	private JProgressBar progressBar;
	private JLabel progressDescription;
	
	protected static final int WIDTH = 450;
	protected static final int HEIGHT = 150;
	protected static final int PROGRESSBAR_WIDTH = 300;
	protected static final int PROGRESSBAR_HEIGHT = 50;
	protected static final int DESCRIPTION_WIDTH = 300;
	protected static final int DESCRIPTION_HEIGHT = 30;
	
	public void setDisplayedText(String text){
		SwingUtilities.invokeLater(new Runnable(){
             @Override
             public void run() {
            	 progressDescription.setText(text);
             }
     });
	}
	
	public void showWindow(){
		UpdateProgressWindow selfRef = this;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					selfRef.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the frame.
	 */
	public UpdateProgressWindow() {
		setupMainWindow();
		setupContentPane();
		setupProgressBar();
		setupProgressLabel();
	}
	
	private void setupMainWindow(){
		setUndecorated(true);
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
	}

	private void setupContentPane(){
		contentPane = new JPanel();
		contentPane.setLayout(null);
		setContentPane(contentPane);
	}
	
	private void setupProgressBar(){
		progressBar = new JProgressBar();
		progressBar.setSize(PROGRESSBAR_WIDTH, PROGRESSBAR_HEIGHT);
		centerComponentWithOffset(progressBar, 0, 0);
		contentPane.add(progressBar);
	}
	
	private void centerComponentWithOffset(Component component, int xoffset, int yoffset){
		int xPos = (WIDTH - component.getWidth())/2 + xoffset;
		int yPos = (HEIGHT - component.getHeight())/2 + yoffset;
		component.setLocation(xPos, yPos);
	}
	
	private void setupProgressLabel(){
		progressDescription = new JLabel();
		progressDescription.setSize(DESCRIPTION_WIDTH, DESCRIPTION_HEIGHT);
		centerComponentWithOffset(progressDescription, 0, DESCRIPTION_HEIGHT);
		progressDescription.setText("test");
		contentPane.add(progressDescription);
	}

}
