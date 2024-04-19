<%@page import="jsoft.home.library.Utilities"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import ="jsoft.*, jsoft.objects.*"%>
<%@ page import ="java.util.*, org.javatuples.*"%>
<%@ page import ="jsoft.home.article.*"%>
<%
	//Xác định tập ký tự cần lấy
	request.setCharacterEncoding("utf-8");

	//Lấy đường dẫn xác định vị trí
	String uri = request.getRequestURI().substring(6);
	int at = uri.indexOf("/");
	
	//Tìm bộ quản lý kết nối
	ConnectionPool cp = (ConnectionPool)application.getAttribute("CPool");
	
	ArticleControl ac = new ArticleControl(cp);
	if(cp==null){
		application.setAttribute("CPool", ac.getCP());
	}
	ArticleObject similar = new ArticleObject();
	
	similar.setArticle_section_id((short)2);
	  
	
	//Lấy từ khóa tìm kiếm nếu có
	String keyword = request.getParameter("key");
	String saveKey = (keyword!=null && !keyword.equalsIgnoreCase("")) ? keyword.trim() : "";
	similar.setArticle_title(Utilities.encode(saveKey));
	
	String tags = request.getParameter("tag");
	String saveTag = (tags!=null && !tags.equalsIgnoreCase("")) ? tags.trim() : "";
	similar.setArticle_tag(Utilities.encode(saveTag));
	
	if(at!=-1){
		
		int id = Utilities.getIntParam(request, "id");
		Quartet<ArticleObject, Short, Byte, Boolean> infors;
		if(id>0){
			similar.setArticle_id(id);
			infors = new Quartet<>(similar, (short)1, (byte)5, true); // true: xem chi tiết false để xem danh mục - 5 là số bản ghi
			ArrayList<String> news = ac.viewNews(infors);
		}else{		
			short cid = Utilities.getShortParam(request, "cid");
			similar.setArticle_category_id(cid);
			short p = Utilities.getShortPage(request, "page");
			similar.setArticle_category_id(cid);
			infors = new Quartet<>(similar, p, (byte)5, false);			
			}
		ArrayList<String> news = ac.viewNews(infors);
		if(news.size()>0){
			//Gửi cấu trúc hiển thị vào phiên
			session.setAttribute("news", news.get(0));
		}
	}else{
		Quartet<ArticleObject, Short, Byte, Boolean> infors = new Quartet<>(similar, (short)1, (byte)5, false);	
		ArrayList<String> postGrid = ac.viewPostGrid(infors);
		//Gửi cấu trúc hiển thị vào phiên
		session.setAttribute("postGrid", postGrid);
	}
	
	ac.releaseConnection();
	
%>