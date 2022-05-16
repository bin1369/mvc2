package employee.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.Command;
import employee.model.DepartmentDTO;
import employee.model.EmployeeDAO;
import employee.model.JobDTO;

public class EmployeeCode implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		//DB에서 부서목록과 업무목록을 조회해와 화면에 출력
		//1.DB연결 -> 부서목록조회 메소드, 업무목록조회메소드 호출
		ArrayList<DepartmentDTO> departments
				= new EmployeeDAO().department_list_all();
		ArrayList<JobDTO> jobs = new EmployeeDAO().job_list_all();
		
		//2.조회한 부서목록과 업무목록을 request에 attribute 로 담는다
		request.setAttribute("departments", departments);
		request.setAttribute("jobs", jobs);
	}

}
