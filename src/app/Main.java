package app;

import java.time.LocalDate;

import java.util.Scanner;

import constants.AccessLevel;
import db_control.ConnectionFactory;
import model.entities.Administrator;

public class Main {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);

        ConnectionFactory.getConnection();
        
        
        ConnectionFactory.closeConnection();

        
        sc.close();
	}
	
}
