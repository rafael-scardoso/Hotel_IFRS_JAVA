package model.DAO.imp;

import java.util.List;

import model.DAO.RoomDAO;

public class RoomDaoJDBC implements RoomDAO  {

	@Override
	public void insert(Object obj) {
		// TODO Auto-generated method stub
		RoomDAO.super.insert(obj);
	}

	@Override
	public void update(Object obj) {
		// TODO Auto-generated method stub
		RoomDAO.super.update(obj);
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		RoomDAO.super.deleteById(id);
	}

	@Override
	public Object findById(Long id) {
		// TODO Auto-generated method stub
		return RoomDAO.super.findById(id);
	}

	@Override
	public List<Object> listAll() {
		// TODO Auto-generated method stub
		return RoomDAO.super.listAll();
	}

}
