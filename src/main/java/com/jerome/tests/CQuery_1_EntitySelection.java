package com.jerome.tests;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import com.jerome.entities.Employee;
import com.jerome.util.HibernateUtil;


public class CQuery_1_EntitySelection {

	public static void main(String[] args) {

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Employee> criteriaQuery = builder.createQuery(Employee.class);
			Root<Employee> root = criteriaQuery.from(Employee.class);
			criteriaQuery.select(root);
			
			Query<Employee> query = session.createQuery(criteriaQuery);
			List<Employee> empList = query.list();
			empList.forEach(System.out::println);
		} catch (HibernateException e) {
			e.printStackTrace();
		}
	
	}

}
