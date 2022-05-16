package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.SHA256Util;
import member.command.KakaoCallback;
import member.command.KakaoLogin;
import member.command.MemberIdCheck;
import member.command.MemberJoin;
import member.command.MemberLogin;
import member.command.MemberLogout;
import member.command.NaverCallback;
import member.command.NaverLogin;
import member.model.MemberDAO;

@WebServlet("*.mb")
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private void encryptPwBySalt() {
		
		SHA256Util util = new SHA256Util();
		ArrayList<String[]> list = new MemberDAO().memberNotSalt();
		for(String[] info : list ) {
			String salt = util.generateSalt();
			String salt_pw = util.getEncrypt(info[1], salt);
			new MemberDAO().memberApplySalt(salt, salt_pw, info[0]);
		}
		
	}
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().setAttribute("category", "mb");
		
		String uri = request.getRequestURI();
		String context = request.getContextPath();
		uri = uri.substring(context.length());
	
		String view = null;
		boolean redirect = false;
		
		if( uri.equals("/member.mb") ) {
			//회원가입화면 요청
			view = "/member/join.jsp";
			
		}else if( uri.equals("/id_check.mb") ) {
			//아이디 중복확인처리 요청
			new MemberIdCheck().execute(request, response);
			return;
			
		}else if( uri.equals("/join.mb") ) {
			//회원가입 처리 요청
			new MemberJoin().execute(request, response);
			return;
			
		}else if( uri.equals("/edulogin.mb") ) {
			//로그인처리 요청
			new MemberLogin().execute(request, response);
			return;
			
		}else if( uri.equals("/login.mb") ) {
//			encryptPwBySalt();
			
			//로그인화면 요청
			view = "/member/login.jsp";
			
		}else if( uri.equals("/logout.mb") ) {
			//로그아웃처리 요청
			new MemberLogout().execute(request, response);
			view = (String)request.getAttribute("url");
			redirect = true;
			
		}else if( uri.equals("/kakaologin.mb") ) {
			new KakaoLogin().execute(request, response);
			view = (String)request.getAttribute("url");
			redirect = true;
			
		}else if( uri.equals("/kakaocallback.mb") ) {
			new KakaoCallback().execute(request, response);
			view = request.getContextPath();
			redirect = true;
			
		}else if( uri.equals("/naverlogin.mb") ) {
			new NaverLogin().execute(request, response);
			view = (String)request.getAttribute("url");
			redirect = true;
			
		}else if( uri.equals("/navercallback.mb") ) {
			new NaverCallback().execute(request, response);
			view = request.getContextPath(); //인덱스화면으로 연결
			redirect = true;
		}
		
		if( redirect ) response.sendRedirect(view);
		else request.getRequestDispatcher(view).forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
