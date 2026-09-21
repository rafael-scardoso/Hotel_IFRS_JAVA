package model.DAO;

import java.util.List;

public interface RoomDAO extends DAO<Object> {

	@Override
	default void insert(Object obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default void update(Object obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default void deleteById(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default Object findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default List<Object> listAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
