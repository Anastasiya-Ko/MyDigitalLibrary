package ru.kopylova.springcourse.DigitalLibrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DigitalLibraryApplication {

	public static void main(String[] args) {
		try{

			SpringApplication.run(DigitalLibraryApplication.class, args);
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}

}
