package employee.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class EmployeeDAO {
	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;
	
	//사원이 소속된 부서목록조회
	public ArrayList<DepartmentDTO> department_list(){
		ArrayList<DepartmentDTO> list = new ArrayList<DepartmentDTO>();
		String sql
		= "select nvl(department_id,0) department_id"
				+ ", nvl(department_name, '기타') department_name "
		+ "from (select distinct department_id from employees) e "
				+ "left outer join departments d using(department_id)";
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while( rs.next() ) {
				DepartmentDTO dto = new DepartmentDTO();
				dto.setDepartment_id( rs.getInt("department_id") );
				dto.setDepartment_name( rs.getString("department_name") );
				list.add(dto);
			}
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}finally {
			disconn();
		}		
		return list;
	}

	
	//사원목록조회
	public ArrayList<EmployeeDTO> employee_list() {
		ArrayList<EmployeeDTO> list = new ArrayList<EmployeeDTO>();
		String sql
		= "select e.*, department_name, job_title "
		+ "from employees e inner join jobs j on e.job_id=j.job_id "
		+ "left outer join departments d on e.department_id=d.department_id";
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while( rs.next() ) {
				EmployeeDTO dto = new EmployeeDTO();
				dto.setEmployee_id( rs.getInt("employee_id") );
				dto.setLast_name( rs.getString("last_name") );
				dto.setFirst_name( rs.getString("first_name") );
				dto.setDepartment_name( rs.getString("department_name") );
				dto.setJob_title( rs.getString("job_title") );
				dto.setHire_date( rs.getDate("hire_date") );
				list.add(dto);
			}
		}catch(Exception e) {
		}finally {
			disconn();
		}
		return list;
	}
	
	public ArrayList<EmployeeDTO> employee_list(int department_id) {
		ArrayList<EmployeeDTO> list = new ArrayList<EmployeeDTO>();
		String sql
		= "select e.*, department_name, job_title "
		+ "from employees e inner join jobs j on e.job_id=j.job_id "
		+ "left outer join departments d on e.department_id=d.department_id "
		+ "where nvl(e.department_id,0) = ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, department_id);
			rs = ps.executeQuery();
			while( rs.next() ) {
				EmployeeDTO dto = new EmployeeDTO();
				dto.setEmployee_id( rs.getInt("employee_id") );
				dto.setLast_name( rs.getString("last_name") );
				dto.setFirst_name( rs.getString("first_name") );
				dto.setDepartment_name( rs.getString("department_name") );
				dto.setJob_title( rs.getString("job_title") );
				dto.setHire_date( rs.getDate("hire_date") );
				list.add(dto);
			}
		}catch(Exception e) {
		}finally {
			disconn();
		}
		return list;
	}
	
	/*
	String sql
	= "";
	try {
		ps = conn.prepareStatement(sql);
	
	}catch(Exception e) {
	}finally {
		disconn();
	}	
	*/
	
	//사원정보조회
	public EmployeeDTO employee_detail(int employee_id) {
		EmployeeDTO dto = null;
		String sql
		= "select e.*, department_name, job_title "
		+ "from employees e inner join jobs j on e.job_id=j.job_id "
		+ "left outer join departments d on e.department_id=d.department_id "
		+ "where employee_id=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, employee_id);
			rs = ps.executeQuery();
			if( rs.next() ) {
				dto = new EmployeeDTO();
				dto.setEmployee_id( rs.getInt("employee_id") );
				dto.setSalary( rs.getInt("salary") );
				dto.setLast_name( rs.getString("last_name") );
				dto.setFirst_name( rs.getString("first_name") );
				dto.setDepartment_id( rs.getInt("department_id") );
				dto.setDepartment_name( rs.getString("department_name") );
				dto.setJob_id( rs.getString("job_id") );
				dto.setJob_title( rs.getString("job_title") );
				dto.setHire_date( rs.getDate("hire_date") );
				dto.setEmail( rs.getString("email") );
				dto.setPhone_number( rs.getString("phone_number") );
			}
		}catch(Exception e) {
		}finally {
			disconn();
		}
		return dto;
	}
	
	
	//부서목록조회
	public ArrayList<DepartmentDTO> department_list_all() {
		ArrayList<DepartmentDTO> list = new ArrayList<DepartmentDTO>();
		String sql = "select * from departments";
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while( rs.next() ){
				DepartmentDTO dto = new DepartmentDTO();
				dto.setDepartment_id( rs.getInt("department_id") );
				dto.setDepartment_name( rs.getString("department_name") );
				list.add(dto);
			}
			
		}catch(Exception e) {
		}finally {
			disconn();
		}	
		return list;
	}
	
	
	//업무목록조회
	public ArrayList<JobDTO> job_list_all() {
		ArrayList<JobDTO> list = new ArrayList<JobDTO>();
		String sql = "select  * from jobs";
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while( rs.next() ) {
				JobDTO dto = new JobDTO();
				dto.setJob_id( rs.getString("job_id") );
				dto.setJob_title( rs.getString("job_title") );
				list.add(dto);
			}
		}catch(Exception e) {
		}finally {
			disconn();
		}	
		return list;
	}
	
	//변경저장처리 메소드
	public void employee_update(EmployeeDTO dto) {
		String sql
		= "update employees set last_name=?, first_name=?"
				+ ", email=?, phone_number=?, salary=?, hire_date=?"
				+ ", department_id=?, job_id=? "
		+ "where employee_id=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, dto.getLast_name());
			ps.setString(2, dto.getFirst_name());
			ps.setString(3, dto.getEmail());
			ps.setString(4, dto.getPhone_number());
			ps.setInt(5, dto.getSalary());
			ps.setDate(6, dto.getHire_date());
			//부서코드가 0 이면 null 로 적용한다.
			if( dto.getDepartment_id()==0 ) {
				ps.setNull(7, Types.INTEGER);
			}else {
				ps.setInt(7, dto.getDepartment_id());
			}
			ps.setString(8, dto.getJob_id());
			ps.setInt(9, dto.getEmployee_id());
			ps.executeUpdate();
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}finally {
			disconn();
		}	
	}
	
	//삭제처리
	public void employee_delete(int employee_id) {
		String sql = "delete from employees where employee_id=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, employee_id);
			ps.executeUpdate();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}finally {
			disconn();
		}
	}
	
	
	//저장처리
	public void employee_insert(EmployeeDTO dto) {
		String sql
		= "insert into employees"
			+ "(employee_id, last_name, first_name, email, phone_number"
			+ ", salary, hire_date, department_id, job_id) "
		+ "values (employees_seq.nextval, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, dto.getLast_name());
			ps.setString(2, dto.getFirst_name());
			ps.setString(3, dto.getEmail());
			ps.setString(4, dto.getPhone_number());
			ps.setInt(5, dto.getSalary());
			ps.setDate(6, dto.getHire_date());
			//부서코드가 0 이면 null 로 적용한다.
			if( dto.getDepartment_id()==0 ) {
				ps.setNull(7, Types.INTEGER);
			}else {
				ps.setInt(7, dto.getDepartment_id());
			}
			ps.setString(8, dto.getJob_id());
			ps.executeUpdate();
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}finally {
			disconn();
		}
		
		
		
	}
	
	
	
	public EmployeeDAO() {
		try {
			Context context = new InitialContext();
			DataSource ds =	(DataSource)context.lookup("java:/comp/env/hr");
			conn = ds.getConnection();
		}catch(Exception e) {
		}
	}
	
	private void disconn() {
		if( rs!=null) { 
			try{ rs.close(); }catch(Exception e) {}
		}
		if( ps!=null) { 
			try{ ps.close(); }catch(Exception e) {}
		}
		if( conn!=null) { 
			try{ conn.close(); }catch(Exception e) {}
		}
	}
}
