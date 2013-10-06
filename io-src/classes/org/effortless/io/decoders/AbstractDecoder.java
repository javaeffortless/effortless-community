package org.effortless.io.decoders;

public abstract class AbstractDecoder<T extends Object> extends Object implements DataDecoder<T> {

	public AbstractDecoder () {
		super();
		initiate();
	}
	
	protected void initiate () {
	}
	
}
