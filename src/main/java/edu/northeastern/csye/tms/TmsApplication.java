package edu.northeastern.csye.tms;

import edu.northeastern.csye.tms.exception.DatabaseDownException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TmsApplication {

	public static void main(String[] args) throws DatabaseDownException {
		try{
			SpringApplication.run(TmsApplication.class, args);
		}
		catch (BeanCreationException ex){
			if (ex.getLocalizedMessage().contains("Error creating bean with name 'entityManagerFactory'")){
				throw new DatabaseDownException();
			}
		}
	}

}