import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class hits_9976 extends LinkAnalysis_9976
{
    private List<Double> authValues;
    private List<Double> hubValues;
    private List<Double> nextAuthValues;
    private List<Double> nextHubValues;

    hits_9976(int iterNum, double initialValue, Graph_9976 graph)
    {
        super(iterNum, initialValue, graph);
        this.authValues = new ArrayList(Collections.nCopies(graph.getVertexCount(), initialVal));
        this.hubValues = new ArrayList(Collections.nCopies(graph.getVertexCount(), initialVal));
        this.nextAuthValues = new ArrayList(Collections.nCopies(graph.getVertexCount(), initialVal));
        this.nextHubValues = new ArrayList(Collections.nCopies(graph.getVertexCount(), initialVal));
    }

    void run()
    {
        final int graphSize = graph.getVertexCount();
        final List<List<Integer>> incomingNodeList = graph.getIncomingNodeList();
        final List<List<Integer>> outgoingNodeList = graph.getOutGoingNodeList();

        int i = 0;
        if (graphSize < 10)
        {
            System.out.print(String.format("Base: %d : ", i));
            for (int j = 0; j < graphSize; j++)
            {
                System.out.print(String.format("A/H[%d] = %.7f/%.7f  ", j, authValues.get(j), hubValues.get(j)));
            }
            System.out.println();
        }

        while (true)
        {
            Double authNorm = 0.0;
            for (int j = 0; j < graphSize; j++)
            {
                List<Integer> incomingNodes = incomingNodeList.get(j);

                Double auth = 0.0;
                if (incomingNodes != null)
                {
                    for (Integer incomingNode : incomingNodes)
                    {
                        auth += hubValues.get(incomingNode);

                    }
                }


                nextAuthValues.set(j, auth);
                authNorm += Math.pow(auth, 2.0);
            }

            double hubNorm = 0.0;
            for (int j = 0; j < graphSize; j++)
            {
                List<Integer> outgoingNodes = outgoingNodeList.get(j);

                Double hub = 0.0;
                if (outgoingNodes != null)
                {
                    for (Integer outgoingNode : outgoingNodes)
                    {
                        hub += nextAuthValues.get(outgoingNode);
                    }
                }

                nextHubValues.set(j, hub);
                hubNorm += Math.pow(hub, 2.0);
            }

            for (int j = 0; j < graphSize; j++)
            {
                nextAuthValues.set(j, nextAuthValues.get(j) / Math.sqrt(authNorm));
                nextHubValues.set(j, nextHubValues.get(j) / Math.sqrt(hubNorm));
            }

            i++;
            if (graphSize < 10)
            {
                System.out.print(String.format("Iter: %d : ", i));
                for (int j = 0; j < graphSize; j++)
                    System.out.print(String.format("A/H[%d] = %.7f/%.7f  ", j, nextAuthValues.get(j), nextHubValues.get(j)));
                System.out.println();
            }

            if (hasConverged(i, nextAuthValues, authValues) && hasConverged(i, nextHubValues, hubValues))
            {
                if (graphSize > 10)
                {
                    System.out.println(String.format("Iter: %d", i));
                    for (int j = 0; j < graphSize; j++)
                        System.out.println(String.format("A/H[%d] = %.7f/%.7f  ", j, nextAuthValues.get(j), nextHubValues.get(j)));
                }

                return;
            }

            List<Double> tempA = authValues;
            authValues = nextAuthValues;
            nextAuthValues = tempA;
            List<Double> tempB = hubValues;
            hubValues = nextHubValues;
            nextHubValues = tempB;
        }
    }

    public static void main(String[] args) throws IOException
    {

        int iter = Integer.parseInt(args[0]);
        double initialVal = Double.parseDouble(args[1]);
        final Graph_9976 graph = Graph_9976.buildGraph(args[2]);
        if (graph.getVertexCount() > 10)
        {
            iter = 0;
            initialVal = -1;
        }

        hits_9976 hits = new hits_9976(iter, initialVal, graph);
        hits.run();
    }
}
