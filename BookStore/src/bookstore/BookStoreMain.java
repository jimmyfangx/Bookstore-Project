/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookstore;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Insets; 
import javafx.geometry.Pos; 
import javafx.scene.control.PasswordField; 
import javafx.scene.layout.GridPane; 
import javafx.scene.text.Text; 
import javafx.scene.control.TextField; 
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 *
 * @author adm
 */
public class BookStoreMain extends Application {
    Scene sceneLogin, sceneAdmin, sceneAdminBook, sceneAdminCostumer;
    TableView<Book> tableBook;
    TableView<Customer> tableCostumer;
    Bookstore BT = new Bookstore();
    File Books = new File("Books.txt");
    
    @Override
    public void start(Stage primaryStage) {

      Admin adminCheck = new Admin("admin", "admin");
      //sceneLogin variables
      Text textW = new Text("Welcome to the BookStore App");    
      Text text1 = new Text("Username:");       
      Text text2 = new Text("Password:"); 
             
      TextField textField1 = new TextField();             
      PasswordField textField2 = new PasswordField();  
     
      Button button1 = new Button("Login"); 
      
     
      //implementing login screen 
      GridPane gridPane = new GridPane();    
      
      gridPane.setMinSize(600, 400);

      gridPane.setPadding(new Insets(10, 10, 10, 10)); 
      
      gridPane.setVgap(3); 
      gridPane.setHgap(3);        
      gridPane.setAlignment(Pos.CENTER); 
      
      gridPane.add(textW, 0, 0);
      gridPane.add(text1, 0, 1); 
      gridPane.add(textField1, 1, 1); 
      gridPane.add(text2, 0, 2);       
      gridPane.add(textField2, 1, 2); 
      gridPane.add(button1, 0, 3); 
      
      button1.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;"); 
      text1.setStyle("-fx-font: normal bold 15px 'Verdana' "); 
      text2.setStyle("-fx-font: normal bold 15px 'Verdana' ");  
      gridPane.setStyle("-fx-background-color: #D1CDC4;"); 
        
      //implementing owner start screen
      VBox layoutStart = new VBox(20);
      layoutStart.setAlignment(Pos.CENTER);
      Button button2 = new Button("Books");         
      Button button3 = new Button("Customers"); 
      Button button4 = new Button("Logout"); 
      
      layoutStart.getChildren().add(button2);
      layoutStart.getChildren().add(button3);
      layoutStart.getChildren().add(button4);
      
      //implementing owner book screen
      Button buttonB1 = new Button("Add");
      Button buttonB2 = new Button("Back");         
      Button buttonB3 = new Button("Delete");
      
      TextField textFieldBook1 = new TextField();     
      TextField textFieldBook2 = new TextField();  
              
      buttonB1.setOnAction(e -> {
            Book newBook = new Book(textFieldBook1.getText(), Double.parseDouble(textFieldBook2.getText()));
            tableBook.getItems().add(newBook);
            BT.writeBooks(Books);
            BT.readBooks(Books);
         });
      
      buttonB3.setOnAction(e -> {
          tableBook.getItems().removeAll(tableBook.getSelectionModel().getSelectedItem());
          BT.removeBook(tableBook.getSelectionModel().getSelectedItem());
          BT.writeBooks(Books);
      });
      
      
      
      TableColumn<Book, String> bookNameColumn = new TableColumn<>("Name"); 
      bookNameColumn.setMinWidth(200);
      bookNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
      
      TableColumn<Book, Double> bookPriceColumn = new TableColumn<>("Price"); 
      bookPriceColumn.setMinWidth(200);
      bookPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
      
      tableBook = new TableView<>();
      tableBook.setItems(getBook());
      tableBook.getColumns().addAll(bookNameColumn, bookPriceColumn);
      
      GridPane gridBook = new GridPane();
      gridBook.add(tableBook, 0, 0, 3, 1);
      gridBook.add(buttonB1, 0, 1, 1, 1);
      gridBook.add(textFieldBook1, 1, 1, 1, 1);
      gridBook.add(textFieldBook2, 2, 1, 1, 1);
      gridBook.add(buttonB2, 0, 2, 1, 1);
      gridBook.add(buttonB3, 1, 2, 2, 1);
      
      gridBook.setHgap(10);
      gridBook.setVgap(10);
      
      gridBook.setPadding(new Insets(10,10,10,10));
      //implementing owner costumer screen
      TableColumn<Customer, String> cosUsernameColumn = new TableColumn<>("Username"); 
      cosUsernameColumn.setMinWidth(200);
      cosUsernameColumn.setCellValueFactory(new PropertyValueFactory<>("Username"));
      
      TableColumn<Customer, String> cosPasswordColumn = new TableColumn<>("Password"); 
      cosPasswordColumn.setMinWidth(200);
      cosPasswordColumn.setCellValueFactory(new PropertyValueFactory<>("Password"));
       
      TableColumn<Customer, Integer> cosPointsColumn = new TableColumn<>("Points"); 
      cosPointsColumn.setMinWidth(200);
      cosPointsColumn.setCellValueFactory(new PropertyValueFactory<>("Points"));
      
      tableCostumer = new TableView<>();
      tableCostumer.setItems(getCustomer());
      tableCostumer.getColumns().addAll(cosUsernameColumn, cosPasswordColumn, cosPointsColumn);
      
      VBox layoutCos= new VBox(20);
      layoutCos.setAlignment(Pos.CENTER);
      layoutCos.getChildren().add(tableCostumer);
      
        //check for admin, then go into admin screen
        button1.setOnAction(e -> {
               
            if (adminCheck.verifyLogin(textField1.getText(), textField2.getText())){
                primaryStage.setScene(sceneAdmin);
          }
      });
        
        //button to go into books screem
         button2.setOnAction(e -> {
             primaryStage.setScene(sceneAdminBook);
        

         });
       
        //button to go into costumer screem
          button3.setOnAction(e -> {primaryStage.setScene(sceneAdminCostumer);});
        //button to logout
          button4.setOnAction(e -> {primaryStage.setScene(sceneLogin);});
         
      sceneLogin = new Scene(gridPane);  
      sceneAdmin = new Scene(layoutStart, 500, 300);  
      sceneAdminBook = new Scene(gridBook);  
      sceneAdminCostumer = new Scene(layoutCos, 500, 300);  
      
      primaryStage.setTitle("Bookstore App"); 
      primaryStage.setScene(sceneLogin);
      primaryStage.show(); }
      
    public static void main(String args[]){ 
        
      launch(args); 
      
   } 


//Get all of the Products
public ObservableList<Book> getBook(){   
    
  ObservableList<Book> books = FXCollections.observableList(BT.bookList);  
    try{
            Scanner scan = new Scanner (Books);
            if(Books.length() != 0){
                while (scan.hasNextLine()){
                    String[] token = scan.nextLine().split("\\s+");
                    String name = token[0];
                    double price = Double.parseDouble(token[1]);
                    books.add(new Book(name, price));
                }
                scan.close();
            }else{
                System.out.println("No books found.");
            }
        }
        catch(IOException e){
            System.out.println("An Error occured");
            e.printStackTrace();
        };
  return books;
}

public ObservableList<Customer> getCustomer(){
  Customer c1 = new Customer("bob", "bobby", 100);
  Customer c2 = new Customer("Mina", "Nemlieth", 800);
  ObservableList<Customer> customers = FXCollections.observableArrayList();  
   customers.add(c1);
   customers.add(c2);
   return customers;
}

}