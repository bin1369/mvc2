package customer.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.Command;
import customer.model.CustomerDAO;
import customer.model.CustomerDTO;

public class CustomerUpdate implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		//화면에서 변경입력한 정보를  DB 에 저장
		//1. 화면에서 입력한 정보를 수집:DTO
		CustomerDTO dto = new CustomerDTO();
		dto.setId( Integer.parseInt(request.getParameter("id")) );
		dto.setGender( request.getParameter("gender") );
		dto.setEmail( request.getParameter("email") );
		dto.setPhone( request.getParameter("phone") );
		//2. DB연결 -> 변경저장메소드를 호출
		new CustomerDAO().customer_update(dto);
	}

}
