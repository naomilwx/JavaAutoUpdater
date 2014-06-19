package htlauncher.descriptors;

import java.net.URI;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="component")
public class ComponentDescriptor {
	private String name;
	private URI localURI;
	private URI serverURI;
	private double version;
	
	@XmlElement
	public String getComponentName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}
	
	@XmlElement
	public double getVersion(){
		return version;
	}
	public URI getLocalURI(){
		return localURI;
	}
	
	@XmlElement
	public URI getServerURI(){
		return serverURI;
	}
	
	public void setVersion(double version){
		this.version = version;
	}

}
