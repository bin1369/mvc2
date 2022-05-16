package board.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.BoardDAO;
import common.Command;

public class BoardRead implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		//화면에서 선택한 글의 조회수를 증가시킨다
		int id = Integer.parseInt( request.getParameter("id") );
		new BoardDAO().board_read(id);
	}

}
