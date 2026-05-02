package application;

import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EachAvlMovies extends VBox {
	static TableView<Movies> table2 = new TableView<Movies>();
	static AVLTree<Movies> tree;
	static int currIndex = 0;
	Label statesticsL;
	Label ratingLabel;

	public EachAvlMovies(Stage primaryStage) {
		tableDisplay();
		Label s = new Label(" Movies Management System ");
		s.setStyle("-fx-font-size: 45px; -fx-font-weight: bold; -fx-text-fill: #2a2a2a; -fx-font-family: 'Arial';");

		statesticsL = new Label("current index = " + currIndex + "  &&  height Avl Tree = " + tree.height());
		statesticsL.setStyle(
				"-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2a2a2a; -fx-font-family: 'Arial';");

		ratingLabel = new Label();
		ratingLabel.setStyle(
				"-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2a2a2a; -fx-font-family: 'Arial';");

		MyButton next = new MyButton();
		next.setIcon("next.png", 60, 100);

		MyButton prev = new MyButton();
		prev.setIcon("prev.png", 60, 100);

		HBox hoho = new HBox(30, prev, table2, next);
		hoho.setAlignment(Pos.CENTER);

		VBox vbox = new VBox(30, s, hoho, statesticsL, ratingLabel);
		vbox.setAlignment(Pos.CENTER);

		this.getChildren().addAll(vbox);
		this.setAlignment(Pos.CENTER);
		this.setStyle("-fx-background-color: linear-gradient(to bottom right, #DDE6ED, #9DB2BF);" + "-fx-padding: 40px;"
				+ "-fx-border-radius: 20px;" + "-fx-background-radius: 20px;"
				+ "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 10, 0, 0, 5);");

		next.setOnAction(e -> {
			if (currIndex < Movies_Interface.catalog.getSize()-1) {
				// tree.clear();
				currIndex++;
				tableDisplay();
				table2.refresh();
				statesticsL.setText("current index = " + currIndex + "  &&   height Avl Tree = " + tree.height());
				updateStatistics();
			}
		});

		prev.setOnAction(e -> {
			if (currIndex >= 1) {
				// tree.clear();
				currIndex--;
				tableDisplay();
				table2.refresh();
				statesticsL.setText("current index = " + currIndex + "  &&  height Avl Tree = " + tree.height());
				updateStatistics();

			}
		});
		updateStatistics();
	}

	public void tableDisplay() {
		if (table2.getColumns().isEmpty()) {

			TableColumn<Movies, String> title = new TableColumn<Movies, String>("Title Movies");
			title.setCellValueFactory(new PropertyValueFactory<>("title"));
			TableColumn<Movies, String> description = new TableColumn<Movies, String>("Description");
			description.setCellValueFactory(new PropertyValueFactory<>("description"));
			TableColumn<Movies, Integer> year = new TableColumn<Movies, Integer>("Year   ");
			year.setCellValueFactory(new PropertyValueFactory<>("year"));
			TableColumn<Movies, Double> rating = new TableColumn<Movies, Double>("Rating     ");
			rating.setCellValueFactory(new PropertyValueFactory<>("rating"));
			table2.getColumns().addAll(title, description, year, rating);

			table2.setStyle("  -fx-background-color: white;\r\n" + "    -fx-background-radius: 10;\r\n"
					+ "    -fx-border-radius: 10;\r\n" + "    -fx-border-color: #E5E7EB;\r\n"
					+ "    -fx-border-width: 1;");
			table2.autosize();
			table2.setMinHeight(450);
			table2.setMinWidth(1100);
			table2.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		}

		tree = Movies_Interface.catalog.getAvl(currIndex);
		System.out.println("Size of tree in index " + currIndex + ": " + tree.toObservableList().size());
		for (Movies m : tree) {
			System.out.println(m.getTitle());
		}
		table2.setItems(tree.toObservableList());

	}

	public ArrayList<String> maxRating() {
		double max = 0;
		ArrayList<String> maxRating = new ArrayList<String>();
		for (Movies movies : tree) {
			if (movies.getRating() > max)
				max = movies.getRating();
		}
		for (Movies movies : tree) {
			if (movies.getRating() == max)
				maxRating.add("Maximum : " + movies.getTitle() + " = " + max + "   &&");
		}
		return maxRating;
	}

	public ArrayList<String> minRating() {
		double min = 1000;
		ArrayList<String> minRating = new ArrayList<String>();
		for (Movies movies : tree) {
			if (movies.getRating() < min)
				min = movies.getRating();
		}
		for (Movies movies : tree) {
			if (movies.getRating() == min)
				minRating.add("Minimum : " + movies.getTitle() + " = " + min);
		}
		return minRating;
	}

	private void updateStatistics() {
		// statesticsL.setText("current index = " + currIndex + " && height Avl Tree = "
		// + tree.height());

		StringBuilder sb = new StringBuilder();

		for (String max : maxRating()) {
			sb.append(max).append("     ");
		}
		for (String min : minRating()) {
			sb.append(min).append("     ");
		}

		ratingLabel.setText(sb.toString());
	}

}
