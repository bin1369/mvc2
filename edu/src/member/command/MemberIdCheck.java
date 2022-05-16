package member.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.Command;
import member.model.MemberDAO;

public class MemberIdCheck implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		//화면에서 입력한 아이디가 DB  에 있는지 존재여부를 파악
		//1.DB  연결 -> 아이디존재유무판단 메소드 호출
		boolean usable
			= new MemberDAO().member_id_usable(request.getParameter("id"));
		try {
			response.getWriter().print(usable);
		}catch(Exception e) {}
	}

}
