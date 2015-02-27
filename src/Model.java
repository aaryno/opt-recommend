
/**
 * Encapsulation of a model with its id, latency, and value
 * @author aaryno1
 *
 */
public class Model {

	private String id;
	private double latency;
	private double value;
	
	public Model(String id,long latency, double value) {
		this.id=id;
		this.latency = latency;
		this.value = value;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getLatency() {
		return latency;
	}

	public void setLatency(double latency) {
		this.latency = latency;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
	
	public double getNormalizedValue(){
		return value/latency;
	}
	
}
