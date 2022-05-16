package member.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MemberDAO {
	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;
	
	//암호화에 사용한 salt 조회
	public String member_salt(String id) {
		String salt = "";
		String sql = "select salt from member where id=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			if( rs.next() ) {
				salt = rs.getString("salt");
			}
		}catch(Exception e) {
		}finally {
			disconnect();
		}
		return salt;
	}
	
	//회원가입저장처리
	public Object[] member_join(MemberDTO dto) {
		Object msg[] = new Object[2];
		boolean success = true;
		String sql 
		= "insert into member "
			+ "(name, id, pw, gender, email, birth, phone, post, address, salt, salt_pw) "
		+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, dto.getName());
			ps.setString(2, dto.getId());
			ps.setString(3, dto.getPw());
			ps.setString(4, dto.getGender());
			ps.setString(5, dto.getEmail());
			ps.setString(6, dto.getBirth());
			ps.setString(7, dto.getPhone());
			ps.setString(8, dto.getPost());
			ps.setString(9, dto.getAddress());
			ps.setString(10, dto.getSalt());
			ps.setString(11, dto.getSalt_pw());
			ps.executeUpdate();
			msg[1] = "성공";
		}catch(Exception e) {
			msg[1] = e.getMessage(); 
			success = false;
		}finally {
			disconnect();
		}
		msg[0] = success;
//		return success;
		return msg;
	}
	
	//아이디 중복체크: 사용가능 여부를 반환
	public boolean member_id_usable(String id) {
		boolean usable = true;
		String sql = "select count(id) cnt from member where id=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			rs.next();
			usable = rs.getInt("cnt")==0 ? true :  false;
		}catch(Exception e) {
		}finally {
			disconnect();
		}
		return usable;
	}
	/*
	try {
		ps = conn.prepareStatement(sql);
	}catch(Exception e) {
	}finally {
		disconnect();
	}
	 * 	*/
	
	//암호화에 사용한 salt와  암호화된 비밀번호 적용
	public void memberApplySalt(String salt, String salt_pw, String id) {
		String sql
		= "update member set salt=?, salt_pw=? where id=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, salt);
			ps.setString(2, salt_pw);
			ps.setString(3, id);
			ps.executeUpdate();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}finally {
			disconnect();
		}
	}
	

	
	public ArrayList<String[]> memberNotSalt() {
		ArrayList<String[]> list = new ArrayList<String[]>();
		String sql
		= "select * from member where pw is not null";
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while( rs.next() ) {
				String info[] = new String[2];
				info[0] = rs.getString("id");
				info[1] = rs.getString("pw");
				list.add(info);
			}
		}catch(Exception e) {
		}finally {
			disconnect();
		}
		return list;
	}
	
	//아이디/비번 일치하는 회원정보조회
	public MemberDTO member_login(String id, String pw) {
		MemberDTO dto = null;
		String sql
		= "select * from member where id=? and salt_pw=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, pw);
			rs = ps.executeQuery();
			if( rs.next() ) {
				dto = new MemberDTO();
				dto.setId( rs.getString("id") );
				dto.setName( rs.getString("name") );
				dto.setEmail( rs.getString("email") );
				dto.setPw( rs.getString("pw") );
				dto.setGender( rs.getString("gender") );
				dto.setPhone( rs.getString("phone") );
				dto.setPost( rs.getString("post") );
				dto.setAddress( rs.getString("address") );
				dto.setAdmin( rs.getString("admin") );
			}
		}catch(Exception e) {
		}finally {
			disconnect();
		}
		return dto;
	}
	
	
	//소셜로그인(네이버/카카오) 정보를 저장
	public void member_social_insert(MemberDTO dto) {
		String sql
		= "insert into member(id, name, email, phone, birth, gender, social)"
		+ "values (?,?,?,?,?,?,?)";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, dto.getId());
			ps.setString(2, dto.getName());
			ps.setString(3, dto.getEmail());
			ps.setString(4, dto.getPhone());
			ps.setString(5, dto.getBirth());
			ps.setString(6, dto.getGender());
			ps.setString(7, dto.getSocial());
			ps.executeUpdate();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}finally {
			disconnect();
		}
		
	}
	public void member_social_update(MemberDTO dto) {
		String sql
		= "update member set name=?, email=?"
				+ ", phone=?, birth=?, gender=? where id=? ";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, dto.getName());
			ps.setString(2, dto.getEmail());
			ps.setString(3, dto.getPhone());
			ps.setString(4, dto.getBirth());
			ps.setString(5, dto.getGender());
			ps.setString(6, dto.getId());
			ps.executeUpdate();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}finally {
			disconnect();
		}
		
	}
	
	
	
	
	
	private void disconnect() {
		if( rs!=null ) { try{ rs.close(); }catch(Exception e) {}  }
		if( ps!=null ) { try{ ps.close(); }catch(Exception e) {}  }
		if( conn!=null ) { try{ conn.close(); }catch(Exception e) {}  }
	}
	
	public MemberDAO() {
		try {
			Context context = new InitialContext();
			DataSource ds = (DataSource)context.lookup("java:/comp/env/edu");
			conn = ds.getConnection();
		}catch(Exception e) {
		}
	}
}
