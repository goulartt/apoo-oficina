package tattool.views.controller.cashier;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableRow;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.sun.javafx.scene.control.skin.VirtualFlow;

import de.jensd.fx.glyphs.octicons.OctIconView;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Callback;
import tattool.domain.model.Session;
import tattool.domain.modelfx.SessionCashierFX;
import tattool.rest.consume.SessionRest;
import tattool.util.ConvertModelToFX;
import tattool.util.MaskFieldUtil;
import tattool.views.controller.service.CustomerGridController;

public class CashierController implements Initializable {

	JFXPopup popup = new JFXPopup();

	JFXButton setPaid = new JFXButton("Pago");

	JFXButton setValor = new JFXButton("Informar valor pago");

	JFXButton setCheck = new JFXButton("Acertado");

	JFXButton unsetPaid = new JFXButton("Remover pagamento");

	JFXButton unsetCheck = new JFXButton("Remover acerto");

	VBox popupBox = new VBox();

	@FXML
	private BorderPane cashierPanel;

	@FXML
	private JFXTextArea obs;

	@FXML
	private JFXTextField search;

	@FXML
	private JFXButton createNoteButton;

	@FXML
	private JFXButton updateNoteButton;

	@FXML
	private JFXButton deleteNoteButton;

	@FXML
	private Label noteLabel;

	@FXML
	private Label sessions;

	@FXML
	private Label paid;

	@FXML
	private Label total;

	@FXML
	private Label balance;
	
	@FXML
	private JFXComboBox<Label> filterStatus;

	@FXML
	private JFXTreeTableView<SessionCashierFX> cashierTable;

	private SessionRest rest = new SessionRest();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		cashierPanel.prefWidthProperty()
				.bind(Bindings.selectDouble(cashierPanel.parentProperty(), "width").multiply(0.3));

		createNoteButton.managedProperty().bind(createNoteButton.visibleProperty());
		updateNoteButton.managedProperty().bind(updateNoteButton.visibleProperty());
		deleteNoteButton.managedProperty().bind(deleteNoteButton.visibleProperty());
		noteLabel.managedProperty().bind(noteLabel.visibleProperty());
		obs.managedProperty().bind(obs.visibleProperty());

		escondeNota();

		createTableColumns();
		populateTable();
		search();
		popup();
		
		filterStatus.getItems().add(new Label("PENDENTE"));
		filterStatus.getItems().add(new Label("AGENDADO"));
		filterStatus.getItems().add(new Label("PAGO"));
		filterStatus.getItems().add(new Label("PARCIALMENTE PAGO"));
		filterStatus.getItems().add(new Label("ACERTADO"));
		filterStatus.getItems().add(new Label("CANCELADO"));
		 
		filterStatus.setPromptText("Status");
	}

	public void escondeNota() {
		createNoteButton.setVisible(false);
		updateNoteButton.setVisible(false);
		deleteNoteButton.setVisible(false);
		obs.setVisible(false);
		paid.setText("");
		total.setText("");
		balance.setText("");
	}

	/*
	 * ## CRIA UMA NOVA NOTA PARA A SESSÃO
	 */

	@FXML
	void createNote(ActionEvent event) {
		noteLabel.setVisible(false);
		createNoteButton.setVisible(false);
		obs.setText("");
		obs.setVisible(true);
		obs.requestFocus();
		updateNoteButton.setVisible(true);
		deleteNoteButton.setVisible(true);

	}

	/*
	 * ## APAGA A NOTA DA SESSÃO
	 */

	@FXML
	void deleteNote(ActionEvent event) {
		SessionCashierFX cashierSession = cashierTable.getSelectionModel().getSelectedItem().getValue();
		Session s = ConvertModelToFX.convertSessionCashierFXToSession(cashierSession);
		s.setObs("");
		rest.save(s);
		populateTable();
		escondeNota();
	}

	/*
	 * ## ALTERA A NOTA DA SESSÃO
	 */

	@FXML
	void updateNote(ActionEvent event) {
		SessionCashierFX cashierSession = cashierTable.getSelectionModel().getSelectedItem().getValue();
		Session s = ConvertModelToFX.convertSessionCashierFXToSession(cashierSession);
		s.setObs(obs.getText());
		rest.save(s);
		populateTable();
		escondeNota();
	}

	/*
	 * ## CRIA COLUNAS DA TABLE
	 */

	@SuppressWarnings("unchecked")
	void createTableColumns() {
		JFXTreeTableColumn<SessionCashierFX, String> date = new JFXTreeTableColumn<>("Data");
		JFXTreeTableColumn<SessionCashierFX, String> service = new JFXTreeTableColumn<>("Serviço");
		JFXTreeTableColumn<SessionCashierFX, String> price = new JFXTreeTableColumn<>("Preço");
		JFXTreeTableColumn<SessionCashierFX, String> paid = new JFXTreeTableColumn<>("Pago");

		// Colunas com largura responsiva

		date.prefWidthProperty().bind(cashierTable.widthProperty().multiply(0.2));
		service.prefWidthProperty().bind(cashierTable.widthProperty().multiply(0.4));
		price.prefWidthProperty().bind(cashierTable.widthProperty().multiply(0.2));
		paid.prefWidthProperty().bind(cashierTable.widthProperty().multiply(0.2));

		date.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<SessionCashierFX, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<SessionCashierFX, String> param) {
						return param.getValue().getValue().date;
					}
				});
		service.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<SessionCashierFX, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<SessionCashierFX, String> param) {
						return param.getValue().getValue().nomeServico;
					}
				});
		price.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<SessionCashierFX, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<SessionCashierFX, String> param) {
						return param.getValue().getValue().preco;
					}
				});
		paid.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<SessionCashierFX, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<SessionCashierFX, String> param) {
						return param.getValue().getValue().pago;
					}
				});

		cashierTable.getColumns().setAll(date, service, price, paid);

		cashierTable.setRowFactory(table -> {
			JFXTreeTableRow<SessionCashierFX> row = new JFXTreeTableRow<>();

			row.setOnMouseClicked(event -> {
				if (event.getButton().equals(MouseButton.PRIMARY)) {
					SessionCashierFX cashierSession = cashierTable.getSelectionModel().getSelectedItem().getValue();

					if (cashierSession.getObs().equals("")) {
						noteLabel.setVisible(false);
						createNoteButton.setVisible(true);
						obs.setText(cashierSession.getObs());
						obs.setVisible(false);
						updateNoteButton.setVisible(false);
						deleteNoteButton.setVisible(false);
					} else {
						noteLabel.setVisible(false);
						createNoteButton.setVisible(false);
						obs.setText(cashierSession.getObs());
						obs.setVisible(true);
						obs.requestFocus();
						updateNoteButton.setVisible(true);
						deleteNoteButton.setVisible(true);
					}
					sessions.setText(cashierSession.getService().getQuantSessions().toString());
					if (cashierSession.getPaid() == null) {
						cashierSession.setPaid(new BigDecimal(0));
					}
					this.paid.setText("R$ " + cashierSession.getPaid().toString());
					
					BigDecimal balanco = cashierSession.getPrice().subtract(cashierSession.getPaid());
					List<Session> findByService = rest.findByService(cashierSession.getService().getId());
					Double valorPago = 0.0;
					Double valorDeve = 0.0;
					for(Session s : findByService) {
						if(s.getPaid() == null)
							s.setPaid(new BigDecimal(0));
						if(s.getPrice() == null) 
							s.setPrice(new BigDecimal(0));
						valorPago += (s.getPaid()).doubleValue();
						valorDeve += (s.getPrice().subtract(s.getPaid())).doubleValue();
					}
					total.setText("R$ " + valorPago.toString());
					balance.setText("R$ " + valorDeve.toString());

				}
				if (event.getButton().equals(MouseButton.SECONDARY)) {
					SessionCashierFX cashierSession = cashierTable.getSelectionModel().getSelectedItem().getValue();

					popupBox.getChildren().clear();

					// setPaid => informa que foi pago o valor da sessão
					// setValor => usuario insere o valor pago
					// setCheck => informar acerto
					// unsetPaid => remover pagamento
					// unsetCheck => remover acerto

					switch (cashierSession.getStatus()) {
					case "PENDENTE":
						popupBox.getChildren().addAll(setPaid, setValor, setCheck);
						break;
					case "AGENDADO":
						popupBox.getChildren().addAll(setPaid, setValor, setCheck);
						break;
					case "PARCIALMENTE PAGO":
						popupBox.getChildren().addAll(setPaid, setValor, unsetPaid);
						break;
					case "PAGO":
						popupBox.getChildren().add(unsetPaid);
						break;
					case "ACERTADO":
						popupBox.getChildren().addAll(unsetCheck);
						break;
					case "CANCELADO":
						//
						break;
					default:
						break;
					}

					popup.show(row, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, event.getX(),
							event.getY());
				}
			});

			return row;
		});
	}

	/*
	 * ## POPULA A TABLE
	 */

	void populateTable() {
		ObservableList<SessionCashierFX> cashierSessions = FXCollections.observableArrayList();
		cashierSessions = FXCollections
				.observableArrayList(ConvertModelToFX.convertSessinToSessionCashierFX(rest.findAll()));

		final TreeItem<SessionCashierFX> root = new RecursiveTreeItem<SessionCashierFX>(cashierSessions,
				RecursiveTreeObject::getChildren);

		cashierTable.setRoot(root);
		cashierTable.setShowRoot(false);
	}

	/*
	 * ## TABLE POP-UP
	 */

	void popup() {

		// Popup Menu Events

		setPaid.setOnMouseClicked(event -> {
			SessionCashierFX cashierSession = cashierTable.getSelectionModel().getSelectedItem().getValue();
			Session s = ConvertModelToFX.convertSessionCashierFXToSession(cashierSession);

			if (s.getDateSession() != null) {
				s.setStatus("PAGO");
				s.setPaid(s.getPrice());
				rest.save(s);
				populateTable();
			} else {
				loadDialog((StackPane) cashierTable.getScene().lookup("#mainStack"), new Text("Por favor, informe a data da sessao antes de setar como paga"));
			}

			popup.hide();
		});

		setValor.setOnMouseClicked(event -> {
			popup.hide();

			loadValorDialog();
		});

		setCheck.setOnMouseClicked(event -> {
			popup.hide();
			SessionCashierFX cashierSession = cashierTable.getSelectionModel().getSelectedItem().getValue();
			Session s = ConvertModelToFX.convertSessionCashierFXToSession(cashierSession);
			s.setStatus("ACERTADO");
			s.setPaid(new BigDecimal(0));
			rest.save(s);
			populateTable();
		
		});

		unsetPaid.setOnMouseClicked(event -> {
			popup.hide();
			SessionCashierFX cashierSession = cashierTable.getSelectionModel().getSelectedItem().getValue();
			Session s = ConvertModelToFX.convertSessionCashierFXToSession(cashierSession);
			s.setPaid(new BigDecimal(0));
			s.setStatus("AGENDADO");
			rest.save(s);
			populateTable();
			// REMOVE PAGAMENTO
		});

		unsetCheck.setOnMouseClicked(event -> {
			popup.hide();
			SessionCashierFX cashierSession = cashierTable.getSelectionModel().getSelectedItem().getValue();
			Session s = ConvertModelToFX.convertSessionCashierFXToSession(cashierSession);
			s.setStatus("AGENDADO");
			s.setPaid(new BigDecimal(0));
			rest.save(s);
			populateTable();
		});

		setPaid.setMaxWidth(Double.MAX_VALUE);
		setCheck.setMaxWidth(Double.MAX_VALUE);

		popupBox.setFillWidth(true);

		popup.setPopupContent(popupBox);
	}

	/*
	 * ## DIALOG INSERIR VALOR PAGO
	 */

	void loadValorDialog() {
		try {
			FXMLLoader modalLoader = new FXMLLoader(getClass().getResource("/views/cashier/set-valor.fxml"));
			Region modalContent = modalLoader.load();
			StackPane mainStack = (StackPane) cashierTable.getScene().lookup("#mainStack");
			JFXDialog modal = new JFXDialog(mainStack, modalContent, JFXDialog.DialogTransition.CENTER, false);

			OctIconView closeButton = (OctIconView) modalContent.getScene().lookup("#closeButton");
			JFXButton confirmButton = (JFXButton) modalContent.getScene().lookup("#confirmButton");

			closeButton.setOnMouseClicked(event -> modal.close());
			
			confirmButton.setOnMouseClicked(event -> {

				JFXTextField paidValor = (JFXTextField) modalContent.getScene().lookup("#paidValor");
				MaskFieldUtil.monetaryField(paidValor);
				SessionCashierFX cashierSession = cashierTable.getSelectionModel().getSelectedItem().getValue();
				Session s = ConvertModelToFX.convertSessionCashierFXToSession(cashierSession);
				BigDecimal valorInformado = new BigDecimal(paidValor.getText());
				
				BigDecimal valorDevendo = new BigDecimal(0);
				if (s.getPaid() != null)
					valorDevendo = s.getPrice().subtract(s.getPaid());
				else
					valorDevendo = s.getPrice();
				if (valorInformado.compareTo(valorDevendo) == 0) {
					s.setPaid(s.getPrice());
					s.setStatus("PAGO");
				} else if (valorInformado.compareTo(valorDevendo) == -1) {
					s.setPaid(valorInformado);
					s.setStatus("PARCIALMENTE PAGO");
				}
				if (valorInformado.compareTo(valorDevendo) == 1) {
					loadDialog(mainStack, new Text("Valor informado maior do que esta devendo"));
				} else {
					rest.save(s);
					populateTable();
					modal.close();
				}
				
			});

			modal.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void loadDialog(StackPane mainStack, Text text) {

		JFXDialogLayout dialogContent = new JFXDialogLayout();
		JFXDialog dialog = new JFXDialog(mainStack, dialogContent, JFXDialog.DialogTransition.CENTER);
		JFXButton ok = new JFXButton("Ok");

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
	 * ## FILTRO DE BUSCA
	 */

	public void search()

	{
		search.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				cashierTable.setPredicate(new Predicate<TreeItem<SessionCashierFX>>() {
					@Override
					public boolean test(TreeItem<SessionCashierFX> cashierSession) {
						// Compara o valor do TextInput com as colunas da table

						return cashierSession.getValue().date.getValue().toLowerCase().contains(newValue.toLowerCase())
								|| cashierSession.getValue().nomeServico.getValue().toLowerCase()
										.contains(newValue.toLowerCase())
								|| cashierSession.getValue().preco.getValue().toLowerCase()
										.contains(newValue.toLowerCase())
								|| cashierSession.getValue().pago.getValue().toLowerCase()
										.contains(newValue.toLowerCase());
					}
				});
			}
		});
	}
	
	@FXML
    void searchFilters(ActionEvent event) {
		//BOTÃO DA LUPA
    }
}