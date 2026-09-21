package model.entities;

import java.time.LocalDate;
import java.util.List;

import constants.AccessLevel;


public abstract class Worker {
	
	private long id;
	private String name,cpf,workShift;
	private double salary;
	private LocalDate hireDate,vacationStart,vacantionEnd;
	private AccessLevel accessLevel;
	
	
	
	
	public Worker(long id, String name, String cpf, String workShift, double salary, LocalDate hireDate,
			LocalDate vacationStart, LocalDate vacantionEnd,AccessLevel acessLevel) {
		super();
		this.id = id;
		this.name = name;
		this.cpf = cpf;
		this.workShift = workShift;
		this.salary = salary;
		this.hireDate = hireDate;
		this.vacationStart = vacationStart;
		this.vacantionEnd = vacantionEnd;
	}
	
	




	public Worker(long id, String name, String cpf, AccessLevel accessLevel) {
		super();
		this.id = id;
		this.name = name;
		this.cpf = cpf;
		this.accessLevel = accessLevel;
	}






	public List<String> getAccess(){
		return accessLevel.getPermissions();
		}
	
	
	
	
	
	
}
