package plugin.trackmate.examples.spotanalyzer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;

import org.scijava.plugin.Plugin;

import fiji.plugin.trackmate.Dimension;
import fiji.plugin.trackmate.TrackMatePlugIn;
import fiji.plugin.trackmate.features.spot.SpotAnalyzer;
import fiji.plugin.trackmate.features.spot.SpotAnalyzerFactory;
import ij.ImageJ;
import ij.ImagePlus;
import net.imagej.ImgPlus;
import net.imglib2.type.NativeType;
import net.imglib2.type.numeric.RealType;

@Plugin( type = SpotAnalyzerFactory.class, priority = -1. )
public class MeanStdIntensityRatioSpotAnalyzerFactory< T extends RealType< T > & NativeType< T >> implements SpotAnalyzerFactory< T >
{

	private static final String KEY = "MEAN_OVER_STD";

	public static final String MEAN_OVER_STD = "MEAN_OVER_STD_CH1";

	public static final List< String > FEATURES = new ArrayList< >( 1 );

	private static final Map< String, Boolean > IS_INT = new HashMap< >( 1 );

	public static final Map< String, String > FEATURE_NAMES = new HashMap< >( 1 );

	public static final Map< String, String > FEATURE_SHORT_NAMES = new HashMap< >( 1 );

	public static final Map< String, Dimension > FEATURE_DIMENSIONS = new HashMap< >( 1 );

	private static final String NAME = "Mean over std intensity ratio ch1";

	static
	{
		FEATURES.add( MEAN_OVER_STD );
		IS_INT.put( MEAN_OVER_STD, false );
		FEATURE_SHORT_NAMES.put( MEAN_OVER_STD, "Mean / std ch1" );
		FEATURE_NAMES.put( MEAN_OVER_STD, NAME );
		FEATURE_DIMENSIONS.put( MEAN_OVER_STD, Dimension.NONE );
	}

	@Override
	public String getName()
	{
		return NAME;
	}

	@Override
	public String getKey()
	{
		return KEY;
	}

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
	
	@Override
	public Map< String, Boolean > getIsIntFeature()
	{
		return IS_INT;
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
	public boolean isManualFeature()
	{
		return false;
	}


	@Override
	public SpotAnalyzer< T > getAnalyzer( final ImgPlus< T > img, final int frame, final int channel )
	{
		// Don't make an analyzer for other channels than the first.
		if ( channel != 0 )
			return SpotAnalyzer.dummyAnalyzer();

		return new MeanStdIntensityRatioSpotAnalyzer<>();
	}

	/*
	 * MAIN METHOD
	 */

	public static void main( final String[] args )
	{
		ImageJ.main( args );
		new ImagePlus( "samples/FakeTracks.tif" ).show();
		new TrackMatePlugIn().run( "" );
	}

}
