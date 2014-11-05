package plugin.trackmate.examples.view;

import ij.ImageJ;
import ij.ImagePlus;

import javax.swing.ImageIcon;
import javax.swing.text.ViewFactory;

import org.scijava.plugin.Plugin;

@Plugin( type = ViewFactory.class )
public class EventLoggerViewFactory implements ViewFactory
{

	private static final String INFO_TEXT = "<html>This factory instantiates an event logger view for TrackMate, that uses the IJ log window to just echo all the event sent by the model.</html>";

	public static final String KEY = "EVENT_LOGGER_VIEW";

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
		return "Event logger view";
	}

	@Override
	public TrackMateModelView create( final Model model, final Settings settings, final SelectionModel selectionModel )
	{
		return new EventLoggerView( model, selectionModel );
	}

	/*
	 * MAIN METHOD
	 */

	public static void main( final String[] args )
	{
		ImageJ.main( args );
		new ImagePlus( "../fiji/samples/FakeTracks.tif" ).show();
		new TrackMatePlugIn_().run( "" );
	}

}
