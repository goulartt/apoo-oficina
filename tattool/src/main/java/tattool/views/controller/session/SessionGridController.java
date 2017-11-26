package tattool.views.controller.session;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class SessionGridController {
	
	@FXML
    private JFXTreeTableView<Session> sessionGrid;

    @FXML
    private OctIconView closeButton;
    
    @FXML
    SessionController sessionController;
	
    public SessionGridController(SessionController sessionController){
		this.sessionController = sessionController;
	}
	public void initialize() {
		closeButton.setOnMouseClicked(event -> {
			sessionController.sessionModal.close();
		});
		createTableColumns();
		populateTable();
	}
	
	/*
	 * 	##	CRIA UMA NOVA SESSÃO
	 */
	
    @FXML
    void create(ActionEvent event) {
    	
    	// SE VIRA GOULART
    	
    }
    
    /*
     * 	##	APAGA A SESSÃO
     */

    @FXML
    void delete(ActionEvent event) {
    	if(sessionGrid.getSelectionModel().getSelectedItem() != null) {
    		loadDialogDelete((StackPane) ((Node) event.getSource()).getScene().lookup("#mainStack"));
    	} else {
    		loadDialog((StackPane) ((Node) event.getSource()).getScene().lookup("#mainStack"), new Text("Selecione uma sessão para apaga-la."));
    	}
    }
    
    /*
     * 	##	SALVA ALTERAÇÕES
     */

    @FXML
    void update(ActionEvent event) {
    	loadDialog((StackPane) ((Node) event.getSource()).getScene().lookup("#mainStack"), new Text("As alterações foram salvas!"));
    }
	
	@SuppressWarnings("unchecked")
	void createTableColumns()
    {
		JFXTreeTableColumn<Session,String> price    = new JFXTreeTableColumn<>("Preço");
    	JFXTreeTableColumn<Session,String> date     = new JFXTreeTableColumn<>("Data");
    	JFXTreeTableColumn<Session,String> duration = new JFXTreeTableColumn<>("Duração");
    	JFXTreeTableColumn<Session,String> status   = new JFXTreeTableColumn<>("Status");
    	
    	//Colunas com largura responsiva
    	
    	price.prefWidthProperty().bind(sessionGrid.widthProperty().multiply(0.3));
    	date.prefWidthProperty().bind(sessionGrid.widthProperty().multiply(0.2));
    	duration.prefWidthProperty().bind(sessionGrid.widthProperty().multiply(0.2));
    	status.prefWidthProperty().bind(sessionGrid.widthProperty().multiply(0.3));
    	
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
    	
    	sessionGrid.getColumns().setAll(price, date, duration, status);
    }
    
    /*
     * 	##	POPULA A TABLE
     */
    
    void populateTable()
    {
    	ObservableList<Session> sessions = FXCollections.observableArrayList();
    	
    	sessions.add(new Session("R$400.00", "17/12/2017", "120min", "Pago"));
    	sessions.add(new Session("R$400.00", "19/12/2017", "120min", "Pendente"));
    	sessions.add(new Session("R$400.00", "21/12/2017", "120min", "Pendente"));
    	sessions.add(new Session("R$400.00", "23/12/2017", "120min", "Pendente"));
  
    	final TreeItem<Session> root = new RecursiveTreeItem<Session>(sessions, RecursiveTreeObject::getChildren);
    	
    	sessionGrid.setRoot(root);
    	sessionGrid.setShowRoot(false);
    }
    
    /*
     * 	##	DIALOG DELETE
     */
    
    void loadDialogDelete(StackPane mainStack) {
    	
    	JFXDialogLayout dialogContent = new JFXDialogLayout();
    	JFXDialog dialog              = new JFXDialog(mainStack, dialogContent, JFXDialog.DialogTransition.CENTER);
    	JFXButton yes                 = new JFXButton("Sim");
    	JFXButton no                  = new JFXButton("Não");
    	
    	dialogContent.setHeading(new Text("Tem certeza que quer excluir este usuário?"));
    	dialogContent.setBody(new Text("Todos os dados deste usuário serão perdidos."));
    	
    	yes.setCursor(Cursor.HAND);
    	no.setCursor(Cursor.HAND);
    	
		yes.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//REST.DELETE
			}
		});
		no.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				dialog.close();
			}
		});
		
		dialogContent.setActions(no, yes);
		dialog.setOverlayClose(false);
		dialog.show();
    }
    
    /*
     * 	##	DIALOG
     */
    
    void loadDialog(StackPane mainStack, Text text) {
    	
    	JFXDialogLayout dialogContent = new JFXDialogLayout();
    	JFXDialog dialog              = new JFXDialog(mainStack, dialogContent, JFXDialog.DialogTransition.CENTER);
    	JFXButton ok                 = new JFXButton("Ok");
    	
    	dialogContent.setHeading(text);
    	
    	ok.setCursor(Cursor.HAND);
    	
		ok.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
	    		dialog.close();
			}
		});
		
		dialogContent.setActions(ok);
		dialog.show();
    }
    
    /*
     * 	##	CLASSE TESTE
     */
    
    class Session extends RecursiveTreeObject<Session> {
    	StringProperty price;
    	StringProperty date;
    	StringProperty duration;
    	StringProperty status;
    	
    	Session(String price, String date, String duration, String status) {
    		this.price    = new SimpleStringProperty(price);
    		this.date     = new SimpleStringProperty(date);
    		this.duration = new SimpleStringProperty(duration);
    		this.status   = new SimpleStringProperty(status);
    	}
    }
}
