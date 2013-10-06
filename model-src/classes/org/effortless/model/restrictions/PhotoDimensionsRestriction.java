package org.effortless.model.restrictions;

import java.io.IOException;

import org.apache.sanselan.ImageInfo;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;
import org.effortless.model.IFile;

public class PhotoDimensionsRestriction extends PropertyRestriction {

	public PhotoDimensionsRestriction () {
		super();
	}
	
	protected void initiate () {
		super.initiate();
		initiateMinWidth();
		initiateMaxWidth();
		initiateMinHeight();
		initiateMaxHeight();
	}
	
	protected Integer minWidth;
	
	protected void initiateMinWidth () {
		this.minWidth = null;
	}
	
	public Integer getMinWidth () {
		return this.minWidth;
	}
	
	public void setMinWidth (Integer newValue) {
		this.minWidth = newValue;
	}
	
	protected Integer maxWidth;
	
	protected void initiateMaxWidth () {
		this.maxWidth = null;
	}
	
	public Integer getMaxWidth () {
		return this.maxWidth;
	}
	
	public void setMaxWidth (Integer newValue) {
		this.maxWidth = newValue;
	}
	
	protected Integer minHeight;
	
	protected void initiateMinHeight () {
		this.minHeight = null;
	}
	
	public Integer getMinHeight () {
		return this.minHeight;
	}
	
	public void setMinHeight (Integer newValue) {
		this.minHeight = newValue;
	}
	
	protected Integer maxHeight;
	
	protected void initiateMaxHeight () {
		this.maxHeight = null;
	}
	
	public Integer getMaxHeight () {
		return this.maxHeight;
	}
	
	public void setMaxHeight (Integer newValue) {
		this.maxHeight = newValue;
	}

	protected void doCheck (Object data, Object property) {
		if (!(property instanceof IFile)) {
//			throwError(dao, data, property, "incompatible");
		}
		else {
			IFile value = (IFile)property;
			if (value != null) {
				ImageInfo imageInfo = null;
				try {
					imageInfo = Sanselan.getImageInfo(value.getContent(), null);
				} catch (ImageReadException e) {
					throwError(data, property, "notimage");
				} catch (IOException e) {
					throwError(data, property, "notimage");
				}
				
				int width = imageInfo.getWidth();
				Integer minWidth = getMinWidth();
				if (minWidth != null) {
					int _minWidth = minWidth.intValue();
					if (width < _minWidth) {
						throwError(data, property, "minWidth");
					}
				}
				Integer maxWidth = getMaxWidth();
				if (maxWidth != null) {
					int _maxWidth = maxWidth.intValue();
					if (width > _maxWidth) {
						throwError(data, property, "maxWidth");
					}
				}
				int height = imageInfo.getHeight();
				Integer minHeight = getMinHeight();
				if (minHeight != null) {
					int _minHeight = minHeight.intValue();
					if (height < _minHeight) {
						throwError(data, property, "minHeight");
					}
				}
				Integer maxHeight = getMaxHeight();
				if (maxHeight != null) {
					int _maxHeight = maxHeight.intValue();
					if (height > _maxHeight) {
						throwError(data, property, "maxHeight");
					}
				}
			}
		}
	}

//	protected int getWidth(FileEjb value) {
//		int result = 0;
//		if (value != null) {
//			try {
//				ImageInfo imageInfo = Sanselan.getImageInfo(value.getInputStream(), null);
//				result = imageInfo.getWidth();
//			} catch (ImageReadException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			throw new UnsupportedOperationException();
//			// TODO Auto-generated method stub
//		}
//		return result;
//	}
//
//	protected int getHeight(FileEjb value) {
//		int result = 0;
//		if (value != null) {
//			throw new UnsupportedOperationException();
//			// TODO Auto-generated method stub
//		}
//		return result;
//	}
	
}
