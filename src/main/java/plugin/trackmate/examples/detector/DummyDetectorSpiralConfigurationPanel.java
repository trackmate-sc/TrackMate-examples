package plugin.trackmate.examples.detector;

import java.awt.Dimension;
import java.util.Collections;
import java.util.Map;

import javax.swing.JLabel;

import fiji.plugin.trackmate.gui.ConfigurationPanel;
import fiji.plugin.trackmate.gui.TrackMateWizard;

public class DummyDetectorSpiralConfigurationPanel extends ConfigurationPanel
{

	private static final long serialVersionUID = 1L;

	public DummyDetectorSpiralConfigurationPanel()
	{
		/*
		 * We have no settings, so nothing to tune. Just display an explanatory
		 * text.
		 */

		final JLabel label1 = new JLabel( SpiralDummyDetectorFactory.NAME );
		label1.setFont( TrackMateWizard.BIG_FONT );
		add( label1 );

		final JLabel label2 = new JLabel( SpiralDummyDetectorFactory.INFO_TEXT );
		label2.setFont( TrackMateWizard.FONT );
		label2.setPreferredSize( new Dimension( 200, 300 ) );
		add( label2 );

	}

	@Override
	public void setSettings( final Map< String, Object > settings )
	{
		/*
		 * With this method, you can receive the settings that were set
		 * elsewhere (e.g. by loading from a file) and display these settings on
		 * this panel. Since we have not settings, there is nothing to do.
		 */
	}

	@Override
	public Map< String, Object > getSettings()
	{
		/*
		 * We are expected to return the settings the user set here. In our case
		 * we must return an empty settings map.
		 */
		return Collections.emptyMap();
	}

}
