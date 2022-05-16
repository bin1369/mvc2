package employee.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.Command;
import employee.model.EmployeeDAO;
import employee.model.EmployeeDTO;

public class EmployeeDetail implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		//화면에서 선택한 사원의 정보를  DB 에서 조회해와 상세화면에 출력
		//1. DB 연결 -> 사원정보조회메소드 호출
		int employee_id = Integer.parseInt(request.getParameter("id"));
		EmployeeDTO dto = new EmployeeDAO().employee_detail(employee_id);
		//2. 조회된 데이터를  request 에  attribute 로 담는다
		request.setAttribute("dto", dto);
	}

}
