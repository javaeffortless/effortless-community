package org.effortless.core;

import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
//import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.io.output.ByteArrayOutputStream;
//import org.apache.tika.Tika;
import org.apache.tika.detect.DefaultDetector;
//import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
//import org.apache.tika.sax.BodyContentHandler;
//import org.effortless.util.metadata.*;
import org.xml.sax.ContentHandler;
//import org.xml.sax.Parser;
//import org.xml.sax.SAXException;
//import org.xml.sax.helpers.DefaultHandler;


/**
 * 
 * @author jesus
 *
 */
public class MetadataFiles {

	public static void main (String[] args) {
//		testDetect(new File("/home/jesus/programs/eclipse-indigo/about.html"));
//		testDetect(new File("/home/jesus/Escritorio/LA GRACIOSA.jpg"));
//		testDetect(new File("/home/jesus/Escritorio/GUIAS_MEDICAS_23_3_3.pdf"));
//		testDetect(new File("/home/jesus/Escritorio/Windows XP SSD_files/back_24x24.png"));
//		testDetect(new File("/home/jesus/Escritorio/Windows XP SSD_files/t.gif"));
//		
//		testDetect(new File("/home/jesus/Escritorio/Windows XP SSD_files/widgets.js"));
//		testDetect(new File("/home/jesus/Escritorio/test.odt"));
//		
//		testParse(new File("/home/jesus/Escritorio/GUIAS_MEDICAS_23_3_3.pdf"));

		
//		F(new File("/home/jesus/Descargas/Linkedin_Presentation.odp"));

//		Map<String, Object> metadata = read("/home/jesus/Descargas/SharpCV.doc");
//		Map<String, Object> metadata = read("/home/jesus/Descargas/Korea_Rep.doc");
		Map<String, Object> metadata = read("/home/jesus/Descargas/meta-extractor-developers-guide-v3.pdf");
		
		Map<String, Object> metadata3 = read("/home/jesus/Descargas/documentos_NOTA_DE_PRENSA_DE_EURO_MILLONES_30_11_12_85f566fc.pdf");		
		
		Map<String, Object> metadata4 = read("/home/jesus/Escritorio/GUIAS_MEDICAS_23_3_3.pdf");
		
		
		Map<String, Object> metadata2 = read("/home/jesus/Descargas/SangaHolidayScheduleupdated.odt");
//		Object edittime1 = metadata.get(EDIT_TIME);
		Object edittime2 = metadata2.get(EDIT_TIME);

if (false) {
	read("/home/jesus/Descargas/SangaHolidayScheduleupdated.odt");
	read("/home/jesus/dwhelper/ABC Song.flv");
	read("/home/jesus/Escritorio/safeplay_button-249x300.jpg");
	read("/home/jesus/Escritorio/PNG_transparency_demonstration_1.png");
	read("/home/jesus/Escritorio/banano.gif");

	read(new File("/home/jesus/Descargas/darknet5.doc"));

	read(new File("/home/jesus/Descargas/VAPProjectTemplate_New.xls"));
	read(new File("/home/jesus/Descargas/SharpCV.doc"));
	read(new File("/home/jesus/Descargas/Korea_Rep.doc"));		
	read(new File("/home/jesus/Descargas/2torrent8_pub.doc"));
	read("/home/jesus/Escritorio/LA GRACIOSA.jpg");
	read(new File("/home/jesus/Descargas/Linkedin_Presentation.odp"));
	read(new File("/home/jesus/Descargas/VAPProjectTemplate_New.xls"));
	read(new File("/home/jesus/Descargas/SangaHolidayScheduleupdated.odt"));
	read(new File("/home/jesus/Descargas/Remack.odt"));
	read(new File("/home/jesus/Descargas/toddlerprogrambrochure.odt"));
	read(new File("/home/jesus/Descargas/Kalorienberechnung_ohne.ods"));
	read(new File("/home/jesus/Descargas/Linkedin_Presentation.odp"));
	read(new File("/home/jesus/Descargas/meta-extractor-developers-guide-v3.pdf"));

	read(new File("/home/jesus/Descargas/documentos_NOTA_DE_PRENSA_DE_EURO_MILLONES_30_11_12_85f566fc.pdf"));		
	
	read(new File("/home/jesus/Escritorio/GUIAS_MEDICAS_23_3_3.pdf"));

	read(new File("/home/jesus/Descargas/Linkedin_Presentation.odp"));
	read(new File("/home/jesus/Descargas/meta-extractor-developers-guide-v3.pdf"));
	read(new File("/home/jesus/Descargas/tika-app-1.2.jar"));

		read(new File("/home/jesus/dwhelper/music/01 - JOLLY SONGS A-Z(from the big book JOLLY SONGS).mp3"));

		read(new File("/home/jesus/dwhelper/ABC Song.flv"));
				
		
		read(new File("/home/jesus/Descargas/Linkedin_Presentation.odp"));
		read(new File("/home/jesus/Descargas/meta-extractor-developers-guide-v3.pdf"));
		read(new File("/home/jesus/Descargas/tika-app-1.2.jar"));
		read(new File("/home/jesus/programs/eclipse-indigo/about.html"));
		read(new File("/home/jesus/Escritorio/LA GRACIOSA.jpg"));
		
		read(new File("/home/jesus/Escritorio/AlojamientoCompartimentado.jpg"));
		
		read(new File("/home/jesus/Escritorio/logo.png"));
				
		read(new File("/home/jesus/Escritorio/GUIAS_MEDICAS_23_3_3.pdf"));
		read(new File("/home/jesus/Escritorio/Windows XP SSD_files/back_24x24.png"));
		read(new File("/home/jesus/Escritorio/Windows XP SSD_files/t.gif"));
		
		read(new File("/home/jesus/Descargas/Linkedin_Presentation.odp"));
		read(new File("/home/jesus/Descargas/meta-extractor-developers-guide-v3.pdf"));
		read(new File("/home/jesus/Descargas/tika-app-1.2.jar"));
		read(new File("/home/jesus/programs/eclipse-indigo/about.html"));
		read(new File("/home/jesus/Escritorio/Windows XP SSD_files/widgets.js"));
		read(new File("/home/jesus/Escritorio/test.odt"));
}
	}

	public static Map<String, Object> read (String file) {
		Map<String, Object> result = null;
		if (file != null && file.trim().length() > 0) {
			result = read(new File(file));
		}
		return result;
	}
	
	
	public static Map<String, Object> read (File file) {
		Map<String, Object> result = null;
		try {
			if (file != null && file.exists()) {
				result = new HashMap<String, Object>();
				
//				System.out.println(file.getAbsolutePath());
				ParseContext context = new ParseContext();
				DefaultDetector detector = new DefaultDetector();
		        AutoDetectParser parser = new AutoDetectParser(detector);
	//	        context.set(Parser.class, parser);
	//	        context.set(PasswordProvider.class, new PasswordProvider() {
	//	            public String getPassword(Metadata metadata) {
	//	                return password;
	//	            }
	//	        });		
		        Metadata metadata = new Metadata();
	//	        FileInputStream input = new FileInputStream(file);
		        TikaInputStream input = TikaInputStream.get(file);
				
		        ByteArrayOutputStream output = new ByteArrayOutputStream();//System.out;
				String encoding = null;
		        final PrintWriter writer = new PrintWriter(getOutputWriter(output, encoding));
		        
		        ContentHandler handler = new NoDocumentMetHandler(metadata, writer);
		        
		        
		        
		        parser.parse(input, handler, metadata, context);
		        // fix for TIKA-596: if a parser doesn't generate
		        // XHTML output, the lack of an output document prevents
		        // metadata from being output: this fixes that
		        if (handler instanceof NoDocumentMetHandler){
		            NoDocumentMetHandler metHandler = (NoDocumentMetHandler)handler;
		            if(!metHandler.metOutput()){
		                metHandler.endDocument();
		            }
		        }
		        
//		        String text = output.toString("UTF-8");
		        
//		        String[] array = (text != null ? text.split("\n") : null);
//		        String contributor = metadata.get(Metadata.CONTRIBUTOR);
//		        String[] names = metadata.names();
//		        for (String name : names) {
//		        	System.out.println("" + name + " = " + metadata.get(name));
//		        }
		        
		        		        
		        readString(CONTENT_TYPE, CONTENT_TYPE_ARRAY, metadata, result);
		        
		        //OFFICE
		        readStringConcat(AUTHOR, AUTHOR_ARRAY, metadata, result);
//		        readString(LAST_AUTHOR, LAST_AUTHOR_ARRAY, metadata, result);
//		        readString(COMPANY, COMPANY_ARRAY, metadata, result);

		        readDate(LAST_MODIFIED, LAST_MODIFIED_ARRAY, metadata, result);
		        readDate(CREATION_DATE, CREATION_DATE_ARRAY, metadata, result);
		        readDate(PRINT_DATE, PRINT_DATE_ARRAY, metadata, result);

//		        readInteger(EDIT_TIME, EDIT_TIME_ARRAY, metadata, result);//PT19H58M4S
		        readEditTime(EDIT_TIME, EDIT_TIME_ARRAY, metadata, result);//PT19H58M4S
		        readInteger(REVISION_NUMBER, REVISION_NUMBER_ARRAY, metadata, result);
		        readInteger(WORD_COUNT, WORD_COUNT_ARRAY, metadata, result);
		        readInteger(TABLE_COUNT, TABLE_COUNT_ARRAY, metadata, result);
		        readInteger(CHARACTER_COUNT, CHARACTER_COUNT_ARRAY, metadata, result);
		        readInteger(OBJECT_COUNT, OBJECT_COUNT_ARRAY, metadata, result);
		        readInteger(PAGE_COUNT, PAGE_COUNT_ARRAY, metadata, result);
		        readInteger(PARAGRAPH_COUNT, PARAGRAPH_COUNT_ARRAY, metadata, result);
		        readInteger(IMAGE_COUNT, IMAGE_COUNT_ARRAY, metadata, result);
		        		        
		        readString(TITLE, TITLE_ARRAY, metadata, result);
		        readString(SUBJECT, SUBJECT_ARRAY, metadata, result);
		        readString(COMMENTS, COMMENTS_ARRAY, metadata, result);
		        readString(LANGUAGE, LANGUAGE_ARRAY, metadata, result);
		        readStringConcat(SOFTWARE, SOFTWARE_ARRAY, metadata, result);
//		        readString(SOFTWARE2, SOFTWARE2_ARRAY, metadata, result);
		        readString(KEYWORDS, KEYWORDS_ARRAY, metadata, result);

		        //MP3
		        readString(AUDIO_COMPRESSOR, AUDIO_COMPRESSOR_ARRAY, metadata, result);
		        readString(AUDIO_CHANNEL_TYPE, AUDIO_CHANNEL_TYPE_ARRAY, metadata, result);
		        readInteger(CHANNELS, CHANNELS_ARRAY, metadata, result);
		        readString(SAMPLE_RATE, SAMPLE_RATE_ARRAY, metadata, result);
		        readString(VERSION, VERSION_ARRAY, metadata, result);

		        //VIDEO
		        readDouble(TOTAL_DATA_RATE, TOTAL_DATA_RATE_ARRAY, metadata, result);
		        readDouble(VIDEO_DATA_RATE, VIDEO_DATA_RATE_ARRAY, metadata, result);
		        readDouble(AUDIO_DATA_RATE, AUDIO_DATA_RATE_ARRAY, metadata, result);
		        readDouble(DURATION, DURATION_ARRAY, metadata, result);
		        readDouble(FRAME_RATE, FRAME_RATE_ARRAY, metadata, result);
		        readDouble(BYTE_LENGTH, BYTE_LENGTH_ARRAY, metadata, result);
		        
		        readInteger(WIDTH, WIDTH_ARRAY, metadata, result);
		        readInteger(HEIGHT, HEIGHT_ARRAY, metadata, result);

		        readBoolean(HAS_VIDEO, HAS_VIDEO_ARRAY, metadata, result);
		        readBoolean(HAS_AUDIO, HAS_AUDIO_ARRAY, metadata, result);
		        readBoolean(CAN_SEEK_ON_TIME, CAN_SEEK_ON_TIME_ARRAY, metadata, result);

		        //IMAGE
		    	readString(BITS_PER_SAMPLE, BITS_PER_SAMPLE_ARRAY, metadata, result);
//		    	readInteger(NUMBER_OF_COMPONENTS, NUMBER_OF_COMPONENTS_ARRAY, metadata, result);
		    	
		    	readOthers(metadata, result);
		    	
				Map<String, Object> general = readGeneral(file);
				if (general != null) {
					result.putAll(general);
				}
		        
		        
		        
		        
//		        System.out.println("TEXT >>>>>>>>>>>>>> ");
//		        System.out.println(text);
			}
		}
		catch (Exception e) {
//			e.printStackTrace();
			throw new ModelException(e);
		}
		return result;
	}
	
	protected static void readOthers(Metadata metadata, Map<String, Object> result) {
		String value = "";
		String[] names = metadata.names();
		for (String name : names) {
			if (!constainsName(name)) {
				value += (value.trim().length() > 0 ? "\n" : "");
				value += name + ": " + metadata.get(name);
			}
		}
		if (value != null && value.trim().length() > 0) {
			result.put(OTHERS, value);
		}
	}
	
	protected static boolean constainsName(String name) {
		boolean result = false;
		if (name != null) {
			for (String[] array : ARRAYS) {
				for (String item : array) {
					if (name.equals(item)) {
						result = true;
						break;
					}
				}
				if (result) {
					break;
				}
			}
		}
		return result;
	}

	public static final String HIDDEN = "hidden";
	public static final String FILE_LAST_MODIFIED = "fileLastModified";
	public static final String SIZE = "size";
	public static final String PATH = "path";
	
	public static Map<String, Object> readGeneral (File file) {
		Map<String, Object> result = null;
		if (file != null) {
			result = new HashMap<String, Object>();
	
			result.put(HIDDEN, Boolean.valueOf(file.isHidden()));
			result.put(FILE_LAST_MODIFIED, new Date(file.lastModified()));
			result.put(SIZE, Long.valueOf(file.length()));
			result.put(PATH, file.getParent());
		}
		return result;
	}
	
	
	protected static void readString (String key, String[] variables, Metadata metadata, Map<String, Object> map) {
		String value = null;
		for (String var : variables) {
			value = metadata.get(var);
			value = (value != null ? value.trim() : "");
			if (value.length() > 0) {
				break;
			}
		}
		if (value.length() > 0) {
			map.put(key, value);
		}
	}
	
	protected static void readStringConcat (String key, String[] variables, Metadata metadata, Map<String, Object> map) {
		String value = "";
		for (String var : variables) {
			String text = metadata.get(var);
			text = (text != null ? text.trim() : "");
			if (text.length() > 0) {
				value += (value.length() > 0 ? ", " : "") + text;
				break;
			}
		}
		if (value.trim().length() > 0) {
			map.put(key, value);
		}
	}
	
	protected static void readInteger (String key, String[] variables, Metadata metadata, Map<String, Object> map) {
		Integer value = null;
		for (String var : variables) {
			String text = metadata.get(var);
			text = (text != null ? text.trim() : "");
			int idx = text.indexOf(" ");
			text = (idx < 0 ? text : text.substring(0, idx));
			text = (text.endsWith(".0") ? text.substring(0, text.length() - 2) : text);
			try {
				value = (text.length() > 0 ? Integer.valueOf(text) : null);
			}
			catch (Throwable t) {
			}
			if (value != null) {
				break;
			}
		}
		if (value != null) {
			map.put(key, value);
		}
	}

	//PT01H51M46S	
	//600000000
	protected static void readEditTime (String key, String[] variables, Metadata metadata, Map<String, Object> map) {
		Integer value = null;
		for (String var : variables) {
			try {
				String text = metadata.get(var);
				text = (text != null ? text.trim() : "");
				if (text.endsWith("0000000")) {
					text = text.substring(0, text.length() - 7);
					if (text.length() > 0) {
							value = Integer.valueOf(text);
					}
				}
				else if (text.length() == 11 && text.startsWith("PT")) {//PT01H51M46S	
					String hours = text.substring(2, 4);
					String minutes = text.substring(5, 7);
					String seconds = text.substring(8, 10);
					
					int _hours = Integer.valueOf(hours).intValue();
					int _minutes = Integer.valueOf(minutes).intValue();
					int _seconds = Integer.valueOf(seconds).intValue();
					
					int _total = (_hours * 60 * 60) + (_minutes * 60) + _seconds;
					value = Integer.valueOf(_total);
				}
			}
			catch (Throwable t) {
			}
			if (value != null) {
				break;
			}
		}
		if (value != null) {
			map.put(key, value);
		}
	}


	
	
	protected static void readDouble (String key, String[] variables, Metadata metadata, Map<String, Object> map) {
		Double value = null;
		for (String var : variables) {
			String text = metadata.get(var);
			text = (text != null ? text.trim() : "");
			try {
				value = (text.length() > 0 ? Double.valueOf(text) : null); 
			}
			catch (Throwable t) {
			}
			if (value != null) {
				break;
			}
		}
		if (value != null) {
			map.put(key, value);
		}
	}

	protected static void readBoolean (String key, String[] variables, Metadata metadata, Map<String, Object> map) {
		Boolean value = null;
		for (String var : variables) {
			String text = metadata.get(var);
			text = (text != null ? text.trim() : "");
			try {
				value = (text.length() > 0 ? Boolean.valueOf(text) : null);
			}
			catch (Throwable t) {
			}
			if (value != null) {
				break;
			}
		}
		if (value != null) {
			map.put(key, value);
		}
	}

	protected static final SimpleDateFormat DATE_FORMAT1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
	protected static final SimpleDateFormat DATE_FORMAT2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	protected static final SimpleDateFormat DATE_FORMAT3 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS");
	protected static final SimpleDateFormat DATE_FORMAT4 = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
	
	protected static Date parse (String value, SimpleDateFormat formatter) {
		Date result = null;
		try {
			result = formatter.parse(value);
		}
		catch (ParseException e) {
			
		}
		return result;
	}
	
				
	
	protected static Date parse (String value) {
		Date result = null;
		if (value != null && value.length() > 0) {
			result = (result == null ? parse(value, DATE_FORMAT1) : result);
			result = (result == null ? parse(value, DATE_FORMAT2) : result);
			result = (result == null ? parse(value, DATE_FORMAT3) : result);
			result = (result == null ? parse(value, DATE_FORMAT4) : result);
		}
		return result;
	}
	
	protected static void readDate (String key, String[] variables, Metadata metadata, Map<String, Object> map) {
		Date value = null;
		for (String var : variables) {
			String text = metadata.get(var);
			text = (text != null ? text.trim() : "");
			value = parse(text);
			if (value != null) {
				break;
			}
		}
		if (value != null) {
			map.put(key, value);
		}
	}
	
    private static Writer getOutputWriter(OutputStream output, String encoding)
            throws UnsupportedEncodingException {
        if (encoding != null) {
            return new OutputStreamWriter(output, encoding);
        } else if (System.getProperty("os.name")
                .toLowerCase().startsWith("mac os x")) {
            // TIKA-324: Override the default encoding on Mac OS X
            return new OutputStreamWriter(output, "UTF-8");
        } else {
            return new OutputStreamWriter(output);
        }
    }	
	
	
	
//	public static void testParse (File file) {
//	    FileInputStream is = null;
//		try {
//			is = new FileInputStream(file);
//		} catch (FileNotFoundException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//
//	    BodyContentHandler contenthandler = new BodyContentHandler();
//	    Metadata metadata = new Metadata();
//	    metadata.set(Metadata.RESOURCE_NAME_KEY, file.getName());
//	    AutoDetectParser parser = new AutoDetectParser();
//	    // OOXMLParser parser = new OOXMLParser();
//	    try {
//			parser.parse(is, contenthandler, metadata);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SAXException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (TikaException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	    
//	    String[] names = metadata.names();
//	    for (String name : names) {
//	    	String value = metadata.get(name);
//	    	System.out.println("name = " + name + " value = " + value);
//	    }
//	    
//	    // **** End Tika-specific
//	}
//	
//	public static void testDetect (File file) {
//		Tika tika = new Tika();
//		String format = null;
//		try {
//			format = tika.detect(file);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println("format = " + format);
//	}
	
//	public static Map<String, Object> read (File file) {
//		Map<String, Object> result = null;
////		org.apache.tika.detect.NameDetector detector = new org.apache.tika.detect.NameDetector();
////		detector.detect(arg0, arg1)
//		if (file != null && file.exists()) {
//			if (MetadataAudioFiles.isValid(file)) {
//				result = MetadataAudioFiles.read(file);
//			}
//			else if (MetadataImageFiles.isValid(file)) {
//				result = MetadataImageFiles.read(file);
//			}
//			else if (MetadataOfficeMsFiles.isValid(file)) {
//				result = MetadataOfficeMsFiles.read(file);
//			}
//			else if (MetadataOfficeOpenFiles.isValid(file)) {
//				result = MetadataOfficeOpenFiles.read(file);
//			}
//			else if (MetadataPdfFiles.isValid(file)) {
//				result = MetadataPdfFiles.read(file);
//			}
//			else if (MetadataPngFiles.isValid(file)) {
//				result = MetadataPngFiles.read(file);
//			}
//			else if (MetadataVideoFiles.isValid(file)) {
//				result = MetadataVideoFiles.read(file);
//			}
//			
//			result = (result != null ? result : new HashMap<String, Object>());
//			
//			Map<String, Object> general = MetadataGeneralFiles.read(file);
//			if (general != null) {
//				result.putAll(general);
//			}
//		}
//		
//		return result;
//	}

	//http://tika.apache.org/
    
    
	public static final String CONTENT_TYPE = "Content-Type";
	protected static final String[] CONTENT_TYPE_ARRAY = {"Content-Type"};
	
	public static final String AUTHOR = "author";
	protected static final String[] AUTHOR_ARRAY = {"Author", "creator", "dc:creator", "meta:author", "initial-creator", "meta:initial-author", "meta:last-author", "Company"};
	
//	public static final String COMPANY = "company";
//	protected static final String[] COMPANY_ARRAY = {"Company"};
	
//	public static final String LAST_AUTHOR = "last author";
//	protected static final String[] LAST_AUTHOR_ARRAY = {"meta:last-author"};

	public static final String LAST_MODIFIED = "last modified";
	protected static final String[] LAST_MODIFIED_ARRAY = {"meta:save-date", "Last-Save-Date", "dcterms:modified", "Last-Modified", "modified"};

	public static final String CREATION_DATE = "creation date";
	protected static final String[] CREATION_DATE_ARRAY = {"date", "dcterms:created", "Creation-Date", "meta:creation-date", "created"};

	public static final String PRINT_DATE = "print date";
	protected static final String[] PRINT_DATE_ARRAY = {"Last-Printed", "meta:print-date"};
	
	public static final String EDIT_TIME = "edit time";
	protected static final String[] EDIT_TIME_ARRAY = {"Edit-Time"};
	
	public static final String REVISION_NUMBER = "revision number";
	protected static final String[] REVISION_NUMBER_ARRAY = {"cp:revision", "Revision-Number", "editing-cycles"};
	
	public static final String WORD_COUNT = "word count";
	protected static final String[] WORD_COUNT_ARRAY = {"Word-Count", "meta:word-count", "nbWord"};
	
	public static final String TABLE_COUNT = "table count";
	protected static final String[] TABLE_COUNT_ARRAY = {"Table-Count", "meta:table-count", "nbTab"};
	
	public static final String CHARACTER_COUNT = "character count";
	protected static final String[] CHARACTER_COUNT_ARRAY = {"meta:character-count", "Character Count", "nbCharacter"};
	
	public static final String OBJECT_COUNT = "object count";
	protected static final String[] OBJECT_COUNT_ARRAY = {"Object-Count", "nbObject", "meta:object-count"};
		
	public static final String PAGE_COUNT = "page count";
	protected static final String[] PAGE_COUNT_ARRAY = {"meta:page-count", "Page-Count", "xmpTPg:NPages", "nbPage"};

	public static final String PARAGRAPH_COUNT = "paragraph count";
	protected static final String[] PARAGRAPH_COUNT_ARRAY = {"meta:paragraph-count", "Paragraph-Count", "nbPara"};

	public static final String IMAGE_COUNT = "image count";
	protected static final String[] IMAGE_COUNT_ARRAY = {"nbImg", "meta:image-count", "Image-Count"};

			
	
	public static final String TITLE = "title";
	protected static final String[] TITLE_ARRAY = {"title", "dc:title"};

	public static final String SUBJECT = "subject";
	protected static final String[] SUBJECT_ARRAY = {"dc:subject", "subject", "cp:subject"};

	public static final String COMMENTS = "comments";
	protected static final String[] COMMENTS_ARRAY = {"Comments", "w:comments", "comment", "Jpeg Comment"};
	
	public static final String LANGUAGE = "language";
	protected static final String[] LANGUAGE_ARRAY = {"language", "dc:language"};
	
	public static final String SOFTWARE = "software";
	protected static final String[] SOFTWARE_ARRAY = {"Application-Name", "generator", "producer", "xmp:CreatorTool"};

//	public static final String SOFTWARE2 = "software2";
//	protected static final String[] SOFTWARE2_ARRAY = {"xmp:CreatorTool"};
		
	public static final String KEYWORDS = "keywords";
	protected static final String[] KEYWORDS_ARRAY = {"meta:keyword", "Keywords"};

	public static final String AUDIO_COMPRESSOR = "audio compressor";
	protected static final String[] AUDIO_COMPRESSOR_ARRAY = {"xmpDM:audioCompressor"};
	
	public static final String AUDIO_CHANNEL_TYPE = "audio channel type";
	protected static final String[] AUDIO_CHANNEL_TYPE_ARRAY = {"xmpDM:audioChannelType"};
	
	public static final String CHANNELS = "channels";
	protected static final String[] CHANNELS_ARRAY = {"channels", "Chroma NumChannels"};
	
	public static final String SAMPLE_RATE = "sample rate";
	protected static final String[] SAMPLE_RATE_ARRAY = {"samplerate", "xmpDM:audioSampleRate"};
	
	public static final String VERSION = "version";
	protected static final String[] VERSION_ARRAY = {"version"};
	
	public static final String TOTAL_DATA_RATE = "total data rate";
	protected static final String[] TOTAL_DATA_RATE_ARRAY = {"totaldatarate"};
	
	public static final String VIDEO_DATA_RATE = "video data rate";
	protected static final String[] VIDEO_DATA_RATE_ARRAY = {"videodatarate"};
	
	public static final String AUDIO_DATA_RATE = "audio data rate";
	protected static final String[] AUDIO_DATA_RATE_ARRAY = {"audiodatarate"};
	
	public static final String DURATION = "duration";
	protected static final String[] DURATION_ARRAY = {"duration", "totalduration"};
	
	public static final String FRAME_RATE = "frame rate";
	protected static final String[] FRAME_RATE_ARRAY = {"framerate"};

	public static final String BYTE_LENGTH = "byte length";
	protected static final String[] BYTE_LENGTH_ARRAY = {"bytelength"};
	
	public static final String WIDTH = "width";
	protected static final String[] WIDTH_ARRAY = {"width", "Image Width", "tiff:ImageWidth"};
	
	public static final String HEIGHT = "height";
	protected static final String[] HEIGHT_ARRAY = {"height", "Image Height", "tiff:ImageLength"};

	public static final String HAS_VIDEO = "has video";
	protected static final String[] HAS_VIDEO_ARRAY = {"hasVideo"};
	
	public static final String HAS_AUDIO = "has audio";
	protected static final String[] HAS_AUDIO_ARRAY = {"hasAudio"};
	
	public static final String CAN_SEEK_ON_TIME = "can seek on time";
	protected static final String[] CAN_SEEK_ON_TIME_ARRAY = {"canseekontime"};
	
	public static final String BITS_PER_SAMPLE = "bits per sample";
	protected static final String[] BITS_PER_SAMPLE_ARRAY = {"Data Precision", "tiff:BitsPerSample"};
	
	public static final String NUMBER_OF_COMPONENTS = "number of components";
	protected static final String[] NUMBER_OF_COMPONENTS_ARRAY = {"Number of Components"};

	public static final String OTHERS = "others";
    protected static final String[][] ARRAYS = {
    	CONTENT_TYPE_ARRAY, 
    	AUTHOR_ARRAY, 
    	
//    	COMPANY_ARRAY, 
//    	LAST_AUTHOR_ARRAY,
    	LAST_MODIFIED_ARRAY,
    	CREATION_DATE_ARRAY,
    	PRINT_DATE_ARRAY,
    	EDIT_TIME_ARRAY,
    	REVISION_NUMBER_ARRAY,
    	WORD_COUNT_ARRAY,
    	TABLE_COUNT_ARRAY,
    	CHARACTER_COUNT_ARRAY,
    	OBJECT_COUNT_ARRAY,
    	PAGE_COUNT_ARRAY,
    	PARAGRAPH_COUNT_ARRAY,
    	IMAGE_COUNT_ARRAY,
    	TITLE_ARRAY,
    	SUBJECT_ARRAY,
    	COMMENTS_ARRAY,
    	LANGUAGE_ARRAY,
    	SOFTWARE_ARRAY,
//    	SOFTWARE2_ARRAY,
    	KEYWORDS_ARRAY,
    	AUDIO_COMPRESSOR_ARRAY,
    	AUDIO_CHANNEL_TYPE_ARRAY,
    	CHANNELS_ARRAY,
    	SAMPLE_RATE_ARRAY,
    	VERSION_ARRAY,
    	TOTAL_DATA_RATE_ARRAY,
    	VIDEO_DATA_RATE_ARRAY,
    	AUDIO_DATA_RATE_ARRAY,
    	DURATION_ARRAY,
    	FRAME_RATE_ARRAY,
    	BYTE_LENGTH_ARRAY,
    	WIDTH_ARRAY,
    	HEIGHT_ARRAY,
    	HAS_VIDEO_ARRAY,
    	HAS_AUDIO_ARRAY,
    	CAN_SEEK_ON_TIME_ARRAY,
    	BITS_PER_SAMPLE_ARRAY,
    	//NUMBER_OF_COMPONENTS_ARRAY,
    };
	
	
}
