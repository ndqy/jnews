package jsoft.home.article;

import java.util.*;

import org.javatuples.*;

import jsoft.objects.*;

public class ArticleLibrary {
	
	public static ArrayList<String> viewPostGrid(Pair<ArrayList<ArticleObject>,ArrayList<ArticleObject>> datas){
		ArrayList<String> view = new ArrayList<>();
		
		
		ArrayList<ArticleObject> items = datas.getValue0();// Danh sách bài viết mới nhất
		
		ArrayList<ArticleObject> most_items = datas.getValue1();// Danh sách bài viết được xem nhiều nhất
		
		StringBuilder tmp = new StringBuilder();
		
		if(items.size()>0) {
			//Vị trí đầu tiên
			ArticleObject item = items.get(0);
			tmp.append("<div class=\"post-entry-1 lg\">");
			tmp.append("<a href=\"/Home/tin-tuc/?id="+item.getArticle_id()+"\"><img src=\""+item.getArticle_image()+"\" alt=\"\" class=\"img-fluid\"></a>");
			tmp.append("<div class=\"post-meta\"><span class=\"date\">"+item.getCategory_name()+"</span> <span class=\"mx-1\">&bullet;</span> <span>"+item.getCategory_created_date()+"</span></div>");
			tmp.append("<h2><a href=\"/Home/tin-tuc/?id="+item.getArticle_id()+"\">"+item.getArticle_title()+"</a></h2>");
			tmp.append("<p class=\"mb-4 d-block\">"+item.getArticle_summary()+"</p>");
			tmp.append("<div class=\"d-flex align-items-center author\">");
			tmp.append("<div class=\"photo\"><img src=\"/Home/img/person-1.jpg\" alt=\"\" class=\"img-fluid\"></div>");
			tmp.append("<div class=\"name\">");
			tmp.append("<h3 class=\"m-0 p-0\">"+item.getArticle_author_name()+"</h3>");
			tmp.append("</div>");
			tmp.append("</div>");
			tmp.append("</div>");
			
			view.add(tmp.toString());
			
			//Vị trí thứ 2 - 2 cột
			tmp.setLength(0);
			for(int i=1; i<items.size(); i++) {
				if(i%2==1) {
					tmp.append("<div class=\"col-lg-4 border-start custom-border\">");			
				}
				item = items.get(i);
				tmp.append("<div class=\"post-entry-1\">");
				tmp.append("<a href=\"/Home/tin-tuc/?id="+item.getArticle_id()+"\"><img src=\""+item.getArticle_image()+"\" alt=\"\" class=\"img-fluid\"></a>");
				tmp.append("<div class=\"post-meta\"><span class=\"date\">"+item.getCategory_name()+"</span> <span class=\"mx-1\">&bullet;</span> <span>"+item.getArticle_created_date()+"</span></div>");
				tmp.append("<h2><a href=\"/Home/tin-tuc/?id="+item.getArticle_id()+"\">"+item.getArticle_title()+"</a></h2>");
				tmp.append("</div>");
				
				if(i%2==0 || i==items.size()-1) {					
					tmp.append("</div>");
				}
					
			}
			//view.add(tmp.toString());
		}
		
		tmp.append("<div class=\"col-lg-4\">");
		
		tmp.append("<div class=\"trending\">");
		tmp.append("<h3>Trending</h3>");
		tmp.append("<ul class=\"trending-post\">");
		if(most_items.size()>0) {
			most_items.forEach(mi ->{
				tmp.append("<li>");
				tmp.append("<a href=\"/Home/tin-tuc/?id="+mi.getArticle_id()+"\">");
				tmp.append("<span class=\"number\">"+(most_items.indexOf(mi)+1)+"</span>");
				tmp.append("<h3>"+mi.getArticle_title()+"</h3>");
				tmp.append("<span class=\"author\">"+mi.getArticle_author_name()+"</span>");
				tmp.append("</a>");
				tmp.append("</li>");
			});
		}
		tmp.append("</ul>");
		tmp.append("</div>");
		tmp.append("</div><!-- End Trending Section -->");
		view.add(tmp.toString());
		return view;
	}
	public static ArrayList<String> viewNews(
			Sextet<	ArrayList<ArticleObject>,
						ArrayList<ArticleObject>,
						ArrayList<CategoryObject>, 
						HashMap<String, Integer>, 
						Integer,
						ArrayList<ArticleObject> 
						> datas,//Lấy dữ liệu
			Quartet<ArticleObject, Short, Byte, Boolean> infors//dùng để phân trang
			){
		ArrayList<String> view = new ArrayList<>();
		
		
		ArrayList<ArticleObject> items = datas.getValue0();// Danh sách bài viết mới nhất ko phân trang
		ArrayList<ArticleObject> most_items = datas.getValue1();// Danh sách bài viết được xem nhiều nhất		
		ArrayList<CategoryObject> cates = datas.getValue2(); // Danh sách thể loại
		HashMap<String, Integer> tags = datas.getValue3();//Danh sách tags
		
		ArticleObject similar = infors.getValue0();
		short page = infors.getValue1();//Số trang
		byte totalperpage = infors.getValue2();//Số bài viết trên trang
		int total = datas.getValue4();//Tổng só bài viết
		
		ArrayList<ArticleObject> pitems = datas.getValue5();// Danh sách bài viết mới nhất có phân trang
		
		StringBuilder tmp = new StringBuilder();
			
		tmp.append("<section>");
		tmp.append("<div class=\"container\">");
		tmp.append("<div class=\"row\">");
		
		tmp.append("<div class=\"col-md-9\" data-aos=\"fade-up\">");
		tmp.append(ArticleLibrary.viewCateOption(cates, similar));
		pitems.forEach(item -> {
			//Hiển thị 10 cái 
			
				tmp.append("<div class=\"d-md-flex post-entry-2 half\">");
				tmp.append("<a href=\"/Home/tin-tuc/?id="+item.getArticle_id()+"\" class=\"me-4 thumbnail\">");
				tmp.append("<img src=\""+item.getArticle_image()+"\" alt=\""+item.getArticle_title()+"\" class=\"img-fluid\">");
				tmp.append("</a>");
				tmp.append("<div>");
				tmp.append("<div class=\"post-meta\"><span class=\"date\">"+item.getCategory_name()+"</span> <span class=\"mx-1\">&bullet;</span> <span>"+item.getCategory_created_date()+"</span></div>");
				tmp.append("<h3><a href=\"/Home/tin-tuc/?id="+item.getArticle_id()+"\">"+item.getArticle_title()+"</a></h3>");
				tmp.append("<p>"+item.getArticle_summary()+"</p>");
				tmp.append("<div class=\"d-flex align-items-center author\">");
				tmp.append("<div class=\"photo\"><img src=\"/Home/img/person-2.jpg\" alt=\"\" class=\"img-fluid\"></div>");
				tmp.append("<div class=\"name\">");
				tmp.append("<h3 class=\"m-0 p-0\">"+item.getArticle_author_name()+"</h3>");
				tmp.append("</div>");
				tmp.append("</div>");
				tmp.append("</div>");
				tmp.append("</div>");
			
		});
		
		//________________________PHÂN TRANG________________________
		tmp.append(ArticleLibrary.getPagination(ArticleLibrary.createURL(similar).toString(), total, page, totalperpage));
		
		tmp.append("</div>");//col-md-9
		
		//___________________HIỂN THỊ SIDEBAR______________________
		tmp.append(viewSidebar(items, most_items, cates, tags));
		
		
		tmp.append("</div>");
		tmp.append("</div>");
		tmp.append("</section>");
		
		
		view.add(tmp.toString());
		
		return view;
	}
	
	public static StringBuilder createURL(ArticleObject similar) {
		StringBuilder tmp = new StringBuilder("/Home/tin-tuc/?");
		if(similar.getArticle_category_id() > 0) {
			tmp.append("cid=").append(similar.getArticle_category_id());
		}
		String key = similar.getArticle_title();
		if(key!=null && !key.equalsIgnoreCase("")) {
			if(tmp.indexOf("=") !=-1) {
				tmp.append("&");
			}
			tmp.append("key=").append(key);
		}
		
		String tag = similar.getArticle_tag();
		if(tag!=null && !tag.equalsIgnoreCase("")) {
			if(tmp.indexOf("=") !=-1) {
				tmp.append("&");
			}
			tmp.append("tag=").append(tag);
		}
		
		return tmp;
	}
	
	public static ArrayList<String> viewDetail(
			Sextet<	ArrayList<ArticleObject>,
						ArrayList<ArticleObject>,
						ArrayList<CategoryObject>, 
						HashMap<String, Integer>, 
						Integer,
						ArrayList<ArticleObject> 
						> datas//Lấy dữ liệu
						){
		ArrayList<String> view = new ArrayList<>();
		
		
		ArrayList<ArticleObject> items = datas.getValue0();// Danh sách bài viết mới nhất ko phân trang
		ArrayList<ArticleObject> most_items = datas.getValue1();// Danh sách bài viết được xem nhiều nhất		
		ArrayList<CategoryObject> cates = datas.getValue2(); // Danh sách thể loại
		HashMap<String, Integer> tags = datas.getValue3();//Danh sách tags
		
		
		int total = datas.getValue4();//Tổng só bài viết
		
		ArrayList<ArticleObject> pitems = datas.getValue5();// Danh sách bài viết mới nhất có phân trang
		ArticleObject item = null;
		if(pitems.size()>0) {
			item = pitems.get(0);
		}
		
		StringBuilder tmp = new StringBuilder();
			
		tmp.append("<section class=\"single-post-content\" >");
		tmp.append("<div class=\"container\">");
		tmp.append("<div class=\"row\">");
		//char first = item.getArticle_summary().charAt(0);
		//String summary = item.getArticle_summary().substring(1);
		tmp.append(" <div class=\"col-md-9 post-content\" data-aos=\"fade-up\">");
		if(item!=null) {
			tmp.append("<div class=\"single-post\">");
			tmp.append("<div class=\"post-meta\"><span class=\"date\">"+item.getCategory_name()+"</span> <span class=\"mx-1\">&bullet;</span> <span>"+item.getArticle_created_date()+"</span></div>\r\n");
			tmp.append("<h1 class=\"mb-5\">"+item.getArticle_title()+"</h1>");
			//tmp.append("<p><span class=\"firstcharacter\">"+first+"</span>"+summary+"</p>");
			// Hiện thị tiêu đề
			tmp.append(ArticleLibrary.viewSummary(item.getArticle_summary()));
			tmp.append("<figure class=\"my-4 bg-light shadow\">");
			tmp.append("<img src=\""+item.getArticle_image()+"\" alt=\"\" class=\"img-fluid\">");
			tmp.append("<figcaption>"+item.getArticle_title()+"</figcaption>");
			tmp.append("</figure>");
			tmp.append("<div class=\"detail\">"+item.getArticle_content()+"</div>");
			tmp.append("</div>");
		}
		tmp.append("</div>");//col-md-9
		
		//___________________HIỂN THỊ SIDEBAR______________________
		tmp.append(viewSidebar(items, most_items, cates, tags));
		
		
		tmp.append("</div>");
		tmp.append("</div>");
		tmp.append("</section>");
		
		
		view.add(tmp.toString());
		
		return view;
	}
	
	private static StringBuilder viewSidebar(
			ArrayList<ArticleObject> items,
			ArrayList<ArticleObject> most_items,
			ArrayList<CategoryObject> cates,
			HashMap<String, Integer> tags) {
		
		StringBuilder tmp = new StringBuilder();
		tmp.append("<div class=\"col-md-3\">");
		tmp.append("<!-- ======= Sidebar ======= -->");
		tmp.append("<div class=\"aside-block\">");
		
		tmp.append("<ul class=\"nav nav-pills custom-tab-nav mb-4\" id=\"pills-tab\" role=\"tablist\">");
		tmp.append("<li class=\"nav-item\" role=\"presentation\">");
		tmp.append("<button class=\"nav-link active\" id=\"pills-trending-tab\" data-bs-toggle=\"pill\" data-bs-target=\"#pills-trending\" type=\"button\" role=\"tab\" aria-controls=\"pills-trending\" aria-selected=\"true\">Xu hướng</button>");
		tmp.append("</li>");
		tmp.append("<li class=\"nav-item\" role=\"presentation\">");
		tmp.append("<button class=\"nav-link\" id=\"pills-latest-tab\" data-bs-toggle=\"pill\" data-bs-target=\"#pills-latest\" type=\"button\" role=\"tab\" aria-controls=\"pills-latest\" aria-selected=\"false\">Mới nhất</button>");
		tmp.append("</li>");
		tmp.append("</ul>");
		
		tmp.append("<div class=\"tab-content\" id=\"pills-tabContent\">");
		
		tmp.append("<!-- Trending -->");
		tmp.append("<div class=\"tab-pane fade show active\" id=\"pills-trending\" role=\"tabpanel\" aria-labelledby=\"pills-trending-tab\">");
		
		most_items.forEach(item -> {
			tmp.append("<div class=\"post-entry-1 border-bottom\">");
			tmp.append("<div class=\"post-meta\"><span class=\"date\">"+item.getCategory_name()+"</span> <span class=\"mx-1\">&bullet;</span> <span>"+item.getArticle_created_date()+"</span></div>");
			tmp.append("<h2 class=\"mb-2\"><a href=\"/Home/tin-tuc/?id="+item.getArticle_id()+"\">"+item.getArticle_title()+"</a></h2>");
			tmp.append("<span class=\"author mb-3 d-block\">"+item.getArticle_author_name()+"</span>");
			tmp.append("</div>");
		});
		
		tmp.append("</div> <!-- End Trending -->");
		
		tmp.append("<!-- Latest -->");
		tmp.append("<div class=\"tab-pane fade\" id=\"pills-latest\" role=\"tabpanel\" aria-labelledby=\"pills-latest-tab\">");
		
		items.forEach(item -> {
			tmp.append("<div class=\"post-entry-1 border-bottom\">");
			tmp.append("<div class=\"post-meta\"><span class=\"date\">"+item.getCategory_name()+"</span> <span class=\"mx-1\">&bullet;</span> <span>"+item.getArticle_created_date()+"</span></div>");
			tmp.append("<h2 class=\"mb-2\"><a href=\"/Home/tin-tuc/?id="+item.getArticle_id()+"\">"+item.getArticle_title()+"</a></h2>");
			tmp.append("<span class=\"author mb-3 d-block\">"+item.getArticle_author_name()+"</span>");
			tmp.append("</div>");
		});
		
		tmp.append("</div> <!-- End Latest -->");
		
		tmp.append("</div>");
		tmp.append("</div>");
		
		tmp.append("<div class=\"aside-block\">");
		tmp.append("<h3 class=\"aside-title\">Video</h3>");
		tmp.append("<div class=\"video-post\">");
		tmp.append("<a href=\"https://www.youtube.com/watch?v=AiFfDjmd0jU\" class=\"glightbox link-video\">");
		tmp.append("<span class=\"bi-play-fill\"></span>");
		tmp.append("<img src=\"assets/img/post-landscape-5.jpg\" alt=\"\" class=\"img-fluid\">");
		tmp.append("</a>");
		tmp.append("</div>");
		tmp.append("</div><!-- End Video -->");
		
		tmp.append("<div class=\"aside-block\">");
		tmp.append("<h3 class=\"aside-title\">Categories</h3>");
		tmp.append("<ul class=\"aside-links list-unstyled\">");
		cates.forEach(cate ->{
			tmp.append("<li><a href=\"/Home/tin-tuc/?cid="+cate.getCategory_id()+"\"><i class=\"bi bi-chevron-right\"></i> "+cate.getCategory_name()+"</a></li>");
		});
		tmp.append("</ul>");
		tmp.append("</div><!-- End Categories -->");
		
		tmp.append("<div class=\"aside-block\">");
		tmp.append("<h3 class=\"aside-title\">Tags</h3>");
		tmp.append("<ul class=\"aside-tags list-unstyled\">");
		tags.forEach((tag, number) ->{
			tmp.append("<li><a href=\"/Home/tin-tuc/?tag="+tag+"\">"+tag+" ("+number+")</a></li>");
		});
		tmp.append("</div><!-- End Tags -->");
		
		tmp.append("</div>");
		
		return tmp;
	}
	private static StringBuilder viewCateOption(ArrayList<CategoryObject> cates, ArticleObject similar) {
		
		StringBuilder tmp = new StringBuilder();
		
		tmp.append("<div class=\"row align-items-center mb-5\" >");
		tmp.append("<div class=\"col-sm-2\" >");
		tmp.append("<h3 style=\"font-size=18\" class=\"fs-4\">Thể loại: </h3>");
		tmp.append("</div>");
		tmp.append("<div class=\"col-sm-4\" >");
		tmp.append("<form method=\"\" method=\"\" >");
		tmp.append("<select class=\"form-select\" name =\"slcCateID\" onChange=\"refreshCate(this.form)\" >");
		tmp.append("<option value=\"0\" >----Chọn----</option>");
		cates.forEach(cate ->{
			if(cate.getCategory_id()==similar.getArticle_category_id()) {
				tmp.append("<option value=\"").append(cate.getCategory_id()).append("\" selected>");
			}else {
				tmp.append("<option value=\"").append(cate.getCategory_id()).append("\" >");
			}
			tmp.append(cate.getCategory_name());
			tmp.append("</option>");
		});
		tmp.append("</select>");
		tmp.append("</form>");
		tmp.append("</div>");
		
		tmp.append("</div>");
		
		
		tmp.append("<script language=\"javascript\" >");
		tmp.append("function refreshCate(fn){");
		tmp.append("let cate_id = fn.slcCateID.value;");
		tmp.append("fn.method = 'post';");
		tmp.append("fn.action = `/Home/tin-tuc/?cid=${cate_id}`;");
		tmp.append("fn.submit();");
		tmp.append("}");
		tmp.append("</script>");

		return tmp;
	}
	private static StringBuilder getPagination(String url, int total, short page, byte totalperpage) { // Đường dẫn - Tổng số bản ghi - trang hiện tại - số bản ghi/trang
		
		StringBuilder tmp = new StringBuilder();
		
		//Đếm tổng số trang
		short pages = (short) (total / totalperpage);
		if(total % totalperpage != 0) {
			pages++;
		}
		//Trang không hợp lệ thì quay về trang 1
		if(page > pages || page <=0) {
			page = 1;
		}
		
		//1,2,...,[page-1],[page],[page+1],...,10,11

		tmp.append("<div class=\"text-start py-4\">");
		tmp.append("<div class=\"custom-pagination\">");
		if(page == 1) {
			tmp.append("<a class=\"prev\"  href=\"#\" tabindex=\"-1\" aria-disabled=\"true\" disabled=\"disabled\" ><span aria-hidden=\"true\">&laquo;</span></a>");	
		}else {
			tmp.append("<a class=\"prev\" href=\""+url+"&page="+(page -1)+ "\"tabindex=\"-1\" aria-disabled=\"true\"><span aria-hidden=\"true\">&laquo;</span></a>");
		}
		
		//Left Current 
		String leftcurrent = "";
		int count = 0;
		for(int i=page-1; i>0; i--) {
			leftcurrent ="<a class=\"prev\" href=\""+url+"&page="+i+"\">"+i+"</a>"+leftcurrent;
			if(++count>=2) {
				break;
			}
		}
		if( page == 4 ) {
			tmp.append("<a class=\"prev\" href=\""+url+"&page=1\">"+1+"</a>");
		}
		if(page>4) {
			leftcurrent = "<a class=\"prev\" href=\"#\" disabled=\"disabled\">...</a>"+leftcurrent;
			leftcurrent = "<a class=\"prev\" href=\""+url+"&page=1"+"\"><span aria-hidden=\"true\">1</span></a>"+leftcurrent;
		}
			
		tmp.append(leftcurrent);
		
		tmp.append("<a class=\"prev active\" href=\"#\">"+page+"</a>");
		
		//Right Current
		String rightcurrent = "";
		count=0;
		for(int i=page+1; i<pages; i++) {
			rightcurrent +="<a class=\"prev\" href=\""+url+"&page="+i+"\">"+i+"</a>";
			if(++count>=2) {
				break;
			}
		}
		
		if(page<pages-3) {
			rightcurrent += "<a class=\"prev\" href=\"#\" disabled = \"disabled\">...</a>";
			rightcurrent += "<a class=\"prev\" href=\""+url+"&page="+pages+"\">"+pages+"</a>";
			rightcurrent += "<a class=\"prev\" href=\""+url+"&page="+(page+1)+"\" tabindex=\"-1\" aria-disabled=\"true\" ><span aria-hidden=\"true\">&raquo;</span></a>";			
			
		}
		tmp.append(rightcurrent);
		if(page == pages-2 || page == pages-3) {
			tmp.append("<a class=\"prev\" href=\""+url+"&page="+(page+2)+"\">"+pages+"</a>");
			tmp.append("<a class=\"prev\" href=\""+url+"&page="+(page+1)+"\" tabindex=\"-1\" aria-disabled=\"true\" ><span aria-hidden=\"true\">&raquo;</span></a>");
		}
		if( page == pages-1 ) {
			tmp.append("<a class=\"prev\" href=\""+url+"&page="+(page+1)+"\">"+pages+"</a>");
			tmp.append("<a class=\"prev\" href=\""+url+"&page="+(page+1)+"\" tabindex=\"-1\" aria-disabled=\"true\" ><span aria-hidden=\"true\">&raquo;</span></a>");
	
		}
		if(page == pages) {
			tmp.append("<a class=\"prev\" href=\"#\" tabindex=\"-1\" aria-disabled=\"false\" disabled=\"disabled\"><span aria-hidden=\"true\">&raquo;</span></a>");
		}/*else {
			tmp.append("<a class=\"prev\" href=\""+url+"&page="+(page+1)+"\" tabindex=\"-1\" aria-disabled=\"true\" ><span aria-hidden=\"true\">&raquo;</span></a>");			
		}*/
		
		
		//tmp.append("<li class=\"page-item disabled\"><a class=\"page-link\" href=\""+url+"?page="+((page<pages) ? (page+1): pages)+"\" tabindex=\"-1\" aria-disabled=\"true\" ><span aria-hidden=\"true\">&raquo;</span></a></li>");
			
		tmp.append("</div>");
		tmp.append("</div>");
		
		return tmp;
	}
	
	private static StringBuilder viewSummary(String str) {
		StringBuilder tmp = new StringBuilder();
		if(str!=null && !str.equalsIgnoreCase("")) {
			tmp.append("<p><span class=\"firstcharacter\">"+str.substring(0,1)+"</span>"+"<span class=\"fw-semibold fs-6\" >"+ str.substring(1)+"</span></p>");
		}
		return tmp;
	}
}
