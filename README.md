# mybatis-spring-zk-multimodule
MyBatis Spring ZK Project - Using multiple Maven modules based off typical project separation


(This project differs from the single module approach in that this project is more representative
of how you would typically set up your artificats with a domain library of pojos, your jar for persistence,
and then your web project.)
 
**To build:**<br/>
Requires maven<br/>
From this project's root directory (parent direcotry) type: mvn clean install

**To run:**</br>
(Build first above) then
Navigate to the parent's subproject directory: mybatis-spring-zk-web directory and type: mvn jetty:run
Then use URL: http://localhost:8080

**To deploy:**<br/>
If using Tomcat7: make sure Tomcat is running and then from mybatis-spring-zk-web directory type: mvn tomcat7:deploy
or you can manually move the generated mybatis-spring-zk-web.war file from mybatis-spring-zk-web/target/ 
to your app server's deploy dir (eg tomcat/webapps) and start your server (if not started already.)
Then use URL: http://localhost:8080/mybatis-spring-zk-web
 
NOTES:
------
Application uses an internal in-memory HSQLDB defined in spring/services-config.xml. You'll see a commented out section
in the config demonstrating using your typical datasouce declaration using an external server DB instance.

You'll notice that I'm using both services classes and MyBatis mappers. Because this application is so simple, you really could just use
the Mappers directly in your ViewModels. All the service class methods do in this application is end up calling the appropriate mapper methods.
However in "real life" I've found that you often perform some other business logic in your service class methods before calling your mappers.
For example your service class for doing an insert might also involve updating some other tables after the insert, or possible sending out a notification message.
Having a service class, can keep unwanted business logic from creeping into your ViewModel.
Of course, feel free to skip using the service class approach if you so desire. There is nothing inherently wrong with
using your mappers directly in your ViewModel.

There are sample filters included. By default the local is used when you do a build. For this sample application the filters don't do much since we're using a standalone internal db, but in real life you'd have different datasource properties for different environments. The filter will replace the appropriate wildcards in anything under web-inf (you could change this in your pom.) The filter currently replaces the wildcards in web-application.properties and the spring config files (in real life the datasource you see commented out in services-config.xml would be replaced based on your filter.) To build using a different profile, for example the qa version, you'd just switch to building with a different profile from maven: mvn clean install -P qa
 
MyBatis
-------
Many people might be coming at this from experience with ORMs such as Hibernate. MyBatis isn't an ORM tool but a sql mapping tool. I prefer using MyBatis, but not going to get into all the reasons why in this readme. The basics with Spring here are really simple.

In the services-config.xml you'll see the setup. This tells MyBatis what datasource to use, where the domain objects are located, and where the mapper files are locaated:

 
    <jdbc:embedded-database id="empDS" type="HSQL">
		<jdbc:script location="classpath:/db.script" />
	</jdbc:embedded-database>
	
	<bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
		<property name="dataSource" ref="empDS"/>
	</bean>
	
	<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
		<property name="dataSource" ref="empDS"/>
		<property name="typeAliasesPackage" value="net.learntechnology.empmaint.domain"/>
	</bean>
	
	<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
		<property name="basePackage" value="net.learntechnology.empmaint.mapper"/>
		<property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"/>
	</bean>

Now you just make a Mapper interface and the mapper xml that holds the sql:

     public interface EmployeeMapper {
		List getAllEmployees();
		void updateEmployee(Employee emp);
		void deleteEmployee(Integer id);
		public Employee getEmployee(Integer id);
		public void insertEmployee(Employee emp);
     }

EmployeMapper.xml

    <?xml version="1.0" encoding="UTF-8" ?> <!DOCTYPE mapper
			PUBLIC "-//ibatis.apache.org//DTD Mapper 3.0//EN"
			"http://ibatis.apache.org/dtd/ibatis-3-mapper.dtd">
	<mapper namespace="net.learntechnology.empmaint.mapper.EmployeeMapper">
	
		 <resultMap id="employeeResult" type="Employee">
			 <result property="id" column="id"/>
			 <result property="firstName" column="firstname"/>
			 <result property="lastName" column="lastname"/>
			 <result property="age" column="age"/>
			 <result property="department.id" column="departmentid"/>
			 <result property="department.name" column="department_name"/>
		 </resultMap>
	
		<select id="getAllEmployees" resultMap="employeeResult">
			SELECT
				emp.id,
				emp.firstname,
				emp.lastname,
				emp.age,
				emp.departmentid,
				dep.name as department_name
			FROM employee emp
			JOIN department dep ON dep.id = emp.departmentid
			ORDER BY firstname, lastname
		</select>
                
         .....
 
Note you don't always have to use a ResultMap when your columns match up to your properties:

Example illustrating mapping directly to an Object (not using a ResultMap):

	<select id="getAllDepartments" resultType="net.learntechnology.empmaint.domain.Department">
		SELECT id, name FROM DEPARTMENT
	</select>
	
You will also notice that, I illustrate both using a pure annotation approach to the Mappers (no xml for the SQL) and the xml approach. The DemographicMapper 
illustrates how you could create your simple sql directly as annotation. The XML approach however gives you some more powerful options that you can't always
do via the annotation based SQL, so I typically go wtih just using XML mappers for everything in my 'real life' code.

To use our mapper we just declare it as a resource. SIMPLE!:
 
	@Service
	public class EmployeeServiceImpl implements EmployeeService {
	
		@Resource
		private EmployeeMapper employeeMapper;
	
		public List getAllEmployees() {
			return employeeMapper.getAllEmployees();
		}

         .....


Example service being used in a ZK ViewModel:

 
	public class EmployeeVM {
		 
		List<Employee> employees;
	
		@WireResource
		private EmployeeService employeeService;
	
		@Init
		public void init() {
			employees = employeeService.getAllEmployees();
		}


