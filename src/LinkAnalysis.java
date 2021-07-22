import java.util.List;

public class LinkAnalysis_9976
{
    protected static final double DAMPING_FACTOR = 0.85;
    protected int iterNum;
    protected Double initialVal;
    protected double errorRate;
    protected Graph_9976 graph;

    LinkAnalysis_9976(int iterNum, double initialVal, Graph_9976 graph)
    {
        this.iterNum = iterNum;
        this.initialVal = initialVal >= 0 ? initialVal :  1 / Math.pow(graph.getVertexCount(), 1 / Math.abs(initialVal));
        this.graph = graph;

        if (iterNum == 0)
        {
            errorRate = Math.pow(10, -5);
        } else if (iterNum < 0)
        {
            errorRate = Math.pow(10, iterNum);
        }

    }

    protected boolean hasConverged(int curIter, List<Double> nextValues, List<Double> curValues)
    {
        // iteration limit
        if (iterNum > 0)
            return !(curIter < iterNum);


        // error rate check
        for (int i = 0; i < graph.getVertexCount(); i++)
        {
            double diff = nextValues.get(i) -  curValues.get(i);

            if (Math.abs(diff) > errorRate)
                return false;
        }

        return true;
    }




}
