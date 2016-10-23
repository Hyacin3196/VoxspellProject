package voxspell;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import handler.BashCommand;
import handler.StatisticsHandler;
import worker.SoundWorker;

@SuppressWarnings("serial")
public class VoxspellFrame extends JFrame {

	private JPanel contentPane;

	private List<Color> _theme = new ArrayList<Color>();
	private List<List<Color>> _themeList = new ArrayList<List<Color>>();

	/**
	 * sends the list of themes to the MenuPanel JComboBox
	 * @return
	 */
	public ThemePreview[] getThemeListAsPreview(){
		List<ThemePreview> previews = new ArrayList<ThemePreview>();
		_themeList.forEach(theme -> {
			Color c1 = theme.get(0);
			Color c2 = theme.get(1);

			BufferedImage image = new BufferedImage(100,30,BufferedImage.TYPE_INT_ARGB);

			Graphics2D g2d = image.createGraphics();
			g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			int w = image.getWidth();
			int h = image.getHeight();
			Color color1 = new Color(c1.getRed(), c1.getGreen(), c1.getBlue(), 96);
			Color color2 = new Color(c2.getRed(), c2.getGreen(), c2.getBlue(), 96);
			GradientPaint primary = new GradientPaint(0, 0, color1, w, 0, color2);
			GradientPaint shade = new GradientPaint(0f, 0f, new Color(0, 0, 0, 0),0f, h, new Color(0, 0, 0, 96));
			g2d.setPaint(primary);
			g2d.fillRect(0, 0, w, h);
			g2d.setPaint(shade);
			g2d.fillRect(0, 0, w, h);

			ThemePreview preview = new ThemePreview(image, theme);
			previews.add(preview);
		});
		return previews.toArray(new ThemePreview[1]);
	}

	/**
	 * Panel gets the theme for paintComponent
	 * @return
	 */
	public List<Color> getTheme(){
		return _theme;
	}

	/**
	 * sets the theme from the Menu Panel JComboBox
	 * @param theme
	 */
	public void setTheme(List<Color> theme){
		_theme = theme;
	}

	private CardLayout _cardLayout;

	static MenuPanel _menu;
	PreSpellingQuizPanel _preSpellingQuiz;
	SpellingQuizPanel _spellingQuiz;
	ViewStatistics _viewStats;

	public SoundWorker singer = new SoundWorker("boot.wav");
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VoxspellFrame frame = new VoxspellFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VoxspellFrame() {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(200, 100, 650, 500);
		setResizable(false);

		//create Jpanel
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		_cardLayout = new CardLayout();
		contentPane.setLayout(_cardLayout);
		setContentPane(contentPane);

		//add all the theme colors saved in the Voxspell frame
		List<Color> th = new ArrayList<Color>();
		th.add(new Color(0,0,255,96));
		th.add(new Color(0,255,255,96));
		_themeList.add(th);
		th = new ArrayList<Color>();
		th.add(new Color(255,0,0,96));
		th.add(new Color(255,0,255,96));
		_themeList.add(th);
		th = new ArrayList<Color>();
		th.add(new Color(0,255,0,96));
		th.add(new Color(255,255,0,96));
		_themeList.add(th);
		th = new ArrayList<Color>();
		th.add(new Color(255,0,255,96));
		th.add(new Color(0,255,255,96));
		_themeList.add(th);

		//set default theme
		_theme.add(new Color(0,0,255,96));
		_theme.add(new Color(0,255,255,96));

		//initialise all the panels
		_menu = new MenuPanel(this, _cardLayout, contentPane);
		_preSpellingQuiz = new PreSpellingQuizPanel(this, _cardLayout, contentPane);
		_spellingQuiz = new SpellingQuizPanel(this, _cardLayout, contentPane);
		_viewStats = new ViewStatistics(this, _cardLayout, contentPane);

		//add all the panels in the content pane
		contentPane.add(_menu,"Menu");
		contentPane.add(_preSpellingQuiz,"PreSpellingQuiz");
		contentPane.add(_spellingQuiz,"SpellingQuiz");
		contentPane.add(_viewStats,"ViewStats");
		_cardLayout.show(contentPane, "Menu");

		//play sound on startup
		singer.execute();

		//when window closes
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if(singer.isDone()){
					singer = new SoundWorker("boot.wav");
					singer.execute();
				}
				if (JOptionPane.showConfirmDialog(VoxspellFrame.this, 
						"Are you sure to close this window?", "Really Closing?", 
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
					StatisticsHandler.saveStats();
					BashCommand.saveOptions();
					System.exit(0);
				}
			}
		});
	}
	/**
	 * repaints all the panels whenever the new theme is set
	 */
	public void repaintAllComponents(){
		_menu.repaint();
		_spellingQuiz.repaint();
		_preSpellingQuiz.repaint();
		_viewStats.repaint();
	}

}
