package employee.command;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.Command;
import employee.model.EmployeeDAO;
import employee.model.EmployeeDTO;

public class EmployeeUpdate implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		//화면에서 변경입력한 정보를  DB 에 변경저장
		//1. 화면에서 입력한 정보를 수집: DTO 에 담기
		EmployeeDTO dto = new EmployeeDTO();
		dto.setEmployee_id( 
			Integer.parseInt(request.getParameter("employee_id")) );
		dto.setSalary( 
				Integer.parseInt(request.getParameter("salary")) );
		dto.setFirst_name( request.getParameter("first_name") );
		dto.setLast_name( request.getParameter("last_name") );
		dto.setEmail( request.getParameter("email") );
		dto.setPhone_number( request.getParameter("phone_number") );
		dto.setHire_date( Date.valueOf(request.getParameter("hire_date")) );
		dto.setJob_id( request.getParameter("job_id") );
		dto.setDepartment_id( 
				Integer.parseInt(request.getParameter("department_id")) );
		 
		//2. DB 연결 -> 변경저장처리 메소드 호출
		new EmployeeDAO().employee_update(dto);
	}

}
