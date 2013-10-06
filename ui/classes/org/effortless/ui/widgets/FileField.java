package org.effortless.ui.widgets;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.effortless.core.ClassUtils;
import org.effortless.core.FileUtils;
import org.effortless.core.IOUtils;
import org.effortless.core.ModelException;
import org.effortless.core.ObjectUtils;
import org.effortless.core.StringUtils;
import org.effortless.model.FileEntity;
import org.effortless.ui.UiApplication;
import org.effortless.ui.impl.CteUi;
import org.zkoss.util.media.AMedia;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.IdSpace;
import org.zkoss.zk.ui.annotation.ComponentAnnotation;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menupopup;
	 
public class FileField extends AbstractField<FileEntity> {
	    
	@Wire
	protected Button btn;
	
//	@Wire
	protected Menupopup wEditPopup;
	
	@Wire
	protected Menuitem wDownload;

	@Wire
	protected Menuitem wBrowse;
	
	@Wire
	protected Menuitem wDelete;
	
	@Wire
	protected Menuitem wProperties;
	 
    public FileField() {
    	super();
    }

    protected void initiate () {
    	super.initiate();
		this._uploadListener = null;
    }
    
	protected void _updateProperty(String propertyName, Object oldValue,
			Object newValue) {
		if ("readonly".equals(propertyName)) {
			_updateReadonly();
		}
	}
    
    
    protected void _updateReadonly() {
		// TODO Auto-generated method stub
    	boolean readonly = (this.readonly != null && this.readonly.booleanValue());
    	if (this.wEditPopup != null) {
//	    	Menuitem wDownload = (Menuitem)this.wEditPopup.getFellowIfAny("wDownload");
//	    	Menuitem wBrowse = (Menuitem)this.wEditPopup.getFellowIfAny("wBrowse");
//	    	Menuitem wDelete = (Menuitem)this.wEditPopup.getFellowIfAny("wDelete");
//	    	Menuitem wProperties = (Menuitem)this.wEditPopup.getFellowIfAny("wProperties");

	    	this.wDownload.setVisible(true);
	    	this.wBrowse.setVisible(!readonly);
	    	this.wDelete.setVisible(!readonly);
	    	this.wProperties.setVisible(true);
	    	
    	}
    	

    	boolean hasContent = !this.isEmpty();
    	this.btn.setVisible(true);
    	if (readonly && !hasContent) {
    		this.btn.setVisible(false);
    	}
    	else {
    		_updateUpload();
    	}
    	
    	_updateSclass();
	}

    protected EventListener _uploadListener;
    
    protected EventListener doGetUploadListener () {
    	if (this._uploadListener == null) {
    		final FileField _this = this;
    		this._uploadListener = new EventListener () {

				@Override
				public void onEvent(Event event) throws Exception {
					_this._upload((UploadEvent)event);
				}
    			
    		};
    	}
    	return this._uploadListener;
    }
    
    protected void _updateUpload () {
    	boolean hasContent = !this.isEmpty();
    	
		if (!hasContent) {
			this.btn.setUpload(this.getUpload());
			this.btn.addEventListener(Events.ON_UPLOAD, doGetUploadListener());
//			String popupCmd = this.btn.getPopup();
//			this.btn.setAttribute(CteUi.FILE_FIELD_POPUP_CMD_CACHE, popupCmd);
			this.btn.setPopup((String)null);
		}
		else {
			if (this._uploadListener != null) {
				this.btn.removeEventListener(Events.ON_UPLOAD, this._uploadListener);
				this._uploadListener = null;
			}
			this.btn.setUpload("false");
//			String popupCmd = (String)this.btn.getAttribute(CteUi.FILE_FIELD_POPUP_CMD_CACHE);
//			this.btn.setPopup(popupCmd);
			initPopup();
		}
    }
    
    protected void _updateSclass () {
    	boolean readonly = (this.readonly != null && this.readonly.booleanValue());
    	boolean hasContent = !this.isEmpty();
    	
    	String sclass = "file-field";
    	sclass += (readonly ? " file-field-readonly" : "");
    	sclass += (hasContent ? " mime-type-" + _loadMimeType() : "");

		this.btn.setSclass(sclass);
    }

	protected String _loadMimeType() {
		String result = null;
		result = (result == null && "pdf".equals(this.format) ? "pdf" : result);
		result = (result == null && "mp3".equals(this.format) ? "audio" : result);
		result = (result == null && "zip".equals(this.format) ? "compress" : result);
		result = (result == null && "png".equals(this.format) ? "image" : result);
		result = (result == null && "jpg".equals(this.format) ? "image" : result);
		result = (result == null && "jpeg".equals(this.format) ? "image" : result);
		result = (result == null && "gif".equals(this.format) ? "image" : result);
		result = (result == null && "odt".equals(this.format) ? "office" : result);
		result = (result == null && "ods".equals(this.format) ? "office" : result);
		result = (result == null && "mpg".equals(this.format) ? "video" : result);
		result = (result == null && "doc".equals(this.format) ? "word" : result);
		result = (result == null && "pdf".equals(this.format) ? "pdf" : result);
		
		result = (result != null ? result : "unknown");
		return result;
	}

	protected String name;
    
	@ComponentAnnotation("@ZKBIND(ACCESS=both, SAVE_EVENT=onChange)")
	public String getName () {
		return this.name;
//		return (this.value != null ? this.value.getName() : "Upload");
	}
	
	public void setName (String newValue) {
//		boolean binded = _applyBinding("fileName", newValue);
//		if (!binded && !ObjectUtils.equals(this.value, newValue)) {
		boolean binded = _applyBinding("name", newValue);
		if (!binded && !ObjectUtils.equals(this.name, newValue)) {
			this.name = newValue;
			notifyChange(null);
			_refreshName();
			initUi_Name();
		}
		
//		this.value = (this.value != null ? this.value : newFile());
//		this.value.setName(newValue);
	}
	
	protected void _refreshName () {
		
	}
	
	protected void initUi_Name () {
		if (this.name != null) {
			this.btn.setLabel(this.name);
		}
		else {
			this.btn.setLabel("Examinar...");
		}
	}
	
	protected File content;
	
	@ComponentAnnotation("@ZKBIND(ACCESS=both, SAVE_EVENT=onChange)")
	public File getContent () {
//		return (this.value != null ? this.value.getContent() : null);
		return this.content;
	}
	
	public void setContent (File newValue) {
//		System.out.println("WRITING VIEW " + newValue);
		if (!ObjectUtils.equals(this.content, newValue)) {
			this.content = newValue;
//			this.value = (this.value != null ? this.value : newFile());
//			this.value.setContent(newValue);
			notifyChange(null);
			_refreshContent();
			initUi_Content();
		}
	}

	public void setContent (String newValue) {
//		System.out.println("WRITING VIEW " + newValue);
		boolean binded = _applyBinding("content", newValue);
	}

	
	
	protected void _refreshContent() {
		boolean readonly = (this.readonly != null && this.readonly.booleanValue());
		
    	boolean hasContent = !this.isEmpty();

    	this.btn.setVisible(true);
    	if (readonly && !hasContent) {
	    	this.btn.setVisible(false);
    	}
    	else {
        	String name = (this.content != null ? this.content.getName() : null);
    		setName(name);
    		_updateUpload();
    	}
		

		this.wDelete.setVisible(hasContent);
		this.wDownload.setVisible(hasContent);
		this.wProperties.setVisible(hasContent);
//		this.wBrowse.setVisible(true);
		_updateSclass();
	}
	
	protected void initUi_Content() {
		
	}
	
	protected String format;
	
	@ComponentAnnotation("@ZKBIND(ACCESS=both, SAVE_EVENT=onChange)")
	public String getFormat () {
//		return (this.value != null ? this.value.getFormat() : null);
		return this.format;
	}
	
	public void setFormat (String newValue) {
//		this.value = (this.value != null ? this.value : newFile());
//		this.value.setFormat(newValue);
		boolean binded = _applyBinding("format", newValue);
		if (!binded && !ObjectUtils.equals(this.format, newValue)) {
			this.format = newValue;
//			this.value = (this.value != null ? this.value : newFile());
//			this.value.setContent(newValue);
			notifyChange(null);
			_refreshFormat();
			initUi_Format();
		}
	}
	
	protected void _refreshFormat () {
		
	}
	
	protected void initUi_Format () {
		
	}
	
	protected String contentType;
	
	@ComponentAnnotation("@ZKBIND(ACCESS=both, SAVE_EVENT=onChange)")
	public String getContentType () {
//		return (this.value != null ? this.value.getContentType() : null);
		return this.contentType;
	}
	
	public void setContentType (String newValue) {
//		this.value = (this.value != null ? this.value : newFile());
//		this.value.setContentType(newValue);
		boolean binded = _applyBinding("contentType", newValue);
		if (!binded && !ObjectUtils.equals(this.contentType, newValue)) {
			this.contentType = newValue;
//			this.value = (this.value != null ? this.value : newFile());
//			this.value.setContent(newValue);
			notifyChange(null);
			_refreshContentType();
			initUi_ContentType();
		}
	}
	
	protected void _refreshContentType() {
		// TODO Auto-generated method stub
		
	}

	protected void initUi_ContentType() {
		// TODO Auto-generated method stub
		
	}

	protected String type;
	
	protected void initiateType () {
		this.type = null;
	}
	
	public String getType () {
		return this.type;
	}
	
	public void setType (String newValue) {
		this.type = newValue;
	}
	
//	protected FileEntity value;
//	
//	protected void initiateValue () {
//		this.value = null;
//	}
//	
//	@ComponentAnnotation("@ZKBIND(ACCESS=both, SAVE_EVENT=onChange)")
//	public FileEntity getValue () {
//		return this.value;
//	}
//	
//	public void setValue (FileEntity newValue) {
//		this.value = newValue;
//		initUi();
//	}
	
	protected FileEntity newFile () {
		FileEntity result = null;
		if (this.type == null) {
			String propertyName = _getBindProperty("value");
			Class<?> classType = UiApplication.getPropertyType(this, propertyName);
			this.type = (classType != null ? classType.getName() : null);
			this.type = (this.type != null ? this.type : FileEntity.class.getName());
		}
		if (this.type != null) {
			result = (FileEntity)ClassUtils.newInstanceRE(this.type);
		}
		else {
			result = new FileEntity();
		}
		return result;
	}
	
	@ComponentAnnotation("@ZKBIND(ACCESS=load, SAVE_EVENT=onChange)")
	public Boolean isEmpty () {
		Boolean result = null;
		boolean empty = (this.value == null);
		empty = (!empty ? !this.value.hasContent() : empty);
		result = Boolean.valueOf(empty);
		return result;
	}
	
	@ComponentAnnotation("@ZKBIND(ACCESS=load, SAVE_EVENT=onChange)")
	public String getUpload () {
//		return (!isEmpty() ? "false" : "true,maxsize=-1,native");
		return (!isEmpty() ? "false" : "true");
	}

    @Listen("onClick = #wDownload")
	public void _download() {
		if (true || this.value != null) {
			try {
				File fileContent = getContent();//this.value.getContent();
				if (fileContent != null) {
				String format = getFormat();//this.value.getFormat()
				String contentType = getContentType();//this.value.getContentType()
				String fileName = this.getName();//this.value.getName()
				AMedia media = new AMedia(fileName, format, contentType, fileContent, true);
//				String prefix = (this.type != null ? StringUtils.lastPart(this.type, ".") : null);
//				File newFile = FileUtils.renameToTempDir(fileContent, this.value.getName(), prefix);
//				Filedownload.save(newFile, this.value.getContentType());
				Filedownload.save(media);
				}
			} catch (FileNotFoundException e) {
				throw new ModelException(e);
			}
		}
	}
	
    protected void initUi () {
    	initUi_Name();
//    	String label = this.getName();
//    	label = (label != null ? label : "Examinar...");
//		this.btn.setLabel(label);
		this.btn.setUpload(this.getUpload());
		initPopup();
		_updateReadonly();
    }
    
    protected void initPopup () {
    	this.wEditPopup = (Menupopup)this.getFirstChild().getNextSibling();
    	String popupId = this.wEditPopup.getUuid();
    	this.wEditPopup.setId(popupId);
		String popupCmd = popupId + ", position=before_start, delay=900";
		this.btn.setPopup(popupCmd);
		
		
//    	Menuitem wDownload = (Menuitem)this.wEditPopup.getFellowIfAny("wDownload");
//    	Menuitem wBrowse = (Menuitem)this.wEditPopup.getFellowIfAny("wBrowse");
//    	Menuitem wDelete = (Menuitem)this.wEditPopup.getFellowIfAny("wDelete");
//    	Menuitem wProperties = (Menuitem)this.wEditPopup.getFellowIfAny("wProperties");
		
    	this.wDownload.setImage(_images("download_file.png"));
    	this.wBrowse.setImage(_images("browse_file.png"));
    	this.wDelete.setImage(_images("delete_file.png"));
    	this.wProperties.setImage(_images("properties_file.png"));
    }
    
    protected void _updateValue () {
    	if (this.value != null) {
    		this.setContent(this.value.getContent());
    		this.setFormat(this.value.getFormat());
    		this.setContentType(this.value.getContentType());
    		this.setName(this.value.getName());
    	}
    }
    
    @Listen("onClick = #wDelete")
    public void _delete () {
    	boolean readonly = (this.readonly != null && this.readonly.booleanValue());
    	if (!readonly) {
    		_doDelete();
    	}
    }
    
    protected void _doDelete () {
    	this.setValue(null);
    	this.setContent((File)null);
    	this.setName(null);
    	this.setFormat(null);
    	this.setContentType(null);
    }
    
    
//    @Listen("onUpload = #btn")
    @Listen("onUpload = #wBrowse")
	public void _upload(UploadEvent event) {
    	boolean readonly = (this.readonly != null && this.readonly.booleanValue());
    	if (!readonly) {
			org.zkoss.util.media.Media media = event.getMedia();
			if (media != null) {
				this.wEditPopup.close();
				_doCheckMediaUpload(media);
				if (_checkBinded("value")) {
					this.value = (this.value != null ? this.value : newFile());
		//			this.value.setContentType(media.getContentType());
		//			this.value.setCreationDate(new java.util.Date());
		//			this.value.setFrameRate(newValue);
		//			this.value
					this.value.setContent(media.getStreamData(), media.getName());
					_updateValue();
				}
				else {
					java.io.InputStream input = media.getStreamData();
					String fileName = media.getName();
					
					String className = this.getClass().getSimpleName();
					File _folder = FileUtils.createTempFolder(className);
					
					File _file = new File(_folder, fileName);
					
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
	//				this.persist();
					
				}
	//			_notifyChange("value");
	//			_notifyChange("fileName");
	//			_notifyChange("content");
			}
			else {
				this.value = null;
			}
			Events.postEvent("onChange", this, null);
			setValue(this.value);
			initUi();
	//		if (media instanceof org.zkoss.image.Image) {
	//			org.zkoss.image.Image img = (org.zkoss.image.Image) media;
	//			if (img.getWidth() > img.getHeight()){
	//				if (img.getHeight() > 300) {
	////					pics.setHeight("300px");
	////					pics.setWidth(img.getWidth() * 300 / img.getHeight() + "px");
	//				}
	//			}
	//			if (img.getHeight() > img.getWidth()){
	//				if (img.getWidth() > 400) {
	////					pics.setWidth("400px");
	////					pics.setHeight(img.getHeight() * 400 / img.getWidth() + "px");
	//				}
	//			}
	////			pics.setContent(img);
	//		} else {
	//			Messagebox.show("Not an image: "+media, "Error", Messagebox.OK, Messagebox.ERROR);
	////			break; //not to show too many errors
	//		}
    	}
	}

	protected void _doCheckMediaUpload(Media media) {
	}

	protected void _refreshValue () {
		super._refreshValue();
		if (this.value != null) {
			_updateValue();
		}
		else {
	    	this.setContent((File)null);
	    	this.setName(null);
	    	this.setFormat(null);
	    	this.setContentType(null);
		}
		
	}
    
    
}	
