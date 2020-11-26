public class Body {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    private static final double g = 6.67e-11;

    public Body (double xP, double yP, double xV, double yV, double m, String img) {
	xxPos = xP;
	yyPos = yP;
	xxVel = xV;
	yyVel = yV;
	mass = m;
	imgFileName = img;
    }

    public Body (Body b) {
	xxPos = b.xxPos;
	yyPos = b.yyPos;
	xxVel = b.xxVel;
	yyVel = b.yyVel;
	mass = b.mass;
	imgFileName = b.imgFileName;
    }

    public double calcDistance(Body b) {
	double dx = b.xxPos - xxPos;
	double dy = b.yyPos - yyPos;
	return Math.sqrt(dx * dx + dy * dy);
    }

    public double calcForceExertedBy (Body b) {
	double distance = calcDistance(b);
	return (g * mass * b.mass) / (distance * distance);
    }

    public double calcForceExertedByX (Body b) {
	double dx = b.xxPos - xxPos;
	double force = calcForceExertedBy(b);
	double distance = calcDistance(b);
	return force * dx / distance;
    }

    public double calcForceExertedByY (Body b) {
	double dy = b.yyPos - yyPos;
	double force = calcForceExertedBy(b);
	double distance = calcDistance(b);
	return force * dy / distance;
    }

    public double calcNetForceExertedByX (Body[] bodies) {
	double force = 0;
	for (Body b : bodies) {
	    if (!this.equals(b)) {
		force += calcForceExertedByX(b);
	    }
	}
	return force;
    }

    public double calcNetForceExertedByY (Body[] bodies) {
	double force = 0;
	for (Body b : bodies) {
	    if (!this.equals(b)) {
		force += calcForceExertedByY(b);
	    }
	}
	return force;
    }

    public void update(double dt, double fx, double fy) {
	double ax = fx / mass;
	double ay = fy / mass;
	xxVel += ax * dt;
	yyVel += ay * dt;
	xxPos += xxVel * dt;
	yyPos += yyVel * dt;
    }

    public void draw() {
	StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
    }
}
