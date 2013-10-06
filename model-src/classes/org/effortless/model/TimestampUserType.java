package org.effortless.model;

import java.io.Serializable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;

import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

public class TimestampUserType implements UserType {

//	static final Log log = LogFactory.getLog(FileUserType.class);

	public TimestampUserType() {
	}

	public int[] sqlTypes() {
		return new int[]{Types.TIMESTAMP};
	}

	@SuppressWarnings("unchecked")
	public Class returnedClass() {
		return java.sql.Timestamp.class;
	}

	public boolean equals(Object x, Object y) {
		return (x == y) || (x != null && y != null && (x.equals(y)));
	}

	public Object deepCopy(Object o) {
		if (o == null) {
			return null;
		}

		java.sql.Timestamp deepCopy = new java.sql.Timestamp(((java.sql.Timestamp)o).getTime());
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
		 java.sql.Timestamp result = null;
				 
		 
		 result = inResultSet.getTimestamp(names[0]);
			return result;
	 }
	
	 public void nullSafeSet(PreparedStatement inPreparedStatement, Object o, int i,
	 SessionImplementor si) throws HibernateException, SQLException {
		 if (o != null) {
			    java.sql.Timestamp val = (java.sql.Timestamp) o;
				inPreparedStatement.setTimestamp(i, val);
		 }
		 else {
				inPreparedStatement.setNull(i, Types.TIMESTAMP);
		 }
	 }

}
