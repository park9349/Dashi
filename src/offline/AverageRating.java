
package offline;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.MapReduceIterable;
import com.mongodb.client.MongoDatabase;

import db.DBUtil;

public class AverageRating {
	// potential good results are AWLVQ1NSU3LDS
	// A1GO6VJZN0UDLN
	private static final String COLLECTION_NAME = "ratings";

	public static void main(String[] args) {
		// Init
		MongoClient mongoClient = new MongoClient();
		MongoDatabase db = mongoClient.getDatabase(DBUtil.DB_NAME);

		/**
		 * var map = function() { emit(this.item, this.rating); }
		 */

		// Construct mapper function
		String map = "function() { emit(this.item, this.rating); }";
		// Construct a reducer function
		String reduce = "function(key, values) {return Array.avg(values)} ";

		// MapReduce
		MapReduceIterable<Document> results = db.getCollection(COLLECTION_NAME).mapReduce(map, reduce);
		// Need a sorting here
		List<Item> items = new ArrayList<>();
		results.forEach(new Block<Document>() {
			@Override
			public void apply(final Document document) {
				String id = document.getString("_id");
				Double value = document.getDouble("value");
				items.add(new Item(id, value));
			}
		});
		// printList(similarUsers);
		// System.out.println("\n\n\n");
		Collections.sort(items);
		printList(items);

		mongoClient.close();
	}

	private static void printList(List<Item> items) {
		for (Item item : items) {
			System.out.println(item.getId() + "," + item.getRating());
		}
	}
}
