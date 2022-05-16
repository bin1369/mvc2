package board.command;

import java.io.File;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.BoardDAO;
import board.model.BoardDTO;
import board.model.BoardPage;
import common.Command;
import common.CommonUtil;

public class BoardUpdate implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		//화면에서 변경입력한 정보를  DB 에 저장
		//1.화면입력정보 수집:DTO에 담기
		BoardDTO dto = new BoardDTO();
		dto.setTitle(request.getParameter("title"));
		dto.setContent( request.getParameter("content"));
		dto.setId( Integer.parseInt(request.getParameter("id")) );
		
		//화면에서 변경하기 전 원래의 파일정보
		BoardDTO board = new BoardDAO().board_detail( dto.getId() );
		String uuid = "d://app" + request.getContextPath() 
								+ "/" + board.getFilepath();
		
		//첨부파일 업로드
		HashMap<String, String> map= new CommonUtil().fileUpload(request, "board");
		if( map.isEmpty() ) {
		//첨부파일이 없는 경우
			//파일명이 있는 경우: 원래 첨부되어있던 파일을 그대로 둔 경우
			if( ! request.getParameter("filename").isEmpty() ) {
				dto.setFilename( board.getFilename() );
				dto.setFilepath( board.getFilepath() );
				
			}else {
			//파일명이 없는 경우
				//원래 첨부된 파일이 있었다면 그 파일은 물리적영역에서 삭제
				if( board.getFilename()!=null ) {
					 File file = new File(uuid);
					 if( file.exists() ) file.delete();
				}
			}
			
		}else {
		//첨부파일이 있는 경우
			//첨부한 첨부파일정보를 DB에 저장되도록 dto에 담는다
			dto.setFilename( map.get("filename") );
			dto.setFilepath( map.get("filepath") );
			
			//변경첨부한 경우 원래있던 첨부파일을 물리적영역에서 삭제
			if( board.getFilename()!=null ) {
				File file = new File( uuid );
				if( file.exists() ) file.delete();
			}
		}
		
		//2.DB연결 -> 변경저장메소드 호출
		new BoardDAO().board_update(dto);
		
		request.setAttribute("url", "detail.bo");
		request.setAttribute("id", dto.getId());
		
		int curPage = Integer.parseInt( request.getParameter("curPage"));
		int pageList = Integer.parseInt( request.getParameter("pageList"));
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
