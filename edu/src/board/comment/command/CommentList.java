package board.comment.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.BoardDAO;
import board.model.CommentDTO;
import common.Command;

public class CommentList implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		//해당 원글(방명록글)에 대한 댓글 목록을 DB에서 조회하여 댓글목록화면에 출력
		
		//1. DB연결
		int board_id = Integer.parseInt(request.getParameter("board_id"));
		ArrayList<CommentDTO> list = new BoardDAO().board_comment_list(board_id);
	
		//2. request attribute에 담기
		request.setAttribute("list", list);
		request.setAttribute("crlf", "\r\n");
		request.setAttribute("lf", "\n");
		
	}

}
