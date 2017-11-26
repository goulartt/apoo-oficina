package tattool.views.controller;

import java.net.URL;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import jfxtras.scene.control.agenda.Agenda;
import jfxtras.scene.control.agenda.Agenda.LocalDateTimeRange;
import tattool.domain.model.Customer;
import tattool.domain.model.Service;
import tattool.views.controller.customer.CreateEditCustomerController;
import tattool.views.controller.service.CreateEditServiceController;

public class HomeController  implements Initializable{
	
	@FXML Label lblData = new Label();
	
	
	@FXML Agenda agenda = new Agenda();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lblData.setText("Hoje é dia " + pegaData() + ".");

	    agenda.appointments().addAll(
	        new Agenda.AppointmentImplLocal()
	            .withStartLocalDateTime(LocalDate.now().atTime(4, 00))
	            .withEndLocalDateTime(LocalDate.now().atTime(15, 30))
	            .withDescription("It's time")
	            .withAppointmentGroup(new Agenda.AppointmentGroupImpl().withStyleClass("group1")) // you should use a map of AppointmentGroups
	    );
	}
	
	@FXML
    void createCustomer(ActionEvent event) {
    	try {
		    FXMLLoader viewLoader = new FXMLLoader(getClass().getResource("/views/customers/create-edit.fxml"));
		    BorderPane main       = (BorderPane) ((Node) event.getSource()).getScene().lookup("#main");
		    viewLoader.setController(new CreateEditCustomerController(new Customer()));
		    viewLoader.setRoot(main);
		    main.getChildren().clear();
		    viewLoader.load();
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void createService(ActionEvent event) {
    	try {
		    FXMLLoader viewLoader = new FXMLLoader(getClass().getResource("/views/services/create-edit.fxml"));
		    BorderPane main       = (BorderPane) ((Node) event.getSource()).getScene().lookup("#main");
		    viewLoader.setController(new CreateEditServiceController(new Service()));
		    viewLoader.setRoot(main);
		    main.getChildren().clear();
		    viewLoader.load();
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
	
	private String pegaData() {
		java.util.Date d = new Date();
		return java.text.DateFormat.getDateInstance(DateFormat.LONG).format(d);
	}

}
