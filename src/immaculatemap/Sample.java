package immaculatemap;

import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Sample {

	
	public static void main (String[] args) {
		
		Main.initGson(); 
		Main.loadBuildings(); 
		Developer d = new Developer(); 
		Building b = new Building("Building B", null, true); 
		d.addBuilding(null);
		Floor f = new Floor(); 
		  f.setName("Building Name"); 
		  f.setMapImage("");
		  f.setAccessibilityLayer("");
		  String building_name = "Building B"; 
		POI poi = new POI(); 
		poi.setFloor(f);
		poi.setName("FLOOR F");
		d.addFloor(null);
		Main.saveBuildings(); 
		return;
		/*
		Floor f1 = new Floor();
		f1.setName("NAME HERE");
		Floor[] f = new Floor[] {f1}; 
		Building b = new Building("thisB", f, true); 
		Developer d = new Developer(); 
		
		
		POI p = new POI();
		p.setName("some name");
		
		f[0].setBuiltInPOIs(new POI[]{p,p,p});
		
		
		Main.loadBuildings(); 
		d.addBuilding(b);
		*/
	}
	
	
}

