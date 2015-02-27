This contains a class for optimally choosing a set of models to maximize value given a maximum latency. 

We assume:

1) Models have a value which, when combined with other models, is additive. 
2) Models can only be run serially, so the latency of two models is the sum of their independent latencies.

The use of ModelPicker is demonstrated in JavaPickerDemo, which requires instantiation with a List of Models, each of which has an id, latency, and value.

