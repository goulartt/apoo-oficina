package tattool.views.controller.art;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;

import de.jensd.fx.glyphs.octicons.OctIconView;
import javafx.animation.PauseTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import tattool.domain.model.Art;
import tattool.domain.model.Cep;
import tattool.domain.model.Tag;
import tattool.rest.consume.ArtRest;
import tattool.service.ArtService;

public class GalleryController implements Initializable {

	@FXML
	private ScrollPane scrollPane;

	@FXML
	private JFXTextField search;

	@FXML
	private VBox column1;

	@FXML
	private VBox column2;

	@FXML
	private VBox column3;

	@FXML
	private VBox column4;

	private ArtRest rest = new ArtRest();
	private ArtService service = new ArtService();
	private Art[] array;
	private List<Art> artes = new ArrayList<>();
	private List<Art> todasArts = new ArrayList<>();
	private List<Art> artesExcluidas = new ArrayList<>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		array = rest.findAll();
		if(array != null) {
			artes.addAll(Arrays.asList(array));
			todasArts.addAll(artes);
		}
		
		loadGallery();
		PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
		search.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				pause.setOnFinished(event -> {
					if (newValue.length() >= 3) {
						for (Art a : artes) {
							boolean contem = false;
							for (Tag tag : a.getTags()) {
								if (tag.getTag().contains(newValue))
									contem = true;
							}
							if (!contem) {
								artesExcluidas.add(a);
							}

						}
						for (Art a : artesExcluidas) {
							artes.remove(a);
						}
						artesExcluidas = new ArrayList<>();
						resetColumns();
						loadGallery();
					} else {
						if (todasArts.size() != artes.size()) {
							artes.clear();
							artes.addAll(todasArts);
							resetColumns();
							loadGallery();
						}

					}
				});
				pause.playFromStart();

			}
		});
	}

	/*
	 * ## CRIAR ARTE
	 */

	private void resetColumns() {
		column1.getChildren().clear();
		column2.getChildren().clear();
		column3.getChildren().clear();
		column4.getChildren().clear();
	}

	@FXML
	void create(ActionEvent event) {
		try {
			FXMLLoader viewLoader = new FXMLLoader(getClass().getResource("/views/gallery/create.fxml"));
			BorderPane main = (BorderPane) ((Node) event.getSource()).getScene().lookup("#main");

			viewLoader.setRoot(main);
			main.getChildren().clear();
			viewLoader.load();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * ## VISUALIZAR ARTE
	 */

	void show(Art a, Image img, StackPane mainStack) {
		try {
			FXMLLoader artLoader = new FXMLLoader(getClass().getResource("/views/gallery/show-art.fxml"));
			artLoader.setController(new ShowArtController(a));
			Region artContent = artLoader.load();
			JFXDialog customerModal = new JFXDialog(mainStack, artContent, JFXDialog.DialogTransition.CENTER, false);
			ShowArtController control = (ShowArtController) artLoader.getController();
			control.dialogShow = customerModal;

			ImageView image = (ImageView) mainStack.getScene().lookup("#image");
			StackPane imageStack = (StackPane) mainStack.getScene().lookup("#imageStack");
			image.setImage(img);
			image.setPreserveRatio(true);

			OctIconView closeButton = (OctIconView) mainStack.getScene().lookup("#closeButton");
			closeButton.setOnMouseClicked(event -> customerModal.close());

			artContent.prefHeightProperty().bind(mainStack.heightProperty().subtract(60));
			artContent.prefWidthProperty().bind(mainStack.widthProperty().subtract(80));
			image.fitHeightProperty().bind(imageStack.heightProperty().subtract(50));
			image.fitWidthProperty().bind(imageStack.widthProperty());

			customerModal.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * ## GALERIA !! RESPEITA AS GAMBIARRA (JAVA) !!
	 */

	void loadGallery() {

		// Colunas responsivas

		column1.prefWidthProperty().bind(scrollPane.widthProperty().divide(4).subtract(3.5));
		column2.prefWidthProperty().bind(scrollPane.widthProperty().divide(4).subtract(3.5));
		column3.prefWidthProperty().bind(scrollPane.widthProperty().divide(4).subtract(3.5));
		column4.prefWidthProperty().bind(scrollPane.widthProperty().divide(4).subtract(3.5));

		loadImages();
	}

	void loadImages() {

		// Contador da coluna atual
		int column = 1;

		for (Art a : artes) {
			Image img = SwingFXUtils.toFXImage(service.createImageFromBytes(a.getImage()), null);
			switch (column) {
			case 1:
				column1.getChildren().add(new GalleryImage(a, img, column1));
				column++;
				break;
			case 2:
				column2.getChildren().add(new GalleryImage(a, img, column2));
				column++;
				break;
			case 3:
				column3.getChildren().add(new GalleryImage(a, img, column3));
				column++;
				break;
			case 4:
				column4.getChildren().add(new GalleryImage(a, img, column4));
				column = 1;
				break;
			default:
				break;

			}
		}
		;
	}

	/*
	 * ## IMAGEM DA GALERIA
	 */

	private class GalleryImage extends ImageView {

		GalleryImage(Art a, Image img, VBox column) {

			setImage(img);

			fitWidthProperty().bind(column.prefWidthProperty());
			getStyleClass().add("gallery-image");
			setPreserveRatio(true);

			setOnMouseEntered(event -> {
				setEffect(new ColorAdjust(0, 0, -0.5, 0));
			});
			setOnMouseExited(event -> {
				setEffect(null);
			});

			setOnMouseClicked(event -> {
				show(a, img, (StackPane) scrollPane.getScene().lookup("#mainStack"));
			});
		}
	}
}
