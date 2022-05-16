package board.comment.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.BoardDAO;
import board.model.CommentDTO;
import common.Command;

public class CommentInsert implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		//화면에서 입력 정보를 DB에 저장
		//1.입력한 정보수집:DTO 담기
		CommentDTO dto = new CommentDTO();
		dto.setBoard_id( Integer.parseInt(request.getParameter("board_id")) );
		dto.setContent( request.getParameter("content") );	
		dto.setWriter( request.getParameter("writer") );
		
		//2.DB연결 -> 댓글저장메소드 호출
		try {
			if( new BoardDAO().board_comment_insert(dto)==0 ) {
				response.getWriter().print( false );
			}else {
				response.getWriter().print( true );
			}
		}catch(Exception e) {}
	}

}
