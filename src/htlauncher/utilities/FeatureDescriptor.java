package htlauncher.utilities;

import java.net.URI;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="feature")
@XmlAccessorType(XmlAccessType.NONE)
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
	
	public URI getPathToDescriptor(){
		return pathToDescriptor;
	}
	
	public void setPathToDescriptor(URI path){
		this.pathToDescriptor = path;
	}
}
