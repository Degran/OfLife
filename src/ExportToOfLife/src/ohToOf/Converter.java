package ohToOf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.regex.Pattern;

public class Converter {

	/**
	 * Splits the text file into multiple text files according to their date,
	 * renames the images to have names deemed more appropriate
	 * and renames the folder.
	 * @param dir
	 * 			The name of the folder that was exported from OhLife
	 * @throws IOException 
	 * @throws URISyntaxException 
	 */
	public static void OhToOf(String dir) throws IOException, URISyntaxException{
		File exportFolder = new File(dir);
		if(!exportFolder.isDirectory()){throw new FileNotFoundException();}
		
		URL url = OhToOf.class.getProtectionDomain().getCodeSource().getLocation();
		File execPath = new File(url.toURI());
		String oflifeDir 	= execPath.getParent() + "/OfLife_files";
		File[] contents = exportFolder.listFiles();
		
		Pattern textPat = Pattern.compile(
				"\\Aexport_\\d\\d\\d\\d-\\d\\d-\\d\\d\\056txt\\Z");
		Pattern imgPat = Pattern.compile(
				"\\Aimg_\\d\\d\\d\\d-\\d\\d-\\d\\d-\\d\\056.*");
		
		for(int i = 0; i < contents.length; i++){
			String fileName = contents[i].getName();
			if(textPat.matcher(fileName).matches()){
				exportToMultiple(contents[i], oflifeDir);
			}else if(imgPat.matcher(fileName).matches()){
				renameImage(contents[i], oflifeDir);
			}
		}//for(i, files in the folder)
	}//OhToOf()
	
	/**
	 * Splits the text file containing all text 
	 * into several text files according to their date
	 * @param exportText
	 * 			The name of the original exported text file
	 * @param oflifeDir 
	 * @throws IOException 
	 * 			When the text file can't be found or does not start correctly
	 */
	private static void exportToMultiple(File exportText, String oflifeDir) throws IOException{
		final BufferedReader bufRead = new BufferedReader(new FileReader(exportText));
		try{
			Pattern datePat = Pattern.compile(
					"\\A\\d\\d\\d\\d-\\d\\d-\\d\\d\\Z");
			
			PrintWriter writer = null;
			String fileName;
			String line;
			try{
				line = bufRead.readLine();
				while(line != null){
					// if it's the date of a new entry,
					// otherwise it should be considered text
					if(datePat.matcher(line).matches()){
						if(writer != null){writer.close();}
						fileName = oflifeDir + "/" + line + ".txt";
						writer = new PrintWriter(new File(fileName));
		
						// always a blank line after a date
						bufRead.readLine();
						
						// prevent the file from starting (or ending)
						// with an empty line
						line = bufRead.readLine();
						if(line == null){break;}
						writer.append(escapeHtml(line));
					}else{
						writer.append("\n"+escapeHtml(line));
					}
					
					line = bufRead.readLine();
				}
			}finally{
				writer.close();
			}
		}finally{
			bufRead.close();
		}
	}

	/**
	 * img_yyyy-mm-dd-0.jpg -> yyyy-mm-dd_img.jpg
	 * 
	 * This is believed to be a better file name
	 * because it will put the image next to the relevant text
	 * when sorted alphabetically.
	 * @param oflifeDir 
	 */
	private static void renameImage(File img, String oflifeDir){
		String noPreImg = img.getName().replaceFirst("img_", "");
		int lastIndex = noPreImg.lastIndexOf(".");
		int firstIndex = lastIndex - (new String("-0")).length();
		String newName = noPreImg.replaceFirst(
				noPreImg.substring(firstIndex, lastIndex)+"\\.", "_img.");
		
		String newPath = oflifeDir + "/" + newName;
		img.renameTo(new File(newPath));
	}
	
	public static String escapeHtml(String orig){
		String result = orig.replaceAll("&amp;", "&");
		result = result.replaceAll("&lt;", "<");
		result = result.replaceAll("&gt;", ">");
		result = result.replaceAll("&quot;", "\"");
		result = result.replaceAll("&#39;", "'");
		
		return result;
	}
}
