package notice.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.Command;
import notice.model.NoticeDAO;

public class NoticeRead implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		//선택한 글의 조회수를 1 증가시킨다.
		//1.DB연결 -> 조회수증가처리메소드 호출
		int id = Integer.parseInt( request.getParameter("id") );
		new NoticeDAO().notice_read(id);
	}

}
