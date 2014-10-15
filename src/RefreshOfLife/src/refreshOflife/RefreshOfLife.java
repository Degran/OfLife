package refreshOflife;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class RefreshOfLife {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws URISyntaxException 
	 */
	public static void main(String[] args) throws IOException, URISyntaxException {
		// see ctrueden's answer in
		// http://stackoverflow.com/questions/320542/how-to-get-the-path-of-a-running-jar-file
		URL url = RefreshOfLife.class.getProtectionDomain().getCodeSource().getLocation();
		File execPath = new File(url.toURI());
		String resourceDir 	= execPath.getParent() + "/OfLife_files";
		String htmlPath		= execPath.getParent() + "/OfLife.html";
		
		FileConverter.refreshHtml(resourceDir, htmlPath);
		
		if(Desktop.isDesktopSupported()){
			File htmlFile = new File(htmlPath);
			Desktop.getDesktop().browse(htmlFile.toURI());
		}
		
	}

}
