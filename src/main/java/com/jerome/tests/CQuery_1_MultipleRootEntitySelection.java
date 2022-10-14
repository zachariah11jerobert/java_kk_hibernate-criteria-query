package com.jerome.tests;

import java.util.List;

import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;

import com.jerome.entities.Partner;
import com.jerome.entities.Person;
import com.jerome.entities.Phone;
import com.jerome.util.HibernateUtil;


public class CQuery_1_MultipleRootEntitySelection {

	public static void main(String[] args) {

		try(Session session = HibernateUtil.getSessionFactory().openSession()) {
			CriteriaBuilder builder = session.getCriteriaBuilder();

			CriteriaQuery<Tuple> criteria = builder.createQuery( Tuple.class );

			Root<Person> personRoot = criteria.from(Person.class);
			Root<Partner> partnerRoot = criteria.from(Partner.class);
			criteria.multiselect( personRoot, partnerRoot );

			Predicate personRestriction = builder.and(
			    builder.equal(personRoot.get("address"), "Bank of Canada,234 Wellington Street"),
			    builder.isNotEmpty(personRoot.get("phones") )
			);
			Predicate partnerRestriction = builder.and(
			    builder.like( partnerRoot.get("name" ), "%Mur%" ),
			    builder.equal( partnerRoot.get("version"), 1 )
			);
			criteria.where(builder.and( personRestriction, partnerRestriction ) );

			List<Tuple> tuples = session.createQuery( criteria ).getResultList();
			for (Tuple tuple : tuples) {
				Person person = (Person)tuple.get(0); 
				if(person != null){
					System.out.println(person);
					List<Phone> phones = person.getPhones();
					for (Phone phone : phones) {
						System.out.println(phone.getId()+"\t"+phone.getNumber()+"\t"+phone.getType().toString());
					}
				}
				
				Partner partner = (Partner)tuple.get(1);
				System.out.println(partner);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}

}
