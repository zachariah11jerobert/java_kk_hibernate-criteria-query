package com.jerome.tests;

import java.util.List;

import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import com.jerome.dto.EmployeeDTO;
import com.jerome.entities.Employee;
import com.jerome.util.HibernateUtil;

public class CQuery_4_4_EntityAttributeSelectionMultipleWithTuple {

	public static void main(String[] args) {
		getEmployessInfo();
	}

	public static void getEmployessInfo() {

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Tuple> criteriaQuery = builder.createQuery(Tuple.class);
			Root<Employee> root = criteriaQuery.from(Employee.class);

			Path<Object> employeeNamePath = root.get("employeeName");
			Path<Object> emailPath = root.get("email");
			Path<Object> salaryPath = root.get("salary");

			criteriaQuery.multiselect(employeeNamePath, emailPath, salaryPath);

			List<Tuple> tuples = session.createQuery(criteriaQuery).getResultList();

			System.out.println("----------------------------------------------");
			for (Tuple tuple : tuples) {
				String employeeName = (String) tuple.get(employeeNamePath);
				String email = (String) tuple.get(emailPath);
				Double salary = (Double) tuple.get(salaryPath);
				System.out.println(employeeName + "\t" + email + "\t" + salary);
			}

		} catch (HibernateException e) {
			e.printStackTrace();
		}

	}
}
