package board.command;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.BoardDAO;
import board.model.BoardDTO;
import common.Command;
import common.CommonUtil;

public class BoardInsert implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		//화면에서 입력한 정보를 DB에 저장
		//1.화면에서 입력한 정보를 수집:DTO에 담기
		BoardDTO dto = new BoardDTO();
		dto.setTitle( request.getParameter("title") );
		dto.setContent( request.getParameter("content") );
		dto.setWriter( request.getParameter("writer") );
		
		HashMap<String, String> map = new CommonUtil().fileUpload(request, "board");
		dto.setFilename( map.get("filename") );
		dto.setFilepath( map.get("filepath") );
		
		//2.DB연결 -> 저장처리메소드호출
		new BoardDAO().board_insert(dto);
	}

}
