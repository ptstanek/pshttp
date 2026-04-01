package com.ptstanek.pshttp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class HtmlFile {
	private String path;
	private File htmlFile;

	public HtmlFile(String path) { // constructor
		this.path = path;
		htmlFile = new File(this.path);
	}
	
	/**
	 * 
	 * @return the contents of a HTML file as an array of bytes.
	 * @throws FileNotFoundException, IOException
	 */
	public byte[] getBytes() throws FileNotFoundException, IOException {
		FileInputStream fis = new FileInputStream(this.path);
		
		byte[] fileBytes = new byte[(int) this.htmlFile.length()];
		fis.read(fileBytes);
		fis.close();
		
		return fileBytes;
	}
	
	
}
