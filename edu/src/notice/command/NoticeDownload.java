package notice.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.Command;
import common.CommonUtil;
import notice.model.NoticeDAO;
import notice.model.NoticeDTO;

public class NoticeDownload implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		//해당 공지글의 첨부파일을 서버로부터 클라이언트로 다운로드 처리
		//1.DB 에서 첨부파일정보를 조회해온다
		int id = Integer.parseInt(request.getParameter("id"));
		NoticeDTO dto = new NoticeDAO().notice_detail(id);
		new CommonUtil().fileDownload(request, response
				, dto.getFilename(), dto.getFilepath());
		
	}

}
