The program automatically downloads the required jars given the configuration file. The path of the configuration file is set in APP_INFO_FILEPATH in AppLauncher.java
Format of configuration file:
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<application URI=path-of-xml-file-on-server appName="application name">
	<mainJAR>path to main jar</MainJar>
	<components>
		<component>
			<name>name of component</name>
			<localURI>local path of component's jar</localURI>
			<serverURI>path of component's jar on the server</serverURI>
			<version>component's version number</version>
		</component>
		<component>
			...
		</component>
	</components>
</application>

The program can be distributed without its initial configuration file. If distributed without the configuration file, the program will download the file from the path set by the constant DEFAULT_XML_PATH in htlauncher.updater.UpdateDataManager.java
