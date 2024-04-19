package jsoft;

import java.sql.*;

import java.util.*;

public class ConnectionPoolImpl implements ConnectionPool {

	// Trình đi�?u khiển làm việc với CSDL
	private String driver;

	// �?ư�?ng dẫn thực thi MySQL;
	private String url;

	// Tài khoản sử dụng CSDL
	private String username;
	private String userpass;

	//�?ối tượng lưu trữ kết nối
	private Stack<Connection> pool;
	
	
	public ConnectionPoolImpl() {
		//Xác định trình đi�?u khiển của MySQL
		this.driver = "com.mysql.jdbc.Driver";
		
		//Xác định đư�?ng dẫn thực thi của MySQL
		//this.url = "jdbc:MySQL://localhost";
		this.url = "jdbc:MySQL://127.0.0.1:3306/jp210302_data?allowMultiQueries=true";
		
		//Xác định tài khoản làm việc
		this.username = "root";
		this.userpass = "0000";
		
		//Nạp trình đi�?u khiển
		this.loadDriver();
		
		//Khởi tạo bộ nhớ đối tượng lưu trữ
		this.pool = new Stack<>();
		
		
	}
	/**
	 * Phương thức nạp trình đi�?u khiển
	 */
	private void loadDriver() {
		try {
			Class.forName(this.driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	@Override
	public Connection getConnection(String objectName) throws SQLException {
		// TODO Auto-generated method stub
		if(this.pool.isEmpty()) {
			//Khởi tạo kết nối mới
			System.out.println(objectName + " have created a new Connection.");
			return DriverManager.getConnection(this.url, this.username, this.userpass);
		}else {
			//Lấy một kết nối mới đã lưu cho đối tượng
			System.out.println(objectName + " have popped the Connection.");
			return this.pool.pop();
		}
		
	}

	@Override
	public void releaseConnection(Connection con, String objectName) throws SQLException {
		// TODO Auto-generated method stub

		//Yêu cầu các đối tượng trả v�? kết nối
		System.out.println(objectName + " have pushed the Connection.");
		
		this.pool.push(con);
	}
	
	protected void finalize()throws Throwable{
		this.pool.clear(); // Loại b�? các đối tượng
		this.pool = null;
		
		System.gc();
		
		System.out.println("ConnectionPool is closed.");
	}

}
