import java.util.ArrayList;
import java.util.List;


/**
 * Given a set of models and a maximum latency, return the optimal set of models
 * to maximize the expected value but not exceed maximum latency
 * @author aaryno1
 *
 */
public class ModelPicker {
	
	private List<Model> models;
	private long maxLatency;
	private ModelPickerTreeNode root;
	
	/**
	 * Construct a new ModelPicker with an ordered List of models
	 * It would be better to use a Collection -- the List requirement is a 
	 * shortcut for this implementation because we build the tree based on
	 * the index of the model in the collection 
	 * @param models
	 */
	public ModelPicker(List<Model> models, long maxLatency) 
	{
		this.models=models;
		this.maxLatency=maxLatency;
		this.root=buildTree(maxLatency);
	}
	

	public long getMaxLatency() {
		return maxLatency;
	}


	public void setMaxLatency(long maxLatency) {
		this.maxLatency = maxLatency;
	}


	/**
	 * Builds the tree of model combinations, only adding models to a combination
	 * if the expected latency does not exceed maxLatency.
	 * 
	 * @param models
	 * @param maxLatency
	 * @return
	 */
	private ModelPickerTreeNode buildTree(long maxLatency){
		ModelPickerTreeNode root=new ModelPickerTreeNode(null, null, 0);
		for (int i=0; i<models.size(); i++){
			addLegalNode(root,i,maxLatency);
		}
		return root;
	}
	
	/**
	 * Return a ModelSet containing the best model combination with expected latency
	 * expected value
	 * 
	 * @return
	 */
	public ModelSet getBestModelSet() 
	{
		ModelPickerTreeNode bestModelTreeNode=getBestModelTreeNode();
		ModelSet modelSet=new ModelSet(bestModelTreeNode.getModels(),
				bestModelTreeNode.getValueSum(),bestModelTreeNode.getLatencySum());
		return modelSet;
	}
	
	/*****************************************************************************/
	
	/**
	 * recursive method to add a model-bearing node to the tree if its addition 
	 * does not make the tree branch illegal (i.e., the latency sum exceeds max)
	 * @param parent
	 * @param modelIndex
	 * @param maxLatency
	 * @return
	 */
	private ModelPickerTreeNode addLegalNode(ModelPickerTreeNode parent, int modelIndex,
			long maxLatency) {
		Model model=models.get(modelIndex);
		if (parent.getLatencySum()+model.getLatency()<maxLatency){
			ModelPickerTreeNode child=new ModelPickerTreeNode(parent, model, modelIndex);
			parent.addChild(child);
//			System.out.println("Adding "+modelIndex+" - sum: "+(parent.getLatencySum()+m.getLatency())+" - max: "+maxLatency);
			for (int i=modelIndex+1; i<models.size(); i++){
				addLegalNode(child,i,maxLatency);
			}
		}		
		return parent;
	}
	
	/**
	 * main method just for a quick test
	 * 
	 * @param arg
	 */
	public static void main(String[] arg){
		List<Model> models=new ArrayList<>();
		models.add(new Model("a",10,10));
		models.add(new Model("b",6,12));
		models.add(new Model("c",20,7));
		models.add(new Model("d",10,10));
		models.add(new Model("e",40,10));
		models.add(new Model("f",20,15));
		models.add(new Model("g",10,20));
		long maxLatency=40;
		ModelPicker modelPicker=new ModelPicker(models, maxLatency);

		ModelPickerTreeNode bestModelTreeNode=modelPicker.getBestModelTreeNode();
		System.out.println("The best model is "+bestModelTreeNode.getName()+ 
				" with a score of "+bestModelTreeNode.getValueSum()+ 
				" and expected latency of "+bestModelTreeNode.getLatencySum());
		ModelSet modelSet=modelPicker.getBestModelSet();
		System.out.println(modelSet.getExpectedValue()+","+modelSet.getExpectedLatency());
		for (Model m : modelSet.getModels())
		{
			System.out.print(m);//.getLatency()+" - ");
		}
	}

	/**
	 * Return the ModelPickerTreeNode with the highest expected value (and also has
	 * an expected latency < max
	 * @return
	 */
	private ModelPickerTreeNode getBestModelTreeNode() {
		return getBestModelTreeNode(root);
	}
	
	/**
	 * recursive method to get the best ModelPickerTreeNode from a subtree.
	 * @param parent
	 * @return
	 */
	private ModelPickerTreeNode getBestModelTreeNode(ModelPickerTreeNode parent) {
		
		ModelPickerTreeNode bestModelTreeNode=parent;
		if (!parent.hasChildren()){ // leaf!
			ModelPickerTreeNode node=parent;
			System.out.println(" -- "+parent.getName()+" -- "+parent.getModelIndex()+" score: "+parent.getValueSum()+", latency: "+parent.getLatencySum());
			
		} 
		for (ModelPickerTreeNode child : parent.getChildren()){
			ModelPickerTreeNode bestChild=getBestModelTreeNode(child);
			if (bestChild.getValueSum()>bestModelTreeNode.getValueSum()){
				bestModelTreeNode=bestChild;
			}
		}
		return bestModelTreeNode;
	}
}
