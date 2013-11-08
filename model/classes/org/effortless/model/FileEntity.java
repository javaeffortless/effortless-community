package org.effortless.model;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.util.Date;
import java.util.Map;
import java.util.zip.Adler32;
import java.util.zip.CRC32;

import javax.activation.MimetypesFileTypeMap;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

import org.effortless.core.FileUtils;
import org.effortless.core.FilenameUtils;
import org.effortless.core.Hex;
import org.effortless.core.IOUtils;
import org.effortless.core.MetadataFiles;
import org.effortless.core.ModelException;
import org.effortless.core.ObjectUtils;
import org.effortless.util.FileHashes;
import org.hibernate.annotations.Tuplizer;
import org.hibernate.tuple.entity.PojoEntityTuplizer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;


//http://code.google.com/p/xxhash/ Para calcular huellas
//https://github.com/jpountz/lz4-java Para comprimir con rapidez  (http://fastcompression.blogspot.com.es/p/lz4.html)
//@Entity
//@Table(name="APP_FILES")
//@javax.persistence.SequenceGenerator(name="sequence_id", sequenceName="app_files")
@MappedSuperclass
@Tuplizer(impl = FileEntityTuplizer.class)
public class FileEntity<Type extends FileEntity<Type>> extends AbstractIdEntity<Type> implements IFile {

	public FileEntity () {
		super();
	}
	
	public static Filter<FileEntity> listBy () {
		return new EntityFilter(FileEntity.class);
	}

	protected void initiate () {
		super.initiate();

		initiateName ();
		initiateDescription ();
		initiateComment ();
		initiateContentType();
		initiateFormat ();
		
		initiateContent ();

		initiateSize ();
		initiateHash1 ();
		initiateHash2 ();
		initiateHash3 ();
		
		initiatePath ();
		
		initiateCreationDate();
		initiateLastModification();
		
		initiateEmbedded ();
		
		initiateTitle();
		initiateSubject();
		initiateAuthor();
		initiateKeywords();
		
		initiateCompress();
	}

	protected String name;
	
	protected void initiateName () {
		this.name = null;
	}
	
	@javax.persistence.Column(name="FILE_NAME")
	@javax.persistence.Basic(fetch=javax.persistence.FetchType.EAGER)
	public String getName() {
		return this.name;
	}
	
	public void setName(String newValue) {
		if (true) {
			_setProperty("name", this.name, this.name = newValue);
		}
		else {
			String oldValue = this.name;
			if (!ObjectUtils.equals(oldValue, newValue)) {
				this.name = newValue;
				firePropertyChange("name", oldValue, newValue);
			}
		}
	}

	protected String description;
	
	protected void initiateDescription () {
		this.description = null;
	}
	
	@javax.persistence.Column(name="FILE_DESCRIPTION")
	@javax.persistence.Basic(fetch=javax.persistence.FetchType.EAGER)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String newValue) {
		if (true) {
			_setProperty("description", this.description, this.description = newValue);
		}
		else {
			String oldValue = this.description;
			if (!ObjectUtils.equals(oldValue, newValue)) {
				this.description = newValue;
				firePropertyChange("description", oldValue, newValue);
			}
		}
	}

	protected String comment;
	
	protected void initiateComment () {
		this.comment = null;
	}
	
	@javax.persistence.Column(name="FILE_COMMENT")
	@javax.persistence.Basic(fetch=javax.persistence.FetchType.EAGER)
	public String getComment() {
		return this.comment;
	}

	public void setComment(String newValue) {
		if (true) {
			_setProperty("comment", this.comment, this.comment = newValue);
		}
		else {
			String oldValue = this.comment;
			if (!ObjectUtils.equals(oldValue, newValue)) {
				this.comment = newValue;
				firePropertyChange("comment", oldValue, newValue);
			}
		}
	}

	protected String contentType;
	
	protected void initiateContentType () {
		this.contentType = null;
	}
	
	@javax.persistence.Column(name="FILE_CONTENT_TYPE")
	@javax.persistence.Basic(fetch=javax.persistence.FetchType.EAGER)
	public String getContentType() {
		return this.contentType;
	}

	public void setContentType(String newValue) {
		if (true) {
			_setProperty("contentType", this.contentType, this.contentType = newValue);
		}
		else {
			String oldValue = this.contentType;
			if (!ObjectUtils.equals(oldValue, newValue)) {
				this.contentType = newValue;
				firePropertyChange("contentType", oldValue, newValue);
			}
		}
	}

//	protected Date date;
//	
//	protected void initiateDate () {
//		this.date = null;
//	}
//	
//	@javax.persistence.Column(name="CDATE")
//	@javax.persistence.Basic(fetch=javax.persistence.FetchType.EAGER)
//	public Date getDate() {
//		return this.date;
//	}
//
//	public void setDate(Date newValue) {
//		Date oldValue = this.date;
//		if (!ObjectUtils.equals(oldValue, newValue)) {
//			this.date = newValue;
//			firePropertyChange("date", oldValue, newValue);
//		}
//	}

	protected String format;

	protected void initiateFormat() {
		this.format = null;
	}

	@javax.persistence.Column(name="FILE_FORMAT")
	@javax.persistence.Basic(fetch=javax.persistence.FetchType.EAGER)
	public String getFormat() {
		return this.format;
	}

	public void setFormat(String newValue) {
		if (true) {
			_setProperty("format", this.format, this.format = newValue);
		}
		else {
			String oldValue = this.format;
			if (!ObjectUtils.equals(oldValue, newValue)) {
				this.format = newValue;
				firePropertyChange("format", oldValue, newValue);
			}
		}
	}

//	protected String type;
//
//	protected void initiateType() {
//		this.type = null;
//	}
//
//	@javax.persistence.Column(name="TYPE")
//	@javax.persistence.Basic(fetch=javax.persistence.FetchType.EAGER)
//	public String getType() {
//		return this.type;
//	}
//
//	public void setType(String newValue) {
//		String oldValue = this.type;
//		if (!ObjectUtils.equals(oldValue, newValue)) {
//			this.type = newValue;
//			firePropertyChange("type", oldValue, newValue);
//		}
//	}

	protected File content;

	protected void initiateContent() {
		this.content = null;
	}

	@javax.persistence.Column(name="FILE_CONTENT")
	@Lob
	@javax.persistence.Basic(fetch=javax.persistence.FetchType.LAZY)
	@org.hibernate.annotations.Type(type="org.effortless.model.FileUserType")
	public File getContent() {
		return this.content;
	}

	public void setContent(File newValue) {
		if (true) {
			_setProperty("content", this.content, this.content = newValue);
		}
		else {
			File oldValue = this.content;
			if (!ObjectUtils.equals(oldValue, newValue)) {
				this.content = newValue;
				firePropertyChange("content", oldValue, newValue);
				updateContent();
			}
		}
	}

	protected void _doChangeProperty (String propertyName, Object oldValue, Object newValue) {
		if ("content".equals(propertyName)) {
			updateContent();
		}
	}
	
	
//	protected String toMimetype (File file) {
//		String result = null;
//		result = new MimetypesFileTypeMap().getContentType(file);
//	    return result;
//	}
	
	protected String toMimetype(File file) {
		String result = null;
		if (file != null) {
			String fileUrl = file.getAbsolutePath();
			FileNameMap fileNameMap = URLConnection.getFileNameMap();
			result = fileNameMap.getContentTypeFor(fileUrl);
		}
		return result;
	}
	
//	protected String[] toHashes (File file) {
//		String[] result = null;
//		result = new String[3];
//		if (file != null) {
//
//			String[] aHashes = new String[] {"MD5", "SHA-256", "SHA-512"};
//			boolean flag = false;
//			for (int i = 0; i < aHashes.length; i++) {
//				if (aHashes[i] != null) {
//					flag = true;
//					break;
//				}
//			}
//	        if (flag) {
//		        try {
//			        FileInputStream is = new FileInputStream(file);
//			        int read = -1;
//			        byte[] buffer = new byte[8192];
//
//		            MessageDigest[] digests = new MessageDigest[aHashes.length];
//		            
//	            	for (int i = 0; i < digests.length; i++) {
//			            digests[i] = MessageDigest.getInstance(aHashes[i]);
//	            	}
//		            
//		            while ((read = is.read(buffer)) > 0) {
//		            	for (int i = 0; i < digests.length; i++) {
//		            		digests[i].update(buffer, 0, read);
//		            	}
//		            }
//		            
//	            	for (int i = 0; i < digests.length; i++) {
//			            byte[] hash = digests[i].digest();
//			            String hashText = String.valueOf(Hex.encodeHex(hash));
//			            result[i] = hashText;
//	            	}
//
//		        } 
//		        catch (Throwable t) {
//		        	throw new ModelException(t);
//		        }
//	        }
//		}
//		return result;
//	}
	
	public static final String KEY_CLASS_NEEDS = "needs_" + FileEntity.class.getName() + "_class";
	public static final String KEY_APP_NEEDS = "needs_" + FileEntity.class.getName() + "_app";
	
//	public InputStream getInputStream () throws IOException {
//		InputStream result = null;
//		File content = getContent();
//		if (content != null) {
//			result = new FileInputStream(content);
//		}
//		return result;
//	}
	
//	public long getContentLength () {
//		long result = 0L;
//		File content = getContent();
//		result = (content != null ? content.length() : 0L);
//		return result;
//	}
	
	public void setContent (String file) throws ModelException {
		File _file = new File(file);
		setContent(_file);
	}
	
	public void setContent (java.io.Reader reader) throws ModelException {
		File _file = null;
		try {
			_file = File.createTempFile(".file", ".tmp");
		} catch (IOException e) {
			throw new ModelException(e);
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(_file);
		} catch (FileNotFoundException e) {
			throw new ModelException(e);
		}
		try {
			IOUtils.copy(reader, fos);
		} catch (IOException e) {
			throw new ModelException(e);
		}
		
		IOUtils.closeQuietly(reader);
		IOUtils.closeQuietly(fos);
	}
	
	public void setContent (java.io.InputStream input) throws ModelException {
		File _file = null;
		try {
			_file = File.createTempFile(".file", ".tmp");
		} catch (IOException e) {
			throw new ModelException(e);
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(_file);
		} catch (FileNotFoundException e) {
			throw new ModelException(e);
		}
		try {
			IOUtils.copy(input, fos);
		} catch (IOException e) {
			throw new ModelException(e);
		}

		IOUtils.closeQuietly(input);
		IOUtils.closeQuietly(fos);
		
		this.content = _file;
	}

	public void setContent (java.io.InputStream input, String name) throws ModelException {
		String className = this.getClass().getSimpleName();
		File _folder = FileUtils.createTempFolder(className);
		
		File _file = new File(_folder, name);
		
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(_file);
		} catch (FileNotFoundException e) {
			throw new ModelException(e);
		}
		try {
			IOUtils.copy(input, fos);
		} catch (IOException e) {
			throw new ModelException(e);
		}

		IOUtils.closeQuietly(input);
		IOUtils.closeQuietly(fos);
		
		this.setContent(_file);
//		this.persist();
	}

	public boolean hasContent () {
		boolean result = false;
		File content = getContent();
		result = (content != null ? content.exists() : false);
		return result;
	}
	
	public void clearContent () {
		setContent((File)null);
	}
	
	@javax.persistence.Transient
	public boolean isClearContent () {
		boolean result = false;
		File content = getContent();
		result = (content == null);
		return result;
	}

	protected Long size;

	protected void initiateSize() {
		this.size = null;
	}

	@javax.persistence.Column(name="FILE_SIZE")
	@javax.persistence.Basic(fetch=javax.persistence.FetchType.EAGER)
	public Long getSize() {
		return this.size;
	}

	public void setSize(Long newValue) {
		if (true) {
			_setProperty("size", this.size, this.size = newValue);
		}
		else {
			Long oldValue = this.size;
			if (!ObjectUtils.equals(oldValue, newValue)) {
				this.size = newValue;
				firePropertyChange("size", oldValue, newValue);
			}
		}
	}

	@javax.persistence.Transient
	public String getSizeText () {
		String result = null;
		if (this.size != null) {
			boolean typeUnit = false;
			long bytes = this.size.longValue();
			
			int unit = typeUnit ? 1000 : 1024;
			
			if (bytes < unit) {
				result = bytes + " B";
			}
			else {
			    int exp = (int) (Math.log(bytes) / Math.log(unit));
			    String pre = (typeUnit ? "kMGTPE" : "KMGTPE").charAt(exp-1) + (typeUnit ? "" : ""/*"i"*/);
			    result = String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
			}
		}
		return result;
	}
	
	protected String hash1;

	protected void initiateHash1() {
		this.hash1 = null;
	}

	@javax.persistence.Column(name="FILE_HASH1")
	@javax.persistence.Basic(fetch=javax.persistence.FetchType.EAGER)
	public String getHash1() {
		return this.hash1;
	}

	public void setHash1(String newValue) {
		if (true) {
			_setProperty("hash1", this.hash1, this.hash1 = newValue);
		}
		else {
			String oldValue = this.hash1;
			if (!ObjectUtils.equals(oldValue, newValue)) {
				this.hash1 = newValue;
				firePropertyChange("hash1", oldValue, newValue);
			}
		}
	}

	protected String hash2;

	protected void initiateHash2() {
		this.hash2 = null;
	}

	@javax.persistence.Column(name="FILE_HASH2")
	@javax.persistence.Basic(fetch=javax.persistence.FetchType.EAGER)
	public String getHash2() {
		return this.hash2;
	}

	public void setHash2(String newValue) {
		if (true) {
			_setProperty("hash2", this.hash2, this.hash2 = newValue);
		}
		else {
			String oldValue = this.hash2;
			if (!ObjectUtils.equals(oldValue, newValue)) {
				this.hash2 = newValue;
				firePropertyChange("hash2", oldValue, newValue);
			}
		}
	}
	
	protected String hash3;

	protected void initiateHash3() {
		this.hash3 = null;
	}

	@javax.persistence.Column(name="FILE_HASH3")
	@javax.persistence.Basic(fetch=javax.persistence.FetchType.EAGER)
	public String getHash3() {
		return this.hash3;
	}

	public void setHash3(String newValue) {
		if (true) {
			_setProperty("hash3", this.hash3, this.hash3 = newValue);
		}
		else {
			String oldValue = this.hash3;
			if (!ObjectUtils.equals(oldValue, newValue)) {
				this.hash3 = newValue;
				firePropertyChange("hash3", oldValue, newValue);
			}
		}
	}
	
	protected String path;

	protected void initiatePath() {
		this.path = null;
	}

	@javax.persistence.Column(name="FILE_PATH")
	@javax.persistence.Basic(fetch=javax.persistence.FetchType.EAGER)
	public String getPath() {
		return this.path;
	}

	public void setPath(String newValue) {
		if (true) {
			_setProperty("path", this.path, this.path = newValue);
		}
		else {
			String oldValue = this.path;
			if (!ObjectUtils.equals(oldValue, newValue)) {
				this.path = newValue;
				firePropertyChange("path", oldValue, newValue);
			}
		}
	}
	
	protected Date registerDate;

	protected void initiateRegisterDate() {
		this.registerDate = null;
	}

	@javax.persistence.Column(name = "FILE_REGISTER_DATE")
	@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
	public Date getRegisterDate() {
		return this.registerDate;
	}

	public void setRegisterDate(Date newValue) {
		if (true) {
			_setProperty("registerDate", this.registerDate, this.registerDate = newValue);
		}
		else {
			Date oldValue = this.getRegisterDate();
			if (!ObjectUtils.equals(oldValue, newValue)) {
				this.registerDate = newValue;
				firePropertyChange("registerDate", oldValue, newValue);
			}
		}
	}
	
	protected Date lastModification;

	protected void initiateLastModification() {
		this.lastModification = null;
	}

	@javax.persistence.Column(name="FILE_LAST_MODIFICATION")
	@javax.persistence.Basic(fetch=javax.persistence.FetchType.EAGER)
	public Date getLastModification() {
		return this.lastModification;
	}

	public void setLastModification(Date newValue) {
		if (true) {
			_setProperty("lastModification", this.lastModification, this.lastModification = newValue);
		}
		else {
			Date oldValue = this.lastModification;
			if (!ObjectUtils.equals(oldValue, newValue)) {
				this.lastModification = newValue;
				firePropertyChange("lastModification", oldValue, newValue);
			}
		}
	}

	protected Boolean embedded;

	protected void initiateEmbedded() {
		this.embedded = Boolean.TRUE;
	}

	@javax.persistence.Column(name="FILE_EMBEDDED")
	@javax.persistence.Basic(fetch=javax.persistence.FetchType.EAGER)
	public Boolean getEmbedded() {
		return this.embedded;
	}

	public void setEmbedded(Boolean newValue) {
		if (true) {
			_setProperty("embedded", this.embedded, this.embedded = newValue);
		}
		else {
			Boolean oldValue = this.embedded;
			if (!ObjectUtils.equals(oldValue, newValue)) {
				this.embedded = newValue;
				firePropertyChange("embedded", oldValue, newValue);
			}
		}
	}

	protected String title;

	protected void initiateTitle() {
		this.title = null;
	}

	@javax.persistence.Column(name="FILE_TITLE")
	@javax.persistence.Basic(fetch=javax.persistence.FetchType.EAGER)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String newValue) {
		if (true) {
			_setProperty("title", this.title, this.title = newValue);
		}
		else {
			String oldValue = this.title;
			if (!ObjectUtils.equals(oldValue, newValue)) {
				this.title = newValue;
				firePropertyChange("title", oldValue, newValue);
			}
		}
	}
	
	protected String subject;

	protected void initiateSubject() {
		this.subject = null;
	}

	@javax.persistence.Column(name="FILE_SUBJECT")
	@javax.persistence.Basic(fetch=javax.persistence.FetchType.EAGER)
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String newValue) {
		if (true) {
			_setProperty("subject", this.subject, this.subject = newValue);
		}
		else {
			String oldValue = this.subject;
			if (!ObjectUtils.equals(oldValue, newValue)) {
				this.subject = newValue;
				firePropertyChange("subject", oldValue, newValue);
			}
		}
	}
	
	protected String author;

	protected void initiateAuthor() {
		this.author = null;
	}

	@javax.persistence.Column(name="FILE_AUTHOR")
	@javax.persistence.Basic(fetch=javax.persistence.FetchType.EAGER)
	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String newValue) {
		if (true) {
			_setProperty("author", this.author, this.author = newValue);
		}
		else {
			String oldValue = this.author;
			if (!ObjectUtils.equals(oldValue, newValue)) {
				this.author = newValue;
				firePropertyChange("author", oldValue, newValue);
			}
		}
	}
	
	protected String keywords;

	protected void initiateKeywords() {
		this.keywords = null;
	}

	@javax.persistence.Column(name="FILE_KEYWORDS")
	@javax.persistence.Basic(fetch=javax.persistence.FetchType.EAGER)
	public String getKeywords() {
		return this.keywords;
	}

	public void setKeywords(String newValue) {
		if (true) {
			_setProperty("keywords", this.keywords, this.keywords = newValue);
		}
		else {
			String oldValue = this.keywords;
			if (!ObjectUtils.equals(oldValue, newValue)) {
				this.keywords = newValue;
				firePropertyChange("keywords", oldValue, newValue);
			}
		}
	}

	protected String compress;

	protected void initiateCompress() {
		this.compress = null;
	}

	@javax.persistence.Column(name="FILE_COMPRESS")
	@javax.persistence.Basic(fetch=javax.persistence.FetchType.EAGER)
	public String getCompress() {
		return this.compress;
	}

	public void setCompress(String newValue) {
		if (true) {
			_setProperty("compress", this.compress, this.compress = newValue);
		}
		else {
			String oldValue = this.compress;
			if (!ObjectUtils.equals(oldValue, newValue)) {
				this.compress = newValue;
				firePropertyChange("compress", oldValue, newValue);
			}
		}
	}
	
	public String toString () {
		String result = null;
		String path = getPath();
		String sizeText = getSizeText();
		path = (path != null ? path : "");
		sizeText = (sizeText != null ? sizeText : "");
		result = path + " " + sizeText;
		return result;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	protected void updateContent () {
		File file = getContent();
		String absolutePath = (file != null ? file.getAbsolutePath() : null);

//		this.setCompress(newValue);

		Map<String, Object> metadata = MetadataFiles.read(file);
		String contentType = (String)metadata.get(MetadataFiles.CONTENT_TYPE);
		contentType = (contentType != null ? contentType : toMimetype(file));
		
		setAuthor((String)metadata.get(MetadataFiles.AUTHOR));
		setLastModified((Date)metadata.get(MetadataFiles.LAST_MODIFIED));
		setCreationDate((Date)metadata.get(MetadataFiles.CREATION_DATE));
		setPrintDate((Date)metadata.get(MetadataFiles.PRINT_DATE));
		setEditTimeSeconds((Integer)metadata.get(MetadataFiles.EDIT_TIME));

		setRevisionNumber((Integer)metadata.get(MetadataFiles.REVISION_NUMBER));
		setWordCount((Integer)metadata.get(MetadataFiles.WORD_COUNT));
		setTableCount((Integer)metadata.get(MetadataFiles.TABLE_COUNT));
		setCharacterCount((Integer)metadata.get(MetadataFiles.CHARACTER_COUNT));
		setObjectCount((Integer)metadata.get(MetadataFiles.OBJECT_COUNT));
		setPageCount((Integer)metadata.get(MetadataFiles.PAGE_COUNT));
		setParagraphCount((Integer)metadata.get(MetadataFiles.PARAGRAPH_COUNT));
		setImageCount((Integer)metadata.get(MetadataFiles.IMAGE_COUNT));

		setTitle((String)metadata.get(MetadataFiles.TITLE));
		setSubject((String)metadata.get(MetadataFiles.SUBJECT));
		setComment((String)metadata.get(MetadataFiles.COMMENTS));
		setLanguage((String)metadata.get(MetadataFiles.LANGUAGE));
		setSoftware((String)metadata.get(MetadataFiles.SOFTWARE));
		setKeywords((String)metadata.get(MetadataFiles.KEYWORDS));

		setAudioCompressor((String)metadata.get(MetadataFiles.AUDIO_COMPRESSOR));
		setAudioChannelType((String)metadata.get(MetadataFiles.AUDIO_CHANNEL_TYPE));

		setChannels((Integer)metadata.get(MetadataFiles.CHANNELS));
		setSampleRate((String)metadata.get(MetadataFiles.SAMPLE_RATE));
		setFileVersion((String)metadata.get(MetadataFiles.VERSION));
		
		setTotalDataRate((Double)metadata.get(MetadataFiles.TOTAL_DATA_RATE));
		setVideoDataRate((Double)metadata.get(MetadataFiles.VIDEO_DATA_RATE));
		setAudioDataRate((Double)metadata.get(MetadataFiles.AUDIO_DATA_RATE));
		setDuration((Double)metadata.get(MetadataFiles.DURATION));
		setFrameRate((Double)metadata.get(MetadataFiles.FRAME_RATE));
		setByteLength((Double)metadata.get(MetadataFiles.BYTE_LENGTH));

		setWidth((Integer)metadata.get(MetadataFiles.WIDTH));
		setHeight((Integer)metadata.get(MetadataFiles.HEIGHT));

		setHasVideo((Boolean)metadata.get(MetadataFiles.HAS_VIDEO));
		setHasAudio((Boolean)metadata.get(MetadataFiles.HAS_AUDIO));

		setCanSeekOnTime((Boolean)metadata.get(MetadataFiles.CAN_SEEK_ON_TIME));
		setBitsPerSample((String)metadata.get(MetadataFiles.BITS_PER_SAMPLE));
		
		
		this.setContentType(contentType);
		this.setRegisterDate(new java.util.Date());
		this.setFormat(FilenameUtils.getExtension(absolutePath));
		
		String[] toHashes = FileHashes.getInstance().tryToHashes(file);
		this.setHash1((toHashes != null && toHashes.length > 0 ? toHashes[0] : null));
		this.setHash2((toHashes != null && toHashes.length > 1 ? toHashes[1] : null));
		this.setHash3((toHashes != null && toHashes.length > 2 ? toHashes[2] : null));
		
		this.setLastModification((file != null ? new java.util.Date(file.lastModified()) : null));
		this.setName(FilenameUtils.getName(absolutePath));
		this.setPath(absolutePath);
		this.setSize((file != null ? Long.valueOf(file.length()) : null));

		
		
//		this.setTitle(newValue);
//		this.setSubject(newValue);
//		this.setAuthor(newValue);
//		this.setDescription(newValue);
//		this.setKeywords(newValue);
//		this.setComment(newValue);
	}

	

	protected Integer width;

	protected void initiateWidth() {
		this.width = null;
	}

	@javax.persistence.Column(name = "FILE_WIDTH")
	@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
	public Integer getWidth() {
		return this.width;
	}

	public void setWidth(Integer newValue) {
		if (true) {
			_setProperty("width", this.width, this.width = newValue);
		}
		else {
			Integer oldValue = this.getWidth();
			if (!ObjectUtils.equals(oldValue, newValue)) {
				this.width = newValue;
				firePropertyChange("width", oldValue, newValue);
			}
		}
	}
	
	protected Integer height;

	protected void initiateHeight() {
		this.height = null;
	}

	@javax.persistence.Column(name = "FILE_HEIGHT")
	@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
	public Integer getHeight() {
		return this.height;
	}

	public void setHeight(Integer newValue) {
		if (true) {
			_setProperty("height", this.height, this.height = newValue);
		}
		else {
			Integer oldValue = this.getHeight();
			if (!ObjectUtils.equals(oldValue, newValue)) {
				this.height = newValue;
				firePropertyChange("height", oldValue, newValue);
			}
		}
	}
	
	protected Boolean hasVideo;

	protected void initiateHasVideo() {
		this.hasVideo = null;
	}

	@javax.persistence.Column(name = "FILE_HAS_VIDEO")
	@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
	public Boolean getHasVideo() {
		return this.hasVideo;
	}

	public void setHasVideo(Boolean newValue) {
		if (true) {
			_setProperty("hasVideo", this.hasVideo, this.hasVideo = newValue);
		}
		else {
			Boolean oldValue = this.getHasVideo();
			if (!ObjectUtils.equals(oldValue, newValue)) {
				this.hasVideo = newValue;
				firePropertyChange("hasVideo", oldValue, newValue);
			}
		}
	}
	
	protected Boolean hasAudio;

	protected void initiateHasAudio() {
		this.hasAudio = null;
	}

	@javax.persistence.Column(name = "FILE_HAS_AUDIO")
	@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
	public Boolean getHasAudio() {
		return this.hasAudio;
	}

	public void setHasAudio(Boolean newValue) {
		if (true) {
			_setProperty("hasAudio", this.hasAudio, this.hasAudio = newValue);
		}
		else {
			Boolean oldValue = this.getHasAudio();
			if (!ObjectUtils.equals(oldValue, newValue)) {
				this.hasAudio = newValue;
				firePropertyChange("hasAudio", oldValue, newValue);
			}
		}
	}
	
	protected Boolean canSeekOnTime;

	protected void initiateCanSeekOnTime() {
		this.canSeekOnTime = null;
	}

	@javax.persistence.Column(name = "FILE_CAN_SEEK_ON_TIME")
	@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
	public Boolean getCanSeekOnTime() {
		return this.canSeekOnTime;
	}

	public void setCanSeekOnTime(Boolean newValue) {
		if (true) {
			_setProperty("canSeekOnTime", this.canSeekOnTime, this.canSeekOnTime = newValue);
		}
		else {
			Boolean oldValue = this.getCanSeekOnTime();
			if (!ObjectUtils.equals(oldValue, newValue)) {
				this.canSeekOnTime = newValue;
				firePropertyChange("canSeekOnTime", oldValue, newValue);
			}
		}
	}

	protected String bitsPerSample;

	protected void initiateBitsPerSample() {
		this.bitsPerSample = null;
	}

	@javax.persistence.Column(name = "FILE_BITS_PER_SAMPLE")
	@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
	public String getBitsPerSample() {
		return this.bitsPerSample;
	}

	public void setBitsPerSample(String newValue) {
		if (true) {
			_setProperty("bitsPerSample", this.bitsPerSample, this.bitsPerSample = newValue);
		}
		else {
			String oldValue = this.getBitsPerSample();
			if (!ObjectUtils.equals(oldValue, newValue)) {
				this.bitsPerSample = newValue;
				firePropertyChange("bitsPerSample", oldValue, newValue);
			}
		}
	}
	
	protected Double totalDataRate;

	protected void initiateTotalDataRate() {
		this.totalDataRate = null;
	}

	@javax.persistence.Column(name = "FILE_TOTAL_DATA_RATE")
	@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
	public Double getTotalDataRate() {
		return this.totalDataRate;
	}

	public void setTotalDataRate(Double newValue) {
		if (true) {
			_setProperty("totalDataRate", this.totalDataRate, this.totalDataRate = newValue);
		}
		else {
			Double oldValue = this.getTotalDataRate();
			if (!ObjectUtils.equals(oldValue, newValue)) {
				this.totalDataRate = newValue;
				firePropertyChange("totalDataRate", oldValue, newValue);
			}
		}
	}

	protected Double videoDataRate;

	protected void initiateVideoDataRate() {
		this.videoDataRate = null;
	}

	@javax.persistence.Column(name = "FILE_VIDEO_DATA_RATE")
	@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
	public Double getVideoDataRate() {
		return this.videoDataRate;
	}

	public void setVideoDataRate(Double newValue) {
		if (true) {
			_setProperty("videoDataRate", this.videoDataRate, this.videoDataRate = newValue);
		}
		else {
			Double oldValue = this.getVideoDataRate();
			if (!ObjectUtils.equals(oldValue, newValue)) {
				this.videoDataRate = newValue;
				firePropertyChange("videoDataRate", oldValue, newValue);
			}
		}
	}

	protected Double audioDataRate;

	protected void initiateAudioDataRate() {
		this.audioDataRate = null;
	}

	@javax.persistence.Column(name = "FILE_AUDIO_DATA_RATE")
	@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
	public Double getAudioDataRate() {
		return this.audioDataRate;
	}

	public void setAudioDataRate(Double newValue) {
		if (true) {
			_setProperty("audioDataRate", this.audioDataRate, this.audioDataRate = newValue);
		}
		else {
			Double oldValue = this.getAudioDataRate();
			if (!ObjectUtils.equals(oldValue, newValue)) {
				this.audioDataRate = newValue;
				firePropertyChange("audioDataRate", oldValue, newValue);
			}
		}
	}

	protected Double duration;

	protected void initiateDuration() {
		this.duration = null;
	}

	@javax.persistence.Column(name = "FILE_DURATION")
	@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
	public Double getDuration() {
		return this.duration;
	}

	public void setDuration(Double newValue) {
		if (true) {
			_setProperty("duration", this.duration, this.duration = newValue);
		}
		else {
			Double oldValue = this.getDuration();
			if (!ObjectUtils.equals(oldValue, newValue)) {
				this.duration = newValue;
				firePropertyChange("duration", oldValue, newValue);
			}
		}
	}

	protected Double frameRate;

	protected void initiateFrameRate() {
		this.frameRate = null;
	}

	@javax.persistence.Column(name = "FILE_FRAME_RATE")
	@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
	public Double getFrameRate() {
		return this.frameRate;
	}

	public void setFrameRate(Double newValue) {
		if (true) {
			_setProperty("frameRate", this.frameRate, this.frameRate = newValue);
		}
		else {
			Double oldValue = this.getFrameRate();
			if (!ObjectUtils.equals(oldValue, newValue)) {
				this.frameRate = newValue;
				firePropertyChange("frameRate", oldValue, newValue);
			}
		}
	}

	protected Double byteLength;

	protected void initiateByteLength() {
		this.byteLength = null;
	}

	@javax.persistence.Column(name = "FILE_BYTE_LENGTH")
	@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
	public Double getByteLength() {
		return this.byteLength;
	}

	public void setByteLength(Double newValue) {
		if (true) {
			_setProperty("byteLength", this.byteLength, this.byteLength = newValue);
		}
		else {
			Double oldValue = this.getByteLength();
			if (!ObjectUtils.equals(oldValue, newValue)) {
				this.byteLength = newValue;
				firePropertyChange("byteLength", oldValue, newValue);
			}
		}
	}
	
	protected String fileVersion;

	protected void initiateFileVersion() {
		this.fileVersion = null;
	}

	@javax.persistence.Column(name = "FILE_VERSION")
	@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
	public String getFileVersion() {
		return this.fileVersion;
	}

	public void setFileVersion(String newValue) {
		if (true) {
			_setProperty("fileVersion", this.fileVersion, this.fileVersion = newValue);
		}
		else {
			String oldValue = this.getFileVersion();
			if (!ObjectUtils.equals(oldValue, newValue)) {
				this.fileVersion = newValue;
				firePropertyChange("fileVersion", oldValue, newValue);
			}
		}
	}
	
	protected String sampleRate;

	protected void initiateSampleRate() {
		this.sampleRate = null;
	}

	@javax.persistence.Column(name = "FILE_SAMPLE_RATE")
	@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
	public String getSampleRate() {
		return this.sampleRate;
	}

	public void setSampleRate(String newValue) {
		if (true) {
			_setProperty("sampleRate", this.sampleRate, this.sampleRate = newValue);
		}
		else {
			String oldValue = this.getSampleRate();
			if (!ObjectUtils.equals(oldValue, newValue)) {
				this.sampleRate = newValue;
				firePropertyChange("sampleRate", oldValue, newValue);
			}
		}
	}
	
	protected Integer channels;

	protected void initiateChannels() {
		this.channels = null;
	}

	@javax.persistence.Column(name = "FILE_CHANNELS")
	@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
	public Integer getChannels() {
		return this.channels;
	}

	public void setChannels(Integer newValue) {
		if (true) {
			_setProperty("channels", this.channels, this.channels = newValue);
		}
		else {
			Integer oldValue = this.getChannels();
			if (!ObjectUtils.equals(oldValue, newValue)) {
				this.channels = newValue;
				firePropertyChange("channels", oldValue, newValue);
			}
		}
	}
	
	protected String audioChannelType;

	protected void initiateAudioChannelType() {
		this.audioChannelType = null;
	}

	@javax.persistence.Column(name = "FILE_AUDIO_CHANNEL_TYPE")
	@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
	public String getAudioChannelType() {
		return this.audioChannelType;
	}

	public void setAudioChannelType(String newValue) {
		if (true) {
			_setProperty("audioChannelType", this.audioChannelType, this.audioChannelType = newValue);
		}
		else {
			String oldValue = this.getAudioChannelType();
			if (!ObjectUtils.equals(oldValue, newValue)) {
				this.audioChannelType = newValue;
				firePropertyChange("audioChannelType", oldValue, newValue);
			}
		}
	}
	
	protected String audioCompressor;

	protected void initiateAudioCompressor() {
		this.audioCompressor = null;
	}

	@javax.persistence.Column(name = "FILE_AUDIO_COMPRESSOR")
	@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
	public String getAudioCompressor() {
		return this.audioCompressor;
	}

	public void setAudioCompressor(String newValue) {
		if (true) {
			_setProperty("audioCompressor", this.audioCompressor, this.audioCompressor = newValue);
		}
		else {
			String oldValue = this.getAudioCompressor();
			if (!ObjectUtils.equals(oldValue, newValue)) {
				this.audioCompressor = newValue;
				firePropertyChange("audioCompressor", oldValue, newValue);
			}
		}
	}
	
	protected String language;

	protected void initiateLanguage() {
		this.language = null;
	}

	@javax.persistence.Column(name = "FILE_LANGUAGE")
	@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
	public String getLanguage() {
		return this.language;
	}

	public void setLanguage(String newValue) {
		if (true) {
			_setProperty("language", this.language, this.language = newValue);
		}
		else {
			String oldValue = this.getLanguage();
			if (!ObjectUtils.equals(oldValue, newValue)) {
				this.language = newValue;
				firePropertyChange("language", oldValue, newValue);
			}
		}
	}

	protected String software;

	protected void initiateSoftware() {
		this.software = null;
	}

	@javax.persistence.Column(name = "FILE_SOFTWARE")
	@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
	public String getSoftware() {
		return this.software;
	}

	public void setSoftware(String newValue) {
		if (true) {
			_setProperty("software", this.software, this.software = newValue);
		}
		else {
			String oldValue = this.getSoftware();
			if (!ObjectUtils.equals(oldValue, newValue)) {
				this.software = newValue;
				firePropertyChange("software", oldValue, newValue);
			}
		}
	}
	
	protected Integer imageCount;

	protected void initiateImageCount() {
		this.imageCount = null;
	}

	@javax.persistence.Column(name = "FILE_IMAGE_COUNT")
	@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
	public Integer getImageCount() {
		return this.imageCount;
	}

	public void setImageCount(Integer newValue) {
		if (true) {
			_setProperty("imageCount", this.imageCount, this.imageCount = newValue);
		}
		else {
			Integer oldValue = this.getImageCount();
			if (!ObjectUtils.equals(oldValue, newValue)) {
				this.imageCount = newValue;
				firePropertyChange("imageCount", oldValue, newValue);
			}
		}
	}
	
	protected Integer paragraphCount;

	protected void initiateParagraphCount() {
		this.paragraphCount = null;
	}

	@javax.persistence.Column(name = "FILE_PARAGRAPH_COUNT")
	@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
	public Integer getParagraphCount() {
		return this.paragraphCount;
	}

	public void setParagraphCount(Integer newValue) {
		if (true) {
			_setProperty("paragraphCount", this.paragraphCount, this.paragraphCount = newValue);
		}
		else {
			Integer oldValue = this.getParagraphCount();
			if (!ObjectUtils.equals(oldValue, newValue)) {
				this.paragraphCount = newValue;
				firePropertyChange("paragraphCount", oldValue, newValue);
			}
		}
	}
	
	protected Integer pageCount;

	protected void initiatePageCount() {
		this.pageCount = null;
	}

	@javax.persistence.Column(name = "FILE_PAGE_COUNT")
	@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
	public Integer getPageCount() {
		return this.pageCount;
	}

	public void setPageCount(Integer newValue) {
		if (true) {
			_setProperty("pageCount", this.pageCount, this.pageCount = newValue);
		}
		else {
			Integer oldValue = this.getPageCount();
			if (!ObjectUtils.equals(oldValue, newValue)) {
				this.pageCount = newValue;
				firePropertyChange("pageCount", oldValue, newValue);
			}
		}
	}
	
	protected Integer objectCount;

	protected void initiateObjectCount() {
		this.objectCount = null;
	}

	@javax.persistence.Column(name = "FILE_OBJECT_COUNT")
	@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
	public Integer getObjectCount() {
		return this.objectCount;
	}

	public void setObjectCount(Integer newValue) {
		if (true) {
			_setProperty("objectCount", this.objectCount, this.objectCount = newValue);
		}
		else {
			Integer oldValue = this.getObjectCount();
			if (!ObjectUtils.equals(oldValue, newValue)) {
				this.objectCount = newValue;
				firePropertyChange("objectCount", oldValue, newValue);
			}
		}
	}
	
	protected Integer characterCount;

	protected void initiateCharacterCount() {
		this.characterCount = null;
	}

	@javax.persistence.Column(name = "FILE_CHARACTER_COUNT")
	@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
	public Integer getCharacterCount() {
		return this.characterCount;
	}

	public void setCharacterCount(Integer newValue) {
		if (true) {
			_setProperty("characterCount", this.characterCount, this.characterCount = newValue);
		}
		else {
			Integer oldValue = this.getCharacterCount();
			if (!ObjectUtils.equals(oldValue, newValue)) {
				this.characterCount = newValue;
				firePropertyChange("characterCount", oldValue, newValue);
			}
		}
	}
	
	protected Integer tableCount;

	protected void initiateTableCount() {
		this.tableCount = null;
	}

	@javax.persistence.Column(name = "FILE_TABLE_COUNT")
	@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
	public Integer getTableCount() {
		return this.tableCount;
	}

	public void setTableCount(Integer newValue) {
		if (true) {
			_setProperty("tableCount", this.tableCount, this.tableCount = newValue);
		}
		else {
			Integer oldValue = this.getTableCount();
			if (!ObjectUtils.equals(oldValue, newValue)) {
				this.tableCount = newValue;
				firePropertyChange("tableCount", oldValue, newValue);
			}
		}
	}
	
	protected Integer wordCount;

	protected void initiateWordCount() {
		this.wordCount = null;
	}

	@javax.persistence.Column(name = "FILE_WORD_COUNT")
	@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
	public Integer getWordCount() {
		return this.wordCount;
	}

	public void setWordCount(Integer newValue) {
		if (true) {
			_setProperty("wordCount", this.wordCount, this.wordCount = newValue);
		}
		else {
			Integer oldValue = this.getWordCount();
			if (!ObjectUtils.equals(oldValue, newValue)) {
				this.wordCount = newValue;
				firePropertyChange("wordCount", oldValue, newValue);
			}
		}
	}
	
	protected Integer revisionNumber;

	protected void initiateRevisionNumber() {
		this.revisionNumber = null;
	}

	@javax.persistence.Column(name = "FILE_REVISION_NUMBER")
	@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
	public Integer getRevisionNumber() {
		return this.revisionNumber;
	}

	public void setRevisionNumber(Integer newValue) {
		if (true) {
			_setProperty("revisionNumber", this.revisionNumber, this.revisionNumber = newValue);
		}
		else {
			Integer oldValue = this.getRevisionNumber();
			if (!ObjectUtils.equals(oldValue, newValue)) {
				this.revisionNumber = newValue;
				firePropertyChange("revisionNumber", oldValue, newValue);
			}
		}
	}
	
	protected Integer editTimeSeconds;

	protected void initiateEditTimeSeconds() {
		this.editTimeSeconds = null;
	}

	@javax.persistence.Column(name = "FILE_EDIT_TIME_SECONDS")
	@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
	public Integer getEditTimeSeconds() {
		return this.editTimeSeconds;
	}

	public void setEditTimeSeconds(Integer newValue) {
		if (true) {
			_setProperty("editTimeSeconds", this.editTimeSeconds, this.editTimeSeconds = newValue);
		}
		else {
			Integer oldValue = this.getEditTimeSeconds();
			if (!ObjectUtils.equals(oldValue, newValue)) {
				this.editTimeSeconds = newValue;
				firePropertyChange("editTimeSeconds", oldValue, newValue);
			}
		}
	}
	
	protected Date printDate;

	protected void initiatePrintDate() {
		this.printDate = null;
	}

	@javax.persistence.Column(name = "FILE_PRINT_DATE")
	@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
	public Date getPrintDate() {
		return this.printDate;
	}

	public void setPrintDate(Date newValue) {
		if (true) {
			_setProperty("printDate", this.printDate, this.printDate = newValue);
		}
		else {
			Date oldValue = this.getPrintDate();
			if (!ObjectUtils.equals(oldValue, newValue)) {
				this.printDate = newValue;
				firePropertyChange("printDate", oldValue, newValue);
			}
		}
	}

	protected Date lastModified;

	protected void initiateLastModified() {
		this.lastModified = null;
	}

	@javax.persistence.Column(name = "FILE_LASTMODIFIED")
	@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
	public Date getLastModified() {
		return this.lastModified;
	}

	public void setLastModified(Date newValue) {
		if (true) {
			_setProperty("lastModified", this.lastModified, this.lastModified = newValue);
		}
		else {
			Date oldValue = this.getLastModified();
			if (!ObjectUtils.equals(oldValue, newValue)) {
				this.lastModified = newValue;
				firePropertyChange("lastModified", oldValue, newValue);
			}
		}
	}

	protected Date creationDate;

	protected void initiateCreationDate() {
		this.creationDate = null;
	}

	@javax.persistence.Column(name = "FILE_CREATIONDATE")
	@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date newValue) {
		if (true) {
			_setProperty("creationDate", this.creationDate, this.creationDate = newValue);
		}
		else {
			Date oldValue = this.getCreationDate();
			if (!ObjectUtils.equals(oldValue, newValue)) {
				this.creationDate = newValue;
				firePropertyChange("creationDate", oldValue, newValue);
			}
		}
	}
	
	
	
	protected void doWrite (com.esotericsoftware.kryo.Kryo kryo, com.esotericsoftware.kryo.io.Output out) {
		super.doWrite(kryo, out);
		kryo.writeObjectOrNull(out, this.audioChannelType, String.class);
		kryo.writeObjectOrNull(out, this.audioCompressor, String.class);
		kryo.writeObjectOrNull(out, this.audioDataRate, Double.class);
		kryo.writeObjectOrNull(out, this.author, String.class);
		kryo.writeObjectOrNull(out, this.bitsPerSample, String.class);
		kryo.writeObjectOrNull(out, this.byteLength, Double.class);
		kryo.writeObjectOrNull(out, this.canSeekOnTime, Boolean.class);
		kryo.writeObjectOrNull(out, this.channels, Integer.class);
		kryo.writeObjectOrNull(out, this.characterCount, Integer.class);
		kryo.writeObjectOrNull(out, this.comment, String.class);
		kryo.writeObjectOrNull(out, this.compress, String.class);
//		kryo.writeObjectOrNull(out, this.content, File.class);
		kryo.writeObjectOrNull(out, this.contentType, String.class);
		kryo.writeObjectOrNull(out, this.creationDate, Date.class);
		kryo.writeObjectOrNull(out, this.description, String.class);
		kryo.writeObjectOrNull(out, this.duration, Double.class);
		kryo.writeObjectOrNull(out, this.editTimeSeconds, Integer.class);
		kryo.writeObjectOrNull(out, this.embedded, Boolean.class);
		kryo.writeObjectOrNull(out, this.fileVersion, String.class);
		kryo.writeObjectOrNull(out, this.format, String.class);
		kryo.writeObjectOrNull(out, this.frameRate, Double.class);
		kryo.writeObjectOrNull(out, this.hasAudio, Boolean.class);
		kryo.writeObjectOrNull(out, this.hash1, String.class);
		kryo.writeObjectOrNull(out, this.hash2, String.class);
		kryo.writeObjectOrNull(out, this.hash3, String.class);
		kryo.writeObjectOrNull(out, this.hasVideo, Boolean.class);
		kryo.writeObjectOrNull(out, this.height, Integer.class);
		kryo.writeObjectOrNull(out, this.imageCount, Integer.class);
		kryo.writeObjectOrNull(out, this.keywords, String.class);
		kryo.writeObjectOrNull(out, this.language, String.class);
		kryo.writeObjectOrNull(out, this.lastModification, Date.class);
		kryo.writeObjectOrNull(out, this.lastModified, Date.class);
		kryo.writeObjectOrNull(out, this.name, String.class);
		kryo.writeObjectOrNull(out, this.objectCount, Integer.class);
		kryo.writeObjectOrNull(out, this.pageCount, Integer.class);
		kryo.writeObjectOrNull(out, this.paragraphCount, Integer.class);
		kryo.writeObjectOrNull(out, this.path, String.class);
		kryo.writeObjectOrNull(out, this.printDate, Date.class);
		kryo.writeObjectOrNull(out, this.registerDate, Date.class);
		kryo.writeObjectOrNull(out, this.revisionNumber, Integer.class);
		kryo.writeObjectOrNull(out, this.sampleRate, String.class);
		kryo.writeObjectOrNull(out, this.size, Long.class);
		kryo.writeObjectOrNull(out, this.software, String.class);
		kryo.writeObjectOrNull(out, this.subject, String.class);
		kryo.writeObjectOrNull(out, this.tableCount, Integer.class);
		kryo.writeObjectOrNull(out, this.title, String.class);
		kryo.writeObjectOrNull(out, this.totalDataRate, Double.class);
		kryo.writeObjectOrNull(out, this.videoDataRate, Double.class);
		kryo.writeObjectOrNull(out, this.width, Integer.class);
		kryo.writeObjectOrNull(out, this.wordCount, Integer.class);
	}
	
	protected void doRead(com.esotericsoftware.kryo.Kryo kryo, com.esotericsoftware.kryo.io.Input in) {
		super.doRead(kryo, in);
		this.audioChannelType = kryo.readObjectOrNull(in, String.class);
		this.audioCompressor = kryo.readObjectOrNull(in, String.class);
		this.audioDataRate = kryo.readObjectOrNull(in, Double.class);
		this.author = kryo.readObjectOrNull(in, String.class);
		this.bitsPerSample = kryo.readObjectOrNull(in, String.class);
		this.byteLength = kryo.readObjectOrNull(in, Double.class);
		this.canSeekOnTime = kryo.readObjectOrNull(in, Boolean.class);
		this.channels = kryo.readObjectOrNull(in, Integer.class);
		this.characterCount = kryo.readObjectOrNull(in, Integer.class);
		this.comment = kryo.readObjectOrNull(in, String.class);
		this.compress = kryo.readObjectOrNull(in, String.class);
//		this.content = kryo.readObjectOrNull(in, File.class);
		this.contentType = kryo.readObjectOrNull(in, String.class);
		this.creationDate = kryo.readObjectOrNull(in, Date.class);
		this.description = kryo.readObjectOrNull(in, String.class);
		this.duration = kryo.readObjectOrNull(in, Double.class);
		this.editTimeSeconds = kryo.readObjectOrNull(in, Integer.class);
		this.embedded = kryo.readObjectOrNull(in, Boolean.class);
		this.fileVersion = kryo.readObjectOrNull(in, String.class);
		this.format = kryo.readObjectOrNull(in, String.class);
		this.frameRate = kryo.readObjectOrNull(in, Double.class);
		this.hasAudio = kryo.readObjectOrNull(in, Boolean.class);
		this.hash1 = kryo.readObjectOrNull(in, String.class);
		this.hash2 = kryo.readObjectOrNull(in, String.class);
		this.hash3 = kryo.readObjectOrNull(in, String.class);
		this.hasVideo = kryo.readObjectOrNull(in, Boolean.class);
		this.height = kryo.readObjectOrNull(in, Integer.class);
		this.imageCount = kryo.readObjectOrNull(in, Integer.class);
		this.keywords = kryo.readObjectOrNull(in, String.class);
		this.language = kryo.readObjectOrNull(in, String.class);
		this.lastModification = kryo.readObjectOrNull(in, Date.class);
		this.lastModified = kryo.readObjectOrNull(in, Date.class);
		this.name = kryo.readObjectOrNull(in, String.class);
		this.objectCount = kryo.readObjectOrNull(in, Integer.class);
		this.pageCount = kryo.readObjectOrNull(in, Integer.class);
		this.paragraphCount = kryo.readObjectOrNull(in, Integer.class);
		this.path = kryo.readObjectOrNull(in, String.class);
		this.printDate = kryo.readObjectOrNull(in, Date.class);
		this.registerDate = kryo.readObjectOrNull(in, Date.class);
		this.revisionNumber = kryo.readObjectOrNull(in, Integer.class);
		this.sampleRate = kryo.readObjectOrNull(in, String.class);
		this.size = kryo.readObjectOrNull(in, Long.class);
		this.software = kryo.readObjectOrNull(in, String.class);
		this.subject = kryo.readObjectOrNull(in, String.class);
		this.tableCount = kryo.readObjectOrNull(in, Integer.class);
		this.title = kryo.readObjectOrNull(in, String.class);
		this.totalDataRate = kryo.readObjectOrNull(in, Double.class);
		this.videoDataRate = kryo.readObjectOrNull(in, Double.class);
		this.width = kryo.readObjectOrNull(in, Integer.class);
		this.wordCount = kryo.readObjectOrNull(in, Integer.class);
	}
	
	
	
}
