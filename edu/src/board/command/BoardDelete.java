package board.command;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.BoardDAO;
import board.model.BoardDTO;
import board.model.BoardPage;
import common.Command;

public class BoardDelete implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		//선택한 방명록 정보를 DB에서 삭제
		//1.DB연결 -> 삭제처리메소드 호출
		int id = Integer.parseInt(request.getParameter("id"));
		
		//첨부된 파일이 있는 글이라면 해당 파일을 물리적영역에서 삭제
		BoardDTO dto = new BoardDAO().board_detail(id);
		if( dto.getFilepath()!=null ) {
			String filepath 
			= "d://app" + request.getContextPath() + "/" + dto.getFilepath();
			File file = new File(filepath);
			if( file.exists() ) file.delete();
		}
		
		new BoardDAO().board_delete(id);
		
		request.setAttribute("url", "list.bo");
		
		//파라미터 노출없이 목록화면으로 연결하는데 사용 
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
