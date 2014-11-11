package ee.ut.math.tvt.salessystem.hibernate.usertypes;

import java.io.Serializable;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Calendar;

import javafx.beans.property.SimpleIntegerProperty;

import org.hibernate.HibernateException;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

public class LocalDateTimeUserType implements UserType {

	@Override
	public Object assemble(Serializable cached, Object owner)
			throws HibernateException {
		return cached;
	}

	@Override
	public Object deepCopy(Object value) throws HibernateException {
		if (value==null)
			return null;
        if (!(value instanceof LocalDateTime))
            throw new UnsupportedOperationException("Can't convert " + value.getClass());
        LocalDateTime dateTime = (LocalDateTime)value;
        LocalDateTime copyDateTime = LocalDateTime.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth(), dateTime.getHour(), dateTime.getMinute(), dateTime.getSecond());
		return copyDateTime;
	}

	@Override
	public Serializable disassemble(Object value) throws HibernateException {
        if (!(value instanceof LocalDateTime))
            throw new UnsupportedOperationException("Can't convert " + value.getClass());
		return (LocalDateTime)value;
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
		return false;
	}

	@Override
	public Object nullSafeGet(ResultSet rs, String[] names,
			SessionImplementor arg2, Object arg3) throws HibernateException,
			SQLException {
		Timestamp value = rs.getTimestamp(names[0]);
		return value.toLocalDateTime();
	}

	@Override
	public void nullSafeSet(PreparedStatement stmt, Object value, int index,
			SessionImplementor arg3) throws HibernateException, SQLException {
		if(value == null){
			stmt.setNull(index, Types.TIMESTAMP);
			return;
		}
		
        if (!(value instanceof LocalDateTime))
            throw new UnsupportedOperationException("Can't convert " + value.getClass());
        LocalDateTime dateTime = (LocalDateTime)value;
        stmt.setString(index, dateTime.toLocalDate().toString() + " " + dateTime.toLocalTime().toString());
	}

	@Override
	public Object replace(Object original, Object target, Object owner)
			throws HibernateException {
		return original;
	}

	@Override
	public Class returnedClass() {
		return LocalDateTime.class;
	}

	@Override
	public int[] sqlTypes() {
		return new int[]{Types.TIMESTAMP};
	}

}
