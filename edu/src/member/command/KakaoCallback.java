package member.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import common.Command;
import common.CommonUtil;
import member.model.MemberDAO;
import member.model.MemberDTO;

public class KakaoCallback implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		String code = request.getParameter("code");
		String state = request.getParameter("state");
		String error = request.getParameter("error");
		HttpSession session = request.getSession();
		String session_state = (String)session.getAttribute("state");
		
		if( error!=null || !state.equals( session_state ) ) return;
			
		//인가코드code를 정상 받아왔다면 접근토큰 발급요청 가능
		
//		curl -v -X POST "https://kauth.kakao.com/oauth/token" \
//		 -H "Content-Type: application/x-www-form-urlencoded" \
//		 -d "grant_type=authorization_code" \
//		 -d "client_id={REST_API_KEY}" \
//		 --data-urlencode "redirect_uri={REDIRECT_URI}" \
//		 -d "code={AUTHORIZE_CODE}"
		
		String REST_API_KEY = "db15c5bcd47ede8df7bad3cf20d134c2";
		StringBuffer url = new StringBuffer(
			"https://kauth.kakao.com/oauth/token?grant_type=authorization_code");
		url.append("&client_id=").append(REST_API_KEY);
		url.append("&code=").append(code);
		url.append("&state=").append(state);
		
		CommonUtil util = new CommonUtil();
		
		//사용자정보 가져오는데 사용할 접큰토큰 발급받기		
		JSONObject json = new JSONObject( util.requestAPI( url ) );
		String token_type = json.getString("token_type");
		String access_token = json.getString("access_token");
		
//		"token_type":"bearer",
//	    "access_token":"{ACCESS_TOKEN}",
		
//		curl -v -X GET "https://kapi.kakao.com/v2/user/me" \
//		  -H "Authorization: Bearer {ACCESS_TOKEN}"
		
		//사용자정보 가져오기
		url = new StringBuffer("https://kapi.kakao.com/v2/user/me");
		json = new JSONObject( 
					util.requestAPI(url, token_type + " " + access_token) );
		if( ! json.isEmpty() ) {
			MemberDTO dto = new MemberDTO();
			dto.setSocial("Kakao");
			
			dto.setId( json.get("id").toString() );
			
			json = json.getJSONObject("kakao_account");
			dto.setName( json.has("name") ? json.getString("name") : "" );
			dto.setEmail( json.getString("email") );
			dto.setGender(
				json.getString("gender").equals("female") ? "여" : "남");
			
			if( json.getJSONObject("profile").has("nickname") )
				dto.setName( json.getJSONObject("profile").getString("nickname") );
			
			//카카오로그인 사용자가 DB에 있는지여부에 따라 insert/update
			if( new MemberDAO().member_id_usable(dto.getId()) ) {
				//insert
				new MemberDAO().member_social_insert(dto);
			}else {
				//update
				new MemberDAO().member_social_update(dto);
			}
			session.setAttribute("loginInfo", dto);
		}
	}

}
