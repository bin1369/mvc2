package notice.command;

import java.io.File;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.Command;
import common.CommonUtil;
import notice.model.NoticeDAO;
import notice.model.NoticeDTO;

public class NoticeUpdate implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		//화면에서 변경입력한 정보를 DB에 변경저장
		//1.화면에서 입력한 정보를 수집: DTO담기
		NoticeDTO dto = new NoticeDTO();
		dto.setId( Integer.parseInt(request.getParameter("id")) );
		dto.setTitle( request.getParameter("title") );
		dto.setContent( request.getParameter("content")	);
		
		HashMap<String, String> map 
			= new CommonUtil().fileUpload(request, "notice");
		
		//원래 저장된 공지글의 파일정보를 확인
		NoticeDTO notice = new NoticeDAO().notice_detail( dto.getId() );
		String uuid = "d://app" + request.getContextPath() 
								+ "/" + notice.getFilepath();
		//첨부된 파일 없는 경우
		if( map.isEmpty() ) {
			//1.원래 첨부된 파일이 있었는데 첨부파일은 삭제한 경우
			//2.원래부터 첨부파일이 없었던 경우
			if( request.getParameter("filename").isEmpty() ) {
				//1.원래 첨부된 파일이 있었던 경우는 원래 첨부파일 물리적영역의 파일삭제
				if( notice.getFilename()!=null ) {
					File file = new File( uuid );
					if( file.exists() ) file.delete();
				}
			}else {
			//3.원래 첨부된 파일이 있었고  첨부파일을 그대로 두는 경우
				dto.setFilename( notice.getFilename() );
				dto.setFilepath( notice.getFilepath() );
			}
			
		}else {
		//첨부된 파일 있는 경우
			//4. 새로 첨부된 파일의 정보를 DB에 저장
			dto.setFilename( map.get("filename") );
			dto.setFilepath( map.get("filepath") );
			//5. 파일 변경첨부 : 원래 첨부파일 물리적영역의 파일삭제
			if( notice.getFilename()!=null ) {
				File file = new File( uuid );
				if( file.exists() ) file.delete();
			}
		}
		
		//2.DB연결 -> 변경저장메소드 호출
		new NoticeDAO().notice_update(dto);
	}

}
