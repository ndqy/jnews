package jsoft.home.article;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

import jsoft.*;
import jsoft.home.library.Utilities;
import jsoft.objects.ArticleObject;
import jsoft.objects.CategoryObject;

import org.javatuples.*;

public class ArticleModel {
	private Article a;
	
	public ArticleModel(ConnectionPool cp) {
		this.a = new ArticleImpl(cp);
	}
	
	protected void finallize() throws Throwable{
		this.a = null;
	}
	public ConnectionPool getCP() {
		return this.a.getCP();
	}
	
	public void releaseConnection() {
		this.a.releaseConnection();
	}
	
	
	public ArticleObject getArticleObject(int id) {
		ArticleObject item = null;
		ResultSet rs = this.a.getArticle(id);
		if (rs != null) {
			try {
				if (rs.next()) {
					item = new ArticleObject();
					item.setArticle_id(rs.getShort("article_id"));
					item.setArticle_title(rs.getString("article_title"));
					item.setArticle_summary(rs.getString("article_summary"));
					// item.setArticle_content(rs.getString("article_content"));
					item.setArticle_image(rs.getString("article_image"));
					item.setArticle_created_date(rs.getString("article_created_date"));
					item.setArticle_last_modified(rs.getString("article_last_modified"));
					item.setArticle_author_name(rs.getString("article_author_name"));
					item.setArticle_tag(rs.getString("article_tag"));
					

					item.setCategory_id(rs.getShort("Category_id"));
					item.setCategory_name(rs.getString("Category_name"));
					
					item.setSection_id(rs.getShort("Section_id"));
					item.setSection_name(rs.getString("Section_name"));
										
				}
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return item;
	}
	
	public Pair<ArrayList<ArticleObject>,ArrayList<ArticleObject>> getArticles(Quartet<ArticleObject, Short, Byte,Boolean> infors){
		
		ArrayList<ResultSet> res = this.a.getArticles(infors);
		
		return new Pair<>(this.getArticleObject(res.get(0), false),this.getArticleObject(res.get(1),false));
	}
	
	/**
	 * Chuyển tập kết quả truy vấn sang tập đối tượng
	 * @param rs
	 * @param isDetail có phải là xem chi tiết hay không
	 * @return
	 */
	private ArrayList<ArticleObject> getArticleObject(ResultSet rs, boolean isDetail){
		
		ArrayList<ArticleObject> items = new ArrayList<>();
		ArticleObject item = null;
		if (rs != null) {
			try {
				while (rs.next()) {
					item = new ArticleObject();
					item.setArticle_id(rs.getShort("article_id"));
					item.setArticle_title(rs.getString("article_title"));
					item.setArticle_summary(rs.getString("article_summary"));
					if(isDetail) {
						item.setArticle_content(Utilities.decode(rs.getString("article_content")));//Giải mã nội dung
					}
					
					item.setArticle_image(rs.getString("article_image"));
					item.setArticle_created_date(rs.getString("article_created_date"));
					//item.setArticle_last_modified(rs.getString("article_last_modified"));
					item.setArticle_author_name(rs.getString("article_author_name"));
					item.setArticle_tag(rs.getString("article_tag"));
					

					item.setCategory_id(rs.getShort("Category_id"));
					item.setCategory_name(rs.getString("Category_name"));
					
					item.setSection_id(rs.getShort("Section_id"));
					item.setSection_name(rs.getString("Section_name"));
					
					items.add(item);
				}
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return items;
	}
	
	public Sextet<	ArrayList<ArticleObject>, // 10 bài viết mới nhất
					ArrayList<ArticleObject>, // xu hướng
					ArrayList<CategoryObject>, 
					HashMap<String, Integer>,
					Integer,
					ArrayList<ArticleObject> // Bài viết theo phân trang
					> 	getNewsArticlesObject(Quartet<ArticleObject, Short, Byte, Boolean> infors){
		
		ArrayList<ResultSet> res = this.a.getArticles(infors);
		boolean isDetail = infors.getValue3();
		ArrayList<CategoryObject> cates = new ArrayList<>();
		CategoryObject cate = null;
		ResultSet rs = res.get(2);
		if(rs!=null) {
			try {
				while(rs.next()) {
					cate = new CategoryObject();
					cate.setCategory_id(rs.getShort("category_id"));
					cate.setCategory_name(rs.getString("category_name"));
					
					cates.add(cate);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		HashMap<String,Integer> tags = new HashMap<>();
		rs=res.get(3);
		String tag;
		String[] tag_list;
		if(rs!=null) {
			try {
				while(rs.next()) {
					tag = Utilities.decode(rs.getString("article_tag")).toLowerCase();
					tag_list = tag.split(",");
					for(String word:tag_list) {		
						if(!"".equalsIgnoreCase(word)) {
							word=word.trim();
							if(tags.containsKey(word)){
								tags.replace(word, tags.get(word)+1);
							}else {
								tags.put(word, 1);
							}
						}
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//Loại bỏ những phẩn tử có giá trị nhỏ hơn 3
		tags.keySet().removeAll(
				tags.entrySet().stream().filter(a -> a.getValue().compareTo(3)<0)
					.map(e -> e.getKey()).collect(Collectors.toList())
						);
		int total = 0;
		if(!isDetail) {
			rs = res.get(5);//Lấy tổng số bài viết
			
			if(rs!=null) {
				try {
					if(rs.next()) {
						total = rs.getInt("total");
					}	
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		}
		//return new Pair<>(this.getArticleObject(res.get(0)),this.getArticleObject(res.get(1))).addAt2(cates);//addAt2: thêm vào vị trí thứ 2 trong javatuple
		return new Sextet(this.getArticleObject(res.get(0),false),
							this.getArticleObject(res.get(1),false),
							cates,
							tags,
							total,
							this.getArticleObject(res.get(4),isDetail));
	}
	
}
