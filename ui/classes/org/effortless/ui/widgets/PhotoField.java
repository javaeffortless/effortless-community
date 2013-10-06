package org.effortless.ui.widgets;

import java.io.IOException;

import org.zkoss.image.AImage;
import org.zkoss.image.Image;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.UiException;
	 
public class PhotoField extends FileField {

    public PhotoField() {
    	super();
    }

	protected void _doCheckMediaUpload(Media media) {
		String ctype = media.getContentType();
		String format = media.getFormat();
		if (!checkImage(ctype, format)) {
//		if (media instanceof org.zkoss.image.Image) {
//			org.zkoss.image.Image img = (org.zkoss.image.Image) media;
//			if (img.getWidth() > img.getHeight()){
//				if (img.getHeight() > 300) {
//		//					pics.setHeight("300px");
//		//					pics.setWidth(img.getWidth() * 300 / img.getHeight() + "px");
//						}
//					}
//					if (img.getHeight() > img.getWidth()){
//						if (img.getWidth() > 400) {
//		//					pics.setWidth("400px");
//		//					pics.setHeight(img.getHeight() * 400 / img.getWidth() + "px");
//						}
//					}
//		//			pics.setContent(img);
//		} else {
			throw new UiException("It's not an image");
//			Messagebox.show("Not an image: "+media, "Error", Messagebox.OK, Messagebox.ERROR);
		}
	}

	protected boolean checkImage(String ctype, String format) {
		boolean result = false;
		result = (!result && "jpeg".equals(format) ? true : result);
		result = (!result && "jpg".equals(format) ? true : result);
		result = (!result && "png".equals(format) ? true : result);
		result = (!result && "gif".equals(format) ? true : result);
		result = (!result && "tiff".equals(format) ? true : result);
		result = (!result && "bmp".equals(format) ? true : result);
		return result;
	}

	protected void _refreshContent() {
		super._refreshContent();
		
    	boolean hasContent = !this.isEmpty();
    	this.btn.setLabel("");
    	if (hasContent) {
    		Image image = null;
			try {
				image = new AImage(this.value.getContent());
			} catch (IOException e) {
				throw new UiException(e);
			}//this.value.getContent()
    		this.btn.setImageContent(image);
//    		this.btn.setWidth("64px");
//    		this.btn.setHeight("64px");
    	}
	}
	
    protected void _updateSclass () {
    	String sclass = "photo-field";
		this.btn.setSclass(sclass);
    }

    protected void _doDelete () {
    	super._doDelete();
    	this.btn.setImage(null);
    }

	protected void initUi_Name () {
    	boolean hasContent = !this.isEmpty();
    	if (hasContent) {
    		this.btn.setLabel("");
    	}
    	else {
			this.btn.setLabel("Examinar...");
		}
	}
	
    
    
}	
