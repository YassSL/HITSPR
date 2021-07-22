import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Graph_9976
{
    private List<List<Integer>> incomingNodeList;
    private List<List<Integer>> outGoingNodeList;
    private List<Integer> outDegreeList;

    public Graph_9976(int numV)
    {

        incomingNodeList = new ArrayList<> (Collections.nCopies(numV, null));
        outGoingNodeList = new ArrayList<> (Collections.nCopies(numV, null));
        outDegreeList = new ArrayList<>(Collections.nCopies(numV, 0));
    }

    public int getVertexCount()
    {
        return incomingNodeList.size();
    }

    public List<List<Integer>> getIncomingNodeList()
    {
        return incomingNodeList;
    }


    public List<List<Integer>> getOutGoingNodeList()
    {
        return outGoingNodeList;
    }

    public Integer getOutDegreeCount(int node)
    {
        return outDegreeList.get(node);
    }

    public void addIncomingEdge(int source, int target)
    {
        List<Integer> edges;
        if ((edges =incomingNodeList.get(target)) == null)
            incomingNodeList.set(target, edges = new ArrayList<>());

        edges.add(source);
    }

    public void addOutgoingEdge(int source, int target)
    {
        List<Integer> edges = outGoingNodeList.get(target);
        if (edges == null)
            outGoingNodeList.set(target, edges = new ArrayList<>());
        edges.add(source);
    }

    public void addOutDegreeCount(int v)
    {
        outDegreeList.set(v, outDegreeList.get(v)+ 1);
    }

    public static int getVertex(String line, int idx)
    {
        String[] edge = line.split(" ");
        return Integer.valueOf(edge[idx]);
    }

    public static Graph_9976 buildGraph(String inputFile) throws IOException
    {
        Graph_9976 graph;
        try (final BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(Paths.get(inputFile).toFile()))))
        {
            final String l = br.readLine();
            if (l == null)
                throw new RuntimeException("Invalid file entry");

            graph = new Graph_9976(Graph_9976.getVertex(l, 0));


            for (int i = 0; i < Graph_9976.getVertex(l, 1); i++)
            {
                final String line = br.readLine();
                final int source = Graph_9976.getVertex(line, 0);
                final int target =  Graph_9976.getVertex(line, 1);
                graph.addIncomingEdge(source, target);
                graph.addOutgoingEdge(target, source);
                graph.addOutDegreeCount(source);

                if (line == null)
                {
                    break;
                }
            }
        }

        return graph;
    }
}
