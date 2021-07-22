import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class pgrk extends LinkAnalysis
{
    private static final double DAMPING_FACTOR = 0.85;
    private List<Double> curValues;
    private List<Double> nextValues;


    pgrk(int iterNum, double initialValue, Graph graph)
    {
        super(iterNum, initialValue, graph);
        this.curValues = new ArrayList(Collections.nCopies(graph.getVertexCount(), initialVal));
        this.nextValues = new ArrayList(Collections.nCopies(graph.getVertexCount(), initialVal));
    }

    void run()
    {
        int graphSize = graph.getVertexCount();
        List<List<Integer>> incomingNodeList = graph.getIncomingNodeList();

        int i = 0;
        if (graphSize < 10)
        {
            System.out.print(String.format("Base: %d  ", i));
            for (int j =0; j < graphSize; j++)
            {
                System.out.print(String.format("P[%d] = %.7f ", j, curValues.get(j)));
            }
            System.out.println();
        }

        while (true)
        {

            for (int j = 0; j < graphSize; j++)
            {
                List<Integer> incomingNodes = incomingNodeList.get(j);
                double sum = 0.0;


                if (incomingNodes != null)
                {
                    for (Integer incomingNode : incomingNodes)
                    {
                        Double aDouble = curValues.get(incomingNode) / graph.getOutDegreeCount(incomingNode);
                        sum += aDouble;
                    }
                }

                double newValue = ((1 - DAMPING_FACTOR) / graphSize) + DAMPING_FACTOR * sum;
                nextValues.set(j, newValue);
            }

            i++;

            if (graphSize < 10)
            {
                System.out.print(String.format("Iter: %d  ", i));
                for (int j =0; j < graphSize; j++)
                    System.out.print(String.format("P[%d] = %.7f ", j, nextValues.get(j)));
                System.out.println();
            }

            if (hasConverged(i, nextValues, curValues))
            {
                if (graphSize > 10)
                {
                    System.out.println(String.format("Iter: %d", i));
                    for (int j =0; j < graphSize; j++)
                        System.out.println(String.format("P[%d] = %.7f ", j, nextValues.get(j)));

                    System.out.println();
                }

                return;
            }

            List<Double> temp = curValues;
            curValues = nextValues;
            nextValues = temp;
        }
    }

    public static void main(String[] args) throws IOException
    {

        int iter = Integer.valueOf(args[0]);
        int initialVal = Integer.valueOf(args[1]);
        final Graph graph = Graph.buildGraph(args[2]);
        if (graph.getVertexCount() > 10)
        {
            iter = 0;
            initialVal = -1;
        }

        pgrk pageRank = new pgrk(iter, initialVal, graph);
        pageRank.run();
    }
}
