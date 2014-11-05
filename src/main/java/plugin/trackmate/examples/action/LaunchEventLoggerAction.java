package plugin.trackmate.examples.action;

import plugin.trackmate.examples.view.EventLoggerView;

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
