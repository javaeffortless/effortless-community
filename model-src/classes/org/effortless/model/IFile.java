package org.effortless.model;

import java.io.File;
import java.util.Date;

public interface IFile {

	public String getName ();
	public void setName(String newValue);

	public String getDescription ();
	public void setDescription(String newValue);
	
	public String getComment ();
	public void setComment(String newValue);
	
	public String getContentType();
	public void setContentType(String newValue);

	public String getFormat ();
	public void setFormat(String newValue);
	
	public File getContent ();
	public void setContent(File newValue);

	public Long getSize ();
	public void setSize(Long newValue);

	public String getHash1 ();
	public void setHash1(String newValue);

	public String getHash2 ();
	public void setHash2(String newValue);

	public String getHash3 ();
	public void setHash3(String newValue);
	
	public String getPath ();
	public void setPath(String newValue);
	
	public Date getCreationDate();
	public void setCreationDate(Date newValue);

	public Date getLastModification();
	public void setLastModification(Date newValue);
	
	public Boolean getEmbedded ();
	public void setEmbedded(Boolean newValue);
	
	public String getTitle();
	public void setTitle(String newValue);

	public String getSubject();
	public void setSubject(String newValue);

	public String getAuthor();
	public void setAuthor(String newValue);

	public String getKeywords();
	public void setKeywords(String newValue);
	
	public String getCompress();
	public void setCompress(String newValue);
	
//	public long getContentLength();
//	public InputStream getInputStream();

}
