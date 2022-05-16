package notice.command;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.Command;
import common.CommonUtil;
import notice.model.NoticeDAO;
import notice.model.NoticeDTO;

public class NoticeInsert implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		//화면에서 입력한 정보를  DB 에 저장
		//1.화면에서 입력한 정보를 수집: DTO에 담기
		NoticeDTO dto = new NoticeDTO();
		dto.setTitle( request.getParameter("title"));
		dto.setContent( request.getParameter("content"));
		dto.setWriter( request.getParameter("writer"));
		
		HashMap<String, String> map 
			= new CommonUtil().fileUpload(request, "notice");
		dto.setFilename( map.get("filename") );
		dto.setFilepath( map.get("filepath") );
		
		//2.DB연결 -> 저장메소드 호출
		new NoticeDAO().notice_insert(dto);
	}

}











