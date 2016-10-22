package voxspell;

/*
 * Class which displays the video reward.
 * This will be a new JFrame which contains the video
 */
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
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
	private JButton playAndPause, stop, mute, exit;
    private final EmbeddedMediaPlayerComponent mediaPlayerComponent;
    private final EmbeddedMediaPlayer video;
    private final String videoFile = "big_buck_bunny_1_minute.avi";

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
        
        // Add components to main frame panel
        framePanel.add(mediaPlayerComponent);
        framePanel.add(Box.createRigidArea(new Dimension(0,5)));
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
    

}
