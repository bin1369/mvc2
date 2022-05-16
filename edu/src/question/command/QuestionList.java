package question.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.Command;
import question.model.QuestionDAO;
import question.model.QuestionDTO;

public class QuestionList implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// DB에서 질문 조회하여 화면에 표시
		ArrayList<QuestionDTO> question_list = new QuestionDAO().question_list();
		
//		System.out.println(question_list.get(0).getWriterDate());
		
		// request attrivute로 담기
		request.setAttribute("question_list", question_list);
	}

}
