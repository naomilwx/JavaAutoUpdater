package htlauncher.utilities;

import java.net.URI;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="feature")
public class FeatureDescriptor {
	@XmlAttribute(name="version")
	private double version;
	@XmlAttribute(name="URI")
	private URI pathToDescriptor;
	
	public double getVersion(){
		return version;
	}
	
	public void setVersion(double ver){
		this.version = ver;
	}
	
	public void setPathToDescriptor(URI path){
		this.pathToDescriptor = path;
	}
}
