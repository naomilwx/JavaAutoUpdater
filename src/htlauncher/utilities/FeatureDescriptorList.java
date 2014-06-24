package htlauncher.utilities;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;


@XmlRootElement(name="features")
@XmlSeeAlso({FeatureDescriptor.class})
@XmlAccessorType(XmlAccessType.FIELD)
public class FeatureDescriptorList {
	@XmlElement(name="feature")
	private ArrayList<FeatureDescriptor> features;
	
	public ArrayList<FeatureDescriptor> getFeatures(){
		return features;
	}
	
	public void setFeatures(ArrayList<FeatureDescriptor> features){
		this.features = features;
	}
	
	public static FeatureDescriptorList unserialiseFromURI(URI uri){
		FeatureDescriptorList list = null;
		try {
			JAXBContext context = JAXBContext.newInstance(FeatureDescriptorList.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			System.out.println(uri);
			list = (FeatureDescriptorList)unmarshaller.unmarshal(uri.toURL());
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public static ArrayList<FeatureDescriptor> getDescriptorListFromURI(URI uri){
		FeatureDescriptorList list = FeatureDescriptorList.unserialiseFromURI(uri);
		if(list != null){
			return list.getFeatures();
		}
		return null;
	}
}