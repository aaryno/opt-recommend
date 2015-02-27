import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

 /**
 * This tree class implements a tree wherein each node in the tree represents a 
 * collection of models that includes a node and all its ancestors.
 * Tree is built progressively, possibly through all permutations of model 
 * combinations but tree growth is halted when a branch's expected latency
 * (the cumulative time of latencies for all ancestor models) exceeds a 
 * maximum.
 * 
 * @author aaryno1
 *
 */
public class ModelPickerTreeNode {	
	private double latencySum;
	private double valueSum;
	private int modelIndex;
	private Model model;
	private Collection<Model> models; // all models contributing to this node
	private String name;
	private ModelPickerTreeNode parent;
	private Collection<ModelPickerTreeNode> children;

	
	/** 
	 * Create a node with a parent or, if parent is null, create a root node with no parent
	 * @param parent
	 * @param model
	 * @param latencySum
	 */
	public ModelPickerTreeNode(ModelPickerTreeNode parent, Model model, int modelIndex) {
		this.children=new ArrayList<>();
		this.parent=parent;
		this.modelIndex=modelIndex;
		this.model=model;
		this.models=null;
		if (parent==null){
			this.latencySum=0;
			this.name="";
		} else {
			this.latencySum=parent.getLatencySum()+model.getLatency();
			this.valueSum=parent.getValueSum()+model.getValue();
			this.name=parent.getName()+"-"+modelIndex;
		}
	}
	
	/**
	 * lazy instantiation of models, derived from this node and ancestry
	 * @return
	 */
	public synchronized Collection<Model> getModels() {
		if (models==null || models.size()==0){
			models=new HashSet<Model>();
			if (this.model!=null){
				models.add(this.model);
			}
			ModelPickerTreeNode parent=this.parent;
			while (parent!=null && parent.getModel()!=null){
				models.add(parent.getModel());
				parent=parent.getParent();
			}
		}
		return models;
	}

	public String getName() {
		return name;
	}

	public double getLatencySum() {
		return latencySum;
	}
	
	public double getValueSum() {
		return valueSum;
	}

	public Collection<ModelPickerTreeNode> getChildren() {
		return children;
	}
	
	public void addChild(ModelPickerTreeNode child){
		children.add(child);
	}

	public ModelPickerTreeNode getParent() {
		return parent;
	}

	public int getModelIndex() {
		return modelIndex;
	}

	public Model getModel() {
		return model;
	}

	public boolean hasChildren() {
		return children!=null && children.size()>0;
	}		
	
}
