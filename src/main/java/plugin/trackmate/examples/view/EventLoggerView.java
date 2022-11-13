/*-
 * #%L
 * TrackMate: your buddy for everyday tracking.
 * %%
 * Copyright (C) 2014 - 2022 TrackMate developers.
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
package plugin.trackmate.examples.view;

import java.util.Map;

import org.jgrapht.graph.DefaultWeightedEdge;

import fiji.plugin.trackmate.Model;
import fiji.plugin.trackmate.ModelChangeEvent;
import fiji.plugin.trackmate.ModelChangeListener;
import fiji.plugin.trackmate.SelectionChangeEvent;
import fiji.plugin.trackmate.SelectionChangeListener;
import fiji.plugin.trackmate.SelectionModel;
import fiji.plugin.trackmate.Spot;
import fiji.plugin.trackmate.visualization.TrackMateModelView;
import ij.IJ;

public class EventLoggerView implements TrackMateModelView, ModelChangeListener, SelectionChangeListener
{

	private final Model model;

	private final SelectionModel selectionModel;

	public EventLoggerView( final Model model, final SelectionModel selectionModel )
	{
		/*
		 * The constructor: We just have been instantiated, but we do nothing
		 * yet. We wait to be #render()ed.
		 */
		this.model = model;
		this.selectionModel = selectionModel;
	}

	@Override
	public void render()
	{
		/*
		 * When this view is "rendered", it just registers itself as a listener
		 * for model and selection changes (if it is not already registered).
		 * That's all.
		 */

		if ( !model.getModelChangeListener().contains( this ) )
		{
			model.addModelChangeListener( this );
		}

		if ( !selectionModel.getSelectionChangeListener().contains( this ) )
		{
			selectionModel.addSelectionChangeListener( this );
		}
	}

	@Override
	public void refresh()
	{
		// Ignored.
	}

	@Override
	public void clear()
	{
		// Clear the log window using a log-specific command.
		IJ.log( "\\Clear" );
	}

	@Override
	public void centerViewOn( final Spot spot )
	{
		// Ignored.
	}

	@Override
	public Model getModel()
	{
		// Ignored.
		return null;
	}

	@Override
	public String getKey()
	{
		// This MUST be the same key that for the facory of this view.
		return EventLoggerViewFactory.KEY;
	}

	/*
	 * MODELCHANGELISTENER
	 */

	@Override
	public void modelChanged( final ModelChangeEvent event )
	{
		/*
		 * Instead of crafting a message for each event type, we can reuse the
		 * ModelChangeEvent#toString() method:
		 */

		IJ.log( event.toString() );
	}

	/*
	 * SELECTIONCHANGELISTENER
	 */

	@Override
	public void selectionChanged( final SelectionChangeEvent event )
	{
		/*
		 * Here we build a string representation of the selection change event,
		 * manually.
		 */

		final StringBuilder str = new StringBuilder();
		str.append( "Selection change event from " + event.getSource() + ":\n" );

		final Map< Spot, Boolean > spotChanged = event.getSpots();
		// Careful: can be null if there is no spot in the change.
		if ( null != spotChanged )
		{
			for ( final Spot spot : spotChanged.keySet() )
			{
				if ( spotChanged.get( spot ).booleanValue() )
				{
					str.append( "\tSpot " + spot.getName() + " was added to selection.\n" );
				}
				else
				{
					str.append( "\tSpot " + spot.getName() + " was removed from selection.\n" );
				}
			}
		}

		final Map< DefaultWeightedEdge, Boolean > edgeChanged = event.getEdges();
		if ( null != edgeChanged )
		{
			for ( final DefaultWeightedEdge edge : edgeChanged.keySet() )
			{
				if ( edgeChanged.get( edge ).booleanValue() )
				{
					str.append( "\tEdge " + edge + " was added to selection.\n" );
				}
				else
				{
					str.append( "\tEdge " + edge + " was removed from selection.\n" );
				}
			}
		}
		str.append( "There are currently " );
		if ( selectionModel.getSpotSelection().size() == 0 )
		{
			str.append( "no spots and " );
		}
		else if ( selectionModel.getSpotSelection().size() == 1 )
		{
			str.append( "one spot and " );
		}
		else
		{
			str.append( selectionModel.getSpotSelection().size() + " spots and " );
		}

		if ( selectionModel.getEdgeSelection().size() == 0 )
		{
			str.append( "no edges " );
		}
		else if ( selectionModel.getEdgeSelection().size() == 1 )
		{
			str.append( "one edge " );
		}
		else
		{
			str.append( selectionModel.getEdgeSelection().size() + " edges " );
		}
		str.append( "in the selection.\n" );

		IJ.log( str.toString() );
	}
}
