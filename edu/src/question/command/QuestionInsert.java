package question.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.Command;
import question.model.QuestionDAO;
import question.model.QuestionDTO;

public class QuestionInsert implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		//1. 화면 정보 수집
		QuestionDTO question = new QuestionDTO();
		question.setTitle(request.getParameter("title"));
		question.setContent(request.getParameter("content"));
		question.setWriter(request.getParameter("writer"));
		//2. DAO 호출
		int rows = new QuestionDAO().question_insert(question);
		
		System.out.println(rows +"행 삽입 완료!");
	}

}
