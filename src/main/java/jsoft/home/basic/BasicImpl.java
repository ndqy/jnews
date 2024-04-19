package jsoft.home.basic;

import java.sql.*;
import java.util.ArrayList;

import jsoft.*;

public class BasicImpl implements Basic {
	// Bộ quản lý kết nối của riêng Basic
	private ConnectionPool cp;

	// Kết nối để Basic sử dụng
	protected Connection con;

	// �?ối tượng làm việc với Basic
	private String objectName;

	// Chạy một lần thì không cần tham số - Chạy nhi�?u lần thì cần tham số
	// Tham số được tính trên thuộc tính độc lập không tính thuộc tính phụ thuộc
	public BasicImpl(ConnectionPool cp, String objectName) {
		// Xác định đối tượng làm việc
		this.objectName = objectName;

		// Xác định - Gán bộ quản lý kết nối
		if (cp == null) {
			// Nếu chưa có thì cần khởi tạo
			this.cp = new ConnectionPoolImpl();
		} else {
			this.cp = cp;
		}

		// Xin kết nối để làm việc
		try {
			this.con = this.cp.getConnection(this.objectName);

			// Kiểm tra kết nối
			if (this.con.getAutoCommit()) {
				this.con.setAutoCommit(false);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private boolean exe(PreparedStatement pre) {
		if (pre != null) {
			// Thực hiện cập nhật vào CSDL
			try {
				int results = pre.executeUpdate();
				if (results == 0) {
					this.con.rollback();
					return false;
				}

				// Xác nhận thực thi sau cùng
				this.con.commit();
				return true;

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

				// Có lỗi lên trở lại trạng thái an toàn của kết nối
				try {
					this.con.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			} finally {
				pre = null;
			}
		}

		return false;
	}



	@Override
	public ResultSet get(String sql, int id) {
		// TODO Auto-generated method stub

		// Biên dịch câu lệnh sql
		try {
			PreparedStatement pre = this.con.prepareStatement(sql);
			if (id > 0) {
				pre.setInt(1, id);
			}

			return pre.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				this.con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			sql = null;
		}
		return null;
	}

	@Override
	public ResultSet get(ArrayList<String> sql, String name, String pass) {
		// TODO Auto-generated method stub

		try {
			String str_select = sql.get(0);
			PreparedStatement pre = this.con.prepareStatement(str_select);
			pre.setString(1, name);
			pre.setString(2, pass);

			ResultSet rs = pre.executeQuery();
			if (rs != null) {
				String str_update = sql.get(1);
				PreparedStatement preUpdate = this.con.prepareStatement(str_update);
				preUpdate.setString(1, name);
				preUpdate.setString(2, pass);
				int result = preUpdate.executeUpdate();
				if (result == 0) {
					this.con.rollback();
					return null;
				} else {
					if (!this.con.getAutoCommit()) {
						this.con.commit();
					}
				}
				return rs;
			}
			return pre.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				this.con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			name = null;
			pass = null;
		}
		return null;
	}

	@Override
	public ResultSet gets(String sql) {
		// TODO Auto-generated method stub
		return this.get(sql, 0);
	}

	@Override
	public ConnectionPool getCP() {
		// TODO Auto-generated method stub
		return this.cp;
	}

	@Override
	public void releaseConnection() {
		// TODO Auto-generated method stub
		try {
			this.cp.releaseConnection(this.con, this.objectName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public ArrayList<ResultSet> getReLists(String mutilSelect) {
		// TODO Auto-generated method stub

		ArrayList<ResultSet> res = new ArrayList<>();

		try {
			PreparedStatement pre = this.con.prepareStatement(mutilSelect);

			boolean result = pre.execute();
			do {
				res.add(pre.getResultSet());

				// �?ây là sang ResultSet thứ 2
				result = pre.getMoreResults(Statement.KEEP_CURRENT_RESULT);
			} while (result);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				this.con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		return res;
	}

	@Override
	public boolean add(PreparedStatement pre) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean edit(PreparedStatement pre) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean del(PreparedStatement pre) {
		// TODO Auto-generated method stub
		return false;
	}

}
