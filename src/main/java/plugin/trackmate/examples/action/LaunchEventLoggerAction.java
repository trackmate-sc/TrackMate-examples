package plugin.trackmate.examples.action;

import plugin.trackmate.examples.view.EventLoggerView;
import fiji.plugin.trackmate.Logger;
import fiji.plugin.trackmate.Model;
import fiji.plugin.trackmate.SelectionModel;
import fiji.plugin.trackmate.TrackMate;
import fiji.plugin.trackmate.action.TrackMateAction;

public class LaunchEventLoggerAction implements TrackMateAction
{

	private final SelectionModel selectionModel;

	private final Model model;

	private Logger logger;

	public LaunchEventLoggerAction( final Model model, final SelectionModel selectionModel )
	{
		this.model = model;
		this.selectionModel = selectionModel;
	}

	@Override
	public void execute( final TrackMate trackmate )
	{
		logger.log( "Launching a new event logger..." );
		final EventLoggerView view = new EventLoggerView( model, selectionModel );
		view.render();
		logger.log( " Done.\n" );
	}

	@Override
	public void setLogger( final Logger logger )
	{
		this.logger = logger;
	}

}
