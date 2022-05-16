package customer.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.Command;
import customer.model.CustomerDAO;
import customer.model.CustomerDTO;

public class CustomerList implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		//DB에서 고객목록을 조회해와 화면에 출력할 수 있도록 저장
		//1.DB 연결 -> 조회메소드 호출
		ArrayList<CustomerDTO> list = new CustomerDAO().customer_list();
		
		//2.request 에  attribute 로 조회한 데이터를 담는다
		request.setAttribute("list", list);
	}

}
