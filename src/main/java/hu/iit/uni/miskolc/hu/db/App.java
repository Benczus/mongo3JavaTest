package hu.iit.uni.miskolc.hu.db;


import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;
import static com.mongodb.client.model.Filters.*;

import java.util.ArrayList;

public class App
{
   static MongoCollection<Document> termekCollection;
   static MongoCollection<? extends Document> rendelesCollection;
    public static void main( String[] args )
    {
        MongoClient client = new MongoClient("193.6.5.58");
        MongoDatabase mongoDatabase= client.getDatabase("dbms");
        termekCollection= mongoDatabase.getCollection("termekek7");
        rendelesCollection= mongoDatabase.getCollection("rendeles7");

//        addRendeles(1, "TP", 3 , new ArrayList());
//
//        addRendeles(2, "TIJ", 3 , new ArrayList());
//
//        addRendeles(3, "SK", 3 , new ArrayList());
//
//        addTermek(1, "tt1", 55, new ArrayList());
//
//        addTermek(2, "tt2", 65, new ArrayList());
//
//        addTermek(3, "tt3", 75, new ArrayList());


        // update Bson megoldás!
        Bson filter = new Document("tnev", "tt1");
        Bson newValue = new Document("ear", 90000);
        Bson updateOperationDocument = new Document("$set", newValue);
        termekCollection.updateOne(filter, updateOperationDocument);

//        update Filter.eq+Updates megoldás!
        termekCollection.updateOne(eq("tnev", "tt1"), Updates.set("ear", 31111));

        //3 hozzáadása a rendelésekhez
       termekCollection.updateOne(eq("tnev", "tt1"), Updates.addToSet("rendelesek", 3));
       //termékek kiiratása
        for (Document termek:termekCollection.find()){
            System.out.println(termek);
        }
    }

    public static void addTermek(int _id, String tnev, int ear, ArrayList rendelesList) {
    Document document= new Document();
    document.put("_id", _id);
    document.put("tnev", tnev);
    document.put("ear", ear);
    document.put("rendelesek", rendelesList);
    termekCollection.insertOne(document);



    }

    public static void addRendeles(int _id, String vevo, int db, ArrayList termekList) {
        Document document= new Document();
        document.put("_id", _id);
        document.put( "vevo", vevo);
        document.put("db", db);
        document.put("termekek", termekList);
        termekCollection.insertOne(document);
    }
}
