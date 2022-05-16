package customer.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class CustomerDAO {
	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;
	
	
	//고객정보삭제
	public void customer_delete(int id) {
		String sql 
		= "delete from customer where id=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
		}catch(Exception e) {
		}finally {
			disconnect();
		}
	}
	
	//고객정보변경저장
	public void customer_update(CustomerDTO dto) {
		String sql
		= "update customer set gender=?, email=?, phone=? "
		+ "where id=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, dto.getGender());
			ps.setString(2, dto.getEmail());
			ps.setString(3, dto.getPhone());
			ps.setInt(4, dto.getId());
			ps.executeUpdate();		
				
		}catch(Exception e) {
		}finally {
			disconnect();
		}
	}
	
	//선택고객조회
	public CustomerDTO customer_detail(int id) {
		CustomerDTO dto = null;
		String sql
		= "select * from customer where id=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if( rs.next() ) {
				dto = new CustomerDTO();
				dto.setId( rs.getInt("id") );
				dto.setName( rs.getString("name") );
				dto.setGender( rs.getString("gender") );
				dto.setEmail( rs.getString("email") );
				dto.setPhone( rs.getString("phone") );
			}
			
		}catch(Exception e) {
		}finally {
			disconnect();
		}
		return dto;
	}
	
	
	//고객목록조회
	public ArrayList<CustomerDTO> customer_list() {
		ArrayList<CustomerDTO> list = new ArrayList<CustomerDTO>();
		String sql
		= "select * from customer order by name";
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while( rs.next() ) {
				CustomerDTO dto = new CustomerDTO();
				dto.setId( rs.getInt("id") );
				dto.setName( rs.getString("name") );
				dto.setGender( rs.getString("gender") );
				dto.setEmail( rs.getString("email") );
				dto.setPhone( rs.getString("phone") );
				list.add(dto);
			}
		}catch(Exception e) {
		}finally {
			disconnect();
		}
		return list;
	}
	
	//신규고객저장처리
	public void customer_insert(CustomerDTO dto) {
		String sql
		= "insert into customer(name, gender, email, phone) "
		+ "values (?, ?, ?, ?)";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, dto.getName());
			ps.setString(2, dto.getGender());
			ps.setString(3, dto.getEmail());
			ps.setString(4, dto.getPhone());
			ps.executeUpdate();
		
		}catch(Exception e) {
			System.out.println("insert:"+e.getMessage());
		}finally {
			disconnect();
		}
	}
	
	private void disconnect() {
		if( rs!=null ) {  try{ rs.close(); }catch(Exception e) {} }
		if( ps!=null ) {  try{ ps.close(); }catch(Exception e) {} }
		if( conn!=null ) {  try{ conn.close(); }catch(Exception e) {} }
	}
	
	public CustomerDAO() {
		try {
			Context context = new InitialContext();
			DataSource ds = (DataSource)context.lookup("java:/comp/env/edu");
			conn = ds.getConnection();
		}catch(Exception e) {
			System.out.println("dao:"+e.getMessage());
		}
	}
}
