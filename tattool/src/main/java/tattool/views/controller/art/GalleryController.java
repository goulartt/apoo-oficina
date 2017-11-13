package tattool.views.controller.art;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import tattool.domain.model.Art;
import tattool.domain.model.Tag;
import tattool.rest.consume.ArtRest;
import tattool.service.ArtService;

public class GalleryController implements Initializable{
	
	@FXML TilePane tilePane = new TilePane();
	@FXML ScrollPane scrollPane = new ScrollPane();
	@FXML Label lblErro = new Label();
	@FXML TextField txtTag = new TextField();
	private ArtRest rest = new ArtRest();
	private ArtService service = new ArtService();
	public Art[] array;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tilePane.setPadding(new Insets(15, 15, 15, 15));
		tilePane.setHgap(15);
		carregaArte();
		scrollPane.setFitToWidth(true);
		scrollPane.setContent(tilePane);
		
	}

	public void carregaArte() {
		array = rest.findAll();
		
		List<Art> artes = Arrays.asList(array);

		for (final Art art : artes) {
			ImageView imageView;
			imageView = createImageView(art);
			tilePane.getChildren().addAll(imageView);
			
		}
	}
	
	private ImageView createImageView(Art art) {
		// DEFAULT_THUMBNAIL_WIDTH is a constant you need to define
		// The last two arguments are: preserveRatio, and use smooth (slower)
		// resizing

		ImageView imageView = null;
		final Image img = SwingFXUtils.toFXImage(service.createImageFromBytes(art.getImage()), null);
		imageView = new ImageView(img);
		imageView.setFitHeight(150);
		imageView.setFitWidth(150);
		imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent mouseEvent) {

				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {

					if (mouseEvent.getClickCount() == 1) {
						try {
							FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/gallery/view.fxml"));
							Pane root = (Pane) loader.load();
							Scene scene = new Scene(root);
							Stage primaryStage = new Stage();
							primaryStage.setScene(scene);

							ArtViewController control = (ArtViewController)loader.getController();
							control.art = art;
							control.image.setImage(img);
							control.txtDescricao.setText(art.getDescription());
							control.tagColumn.setCellValueFactory(new PropertyValueFactory<Tag, String>("tag"));
							control.observableTag = FXCollections.observableArrayList(art.getTags());
							control.tableTag.setItems(control.observableTag);
							primaryStage.setTitle(art.getDescription());
							primaryStage.show();
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}

					}
				}
			}
		});
		return imageView;
	}
	
	@FXML
	public void buscarImagem(ActionEvent event) {
		
	}

}
