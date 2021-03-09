
package djview;

public class BeatController implements ControllerInterface {
	BeatModelInterface model;
	DJView view;
        
   
	public BeatController(BeatModelInterface model) {
		this.model = model;
		view = new DJView(this, model);
                view.createView();
                view.createControls();
		view.disableStopMenuItem();
		view.enableStartMenuItem();
		model.initialize();
	}
  
	public void start() {
		model.on();
		view.disableStartMenuItem();
		view.enableStopMenuItem();
	}
  
	public void stop() {
		model.off();
		view.disableStopMenuItem();
		view.enableStartMenuItem();
	}
    
	public void increaseBPM() {
        float bpm = model.getBPM();
        model.setBPM(bpm + 0.1f);
        
	}
    
	public void decreaseBPM() {
        float bpm = model.getBPM();
        model.setBPM(bpm - 0.1f);
        
  	}
  
 	public void setBPM(float bpm) {
            
             model.setBPM(bpm);
	}
}
