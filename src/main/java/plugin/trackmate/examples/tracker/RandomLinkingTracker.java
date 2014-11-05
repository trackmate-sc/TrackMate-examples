package plugin.trackmate.examples.tracker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NavigableSet;
import java.util.Random;
import java.util.Set;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

public class RandomLinkingTracker implements SpotTracker
{

	/**
	 * This is how we return tracking results: as a JGraphT graph.
	 */
	private SimpleWeightedGraph< Spot, DefaultWeightedEdge > graph;

	private final SpotCollection sc;

	private final int maxLinks;

	private String errorMessage;

	public RandomLinkingTracker( final SpotCollection spots, final int maxLinks )
	{
		this.sc = spots;
		this.maxLinks = maxLinks;
	}

	@Override
	public SimpleWeightedGraph< Spot, DefaultWeightedEdge > getResult()
	{
		return graph;
	}

	@Override
	public boolean checkInput()
	{
		if ( sc == null )
		{
			errorMessage = "SpotCollection is null.\n";
			return false;
		}

		if ( maxLinks < 0 )
		{
			errorMessage = "Max links per frame is negative.\n";
			return false;
		}

		return true;
	}

	@Override
	public boolean process()
	{
		final Random ran = new Random();
		// First create a new empty graph.
		graph = new SimpleWeightedGraph< Spot, DefaultWeightedEdge >( DefaultWeightedEdge.class );

		// Loop over frames
		final NavigableSet< Integer > frames = sc.keySet();
		Set< Spot > previousSpots = null;

		for ( final Integer frame : frames )
		{
			if ( null == previousSpots )
			{
				// Collect some spots.
				previousSpots = pickNRandomSpot( frame );
				// And loop to next frame.
				continue;
			}

			final Set< Spot > targetSpots = pickNRandomSpot( frame );
			final Iterator< Spot > itp = previousSpots.iterator();
			final Iterator< Spot > itt = targetSpots.iterator();
			while ( itp.hasNext() && itt.hasNext() )
			{
				// Create link between 2 random spots.
				final DefaultWeightedEdge edge = graph.addEdge( itp.next(), itt.next() );
				// Give a random weight.
				graph.setEdgeWeight( edge, Math.abs( ran.nextDouble() ) );
			}

			previousSpots = targetSpots;
		}

		return true;
	}

	private final Set< Spot > pickNRandomSpot( final int frame )
	{
		// We only play with spots marked as visible.
		final int nSpots = sc.getNSpots( frame, true );

		// Collect them.
		final List< Spot > spots = new ArrayList< Spot >( nSpots );
		for ( final Spot spot : sc.iterable( frame, true ) )
		{
			spots.add( spot );
		}

		// Shuffle them
		Collections.shuffle( spots );

		// Pick the first ones
		final Set< Spot > randomSpots = new HashSet< Spot >( maxLinks );
		final int n = Math.min( maxLinks, nSpots );
		for ( int i = 0; i < n; i++ )
		{
			randomSpots.add( spots.get( i ) );
		}
		return randomSpots;
	}

	@Override
	public String getErrorMessage()
	{
		return errorMessage;
	}

	@Override
	public void setLogger( final Logger logger )
	{
		// Ignored.
	}

	@Override
	public void setNumThreads()
	{
		// Ignored.
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

}
