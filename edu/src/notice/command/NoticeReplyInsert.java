package notice.command;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.Command;
import common.CommonUtil;
import notice.model.NoticeDAO;
import notice.model.NoticeDTO;

public class NoticeReplyInsert implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		//화면에서 입력한 정보를  DB에 저장
		//1. 화면에서 입력한 정보수집: DTO에 담기
		NoticeDTO dto = new NoticeDTO();
		//입력한 답글정보
		dto.setTitle( request.getParameter("title") );
		dto.setContent( request.getParameter("content") );
		dto.setWriter( request.getParameter("writer") );
		
		//원글정보
		dto.setRoot( Integer.parseInt(request.getParameter("root")) );
		dto.setStep( Integer.parseInt(request.getParameter("step")) );
		dto.setIndent( Integer.parseInt(request.getParameter("indent")) );
		
		//첨부파일 업로드처리
		HashMap<String, String> map 
			= new CommonUtil().fileUpload(request, "notice");
		dto.setFilename( map.get("filename") );
		dto.setFilepath( map.get("filepath") );
		
		//2. DB연결 -> 답글저장처리메소드 호출
		new NoticeDAO().reply_insert(dto);
		
	}

}
