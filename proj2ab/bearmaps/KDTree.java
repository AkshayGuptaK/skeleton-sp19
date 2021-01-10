package bearmaps;

public class KDTree {
    private class Node {
        Point point;
        Node left;
        Node right;

        Node(Point p) {
            point = p;
            left = null;
            right = null;
        }
    }

    private Node root;

    private double comparePointsByX(Point p1, Point p2) {
        return p1.getX() - p2.getX();
    }

    private double comparePointsByY(Point p1, Point p2) {
        return p1.getY() - p2.getY();
    }

    private Node addPointHelperX(Point point, Node node) {
        if (node == null)
            return new Node(point);
        if (comparePointsByX(node.point, point) > 0) {
            node.left = addPointHelperY(point, node.left);
            return node;
        }
        node.right = addPointHelperY(point, node.right);
        return node;
    }

    private Node addPointHelperY(Point point, Node node) {
        if (node == null)
            return new Node(point);
        if (comparePointsByY(node.point, point) > 0) {
            node.left = addPointHelperX(point, node.left);
            return node;
        }
        node.right = addPointHelperX(point, node.right);
        return node;
    }

    private void addPoint(Point point) {
        root = addPointHelperX(point, root);
    }

    public KDTree(List<Point> points) {
        for (Point point : points) {
            addPoint(point);
        }
    }

    private double closestPossibleX(Node node, Point goal, Point bound) {
        double nodeX = node.point.getX();
        double goalX = goal.getX();
        double boundX = bound.getX();
        if (nodeX < goalX)
            return Math.min(goalX, boundX);
        return Math.max(goalX, boundX);
    }

    private double closestPossibleY(Node node, Point goal, Point bound) {
        double nodeY = node.point.getY();
        double goalY = goal.getY();
        double boundY = bound.getY();
        if (nodeY < goalY)
            return Math.min(goalY, boundY);
        return Math.max(goalY, boundY);
    }

    private boolean mightBadSideHelp(Point goal, Node best, Point closestPossible) {
        return Point.distance(closestPossible, goal) < Point.distance(best.point, goal);
    }

    private Node nearestHelperX(Node node, Point goal, Node best, Point bound) {
        Comparator<Node> nodeComparator = (m, n) -> Double.compare(Point.distance(m.point, goal),
                Point.distance(n.point, goal));
        if (node == null)
            return best;
        if (nodeComparator(node, best) < 0)
            best = node;
        Node good;
        Node bad;
        if (comparePointsByX(node.point, goal) > 0) {
            good = node.left;
            bad = node.right;
        } else {
            good = node.right;
            bad = node.left;
        }
        best = nearestHelperY(good, goal, best);
        Point closestPossible = new Point(node.x, closestPossibleY(node, goal, bound));
        if (mightBadSideHelp(goal, best, closestPossible))
            best = nearestHelperY(bad, goal, best);
        return best;
    }

    private Node nearestHelperY(Node node, Point goal, Node best, Point bound) {
        Comparator<Node> nodeComparator = (m, n) -> Double.compare(Point.distance(m.point, goal),
                Point.distance(n.point, goal));
        if (node == null)
            return best;
        if (nodeComparator(node, best) < 0)
            best = node;
        Node good;
        Node bad;
        if (comparePointsByY(node.point, goal) > 0) {
            good = node.left;
            bad = node.right;
        } else {
            good = node.right;
            bad = node.left;
        }
        best = nearestHelperX(good, goal, best);
        Point closestPossible = new Point(closestPossibleX(node, goal, bound), node.y);
        if (mightBadSideHelp())
            best = nearestHelperX(bad, goal, best);
        return best;
    }

    public Point nearest(double x, double y) {
        Point goal = new Point(x, y);
        return nearestHelperX(root, goal, root).point;
    }
}
