package org.effortless.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
import org.effortless.core.FileUtils;
import org.hibernate.HibernateException;

import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

public class ObjectUserType implements UserType {

//	static final Log log = LogFactory.getLog(ObjectUserType.class);

	public ObjectUserType() {
	}

	public int[] sqlTypes() {
		return new int[Types.BLOB];
	}

	@SuppressWarnings("unchecked")
	public Class returnedClass() {
		return java.io.File.class;
	}

	public boolean equals(Object x, Object y) {
		return (x == y) || (x != null && y != null && (x.equals(y)));
	}

	public Object deepCopy(Object o) {

		if (o == null) {
			return null;
		}

		File deepCopy = null;
		deepCopy = ((File)o).getAbsoluteFile();
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
		 File result = null;
				 
		 InputStream is = inResultSet.getBinaryStream(names[0]);
		 if (is != null) {
			 try {
					result = File.createTempFile(".tmp", ".blob");
				} catch (IOException e) {
					throw new HibernateException(e);
				}
				 
				 try {
					FileUtils.copyInputStreamToFile(is, result);
				} catch (Exception e) {
					throw new HibernateException(e);
				}
				 try {
					is.close();
				} catch (IOException e) {
					throw new HibernateException(e);
				}
		 }
			return result;
	 }
	
	 public void nullSafeSet(PreparedStatement inPreparedStatement, Object o, int i,
	 SessionImplementor si) throws HibernateException, SQLException {
		 if (o != null) {
				File val = (File) o;
				InputStream is = null;
				try {
					is = new FileInputStream(val);
				} catch (FileNotFoundException e) {
					throw new HibernateException(e);
				}
				long length = val.length();
				inPreparedStatement.setBinaryStream(i, is, length);
		 }
		 else {
				inPreparedStatement.setNull(i, Types.BLOB);
		 }
	 }

}
