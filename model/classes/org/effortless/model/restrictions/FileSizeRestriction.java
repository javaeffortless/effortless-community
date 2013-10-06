package org.effortless.model.restrictions;

import java.io.File;

import org.effortless.model.IFile;

public class FileSizeRestriction extends PropertyRestriction {

	public FileSizeRestriction () {
		super();
	}
	
	protected void initiate () {
		super.initiate();
		initiateMinSize();
		initiateMaxSize();
	}
	
	protected Long minSize;
	
	protected void initiateMinSize () {
		this.minSize = null;
	}
	
	public Long getMinSize () {
		return this.minSize;
	}
	
	public void setMinSize (Long newValue) {
		this.minSize = newValue;
	}
	
	protected Long maxSize;
	
	protected void initiateMaxSize () {
		this.maxSize = null;
	}
	
	public Long getMaxSize () {
		return this.maxSize;
	}
	
	public void setMaxSize (Long newValue) {
		this.maxSize = newValue;
	}
	
	protected void doCheck (Object data, Object property) {
		if (!(property instanceof IFile)) {
//			throwError(dao, data, property, "incompatible");
		}
		else {
			IFile value = (IFile)property;
			if (value != null) {
				long size = getSize(value);
				Long minSize = getMinSize();
				if (minSize != null) {
					long _minSize = minSize.longValue();
					if (size < _minSize) {
						throwError(data, property, "min");
					}
				}
				Long maxSize = getMaxSize();
				if (maxSize != null) {
					long _maxSize = maxSize.longValue();
					if (size > _maxSize) {
						throwError(data, property, "max");
					}
				}
			}
		}
	}
	
	protected long getSize (IFile value) {
		long result = 0L;
		File file = (value != null ? value.getContent() : null);
		result = (file != null ? file.length() : 0L);
		return result;
	}
	
}
