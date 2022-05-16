package notice.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.Command;
import notice.model.NoticeDAO;
import notice.model.NoticeDTO;
import notice.model.NoticePage;

public class NoticeList implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		//페이지 번호를 클릭하면 파라미터로 페이지번호를 가져온다
		//카테고리 공지사항을 클릭하면 파라미터는 없고 1페이지이다.
		int curPage = request.getParameter("curPage") == null 
					? 1 : Integer.parseInt( request.getParameter("curPage") ); 
		
		String search = request.getParameter("search")==null 
						? "" : request.getParameter("search");
		String keyword = request.getParameter("keyword")==null 
						? "" : request.getParameter("keyword");
		
		NoticePage page = new NoticePage();
		page.setCurPage(curPage);
		page.setSearch(search);
		page.setKeyword(keyword);
		
		//DB에서 공지글목록을 조회해와 화면에 출력
		//1.DB연결 -> 목록조회 메소드 호출
		//ArrayList<NoticeDTO> list = new NoticeDAO().notice_list();
		//request.setAttribute("list", list);
		
		//총목록수 조회해오기 -> 페이지관련정보 계산됨
		page.setTotalList( new NoticeDAO().notice_total_list(page) );
		page.setList( new NoticeDAO().notice_list(page) );
		
		//2.조회해온 목록을 화면에 출력할 수 있도록  request에 attribute 로 담는다
		request.setAttribute("page", page);
	}

}
