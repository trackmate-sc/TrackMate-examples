package plugin.trackmate.examples.tracker;

import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import org.jdom2.DataConversionException;
import org.jdom2.Element;
import org.scijava.plugin.Plugin;

import fiji.plugin.trackmate.Model;
import fiji.plugin.trackmate.SpotCollection;
import fiji.plugin.trackmate.TrackMatePlugIn;
import fiji.plugin.trackmate.gui.components.ConfigurationPanel;
import fiji.plugin.trackmate.tracking.SpotTracker;
import fiji.plugin.trackmate.tracking.SpotTrackerFactory;
import ij.ImageJ;
import ij.ImagePlus;

@Plugin( type = SpotTrackerFactory.class )
public class RandomLinkingTrackerFactory implements SpotTrackerFactory
{

	private static final String INFO_TEXT = "<html>This tracker is the worst: It links particle at random.</html>";

	private static final String KEY = "RANDOM_LINKING_TRACKER";

	private static final String NAME = "Random linking tracker";

	static final String MAX_LINKS_PER_FRAME = "MAX_LINKS_PER_FRAME";

	static final int DEFAULT_MAX_LINKS_PER_FRAME = 5;

	private String errorMessage;

	@Override
	public String getInfoText()
	{
		return INFO_TEXT;
	}

	@Override
	public ImageIcon getIcon()
	{
		return null;
	}

	@Override
	public String getKey()
	{
		return KEY;
	}

	@Override
	public String getName()
	{
		return NAME;
	}

	@Override
	public SpotTracker create( final SpotCollection spots, final Map< String, Object > settings )
	{
		final int maxLinks = ( Integer ) settings.get( MAX_LINKS_PER_FRAME );
		return new RandomLinkingTracker( spots, maxLinks );
	}

	@Override
	public ConfigurationPanel getTrackerConfigurationPanel( final Model model )
	{
		return new RandomLinkingTrackerConfigPanel();
	}

	@Override
	public boolean marshall( final Map< String, Object > settings, final Element element )
	{
		if ( !checkSettingsValidity( settings ) ) { return false; }

		/*
		 * To save to XML, we simply add an attribute to the element, using the
		 * key as a XML tag.
		 */
		final int val = ( Integer ) settings.get( MAX_LINKS_PER_FRAME );
		element.setAttribute( MAX_LINKS_PER_FRAME, "" + val );
		return true;
	}

	@Override
	public boolean unmarshall( final Element element, final Map< String, Object > settings )
	{
		try
		{
			final int val = element.getAttribute( MAX_LINKS_PER_FRAME ).getIntValue();
			settings.put( MAX_LINKS_PER_FRAME, val );
		}
		catch ( final DataConversionException e )
		{
			errorMessage = "Could not read " + MAX_LINKS_PER_FRAME + " attribute:\n" + e.getMessage();
			return false;
		}
		return true;
	}

	@Override
	public String toString( final Map< String, Object > sm )
	{
		// If the settings is not properly built, we return the error message.
		if ( !checkSettingsValidity( sm ) )
			return errorMessage;

		final StringBuilder str = new StringBuilder();
		final int nLinks = ( Integer ) sm.get( MAX_LINKS_PER_FRAME );
		str.append( "  Max number of links per frame pair: " + nLinks + ".\n" );

		return str.toString();
	}

	@Override
	public Map< String, Object > getDefaultSettings()
	{
		// Just made of 1 parameter.
		final Map< String, Object > settings = new HashMap< >( 1 );
		settings.put( MAX_LINKS_PER_FRAME, DEFAULT_MAX_LINKS_PER_FRAME );
		return settings;
	}

	@Override
	public boolean checkSettingsValidity( final Map< String, Object > settings )
	{
		// Be ULTRA pedantic.

		if ( null == settings )
		{
			errorMessage = "Settings map is null.\n";
			return false;
		}

		if ( settings.size() != 1 )
		{
			errorMessage = "Settings map should contain only 1 key, found " + settings.size() + "./n";
			return false;
		}

		final String key = settings.keySet().iterator().next();
		if ( !key.equals( MAX_LINKS_PER_FRAME )) {
			errorMessage = "Could not find parameter " + MAX_LINKS_PER_FRAME + " in settings map.\n";
			return false;
		}

		final Object obj = settings.get( key );
		if ( !Integer.class.isInstance( obj ) )
		{
			errorMessage = "Value for parameter " + MAX_LINKS_PER_FRAME + " should be of Integer class, but found " + obj.getClass() + "./n";
			return false;
		}

		final int val = ( Integer ) obj;
		if (val < 0) {
			errorMessage = "Found a negative value for " + MAX_LINKS_PER_FRAME + ".\n";
			return false;
		}
		// All of this for just one parameter...
		return true;
	}

	@Override
	public String getErrorMessage()
	{
		return errorMessage;
	}

	@Override
	public SpotTrackerFactory copy()
	{
		return new RandomLinkingTrackerFactory();
	}

	public static void main( final String[] args )
	{
		ImageJ.main( args );
		new ImagePlus( "samples/FakeTracks.tif" ).show();
		new TrackMatePlugIn().run( "" );
	}
}
