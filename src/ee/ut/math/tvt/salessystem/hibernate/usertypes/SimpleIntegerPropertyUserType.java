package ee.ut.math.tvt.salessystem.hibernate.usertypes;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javafx.beans.property.SimpleIntegerProperty;

import org.hibernate.HibernateException;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

public class SimpleIntegerPropertyUserType implements UserType {

	@Override
	public Object assemble(Serializable cached, Object owner)
			throws HibernateException {
		return cached;
	}

	@Override
	public Object deepCopy(Object value) throws HibernateException {
		if (value==null)
			return null;
        if (!(value instanceof SimpleIntegerProperty))
            throw new UnsupportedOperationException("Can't convert " + value.getClass());
		return new SimpleIntegerProperty(((SimpleIntegerProperty)value).get());
	}

	@Override
	public Serializable disassemble(Object value) throws HibernateException {
        if (!(value instanceof SimpleIntegerProperty))
            throw new UnsupportedOperationException("Can't convert " + value.getClass());
		return ((SimpleIntegerProperty)value).get();
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
		int value = rs.getInt(names[0]);
		return new SimpleIntegerProperty(value);
	}

	@Override
	public void nullSafeSet(PreparedStatement stmt, Object value, int index,
			SessionImplementor arg3) throws HibernateException, SQLException {
		if(value == null){
			stmt.setNull(index, Types.INTEGER);
			return;
		}
		
        if (!(value instanceof SimpleIntegerProperty))
            throw new UnsupportedOperationException("Can't convert " + value.getClass());
		stmt.setInt(index, ((SimpleIntegerProperty)value).get());
	}

	@Override
	public Object replace(Object original, Object target, Object owner)
			throws HibernateException {
		return original;
	}

	@Override
	public Class returnedClass() {
		return SimpleIntegerProperty.class;
	}

	@Override
	public int[] sqlTypes() {
		return new int[]{Types.INTEGER};
	}

}
