package member.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.Command;
import common.EmailSend;
import common.SHA256Util;
import member.model.MemberDAO;
import member.model.MemberDTO;

public class MemberJoin implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		//화면에서 입력한 정보를 DB 에 저장
		//1.화면에서 입력한 정보수집: DTO에 담기
		MemberDTO dto = new MemberDTO();
		dto.setName( request.getParameter("name") );
		dto.setId( request.getParameter("id") );
		dto.setPw( request.getParameter("pw") );
		dto.setGender( request.getParameter("gender") );
		dto.setEmail( request.getParameter("email") );
		dto.setBirth( request.getParameter("birth") );
		dto.setPhone( String.join("-", request.getParameterValues("phone")) );
		dto.setPost( request.getParameter("post") );
		dto.setAddress( String.join("<br>", request.getParameterValues("address")) );
		
		//입력한 비밀번호를 암호화하는데 사용할 salt 생성
		SHA256Util util = new SHA256Util();
		String salt = util.generateSalt();
		dto.setSalt(salt);
		dto.setSalt_pw( util.getEncrypt(dto.getPw(), salt) );
		
		//2.DB 연결 -> 저장처리메소드 호출
		Object[] result = new MemberDAO().member_join(dto);
//		boolean success = new MemberDAO().member_join(dto);
		response.setContentType("text/html; charset=utf-8");
		String msg = "";
		if( (Boolean)result[0] ) {
			new EmailSend().send(dto.getEmail(), dto.getName(), request);
			msg = "<script>alert('회원가입 축하^^'); location='"
						+ request.getContextPath() + "'; </script>";
		}else {
			msg = "<script>alert('회원가입 실패ㅠㅠ : "
					+  (String)result[1]							
					+ "'); history.go(-1); </script>";
		}
		try {
			response.getWriter().print(msg);
		}catch(Exception e) {}
	}

}
