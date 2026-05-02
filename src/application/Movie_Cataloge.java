package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Movie_Cataloge implements Iterable<Movies> {

	static HashTable<Movies> table;

	public Movie_Cataloge() {
		table = new HashTable<>(5);
	}

	public int getSize() {
		return table.getSize();
	}

//	public AVLTree<Movies> getAvl(int index) {
//		return table.getAvll(index);
//	}
	public AVLTree<Movies> getAvl(int index) {
		if (index < 0 || index >= table.table.length) {
			System.out.println("Invalid index: " + index);
			return new AVLTree<Movies>(); 
		}
		return table.getAvll(index);
	}

	public void add(Movies movie) {
		table.insert(movie);
	}

	public void delete(Movies movie) {
		table.delete(movie);
	}

	public Movies search(Movies movie) {
		return table.search(movie);
	}

	public ObservableList<Movies> toObservableList() {
		return table.toObservableList();
	}

	public void traverse() {
		table.traverse();
	}

	public void readFile(File file) {
		int count = 0;
		try {
			Scanner input = new Scanner(file);
			while (input.hasNext()) {
				try {
					String title = input.nextLine().trim();
					if (title.isEmpty())
						continue;

					String description = input.nextLine().trim();
					int year = Integer.parseInt(input.nextLine().trim());
					double rating = Double.parseDouble(input.nextLine().trim());

					Movies movie = new Movies(title, description, year, rating);
					this.add(movie);
					count += 1;
					System.err.println("1");
					// تخطي السطر الفارغ بين كل فيلم وفيلم (اختياري)
					if (input.hasNextLine())
						input.nextLine();
					System.err.println("2");
				} catch (Exception e) {
					System.out.println("تم تجاوز فلم اثناء قراءة الفايل");
					e.printStackTrace();
					continue;
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("file not exist");
		}
		System.err.println(count);
	}

	public void saveInFile(File file) {
		try {
			PrintWriter out = new PrintWriter(file);
			for (AVLTree<Movies> avlTree : table.getTable()) {
				for (Movies movie : avlTree) {
					out.println(movie.getTitle());
					out.println(movie.getDescription());
					out.println(movie.getYear());
					out.println(movie.getRating());
					out.println();
				}
			}
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Iterator<Movies> iterator() {
		ArrayList<Movies> allMovies = new ArrayList<>();
		for (AVLTree<Movies> avlTree : table.getTable()) {
			for (Movies m : avlTree) {
				allMovies.add(m);
			}
		}
		return allMovies.iterator();
	}

	public void clear() {
		table.clear();
	}
}
