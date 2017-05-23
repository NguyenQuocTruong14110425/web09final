package dao;

import java.util.List;

import model.UserDocument;

public interface UserDocumentDao {
	 
    List<UserDocument> findAll();
     
    UserDocument findById(int id);
     
    void save(UserDocument document);
     
    List<UserDocument> findAllByNewsId(int newsId);
     
    void deleteById(int id);
}