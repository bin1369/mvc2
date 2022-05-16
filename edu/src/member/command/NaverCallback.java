package member.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import common.Command;
import common.CommonUtil;
import member.model.MemberDAO;
import member.model.MemberDTO;

public class NaverCallback implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		String state = request.getParameter("state");
		String code = request.getParameter("code");
		String error = request.getParameter("error");
		
		//세션에있는  state와 파라미터로 받은 state 가 다르거나,
		//실패로  error 가 있으면 
		HttpSession session = request.getSession();
		String session_state = (String)session.getAttribute("state") ;
		if( error!=null || !state.equals(session_state)  ) return;
		
		//전달받은 'code' 값을 이용하여 '접근토큰발급API'를 호출하게 되면 
		//API 응답으로 접근토큰에 대한 정보를 받을 수 있습니다
		
		//https://nid.naver.com/oauth2.0/token
		//?grant_type=authorization_code
		//&client_id=jyvqXeaVOVmV
		//&client_secret=527300A0_COq1_XV33cf
		//&code=EIc5bFrl4RibFls1
		//&state=9kgsGTfH4j7IyAkg  
		String client_id = "iyDf6t9F2FONXW4yK_MB";
		String client_secret = "94RSDCGtPz";
		
		//접근토큰 발급을 위한  code 
		StringBuffer url = new StringBuffer(
			"https://nid.naver.com/oauth2.0/token?grant_type=authorization_code");
		url.append("&client_id=").append(client_id);
		url.append("&client_secret=").append(client_secret);
		url.append("&code=").append(code);
		url.append("&state=").append(state);
		
		CommonUtil util = new CommonUtil();
		
		//code 를 사용해 접근토큰 발급 요청
		JSONObject json = new JSONObject( util.requestAPI(url) );
		String access_token = json.getString("access_token");
		String token_type = json.getString("token_type");
		
		System.out.println(access_token);
		System.out.println(token_type);
		
		//접근토큰을 사용해 사용자프로필정보 요청
		//https://openapi.naver.com/v1/nid/me
		url = new StringBuffer( "https://openapi.naver.com/v1/nid/me" );
		
		json = new JSONObject( 
				util.requestAPI(url, token_type + " " + access_token) );
		
		//API 호출 결과 코드가 정상인 경우 프로픨 정보에 접근하자.
		if( json.getString("resultcode").equals("00") ) {
			MemberDTO dto = new MemberDTO();
			dto.setSocial("Naver");
			
			json = json.getJSONObject("response");
			dto.setId( json.getString("id") );
			dto.setEmail( json.getString("email"));
			//닉네임이 있으면 닉네임을 네임으로 하고, 없으면 네임을 사용
			String name 
			= json.has("nickname")
			? json.getString("nickname") : json.getString("name");
			dto.setName( name );
			
			dto.setGender( 
				json.getString("gender").equals("F") ? "여" : "남" );
			
			String birth 
				= (json.has("birthyear") 
				  ?	json.getString("birthyear") : "") + "-"
				+ (json.has("birthday") 
				  ? json.getString("birthday") : "");
			dto.setBirth(birth.length()==10 ? birth : "");

			dto.setPhone( json.has("mobile") ? json.getString("mobile") : "");
			
			
			//DB저장
			//이미 있는 아이디있지의 여부를 판단해 신규저장/변경저장
			//사용가능한 아이디인지의 메소드true: insert 
			if( new MemberDAO().member_id_usable( dto.getId() ) ) {
				new MemberDAO().member_social_insert(dto);
			}else {
				new MemberDAO().member_social_update(dto);
				
			}
			
			
			//세션에 네이버로그인 정보를 담는다.
			session.setAttribute("loginInfo", dto);
		}
	}

}
