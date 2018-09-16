package staff;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import java.util.List;
import java.util.ArrayList;

import staff.container.Employee;

public class CreateEmployee {

	static String puName = "Eclipselink_JPA";
	/**
	 * List of employees to put in datavbase
	 */
	static List<Employee> employees = new ArrayList<Employee>() {

		private static final long serialVersionUID = -7407585995473684088L;

		{
			add(new Employee(1201, "Gupal", 40000, "Technical Manager"));
			add(new Employee(1202, "Manisha", 40000, "proof reader"));
			add(new Employee(1203, "Masthanvali", 40000, "Technical writer"));
			add(new Employee(1204, "Satish", 30000, "Technical writer"));
			add(new Employee(1205, "Krishna", 30000, "Technical writer"));
			add(new Employee(1206, "Kiran", 35000, "proof reader"));
		}
	};

	public static void create(Employee employee) {

		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(employee);
		entityManager.getTransaction().commit();
		entityManager.close();
		entityManagerFactory.close();
	}

	public static void read(int id) {

		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		Employee employee = entityManager.find(Employee.class, id);

		System.out.println("employee ID = " + employee.getEid());
		System.out.println("employee NAME = " + employee.getEname());
		System.out.println("employee SALARY = " + employee.getSalary());
		System.out.println("employee DESIGNATION = " + employee.getDeg());
	}

	public static void update(Employee updatedEmployee) {

		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		Employee employee = entityManager.find(Employee.class, updatedEmployee.getEid());
		System.out.println(employee);
		employee.setEname(updatedEmployee.getEname());
		employee.setSalary(updatedEmployee.getSalary());
		employee.setDeg(updatedEmployee.getDeg());
		entityManager.getTransaction().commit();
		System.out.println(employee);
		entityManager.close();
		entityManagerFactory.close();
	}

	public static void delete(int id) {

		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		Employee employee = entityManager.find(Employee.class, id);
		entityManager.remove(employee);
		entityManager.getTransaction().commit();
		entityManager.close();
		entityManagerFactory.close();
	}

	public static void queryBetween() {

		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(puName);
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		Query query = entityManager.createQuery("select e from Employee e where e.salary Between 30000 and 40000");
		List<Employee> list = (List<Employee>) query.getResultList();

		for (Employee employee : list) {
			System.out.println("Employee name: " + employee.getEname());
			System.out.println("Salary: " + employee.getSalary());
		}

	}

	public static void queryScalarAndAggragate() {

		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(puName);
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		// Scalar function
		Query query = entityManager.createQuery("Select UPPER(e.ename) from Employee e");
		List<String> list = (List<String>) query.getResultList();

		for (String name : list) {
			System.out.println("Employee name: " + name);
		}
		
		Query query01 = entityManager.createQuery("Select MAX(e.salary) from Employee e");
		
		Double result = (Double) query01.getSingleResult();
		System.out.println("Highest salary is: "+ result);

	}
	
	
	public static void  queryOrder() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(puName);
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		Query query01 = entityManager.createQuery("Select e from Employee e ORDER BY e.ename ASC");
		List<Employee> employees = (List<Employee>) query01.getResultList();
		for ( Employee employee : employees) {
			System.out.println(employee);
		}
	}
	
	public static void useNamedQuery(){
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(puName);
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		Query query = entityManager.createNamedQuery("find employee by id");
		query.setParameter("id", 1204);
		List<Employee> employees = (List<Employee>) query.getResultList();
		for (Employee employee : employees) {
			System.err.println(employee);
		}
	}

	public static void main(String[] args) {

		for (Employee employee : employees) {

			// create(employee);
			/*
			 * read(employee.getEid()); employee.setSalary(50000); update(employee);
			 */

			// delete(employee.getEid());
		}
		//queryScalarAndAggragate();
		useNamedQuery();
	}

}
