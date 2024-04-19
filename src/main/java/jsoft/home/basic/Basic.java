package jsoft.home.basic;

import java.sql.*;
import jsoft.*;
import java.util.*;

public interface Basic extends ShareControl {

	// PreparedStatement pre - đã được biên dịch, đã truy�?n giá trị

	public boolean add(PreparedStatement pre);

	public boolean edit(PreparedStatement pre);

	public boolean del(PreparedStatement pre);

	public ResultSet get(String sql, int id);

	public ResultSet get(ArrayList<String> sql, String name, String pass);

	public ResultSet gets(String sql);

	// Lấy nhi�?u ResultSet
	public ArrayList<ResultSet> getReLists(String mutilSelect);
	

}
