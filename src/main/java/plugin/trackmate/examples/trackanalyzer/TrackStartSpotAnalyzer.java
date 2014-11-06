package plugin.trackmate.examples.trackanalyzer;

import ij.ImageJ;
import ij.ImagePlus;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.ImageIcon;

import org.scijava.plugin.Plugin;

@Plugin( type = TrackAnalyzer.class )
public class TrackStartSpotAnalyzer implements TrackAnalyzer
{

	private static final String KEY = "TRACK_START_SPOT_ANALYZER";

	public static final String TRACK_START_X = "TRACK_START_X";

	public static final String TRACK_START_Y = "TRACK_START_Y";

	public static final String TRACK_START_Z = "TRACK_START_Z";

	public static final String TRACK_STOP_X = "TRACK_STOP_X";

	public static final String TRACK_STOP_Y = "TRACK_STOP_Y";

	public static final String TRACK_STOP_Z = "TRACK_STOP_Z";

	private static final List< String > FEATURES = new ArrayList< String >( 6 );

	private static final Map< String, Boolean > IS_INT = new HashMap< String, Boolean >( 6 );

	private static final Map< String, String > FEATURE_SHORT_NAMES = new HashMap< String, String >( 6 );

	private static final Map< String, String > FEATURE_NAMES = new HashMap< String, String >( 6 );

	private static final Map< String, Dimension > FEATURE_DIMENSIONS = new HashMap< String, Dimension >( 6 );

	static
	{
		FEATURES.add( TRACK_START_X );
		FEATURES.add( TRACK_START_Y );
		FEATURES.add( TRACK_START_Z );
		FEATURES.add( TRACK_STOP_X );
		FEATURES.add( TRACK_STOP_Y );
		FEATURES.add( TRACK_STOP_Z );

		IS_INT.put( TRACK_START_X, false );
		IS_INT.put( TRACK_START_Y, false );
		IS_INT.put( TRACK_START_Z, false );
		IS_INT.put( TRACK_STOP_X, false );
		IS_INT.put( TRACK_STOP_Y, false );
		IS_INT.put( TRACK_STOP_Z, false );

		FEATURE_NAMES.put( TRACK_START_X, "Track start X" );
		FEATURE_NAMES.put( TRACK_START_Y, "Track start Y" );
		FEATURE_NAMES.put( TRACK_START_Z, "Track start Z" );
		FEATURE_NAMES.put( TRACK_STOP_X, "Track stop X" );
		FEATURE_NAMES.put( TRACK_STOP_Y, "Track stop Y" );
		FEATURE_NAMES.put( TRACK_STOP_Z, "Track stop Z" );

		FEATURE_SHORT_NAMES.put( TRACK_START_X, "X start" );
		FEATURE_SHORT_NAMES.put( TRACK_START_Y, "Y start" );
		FEATURE_SHORT_NAMES.put( TRACK_START_Z, "Z start" );
		FEATURE_SHORT_NAMES.put( TRACK_STOP_X, "X stop" );
		FEATURE_SHORT_NAMES.put( TRACK_STOP_Y, "Y stop" );
		FEATURE_SHORT_NAMES.put( TRACK_STOP_Z, "Z stop" );

		FEATURE_DIMENSIONS.put( TRACK_START_X, Dimension.POSITION );
		FEATURE_DIMENSIONS.put( TRACK_START_Y, Dimension.POSITION );
		FEATURE_DIMENSIONS.put( TRACK_START_Z, Dimension.POSITION );
		FEATURE_DIMENSIONS.put( TRACK_STOP_X, Dimension.POSITION );
		FEATURE_DIMENSIONS.put( TRACK_STOP_Y, Dimension.POSITION );
		FEATURE_DIMENSIONS.put( TRACK_STOP_Z, Dimension.POSITION );
	}

	private long processingTime;

	/*
	 * TRACKMODULE METHODS
	 */

	@Override
	public String getKey()
	{
		return KEY;
	}

	@Override
	public String getName()
	{
		return "Track starting and end points position";
	}

	// We do not use info texts for any feature actually.
	@Override
	public String getInfoText()
	{
		return "";
	}

	// The same: we don't use icons for features.
	@Override
	public ImageIcon getIcon()
	{
		return null;
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
	 * BENCHMARK METHOD
	 */

	@Override
	public long getProcessingTime()
	{
		return processingTime;
	}

	/*
	 * TRACKANALYZER METHODS
	 */

	@Override
	public void process( final Collection< Integer > trackIDs, final Model model )
	{
		// The feature model where we store the feature values:
		final FeatureModel fm = model.getFeatureModel();

		// Loop over all the tracks we have to process.
		for ( final Integer trackID : trackIDs )
		{
			// The tracks are indexed by their ID. Here is how to get their
			// content:
			final Set< Spot > spots = model.getTrackModel().trackSpots( trackID );
			// Or .trackEdges( trackID ) if you want the edges.

			// This set is NOT ordered. If we want the first one and last one we
			// have to sort them:
			final Comparator< Spot > comparator = Spot.frameComparator;
			final List< Spot > sorted = new ArrayList< Spot >( spots );
			Collections.sort( sorted, comparator );

			// Extract and store feature values.
			final Spot first = sorted.get( 0 );
			fm.putTrackFeature( trackID, TRACK_START_X, Double.valueOf( first.getDoublePosition( 0 ) ) );
			fm.putTrackFeature( trackID, TRACK_START_Y, Double.valueOf( first.getDoublePosition( 1 ) ) );
			fm.putTrackFeature( trackID, TRACK_START_Z, Double.valueOf( first.getDoublePosition( 2 ) ) );

			final Spot last = sorted.get( sorted.size() - 1 );
			fm.putTrackFeature( trackID, TRACK_STOP_X, Double.valueOf( last.getDoublePosition( 0 ) ) );
			fm.putTrackFeature( trackID, TRACK_STOP_Y, Double.valueOf( last.getDoublePosition( 1 ) ) );
			fm.putTrackFeature( trackID, TRACK_STOP_Z, Double.valueOf( last.getDoublePosition( 2 ) ) );

			// Et voilà!
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

	@Override
	public Map<String, Boolean> getIsIntFeature() {
		return Collections.unmodifiableMap( IS_INT );
	}

	@Override
	public boolean isManualFeature() {
		return false;
	}
}
