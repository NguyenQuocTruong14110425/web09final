package service;

import java.util.List;

import model.UserDocument;

public interface UserDocumentService {

	UserDocument findById(int id);

	List<UserDocument> findAll();
	
	List<UserDocument> findAllByNewsId(int id);
	
	void saveDocument(UserDocument document);
	
	void deleteById(int id);
}
