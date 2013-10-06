package org.effortless.io.decoders;

public interface StandardDecoders {

	public static final BooleanDecoder BOOLEAN = new BooleanDecoder();

	public static final DateDecoder DATE = new DateDecoder();
	
	public static final DoubleDecoder DOUBLE = new DoubleDecoder();
	
	public static final FileDecoder FILE = new FileDecoder();

	public static final IntegerDecoder INTEGER = new IntegerDecoder();

	public static final LongDecoder LONG = new LongDecoder();

	public static final StringDecoder STRING = new StringDecoder();
	
	public static final TimestampDecoder TIMESTAMP = new TimestampDecoder();
	
	public static final TimeDecoder TIME = new TimeDecoder();
	
	
}
