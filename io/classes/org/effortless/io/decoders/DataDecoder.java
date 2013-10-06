package org.effortless.io.decoders;

/**
 * 

BooleanDecoder, 
DateDecoder, 
DoubleDecoder, 
EnumDecoder, 
FileDecoder, 
IntegerDecoder, 
LongDecoder, 
StringDecoder, 
SqlDateDecoder, 
SqlTimeDecoder, 

FloatDecoder, 
BigDecimalDecoder, 
BigIntegerDecoder, 
ByteDecoder, 
CalendarDecoder, 
CharacterDecoder, 
CharsetDecoder, 
ClassDecoder, 
CSVDecoder, 
FreeMarkerTemplateDecoder, 
MappingDecoder, 
MVELExpressionEvaluatorDecoder, 
ShortDecoder, 
URIDecoder, 
URLDecoder 
 * 
 * 
 * @author jesus
 *
 * @param <T>
 */
public interface DataDecoder<T extends Object> {

	public T decode (String value);
	
}
