package com.niit;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class datastore 
{
	public static void main(String[] args) {    
        
	      
	    StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("ProductConfig.xml").build();  
	          
	   Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();  
	  
	SessionFactory factory = meta.getSessionFactoryBuilder().build();  
	Session session = factory.openSession();  
	Transaction t = session.beginTransaction();   
	            
	    Product e1=new Product();    
	    e1.setProid(101);    
	    e1.setProname("Mobile");
	    e1.setPrice(10000);
	    session.save(e1);
	    
	    Product e2=new Product(); 
	    e2.setProid(102);    
	    e2.setProname("Tv");
	    e2.setPrice(11000);
	        
	    session.save(e2);  
	    t.commit();  
	    System.out.println("successfully saved");
	    
	    
	    ArrayList<Product> list=new ArrayList<>();
	    
	    for(Product str:list){
	    	System.out.println(str.getProid());
	    	System.out.println(str.getProname());
	    	System.out.println(str.getPrice());
	    }
	    factory.close();  
	    session.close();    
	        
	}    

}
