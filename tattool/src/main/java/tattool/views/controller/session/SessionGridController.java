package tattool.views.controller.session;

import java.util.ArrayList;
import java.util.List;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import de.jensd.fx.glyphs.octicons.OctIconView;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.TreeTableColumn.CellEditEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Callback;
import tattool.domain.model.Session;
import tattool.domain.model.User;
import tattool.domain.modelfx.SessionFX;
import tattool.rest.consume.ServiceRest;
import tattool.rest.consume.SessionRest;
import tattool.util.ConvertModelToFX;

public class SessionGridController {
	
	@FXML
    private JFXTreeTableView<SessionFX> sessionGrid;

    @FXML
    private OctIconView closeButton;
    
    @FXML
    SessionController sessionController;
    
    private List<SessionFX> sessoesNovas = new ArrayList<>();
    
    private SessionRest rest = new SessionRest();
    ObservableList<SessionFX> sessions;
	
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
    	
    	SessionFX session = new SessionFX();
    	session.setService(sessionController.serviceCarregado);
    	sessoesNovas.add(session);
    	sessions.add(session);
    	sessionController.serviceCarregado.setQuantSessions(sessionController.serviceCarregado.getQuantSessions() + 1);
    	
    }
    

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
    	for(SessionFX s : sessoesNovas) {
			Session sessao = ConvertModelToFX.converSessionFXtoSession(s);
			if(sessao.getDateSession() != null && sessao.getPrice() != null)
				sessao.setStatus("AGENDADO");
    		rest.save(sessao);
    	}
		new ServiceRest().save(sessionController.serviceCarregado);
    	loadDialog((StackPane) ((Node) event.getSource()).getScene().lookup("#mainStack"), new Text("As alterações foram salvas!"));
    }
	
	@SuppressWarnings("unchecked")
	void createTableColumns()
    {
		JFXTreeTableColumn<SessionFX,String> price    = new JFXTreeTableColumn<>("Preço");
    	JFXTreeTableColumn<SessionFX,String> date     = new JFXTreeTableColumn<>("Data");
    	JFXTreeTableColumn<SessionFX,String> duration = new JFXTreeTableColumn<>("Duração");
    	JFXTreeTableColumn<SessionFX,String> status   = new JFXTreeTableColumn<>("Status");
    	
    	//Colunas com largura responsiva
    	
    	price.prefWidthProperty().bind(sessionGrid.widthProperty().multiply(0.3));
    	date.prefWidthProperty().bind(sessionGrid.widthProperty().multiply(0.2));
    	duration.prefWidthProperty().bind(sessionGrid.widthProperty().multiply(0.2));
    	status.prefWidthProperty().bind(sessionGrid.widthProperty().multiply(0.3));
    	
    	price.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<SessionFX, String>, ObservableValue<String>>()
    	{
			@Override
			public ObservableValue<String> call(CellDataFeatures<SessionFX, String> param)
			{
				return param.getValue().getValue().price;
			}
    	});
    	date.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<SessionFX, String>, ObservableValue<String>>()
    	{
			@Override
			public ObservableValue<String> call(CellDataFeatures<SessionFX, String> param)
			{
				return param.getValue().getValue().date;
			}
    	});

    	duration.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<SessionFX, String>, ObservableValue<String>>()
    	{
			@Override
			public ObservableValue<String> call(CellDataFeatures<SessionFX, String> param)
			{
				return param.getValue().getValue().duration;
			}
    	});
    	status.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<SessionFX, String>, ObservableValue<String>>()
    	{
			@Override
			public ObservableValue<String> call(CellDataFeatures<SessionFX, String> param)
			{
				return param.getValue().getValue().status;
			}
    	});
    	

    	//	TABLE EDITAVEL
    	
    	price.setCellFactory((TreeTableColumn<SessionFX, String> param) -> new GenericEditableTreeTableCell<>(new TextFieldEditorBuilder()));
    	price.setOnEditCommit(new EventHandler<TreeTableColumn.CellEditEvent<SessionFX, String>>() { 
			@Override
			public void handle(CellEditEvent<SessionFX, String> event) {
				event.getTreeTableView().getTreeItem(event.getTreeTablePosition().getRow()).getValue().price.set(event.getNewValue());
				TreeItem<SessionFX> item = event.getRowValue();
                SessionFX sessionOld = item.getValue();
                String newer = event.getNewValue();
                sessionOld.setPrice(new SimpleStringProperty(newer));
                sessoesNovas.add(sessionOld);
			}
        });
        
    	date.setCellFactory((TreeTableColumn<SessionFX, String> param) -> new GenericEditableTreeTableCell<>(new TextFieldEditorBuilder()));
    	date.setOnEditCommit(new EventHandler<TreeTableColumn.CellEditEvent<SessionFX, String>>() { 
			@Override
			public void handle(CellEditEvent<SessionFX, String> event) {
				event.getTreeTableView().getTreeItem(event.getTreeTablePosition().getRow()).getValue().date.set(event.getNewValue());
				TreeItem<SessionFX> item = event.getRowValue();
                SessionFX sessionOld = item.getValue();
                String newer = event.getNewValue();
                sessionOld.setDate(new SimpleStringProperty(newer));
                sessoesNovas.add(sessionOld);
			}
        });
    	
      	duration.setCellFactory((TreeTableColumn<SessionFX, String> param) -> new GenericEditableTreeTableCell<>(new TextFieldEditorBuilder()));
      	duration.setOnEditCommit(new EventHandler<TreeTableColumn.CellEditEvent<SessionFX, String>>() { 
			@Override
			public void handle(CellEditEvent<SessionFX, String> event) {
				event.getTreeTableView().getTreeItem(event.getTreeTablePosition().getRow()).getValue().duration.set(event.getNewValue());
				TreeItem<SessionFX> item = event.getRowValue();
                SessionFX sessionOld = item.getValue();
                String newer = event.getNewValue();
                sessionOld.setDuration(new SimpleStringProperty(newer));
                sessoesNovas.add(sessionOld);
				
			}
        });
      	sessionGrid.setEditable(true);
    	sessionGrid.getColumns().setAll(price, date, duration, status);
    }
    
    /*
     * 	##	POPULA A TABLE
     */
    
    void populateTable()
    {
    	
    	sessions = FXCollections.observableArrayList(ConvertModelToFX.convertListSessionToSessionFX(rest.findByService(sessionController.serviceCarregado.getId())));
    	
    	final TreeItem<SessionFX> root = new RecursiveTreeItem<SessionFX>(sessions, RecursiveTreeObject::getChildren);
    	
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
				SessionFX s = sessionGrid.getSelectionModel().getSelectedItem().getValue();
	    		rest.deleteSession(s.getId());
	    		sessionController.serviceCarregado.setQuantSessions(sessionController.serviceCarregado.getQuantSessions() - 1);
	    		new ServiceRest().save(sessionController.serviceCarregado);
	    		populateTable();
	    		dialog.close();
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
    
}
