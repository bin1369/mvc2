package customer.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.Command;
import customer.model.CustomerDAO;
import customer.model.CustomerDTO;

public class CustomerInsert implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// 화면에서 입력한 정보를 DB에 저장한다.
		//1.화면에서 입력한 정보를 수집:DTO에 담기
		CustomerDTO dto = new CustomerDTO();
		dto.setName( request.getParameter("name"));
		dto.setGender( request.getParameter("gender"));
		dto.setEmail( request.getParameter("email"));
		dto.setPhone( request.getParameter("phone"));
		
		//2.DB연결:DAO -> 저장처리 메소드호출
		new CustomerDAO().customer_insert(dto);
	}

}
