package notice.command;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.Command;
import notice.model.NoticeDAO;
import notice.model.NoticeDTO;

public class NoticeDelete implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		//선택한 공지글을 DB에서 삭제처리
		//해당글에 첨부된 파일이 있다면 물리적영역에서 해당 파일도 삭제 
		//1.DB연결 -> 공지글삭제메소드 호출
		int id = Integer.parseInt(request.getParameter("id"));
		NoticeDTO dto = new NoticeDAO().notice_detail(id);
		if( dto.getFilepath()!= null ) {
			String filepath 
			= "d://app" + request.getContextPath() + "/" + dto.getFilepath();
			File file = new File(filepath);
			if( file.exists() ) file.delete();
		}
		
		new NoticeDAO().notice_delete(id);
	}

}
