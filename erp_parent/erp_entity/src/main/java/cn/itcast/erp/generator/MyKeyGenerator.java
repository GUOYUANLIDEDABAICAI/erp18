package cn.itcast.erp.generator;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

public class MyKeyGenerator extends SequenceStyleGenerator {

	@Override
	public Serializable generate(SessionImplementor session, Object object) throws HibernateException {
		Long key = (Long)super.generate(session, object);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String newKey = String.format("%s%03d", sdf.format(new Date()),key);
		return Long.valueOf(newKey);
	}
	
	/*public static void main(String[] args){
		Long key = 15l;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String newKey = String.format("%s%02d", sdf.format(new Date()),999);
		System.out.println(newKey);
	}*/
}
