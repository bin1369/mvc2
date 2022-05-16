package board.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.BoardDAO;
import board.model.BoardDTO;
import board.model.BoardPage;
import common.Command;

public class BoardList implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		//DB에서 방명록 목록을 조회해와 화면에 출력
		//1.DB연결 -> 목록조회처리 메소드 호출
		BoardPage page = new BoardPage();
		String viewType = request.getParameter("viewType")==null 
							? "list" : request.getParameter("viewType");
		String search = request.getParameter("search")==null 
							? "" : request.getParameter("search");
		String keyword = request.getParameter("keyword")==null 
							? "" : request.getParameter("keyword");
		
		int pageList = request.getParameter("pageList")==null 
				? 10 : Integer.parseInt(request.getParameter("pageList"));
		
		int curPage = request.getParameter("curPage")==null 
					? 1 : Integer.parseInt(request.getParameter("curPage")); 
		page.setCurPage(curPage);
		page.setSearch(search);
		page.setKeyword(keyword);
		page.setPageList(pageList);
		page.setViewType(viewType);
		
		page.setTotalList( new BoardDAO().board_list_count(page) );
		ArrayList<BoardDTO> list = new BoardDAO().board_list(page);
		page.setList(list);
		
		//2.화면에 출력할 수 있도록  request 에  attribute 로 데이터를 담는다.
		request.setAttribute("page", page);
	}

}
