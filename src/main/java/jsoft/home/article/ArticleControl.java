package jsoft.home.article;

import java.util.ArrayList;
import java.util.HashMap;

import org.javatuples.*;

import jsoft.*;
import jsoft.objects.*;
public class ArticleControl {
	
	private ArticleModel am;
	

	public ArticleControl(ConnectionPool cp) {
		this.am = new ArticleModel(cp);
	}
	public ConnectionPool getCP() {
		return this.am.getCP();
	}
	
	public void releaseConnection() {
		this.am.releaseConnection();
	}
		
	//****************************************************
	public ArticleObject getArticleObject(int id) {
		return this.am.getArticleObject(id);
	}
	
	public ArrayList<String> viewPostGrid(Quartet<ArticleObject, Short, Byte, Boolean> infors){
		Pair<ArrayList<ArticleObject>, ArrayList<ArticleObject>> datas = this.am.getArticles(infors);
		return ArticleLibrary.viewPostGrid(datas);
	}
	
	public ArrayList<String> viewNews(Quartet<ArticleObject, Short, Byte, Boolean> infors){
		Sextet<		ArrayList<ArticleObject>, 
					ArrayList<ArticleObject>, 
					ArrayList<CategoryObject>, 
					HashMap<String, Integer>, 
					Integer,
					ArrayList<ArticleObject>
					> datas = this.am.getNewsArticlesObject(infors);
		
		if(infors.getValue3()) {
			return ArticleLibrary.viewDetail(datas);
		}else {
			return ArticleLibrary.viewNews(datas, infors);
		}
		
	}
	
}
