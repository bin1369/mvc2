package employee.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.Command;
import employee.model.EmployeeDAO;

public class EmployeeDelete implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		//선택한 사원의 정보를 DB에서 삭제
		//1.DB연결 -> 삭제처리메소드 호출
		int employee_id = Integer.parseInt(request.getParameter("id"));
		new EmployeeDAO().employee_delete(employee_id);
	}

}
