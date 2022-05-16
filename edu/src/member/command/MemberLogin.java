package member.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.Command;
import common.SHA256Util;
import member.model.MemberDAO;
import member.model.MemberDTO;

public class MemberLogin implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		//DB에서 화면에서 입력한 아이디.비번이 일치하는 회원정보를 조회하여 화면에 출력
		//1.DB연결 -> 회원정보조회메소드 호출
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		
		//DB저장된 salt 를 사용해 입력한 pw를 암호화한다
		String salt = new MemberDAO().member_salt(id);
		pw = new SHA256Util().getEncrypt(pw, salt);
		
		MemberDTO dto = new MemberDAO().member_login(id, pw);
		
		//2.로그인한 회원정보를 화면에 출력할 수 있도록 세션에 담는다
		request.getSession().setAttribute("loginInfo", dto);
		try {
			response.getWriter().print( dto == null ? false : true );
		}catch(Exception e) {}
	}

}
