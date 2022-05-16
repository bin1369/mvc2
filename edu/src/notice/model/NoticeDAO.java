package notice.model;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class NoticeDAO {
	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;

//	try{
//	ps = conn.prepareStatement(sql);
//	
//}catch(Exception e) {
//}finally {
//	disconnect();
//}
	//공지글삭제처리
	public void notice_delete(int id) {
		String sql
		= "delete from notice where id=?";
		try{
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
		}catch(Exception e) {
		}finally {
			disconnect();
		}
	}

	//선택한 글의 조회수증가처리
	public void notice_read(int id) {
		String sql 
		= "update notice set readcnt=readcnt+1 where id=?";
		try{
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
		}catch(Exception e) {
		}finally {
			disconnect();
		}
	}
	
	
	//선택한 공지글 조회
	public NoticeDTO notice_detail(int id) {
		NoticeDTO dto = null;
		String sql
		= "select n.*, name "
		+ "from notice n left outer join member m on n.writer=m.id "
		+ "where n.id=?";
		try{
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if( rs.next() ) {
				dto = new NoticeDTO();
				dto.setId( rs.getInt("id") );
				dto.setRoot( rs.getInt("root") );
				dto.setStep( rs.getInt("step") );
				dto.setIndent( rs.getInt("indent") );
				dto.setReadcnt( rs.getInt("readcnt") );
				dto.setTitle( rs.getString("title") );
				dto.setWriter( rs.getString("writer") );				
				dto.setName( rs.getString("name") );				
				dto.setContent( rs.getString("content") );				
				dto.setWritedate( rs.getDate("writedate") );
				dto.setFilename( rs.getString("filename") );
				dto.setFilepath( rs.getString("filepath") );
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}finally {
			disconnect();
		}		
		return dto;
	}
	
	//페이지처리된 공지글목록 조회
	public ArrayList<NoticeDTO> notice_list( NoticePage page ) {
		ArrayList<NoticeDTO> list = new ArrayList<NoticeDTO>();
		String where = search_keyword(page.getSearch(), page.getKeyword());
		String sql
		= "select * "
		+ "from (select rownum no, n.*, name "
		+ "      from (select * from notice " + where + " order by root, step desc) n left outer join member m on n.writer = m.id "
		+ "      order by no desc) n "
		+ "where no between ? and ?";
		try{
			ps = conn.prepareStatement(sql);
			if( where.isEmpty() ) {
				ps.setInt(1, page.getBeginList());
				ps.setInt(2, page.getEndList());
			}else {
				if( page.getSearch().equals("all") ) {
					ps.setString(1, "%"+page.getKeyword()+"%");
					ps.setString(2, "%"+page.getKeyword()+"%");
					ps.setString(3, "%"+page.getKeyword()+"%");
					ps.setInt(4, page.getBeginList());
					ps.setInt(5, page.getEndList());					
				}else {
					ps.setString(1, "%"+page.getKeyword()+"%");
					ps.setInt(2, page.getBeginList());
					ps.setInt(3, page.getEndList());					
				}
			}
			
			rs = ps.executeQuery();
			while( rs.next() ) {
				NoticeDTO dto = new NoticeDTO();
				dto.setIndent( rs.getInt("indent") );
				dto.setNo( rs.getInt("no") );
				dto.setId( rs.getInt("id") );
				dto.setTitle( rs.getString("title") );
				dto.setWriter( rs.getString("writer") );
				dto.setName( rs.getString("name") );
				dto.setFilename( rs.getString("filename") );
				dto.setFilepath( rs.getString("filepath") );
				dto.setWritedate( rs.getDate("writedate") );
				list.add( dto );
			}
		}catch(Exception e) {
		}finally {
			disconnect();
		}
		return list;
	}
	
	
	
	//공지글 목록조회
	public ArrayList<NoticeDTO> notice_list() {
		ArrayList<NoticeDTO> list = new ArrayList<NoticeDTO>();
		String sql
		= "select rownum no, n.*, name "
		+ "from (select * from notice order by root, step desc) n left outer join member m on n.writer = m.id "
		+ "order by no desc";
		try{
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while( rs.next() ) {
				NoticeDTO dto = new NoticeDTO();
				dto.setIndent( rs.getInt("indent") );
				dto.setNo( rs.getInt("no") );
				dto.setId( rs.getInt("id") );
				dto.setTitle( rs.getString("title") );
				dto.setWriter( rs.getString("writer") );
				dto.setName( rs.getString("name") );
				dto.setFilename( rs.getString("filename") );
				dto.setFilepath( rs.getString("filepath") );
				dto.setWritedate( rs.getDate("writedate") );
				list.add( dto );
			}
		}catch(Exception e) {
		}finally {
			disconnect();
		}
		return list;
	}
	
	
	//공지글 신규저장처리
	public void notice_insert(NoticeDTO dto) {
		String sql
		= "insert into notice (id, root, title, writer, content, filename, filepath) "
		+ "values (seq_notice.nextval, seq_notice.currval, ?, ?, ?, ?, ?)";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, dto.getTitle());
			ps.setString(2, dto.getWriter());
			ps.setString(3, dto.getContent());
			ps.setString(4, dto.getFilename());
			ps.setString(5, dto.getFilepath());
			ps.executeUpdate();
		}catch(Exception e) {
		}finally {
			disconnect();
		}
	}
	
	//변경저장
	public void notice_update(NoticeDTO dto) {
		String sql 
		= "update notice set title=?, content=?, filename=?, filepath=? "
		+ "where id=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, dto.getTitle());
			ps.setString(2, dto.getContent());
			ps.setString(3, dto.getFilename());
			ps.setString(4, dto.getFilepath());
			ps.setInt(5, dto.getId());
			ps.executeUpdate();
			
		}catch(Exception e) {
		}finally {
			disconnect();
		}
	}
	
	//답글저장처리
	public void reply_insert(NoticeDTO dto) {
//		root가 같고 원글의 step보다 더 큰 step 에 대해서는 step+1
//		root:	원글의  root 를 그대로
//		step:	원글의 step+1
//		indent:	원글의 indent+1

		String sql
		= "{ call declare "
		+ "begin "
		+ "update notice set step=step+1 where root = ? and step > ?; "
		+ "insert into notice "
		+ "(id, title, writer, content, filename, filepath, root, step, indent) "
		+ "values (seq_notice.nextval, ?, ?, ?, ?, ?, ?, ?, ?); "
		+ "end }";
		CallableStatement cs = null;
		try{
			cs = conn.prepareCall(sql);
			//update
			cs.setInt(1, dto.getRoot() );
			cs.setInt(2, dto.getStep() );
			//insert
			cs.setString(3, dto.getTitle());
			cs.setString(4, dto.getWriter());
			cs.setString(5, dto.getContent());
			cs.setString(6, dto.getFilename());
			cs.setString(7, dto.getFilepath());
			cs.setInt(8, dto.getRoot() );
			cs.setInt(9, dto.getStep()+1 );
			cs.setInt(10, dto.getIndent()+1 );
			cs.execute();
		}catch(Exception e) {
		}finally {
			if( cs!=null ) { try{ cs.close(); }catch(Exception e) {}  }
			disconnect();
		}
	}
	
	private String search_keyword(String search, String keyword) {
		String sql = "";
		if( keyword.isEmpty() ) return sql;
		
		if( search.equals("all") ) {
			sql 
			= "where title like ? or content like ? "
			+ "or writer in ( select id from member where name  like ? ) ";
		}else if ( search.equals("writer") ) {
			sql = "where writer in ( select id from member where name  like ? ) ";
		}else {		//title, content
			sql = "where " + search + " like ? ";
		}
		return sql;
	}
	
	//총목록수 조회
	public int notice_total_list(NoticePage page) {
		int count=0;
		String where = search_keyword(page.getSearch(), page.getKeyword());
		String sql
		= "select count(*) cnt from notice " + where ;
		try {
			ps = conn.prepareStatement(sql);
			if( ! where.isEmpty() ) {
				ps.setString(1, "%"+page.getKeyword()+"%" );
				if( page.getSearch().equals("all") ) {
					ps.setString(2, "%"+page.getKeyword()+"%" );
					ps.setString(3, "%"+page.getKeyword()+"%" );
				}
			}
			
			rs = ps.executeQuery();
			rs.next();
			count = rs.getInt("cnt");
		}catch(Exception e) {
		}finally {
			disconnect();
		}
		return count;
	}
	
	
	
	private void disconnect() {
		if( rs!=null ) { try{ rs.close();}catch(Exception e) {} }
		if( ps!=null ) { try{ ps.close();}catch(Exception e) {} }
		if( conn!=null ) { try{ conn.close();}catch(Exception e) {} }
	}
	
	
	public NoticeDAO() {
		try {
			Context app = new InitialContext();
			DataSource ds = (DataSource)app.lookup("java:/comp/env/edu");
			conn = ds.getConnection();
		}catch(Exception e) {}
	}
}
