package board.comment.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import board.model.BoardDAO;
import board.model.CommentDTO;
import common.Command;

public class CommentUpdate implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// 화면에서 입력한 댓글정보를 DB에 변경 저장
		// 1. 화면에서 입력한 정보 수집: DTO에 담기
		JSONObject json = new JSONObject(request.getParameter("comment"));
		CommentDTO dto = new CommentDTO();
		dto.setId(json.getInt("id"));
		dto.setContent(json.getString("content"));

		// 2. DB연결 변경저장메소드 호출
		try {
			if (new BoardDAO().board_comment_update(dto) == 0) {
				response.getWriter().print("실패ㅠㅠ");
			} else {
				response.getWriter().print("성공^^");
			}
		} catch (Exception e) {
		}

	}
}
