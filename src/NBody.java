import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class NBody {

	public static double readRadius(String filename){ //reads radius from text file
		double radius = 0;
		try {
			File f = new File(filename); 
			Scanner sc = new Scanner(f);
			sc.nextDouble();
			radius = sc.nextDouble(); 
			sc.close();
		}
		catch (FileNotFoundException e) { // stops the program if file not found
			e.printStackTrace();
		}
		return radius;
	}
	public static Planet[] readPlanets(String filename){ //extracts array of Planets from text file
		ArrayList<Planet> pArrayList = new ArrayList<Planet>(); //creates an ArrayList of Planets

		try { 
			File f = new File(filename); 
			Scanner sc = new Scanner(f);
			sc.nextDouble();
			sc.nextDouble();
			while (sc.hasNextDouble()){ //while the scanner still has data to go through
				Planet p = new Planet(0,0,0,0,0,""); //initializes a planet
				p.myXPos = sc.nextDouble(); //fields of the planet are defined by text file
				p.myYPos = sc.nextDouble();
				p.myXVel = sc.nextDouble();
				p.myYVel = sc.nextDouble();
				p.myMass = sc.nextDouble();
				p.myFileName = sc.next(); 
				pArrayList.add(p); // adds the planet to the ArrayList
			}
			sc.close();
		}
		catch (FileNotFoundException e) { // stops the program if file not found
			e.printStackTrace();
		}
		Planet [] pArray = pArrayList.toArray(new Planet[pArrayList.size()]); // converts ArrayList to array
		return pArray;
	}
	public static void main (String[] args){ //creates animation and prints final state
//		int count = 1;
//		String[] mystrarray = {"data/kaleidoscope.txt", "data/3body.txt", "data/hypnosis.txt"};
		double T = 157788000.0; //total time (in seconds)
		double dt = 25000.0; //time increment (in seconds)
		String filename = "data/planets.txt";
//		String filename = mystrarray[count];
		Planet[] pArray = readPlanets(filename); //creates array of planets
		double radius = readRadius(filename); //reads radius
		StdDraw.setScale(-radius, radius);
		StdDraw.square(0, 0, radius);
		StdDraw.picture(0, 0, "images/starfield.jpg"); //draws background
		for (int c=0; c<pArray.length; c++){
			pArray[c].draw(pArray[c]); //draws initial state of planets
		}

		for (double time =0; time<T; time+=dt){//loops through time by increments dt until T reached
			double xForces[] = new double[pArray.length];
			double yForces[] = new double[pArray.length];
			for (int k=0; k<pArray.length; k++){ //calculates force on each planet
				xForces[k] = pArray[k].calcNetForceExertedByX(pArray);
				yForces[k] = pArray[k].calcNetForceExertedByY(pArray);
			}
			for (int i = 0; i<pArray.length; i++){
				pArray[i].update(dt, xForces[i],yForces[i]); //updates planet positions
			}
			StdDraw.square(0, 0, radius);
			StdDraw.picture(0, 0, "images/starfield.jpg"); //draws background
			for (int c=0; c<pArray.length; c++){ 
				pArray[c].draw(pArray[c]); //draws planets at current time
			}
			StdDraw.show(10);
		}

		System.out.printf("%d\n", pArray.length); //prints number of planets
		System.out.printf("%.2e\n", radius); //prints radius of the universe
		for (int k = 0; k < pArray.length; k++) { //prints final state of all planets
			System.out.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
					pArray[k].myXPos, pArray[k].myYPos, 
					pArray[k].myXVel, pArray[k].myYVel, 
					pArray[k].myMass, pArray[k].myFileName);
		}
	}
}
