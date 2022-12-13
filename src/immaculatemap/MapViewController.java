package immaculatemap;

/**
 * Controller for map view UI, responsible for both building list panel and pannable map.
 *
 * @version 1.0.0
 * @author Scott Gilhuly (sgilhul@uwo.ca)
 * @author Samantha Romero (sromero5@uwo.ca)
 * @author Connor McGoey (cmcgoey3@uwo.ca)
 */

import javafx.fxml.FXML;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Timer;

import javafx.event.*;
//import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import javafx.scene.Node;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @author Scott Gilhuly
 * @author Connor McGoey
 *
 */
public class MapViewController {

	/** Scrollpane that houses building list */
	@FXML
	private ScrollPane buildingPane;
	/** Building list itself, able to change size based on contents */
	private GridPane buildings;
	/** Pane containing map */
	@FXML
	private Pane mapPane;
	/** ImageView for displaying the map */
	@FXML
	private ImageView mapImageView;
	
	/** Panel for naming custom PoIs */
	@FXML
	private Pane poiPanel;
	
	/** Banner indicating cloud saving */
	@FXML
	private Label cloudBanner;
	
	/**Logout button*/
	@FXML
	private Button logoutButton;
	/**Cloud save button*/
	@FXML
	private Button cloudsaveButton;
	/**Accessibility layer button*/
	@FXML
	private Button accessibilityButton;
	/**Help button*/
	@FXML
	private Button helpButton;
	/** AddPOI Button */
	@FXML
	private Button addPOIbtn;
	/** ADDPOI Name Field */
	@FXML
	private TextField POINameField;
	/** ADDPOI Description Field */
	@FXML
	private TextField POIDescField;
	/** ADDPOI Type Field */
	@FXML
	private TextField POITypeField;
	/** Cancel add POI Button */
	@FXML
	private Button cancelAddPOIbtn;
	/** Cloud Confirmation Popup Pane */
	@FXML
	private Pane cloudPopup;
	/** Cloud Confirmation Popup Button */
	@FXML
	private Button cloudOkBtn;
	/** Cloud Popup Label */
	@FXML
	private Label CloudPopupLabel;
	/** Search POI Text*/
	@FXML
	private TextField searchPOIText;
	/** Search POI Floor*/
	@FXML
	private TextField searchPOIFloor;
	/** Search MC Building Button*/
	@FXML
	private Button searchMC;
	/** Search VAC Building Button*/
	@FXML
	private Button searchVAC;
	/** Search WH Building Button*/
	@FXML
	private Button searchWH;

	private Floor currentFloor;
	private Pane poiHolder = null;
	
	private boolean isAccessible;
	
	/** Nested class for controlling floor list dropdown. */
    private class menuExpanderHandler implements EventHandler<ActionEvent> {
    	
    	MapViewController controller;
    	
    	public menuExpanderHandler(MapViewController controller) {
    		this.controller = controller;
    	}
    	
    	/**
    	 * Event logic for expanding menu buttons.
		 */
		public void handle(ActionEvent event) {
			 	
			Button button = (Button) event.getSource();
			Pane holder = (Pane) button.getParent();
			//TODO access buildling name
			
			if (holder.getHeight() == 50.0) { //If the dropdown is closed...
				int desiredHeight = 50 * holder.getChildren().size();
				
				holder.setPrefHeight(desiredHeight); //adjust size from 50 to 50*(floors + 1)
				holder.setClip(new Rectangle(200,desiredHeight)); //adjust clipping to match available space
			} else {
				holder.setPrefHeight(50.0); //adjust size to 50, closing it
				holder.setClip(new Rectangle(200,50)); //adjust clipping to keep building list inside
			}
		}
    }    
    
    /** Nested class for controlling map selection */
    private class floorSelectHandler implements EventHandler<ActionEvent> {
    	
    	MapViewController controller;
    	
    	public floorSelectHandler(MapViewController controller) {
    		this.controller = controller;
    	}
    	
    	/**
    	 * Event logic for map selection
    	 */
    	public void handle(ActionEvent event) {
    		
    		controller.searchPOIText.setText("");
    		controller.searchPOIFloor.setText("");
    		
    		if (poiHolder != null) {
    			mapPane.getChildren().remove(poiHolder);
    		}
    		poiHolder = new Pane();
    		poiHolder.setPrefSize(mapPane.getPrefWidth(), mapPane.getPrefHeight());
    		poiHolder.setViewOrder(1.0);
    		poiHolder.setOnMouseClicked(mapClicked);
    		mapPane.getChildren().add(poiHolder);    		
    		
    		FloorButton button = (FloorButton) event.getSource();
    		controller.currentFloor = button.getFloor();
    		
    		POI[] floorPOIs = controller.currentFloor.getBuiltInPOIs();
    		
    		User mainUser = Main.currentUser;
    		
    		String currentFloorName = controller.currentFloor.getName();
    		String currentFloorBuilding = controller.currentFloor.getBuilding();
    		
    		if(Main.searched == false) {
    			Main.searched = true;
	    		if(!Main.searchedItem.equals("")) {
	    			Boolean found = false;
	    			for(int i = 0; i < floorPOIs.length; i++) {
	    				if((floorPOIs[i] != null) && (floorPOIs[i].name.equals(Main.searchedItem))) {
	    					found = true;
	    					int[] location = floorPOIs[i].getLocation();
	    					Circle poiIcon = new Circle(14);
	    					poiIcon.setLayoutX(location[0]);
	    					poiIcon.setLayoutY(location[1]);
	    					poiIcon.setFill(Color.RED);
	    					
	    					poiHolder.getChildren().add(poiIcon);
	    					
	    					poiIcon.setOnMouseEntered(new EventHandler<MouseEvent>() {
	    						public void handle(MouseEvent event) {
	    							poiHolder.getChildren().remove(poiIcon);
	    						}
	    					});
	    				}
	    			} for (int i = 0; i < mainUser.userPOIs.length; i++) {
	    				if(mainUser.userPOIs[i] != null) {
	    					if(mainUser.userPOIs[i].floor.getName().equals(currentFloorName)) {
	    						if(mainUser.userPOIs[i].floor.getBuilding().equals(currentFloorBuilding)) {
	    							if(mainUser.userPOIs[i].getName().equals(Main.searchedItem)) {
	    								found = true;
		    							int[] location = mainUser.userPOIs[i].getLocation();
		    							Circle poiIcon = new Circle(14);
		    	    					poiIcon.setLayoutX(location[0]);
		    	    					poiIcon.setLayoutY(location[1]);
		    	    					poiIcon.setFill(Color.RED);
		    	    					
		    	    					poiHolder.getChildren().add(poiIcon);
		    	    					
		    	    					poiIcon.setOnMouseEntered(new EventHandler<MouseEvent>() {
		    	    						public void handle(MouseEvent event) {
		    	    							poiHolder.getChildren().remove(poiIcon);
		    	    						}
		    	    					});
	    							}
	    						}
	    					}
	    				}
	    			}
	    			controller.cloudOkBtn.setOnAction((new EventHandler<ActionEvent>() {
	    				public void handle(ActionEvent event) {
	    					controller.cloudPopup.setVisible(false);
            			}
	    			}));
	    		
	    			if(!found) {
		    			controller.cloudPopup.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK,null,null)));
		    			controller.CloudPopupLabel.setText("NO SUCH POI EXISTS ON THIS LEVEL");
		    			controller.cloudPopup.setVisible(true);
	    			}
	    		}
	    		
    		}
    		

    		
    		
    		    		
    		// set map image to floor's map image
    		String imagePath = System.getProperty("user.dir");
    		imagePath = imagePath + "/MapImages/" + controller.currentFloor.getMapImage();
    		
    		//System.out.println(imagePath);
    		
    		try {
    			FileInputStream inputStream = new FileInputStream(imagePath);
        		Image image = new Image(inputStream);
        		
        		
        		controller.mapImageView.setImage(image);
    		} catch (FileNotFoundException e) {
    			System.out.println("Map not found.\n" + e);
    		}
    		
    		isAccessible = false;
            accessibilityButton.setOnAction(new EventHandler<ActionEvent>() {
            		public void handle(ActionEvent event) {
            			if (isAccessible) {
            				try {
            					// set map image to floor's map image
            		    		String imagePath = System.getProperty("user.dir");
            		    		imagePath = imagePath + "/MapImages/" + controller.currentFloor.getMapImage();
            					
            	    			FileInputStream inputStream = new FileInputStream(imagePath);
            	        		Image image = new Image(inputStream);
            	        		
            	        		
            	        		controller.mapImageView.setImage(image);
            	        		isAccessible = false;
            	    		} catch (FileNotFoundException e) {
            	    			System.out.println("Map not found.\n" + e);
            	    		}
            			} else {
            				try {
            					// set map image to floor's map image
            		    		String imagePath = System.getProperty("user.dir");
            		    		imagePath = imagePath + "/MapImages/" + controller.currentFloor.getAccessibilityLayer();
            					
            	    			FileInputStream inputStream = new FileInputStream(imagePath);
            	        		Image image = new Image(inputStream);
            	        		
            	        		
            	        		controller.mapImageView.setImage(image);
            	        		isAccessible = true;
            	    		} catch (FileNotFoundException e) {
            	    			System.out.println("Map not found.\n" + e);
            	    		}
            			}
            		}
            });
    		
    		//populate built-in pois
	    	if (controller.currentFloor.getBuiltInPOIs() != null) {
	    		for (int i = 0; i < controller.currentFloor.getBuiltInPOIs().length; i++) {
	    			if (controller.currentFloor.getBuiltInPOIs()[i] == null) continue;
	    			makePoiIcon(controller.currentFloor.getBuiltInPOIs()[i], false);	    			
	    		}
	    	}
	    	
	    	POI[] userPOIs = Main.currentUser.getUserPOIs();
	    	if (userPOIs.length > 0) {
	    		for (int i = 0; i < userPOIs.length; i++) {
	    			if (userPOIs[i] == null || !userPOIs[i].floor.equals(controller.currentFloor)) continue;
	    			makePoiIcon(userPOIs[i], true);   			
	    		}
	    	}
    	}
    	
    	/** Helper for making those funny little dots */
    	private void makePoiIcon(POI poi, boolean isUser) {
			Circle poiIcon = new Circle(5.0);
			
			boolean fav = false;
            for (int i = 0; i < Main.currentUser.getFavourites().length; i++) {
            	POI p = Main.currentUser.getFavourites()[i];
            	if (p != null && p.getName().equals(poi.getName()) &&
            			p.getFloor().equals(poi.getFloor())) {
            		fav = true;
            		break;
            	}
            }
            if (fav) {
    			poiIcon.setFill(Color.BISQUE);
            } else if (isUser) {
            	poiIcon.setFill(Color.CORNFLOWERBLUE);
            }
			
            poiIcon.setStroke(Color.BLACK);
			int[] position = poi.getLocation();
			poiIcon.setLayoutX(position[0]);
			poiIcon.setLayoutY(position[1]);
			
			poiIcon.setOnMouseClicked(clickevent -> {
				clickevent.consume();
				ContextMenu contextMenu = new ContextMenu();
	            contextMenu.setAutoHide(true);
	            
	            //Get POI information
	            MenuItem menuItem1 = new MenuItem(poi.getName());
	            //TODO click on name for more information?
	            contextMenu.getItems().add(menuItem1);
	            
	            //Change favourite status
	            boolean canFav = false;
	            boolean isFav = false;
	            for (int i = 0; i < Main.currentUser.getFavourites().length; i++) {
	            	POI p = Main.currentUser.getFavourites()[i];
	            	if (p == null) {
	            		canFav = true;
	            	} else if (p.getName().equals(poi.getName()) && p.getFloor().equals(poi.getFloor())) {
	            		isFav = true;
	            		break;
	            	}
	            }
	            
	            if (isFav) {
	            	MenuItem menuItem2 = new MenuItem("Unfavourite");
	            	menuItem2.setOnAction(event -> {
	            		Main.currentUser.removeFavourite(poi.getName());
	            		controller.initialize();
	            	});
	            	contextMenu.getItems().add(menuItem2);
	            	
	            } else if (canFav) {
	            	MenuItem menuItem2 = new MenuItem("Favourite");
	            	menuItem2.setOnAction(event -> {
	            		Main.currentUser.addFavourite(poi);
	            		controller.initialize();
	            	});
		            contextMenu.getItems().add(menuItem2);
	            }
	            
	            
	            //Delete, if it's a user poi
	            if (isUser) {
	            	MenuItem menuItem3 = new MenuItem("Delete");
	            	menuItem3.setOnAction(event -> {
	            		Main.currentUser.removePOI(poi.getName());
	            		poiHolder.getChildren().remove(poiIcon);
	            	});
		            contextMenu.getItems().add(menuItem3);
	            }

	            //Does nothing
	            MenuItem menuItem4 = new MenuItem("Cancel");
	            contextMenu.getItems().add(menuItem4);
	                        
	            contextMenu.show(controller.mapImageView.getParent(),
            		clickevent.getScreenX(),
	            	clickevent.getScreenY()
	            );
			});
			
			//Makes them a little more alive
			poiIcon.setOnMouseEntered(event -> {
				poiIcon.setRadius(8.0);
			});
			poiIcon.setOnMouseExited(event -> {
				poiIcon.setRadius(5.0);
			});
			
			poiHolder.getChildren().add(poiIcon);
    	}
    }
    
    /**
     * Nested class for implementing a save to cloud handler.
     * 
     * @author Connor McGoey
     *
     */
    private class saveToCloudHandler implements EventHandler<ActionEvent> {
	    
    	MapViewController controller;
    	
    	public saveToCloudHandler(MapViewController controller) {
    		this.controller = controller;
    	}
    	
    	public void handle(ActionEvent event) {
    		/**
    		 * Logic for controlling the necessary pop up window with a success or fail.
    		 */
    		controller.cloudOkBtn.setOnAction((new EventHandler<ActionEvent>() {
            		public void handle(ActionEvent event) {
            			controller.cloudPopup.setVisible(false);
            		}
            }));
    		User mainUser = Main.currentUser;
    		if(!LoginController.netIsAvailable()) {
    			controller.cloudPopup.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK,null,null)));
    			controller.CloudPopupLabel.setText("YOU ARE NOT CONNECTED TO THE INTERNET");
    			controller.cloudPopup.setVisible(true);
    			return;
    		}
    		if(Main.currentUser.isCloudEnabled) {
	    		Main.currentUser.saveToCloud();
	    		controller.cloudPopup.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN,null,null)));
	    		controller.CloudPopupLabel.setText("Information Successfully Uploaded To Cloud");
	    		controller.cloudPopup.setVisible(true);
    		} else {
    			controller.cloudPopup.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK,null,null)));
    			controller.CloudPopupLabel.setText("YOUR ACCOUNT IS NOT CLOUD-ENABLED");
    			controller.cloudPopup.setVisible(true);
    		}
	    }
    }
    
    /**
     * Nested class for handling search within Middlesex College
     * 
     * @author Connor McGoey
     *
     */
    private class searchMCHandler implements EventHandler<ActionEvent> {
	    
    	MapViewController controller;
    	
    	public searchMCHandler(MapViewController controller) {
    		this.controller = controller;
    	}
    	
    	public void handle(ActionEvent event) {
    		
    		Main.searched = false;
    		
    		controller.cloudOkBtn.setOnAction((new EventHandler<ActionEvent>() {
        		public void handle(ActionEvent event) {
        			controller.cloudPopup.setVisible(false);
        		}
    		}));
    		
            Floor Floors[] = Main.buildingList.get(0).getFloors();
            int floorNumber = -1;
            try {
            	floorNumber = Integer.parseInt(controller.searchPOIFloor.getText());
            } catch(NumberFormatException e) {
            	controller.cloudPopup.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK,null,null)));
    			controller.CloudPopupLabel.setText("INVALID NUMBER");
    			controller.cloudPopup.setVisible(true);
            }
            if ((floorNumber > 5) || (floorNumber < 1)) {
            	controller.cloudPopup.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK,null,null)));
    			controller.CloudPopupLabel.setText("INVALID FLOOR NUMBER (CHOOSE BETWEEN 1-5)");
    			controller.cloudPopup.setVisible(true);
            } else {
	    		FloorButton floorButton = new FloorButton("search", Floors[floorNumber - 1]);
	    		floorButton.setVisible(false);
	    		Main.searchedItem = searchPOIText.getText();
	    		floorButton.setOnAction(floorSelect);
	    		floorButton.fire();
            }
    	}
    }
    
    /**
     * Nested class for handling search in Westminster Hall
     * 
     * @author Connor McGoey
     *
     */
    private class searchWHHandler implements EventHandler<ActionEvent> {
	    
    	MapViewController controller;
    	
    	public searchWHHandler(MapViewController controller) {
    		this.controller = controller;
    	}
    	
    	public void handle(ActionEvent event) {
    		
    		Main.searched = false;
    		
    		controller.cloudOkBtn.setOnAction((new EventHandler<ActionEvent>() {
        		public void handle(ActionEvent event) {
        			controller.cloudPopup.setVisible(false);
        		}
    		}));
    		
            Floor Floors[] = Main.buildingList.get(2).getFloors();
            int floorNumber = -1;
            try {
            	floorNumber = Integer.parseInt(controller.searchPOIFloor.getText());
            } catch(NumberFormatException e) {
            	controller.cloudPopup.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK,null,null)));
    			controller.CloudPopupLabel.setText("INVALID NUMBER");
    			controller.cloudPopup.setVisible(true);
            }
            if ((floorNumber > 4) || (floorNumber < 1)) {
            	controller.cloudPopup.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK,null,null)));
    			controller.CloudPopupLabel.setText("INVALID FLOOR NUMBER (CHOOSE BETWEEN 1-4)");
    			controller.cloudPopup.setVisible(true);
            } else {
	    		FloorButton floorButton = new FloorButton("search", Floors[floorNumber - 1]);
	    		floorButton.setVisible(false);
	    		Main.searchedItem = searchPOIText.getText();
	    		floorButton.setOnAction(floorSelect);
	    		floorButton.fire();
            }
    	}
    }
    
    /**
     * Nested class for handling search in the Visual Arts Center
     * 
     * @author Connor McGoey
     *
     */
    private class searchVACHandler implements EventHandler<ActionEvent> {
	    
    	MapViewController controller;
    	
    	public searchVACHandler(MapViewController controller) {
    		this.controller = controller;
    	}
    	
    	public void handle(ActionEvent event) {
    		
    		Main.searched = false;
    		
    		controller.cloudOkBtn.setOnAction((new EventHandler<ActionEvent>() {
        		public void handle(ActionEvent event) {
        			controller.cloudPopup.setVisible(false);
        		}
    		}));
    		
            Floor Floors[] = Main.buildingList.get(1).getFloors();
            int floorNumber = -1;
            try {
            	floorNumber = Integer.parseInt(controller.searchPOIFloor.getText());
            } catch(NumberFormatException e) {
            	controller.cloudPopup.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK,null,null)));
    			controller.CloudPopupLabel.setText("INVALID NUMBER");
    			controller.cloudPopup.setVisible(true);
            }
            if ((floorNumber > 3) || (floorNumber < 1)) {
            	controller.cloudPopup.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK,null,null)));
    			controller.CloudPopupLabel.setText("INVALID FLOOR NUMBER (CHOOSE BETWEEN 1-3)");
    			controller.cloudPopup.setVisible(true);
            } else {
	    		FloorButton floorButton = new FloorButton("search", Floors[floorNumber - 1]);
	    		floorButton.setVisible(false);
	    		Main.searchedItem = searchPOIText.getText();
	    		floorButton.setOnAction(floorSelect);
	    		floorButton.fire();
            }
    	}
    }
    
    
    /** Nested class for controlling adding POI */
    private class mapClickHandler implements EventHandler<MouseEvent> {

    	MapViewController controller;
    	
    	public mapClickHandler(MapViewController controller) {
    		this.controller = controller;
    	}
    	
        /**
         * Event logic for map click to add POI
         */
        public void handle(MouseEvent event) {
        	//System.out.println("Clicked!");
        	
        	controller.POINameField.setPromptText("POI Name");
    		controller.POIDescField.setPromptText("POI Description");
    		controller.POITypeField.setPromptText("POI Type");
        
        	int xValue = (int) event.getX();
        	int yValue = (int) event.getY();
        	int[] location = {xValue, yValue};

            ContextMenu contextMenu = new ContextMenu();
            contextMenu.setAutoHide(true);
            
            MenuItem menuItem1 = new MenuItem("Add POI"); //option to add poi
            menuItem1.setOnAction(new EventHandler<ActionEvent>() {
            	public void handle(ActionEvent event) {
            		controller.poiPanel.setVisible(true);
            		
            		
            		controller.cancelAddPOIbtn.setOnAction(new EventHandler<ActionEvent>() {
            			public void handle(ActionEvent event) {
            				controller.poiPanel.setVisible(false);
            			}
            		});
            		
            		controller.cloudOkBtn.setOnAction((new EventHandler<ActionEvent>() {
                		public void handle(ActionEvent event) {
                			controller.cloudPopup.setVisible(false);
                		}
            		}));
            		
            		controller.addPOIbtn.setOnAction(new EventHandler<ActionEvent>() {
            			public void handle(ActionEvent event) {
            				String name = controller.POINameField.getText();
            				String description = controller.POIDescField.getText();
            				String type = controller.POITypeField.getText();
            				
            				controller.POINameField.setText("");
            				controller.POIDescField.setText("");
            				controller.POITypeField.setText("");
            				
            				if(Main.currentUser.addPOI(name, description, type, location, controller.currentFloor)) {
            					controller.poiPanel.setVisible(false);
            					String successString = "NEW POI '" + name + "' ADDED!";
            					controller.cloudPopup.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN,null,null)));
            		    		controller.CloudPopupLabel.setText(successString);
            		    		controller.cloudPopup.setVisible(true);
            				} else {
            					controller.poiPanel.setVisible(false);
            					controller.cloudPopup.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK,null,null)));
            					String failedString = "UNABLE TO ADD POI '" + name + "'";
            					controller.CloudPopupLabel.setText(failedString);
            	    			controller.cloudPopup.setVisible(true);
            				};
            			}
            		});
            	}
            });
            
            
            MenuItem menuItem2 = new MenuItem("Cancel"); //option to cancel

            contextMenu.getItems().add(menuItem1);
            contextMenu.getItems().add(menuItem2);
                        
            contextMenu.show(controller.mapImageView.getParent(),
        		event.getScreenX(),
        		event.getScreenY()
            );
            
        }
    }
    
    /** Drop down event handler is declared here */
    private menuExpanderHandler menuExpander;
	/**	Map selection event handler is declared here */
    private floorSelectHandler floorSelect;
    /** Map click event handler is declared here */
    private mapClickHandler mapClicked;
    /** Save to cloud event handler */
    private saveToCloudHandler saveToCloud;
    /** Search MC event handler */
    private searchMCHandler searchMChandler;
    /** Search VAC event handler */
    private searchVACHandler searchVAChandler;
    /** Search WH event handler */
    private searchWHHandler searchWHhandler;
    
    //public void 
    
    /**
     * Code ran when the controller is started.
     * Populates gridpane with buttons for each building.
     */
    public void initialize() {
    	
    	
    	
        // Initialize event handlers
        menuExpander = new menuExpanderHandler(this);
        floorSelect = new floorSelectHandler(this);
        mapClicked = new mapClickHandler(this);
        saveToCloud = new saveToCloudHandler(this);
        searchMChandler = new searchMCHandler(this);
        searchVAChandler = new searchVACHandler(this);
        searchWHhandler = new searchWHHandler(this);
        
        searchMC.setOnAction(searchMChandler);
        searchVAC.setOnAction(searchVAChandler);
        searchWH.setOnAction(searchWHhandler);
        
        cloudsaveButton.setOnAction(saveToCloud);
        
        mapImageView.setOnMouseClicked(mapClicked);
        
        logoutButton.setOnAction(logEvent -> {
    		logEvent.consume();
    		Stage stage = (Stage) ((Node) logEvent.getSource()).getScene().getWindow();
        	try {
				Main.logout(stage);
			} catch (Exception e) {
			}
        });    

    	buildings = new GridPane();
    	buildingPane.setContent(buildings);    	
    	//System.out.println(Main.buildingList);
    	//ArrayList<Building> buildingList = Main.buildingList;
    	
    	poiPanel.setBackground(new Background(new BackgroundFill(Color.gray(0.75),null,null)));
    	
    	cloudBanner.setBackground(new Background(new BackgroundFill(Color.LEMONCHIFFON,null,null)));

    	Pane favourites = new Pane();
    	favourites.setPrefHeight(50.0);
    	favourites.setPrefWidth(200.0);
    	favourites.setBackground(new Background(new BackgroundFill(Color.YELLOW,null,null))); //TODO make this pretty -- algorithmically determine building color???
    	favourites.setClip(new Rectangle(200,50));
    	
    	Button favCategory = new Button("Favourites");
    	favCategory.setPrefHeight(50.0);
    	favCategory.setPrefWidth(200.0);
    	favCategory.setOnAction(menuExpander); //set control for button click to expand
    	favourites.getChildren().add(favCategory);
    	
    	buildings.add(favourites, 0, 0);
    	
    	
    	for (int i = 0; i < Main.buildingList.size(); i++) {
    		Building build = Main.buildingList.get(i);
    		Pane buttons = new Pane(); //create holder for space
    		buttons.setPrefHeight(50.0);
    		buttons.setPrefWidth(200.0);
    		buttons.setBackground(new Background(new BackgroundFill(Color.gray(0.5),null,null))); //TODO make this pretty -- algorithmically determine building color???
    		buttons.setClip(new Rectangle(200,50));
    		
    		Button button1 = new Button(build.getName());
    		button1.setPrefSize(200, 50);
    		buttons.getChildren().add(button1);    		
    		button1.setOnAction(menuExpander); //set control for button click to expand
  
    		Floor buildingFloors[] = build.getFloors();
    		for (int j = 0; j < buildingFloors.length; j++) {
    			FloorButton floorButton = new FloorButton(buildingFloors[j].getName(), buildingFloors[j]);
    			floorButton.setLayoutY(50.0 * (j+1));
    			floorButton.setLayoutX(50);
    			floorButton.setPrefSize(150, 50);
    			floorButton.setOnAction(floorSelect);
        		buttons.getChildren().add(floorButton);
    		}
    		
    		buildings.add(buttons, 0, i+1); // adds this 'building' to the list
    	}
    	
    	//Compensates for favourites being a simple array with potential null positions
    	int offset = -1;
    	
    	POI[] userFaves = Main.currentUser.getFavourites();
    	for (int i = 0; i < userFaves.length; i++) { //access length of user favourites
    		if (userFaves[i] == null) {offset++; continue;}
    		
			FloorButton floorButton = new FloorButton(userFaves[i].getName(), userFaves[i].getFloor()); // new subclass JUST for favourite PoIs?
			floorButton.setLayoutY(50.0 * (i-offset)); //put this below existing buttons but no gaps
			floorButton.setLayoutX(50);
			floorButton.setPrefSize(150, 50);
			floorButton.setOnAction(floorSelect);
    		favourites.getChildren().add(floorButton);
		}
    }
}
