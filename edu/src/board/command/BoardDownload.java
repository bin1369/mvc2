package board.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.BoardDAO;
import board.model.BoardDTO;
import common.Command;
import common.CommonUtil;

public class BoardDownload implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		//DB에서 해당 파일정보를 조회해와 파일다운로드 처리
		int id = Integer.parseInt(request.getParameter("id"));
		BoardDTO dto = new BoardDAO().board_detail(id);
		
		new CommonUtil().fileDownload(request, response
				, dto.getFilename(), dto.getFilepath() );
		
	}

}
