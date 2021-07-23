package plugin.trackmate.examples.tracker;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;

import fiji.plugin.trackmate.gui.components.ConfigurationPanel;

public class RandomLinkingTrackerConfigPanel extends ConfigurationPanel
{

	private static final long serialVersionUID = 1L;

	private JFormattedTextField maxTF;

	public RandomLinkingTrackerConfigPanel()
	{
		initGui();
	}

	private void initGui()
	{
		add( new JLabel( "Max number of links per track:" ) );
		maxTF = new JFormattedTextField( Integer.valueOf( RandomLinkingTrackerFactory.DEFAULT_MAX_LINKS_PER_FRAME ) );
		add( maxTF );
	}

	@Override
	public void setSettings( final Map< String, Object > settings )
	{
		maxTF.setValue( settings.get( RandomLinkingTrackerFactory.MAX_LINKS_PER_FRAME ) );
	}

	@Override
	public Map< String, Object > getSettings()
	{
		final Map< String, Object > map = new HashMap< >( 1 );
		map.put( RandomLinkingTrackerFactory.MAX_LINKS_PER_FRAME, ( ( Number ) maxTF.getValue() ).intValue() );
		return map;
	}

	@Override
	public void clean() {}
}
