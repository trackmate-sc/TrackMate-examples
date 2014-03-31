package plugin.trackmate.examples.action;

import ij.ImageJ;
import ij.ImagePlus;

import javax.swing.ImageIcon;

import org.scijava.plugin.Plugin;

import fiji.plugin.trackmate.TrackMatePlugIn_;
import fiji.plugin.trackmate.action.TrackMateAction;
import fiji.plugin.trackmate.action.TrackMateActionFactory;
import fiji.plugin.trackmate.gui.TrackMateGUIController;

@Plugin( type = TrackMateActionFactory.class )
public class LaunchEventLoggerActionFactory implements TrackMateActionFactory
{

	private static final String INFO_TEXT = "<html>This action will launch a new event logger, that uses the ImageJ log window to append TrackMate events.</html>";

	private static final String KEY = "LAUNCH_EVENT_LOGGER";

	private static final String NAME = "Launch the event logger";

	@Override
	public String getInfoText()
	{
		return INFO_TEXT;
	}

	@Override
	public ImageIcon getIcon()
	{
		return null; // No icon for this one.
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
	public TrackMateAction create( final TrackMateGUIController controller )
	{
		return new LaunchEventLoggerAction( controller.getPlugin().getModel(), controller.getSelectionModel() );
	}

	public static void main( final String[] args )
	{
		ImageJ.main( args );
		new ImagePlus( "../fiji/samples/FakeTracks.tif" ).show();
		new TrackMatePlugIn_().run( "" );
	}

}
