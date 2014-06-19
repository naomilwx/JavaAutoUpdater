package htlauncher.utilities;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement(name="Application")
@XmlSeeAlso({ComponentDescriptor.class})
public class AppDescriptor {
	private URI launchPath;
	private URI serverAppDescriptorURI;
	private ArrayList<ComponentDescriptor> components;
	/*
	public String serialiseToXML(boolean XMLTag){
		try {
			JAXBContext context = JAXBContext.newInstance(AppDescriptor.class);
			Marshaller marshaller = context.createMarshaller();
			
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			
			marshaller.marshal(this, os);
			os.flush();
			return os.toString();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  "";
	}
	*/
	public static AppDescriptor unserialiseFromXMLFile(File file){
		JAXBContext context;
		try {
			context = JAXBContext.newInstance(AppDescriptor.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			
			AppDescriptor desc = (AppDescriptor)unmarshaller.unmarshal(file);
			return desc;
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@XmlElement(name="MainJAR")
	public URI getLaunchPath(){
		return launchPath;
	}
	public void setLaunchPath(URI path){
		this.launchPath = path;
	}
	
	@XmlAttribute
	public URI getserverAppDescriptorURI(){
		return serverAppDescriptorURI;
	}
	
	public void setserverAppDescriptorURI(URI descriptorURI){
		this.serverAppDescriptorURI = descriptorURI;
	}
	
	@XmlMixed
	public ArrayList<ComponentDescriptor> getComponents(){
		return components;
	}
	public void setComponents(ArrayList<ComponentDescriptor> components){
		this.components = components;
	}
	
}
