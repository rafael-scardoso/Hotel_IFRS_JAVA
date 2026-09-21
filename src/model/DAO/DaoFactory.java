package model.DAO;

import model.DAO.imp.*;

public class DaoFactory {
	
	public static HotelDAO createHotelDao() {return new HotelDaoJDBC();}
	public static CustumerDAO createCustumerDAO() {return new CustumoerDaoJDBC();}
	public static ReservationDAO createReservationDAO() {return new ReservationDaoJDBC();}
	public static ReservationServiceDAO createReservationServiceDAO() {return new ReservationServiceDaoJDBC();}
	public static RoomDAO createRoomDAO() {return new RoomDaoJDBC();}
	public static ServiceDAO createServiceDAO() {return new ServiceDaoJDBC();}
	public static WorkerDAO createWorkerDAO() {return new WorkerDaoJDBC();}
	

	
	
	
	
	
	
	
	
}
