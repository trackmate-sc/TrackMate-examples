package plugin.trackmate.examples.tracker;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;

import fiji.plugin.trackmate.gui.ConfigurationPanel;
import fiji.plugin.trackmate.gui.panels.components.JNumericTextField;

public class RandomLinkingTrackerConfigPanel extends ConfigurationPanel
{

	private static final long serialVersionUID = 1L;
	private JNumericTextField maxTF;

	public RandomLinkingTrackerConfigPanel()
	{
		initGui();
	}

	private void initGui()
	{
		add( new JLabel( "Max number of links per track:" ) );
		maxTF = new JNumericTextField( RandomLinkingTrackerFactory.DEFAULT_MAX_LINKS_PER_FRAME );
		add( maxTF );

	}

	@Override
	public void setSettings( final Map< String, Object > settings )
	{
		maxTF.setText( "" + settings.get( RandomLinkingTrackerFactory.MAX_LINKS_PER_FRAME ) );
	}

	@Override
	public Map< String, Object > getSettings()
	{
		final Map< String, Object > map = new HashMap< String, Object >( 1 );
		map.put( RandomLinkingTrackerFactory.MAX_LINKS_PER_FRAME, ( int ) maxTF.getValue() );
		return map;
	}

}
