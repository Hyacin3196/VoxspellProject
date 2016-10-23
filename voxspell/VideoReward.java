package voxspell;

/*
 * Class which displays the video reward.
 * This will be a new JFrame which contains the video
 */
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.MediaPlayerEventListener;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

/**
 * This class was made entirely by Andy Tang
 * @author Andy Tang
 *
 */
@SuppressWarnings("serial")
public class VideoReward extends JFrame{

	private JPanel framePanel, buttonPanel;
	private JSlider progressBar;
	private JButton playAndPause, stop, mute, exit;
	private final EmbeddedMediaPlayerComponent mediaPlayerComponent;
	private final EmbeddedMediaPlayer video;
	private final String videoFile = "big_buck_bunny_1_minute.avi";
	
	private boolean syncTimeline = false;

	public VideoReward() {
		super("Video Reward");

		NativeLibrary.addSearchPath(
				RuntimeUtil.getLibVlcLibraryName(), "/Applications/vlc-2.0.0/VLC.app/Contents/MacOS/lib"
				);
		Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);

		// Set up panel
		framePanel = new JPanel();
		framePanel.setLayout(new BoxLayout(framePanel, BoxLayout.Y_AXIS));
		setContentPane(framePanel);

		// Set up video player
		mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
		mediaPlayerComponent.setMaximumSize(new Dimension(854, 480));
		video = mediaPlayerComponent.getMediaPlayer();

		// Set up frame
		setSize(854, 520);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				video.stop();
				dispose();
			}
		});

		/*
		 * edited snippet of:
		 * http://svn.openstreetmap.org/applications/editors/josm/plugins/videomapping/src/org/openstreetmap/josm/plugins/videomapping/video/SimpleVideoPlayer.java?p=24624
		 */
		ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
		executorService.scheduleAtFixedRate(new Runnable() {
			//We have to do syncing in the main thread
			public void run() {
				SwingUtilities.invokeLater(new Runnable() {
					//here we update
					public void run() {
						if (video.isPlaying()) updateTime(); //if the video is seeking we get a mess
					}
				});
			}
		}, 0L, 1L, TimeUnit.MILLISECONDS);

		// Set up button panel
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.setMaximumSize(new Dimension(250, 30));


		// Set up play/pause button
		playAndPause = generateButton();
		playAndPause.setText("Pause");
		playAndPause.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {		
				if (playAndPause.getText().equals("Pause")) {
					video.pause();
					playAndPause.setText("Play");
				}
				else {
					video.play();
					playAndPause.setText("Pause");
				}
			}

		});


		// Set up stop button
		stop = generateButton();
		stop.setText("Stop");
		stop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {		
				video.stop();
				playAndPause.setText("Play");
			}

		});


		// Set up mute button
		mute = generateButton();
		mute.setText("Mute");
		mute.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {		
				if (mute.getText().equals("Mute")) {
					video.mute(true);
					mute.setText("Unmute");
				}
				else {
					video.mute(false);
					mute.setText("Mute");
				}
			}

		});

		// Set up mute button
		exit = generateButton();
		exit.setText("Exit");
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {		

				VideoReward.this.dispatchEvent(new WindowEvent(VideoReward.this, WindowEvent.WINDOW_CLOSING));
			}

		});

		// Add buttons to panel
		buttonPanel.add(playAndPause);
		buttonPanel.add(Box.createRigidArea(new Dimension(5,0)));
		buttonPanel.add(stop);
		buttonPanel.add(Box.createRigidArea(new Dimension(5,0)));
		buttonPanel.add(mute);
		buttonPanel.add(Box.createRigidArea(new Dimension(5,0)));
		buttonPanel.add(exit);


		progressBar = new JSlider(0,100,0);
		progressBar.setMajorTickSpacing(10);
		progressBar.setMajorTickSpacing(5);
		progressBar.setPaintTicks(true);
		/*
		 * edited snippet of:
		 * http://svn.openstreetmap.org/applications/editors/josm/plugins/videomapping/src/org/openstreetmap/josm/plugins/videomapping/video/SimpleVideoPlayer.java?p=24624
		 */
		progressBar.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(!syncTimeline) //only if user moves the slider by hand
				{
					if(!progressBar.getValueIsAdjusting()) //and the slider is fixed
					{
						//recalc to 0.x percent value
						video.setPosition((float)progressBar.getValue()/100.0f);
					}                   
				}
			}
		});
		video.addMediaPlayerEventListener(new MediaPlayerEventAdapter(){
			public void positionChanged(MediaPlayer mp, float pos){
				int value = Math.min(100, Math.round(pos * 100.0f));
				progressBar.setValue(value);
			}
		});
		// Add components to main frame panel
		framePanel.add(mediaPlayerComponent);
		framePanel.add(Box.createRigidArea(new Dimension(0,5)));
		framePanel.add(progressBar);
		framePanel.add(buttonPanel);

		// Play video
		video.playMedia(videoFile);
		video.mute(false);


	}

	/*
	 * This is a helper method used to create buttons
	 */
	private JButton generateButton() {
		JButton button = new JButton();
		button.setFont(new Font("Comic Sans MS", 0, 10));
		button.setAlignmentX(CENTER_ALIGNMENT);
		button.setMaximumSize(new Dimension(80, 30));	

		return button;
	}
	
	/*
	 * edited snippet of:
	 * http://svn.openstreetmap.org/applications/editors/josm/plugins/videomapping/src/org/openstreetmap/josm/plugins/videomapping/video/SimpleVideoPlayer.java?p=24624
	 */
	//gets called by the Syncer thread to update all observers
    public void updateTime ()
    {
        if(video.isPlaying())
        {
        	long millis=video.getTime();
        	String s = String.format("%02d:%02d:%02d", //dont know why normal Java date utils doesn't format the time right
		      TimeUnit.MILLISECONDS.toHours(millis),
		      TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), 
		      TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
		    );
            //setTitle(ms.format(new Time(sec)));
        	setTitle(s);
            syncTimeline=true;
            progressBar.setValue(Math.round(video.getPosition()*100));
            syncTimeline=false;
        }
    }
}
