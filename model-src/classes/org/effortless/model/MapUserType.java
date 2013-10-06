package org.effortless.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;

import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class MapUserType implements UserType {

//	static final Log log = LogFactory.getLog(MapUserType.class);

	public MapUserType() {
	}

	public int[] sqlTypes() {
		return new int[]{Types.BLOB};
	}

	@SuppressWarnings("unchecked")
	public Class returnedClass() {
		return java.util.Map.class;
	}

	public boolean equals(Object x, Object y) {
		return (x == y) || (x != null && y != null && (x.equals(y)));
	}

	public Object deepCopy(Object o) {

		if (o == null) {
			return null;
		}

		
		Map deepCopy = new HashMap();
		deepCopy.putAll((Map)o);
		return deepCopy;
	}

	public boolean isMutable() {
		return true;
	}

	public Object assemble(Serializable cached, Object owner) {
		return deepCopy(cached);
	}

	public Serializable disassemble(Object value) {
		return (Serializable) deepCopy(value);
	}

	public Object replace(Object original, Object target, Object owner) {
		return deepCopy(original);
	}

	public int hashCode(Object x) {
		return x.hashCode();
	}

	 public Object nullSafeGet(ResultSet inResultSet, String[] names,
	 SessionImplementor si, Object o) throws HibernateException,
	 SQLException {
		 Map result = null;
				 
		 InputStream is = inResultSet.getBinaryStream(names[0]);
		 if (is != null) {
			 try {
				 Kryo kryo = new Kryo();
				 
				 Input input = new Input(is);
				 result = kryo.readObject(input, HashMap.class);			 
			 }
			 catch (Throwable t) {
				 throw new HibernateException(t);
			 }
		 }
			return result;
	 }
	
	 public void nullSafeSet(PreparedStatement inPreparedStatement, Object o, int i,
	 SessionImplementor si) throws HibernateException, SQLException {
		 if (o != null) {
				Map val = (Map) o;
				
				Kryo kryo = new Kryo();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				Output output = new Output(baos);
				kryo.writeObject(output, val);
				output.close();
				
				byte[] bytes = baos.toByteArray();
				
				InputStream is = new ByteArrayInputStream(bytes);
				long length = bytes.length;
				inPreparedStatement.setBlob(i, is, length);
		 }
		 else {
				inPreparedStatement.setNull(i, Types.BLOB);
		 }
	 }

}
