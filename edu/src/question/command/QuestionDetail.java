package question.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.Command;
import question.model.QuestionDAO;
import question.model.QuestionDTO;

public class QuestionDetail implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
//		System.out.println("detail command");
		String id = request.getParameter("id");
//		System.out.println(id);
		
		// 1.DB연결 -> 선택한 공지글조회메소드 호출
		QuestionDAO dao = new QuestionDAO();
		QuestionDTO question = dao.question_detail(id);
		
		// 2.조회된 데이터를 화면에 출력할 수 있도록 request에 attribute로 담는다
		request.setAttribute("question", question);
	}

}
