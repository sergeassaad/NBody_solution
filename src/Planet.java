
public class Planet {
	double myXPos;
	double myYPos;
	double myXVel;
	double myYVel;
	double myMass;
	String myFileName;

	public Planet(double xP, double yP, double xV,double yV, double m, String img){
		myXPos = xP;
		myYPos = yP;
		myXVel = xV;
		myYVel = yV;
		myMass = m;
		myFileName = img;
	}

	public Planet(Planet p){
		myXPos = p.myXPos;
		myYPos = p.myYPos;
		myXVel = p.myXVel;
		myYVel = p.myYVel;
		myMass = p.myMass;
		myFileName = p.myFileName;
	}

	public double calcDistance(Planet planet){ // calculates distance between 2 planets 
		double dx = planet.myXPos - myXPos;
		double dy = planet.myYPos - myYPos;
		double distance = Math.sqrt(dx*dx+dy*dy);
		return distance;
	}
	public double calcForceExertedBy(Planet p){ // calculates total force exerted by a planet on another one
		double rsq = Math.pow(this.calcDistance(p),2);
		double Force = 6.67*Math.pow(10, -11)*myMass*p.myMass/rsq;
		return Force;
	}
	public double calcForceExertedByX(Planet p){ // calculates x-component of the force
		double dx = p.myXPos - myXPos;
		double Force = this.calcForceExertedBy(p);
		double Fx = Force * dx/(this.calcDistance(p));
		return Fx;
	}
	public double calcForceExertedByY(Planet p){ // calculates y-component of the force
		double dy = p.myYPos - myYPos;
		double Force = this.calcForceExertedBy(p);
		double Fy = Force * dy/(this.calcDistance(p));
		return Fy;
	}
	
	public double calcNetForceExertedByX(Planet[] pArray){ //calculates sum of x-forces on a planet
		double SumForceX = 0;
		for (int i=0; i<pArray.length; i++){
			if (! pArray[i].equals(this)){
				SumForceX = SumForceX + this.calcForceExertedByX(pArray[i]);
			}
		}
		return SumForceX;
	}
	
	public double calcNetForceExertedByY(Planet[] pArray){ //calculates sum of y-forces on a planet
		double SumForceY = 0;
		for (int i=0; i<pArray.length; i++){
			if (! pArray[i].equals(this)){
				SumForceY = SumForceY + this.calcForceExertedByY(pArray[i]);
			}
		}
		return SumForceY;
	}
	public void update(double seconds, double xforce, double yforce){ //updates acceleration, velocity, position of planet
		double ax = xforce/myMass;
		double ay = yforce/myMass;
		myXVel = myXVel + ax*seconds;
		myYVel = myYVel + ay*seconds;
		myXPos = myXPos + myXVel*seconds;
		myYPos = myYPos + myYVel*seconds;
	}
	public void draw(Planet p){ // draws the planet at its current position
		double xP = p.myXPos;
		double yP = p.myYPos;
		String filename = p.myFileName;
		filename = "images/"+filename;
		StdDraw.picture(xP,yP,filename);
	}

}
