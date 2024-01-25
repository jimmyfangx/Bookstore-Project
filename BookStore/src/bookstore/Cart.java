/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookstore;

import java.io.File;
import java.util.ArrayList;



/**
 *
 * @author Vlad
 */
public class Cart extends Bookstore{
    
    private ArrayList<Book> booksincart;
    private double totalPrice;
    private Customer buyer;
    
    
    
    
//Effects: creates a shopping cart for a customer c.    
    public Cart(Customer c){
        
        booksincart = new ArrayList<Book>();
        totalPrice = 0;
        buyer = c;
        
    }
    
//Requires: Book b (b must be valid Book) to be added to array list.
//Effects: adds Book b to arraylist books and calculates new totalPrice for customer.
//Modifies: this arraylist books (adds new book to list). totalPrice is updated
    public void addToCart(Book b){
        booksincart.add(b);
        totalPrice = totalPrice + b.getPrice();
    }
    

//Effects: removes all books from cart and resets total price
//Modifies: this object.    
    public boolean clearCart(){
        booksincart.clear();
        totalPrice = 0;
        return true;
        
    }
    
    
    
    
    
    
//Requires: Book b to be removed from arraylist books (b must be valid Book).
//Effects: Scans Arraylist books and removes only the first instance of the book 
//         to be removed also updates totalPrice of customer. If Book b is not
//         found in arraylist nothing changes.
//Modifies: This ArrayList books (removes book if found). This totalPrice is updated     
    public void removeFromCart(Book b){
        boolean removed = true;
        for(int i = 0; i < booksincart.size(); i++){
            if((booksincart.get(i)).compareBook(b) && removed == true){
                totalPrice = totalPrice - b.getPrice();
                booksincart.remove(i);
                removed = false;
            }
            
        }    
    }
    
//Effects: returns an ArrayList of Strings that represents the current Cart.
//         every index other than the last index is the String representation of
//         each book in the arraylist books. The Last index contains the total
//         Price in format: "Total Price: " + totalPrice + "$"
   public void newToString(){
       
       if(!booksincart.isEmpty()){
            System.out.println("Books in cart: ");
            for(Book b: booksincart){
            System.out.println(b);
            }
            System.out.println("Total Price: $" + totalPrice);
       }else{
           System.out.println("No books in cart.");
       }
   } 
    
   
//Effects: buys books without points. Adds points made off current purchase to 
//         the customers account. Also removes books from bookstore once purchased (also clears cart).
//Modifies: customer points, in text and variable, and books in stock.
   public boolean buyBooks(){
       
       File books = new File("books.txt");
       File customers = new File("Customers.txt");
       
       // updates points based on amount payed in variable and text file
       
       removeCustomer(buyer.getUsername());
       
       
       int newPoints = 0;
       newPoints = ((int)(this.totalPrice)) * 10;
       newPoints = newPoints + buyer.getPoints();
       if(newPoints < 0){return false;}
       buyer.setPoints(newPoints);
       
       addCustomer(buyer);
       writeCustomers(customers);
       
       
       //updates book.txt to remove books found in cart 
       
       readBooks(books);
       for(Book b: booksincart){
       this.removeBook(b.getName());
       }
       writeBooks(books);
                    
       
       
       //clear variables from this Cart to not allow re-buying etc.
       
       System.out.println("Purchase made:\n");
       for(Book b : booksincart){
           System.out.println(b);
       }
       System.out.println("Total Price: " + this.totalPrice);

       
       this.booksincart.clear();
       this.totalPrice = 0;
       
       return true;
   }
   //
   
   
   
//Effects: buys books with points. removes points used for purchase and adds 
//         points made off current purchase to the customers account.
//         Also removes books from bookstore once purchased (also clears cart)
//Modifies: customer points, in text and variable, and books in stock.
   public boolean buyWithPoints(){
       
       File books = new File("books.txt");
       File customers = new File("Customers.txt");
       
       // updates points based on amount payed and points used in variable and text file
       
       readCustomers(customers);
       removeCustomer(buyer.getUsername());
       
       
       double pointInCad = (int)(buyer.getPoints() / 100);
       int pointsUsed = 0;
       
       if( (totalPrice - pointInCad) <= 0){
           pointsUsed = ((int)(totalPrice)) * 100;
           totalPrice = 0; 
       }else{
           
           totalPrice = (totalPrice - pointInCad);
           pointsUsed = (int)(pointInCad*100);
       }
       
       if((pointsUsed > buyer.getPoints()) || (totalPrice < 0)){
           addCustomer(buyer);
           return false;
       }
       buyer.setPoints(buyer.getPoints() - pointsUsed + (((int)totalPrice) * 10));
       addCustomer(buyer);
       writeCustomers(customers);
       
       
       
       //updates book.txt to remove books found in cart 

       readBooks(books);
       for(Book b: booksincart){
       this.removeBook(b.getName());
       }
       writeBooks(books);
                    
       
       
       //clear variables from this Cart to not allow re-buying etc.
       //Also prints the "receipt".
       
       System.out.println("Purchase made:\n");
       this.newToString();

       
       this.booksincart.clear();
       this.totalPrice = 0;
    
       
       
       
       
       
       
       
       return true;
   } 
    
    
    
    
    
    
}
