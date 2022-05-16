package member.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.Command;
import member.model.MemberDTO;

public class MemberLogout implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		//"https://kauth.kakao.com/oauth/logout
		//?client_id={YOUR_REST_API_KEY}
		//&logout_redirect_uri={YOUR_LOGOUT_REDIRECT_URI}"
		HttpSession session = request.getSession();
		String social 
		= ((MemberDTO)session.getAttribute("loginInfo")).getSocial();
		//회원가입한 회원로그인: null
		//네이버로그인: Naver, 카카오로그인: Kakao
		String REST_API_KEY = "db15c5bcd47ede8df7bad3cf20d134c2";
		if( social != null && social.equals("Kakao") ) {
			int len = request.getRequestURL().length()
						- request.getServletPath().length();
			String app = request.getRequestURL().substring(0, len);
			
			StringBuffer url = new StringBuffer(
					"https://kauth.kakao.com/oauth/logout");
			url.append("?client_id=").append(REST_API_KEY);
			url.append("&logout_redirect_uri=").append(app);
			request.setAttribute("url", url.toString());
			
		}else {
			request.setAttribute("url", request.getContextPath());
		}
		
		//세션에 저장된 로그인정보를 삭제한 후 인덱스화면으로 연결
		session.removeAttribute("loginInfo");
	}

}
