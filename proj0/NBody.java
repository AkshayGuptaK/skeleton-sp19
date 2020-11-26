public class NBody {
    public static double readRadius(String filename) {
	In in = new In(filename);
	in.readInt();
	return in.readDouble();
    }

    public static Body[] readBodies(String filename) {
	In in = new In(filename);
	int numberOfBodies = in.readInt();
	in.readDouble();
	Body[] bodies = new Body[numberOfBodies];

	for(int i = 0; i < numberOfBodies; i++) {
	    double xP = in.readDouble();
	    double yP = in.readDouble();
	    double xV = in.readDouble();
	    double yV = in.readDouble();
	    double m = in.readDouble();
	    String img = in.readString();
	    bodies[i] = new Body(xP, yP, xV, yV, m, img);
	}

	return bodies;
    }

    public static void main (String[] args) {
	double T = Double.parseDouble(args[0]);
	double dt = Double.parseDouble(args[1]);
	String filename = args[2];
	double universeRadius = readRadius(filename);
	Body[] bodies = readBodies(filename);

	StdDraw.setScale(-universeRadius, universeRadius);
	StdDraw.clear();
	StdDraw.picture(0, 0, "images/starfield.jpg");

	for (Body body : bodies) {
	    body.draw();
	}

	StdDraw.enableDoubleBuffering();
	double time = 0;
	int numberOfBodies = bodies.length;
	
	while (time < T) {
	    double[] xForces = new double[numberOfBodies];
	    double[] yForces = new double[numberOfBodies];

	    for (int i = 0; i < numberOfBodies; i++) {
		xForces[i] = bodies[i].calcNetForceExertedByX(bodies);
		yForces[i] = bodies[i].calcNetForceExertedByY(bodies);
	    }

	    for (int i = 0; i < numberOfBodies; i++) {
		bodies[i].update(dt, xForces[i], yForces[i]);
	    }

	    StdDraw.picture(0, 0, "images/starfield.jpg");
	    for (Body body : bodies) {
		body.draw();
	    }
	    StdDraw.show();
	    StdDraw.pause(10);
	    time += dt;
	}
    }
}
