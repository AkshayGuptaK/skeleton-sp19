package bearmaps.hw4;

import edu.princeton.cs.algs4.Stopwatch;
import java.util.HashMap;
import bearmaps.proj2ab.DoubleMapPQ;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private SolverOutcome outcome; // SOLVED, TIMEOUT, UNSOLVABLE
    private List<Vertex> solution;
    private double solutionWeight;
    private int exploredStates;
    private double timeSpent;

    private Stopwatch sw;
    private AStarGraph<Vertex> graph;
    private Vertex goal;
    private double timeLimit;
    private DoubleMapPQ<Vertex> pq;
    private HashMap<Vertex, Double> distances;
    private HashMap<Vertex, Vertex> edgeTo;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        graph = input;
        goal = end;
        timeLimit = timeout;
        exploredStates = 0;
        solution = new List();
        solutionWeight = 0.0;
        sw = new Stopwatch();
        pq = new DoubleMapPQ();
        distances = new HashMap();
        edgeTo = new HashMap();
        pq.add(start, graph.estimatedDistanceToGoal(start, end));
        distances.put(start, 0.0);
        outcome = solve();
        if (outcome == "SOLVED") {
            generateSolution();
        }
    }

    private void generateSolution() {
        Vertex current = edgeTo.get(goal);
        while (current) {
            solution.add(0, current);
            current = edgeTo.get(current);
        }
    }

    private SolverOutcome solve() {
        timeSpent = sw.elapsedTime();
        if (pq.size() == 0)
            return "UNSOLVABLE";
        if (pq.getSmallest() == goal) {
            solutionWeight = distances.get(goal);
            return "SOLVED";
        }
        if (timeSpent > timeLimit)
            return "TIMEOUT";
        Vertex current = pq.removeSmallest();
        exploredStates++;
        for (WeightedEdge<Vertex> e : graph.neighbors(current)) {
            relax(e);
        }
        return solve();
    }

    private void relax(WeightedEdge<Vertex> e) {
        Vertex from = e.from();
        Vertex to = e.to();
        double weight = e.weight();
        double newDistance = distances.get(from) + weight;
        if (!distances.containsKey(to) || newDistance < distances.get(to)) {
            distances.put(to, newDistance);
            edgeTo.put(to, from);
            double newPriority = distances.get(to) + graph.estimatedDistanceToGoal(to, goal);
            if (pq.contains(to)) {
                pq.changePriority(to, newPriority);
            } else {
                pq.add(to, newPriority);
            }
        }
    }

    @Override
    public SolverOutcome outcome() {
        return outcome;
    }

    @Override
    public List<Vertex> solution() {
        return solution;
    }

    @Override
    public double solutionWeight() {
        return solutionWeight;
    }

    @Override
    public int numStatesExplored() {
        return exploredStates;
    }

    @Override
    public double explorationTime() {
        return timeSpent;
    }
}
