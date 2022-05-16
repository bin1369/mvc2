package customer.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.Command;
import customer.model.CustomerDAO;
import customer.model.CustomerDTO;

public class CustomerDetail implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		//DB에서 선택한 고객정보를 조회하여 화면에 출력할 수 있도록 저장
		//1.DB 연결 -> 선택고객정보 조회메소드 호출
		int id = Integer.parseInt( request.getParameter("id") );
		CustomerDTO dto = new CustomerDAO().customer_detail(id);
		
		//2.조회한 정보를 request 에 attribute 로 담는다
		request.setAttribute("dto", dto);
	}

}
