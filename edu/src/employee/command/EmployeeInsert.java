package employee.command;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.Command;
import employee.model.EmployeeDAO;
import employee.model.EmployeeDTO;

public class EmployeeInsert implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		//화면에서 입력한 정보를  DB 에 저장
		//1.화면에서 입력한 정보 수집: DTO담기
		EmployeeDTO dto = new EmployeeDTO();
		dto.setFirst_name( request.getParameter("first_name") );
		dto.setLast_name( request.getParameter("last_name") );
		dto.setEmail( request.getParameter("email") );
		dto.setPhone_number( request.getParameter("phone_number") );
		dto.setSalary( 
				Integer.parseInt(request.getParameter("salary")) );
		dto.setHire_date( Date.valueOf(request.getParameter("hire_date")) );
		dto.setJob_id( request.getParameter("job_id") );
		dto.setDepartment_id( 
				Integer.parseInt(request.getParameter("department_id")) );

		//2.DB연결 -> 저장처리메소드 호출
		new EmployeeDAO().employee_insert(dto);
	}

}
