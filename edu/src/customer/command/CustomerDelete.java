package customer.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.Command;
import customer.model.CustomerDAO;

public class CustomerDelete implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		//해당 고객의 정보를  DB에서 삭제
		//DB연결 -> 고객정보삭제 메소드 호출
		int id = Integer.parseInt(request.getParameter("id"));
		new CustomerDAO().customer_delete(id);
	}

}
