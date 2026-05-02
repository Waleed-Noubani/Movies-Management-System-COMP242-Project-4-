package application;

import java.io.File;
import java.util.Comparator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Movies_Interface extends VBox {
	TableView<Movies> tableView = new TableView<Movies>();
	static Movie_Cataloge catalog = new Movie_Cataloge();
	TextField searchTf;
	RadioButton r1Tit;
	RadioButton r2Year;
	ToggleGroup tg;

	public Movies_Interface(Stage primaryStage) {
		tableDisplay();
		Label s = new Label(" Movies Management System ");
		s.setStyle("-fx-font-size: 45px; -fx-font-weight: bold; -fx-text-fill: #2a2a2a; -fx-font-family: 'Arial';");

		File fileR = new File("dataMovies.txt");
		File fileW = new File("saveData.txt");
		catalog.readFile(fileR);
		tableView.setItems(catalog.toObservableList());

		r1Tit = new RadioButton("By title");
		r2Year = new RadioButton("By year");
		tg = new ToggleGroup();
		r1Tit.setToggleGroup(tg);
		r2Year.setToggleGroup(tg);
		r1Tit.setSelected(true);
		ComboBox<String> comboBox = new ComboBox<>();
		comboBox.setStyle("-fx-background-color: linear-gradient(to right, #74ebd5, #acb6e5);" + "-fx-font-size: 14px;"
				+ "-fx-font-weight: bold;" + "-fx-text-fill: #2c3e50;" + "-fx-border-color: #2980b9;"
				+ "-fx-border-radius: 8;" + "-fx-background-radius: 8;" + "-fx-padding: 5 10 5 10;");
		comboBox.getItems().addAll("Ascending", "Descending");
		comboBox.setOnAction(e -> {
			if (comboBox.getValue().equalsIgnoreCase("Ascending"))
				sortByTitleAcs();
			else
				sortByTitleDesc();
		});

		HBox radioHB = new HBox(10, r1Tit, r2Year, comboBox);

		MyButton saveF = new MyButton();
		saveF.setText("Save in file");
		saveF.setPrefHeight(30);
		saveF.setPrefWidth(170);
		searchTf = new TextField();
		searchTf.setPromptText("Search Movies Title");
		searchTf.setPrefWidth(1000);
		HBox serHbox = new HBox(20, searchTf, radioHB);
		serHbox.setAlignment(Pos.CENTER);
		MyButton addM = new MyButton();
		addM.setText("Add Movie");
		addM.setPrefHeight(30);
		addM.setPrefWidth(120);
		HBox addHb = new HBox(100);
		addHb.setAlignment(Pos.CENTER);
		TextField title = new TextField();
		title.setPromptText("Enter the title");
		title.setPrefHeight(30);
		TextField describtion = new TextField();
		describtion.setPromptText("Enter description");
		describtion.setPrefHeight(30);
		TextField year = new TextField();
		validation(year);
		year.setPromptText("Enter the year");
		year.setPrefHeight(30);
		TextField ratingTf = new TextField();
		validation(ratingTf);
		ratingTf.setPrefHeight(30);
		ratingTf.setPromptText("Enter the Rating");
		addHb.getChildren().addAll(title, describtion, year, ratingTf, addM, saveF);
		VBox vbox = new VBox(30, s, serHbox, tableView, addHb);
		vbox.setAlignment(Pos.CENTER);
		this.getChildren().addAll(vbox);
		this.setAlignment(Pos.CENTER);
		this.setStyle("-fx-background-color: linear-gradient(to bottom right, #DDE6ED, #9DB2BF);" + "-fx-padding: 40px;"
				+ "-fx-border-radius: 20px;" + "-fx-background-radius: 20px;"
				+ "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 10, 0, 0, 5);");

		searchTf.setOnKeyTyped(e -> {
			if (!searchTf.getText().isEmpty())
				filter();
			else
				tableView.setItems(catalog.toObservableList());
		});

		addM.setOnAction(e -> {
			if (!(title.getText().isEmpty() && describtion.getText().isEmpty() && year.getText().isEmpty()
					&& ratingTf.getText().isEmpty())) {
				try {
					String titlee = title.getText();
					String descriptionn = describtion.getText();
					int yearr = Integer.parseInt(year.getText().trim());
					double ratingg = Double.parseDouble(ratingTf.getText().trim());

					Movies m = new Movies(titlee, descriptionn, yearr, ratingg);
					catalog.add(m); // make validation in title ممنوع يتكرر بالاضافة
					catalog.traverse();
					tableView.setItems(catalog.toObservableList());
					tableView.refresh();
					catalog.traverse();

					title.setText("");
					describtion.setText("");
					year.setText("");
					ratingTf.setText("");

					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Success");
					alert.setHeaderText("Movies Added");
					alert.setContentText("Movies added successfully!");
					alert.showAndWait();
				} catch (Exception ex) {
					Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid input");
					alert.show();
				}
			} else {
				Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid input");
				alert.show();
			}
		});

		saveF.setOnAction(e -> {
			catalog.saveInFile(fileW);
		});
	}

	public void filter() {
		if (searchTf != null && searchTf.getText() != null) {
			String key = searchTf.getText().trim().toLowerCase();
			ObservableList<Movies> filtered = FXCollections.observableArrayList();

			if (tg.getSelectedToggle() == r1Tit) {
				for (Movies movie : catalog) {
					if (movie.getTitle().toLowerCase().startsWith(key)) {
						filtered.add(movie);
					}
				}
			} else {
				for (Movies movie : catalog) {
					if (String.valueOf(movie.getYear()).startsWith(key)) {
						filtered.add(movie);
					}
				}
			}

			tableView.setItems(filtered);
		}
	}

	public void tableDisplay() {
		TableColumn<Movies, String> title = new TableColumn<Movies, String>("Title Movies");
		title.setCellValueFactory(new PropertyValueFactory<>("title"));
		TableColumn<Movies, String> description = new TableColumn<Movies, String>("Description");
		description.setCellValueFactory(new PropertyValueFactory<>("description"));
		TableColumn<Movies, Integer> year = new TableColumn<Movies, Integer>("Year   ");
		year.setCellValueFactory(new PropertyValueFactory<>("year"));
		TableColumn<Movies, Double> rating = new TableColumn<Movies, Double>("Rating     ");
		rating.setCellValueFactory(new PropertyValueFactory<>("rating"));

		tableView.setStyle("  -fx-background-color: white;\r\n" + "    -fx-background-radius: 10;\r\n"
				+ "    -fx-border-radius: 10;\r\n" + "    -fx-border-color: #E5E7EB;\r\n" + "    -fx-border-width: 1;");
		tableView.autosize();
		tableView.setMinHeight(400);
		tableView.getColumns().addAll(title, description, year, rating);
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		tableView.setRowFactory(tv -> {
			TableRow<Movies> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (!row.isEmpty() && event.getClickCount() == 2) {
					Movies selectedMovie = row.getItem();

					// نافذة خيارات التحديث أو الحذف
					Stage optionStage = new Stage();
					VBox vbox = new VBox(20);
					vbox.setAlignment(Pos.CENTER);
					vbox.setStyle("-fx-padding: 20; -fx-background-color: #f0f0f0;");

					Label label = new Label("Choose an action for the movie:\n" + selectedMovie.getTitle());
					MyButton updateBtn = new MyButton();
					updateBtn.setText("Update");
					MyButton deleteBtn = new MyButton();
					deleteBtn.setText("Delete");
					updateBtn.setOnAction(e -> {
						optionStage.close();
						actionUpdate(selectedMovie);
					});

					deleteBtn.setOnAction(e -> {
						optionStage.close();
						actionDelete(selectedMovie);
						tableView.setItems(catalog.toObservableList());
						tableView.refresh();
					});

					vbox.getChildren().addAll(label, updateBtn, deleteBtn);
					Scene scene = new Scene(vbox, 300, 180);
					optionStage.setScene(scene);
					optionStage.setTitle("Choose Action");
					optionStage.show();
				}
			});
			return row;
		});

	}

	public void actionUpdate(Movies movie) {
		Stage stageUpdate = new Stage();
		GridPane grid = new GridPane();
		grid.setVgap(15);
		grid.setHgap(15);

		TextField title = new TextField(movie.getTitle());
		title.setEditable(false);
		title.setStyle("-fx-text-fill: lightgray;");
		TextField description = new TextField(movie.getDescription());
		TextField year = new TextField(String.valueOf(movie.getYear()));
		TextField rating = new TextField(String.valueOf(movie.getRating()));
		MyButton updateMovies = new MyButton();
		updateMovies.setText("Add");

		grid.add(new Label("Movie Title:"), 0, 0);
		grid.add(title, 1, 0);
		grid.add(new Label("Description:"), 0, 1);
		grid.add(description, 1, 1);
		grid.add(new Label("Year:"), 0, 2);
		grid.add(year, 1, 2);
		grid.add(new Label("Rating:"), 0, 3);
		grid.add(rating, 1, 3);
		grid.add(updateMovies, 1, 4);

		Scene scene = new Scene(grid, 400, 300);
		stageUpdate.setScene(scene);

		updateMovies.setOnAction(e -> {
			if (!(title.getText().isEmpty() && description.getText().isEmpty() && year.getText().isEmpty()
					&& rating.getText().isEmpty())) {
				try {
					catalog.delete(movie);
					String titlee = title.getText();
					String descriptionn = description.getText();
					int yearr = Integer.parseInt(year.getText().trim());
					double ratingg = Double.parseDouble(rating.getText().trim());

					Movies m = new Movies(titlee, descriptionn, yearr, ratingg);
					catalog.add(m);
					catalog.traverse();
					tableView.setItems(catalog.toObservableList());
					tableView.refresh();
					catalog.traverse();

					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("Success");
					alert.setHeaderText("Movie Updated");
					alert.setContentText("Movie updated successfully!");
					alert.showAndWait();
					stageUpdate.close();
				} catch (Exception ex) {
					Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid input!");
					alert.show();
				}
			} else {
				Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid input!");
				alert.show();
			}
		});

		stageUpdate.setTitle("Update Movie");
		stageUpdate.show();
	}

	public void actionDelete(Movies movie) {
		catalog.delete(movie);
		catalog.traverse();
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Success");
		alert.setHeaderText("Movie Deleted");
		alert.setContentText("Movie deleted successfully!");
		alert.showAndWait();
	}

	public void sortByTitleAcs() {
		ObservableList<Movies> tempList = FXCollections.observableArrayList();

		for (Movies movie : catalog) {
			tempList.add(movie);

		}

		tempList.sort(new Comparator<Movies>() {

			@Override
			public int compare(Movies o1, Movies o2) {
				return o1.getTitle().compareTo(o2.getTitle());
			}
		});

		tableView.setItems(tempList);
	}

	public void sortByTitleDesc() {
		ObservableList<Movies> tempList = FXCollections.observableArrayList();

		for (Movies movie : catalog) {
			tempList.add(movie);

		}

		tempList.sort(new Comparator<Movies>() {

			@Override
			public int compare(Movies o1, Movies o2) {
				return o2.getTitle().compareTo(o1.getTitle());
			}
		});

		tableView.setItems(tempList);
	}

	public void validation(TextField tf) {
		tf.setTextFormatter(new TextFormatter<>(change -> {
			String newText = change.getControlNewText();

			if (newText.matches("\\d*")) { // باث للارقام
				if (newText.isEmpty()) {
					return change;
				}
				try {
					int value = Integer.parseInt(newText);
					if (value > 0) {
						return change;
					}
				} catch (NumberFormatException e) {
					return null;
				}
			}
			return null;
		}));
	}

}
