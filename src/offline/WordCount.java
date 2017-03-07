
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

public class WordCount {
	// potential good results are AWLVQ1NSU3LDS
	// A1GO6VJZN0UDLN
	private static final String COLLECTION_NAME = "words";

	public static void main(String[] args) {
		// Init
		MongoClient mongoClient = new MongoClient();
		MongoDatabase db = mongoClient.getDatabase(DBUtil.DB_NAME);

		// Get USER_ID's purchase records
		/**
		 * var map = function() {emit(this.word, 1); }
		 */

		// Construct mapper function
		String map = "function() {emit(this.word, 1); }";
		// Construct a reducer function
		String reduce = "function(key, values) {return Array.sum(values)} ";

		// MapReduce
		MapReduceIterable<Document> results = db.getCollection(COLLECTION_NAME).mapReduce(map, reduce);
		// Need a sorting here
		List<Word> wordcount = new ArrayList<>();
		results.forEach(new Block<Document>() {
			@Override
			public void apply(final Document document) {
				String id = document.getString("_id");
				Double value = document.getDouble("value");
				wordcount.add(new Word(id, value));
			}
		});
		// printList(similarUsers);
		// System.out.println("\n\n\n");
		Collections.sort(wordcount);
		printList(wordcount);

		mongoClient.close();
	}

	private static void printList(List<Word> wordcount) {
		for (Word word : wordcount) {
			System.out.println(word.getWord() + "," + word.getVal());
		}
	}
}
