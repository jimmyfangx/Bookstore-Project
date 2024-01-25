/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookstore;

/**
 *
 * @author Vlad
 */
public class Book {
    
    
    //*****************************************create get and set methods if necisarry
    private String name;
    private double price;
    
    
//Requires: String n with book name, and double p with book price.
//Effects: creates a book with name n and price p.
//Modifies: None
    public Book(String n, double p){
        
        
            
            this.name = n;
            this.price = p;
        
        
    }
    
//Effects: returns price of this book    
    public double getPrice(){
        return price;
    }
//Effects: returns name of this book
    public String getName(){
        return name;
    }
    
//Requires: Book b to be compared with (this) book.
//Effects: compares Book b with this book; returns true if they 
//          are the same otherwise returns false.
//Modifies: None.
    public boolean compareBook(Book b){
        
        if(this.price == b.price && (this.name).equals(b.name)){
            return true;
        }
        return false;
    }
    
    
   //Effects: returns this book as a string. The String is of format: 
   //         "Book Name: " + name + "/tPrice: "+ price + "$" 
   @Override 
   public String toString(){
       return ("Book Name: " + name + "\nPrice: $"+ price + "\n");
   }
    
    
}