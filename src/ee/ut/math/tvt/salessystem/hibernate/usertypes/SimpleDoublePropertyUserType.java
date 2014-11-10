package ee.ut.math.tvt.salessystem.hibernate.usertypes;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javafx.beans.property.SimpleDoubleProperty;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

public class SimpleDoublePropertyUserType implements UserType {

	@Override
	public Object assemble(Serializable cached, Object owner)
			throws HibernateException {
		return cached;
	}

	@Override
	public Object deepCopy(Object value) throws HibernateException {
		if (value==null)
			return null;
        if (!(value instanceof SimpleDoubleProperty))
            throw new UnsupportedOperationException("Can't convert " + value.getClass());
		return new SimpleDoubleProperty(((SimpleDoubleProperty)value).get());
	}

	@Override
	public Serializable disassemble(Object value) throws HibernateException {
        if (!(value instanceof SimpleDoubleProperty))
            throw new UnsupportedOperationException("Can't convert " + value.getClass());
		return ((SimpleDoubleProperty)value).get();
	}

	@Override
	public boolean equals(Object a, Object b) throws HibernateException {
		return a.equals(b);
	}

	@Override
	public int hashCode(Object o) throws HibernateException {
		return o.hashCode();
	}

	@Override
	public boolean isMutable() {
		return true;
	}

	@Override
	public Object nullSafeGet(ResultSet rs, String[] names,
			SessionImplementor arg2, Object arg3) throws HibernateException,
			SQLException {
		double value = rs.getDouble(names[0]);
		return new SimpleDoubleProperty(value);
	}

	@Override
	public void nullSafeSet(PreparedStatement stmt, Object value, int index,
			SessionImplementor arg3) throws HibernateException, SQLException {
		if(value == null){
			stmt.setNull(index, Types.DOUBLE);
			return;
		}
		
        if (!(value instanceof SimpleDoubleProperty))
            throw new UnsupportedOperationException("Can't convert " + value.getClass());
		stmt.setDouble(index, ((SimpleDoubleProperty)value).get());
	}

	@Override
	public Object replace(Object original, Object target, Object owner)
			throws HibernateException {
		return original;
	}

	@Override
	public Class returnedClass() {
		return SimpleDoubleProperty.class;
	}

	@Override
	public int[] sqlTypes() {
		return new int[]{Types.DOUBLE};
	}

}
