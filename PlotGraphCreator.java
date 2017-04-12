
import datastructure.ActionChildEdge;
import datastructure.ActionVertex;
import datastructure.ActionVertexChild;
import datastructure.CorefVertex;
import datastructure.PlotGraph;
import edu.stanford.nlp.dcoref.CorefChain;
import edu.stanford.nlp.dcoref.CorefCoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;
import edu.stanford.nlp.util.ArrayCoreMap;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.IntPair;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 *
 * @author V Anugrah
 */
public class PlotGraphCreator {

    public static PlotGraph createPlotGraph(String original) {
        // =====================================================================
        // Bagian annotation 
        // =====================================================================
        Properties prop = new Properties();
        prop.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");

        StanfordCoreNLP pipeline = new StanfordCoreNLP(prop);
        Annotation annotation = new Annotation(original);

        pipeline.annotate(annotation);

        // =====================================================================
        // Kumpulkan actions ke dalam action vertex
        // =====================================================================
        PlotGraph plotGraph = createActionGraph(annotation);
        plotGraph.originalStory = original;
//        ArrayList<CorefVertex> corefVertexList = corefChainMaker(annotation);
//        plotGraph.corefVertexList = corefVertexList;
        corefChainMaker(annotation, plotGraph);
//        plotGraph.linkCorefChain();
        plotGraph.linkCorefChainHash();
        plotGraph.linkAction();
        plotGraph.replaceCorefWords();

        return plotGraph;
    }

    /**
     *
     * @param annotation
     * @return
     */
    private static PlotGraph createActionGraph(Annotation annotation) {
        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
        PlotGraph pg = new PlotGraph();

        // =====================================================================
        // Memasukkan kata kerja penting ke dalam ActionVertex
        // =====================================================================
        for (int i = 0; i < sentences.size(); i++) {
            int sentenceNum = i + 1;
            pg.numOfSentence++;

            // Ambil sentence dijadikan graph
            ArrayCoreMap sentence = (ArrayCoreMap) sentences.get(i);

            SemanticGraph graph = sentence.get(SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation.class);
            List<SemanticGraphEdge> sge = getAllEdges(graph);

            // Masukkan ke Action Vertex            
            for (SemanticGraphEdge e : sge) {
                String rel = e.getRelation().getShortName();

                // aux jangan dibuang?
                if (rel.contains("nsubj") || rel.contains("expl") || rel.contains("aux")) {
                    IndexedWord parent = e.getSource();
                    ActionVertex a = new ActionVertex(parent.lemma(), parent.index(), sentenceNum);

                    if (!pg.contains(a)) {
                        // Ambil semua anak2 dari parent
                        Set<IndexedWord> children = graph.getChildren(parent);
                        for (IndexedWord child : children) {
                            String relation = graph.reln(parent, child).toString();

                            // Jika berhubungan conj_and, jangan disimpan
                            // advmod, advcl, aux, cop, dep, expl, mark tidak perlu disimpan
                            if (!relation.contains("conj")
                                    && !relation.contains("ccomp")
                                    && !relation.contains("adv")
                                    && !relation.contains("aux")
                                    && !relation.contains("cop")
                                    && !relation.contains("dep")
                                    && !relation.contains("expl")
                                    && !relation.contains("mark")) {
                                ActionVertexChild avc = new ActionVertexChild(child.lemma(), child.tag(), relation, child.index(), sentenceNum);
                                ActionChildEdge ace = new ActionChildEdge(a, avc, relation);
                                a.addChild(avc);
                                pg.addActionVertexChild(avc);
                                pg.addActionChildEdge(ace);
                                pg.numOfTotalChildren++;
                            }
                        }
                        pg.addActionVertex(a);
                        pg.addActionVertexToHashMap(a);
                    }
                }
            }
        } // end for
        pg.linkAction();
        return pg;
    }

    /**
     * Method untuk mengambil seluruh SemanticGraphEdge dari sebuah sentence
     *
     * @param sentence
     * @return list of SemanticGraphEdge
     */
    private static List<SemanticGraphEdge> getAllEdges(SemanticGraph graph) {
        // Ambil edge
        Iterator iter = graph.edgeIterable().iterator();
        List<SemanticGraphEdge> sge = new ArrayList<SemanticGraphEdge>();
        while (iter.hasNext()) {
            sge.add((SemanticGraphEdge) iter.next());
        }
        return sge;
    }

    /**
     *
     * @param annotation
     * @return
     */
    private static void corefChainMaker(Annotation annotation, PlotGraph p) {
        Map<Integer, CorefChain> map = annotation.get(CorefCoreAnnotations.CorefChainAnnotation.class);
//        ArrayList<CorefVertex> corefList = new ArrayList<CorefVertex>();        
        
        for (Integer k : map.keySet()) {
            CorefChain chain = map.get(k);
            List<CorefChain.CorefMention> mentions = chain.getMentionsInTextualOrder();
            int size = mentions.size();
            
            int start = mentions.get(0).startIndex;
            int head = mentions.get(0).headIndex;
            
            String mainWord = mentions.get(0).mentionSpan.split(" ")[head-start];
            
            CorefVertex cv = new CorefVertex(mainWord, mentions.get(0).mentionID, size);
//            corefList.add(cv);
            
            int i = 0;
            for (CorefChain.CorefMention c : mentions) {
                cv.intpairs[i] = new IntPair(c.headIndex, c.sentNum);
                i++;
            }
            
            p.corefVertexList.add(cv);
            p.addCorefVertexToHashMap(cv);
        } // end for
//        return corefList;
    }
}