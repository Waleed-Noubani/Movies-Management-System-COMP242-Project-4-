package application;

public class Movies implements Comparable<Movies> {

	private String title;
	private String description;
	private int year;
	private double rating;

	public Movies(String title, String description, int year, double rating) {
		super();
		this.title = title;
		this.description = description;
		this.year = year;
		this.rating = rating;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	@Override
	public String toString() {
		return "Movies [title=" + title + ", description=" + description + ", year=" + year + ", rating=" + rating
				+ "]";
	}

	@Override
	public int hashCode() {
		return title.toLowerCase().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
	    if (obj == null) {
	        return false;
	    }
	    if (!(obj instanceof Movies)) {
	        return false;
	    }
	    Movies other = (Movies) obj;
	    if (this.title == null) {
	        return other.title == null;
	    }
	    return this.title.equals(other.title);
	}


	@Override
	public int compareTo(Movies o) {

		return this.getTitle().compareTo(o.getTitle());
	}

}
