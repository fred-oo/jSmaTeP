package com.kardass.jsmatep.codegenerator;

/**
 * Holds the configuration for the code generation.
 *
 * @author Manfred Kardass (manfred@kardass.de)
 *
 */
public class CodeGeneratorConfiguration {
	
    /**
     * The path (dir) where the generation result will be saved.
     */
    protected String sourceDestinationPath;
    
    /**
     * XML file with data configuration.
     */
    protected String xmlDataFile;
    
	public CodeGeneratorConfiguration() {
    }

    /**
     * @param sourceDestinationPath  Destination of generated source. 
     * @param xmlDataFile   Data XML configuration file.
     */
    public CodeGeneratorConfiguration(String sourceDestinationPath, String xmlDataFile) {
		super();
		this.sourceDestinationPath = sourceDestinationPath;
		this.xmlDataFile = xmlDataFile;
	}

	/**
	 * @return
	 */
	public String getSourceDestinationPath() {
		return sourceDestinationPath;
	}

	/**
	 * @param path
	 */
	public void setSourceDestinationPath(String path) {
		this.sourceDestinationPath = path;
	}

	/**
	 * @return
	 */
	public String getXmlDataFile() {
		return xmlDataFile;
	}

	/**
	 * @param xmlFile
	 */
	public void setXmlDataFile(String xmlFile) {
		this.xmlDataFile = xmlFile;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("Generator destination path: ").append(getSourceDestinationPath()).append("\n");
		strBuilder.append("XML data file:              ").append(getXmlDataFile()).append("\n");
		return strBuilder.toString();
	}
   
}
