package dao;

import java.util.List;

import model.NewsConten;;

public interface NewsDao {
	NewsConten findById(int id);
	
	void save(NewsConten news);

	void deleteById(int id);

	List<NewsConten> findAllNews();
}
