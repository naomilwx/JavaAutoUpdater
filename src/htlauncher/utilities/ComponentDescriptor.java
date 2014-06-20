package htlauncher.utilities;

import java.net.URI;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlRootElement(name="component")
@XmlAccessorType(XmlAccessType.FIELD)
public class ComponentDescriptor {
	private String name;
	private URI localURI;
	private URI serverURI;
	private double version;
	
	public String getComponentName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}
	
	public double getVersion(){
		return version;
	}
	
	public void setVersion(double version){
		this.version = version;
	}
	
	public URI getLocalURI(){
		return localURI;
	}
	
	public void setLocalURI(URI local){
		this.localURI = local;
	}
	
	public URI getServerURI(){
		return serverURI;
	}
	
	public void setServerURI(URI server){
		this.serverURI = server;
	}
	

}
