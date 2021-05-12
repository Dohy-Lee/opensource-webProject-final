package User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import util.DatabaseUtil;

public class UserDAO {
	
	// ���� �̸����ؽ� ��������
	public String getHash(String userID) {
		Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
		String Hash=null;
		
		try {
        	String sql = "SELECT EmailHash FROM USER WHERE ID=?";
            
            conn = DatabaseUtil.getConnection();
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1, userID);
            rs = pstmt.executeQuery();
            
            if(rs.next()) {
            	Hash=rs.getString("EmailHash");
            	return Hash;
            }
            else
            	return Hash;
    		
        } catch(Exception e){
			e.printStackTrace();
		} finally {
			try{if(conn!=null) conn.close();}catch (Exception e) {
				e.printStackTrace();
			}
			try{if(pstmt!=null) pstmt.close();}catch (Exception e) {
				e.printStackTrace();
			}
			try{if(rs!=null) rs.close();}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return Hash;
	}
	
	// ���̵� ã��
	public String idFind(String name, String email) {
		Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String dbID=null;
        
        try {
        	String sql = "SELECT ID FROM USER WHERE Email=? AND Name=?";
            
            conn = DatabaseUtil.getConnection();
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1, email);
            pstmt.setString(2, name);
            rs = pstmt.executeQuery();
            
            if(rs.next()) {
            	dbID=rs.getString("ID");
            	return dbID;
            }
            else
            	return dbID;
    		
        } catch(Exception e){
			e.printStackTrace();
		} finally {
			try{if(conn!=null) conn.close();}catch (Exception e) {
				e.printStackTrace();
			}
			try{if(pstmt!=null) pstmt.close();}catch (Exception e) {
				e.printStackTrace();
			}
			try{if(rs!=null) rs.close();}catch (Exception e) {
				e.printStackTrace();
			}
		}
        return dbID;
	}
	
	
	// ��й�ȣ ã��
		public String pwFind(String name, String email, String id) {
			Connection conn = null;
	        PreparedStatement pstmt = null;
	        ResultSet rs = null;
	        String dbPW=null;
	        
	        try {
	        	String sql = "SELECT PW FROM USER WHERE Email=? AND Name=? AND ID=?";
	            
	            conn = DatabaseUtil.getConnection();
	            pstmt=conn.prepareStatement(sql);
	            pstmt.setString(1, email);
	            pstmt.setString(2, name);
	            pstmt.setString(3, id);
	            rs = pstmt.executeQuery();
	            
	            if(rs.next()) {
	            	dbPW=rs.getString("PW");
	            	return dbPW;
	            }
	            else
	            	return dbPW;
	    		
	        } catch(Exception e){
				e.printStackTrace();
			} finally {
				try{if(conn!=null) conn.close();}catch (Exception e) {
					e.printStackTrace();
				}
				try{if(pstmt!=null) pstmt.close();}catch (Exception e) {
					e.printStackTrace();
				}
				try{if(rs!=null) rs.close();}catch (Exception e) {
					e.printStackTrace();
				}
			}
	        return dbPW;
		}
		
		
	
	// �α���
	public int login(String ID, String pw) {
		
		Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
 
        String dbPW = ""; // db���� ���� ��й�ȣ�� ���� ����
        int dbEC=0;
        int x = -1;
 
        try {
            String sql = "SELECT PW FROM USER WHERE ID=?";
            
            conn = DatabaseUtil.getConnection();
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1, ID);
            rs = pstmt.executeQuery();
 
            if (rs.next()) // �Էµ� ���̵� �ش��ϴ� ��� �������
            {
                dbPW = rs.getString("PW"); // ����� ������ �ִ´�.
                
 
                if (dbPW.equals(pw)) // DB���� �Ѱܹ��� ��й�ȣ�� �Է��� ��й�ȣ ��. ������ �̸�������(EmailChecked) Ȯ������ �Ѿ
                {               	
                	sql = "SELECT EmailChecked FROM USER WHERE ID=?";
                    
                	pstmt.close();
                    pstmt=conn.prepareStatement(sql);
                    pstmt.setString(1, ID);
                    rs.close();
                    rs = pstmt.executeQuery();
                    rs.next();
                    dbEC=rs.getInt("EmailChecked");
                    
                	if(dbEC==1) // EmailChecked 1�̸� �̸��� ���� ������ ����
                		x = 1; 
                	else
                		x=2;
                }
                else                  
                    x = 0; // DB�� ��й�ȣ�� �Է¹��� ��й�ȣ �ٸ�
                
            }
            else {
                x = -1; // �ش� ���̵� ���� ���
            }
 
            return x;
 
        } catch(Exception e){
			e.printStackTrace();
		}finally {
			try{if(conn!=null) conn.close();}catch (Exception e) {
				e.printStackTrace();
			}
			try{if(pstmt!=null) pstmt.close();}catch (Exception e) {
				e.printStackTrace();
			}
			try{if(rs!=null) rs.close();}catch (Exception e) {
				e.printStackTrace();
			}
		}
        return -1;
	}
	
	
	// ȸ������
	public int signUp(UserDTO user) {
		
		Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        
		try {
			String dbID=user.getID();
			String sql = "SELECT ID FROM USER WHERE ID=?";
			conn = DatabaseUtil.getConnection();
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1, user.getID());
            rs=pstmt.executeQuery();
            
            if(rs.next())	// ���̵� �ߺ� �� -1 ��ȯ
            	return -1;
            else {
            	String dbEmail=user.getEmail();
            	sql = "SELECT Email FROM USER WHERE Email=?";
            	pstmt.close();
            	pstmt=conn.prepareStatement(sql);
	            pstmt.setString(1, user.getEmail());
	            rs.close();
	            rs=pstmt.executeQuery();
            	if(rs.next())		// �̸��� �ߺ� �� -2 ��ȯ
            	{
            		return -2;
            	}
            	else {				// ���̵�, �̸��� �ߺ�x
            		sql = "INSERT INTO USER VALUES(?,?,?,?,false,?,10000)";
        			pstmt=conn.prepareStatement(sql);
        			pstmt.setString(1, user.getID());
        			pstmt.setString(2, user.getPW());
        			pstmt.setString(3, user.getEmail());
        			pstmt.setString(4, user.getEmailHash());
        			pstmt.setString(5, user.getName());
        			pstmt.setInt(6, user.getPoint());
        			return pstmt.executeUpdate();
            	}
            }
			
		}
		catch(Exception e){
			e.printStackTrace();
		}finally {
			try{if(conn!=null) conn.close();}catch (Exception e) {
				e.printStackTrace();
			}
			try{if(pstmt!=null) pstmt.close();}catch (Exception e) {
				e.printStackTrace();
			}
			try{if(rs!=null) rs.close();}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return -3;		 // ������ ���� �� -3 ��ȯ
	}
	
	// �̸���
	public String getEmail(String ID) {
		String SQL = "SELECT Email FROM USER WHERE ID = ? ";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, ID);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				return rs.getString(1);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try{if(conn!=null) conn.close();}catch (Exception e) {
				e.printStackTrace();
			}
			try{if(pstmt!=null) pstmt.close();}catch (Exception e) {
				e.printStackTrace();
			}
			try{if(rs!=null) rs.close();}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null; //�����ͺ��̽� ����
	}
	
	
	// �̸��� üũ
	public boolean getEmailChecked(String userID) {
		String SQL = "SELECT EmailChecked FROM USER WHERE ID = ? ";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				return rs.getBoolean(1);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try{if(conn!=null) conn.close();}catch (Exception e) {
				e.printStackTrace();
			}
			try{if(pstmt!=null) pstmt.close();}catch (Exception e) {
				e.printStackTrace();
			}
			try{if(rs!=null) rs.close();}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false; //�����ͺ��̽� ����
	}
	
	
	public boolean setEmailChecked(String userID) {
		String SQL = "UPDATE USER SET EmailChecked = true WHERE ID= ? ";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			pstmt.executeUpdate();
			return true; //Insert, Update,Delete �� executeUpdate �̿�, ������ ������ ���� ������ ������ ��ȯ
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try{if(conn!=null) conn.close();}catch (Exception e) {
				e.printStackTrace();
			}
			try{if(pstmt!=null) pstmt.close();}catch (Exception e) {
				e.printStackTrace();
			}
			try{if(rs!=null) rs.close();}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false; //�����ͺ��̽� ����
	}
}
