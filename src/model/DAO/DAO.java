package model.DAO;

import java.util.List;


public interface DAO<T> {

	void insert(T obj);
    void update(T obj);
    void deleteById(Long id);
    T findById(Long id);
    List<T> listAll();
    
	
}
