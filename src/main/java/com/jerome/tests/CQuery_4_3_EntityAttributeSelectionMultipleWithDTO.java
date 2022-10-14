package com.jerome.tests;

import java.util.List;

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

public class CQuery_4_3_EntityAttributeSelectionMultipleWithDTO {

	public static void main(String[] args) {
		List<EmployeeDTO> employeeInfo = getEmployessInfo();
		System.out.println("-------------------------------------------------");
		for(EmployeeDTO employeeDTO : employeeInfo){
			System.out.println(employeeDTO.getEmployeeName()+"\t"+employeeDTO.getEmail()+"\t"+employeeDTO.getSalary());
		}
		System.out.println("-------------------------------------------------");

	}

	public static List<EmployeeDTO> getEmployessInfo() {
		List<EmployeeDTO> resultList = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<EmployeeDTO> criteriaQuery = builder.createQuery(EmployeeDTO.class);
			Root<Employee> root = criteriaQuery.from(Employee.class);

			Path<Object> employeeNamePath = root.get("employeeName");
			Path<Object> emailPath = root.get("email");
			Path<Object> salaryPath = root.get("salary");

			// criteriaQuery.select(builder.array(employeeNamePath, emailPath, salaryPath));
			criteriaQuery.select(builder.construct(EmployeeDTO.class, employeeNamePath, emailPath, salaryPath));

			Query<EmployeeDTO> query = session.createQuery(criteriaQuery);
			// List<EmployeeDTO> list = query.list();
			resultList = query.getResultList();
		} catch (HibernateException e) {
			e.printStackTrace();
		}

		return resultList;
	}
}
