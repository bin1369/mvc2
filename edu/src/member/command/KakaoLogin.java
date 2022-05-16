package member.command;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.Command;

public class KakaoLogin implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {

		//https://kauth.kakao.com/oauth/authorize?response_type=code
		//&client_id={REST_API_KEY}
		//&redirect_uri={REDIRECT_URI}
		//&state=state
		String REST_API_KEY = "db15c5bcd47ede8df7bad3cf20d134c2";
		String state = UUID.randomUUID().toString();
		request.getSession().setAttribute("state", state);
		
		int len = request.getRequestURL().length() 
						- request.getServletPath().length();
		String app = request.getRequestURL().substring(0, len);
		
		StringBuffer url = new StringBuffer(
			"https://kauth.kakao.com/oauth/authorize?response_type=code");
		url.append("&client_id=").append(REST_API_KEY);
		url.append("&redirect_uri=").append(app +"/kakaocallback.mb");
		url.append("&state=").append(state);
		
		request.setAttribute("url", url.toString());
	}

}
