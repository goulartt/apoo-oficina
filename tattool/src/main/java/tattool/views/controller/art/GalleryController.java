package tattool.views.controller.art;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDialog;

import de.jensd.fx.glyphs.octicons.OctIconView;
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
import tattool.domain.model.Art;
import tattool.rest.consume.ArtRest;
import tattool.service.ArtService;

public class GalleryController implements Initializable{
	
//	@FXML TilePane tilePane = new TilePane();
//	@FXML ScrollPane scrollPane = new ScrollPane();
//	@FXML Label lblErro = new Label();
//	@FXML TextField txtTag = new TextField();
//	private ArtRest rest = new ArtRest();
//	private ArtService service = new ArtService();
//	public Art[] array;
//	@Override
//	public void initialize(URL location, ResourceBundle resources) {
//		tilePane.setPadding(new Insets(15, 15, 15, 15));
//		tilePane.setHgap(15);
//		carregaArte();
//		scrollPane.setFitToWidth(true);
//		scrollPane.setContent(tilePane);
//		
//	}
//
//	public void carregaArte() {
//		array = rest.findAll();
//		
//		List<Art> artes = Arrays.asList(array);
//
//		for (final Art art : artes) {
//			ImageView imageView;
//			imageView = createImageView(art);
//			tilePane.getChildren().addAll(imageView);
//			
//		}
//	}
//	
//	private ImageView createImageView(Art art) {
//		// DEFAULT_THUMBNAIL_WIDTH is a constant you need to define
//		// The last two arguments are: preserveRatio, and use smooth (slower)
//		// resizing
//
//		ImageView imageView = null;
//		final Image img = SwingFXUtils.toFXImage(service.createImageFromBytes(art.getImage()), null);
//		imageView = new ImageView(img);
//		imageView.setFitHeight(150);
//		imageView.setFitWidth(150);
//		imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
//
//			@Override
//			public void handle(MouseEvent mouseEvent) {
//
//				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
//
//					if (mouseEvent.getClickCount() == 1) {
//						try {
//							FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/gallery/view.fxml"));
//							Pane root = (Pane) loader.load();
//							Scene scene = new Scene(root);
//							Stage primaryStage = new Stage();
//							primaryStage.setScene(scene);
//
//							ArtViewController control = (ArtViewController)loader.getController();
//							control.art = art;
//							control.image.setImage(img);
//							control.txtDescricao.setText(art.getDescription());
//							control.tagColumn.setCellValueFactory(new PropertyValueFactory<Tag, String>("tag"));
//							control.observableTag = FXCollections.observableArrayList(art.getTags());
//							control.tableTag.setItems(control.observableTag);
//							primaryStage.setTitle(art.getDescription());
//							primaryStage.show();
//						} catch (FileNotFoundException e) {
//							e.printStackTrace();
//						} catch (IOException e) {
//							e.printStackTrace();
//						}
//
//					}
//				}
//			}
//		});
//		return imageView;
//	}
//	
//	@FXML
//	public void buscarImagem(ActionEvent event) {
//		
//	}
	
	@FXML
	private ScrollPane scrollPane;
    
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

    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	
    	array = rest.findAll();
    	artes = Arrays.asList(array);
    	loadGallery();
	}
    
    /*
     * 	##	CRIAR ARTE
     */
	
    @FXML
    void create(ActionEvent event) {
    	try {
    		FXMLLoader viewLoader = new FXMLLoader(getClass().getResource("/views/gallery/create.fxml"));
    		BorderPane main       = (BorderPane) ((Node) event.getSource()).getScene().lookup("#main");
    		
    		viewLoader.setRoot(main);
    		main.getChildren().clear();
    		viewLoader.load();
    	}catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
    
    /*
     * 	##	VISUALIZAR ARTE
     */
    
    void show(Image img, StackPane mainStack) {
    	try {
			FXMLLoader artLoader    = new FXMLLoader(getClass().getResource("/views/gallery/show-art.fxml"));
			Region artContent       = artLoader.load();
			JFXDialog customerModal = new JFXDialog(mainStack, artContent, JFXDialog.DialogTransition.CENTER, false);
			
			ImageView image      = (ImageView) mainStack.getScene().lookup("#image");
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
     * 	##	GALERIA !! RESPEITA AS GAMBIARRA (JAVA) !!
     */
    
    void loadGallery() {
    	
    	//Colunas responsivas
    	
    	column1.prefWidthProperty().bind(scrollPane.widthProperty().divide(4).subtract(3.5));
    	column2.prefWidthProperty().bind(scrollPane.widthProperty().divide(4).subtract(3.5));
    	column3.prefWidthProperty().bind(scrollPane.widthProperty().divide(4).subtract(3.5));
    	column4.prefWidthProperty().bind(scrollPane.widthProperty().divide(4).subtract(3.5));
    	
    	loadImages();
    }
    
    void loadImages() {
    	
    	//Contador da coluna atual
    	int column = 1;
    	

    	for(Art a : artes) {
    		Image img = SwingFXUtils.toFXImage(service.createImageFromBytes(a.getImage()), null);
    		switch(column) {
	    		case 1:
	    			column1.getChildren().add(new GalleryImage(img, column1));
	    			column++;
	    			break;
	    		case 2:
	    			column2.getChildren().add(new GalleryImage(img, column2));
	    			column++;
	    			break;
	    		case 3:
	    			column3.getChildren().add(new GalleryImage(img, column3));
	    			column++;
	    			break;
	    		case 4:
	    			column4.getChildren().add(new GalleryImage(img, column4));
	    			column = 1;
	    			break;
	    		default: 
	    			break;
	    		
	    		}
    	};
    }
    
    /*
     * 	##	IMAGEM DA GALERIA
     */

    private class GalleryImage extends ImageView {
    	
    	GalleryImage(Image img, VBox column) {
    		
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
    			show(img, (StackPane) scrollPane.getScene().lookup("#mainStack"));
    		});
    	}
    }
}
