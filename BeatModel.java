
package djview;


import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.midi.*;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BeatModel implements BeatModelInterface, MetaEventListener {
	//Sequencer sequencer;
	ArrayList<BeatObserver> beatObservers = new ArrayList<BeatObserver>();
	ArrayList<BPMObserver> bpmObservers = new ArrayList<BPMObserver>();
	float bpm = 0.0f;
	//Sequence sequence;
	//Track track;
         static final int DRUM_TRACK = 1;
         private MidiPlayer player;
         Sequence sequence;
      
         

	public void initialize() {
		
		//buildTrackAndStart();
	}

	/*public void on() {
		System.out.println("Starting the sequencer");
		sequencer.start();
		setBPM(90);
                
	}*/
        
         public void run() {

    player = new MidiPlayer();

    // load a sequence
     sequence = player.getSequence("C:\\Users\\bidro\\Documents\\NetBeansProjects\\mavenproject1\\src\\main\\java\\djview\\meanwoman.mid");

    // play the sequence
    player.play(sequence, true);
/*
    // turn off the drums
    System.out.println("Playing (without drums)...");
     sequencer = player.getSequencer();
    sequencer.setTrackMute(DRUM_TRACK, true);
    sequencer.addMetaEventListener(this);
  */
  }       
         public void on() {
             run();
         
         }
      /*   
        public void on() {
            
            System.out.println("Starting fabricated one");
                player = new MidiPlayer();

    // load a sequence
    sequence = player.getSequence("C:\\Users\\bidro\\Documents\\NetBeansProjects\\mavenproject1\\src\\main\\java\\djview\take5.mid");

    // play the sequence
    player.play(sequence, true);

    // turn off the drums
    System.out.println("Playing (without drums)...");
    sequencer = player.getSequencer();
    sequencer.setTrackMute(DRUM_TRACK, true);
    sequencer.addMetaEventListener(this);
        }
         
         */

        
        public void off(){
        setBPM(0.0f);
        player.stop();
        
        }
        
        /*
	public void off() {
		setBPM(0);
		sequencer.stop();
	}

	public void setBPM(int bpm) {
		this.bpm = bpm;
		sequencer.setTempoInBPM(getBPM());
		notifyBPMObservers();
	}
        
        
*/
        public void  setBPM(float BPM){
            this.bpm = BPM;
		notifyBPMObservers();
            
  try{
      
       Receiver synthReceiver = player.getSynthesizer().getReceiver();
     Transmitter seqTransmitter = player.getSequencer().getTransmitter();
     seqTransmitter.setReceiver(synthReceiver);
     ShortMessage volumeMessage = new ShortMessage();
            for ( int i = 0; i < 16; i++ ) {
                volumeMessage.setMessage( ShortMessage.CONTROL_CHANGE, i, 7, (int)(BPM*127) );
                synthReceiver.send( volumeMessage, -1 );
     
  }  
  }
  catch (MidiUnavailableException ex) {
                Logger.getLogger(BeatModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvalidMidiDataException ex) {
                Logger.getLogger(BeatModel.class.getName()).log(Level.SEVERE, null, ex);
            }
  
         
                
     MidiChannel[] channels = player.synthesizer.getChannels();
            System.out.println("channels[0]" + channels[0]);
    
              for( int c = 0; c < channels.length; c++ ) {
                 if(channels[c] != null)   channels[c].controlChange( 10, (int)( BPM*127) );
              
              }
    
        }

  
        
        
	public float getBPM() {
		return bpm;
	}

	void beatEvent() {
		notifyBeatObservers();
	}


	public void registerObserver(BeatObserver o) {
		beatObservers.add(o);
	}

	public void notifyBeatObservers() {
		for(int i = 0; i < beatObservers.size(); i++) {
			BeatObserver observer = (BeatObserver)beatObservers.get(i);
			observer.updateBeat();
		}
	}

	public void registerObserver(BPMObserver o) {
		bpmObservers.add(o);
	}

	public void notifyBPMObservers() {
		for(int i = 0; i < bpmObservers.size(); i++) {
			BPMObserver observer = (BPMObserver)bpmObservers.get(i);
			observer.updateBPM();
		}
	}


	public void removeObserver(BeatObserver o) {
		int i = beatObservers.indexOf(o);
		if (i >= 0) {
			beatObservers.remove(i);
		}
	}



	public void removeObserver(BPMObserver o) {
		int i = bpmObservers.indexOf(o);
		if (i >= 0) {
			bpmObservers.remove(i);
		}
	}


	/*public void meta(MetaMessage message) {
		if (message.getType() == 47) {
			beatEvent();
			sequencer.start();
			setBPM(getBPM());
		}
	}
*/
        
        public void meta(MetaMessage event) {
    if (event.getType() == MidiPlayer.END_OF_TRACK_MESSAGE) {
      Sequencer sequencer = player.getSequencer();
      if (sequencer.getTrackMute(DRUM_TRACK)) {
        // turn on the drum track
        System.out.println("Turning on drums...");
        sequencer.setTrackMute(DRUM_TRACK, false);
      } else {
        // close the sequencer and exit
        System.out.println("Exiting...");
        player.close();
        System.exit(0);
      }
    }
  }}
	
        /*public void setUpMidi() {
		try {
                   
                    
			sequencer = MidiSystem.getSequencer();
			sequencer.open();
			sequencer.addMetaEventListener(this);
			sequence = new Sequence(Sequence.PPQ,4);
			track = sequence.createTrack();
			sequencer.setTempoInBPM(getBPM());
			sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
		} catch(Exception e) {
			e.printStackTrace();
		}
	} 
*/
      /*  
        public  void setUpMidi(){
            
         player = new MidiPlayer();

    // load a sequence
     sequence = player.getSequence("midi.mid");

    // play the sequence
    player.play(sequence, true);

    // turn off the drums
    System.out.println("Playing (without drums)...");
     sequencer = player.getSequencer();
    sequencer.setTrackMute(DRUM_TRACK, true);
    sequencer.addMetaEventListener(this);
        }
        
        
	public void buildTrackAndStart() {
		int[] trackList = {50, 0, 1000, 0};

		sequence.deleteTrack(null);
		track = sequence.createTrack();

		makeTracks(trackList);
		track.add(makeEvent(192,9,1,0,4));      
		try {
			sequencer.setSequence(sequence);                    
		} catch(Exception e) {
			e.printStackTrace();
		}
	} 

	public void makeTracks(int[] list) {        

		for (int i = 0; i < list.length; i++) {
			int key = list[i];

			if (key != 0) {
				track.add(makeEvent(144,9,key, 100, i));
				track.add(makeEvent(128,9,key, 100, i+1));
                                                               
                                
			}
		}
	}

	public  MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
		MidiEvent event = null;
		try {
			ShortMessage a = new ShortMessage();
			a.setMessage(comd, chan, one, two);
			event = new MidiEvent(a, tick);

		} catch(Exception e) {
			e.printStackTrace(); 
		}
		return event;
	}
}
*/
class MidiPlayer implements MetaEventListener {
    public Synthesizer synthesizer;

  // Midi meta event
  public static final int END_OF_TRACK_MESSAGE = 47;

  public Sequencer sequencer;

  private boolean loop;

  private boolean paused;
  public MidiChannel[] channels;

  /**
   * Creates a new MidiPlayer object.
   */
  public MidiPlayer() {
    try {
      sequencer = MidiSystem.getSequencer(false);
      sequencer.open();
       synthesizer = MidiSystem.getSynthesizer();
     synthesizer.open();
     Receiver synthReceiver = synthesizer.getReceiver();
     Transmitter seqTransmitter = sequencer.getTransmitter();
     seqTransmitter.setReceiver(synthReceiver);
     
       
      
      sequencer.addMetaEventListener(this);
    } catch (MidiUnavailableException ex) {
      sequencer = null;
    }
  }

  /**
   * Loads a sequence from the file system. Returns null if an error occurs.
   */
  public Sequence getSequence(String filename) {
    try {
      return getSequence(new FileInputStream(filename));
    } catch (IOException ex) {
      ex.printStackTrace();
      return null;
    }
  }

  /**
   * Loads a sequence from an input stream. Returns null if an error occurs.
   */
  public Sequence getSequence(InputStream is) {
    try {
      if (!is.markSupported()) {
        is = new BufferedInputStream(is);
      }
      Sequence s = MidiSystem.getSequence(is);
      is.close();
      return s;
    } catch (InvalidMidiDataException ex) {
      ex.printStackTrace();
      return null;
    } catch (IOException ex) {
      ex.printStackTrace();
      return null;
    }
  }

  /**
   * Plays a sequence, optionally looping. This method returns immediately.
   * The sequence is not played if it is invalid.
   */
  public void play(Sequence sequence, boolean soop) {
    if (sequencer != null && sequence != null && sequencer.isOpen()) {
      try {
        sequencer.setSequence(sequence);
        sequencer.start();
        this.loop = soop;
      } catch (InvalidMidiDataException ex) {
        ex.printStackTrace();
      }
    }
  }

  /**
   * This method is called by the sound system when a meta event occurs. In
   * this case, when the end-of-track meta event is received, the sequence is
   * restarted if looping is on.
   */
  public void meta(MetaMessage event) {
    if (event.getType() == END_OF_TRACK_MESSAGE) {
      if (sequencer != null && sequencer.isOpen() && loop) {
        sequencer.start();
      }
    }
  }

  /**
   * Stops the sequencer and resets its position to 0.
   */
  public void stop() {
    if (sequencer != null && sequencer.isOpen()) {
      sequencer.stop();
      sequencer.setMicrosecondPosition(0);
    }
  }

  /**
   * Closes the sequencer.
   */
  public void close() {
    if (sequencer != null && sequencer.isOpen()) {
      sequencer.close();
    }
  }

  /**
   * Gets the sequencer.
   */
  public Sequencer getSequencer() {
    return sequencer;
  }

   public Synthesizer getSynthesizer() {
    return synthesizer;
  }
  /**
   * Sets the paused state. Music may not immediately pause.
   */
  public void setPaused(boolean paused) {
    if (this.paused != paused && sequencer != null && sequencer.isOpen()) {
      this.paused = paused;
      if (paused) {
        sequencer.stop();
      } else {
        sequencer.start();
      }
    }
  }

  /**
   * Returns the paused state.
   */
  public boolean isPaused() {
    return paused;
  }
  
  
   

}
