package djview;


  
public class DJTestDrive {

    public static void main (String[] args) throws Exception {
       AudioWaveformCreator awc = new AudioWaveformCreator("C:\\Users\\bidro\\Documents\\NetBeansProjects\\mavenproject1\\src\\main\\java\\djview\\meanwoman2.wav", "C:\\Users\\bidro\\Documents\\NetBeansProjects\\mavenproject1\\src\\main\\java\\djview\\human.png");
		awc.createAudioInputStream();
        BeatModelInterface model = new BeatModel();
		ControllerInterface controller = new BeatController(model);
    }
}
 