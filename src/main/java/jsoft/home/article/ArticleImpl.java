package jsoft.home.article;

import java.sql.ResultSet;
import java.util.ArrayList;

import org.javatuples.Quartet;
import org.javatuples.Triplet;

import jsoft.ConnectionPool;
import jsoft.home.basic.BasicImpl;
import jsoft.objects.ArticleObject;

public class ArticleImpl extends BasicImpl implements Article {

	public ArticleImpl(ConnectionPool cp) {
		super(cp, "Article-Home");
		// TODO Auto-generated constructor stub
	}


	@Override
	public synchronized ResultSet getArticle(int id) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM tblarticle ");
		sql.append("LEFT JOIN tblcategory ON tblarticle_category_id = category_id ");
		sql.append("LEFT JOIN tblsection ON category_section_id = section_id ");
		sql.append("WHERE (article_id=?) AND (article_delete=0) AND (article_enable=1) ");
		sql.append("");
		return this.get(sql.toString(), id);
	}

	@Override
	public synchronized ArrayList<ResultSet> getArticles(Quartet<ArticleObject, Short, Byte, Boolean> infors) {
		// TODO Auto-generated method stub
		ArticleObject similar = infors.getValue0();
		byte totalperpage = infors.getValue2();
		int at = (infors.getValue1()-1)*totalperpage;
		boolean isDetail = infors.getValue3();
		
		StringBuilder sql = new StringBuilder();
		
		//Danh sách bài viết mới nhất không ảnh hưởng bởi phân trang
		sql.append("SELECT * FROM tblarticle ");
		sql.append("LEFT JOIN tblcategory ON article_category_id = category_id ");
		sql.append("LEFT JOIN tblsection ON category_section_id = section_id ");
		sql.append("WHERE (article_delete=0) AND (article_enable=1) ");
		sql.append(this.createConditions(similar,true));
		sql.append(" ORDER BY article_id DESC ");
		sql.append(" LIMIT 5;");
		
		//Danh sách bài viết xem nhiều nhất không bị ảnh hưởng bởi phân trang
		sql.append("SELECT * FROM tblarticle ");
		sql.append("LEFT JOIN tblcategory ON article_category_id = category_id ");
		sql.append("LEFT JOIN tblsection ON category_section_id = section_id ");
		sql.append("WHERE (article_delete=0) AND (article_enable=1) ");
		sql.append(this.createConditions(similar,true));
		sql.append(" ORDER BY article_visited DESC ");
		sql.append(" LIMIT 5;");//.append(at).append(", ").append(totalperpage).append("; ");
		
		//Danh sách thể loại
		sql.append("SELECT * FROM tblcategory ");
		sql.append("WHERE (category_section_id=").append(similar.getArticle_section_id()).append(") ");
		sql.append("ORDER BY category_name ASC; ");
		
		//Danh sách Tag bài viết
		sql.append("SELECT article_tag FROM tblarticle ");
		sql.append("WHERE article_section_id = ").append(similar.getArticle_section_id()).append(" ; ");
		
		if(isDetail) {
			sql.append("SELECT * FROM tblarticle ");
			sql.append("LEFT JOIN tblcategory ON article_category_id = category_id ");
			sql.append("LEFT JOIN tblsection ON category_section_id = section_id ");
			sql.append("WHERE (article_delete=0) AND (article_enable=1) AND (article_id= ").append(similar.getArticle_id()).append(" );");
			
		}else {
			
			//Bài viết mới có phân trang
			sql.append("SELECT * FROM tblarticle ");
			sql.append("LEFT JOIN tblcategory ON article_category_id = category_id ");
			sql.append("LEFT JOIN tblsection ON category_section_id = section_id ");
			sql.append("WHERE (article_delete=0) AND (article_enable=1) ");
			sql.append(this.createConditions(similar,false));
			sql.append(" ORDER BY article_id DESC ");
			sql.append(" LIMIT ").append(at).append(", ").append(totalperpage).append("; ");
			
			//Tổng số bài viết
			sql.append("SELECT COUNT(article_id) AS total FROM tblarticle ");
			sql.append("WHERE (article_delete=0) AND (article_enable=1) ");
			sql.append(this.createConditions(similar,false)).append("; ");
			
		}
		
		return this.getReLists(sql.toString());
	}
	/**
	 * Khởi tạo điều kiện cho mệnh đề WHERE
	 * 
	 * @param similar - Đối tượng lưu trữ thông tin
	 * @param disabled_cate - Loại bỏ thể loại trong truy vấn nếu truyền vào là True
	 * @return
	 */
	private StringBuilder createConditions(ArticleObject similar, boolean disabled_cate) {
		StringBuilder tmp = new StringBuilder();
		if(similar!=null) {
			Short sid = similar.getArticle_section_id();
			if(sid == 0) {
				sid  = similar.getCategory_section_id();
			}
			if(sid == 0) {
				sid = similar.getSection_id();
			}
			if(!disabled_cate) {
				if(sid > 0) {
					tmp.append(" ( article_section_id =").append(sid).append(") ");
				}
				Short cid = similar.getArticle_category_id();
				if(cid==0) {
					cid=similar.getCategory_id();
				}
				if(cid > 0) {
					if(!tmp.toString().equalsIgnoreCase("")) {
						tmp.append(" AND ");
					}
					tmp.append(" ( article_category_id = ").append(cid).append(") ");
				}
			}
			
			//Lấy từ khóa tìm kiếm
			String key  = similar.getArticle_title();
			if(!key.equalsIgnoreCase("")) {
				if(!tmp.toString().equalsIgnoreCase("")) {
					tmp.append(" AND ");
				}
				tmp.append("(");
				tmp.append("(article_title LIKE '%"+key+"%' ) OR ");
				tmp.append("(article_summary LIKE '%"+key+"%' ) OR ");
				tmp.append("(article_content LIKE '%"+key+"%' ) OR ");
				tmp.append("(article_tag LIKE '%"+key+"%' ) ");
				tmp.append(")");
			}
			
			//Lấy thẻ tìm kiếm 
			String tag = similar.getArticle_tag();
			if(!tag.equalsIgnoreCase("")) {
				if(!tmp.toString().equalsIgnoreCase("")) {
					tmp.append(" AND ");
				}
				tmp.append("(article_tag LIKE '%"+tag+"%' )");
			}	
		}
		
		if(!tmp.toString().equalsIgnoreCase("")) {
			// Chèn AND lên đầu chuỗi
			tmp.insert(0, " AND ");
		}
		
		return tmp;
	}


}
