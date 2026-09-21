package model.entities;

import java.time.LocalDate;
import java.util.List;

import constants.AccessLevel;

public class Manager extends Worker {

			

	public Manager(long id, String name, String cpf, String workShift, double salary, LocalDate hireDate,
			LocalDate vacationStart, LocalDate vacantionEnd,AccessLevel accessLevel) {
		super(id, name, cpf, workShift, salary, hireDate, vacationStart, vacantionEnd, AccessLevel.MANAGER);
		
		
	}
	



	public Manager(long id, String name, String cpf, AccessLevel accessLevel) {
		super(id, name, cpf, AccessLevel.MANAGER);
		// TODO Auto-generated constructor stub
	}




	@Override
	public List<String> getAccess() {
		// TODO Auto-generated method stub
		return super.getAccess();
		
	}


	@Override
	public String toString() {
		return "Administrator [getAccess()=" + getAccess() + "]";
	}
	
	
	
}
