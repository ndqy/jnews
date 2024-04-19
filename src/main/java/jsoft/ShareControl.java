package jsoft;

public interface ShareControl {
	// Chia sẻ bộ quản lý kết nối giữa các Basic với nhau
	public ConnectionPool getCP();

	// Ra lệnh các đối tượng phải trả lại kết nối
	public void releaseConnection();
}
