package htlauncher.updater.ui;

import java.awt.Component;
import java.awt.EventQueue;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

public class UpdateProgressWindow extends JFrame {

	private static final long serialVersionUID = 7695164016513402916L;

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
	
	public void setWindowVisibility(boolean visible){
		WeakReference<UpdateProgressWindow> selfRef = new WeakReference<UpdateProgressWindow>(this);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					selfRef.get().setVisible(visible);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void setProgress(int progress){
		try {
			SwingUtilities.invokeAndWait(new Runnable(){
			    @Override
			    public void run() {
			    	progressBar.setValue(progress);
			    }
			});
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Create the frame.
	 */
	public UpdateProgressWindow() {
		try {
			SwingUtilities.invokeAndWait(new Runnable(){
			    @Override
			    public void run() {
			    	setupMainWindow();
					setupContentPane();
					setupProgressBar();
					setupProgressLabel();
			    }
			});
		} catch (InvocationTargetException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		progressBar = new JProgressBar(0, 100);
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
		contentPane.add(progressDescription);
	}

}
