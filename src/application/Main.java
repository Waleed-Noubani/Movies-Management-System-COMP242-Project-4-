package application;

import java.io.File;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			TabPane tab = new TabPane();
			tab.setTabMinWidth(150);
			tab.setTabMinHeight(40);
			tab.setStyle("-fx-background-color: #f4f4f4;");
			tab.setStyle("""
					    -fx-tab-min-height: 33px;
					    -fx-tab-max-height: 33px;
					""");
			Tab tab1 = new Tab("📁 Movies Mangment system");
			Tab tab2 = new Tab("📊 statestics ");
			Tab tab3 = new Tab("📊Close ");

			tab1.setClosable(false);
			tab2.setClosable(false);
			tab3.setClosable(false);

			tab.getTabs().addAll(tab1, tab2, tab3);

			Movies_Interface s = new Movies_Interface(primaryStage);
			tab1.setContent(s);
			EachAvlMovies e = new EachAvlMovies(primaryStage);
			tab2.setContent(e);

			Button clos = new Button("Close");
			clos.setPrefSize(200, 200);
			tab3.setContent(clos);

			Scene scene = new Scene(tab, 400, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setFullScreen(true);

			primaryStage.show();
			clos.setOnAction(ee -> primaryStage.close());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		launch(args);
		Movies m = new Movies("fgfdg", "sdfsdg", 0, 0);
		Movie_Cataloge mc = new Movie_Cataloge();
		mc.add(m);

		HashTable<Integer> table = new HashTable<Integer>(3);

		table.insert(5);
		table.traverse();
		mc.traverse();
	}

}
//--module-path "C:\Users\HP\Downloads\javafx-sdk-23.0.2\lib" --add-modules javafx.controls,javafx.fxml
