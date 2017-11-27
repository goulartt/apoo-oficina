package tattool.views.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.effects.JFXDepthManager;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import tattool.domain.model.User;
import tattool.rest.consume.UserRest;

public class LoginController implements Initializable {

	@FXML
	private JFXTextField txtUsuario;

	@FXML
	private JFXPasswordField txtSenha;

	@FXML
	private Label error;
	
    @FXML
    private VBox loginPane;
	
	@FXML
	private ImageView background;
	
	@FXML
    private Label tattool;
	
	@FXML
	private MediaView media;
	MediaPlayer mp;
	Media m;
	
	private UserRest rest = new UserRest();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		rest.verificaAdmin();

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				txtUsuario.requestFocus();
			}
		});
		
		JFXDepthManager.setDepth(loginPane, 4);
		
		error.managedProperty().bind(error.visibleProperty());
		error.setVisible(false);
		loadBackground();
	}

	/*
	 * ## BOTAO LOGIN
	 */

	@FXML
	void login(ActionEvent event) {
		login((Stage) ((Node) event.getSource()).getScene().getWindow());
	}

	/*
	 * ## BOTAO ENCERRAR
	 */

	@FXML
	void closeApp(ActionEvent event) {
		closeApp();
	}

	/*
	 * ## TECLAS DE ATALHO
	 */

	@FXML
	void keyPressed(KeyEvent event) {
		switch (event.getCode()) {
		case ENTER:
			login((Stage) ((Node) event.getSource()).getScene().getWindow());
			break;
		case ESCAPE:
			closeApp();
			break;
		default:
			break;
		}
	}

	/*
	 * ## LOGIN
	 */

	void login(Stage primaryStage) {

		error.setVisible(false);

		// Stage view login

		if (!txtUsuario.getText().isEmpty() && !txtSenha.getText().isEmpty()) {

			User user = rest.verificaLogin(txtUsuario.getText(), txtSenha.getText());

			if (user != null) {
				try {

					// New Stage dashboard

					Stage stage = new Stage();
					FXMLLoader templateLoader = new FXMLLoader(getClass().getResource("/views/template/template.fxml"));
					Scene scene = new Scene(new JFXDecorator(stage, templateLoader.load()), 1280, 750);
					FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/views/home.fxml"));
					DashboardController control = (DashboardController) templateLoader.getController();
					if (user.getRole() != 1)
						control.btnUsers.setVisible(false);
					control.nome.setText(user.getNome());
					mainLoader.setRoot(templateLoader.getNamespace().get("main"));
					mainLoader.load();

					scene.getStylesheets().add("/css/application.css");

					stage.setScene(scene);
					primaryStage.hide();
					stage.setMinHeight(750);
					stage.setMinWidth(1280);
					stage.show();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				error.setText("Credenciais Inváldias");
				error.setVisible(true);
				txtUsuario.setText("");
				txtSenha.setText("");
				txtUsuario.requestFocus();
			}
		} else {
			error.setText("Por favor preencha os campos");
			error.setVisible(true);
			txtUsuario.requestFocus();
		}
	}
	
	/*
	 * 	##	CARREGA FUNDO DA VIEW
	 */

	void loadBackground() {
		
		final Font font =Font.loadFont(getClass().getResource("/css/Angilla.ttf").toExternalForm(), 140);
		tattool.setFont(font);
		m = new Media(getClass().getClassLoader().getResource("video/video.mp4").toExternalForm());
		mp = new MediaPlayer(m);
		mp.setAutoPlay(true);
		mp.setCycleCount(MediaPlayer.INDEFINITE);
		media.setMediaPlayer(mp);
		media.fitWidthProperty().bind(Bindings.selectDouble(media.sceneProperty(), "width"));
		media.fitHeightProperty().bind(Bindings.selectDouble(media.sceneProperty(), "height"));
		background.fitWidthProperty().bind(Bindings.selectDouble(background.sceneProperty(), "width"));
		background.fitHeightProperty().bind(Bindings.selectDouble(background.sceneProperty(), "height"));
	}
	
	void closeApp() {
		Platform.exit();
	}
}
