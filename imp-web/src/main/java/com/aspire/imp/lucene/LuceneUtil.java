package com.aspire.imp.lucene;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.TextField;

import com.aspire.imp.web.entity.Post;  

public class LuceneUtil {
	public static Document post2Doc(Post post){
        Document document = new Document();  
        Field idField = new LongPoint("id", post.getId());
        Field titleField = new TextField("title", post.getTitle(), Store.YES);
        Field contentField = new TextField("content", post.getContent(), Store.YES); 
        //2.把field放入document中  
        document.add(idField);  
        document.add(titleField);  
        document.add(contentField);  
        return document;  
    }  
    
    public static Post doc2Post(Document document){  
    	Post post = new Post();  
    	post.setId(Long.parseLong(document.get("id")));  
    	post.setTitle(document.get("title"));  
    	post.setContent(document.get("content"));  
        return post;  
    }
}
