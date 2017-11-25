package tattool.views.controller.service;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import de.jensd.fx.glyphs.octicons.OctIconView;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.util.Callback;

public class ShowServiceController implements Initializable{
	
	@FXML
    private Label serviceName;

    @FXML
    private Label customerName;
    
    @FXML
    private Label serviceStatus;

    @FXML
    private Label numberSessions;

    @FXML
    private Label paidSessions;

    @FXML
    private JFXTreeTableView<Session> sessionsTable;

    @FXML
    private OctIconView closeButton;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		createTableColumns();
    	populateTable();
	}
	
	@SuppressWarnings("unchecked")
	void createTableColumns()
    {
    	JFXTreeTableColumn<Session,String> price    = new JFXTreeTableColumn<>("Preço");
    	JFXTreeTableColumn<Session,String> date     = new JFXTreeTableColumn<>("Data");
    	JFXTreeTableColumn<Session,String> time     = new JFXTreeTableColumn<>("Horário");
    	JFXTreeTableColumn<Session,String> duration = new JFXTreeTableColumn<>("Duração");
    	JFXTreeTableColumn<Session,String> status   = new JFXTreeTableColumn<>("Status");
    	
    	//Colunas com largura responsiva
    	
    	price.prefWidthProperty().bind(sessionsTable.widthProperty().multiply(0.3));
    	date.prefWidthProperty().bind(sessionsTable.widthProperty().multiply(0.2));
    	time.prefWidthProperty().bind(sessionsTable.widthProperty().multiply(0.2));
    	status.prefWidthProperty().bind(sessionsTable.widthProperty().multiply(0.3));
    	
    	price.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Session, String>, ObservableValue<String>>()
    	{
			@Override
			public ObservableValue<String> call(CellDataFeatures<Session, String> param)
			{
				return param.getValue().getValue().price;
			}
    	});
    	date.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Session, String>, ObservableValue<String>>()
    	{
			@Override
			public ObservableValue<String> call(CellDataFeatures<Session, String> param)
			{
				return param.getValue().getValue().date;
			}
    	});
    	time.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Session, String>, ObservableValue<String>>()
    	{
			@Override
			public ObservableValue<String> call(CellDataFeatures<Session, String> param)
			{
				return param.getValue().getValue().time;
			}
    	});
    	duration.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Session, String>, ObservableValue<String>>()
    	{
			@Override
			public ObservableValue<String> call(CellDataFeatures<Session, String> param)
			{
				return param.getValue().getValue().duration;
			}
    	});
    	status.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Session, String>, ObservableValue<String>>()
    	{
			@Override
			public ObservableValue<String> call(CellDataFeatures<Session, String> param)
			{
				return param.getValue().getValue().status;
			}
    	});
    	
    	sessionsTable.getColumns().setAll(price, date, time, duration, status);
    }
    
    /*
     * 	##	POPULA A TABLE
     */
    
    void populateTable()
    {
    	ObservableList<Session> sessions = FXCollections.observableArrayList();
    	
    	sessions.add(new Session("R$400.00", "17/12/2017", "16:00", "120min", "Pago"));
    	sessions.add(new Session("R$400.00", "19/12/2017", "18:00", "120min", "Pago"));
    	sessions.add(new Session("R$400.00", "23/12/2017", "14:00", "120min", "Pendente"));
    	sessions.add(new Session("R$400.00", "28/12/2017", "22:00", "120min", "Pendente"));
  
    	final TreeItem<Session> root = new RecursiveTreeItem<Session>(sessions, RecursiveTreeObject::getChildren);
    	
    	sessionsTable.setRoot(root);
    	sessionsTable.setShowRoot(false);
    }
    
    /*
     * 	##	CLASSE TESTE
     */
	
    class Session extends RecursiveTreeObject<Session> {
    	StringProperty price;
    	StringProperty date;
    	StringProperty time;
    	StringProperty duration;
    	StringProperty status;
    	
    	Session(String price, String date, String time, String duration, String status) {
    		this.price    = new SimpleStringProperty(price);
    		this.date     = new SimpleStringProperty(date);
    		this.time     = new SimpleStringProperty(time);
    		this.duration = new SimpleStringProperty(duration);
    		this.status   = new SimpleStringProperty(status);
    	}
    }
}
