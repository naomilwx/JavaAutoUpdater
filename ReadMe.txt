The program automatically downloads the required jars given the configuration file. The path of the configuration file is set in APP_INFO_FILEPATH in AppLauncher.java
Format of configuration file:
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<application URI=path-of-xml-file-on-server>
	<mainJAR>path to main jar</MainJar>
	<component>
		<componentName>name of component</componentName>
		<localURI>local path of component's jar</localURI>
		<serverURI>path of component's jar on the server</serverURI>
		<version>component's version number</version>
	</component>
	<component>
		...
	</component>
</application>
