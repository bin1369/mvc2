package board.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.BoardDAO;
import board.model.BoardDTO;
import board.model.BoardPage;
import common.Command;

public class BoardDetail implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		//화면에서 선택한 방명록의 정보를 DB에서 조회해와 상세화면에 출력
		//1.DB연결 -> 상세조회메소드 호출
		int id = Integer.parseInt(request.getParameter("id"));
		BoardDTO dto = new BoardDAO().board_detail(id); 
		
		//2.request 에 attribute 로 담는다
		request.setAttribute("dto", dto);
		request.setAttribute("crlf", "\r\n");
		
		//목록/변경화면/삭제처리 연결에 사용할 데이터들
		int curPage = Integer.parseInt(request.getParameter("curPage"));
		int pageList = Integer.parseInt(request.getParameter("pageList"));
		
		String search = request.getParameter("search");
		String keyword = request.getParameter("keyword");
		String viewType = request.getParameter("viewType");
		
		BoardPage page = new BoardPage();
		page.setCurPage(curPage);
		page.setPageList(pageList);
		page.setSearch(search);
		page.setKeyword(keyword);
		page.setViewType(viewType);
		
		request.setAttribute("page", page);
	}

}
