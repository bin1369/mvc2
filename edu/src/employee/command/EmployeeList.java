package employee.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.Command;
import employee.model.DepartmentDTO;
import employee.model.EmployeeDAO;
import employee.model.EmployeeDTO;

public class EmployeeList implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		//DB에서 사원목록을 조회해와 화면에 출력할 수 있도록 attribute 에 저장
		//1.DB연결 -> 사원목록조회 메소드 호출
		//요청한 파라미터가 없는 경우와 파라미터 값이 비어있는 경우
		ArrayList<EmployeeDTO> list = null;
		if( request.getParameter("department_id")==null 
				|| request.getParameter("department_id").isEmpty() ) {
			list = new EmployeeDAO().employee_list();
		}else {
		//요청한 파라미터가 있는 경우
			int id = Integer.parseInt(request.getParameter("department_id"));
			list = new EmployeeDAO().employee_list(id);
			request.setAttribute("department_id", id);
		}
		
		ArrayList<DepartmentDTO> departments = new EmployeeDAO().department_list();
		//2.조회해온 정보를 request  에 attribute 로 담는다
		request.setAttribute("list", list);
		request.setAttribute("departments", departments);
	}

}
