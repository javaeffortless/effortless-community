package org.effortless.ui.vm.fields;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import java.util.List;

import org.effortless.core.ClassUtils;
import org.effortless.core.ModelException;
import org.effortless.core.PropertyUtils;
import org.effortless.model.FileEntity;
import org.effortless.model.Filter;
import org.effortless.server.WebUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Messagebox;

public class FileFieldVM extends Object {

	public FileFieldVM () {
		super();
		initiate();
	}
	
	protected void initiate () {
		initiateValue();
	}
	
//	public void init (@org.zkoss.bind.annotation.ExecutionArgParam("args") java.util.Map args, 
	//@init('org.effortless.ui.RefFieldVM', type='org.effortless.icondb.icons.Icon', filter='', )
	@Init
	public void init(@BindingParam("type") String type, @BindingParam("item") Object item, @BindingParam("String") String property) {
		this.type = type;
		this.item = item;
		this.property = property;
		this.value = loadValue();
	}
	
	protected FileEntity loadValue() {
		return (FileEntity)PropertyUtils.getProperty(this.item, this.property);
	}

	protected Object item;
	protected String property;
	
	public String getFileName () {
		return (this.value != null ? this.value.getName() : "Upload");
	}
	
	protected org.zkoss.util.media.Media media;
	
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
	
	protected FileEntity value;
	
	protected void initiateValue () {
		this.value = null;
	}
	
	public FileEntity getValue () {
		return this.value;
	}
	
	public void setValue (FileEntity newValue) {
		this.value = newValue;
		saveValue();
	}
	
	protected void saveValue() {
		PropertyUtils.setProperty(this.item, this.property, this.value);
	}

	protected FileEntity newFile () {
		return (this.type != null ? (FileEntity)ClassUtils.newInstanceRE(this.type) : null);
	}
	
	public Boolean isEmpty () {
		return Boolean.valueOf((this.value != null));
	}
	
	public String getUpload () {
		return (this.value != null ? "false" : "true,maxsize=-1,native");		
	}

	@Command("download")
	public void download() {
		if (this.value != null) {
			try {
				Filedownload.save(this.value.getContent(), this.value.getContentType());
			} catch (FileNotFoundException e) {
				throw new ModelException(e);
			}
		}
	}
	
	@Command("upload")
	public void upload(@BindingParam("upEvent") UploadEvent event) {
		org.zkoss.util.media.Media media = event.getMedia();
		if (media != null) {
			this.value = (this.value != null ? this.value : newFile());
//			this.value.setContentType(media.getContentType());
//			this.value.setCreationDate(new java.util.Date());
//			this.value.setFrameRate(newValue);
//			this.value
			this.value.setContent(media.getStreamData(), media.getName());
		}
		else {
			setValue(null);
		}
		org.zkoss.bind.BindUtils.postNotifyChange(null, null, this, "value");
		org.zkoss.bind.BindUtils.postNotifyChange(null, null, this, "fileName");
		org.zkoss.bind.BindUtils.postNotifyChange(null, null, this, "upload");
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
