package board.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;;

public class BoardDAO {
	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;

	// 신규저장처리
	public void board_insert(BoardDTO dto) {
		String sql = "insert into board(title, content, writer, filename, filepath) " + "values (?, ?, ?, ?, ?)";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, dto.getTitle());
			ps.setString(2, dto.getContent());
			ps.setString(3, dto.getWriter());
			ps.setString(4, dto.getFilename());
			ps.setString(5, dto.getFilepath());
			ps.executeUpdate();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			disconn();
		}
	}

	// 총 글의 건수 조회
	public int board_list_count(BoardPage page) {
		String where = search_keyword(page);
		int count = 0;
		String sql = "select count(*) cnt from board " + where;
		try {
			ps = conn.prepareStatement(sql);
			if (!where.isEmpty()) {
				ps.setString(1, "%" + page.getKeyword() + "%");
				if (page.getSearch().equals("all")) {
					ps.setString(2, "%" + page.getKeyword() + "%");
					ps.setString(3, "%" + page.getKeyword() + "%");
				}
			}
			rs = ps.executeQuery();
			rs.next();
			count = rs.getInt("cnt");
		} catch (Exception e) {
		} finally {
			disconn();
		}
		return count;
	}

	private String search_keyword(BoardPage page) {
		String sql = "";
		if (page.getKeyword().isEmpty())
			return sql;

		if (page.getSearch().equals("all")) {
			sql = "where title like ? or content like ? " + "or writer in (select id from member where name like ?) ";

		} else if (page.getSearch().equals("writer")) {
			sql = "where writer in (select id from member where name like ?) ";

		} else {
			sql = "where " + page.getSearch() + " like ? ";
		}

		return sql;
	}

	// 방명록목록조회
	public ArrayList<BoardDTO> board_list(BoardPage page) {
		String where = search_keyword(page);
		ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();
		String sql = "select b.*, name " + "from (select rownum no, b.* " + "      from (select * from board " + where
				+ " order by id) b " + "      order by no desc) b left outer join member m on m.id=b.writer "
				+ "where no between ? and ?";
		try {
			ps = conn.prepareStatement(sql);
			if (where.isEmpty()) {
				ps.setInt(1, page.getBeginList());
				ps.setInt(2, page.getEndList());
			} else {
				if (page.getSearch().equals("all")) {
					ps.setString(1, "%" + page.getKeyword() + "%");
					ps.setString(3, "%" + page.getKeyword() + "%");
					ps.setString(2, "%" + page.getKeyword() + "%");
					ps.setInt(4, page.getBeginList());
					ps.setInt(5, page.getEndList());
				} else {
					ps.setString(1, "%" + page.getKeyword() + "%");
					ps.setInt(2, page.getBeginList());
					ps.setInt(3, page.getEndList());
				}
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				BoardDTO dto = new BoardDTO();
				dto.setId(rs.getInt("id"));
				dto.setNo(rs.getInt("no"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setWriter(rs.getString("writer"));
				dto.setName(rs.getString("name"));
				dto.setWritedate(rs.getDate("writedate"));
				dto.setFilename(rs.getString("filename"));
				list.add(dto);
			}
		} catch (Exception e) {

		} finally {
			disconn();
		}
		return list;
	}

	// 변경저장
	public void board_update(BoardDTO dto) {
		String sql = "update board set title=?, content=?, filename=?, filepath=? " + "where id=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, dto.getTitle());
			ps.setString(2, dto.getContent());
			ps.setString(3, dto.getFilename());
			ps.setString(4, dto.getFilepath());
			ps.setInt(5, dto.getId());
			ps.executeUpdate();
		} catch (Exception e) {
		} finally {
			disconn();
		}
	}

	// 상세조회
	public BoardDTO board_detail(int id) {
		BoardDTO dto = null;
		String sql = "select b.*, name " + "from board b left outer join member m on m.id=b.writer "
				+ "where b.id = ? ";
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				dto = new BoardDTO();
				dto.setId(rs.getInt("id"));
				dto.setWriter(rs.getString("writer"));
				dto.setName(rs.getString("name"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setWritedate(rs.getDate("writedate"));
				dto.setReadcnt(rs.getInt("readcnt"));
				dto.setFilename(rs.getString("filename"));
				dto.setFilepath(rs.getString("filepath"));
			}
		} catch (Exception e) {
		} finally {
			disconn();
		}
		return dto;
	}

	// 조회수증가처리
	public void board_read(int id) {
		String sql = "update board set readcnt = readcnt + 1 where id = ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();

		} catch (Exception e) {
		} finally {
			disconn();
		}

	}

	// 방명록 글삭제 처리
	public void board_delete(int id) {
		String sql = "delete from board where id = ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();

		} catch (Exception e) {
		} finally {
			disconn();
		}
	}

	// 댓글저장처리
	public int board_comment_insert(CommentDTO dto) {
		int rows = 0;
		String sql = "insert into board_comment (board_id, writer, content) " + "values (?, ?, ?)";

		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, dto.getBoard_id());
			ps.setString(2, dto.getWriter());
			ps.setString(3, dto.getContent());
			rows = ps.executeUpdate();
		} catch (Exception e) {
		} finally {
			disconn();
		}
		return rows;
	}

	// 댓글목록 조회
	public ArrayList<CommentDTO> board_comment_list(int board_id) {

		ArrayList<CommentDTO> list = new ArrayList<CommentDTO>();
		String sql = "select * " + "from board_comment b left outer join member m on b.writer = m.id "
				+ "where board_id = ? " + "order by writedate desc";
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, board_id);
			rs = ps.executeQuery();
			while (rs.next()) {
				CommentDTO dto = new CommentDTO();
				dto.setId(rs.getInt("id"));
				dto.setBoard_id(rs.getInt("board_id"));
				dto.setWriter(rs.getString("writer"));
				dto.setContent(rs.getString("content"));
				dto.setWritedate(rs.getString("writedate"));
				dto.setName(rs.getString("name"));
				list.add(dto);
			}

		} catch (Exception e) {

		} finally {
			disconn();
		}
		return list;
	}

	// 댓글변경저장처리
	public int board_comment_update(CommentDTO dto) {
		String sql = "update board_comment set content = ? where id = ?";
		int rows = 0;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, dto.getContent());
			ps.setInt(2, dto.getId());
			rows = ps.executeUpdate();
		} catch (Exception e) {
		} finally {
			disconn();
		}
		return rows;
	}

	public void board_comment_delete(int id) {
		String sql = "delete from board_comment where id=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
		
		} catch (Exception e) {
		} finally {
			disconn();
		}
		
	}

	private void disconn() {
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

	public BoardDAO() {
		try {
			Context context = new InitialContext();
			DataSource ds = (DataSource) context.lookup("java:/comp/env/edu");
			conn = ds.getConnection();
		} catch (Exception e) {
		}
	}


}
