/*-
 * #%L
 * TrackMate: your buddy for everyday tracking.
 * %%
 * Copyright (C) 2014 - 2023 TrackMate developers.
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
package plugin.trackmate.examples.tracker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.NavigableSet;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import fiji.plugin.trackmate.Logger;
import fiji.plugin.trackmate.Spot;
import fiji.plugin.trackmate.SpotCollection;
import fiji.plugin.trackmate.tracking.SpotTracker;

public class DrunkenCellDivisionTracker implements SpotTracker
{

	private SimpleWeightedGraph< Spot, DefaultWeightedEdge > graph;

	private String errorMessage;

	private Logger logger = Logger.VOID_LOGGER;

	private final SpotCollection spots;

	@Override
	public SimpleWeightedGraph< Spot, DefaultWeightedEdge > getResult()
	{
		return graph;
	}

	/*
	 * CONSTRUCTOR
	 */

	public DrunkenCellDivisionTracker( final SpotCollection spots )
	{
		this.spots = spots;
	}

	/*
	 * METHODS
	 */

	@Override
	public boolean checkInput()
	{
		return true;
	}

	@Override
	public boolean process()
	{
		graph = new SimpleWeightedGraph< Spot, DefaultWeightedEdge >( DefaultWeightedEdge.class );

		// Get the frames in order.
		final NavigableSet< Integer > frames = spots.keySet();
		final Iterator< Integer > frameIterator = frames.iterator();

		// Get all the visible spots in the first frame, and put them in a new
		// collection.
		final Iterable< Spot > iterable = spots.iterable( frameIterator.next(), true );
		final Collection< Spot > sourceSpots = new ArrayList< Spot >();
		for ( final Spot spot : iterable )
		{
			sourceSpots.add( spot );
		}

		// Loop over frames, and link the source spots to spots in the next
		// frame.
		double progress = 0;
		while ( frameIterator.hasNext() )
		{
			final Integer frame = frameIterator.next();
			final Iterator< Spot > it = spots.iterator( frame, true );
			SOURCE_LOOP: for ( final Spot source : sourceSpots )
			{
				/*
				 * Add the source to the graph, if it is not already done (doing
				 * it several time is not a problem: it's backed up by a Set).
				 */
				graph.addVertex( source );
				// Finds 2 targets.
				for ( int i = 0; i < 2; i++ )
				{
					if ( it.hasNext() )
					{
						final Spot target = it.next();
						// You must add it as vertex before creating the link.
						graph.addVertex( target );
						// This is how we create a link.
						final DefaultWeightedEdge edge = graph.addEdge( source, target );
						// We get the edge back, and set its weight through:
						if ( null != edge )
						{
							graph.setEdgeWeight( edge, 3.14 );
							/*
							 * Edge can be null if a link already exists between
							 * the two spots.
							 */
						}
					}
					else
					{
						break SOURCE_LOOP;
					}
				}
			}

			// Regenerate source list for next frame.
			sourceSpots.clear();
			for ( final Spot spot : spots.iterable( frame, true ) )
			{
				sourceSpots.add( spot );
			}

			progress += 1;
			logger.setProgress( progress / frames.size() );
		}
		return true;
	}

	@Override
	public String getErrorMessage()
	{
		return errorMessage;
	}

	@Override
	public void setNumThreads()
	{
		// Ignored. We do not multithreading here.
	}

	@Override
	public void setNumThreads( final int numThreads )
	{
		// Ignored.
	}

	@Override
	public int getNumThreads()
	{
		return 1;
	}

	@Override
	public void setLogger( final Logger logger )
	{
// Just store the instance for later use.
		this.logger = logger;
	}

}
