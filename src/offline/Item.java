package offline;

public class Item implements Comparable<Item> {
	String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	double rating;

	Item(String id, double rating) {
		this.id = id;
		this.rating = rating;
	}

	@Override
	public int compareTo(Item another) {
		if (this.rating == another.rating) {
			return 0;
		}
		return this.rating < another.rating ? 1 : -1;
	}
}
