package com.winbits.api.affiliation.services

import com.mongodb.BasicDBObject
import com.mongodb.DB
import com.mongodb.DBCollection
import com.mongodb.DBCursor
import com.mongodb.DBObject
import org.bson.types.ObjectId
import org.joda.time.DateTime

class MongoDbService {

  DB mongoDb

  def insertInCollection(String collectionName, String json) {
    ObjectId objectId = ObjectId.get()
    def collection = mongoDb.getCollection(collectionName)
    DBObject dbObject = (DBObject)com.mongodb.util.JSON.parse(json)
    dbObject.append("_id", objectId)
    dbObject.append("date_created", new DateTime().toString('yyyy-MM-dd hh:mm:ss') )
    collection.insert(dbObject)
    objectId.toString()
  }

  def findDocumentById(String collectionName, String documentId) {
    DBCollection coll = mongoDb.getCollection(collectionName)
    ObjectId objectId = new ObjectId(documentId)
    BasicDBObject query = new BasicDBObject("_id", objectId)
    BasicDBObject fields = new BasicDBObject("_id", false)
    DBCursor cursor = coll.find(query, fields)
    cursor.hasNext() ? cursor.next().toString() : null
  }


    def findDocumentByEmail(String collectionName, String eMail) {
        collectionName="Bebitos"
        println("email: " + eMail +" collectionName: "+collectionName)
        DBCollection coll = mongoDb.getCollection(collectionName)
        BasicDBObject query = new BasicDBObject("email", eMail)
        BasicDBObject fields = new BasicDBObject("email", false)
        DBCursor cursor = coll.find(query, fields)
        cursor.hasNext() ? cursor.next().toString() : null
    }

}
