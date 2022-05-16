package notice.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.Command;
import notice.model.NoticeDAO;
import notice.model.NoticeDTO;

public class NoticeDetail implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		//선택한 공지글을 DB에서 조회해와 화면서 출력
		//1.DB연결 -> 선택한 공지글조회메소드 호출
		int id = Integer.parseInt( request.getParameter("id") );
		NoticeDTO dto = new NoticeDAO().notice_detail(id);
		
		//2.조회된 데이터를 화면에 출력할 수 있도록 request에 attribute로 담는다
		request.setAttribute("dto", dto);
		request.setAttribute("crlf", "\r\n");
		request.setAttribute("lf", "\n");
	}

}
