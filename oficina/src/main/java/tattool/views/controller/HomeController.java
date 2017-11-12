package tattool.views.controller;

import java.net.URL;
import java.text.DateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class HomeController  implements Initializable{
	
	@FXML Label lblData = new Label();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lblData.setText(pegaData());
		
	}
	
	private String pegaData() {
		java.util.Date d = new Date();
		return java.text.DateFormat.getDateInstance(DateFormat.LONG).format(d);
	}


}
