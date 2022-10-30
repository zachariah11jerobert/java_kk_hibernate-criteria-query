package com.jerome.tests;

import java.util.Date;

import com.jerome.entities.Person;
import com.jerome.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;



public class SaveDataClientTest {

	public static void main(String[] args) {

		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		try {
			session = sf.openSession();
			session.beginTransaction();

			Person person1 = new Person();
			person1.setName("Mark Bingel");
			person1.setNickName("Mac");
			person1.setAddress("Alameda Street Los Angeles");
			person1.setCreatedOn(new Date());
			person1.setVersion(1);

			Person person2 = new Person();
			person2.setName("Sean Murphy");
			person2.setNickName("Sam");
			person2.setAddress("Bank of Canada,234 Wellington Street");
			person2.setCreatedOn(new Date());
			person2.setVersion(1);

			session.save(person1);
			session.save(person2);

			session.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(session != null){
				session.close();
			}
		}

	}
}
