package plugin.trackmate.examples.detector;

import ij.ImagePlus;

import java.awt.Dimension;
import java.util.Map;

import javax.swing.JLabel;

import fiji.plugin.trackmate.Model;
import fiji.plugin.trackmate.gui.TrackMateWizard;
import fiji.plugin.trackmate.gui.panels.detector.DogDetectorConfigurationPanel;

public class PreProcessingDoGConfigurationPanel extends DogDetectorConfigurationPanel
{

	private static final long serialVersionUID = 1L;

	public PreProcessingDoGConfigurationPanel(ImagePlus imp, String infoText, String detectorName, Model model)
	{
		super(imp, infoText, detectorName, model);

//		final JLabel label1 = new JLabel( detectorName );
//		label1.setFont( TrackMateWizard.BIG_FONT );
//		add( label1 );
//
//		final JLabel label2 = new JLabel( infoText );
//		label2.setFont( TrackMateWizard.FONT );
//		label2.setPreferredSize( new Dimension( 200, 300 ) );
//		add( label2 );

	}

	@Override
	public void setSettings( final Map< String, Object > settings )
	{
		/*
		 * With this method, you can receive the settings that were set
		 * elsewhere (e.g. by loading from a file) and display these settings on
		 * this panel. Since we have not settings, there is nothing to do.
		 */
		super.setSettings( settings );
	}

	@Override
	public Map< String, Object > getSettings()
	{
		return super.getSettings();
	}

}
