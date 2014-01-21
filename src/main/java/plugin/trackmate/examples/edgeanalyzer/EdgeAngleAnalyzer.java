package plugin.trackmate.examples.edgeanalyzer;

import ij.ImageJ;
import ij.ImagePlus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.scijava.plugin.Plugin;

import fiji.plugin.trackmate.Dimension;
import fiji.plugin.trackmate.FeatureModel;
import fiji.plugin.trackmate.Model;
import fiji.plugin.trackmate.Spot;
import fiji.plugin.trackmate.TrackMatePlugIn_;
import fiji.plugin.trackmate.features.edges.EdgeAnalyzer;

@Plugin( type = EdgeAnalyzer.class )
public class EdgeAngleAnalyzer implements EdgeAnalyzer
{

	private static final String KEY = "Edge angle";

	private static final String EDGE_ANGLE = "EDGE_ANGLE";

	private static final List< String > FEATURES = new ArrayList< String >( 1 );

	public static final Map< String, String > FEATURE_NAMES = new HashMap< String, String >( 1 );

	public static final Map< String, String > FEATURE_SHORT_NAMES = new HashMap< String, String >( 1 );

	public static final Map< String, Dimension > FEATURE_DIMENSIONS = new HashMap< String, Dimension >( 1 );

	static
	{
		FEATURES.add( EDGE_ANGLE );
		FEATURE_NAMES.put( EDGE_ANGLE, "Link angle" );
		FEATURE_SHORT_NAMES.put( EDGE_ANGLE, "Angle" );
		FEATURE_DIMENSIONS.put( EDGE_ANGLE, Dimension.ANGLE );
	}

	private long processingTime;

	/*
	 * TRACKMATEMODULE METHODS
	 */

	@Override
	public String getKey()
	{
		return KEY;
	}

	@Override
	public String getInfoText()
	{
		return "";
	}

	@Override
	public ImageIcon getIcon()
	{
		return null;
	}

	@Override
	public String getName()
	{
		return "Edge angle";
	}

	/*
	 * FEATUREANALYZER METHODS
	 */

	@Override
	public List< String > getFeatures()
	{
		return FEATURES;
	}

	@Override
	public Map< String, String > getFeatureShortNames()
	{
		return FEATURE_SHORT_NAMES;
	}

	@Override
	public Map< String, String > getFeatureNames()
	{
		return FEATURE_NAMES;
	}

	@Override
	public Map< String, Dimension > getFeatureDimensions()
	{
		return FEATURE_DIMENSIONS;
	}

	/*
	 * MULTITHREADED METHODS
	 */

	@Override
	public void setNumThreads()
	{
		// We ignore multithreading for this tutorial.
	}

	@Override
	public void setNumThreads( final int numThreads )
	{
		// We ignore multithreading for this tutorial.
	}

	@Override
	public int getNumThreads()
	{
		// We ignore multithreading for this tutorial.
		return 1;
	}

	/*
	 * BENCHMARK METHODS
	 */

	@Override
	public long getProcessingTime()
	{
		return processingTime;
	}

	/*
	 * EDGEANALYZER METHODS
	 */

	@Override
	public void process( final Collection< DefaultWeightedEdge > edges, final Model model )
	{
		final FeatureModel fm = model.getFeatureModel();
		for ( final DefaultWeightedEdge edge : edges )
		{
			final Spot source = model.getTrackModel().getEdgeSource( edge );
			final Spot target = model.getTrackModel().getEdgeTarget( edge );

			final double x1 = source.getDoublePosition( 0 );
			final double y1 = source.getDoublePosition( 1 );
			final double x2 = target.getDoublePosition( 0 );
			final double y2 = target.getDoublePosition( 1 );

			final double angle = Math.atan2( y2 - y1, x2 - x1 );
			fm.putEdgeFeature( edge, EDGE_ANGLE, Double.valueOf( angle ) );
		}
	}

	@Override
	public boolean isLocal()
	{
		return true;
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
