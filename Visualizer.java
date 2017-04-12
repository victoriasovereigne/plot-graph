
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import datastructure.ActionVertex;
import datastructure.ActionVertexChild;
import datastructure.CorefEdge;
import datastructure.CorefVertex;
import datastructure.PlotGraph;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JFrame;


/**
 * Visualizes a plot graph using JGraphX
 * @author V Anugrah
 */
public class Visualizer extends JFrame {

    PlotGraph p;

    public Visualizer(PlotGraph p) {
        super("Story Graph");
        this.p = p;

        mxGraph graph = new mxGraph();
        Object parent = graph.getDefaultParent();

        graph.getModel().beginUpdate();

        int origin = 100;
        int x = origin;
        int y = 100;
        try {
            ArrayList<CorefVertex> cvList = p.corefVertexList;
            Collections.sort(cvList);
            int cvSize = cvList.size();
            int cvSizeNotZero = 0;
            for (CorefVertex v : cvList) {
                if (p.getNumOfChildren(v) > 0) {
                    cvSizeNotZero++;
                }
            }

            ArrayList<ActionVertex> avlist = p.actionVertexList;
            int avSize = avlist.size();
            Object[] actions = new Object[avSize];
            Object[][] actionsChild = new Object[avSize][];

            Object[] corefs = new Object[cvSize];
            int distance = (avSize * 300 - origin) / cvSizeNotZero;
//            System.out.println(cvSize);
//            System.out.println(x);
//            System.out.println(distance);
            int cx = distance;
            int cy = 500;

            for (int i = 0; i < cvSize; i++) {
                CorefVertex cv = cvList.get(i);
                if (p.getNumOfChildren(cv) > 0) {
                    corefs[i] = graph.insertVertex(parent, null, cv.word + " [" + cv.chainID + "]", cx, cy, 80, 30);
                    cx += distance;
                }
            }

            for (int i = 0; i < avSize; i++) {
                ActionVertex av = avlist.get(i);
                actions[i] = graph.insertVertex(parent, null,
                        av.word + " [" + av.index + ", " + av.sentenceNumber + "]", x, y, 80, 30);

                ArrayList<ActionVertexChild> children = av.children;
                int size = children.size();
                int xChild = x - (size * size * 10);

                // Masukin child vertex
                for (int j = 0; j < size; j++) {
                    ActionVertexChild child = children.get(j);
                    actionsChild[i] = new Object[size];
                    actionsChild[i][j] = graph.insertVertex(parent, null,
                            child.word + " [" + child.index + ", " + child.sentenceNumber + "]",
                            xChild, y + 200, 80, 30);
                    graph.insertEdge(parent, null, child.relation, actions[i], actionsChild[i][j]);
                    xChild += 100;

                    for (int k = 0; k < cvSize; k++){
                        CorefEdge ce = p.getChain(cvList.get(k), child);
                        if (ce != null){
                            graph.insertEdge(parent, null, ce.chainID, corefs[k], actionsChild[i][j]);
                            break;
                        }
                    }
                }
                x += 300;
            }

            for (int i = 0; i < avSize - 1; i++) {
                graph.insertEdge(parent, null, "then", actions[i], actions[i + 1]);
            }
        } finally {
            graph.getModel().endUpdate();
        }

        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        getContentPane().add(graphComponent);
    }

    public static void visualize(PlotGraph p) {
        Visualizer frame = new Visualizer(p);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 320);
        frame.setVisible(true);
    }
}
