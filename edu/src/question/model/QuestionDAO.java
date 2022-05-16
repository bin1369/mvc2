package question.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class QuestionDAO {
	// 필드
	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;

	// 생성자

	// 메소드
	private void connect() { // 연결 메소드
		try {
			Context context = new InitialContext();
			DataSource ds = (DataSource) context.lookup("java:/comp/env/edu");
			conn = ds.getConnection();
		} catch (Exception e) {
			System.out.println("연결오류: " + e.getMessage());
		}
	}

	private void disconnect() { // 종료 메소드
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
			}
		}
		if (ps != null) {
			try {
				ps.close();
			} catch (Exception e) {
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
	}

	public ArrayList<QuestionDTO> question_list() { // 질문 전체조회 메소드
		ArrayList<QuestionDTO> list = new ArrayList<QuestionDTO>();
		String sql = "select rownum idx, q.* from question q order by writedate desc";
		try {
			connect();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				QuestionDTO question = new QuestionDTO();
				question.setIdx(rs.getInt("idx"));
				question.setId(rs.getInt("id"));
				question.setTitle(rs.getString("title"));
				question.setContent(rs.getString("content"));
				question.setWriter(rs.getString("writer"));
				question.setWriteDate(rs.getString("writedate"));
				question.setReadcnt(rs.getInt("readcnt"));
				question.setComplete(rs.getString("complete").equals("Y") ? true : false);
				list.add(question);
			}

		} catch (Exception e) {
			System.out.println("조회 메소드 에러: " + e.getMessage());
		} finally {
			disconnect();
		}
		return list;

	}

	public int question_insert(QuestionDTO question) { // 질문 삽입 메소드
		int rows = 0;
		String sql = "insert into question (id, title, content, writer) " + "values (seq_question.nextval, ?, ?, ?)";

		try {
			connect();
			ps = conn.prepareStatement(sql);
			ps.setString(1, question.getTitle());
			ps.setString(2, question.getContent());
			ps.setString(3, question.getWriter());
			rows = ps.executeUpdate();

		} catch (Exception e) {
			System.out.println("삽입 메소드 에러: " + e.getMessage());
		} finally {
			disconnect();
		}
		return rows;

	}

	public QuestionDTO question_detail(String id) { // 질문 상세 조회 메소드
		QuestionDTO question = new QuestionDTO();
		String sql = "select * from question where writer = ?";
		try {
			connect();
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			
			if (rs.next()) {
				question.setId(rs.getInt("id"));
				question.setTitle(rs.getString("title"));
				question.setContent(rs.getString("content"));
				question.setWriter(rs.getString("writer"));
				question.setWriteDate(rs.getString("writedate"));
				question.setReadcnt(rs.getInt("readcnt"));
				question.setComplete(rs.getString("complete").equals("Y") ? true : false);
			}

		} catch (Exception e) {
			System.out.println("상세 조회 실패: " + e.getMessage());
		} finally {
			disconnect();
		}
		return question;
	}
}
