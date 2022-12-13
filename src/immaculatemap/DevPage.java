package immaculatemap;



import java.io.IOException;
import java.util.Optional;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.HBox;

/**
 * Developer Page UI Component 
 * @author Matia Lee (mlee927@uwo.ca)
 * December 3, 2022
 * Last push made 120322, at 1438 Hrs. 
 */

public class DevPage extends Application {
	// Needed to perform dev operations later
	static Developer dev = new Developer(); 
	
   public static void main(String[] args) {
       launch(args);
   }
   
   @Override
   public void start(Stage primaryStage) {
	  Pane root = new Pane();
	  Text caution = new Text("CAUTION!");
	  caution.setFont(Font.font("Futura", FontWeight.BOLD, 28)); 
	  caution.setFill(Color.RED);
	  
	  caution.setLayoutX(400);
	  caution.setLayoutY(70);
	   root.getChildren().add(caution); 
	  
	  Text scenetitle = new Text("You have entered the developer mode. "
	   		+ "\nChanges you make here will be applied for ALL USERS. ");
	   scenetitle.setFont(Font.font("Avenir", FontWeight.NORMAL, 18)); 
	   GridPane grid = new GridPane();
	   StackPane.setAlignment(scenetitle, Pos.CENTER);
	   grid.add(scenetitle, 0, 0, 2, 1);
	  
	   
	   
       primaryStage.setTitle("Immaculate Map -- Developer Mode");
     
       
       /**
        * Creates the footer with information about the project/team
        */
	   Text masthead = new Text("(C) 2022 Immaculators"
	   		+ "\nAll rights reserved. "
	   		+ "\nMade with " + "\u2764\ufe0f" + " "
	   				+ "in London");
	   masthead.setFont(Font.font("Verdana", FontWeight.NORMAL, 10)); 
		   StackPane.setAlignment(masthead, Pos.CENTER);
		   grid.add(masthead, 0, 0, 2, 1);
		   masthead.setLayoutX(800);
	       masthead.setLayoutY(450);
		   root.getChildren().add(masthead); 
	       
       
		   /*
		    * Adding a Building
		    * Asks user to enter the name of the building and 
		    * check off whether that building has Built-In POIs enabled
		    */
       Button addBuilding = new Button(); 
       addBuilding.setText("Add a building");
       // Actions performed when "Add a building" button is clicked. 
       addBuilding.setOnAction(new EventHandler<ActionEvent>() {
    	   public void handle(ActionEvent event) {
    		  Stage popup = new Stage(); 
    		  popup.initModality(Modality.APPLICATION_MODAL);
    		  popup.setTitle("Add new building"); 
    		    
    		  Label newName= new Label("Enter the name of the new building: ");
    		  newName.setLayoutX(0);
    		  TextField NameInput = new TextField();
    		       
    		  Button ok= new Button("OK");

    		  CheckBox cb2 = new CheckBox("Builtin POIs are ENABLED in this building");
    		  
    		  ok.setOnAction(new EventHandler<ActionEvent>() {
    			  public void handle(ActionEvent e) {
    				  System.out.println(NameInput.getText()); 
    				  System.out.println(cb2.isSelected()); 
    				  Floor[] list = new Floor[] {};
    				  Building newBuilding = new Building(NameInput.getText(), list,cb2.isSelected());
    				  dev.addBuilding(newBuilding);
    				  
    				  Stage pop = new Stage(); 
    				  pop.initModality(Modality.APPLICATION_MODAL);
    				  pop.setTitle("Add new building"); 
    	    		  Label notification = new Label("A new building, [" + NameInput.getText()+ "] "
    	    		  		+ "with builtInPoi status [" + cb2.isSelected()+ "] has been successfully added. "
    	    		  				+ "\nYou may add floors to this building using the [Add a floor] button. ");
    	    		  
    	    		  Button ok2 = new Button ("OK"); 
    	    		  ok2.setOnAction(e1 -> {popup.close(); pop.close(); });
    	    		  VBox l = new VBox(2); 
    	    		  l.setAlignment(Pos.CENTER);
    	    		  l.getChildren().addAll(notification, ok2);
    	    		  Scene s = new Scene(l, 500, 200); 
    	    		  pop.setScene(s);
    	    		  pop.showAndWait(); 
    	    		  }});
    		  
    		  VBox layout= new VBox(10);
    		  layout.getChildren().addAll(newName, NameInput, cb2, ok);
    		  layout.setAlignment(Pos.CENTER);
    		  Scene scene1= new Scene(layout, 300, 250);
    		  popup.setScene(scene1);
    		  popup.showAndWait();
    	   }
       });
       
       /*
        * Removes a building. 
        * Asks the user to select from a drop down list of currently
        * existing buildings which building to remove. 
        */
       Button removeBuilding = new Button(); 
       removeBuilding.setText("Remove a building");
       removeBuilding.setOnAction(new EventHandler<ActionEvent>() {
    	   public void handle(ActionEvent event) {
    		  Stage popup = new Stage(); 
     		  popup.initModality(Modality.APPLICATION_MODAL);
     		  popup.setTitle("Remove a building"); 
     		    
     		  Label rmLabel= new Label("Choose from the below list which building to remove:  ");
     		 rmLabel.setLayoutX(0);
     		  ObservableList<String> options = 
     				  FXCollections.observableArrayList();
     		 int i = 0;
     		  for (; i < Main.buildingList.size(); i++) {
     			  options.add(Main.buildingList.get(i).getName());
     		  }
     		  
     		  
     		  Button ok= new Button("OK");
     		  final ComboBox buildinglist = new ComboBox(options); 
    		  
    		  ok.setOnAction(e -> {
    			// Store in a string the name of a building to remove
    			  String targetBuildingName = buildinglist.getValue().toString(); 
    			  int k = 0;
    			  for (; k < Main.buildingList.size(); k++) {
         			  if (targetBuildingName.equals(Main.buildingList.get(k).getName()))
         				  break; 
         		  }
    			  Building b = Main.buildingList.get(k); 
    			  // Logically, this loop is logically bound to produce the correct result
    			  // without failure. 
    			  
    			  Stage pop = new Stage(); 
				  pop.initModality(Modality.APPLICATION_MODAL);
				  pop.setTitle("Add new building"); 
	    		  Label notification = new Label(
	    				  "Are you sure you want to remove the [" +
	    		  targetBuildingName + 
	    						  "] building?"
	    				  );
	    		  
	    		  Button ok2 = new Button ("Yes"); 
	    		  
	    		  // actual remove operation here: 
	    		  
	    		  ok2.setOnAction(e1 -> {
	    			  dev.removeBuilding(b);
	    			  popup.close(); 
	    		  pop.close(); });
	    		  VBox l = new VBox(2); 
	    		  l.setAlignment(Pos.CENTER);
	    		  l.getChildren().addAll(notification, ok2);
	    		  Scene s = new Scene(l, 500, 200); 
	    		  pop.setScene(s);
	    		  pop.showAndWait(); 
    			  popup.close();
    		  });
    		  
    		  VBox layout= new VBox(5);
    		  layout.getChildren().addAll(rmLabel, buildinglist, ok);
    		  layout.setAlignment(Pos.CENTER);
    		  Scene scene1= new Scene(layout, 300, 250);
    		  popup.setScene(scene1);
    		  popup.showAndWait();
    		  
    		
    	   }
       });
       
    // Add a Floor
    // Add a Floor
       Button addFloor = new Button(); 
       addFloor.setText("Add a floor");
       addFloor.setOnAction(new EventHandler<ActionEvent>() {
    	   public void handle(ActionEvent event) {
    		   Stage popup = new Stage(); 
     		  popup.initModality(Modality.APPLICATION_MODAL);
     		  popup.setTitle("Add a floor"); 
     		    
     		  Label newName= new Label("Enter the name of the floor: ");
     		  newName.setLayoutX(0);
     		  TextField NameInput = new TextField();
     		  
     		  Label mapImage = new Label("Enter the path to the image file for this floor: ");
     		  TextField mapImageInput = new TextField(); 
     		  
     		       
     		 Label accLayer = new Label("Enter the path to the accessibility layer for this floor: ");
    		  TextField accLayerInput = new TextField(); 
    		  
    		  Label buildingName = new Label("Choose the building this floor should belong to: ");
     		  TextField buildingNameInput = new TextField(); 
     		  ObservableList<String> options = FXCollections.observableArrayList();
     		  for (int i = 0; i < Main.buildingList.size(); i++) {
     			  options.add(Main.buildingList.get(i).getName()) ; 
     		  }
     		  final ComboBox ComboBox = new ComboBox(options);
     		  Button ok= new Button("OK");
     		  
     		  
     		  // ok is pressed
     		  ok.setOnAction(e -> {
     			  Floor f = new Floor(); 
     			  f.setName(NameInput.getText()); 
     			  f.setMapImage(mapImageInput.getText());
     			  f.setAccessibilityLayer(accLayerInput.getText());
     			  String building_name = ComboBox.getValue().toString(); 
     			  Building b; 
     			 int i = 0;
     			  for (; i < Main.buildingList.size(); i++) {
     				  if (building_name.equals(Main.buildingList.get(i).getName())) {
     					  break; 
     				  }
     			  }
     			  
     			  if (i == Main.buildingList.size()) {
     				  System.out.println("I HERE: " + i);
     				  System.out.println("ERROR! - Building for this floor cannot be"
     				  		+ "found. Floors can only be added to existing buildings. ");
   				  
     				  Stage errormessage = new Stage(); 
     				 errormessage.initModality(Modality.APPLICATION_MODAL); 
     				errormessage.setTitle("ERROR!"); 
     				Label message = new Label("ERROR! - Building for this floor cannot be"
     				  		+ " found. \nFloors can only be added to existing buildings. ");
     				Button b1 = new Button("OK"); 
     				b1.setOnAction(e1 -> {errormessage.close(); popup.close();
     				});
     				VBox l3 = new VBox(3); 
     				l3.getChildren().addAll(message, b1); 
     				l3.setAlignment(Pos.CENTER); 
     				Scene s3 = new Scene(l3, 500, 300); 
     				errormessage.setScene(s3);
     				errormessage.showAndWait(); 
     				}
     			  
     			  
     			  b = Main.buildingList.get(i); 
     			  
     			  f.setBuilding(building_name);
     			  b.addFloor(f); 
     			  popup.close(); 
     			  
     
     			  Stage pop = new Stage(); 
				  pop.initModality(Modality.APPLICATION_MODAL);
				  pop.setTitle("Add new building"); 
	    		  Label notification = new Label(
	    				  "The [" + NameInput.getText()+"] floor has been successfully added. "
	    				  );
	    		  
	    		  Button ok2 = new Button ("OK"); 
	    		  
	    		  
	    		  ok2.setOnAction(e1 -> {
	    			  popup.close(); 
	    		  pop.close(); });
	    		  VBox l = new VBox(5); 
	    		  l.setAlignment(Pos.CENTER);
	    		  l.getChildren().addAll(notification, ok2);
	    		  Scene s = new Scene(l, 500, 200); 
	    		  pop.setScene(s);
	    		  pop.show(); 
	    		  pop.showAndWait(); 
   			  popup.close();
     			  
     			  
     			  
     		  });
     		  
     		 VBox layout= new VBox(11);
   		  layout.getChildren().addAll(newName, NameInput, mapImage, mapImageInput, accLayer
   				  , accLayerInput, buildingName, ComboBox, ok);
   		  layout.setAlignment(Pos.CENTER);
   		  Scene scene1= new Scene(layout, 500, 300);
   		  popup.setScene(scene1);
   		  popup.showAndWait();
     		  
    	   }
       });
       
       
       
       // Remove a Floor
       Button removeFloor = new Button(); 
       removeFloor.setText("Remove a floor");
       removeFloor.setOnAction(new EventHandler<ActionEvent>() {
    	   public void handle(ActionEvent event) {Stage popup = new Stage(); 
  		  popup.initModality(Modality.APPLICATION_MODAL);
  		  popup.setTitle("Remove a floor"); 
  		    
  		  Label rmLabel= new Label("Choose from the below list which building to remove a floor from:  ");
  		 rmLabel.setLayoutX(0);
  		  ObservableList<String> options_here = 
  				  FXCollections.observableArrayList();
  		 int i = 0;
  		  for (; i < Main.buildingList.size(); i++) {
  			  options_here.add(Main.buildingList.get(i).getName());
  		  }
  		  
  		  
  		  Button ok= new Button("OK");
  		  final ComboBox buildinglist = new ComboBox(options_here); 
 		  
 		  ok.setOnAction(e -> {
 			// Store in a string the name of a building to remove
 			  String targetBuildingName = buildinglist.getValue().toString(); 
 			  int k = 0;
 			  for (; k < Main.buildingList.size(); k++) {
      			  if (targetBuildingName.equals(Main.buildingList.get(k).getName()))
      				  break; 
      		  }
 			  Building b = Main.buildingList.get(k); 
 			  
 			  Stage pop = new Stage(); 
 			 pop.initModality(Modality.APPLICATION_MODAL);
			  pop.setTitle("Remove a floor"); 
   		  Label notification = new Label("Select from below dropdown which floor to remove: ");
   		  
   		ObservableList<String> floorOptions = 
				  FXCollections.observableArrayList();
		 int p = 0;
		  for (; p < b.getFloors().length; p++) {
			  System.out.println("HERE!: " + b.getFloors()[p].getName());
			  floorOptions.add(b.getFloors()[p].getName());
		  }
		  
		  Button ok2 = new Button("OK"); 
		  final ComboBox floorList = new ComboBox(floorOptions); 
		  
		  ok2.setOnAction(ex -> {
			  
			  String targetFloorName = floorList.getValue().toString(); 
			  
			  int x = 0; 
			  for (; x < b.getFloors().length; x++) {
				  if (targetFloorName.equals(b.getFloors()[x].getName()))
					  break; 
			  }
			  dev.removeFloor(b.getFloors()[x]); 
			  pop.close();
			  popup.close(); 
		  });
		  
		  
		  VBox layout2 = new VBox(5); 
		  layout2.getChildren().addAll(notification, floorList, ok2); // add here
		  layout2.setAlignment(Pos.CENTER); 
		  Scene scene2 = new Scene(layout2, 300, 250); 
		  pop.setScene(scene2); 
		  pop.showAndWait(); 
   		  
 		  });
 		  
 		  VBox layout= new VBox(5);
 		  layout.getChildren().addAll(rmLabel, buildinglist, ok);
 		  layout.setAlignment(Pos.CENTER);
 		  Scene scene1= new Scene(layout, 300, 250);
 		  popup.setScene(scene1);
 		  popup.showAndWait();
 		  
    		   
    	   }
       });
       
       
       Button addPOI = new Button(); 
       addPOI.setText("Add a POI");

       addPOI.setOnAction(e -> {
    	   Stage popupwindow=new Stage();
    	      
    	   popupwindow.initModality(Modality.APPLICATION_MODAL);
    	   popupwindow.setTitle("Add a POI");
    	         
    	         
    	   	Label label1 = new Label("Fill in the below fields: \n");
    	   	
    	   	Label poiName = new Label("POI Name ");
 		  	TextField poiNameInput = new TextField();
 		  	Label poiDesc = new Label("POI Description "); 
 		  	TextField poiDescInput = new TextField(); 
 		  	Label poiType = new Label("POI Type"); 
 		  	TextField poiTypeInput = new TextField(); 
 		  	Label x_axis = new Label("x-axis of the POI (integer)"); 
 		  	TextField x_axisInput = new TextField();  
 		  	Label y_axis = new Label("y-axis of the POI (integer)"); 
 		  	TextField y_axisInput = new TextField(); 
 		  	Label b = new Label("Name of the building this POI belongs in");
 		  	ObservableList<String> blist = FXCollections.observableArrayList(); 
 		  	for (int i = 0; i < Main.buildingList.size(); i++) {
 		  		blist.add(Main.buildingList.get(i).getName()); 
 		  	}
 		  	final ComboBox blist_drop = new ComboBox(blist); 
 		  	
 		   
    	   
    	   Button button1= new Button("OK");
    	        
    	        
    	   button1.setOnAction(e2 -> {
    		   int z = 0;
    		   for (; z < Main.buildingList.size(); z++) {
    			   if (Main.buildingList.get(z).getName().equals(blist_drop.getValue().toString())) 
    				   break; 
    		   } 
    		   if (z == Main.buildingList.size())  // logically impossible to fail this condition. 
    			   System.out.println("no such building"); 
    		   Building bb = Main.buildingList.get(z); 
    		   
     		  	Label floorName = new Label("Name of the floor this POI belongs in"); 
     		  	ObservableList<String> flist = FXCollections.observableArrayList(); 
     		  	for(int i = 0; i < bb.getFloors().length; i++) {
     		  		flist.add(bb.getFloors()[i].getName()); 
     		  	}
     		  	final ComboBox flist_drop = new ComboBox(flist); 
     		  	
     		  	Stage selectFloor = new Stage(); 
     		   selectFloor.initModality(Modality.APPLICATION_MODAL); 
    		   selectFloor.setTitle("Add a POI"); 
    		   Button bx = new Button("OK"); 
    		   
    		   bx.setOnAction(ek -> {
    			   
    			   Stage popup = new Stage(); 
        		   popup.initModality(Modality.APPLICATION_MODAL); 
        		   popup.setTitle("Add a POI"); 
        		   Label alert = new Label("A new POI with the following properties "
        		   		+ "\n will be added. Clicking on \"CONFIRM\" will process"
        		   		+ "\n this change. ");
        		   Button confirm = new Button("CONFIRM"); 
        		   
        		   String newName = poiNameInput.getText(); 
        		   String newDesc = poiDescInput.getText(); 
        		   String newType = poiTypeInput.getText(); 
        		   int new_x_axis = Integer.parseInt(x_axisInput.getText());  
        		   int new_y_axis = Integer.parseInt(y_axisInput.getText());  
        		   String whichBuilding = blist_drop.getValue().toString(); 
        		   String whichFloor = flist_drop.getValue().toString(); 
        		   Label message = new Label("Name: " + newName + 
        		   		"\nDescription: " + newDesc
        		   		+ "\nType: " + newType + 
        		   		"\nLocation: (" + new_x_axis + ", " + new_y_axis 
        		   		+ ") " 
        		   		+ "\nIn this building: " + whichBuilding
        		   		+ "\nIn this floor: " + whichFloor);
        		   
        		   confirm.setOnAction(e3 -> { // User confirms the addition.
        			   int[] loc = new int[] {new_x_axis, new_y_axis}; 
        			   
        			   int zz = 0; 
        			   for (; zz < bb.getFloors().length; zz++) {
        				   if (bb.getFloors()[zz].getName().equals(whichFloor))
        					   break; 
        			   }
        			   if (zz == bb.getFloors().length) 
        				   System.out.println("no such floor"); 
        			   Floor f = bb.getFloors()[zz]; 
        			   f.setBuilding(whichBuilding);
        			   
        			   Button confirm2 = new Button("CONFIRM");
        			   
        			   POI toAdd = new POI(newName, newDesc, newType, loc, f);
        			   
        			   dev.addPOI(toAdd);
        			   
        			   popupwindow.close(); 
        			   popup.close(); 
        			   selectFloor.close(); 
        		   }); 
        		   VBox layout2 = new VBox(10); 
        		   layout2.getChildren().addAll(alert, message, confirm); 
        		   layout2.setAlignment(Pos.CENTER); 
        		   Scene sc = new Scene(layout2, 300, 350); 
        		   popup.setScene(sc); 
        		   popup.showAndWait(); 
        		   
        		   
        		   
        		   popupwindow.close(); 
    			   
    		   });
    		   VBox layoutx = new VBox(10); 
    		   layoutx.getChildren().addAll(floorName, flist_drop, bx);
     		  	layoutx.setAlignment(Pos.CENTER); 
     		  	Scene scenex =  new Scene(layoutx, 300, 250);
     		  	selectFloor.setScene(scenex); 
     		  	selectFloor.showAndWait(); 
     		  	
    		   
    	   });
    	        
    	        

    	   VBox layout= new VBox(10);
    	        
    	         
    	   layout.getChildren().addAll(label1, poiName, poiNameInput
    			   , poiDesc, poiDescInput, poiType, poiTypeInput, 
    			   x_axis, x_axisInput, y_axis, y_axisInput, b, blist_drop, button1);
    	         
    	   layout.setAlignment(Pos.CENTER);
    	         
    	   Scene scene1= new Scene(layout, 300, 500);
    	         
    	   popupwindow.setScene(scene1);
    	         
    	   popupwindow.showAndWait();
       });
       
       
       
       
       Button removePOI = new Button("Remove a POI"); 
       removePOI.setOnAction(e -> {
    	   Stage popupwindow = new Stage(); 
    	   popupwindow.initModality(Modality.APPLICATION_MODAL); 
    	   
    	   
    	   Label label1 = new Label("Building this POI belongs in"); 
    	   
    	   ObservableList<String> options = FXCollections.observableArrayList(); 
    	   for (int i = 0; i < Main.buildingList.size(); i++) {
    		   options.add(Main.buildingList.get(i).getName()); 
    	   }
    	   final ComboBox comboBox = new ComboBox (options); 
    	   
    	   Button button1 = new Button("OK"); 
    	   button1.setOnAction(e1 -> {
    		  
    		   int k = 0; 
    		   for (; k < Main.buildingList.size(); k++) {
    			   if (Main.buildingList.get(k).getName().equals(comboBox.getValue().toString()))
    				   break; 
    		   }
    		   Building b = Main.buildingList.get(k); 
    		   
    		   Stage popupwindow1 = new Stage(); 
        	   popupwindow1.initModality(Modality.APPLICATION_MODAL); 
        	   
        	   
        	   Label label2 = new Label("Floor this POI belongs in"); 
        	   
        	   ObservableList<String> options1 = FXCollections.observableArrayList(); 
        	   for (int i = 0; i < b.getFloors().length; i++) {
        		   options1.add(b.getFloors()[i].getName()); 
        	   }
        	   final ComboBox cb2 = new ComboBox(options1); 
        	   Button button2 = new Button("OK"); 
        	   button2.setOnAction(e2 -> {
        		   int y = 0;
        		   for (; y < b.getFloors().length; y++) {
        			   if (b.getFloors()[y].getName().equals(cb2.getValue().toString())
        					   ) break; 
        		   }
        		   Floor f = b.getFloors()[y]; 
        		   
        		   Stage s3 = new Stage(); 
        		   s3.initModality(Modality.APPLICATION_MODAL); 
        		   s3.setTitle("Remove a POI"); 
        		   Label l = new Label("Which POI do you wish to remove?"); 
        		   ObservableList<String> op = FXCollections.observableArrayList(); 
        		   for (int m = 0; m < f.getBuiltInPOIs().length; m++) {
        			   if (f.getBuiltInPOIs()[m] == null) break; 
        			   op.add(f.getBuiltInPOIs()[m].getName()); 
        		   }
        		   final ComboBox cb3 = new ComboBox(op); 
        		   Button rm = new Button ("REMOVE THIS POI"); 
        		   rm.setOnAction(ex -> {
        			   int n = 0; 
        			   for (; n < f.getBuiltInPOIs().length; n++) {
        				   if (f.getBuiltInPOIs()[n].getName().equals(cb3.getValue().toString()))
        					   break; 
        			   }
        			   POI target = f.getBuiltInPOIs()[n]; 
        			   dev.removePOI(target, f); 
        			   s3.close();
        			   popupwindow1.close(); 
        			   popupwindow.close(); 
        		   });
        		   
        		   VBox l3 = new VBox(10); 
        		   l3.getChildren().addAll(l, cb3, rm); 
        		   l3.setAlignment(Pos.CENTER); 
        		   Scene s4 = new Scene(l3, 300, 250); 
        		   s3.setScene(s4); 
        		   s3.showAndWait(); 
        		   
        		   
        		   
        	   }); 
        	   VBox layout2 = new VBox(10); 
        	   layout2.getChildren().addAll(label2, cb2, button2); 
        	   layout2.setAlignment(Pos.CENTER); 
        	   Scene scene1 = new Scene(layout2, 300, 250); 
        	   popupwindow1.setScene(scene1); 
        	   popupwindow1.showAndWait(); 
    		   
    	   }); 
    	   VBox layout = new VBox(10); 
    	   layout.getChildren().addAll(label1, comboBox, button1); 
    	   layout.setAlignment(Pos.CENTER); 
    	   Scene scene1 = new Scene(layout, 300, 250); 
    	   popupwindow.setScene(scene1); 
    	   popupwindow.showAndWait(); 
    	   

       });
       
       
       
       // Edit POIs
       Button editPOI = new Button("Edit a POI"); 
       editPOI.setOnAction(e -> {
    	   Stage popupwindow = new Stage(); 
    	   popupwindow.initModality(Modality.APPLICATION_MODAL); 
    	   
    	   
    	   Label label1 = new Label("Which builing does the POI you want to edit belong to?"); 
    	   
    	   ObservableList<String> options = FXCollections.observableArrayList(); 
    	   for (int i = 0; i < Main.buildingList.size(); i++) {
    		   options.add(Main.buildingList.get(i).getName()); 
    	   }
    	   final ComboBox comboBox = new ComboBox (options); 
    	   
    	   Button button1 = new Button("OK"); 
    	   button1.setOnAction(e1 -> {
    		  
    		   int k = 0; 
    		   for (; k < Main.buildingList.size(); k++) {
    			   if (Main.buildingList.get(k).getName().equals(comboBox.getValue().toString()))
    				   break; 
    		   }
    		   Building b = Main.buildingList.get(k); 
    		   
    		   Stage popupwindow1 = new Stage(); 
        	   popupwindow1.initModality(Modality.APPLICATION_MODAL); 
        	   
        	   
        	   Label label2 = new Label("Which floor does the POI you want to edit belong to?"); 
        	   
        	   ObservableList<String> options1 = FXCollections.observableArrayList(); 
        	   for (int i = 0; i < b.getFloors().length; i++) {
        		   options1.add(b.getFloors()[i].getName()); 
        	   }
        	   final ComboBox cb2 = new ComboBox(options1); 
        	   Button button2 = new Button("OK"); 
        	   button2.setOnAction(e2 -> {
        		   int y = 0;
        		   for (; y < b.getFloors().length; y++) {
        			   if (b.getFloors()[y].getName().equals(cb2.getValue().toString())
        					   ) break; 
        		   }
        		   Floor f = b.getFloors()[y]; 
        		   
        		   Stage s3 = new Stage(); 
        		   s3.initModality(Modality.APPLICATION_MODAL); 
        		   s3.setTitle("Edit a POI"); 
        		   Label l = new Label("Which POI do you wish to edit?"); 
        		   ObservableList<String> op = FXCollections.observableArrayList(); 
        		   for (int m = 0; m < f.getBuiltInPOIs().length; m++) {
        			   if (f.getBuiltInPOIs()[m] == null) break; 
        			   op.add(f.getBuiltInPOIs()[m].getName()); 
        		   }
        		   final ComboBox cb3 = new ComboBox(op); 
        		   Button rm = new Button ("EDIT THIS POI"); 
        		   
        		   rm.setOnAction(ex -> {
        			   Stage last = new Stage(); 
        			   last.initModality(Modality.APPLICATION_MODAL);
        			   last.setTitle("Edit a POI");
        			   Label lk = new Label("Fill in the below fields: ");
        				Label poiName = new Label("New POI Name ");
        	 		  	TextField poiNameInput = new TextField();
        	 		  	Label poiDesc = new Label("New POI Description "); 
        	 		  	TextField poiDescInput = new TextField(); 
        	 		  	Label poiType = new Label("New POI Type"); 
        	 		  	TextField poiTypeInput = new TextField(); 
        	 		  	Label x_axis = new Label("New x-axis of the POI (integer)"); 
        	 		  	TextField x_axisInput = new TextField();  
        	 		  	Label y_axis = new Label("New y-axis of the POI (integer)"); 
        	 		  	TextField y_axisInput = new TextField(); 
        	 		  	Button okx = new Button("OK"); 
        	 		  	okx.setOnAction(ep -> {
        	 		  		int n = 0; 
             			   for (; n < f.getBuiltInPOIs().length; n++) {
             				   if (f.getBuiltInPOIs()[n].getName().equals(cb3.getValue().toString()))
             					   break; 
             			   }
             			   POI target = f.getBuiltInPOIs()[n]; 
             			   
             			   
             			   String name = poiNameInput.getText(); 
             			  String desc = poiDescInput.getText(); 
            			   
             			 String type = poiTypeInput.getText(); 
           			   int x_ax = Integer.parseInt(x_axisInput.getText());
           			int y_ax = Integer.parseInt(y_axisInput.getText());
           			
           			
           				dev.editPOI(target, name, desc, type, new int[] {x_ax, y_ax}
           						);
             			 
             			   
             			   
             			   
             			   s3.close();
             			   last.close(); 
             			   popupwindow1.close(); 
             			   popupwindow.close(); 
        	 		  	});
        	 		  	
        	 		  	VBox layoutlast = new VBox (20); 
        	 		  	/*
        	 		  	 *  Label lk = new Label("Fill in the below fields: ");
        				Label poiName = new Label("New POI Name ");
        	 		  	TextField poiNameInput = new TextField();
        	 		  	Label poiDesc = new Label("New POI Description "); 
        	 		  	TextField poiDescInput = new TextField(); 
        	 		  	Label poiType = new Label("New POI Type"); 
        	 		  	TextField poiTypeInput = new TextField(); 
        	 		  	Label x_axis = new Label("New x-axis of the POI (integer)"); 
        	 		  	TextField x_axisInput = new TextField();  
        	 		  	Label y_axis = new Label("New y-axis of the POI (integer)"); 
        	 		  	TextField y_axisInput = new TextField(); 
        	 		  	 */
        	 		  	layoutlast.getChildren().addAll(
        	 		  			lk, poiName, poiNameInput, poiDesc, poiDescInput, poiType, poiTypeInput,
        	 		  			x_axis, x_axisInput, y_axis, y_axisInput, okx
        	 		  			); 
        	 		  	layoutlast.setAlignment(Pos.CENTER); 
        	 		  	Scene scenelast = new Scene (layoutlast, 600, 600); 
        	 		  	last.setScene(scenelast); 
        	 		  	last.showAndWait(); 
        	 		  	
        	 		  	
        			   
        			   
        		   });
        		   
        		   VBox l3 = new VBox(10); 
        		   l3.getChildren().addAll(l, cb3, rm); 
        		   l3.setAlignment(Pos.CENTER); 
        		   Scene s4 = new Scene(l3, 300, 250); 
        		   s3.setScene(s4); 
        		   s3.showAndWait(); 
        		   
        		   
        		   
        	   }); 
        	   VBox layout2 = new VBox(10); 
        	   layout2.getChildren().addAll(label2, cb2, button2); 
        	   layout2.setAlignment(Pos.CENTER); 
        	   Scene scene1 = new Scene(layout2, 300, 250); 
        	   popupwindow1.setScene(scene1); 
        	   popupwindow1.showAndWait(); 
    		   
    	   }); 
    	   VBox layout = new VBox(10); 
    	   layout.getChildren().addAll(label1, comboBox, button1); 
    	   layout.setAlignment(Pos.CENTER); 
    	   Scene scene1 = new Scene(layout, 300, 250); 
    	   popupwindow.setScene(scene1); 
    	   popupwindow.showAndWait(); 
    	   
    	   
       });
       
       Button logoutButton = new Button("Save Changes and Exit to Login/Registration page"); 

       logoutButton.setOnAction(logEvent -> {
    	   
   		logEvent.consume();
   		Stage stage = (Stage) ((Node) logEvent.getSource()).getScene().getWindow();
       	try {
				Main.logout(stage);
			} catch (Exception e) {
			}
       });  
   
       addBuilding.setLayoutX(250);
       addBuilding.setLayoutY(200);
       root.getChildren().add(addBuilding);
       removeBuilding.setLayoutX(600);
       removeBuilding.setLayoutY(200);
       root.getChildren().add(removeBuilding);
       grid.setLayoutX(250);
       grid.setLayoutY(100);
       
       addFloor.setLayoutX(250);
       addFloor.setLayoutY(240);
       root.getChildren().add(addFloor); 
       
       removeFloor.setLayoutX(600);
       removeFloor.setLayoutY(240);
       root.getChildren().add(removeFloor); 
       
       addPOI.setLayoutX(250);
       addPOI.setLayoutY(280);
       root.getChildren().add(addPOI); 
       
       removePOI.setLayoutX(600);
       removePOI.setLayoutY(280);
       root.getChildren().add(removePOI); 
       
       editPOI.setLayoutX(250);
       editPOI.setLayoutY(320);
       root.getChildren().add(editPOI);
       
       logoutButton.setLayoutX(600);
       logoutButton.setLayoutY(320);
       root.getChildren().add(logoutButton); 
       
       root.getChildren().add(grid);
       primaryStage.setScene(new Scene(root, 950, 500));
       primaryStage.show();
   }
   
}