import java.util.ArrayList;
import java.util.List;


public class JavaPickerDemo {
	
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
		ModelSet modelSet=modelPicker.getBestModelSet();
		System.out.println("The best model set has expected value: "+modelSet.getExpectedValue()+
				", expected latency: "+modelSet.getExpectedLatency()+", and contains models: ");
		boolean first=true;
		for (Model m : modelSet.getModels())
		{
			if (!first){
				System.out.print(" ");
			}
			System.out.print(m.getId ());
			first=false;
		}
	}
}
