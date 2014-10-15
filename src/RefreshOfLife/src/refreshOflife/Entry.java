package refreshOflife;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Entry implements Comparable<Entry>{

	protected final Date date;
	protected String text;
	protected String img;
	
	public Entry(Date date){
		this.date = date;
	}
	
	public Date getDate(){
		return date;
	}
	
	public void setImage(String fileName) {
		img = fileName;
	}
	
	public String getImage(){
		return img;
	}
	
	public void setText(String text){
		this.text = text;
	}
	
	public String getText(){
		return text;
	}

	/**
	 * Only compares entries according to the order of their dates.
	 * The text and image strings should be fetched to see if the content
	 * of two entries is identical.
	 */
	@Override
	public int compareTo(Entry arg0) {
		return date.compareTo(arg0.getDate());
	}
	
	public boolean isEmpty(){
		return text == null && img == null;
	}

	protected String toHtml() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getDate());
		
		String result = "\t\t\t<h2>"
			+ cal.getDisplayName(cal.MONTH, cal.LONG, Locale.US)
			+ " " + Integer.toString(cal.get(cal.DATE))
			+ ", "+ Integer.toString(cal.get(cal.YEAR)) + "</h2>\n"
		
			+ "\t\t\t<h3>" + cal.getDisplayName(cal.DAY_OF_WEEK, cal.LONG, Locale.US)
				+ "</h3>\n";
		
		if(img != null){
			result = result 
			+"\t\t\t<div class=\"pictureframe\"><div class=\"picture\">\n"
			+"\t\t\t<div class=\"picinside\">\n"
				+"\t\t\t\t<a href=\"./OfLife_files/"+img+"\">\n"
				+"\t\t\t\t<img src=\"./OfLife_files/"+img+"\" width=\"528px\"></a>\n"
			+"\t\t\t</div></div></div>\n";
		}
		
		if(text != null){
			result = result 
				+"\t\t\t<div class=\"text\">\n"
					+unescapeHtml(text)
				+"\t\t\t</div>\n"
				+"\t\t</div>\n";
		}
		
		return result;
	}
	
	public static String unescapeHtml(String orig){
		String result = orig.replaceAll("&","&amp;");
		result = result.replaceAll("<","&lt;");
		result = result.replaceAll(">","&gt;");
		result = result.replaceAll("\"","&quot;");
		result = result.replaceAll("'","&#39;");
		
		result = result.replaceAll("\n\n", "</p><p>");
		result = result.replaceAll("\n", "<br>");
		
		return "<p>"+result+"</p>\n";
	}

}