import java.util.Collection;

/**
 * set of models with total of expected value and expected latency
 * considering the models run sequentially and have additive benefit
 * 
 * @author aaryno
 *
 */
public class ModelSet {

	private Collection<Model> models;
	private double expectedValue;
	private double expectedLatency;
	
	public ModelSet(Collection<Model> models, double expectedValue,
			double expectedLatency) {
		this.models = models;
		this.expectedValue = expectedValue;
		this.expectedLatency = expectedLatency;
	}
	
	public Collection<Model> getModels() {
		return models;
	}
	
	public double getExpectedValue() {
		return expectedValue;
	}
	
	public double getExpectedLatency() {
		return expectedLatency;
	}
	
}
