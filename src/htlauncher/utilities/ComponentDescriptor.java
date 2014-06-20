package htlauncher.utilities;

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
	
	public void setVersion(double version){
		this.version = version;
	}
	
	@XmlElement
	public URI getLocalURI(){
		return localURI;
	}
	
	public void setLocalURI(URI local){
		this.localURI = local;
	}
	
	@XmlElement
	public URI getServerURI(){
		return serverURI;
	}
	
	public void setServerURI(URI server){
		this.serverURI = server;
	}
	

}
