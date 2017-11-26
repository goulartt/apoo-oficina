package application;

import java.time.LocalDate;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jfxtras.scene.control.agenda.Agenda;

public class AgendaTeste extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
	    Agenda agenda = new Agenda();

	    // add an appointment
	    agenda.appointments().addAll(
	        new Agenda.AppointmentImplLocal()
	            .withStartLocalDateTime(LocalDate.now().atTime(4, 00))
	            .withEndLocalDateTime(LocalDate.now().atTime(15, 30))
	            .withDescription("It's time")
	            .withAppointmentGroup(new Agenda.AppointmentGroupImpl().withStyleClass("group1")) // you should use a map of AppointmentGroups
	    );

	   // show it
	   primaryStage.setScene(new Scene(agenda, 800, 600));
	   primaryStage.show();
	}
	
	public static void main(String[] args)
	{
		launch(args);
	}
	
	  
}
