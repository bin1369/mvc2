package board.comment.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.BoardDAO;
import common.Command;

public class CommentDelete implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		//화면에서 선택한 댓글을 DB에서 삭제
		//1. DB연결 삭제메소드 호출
		int id = Integer.parseInt(request.getParameter("id"));
		new BoardDAO().board_comment_delete(id);
	}

}
