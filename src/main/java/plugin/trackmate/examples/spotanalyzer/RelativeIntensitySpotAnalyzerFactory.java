package plugin.trackmate.examples.spotanalyzer;

import ij.ImageJ;
import ij.ImagePlus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;

import net.imglib2.meta.ImgPlus;
import net.imglib2.type.NativeType;
import net.imglib2.type.numeric.RealType;

import org.scijava.plugin.Plugin;

import fiji.plugin.trackmate.Dimension;
import fiji.plugin.trackmate.Model;
import fiji.plugin.trackmate.TrackMatePlugIn_;
import fiji.plugin.trackmate.features.spot.SpotAnalyzer;
import fiji.plugin.trackmate.features.spot.SpotAnalyzerFactory;

@Plugin( type = SpotAnalyzerFactory.class, priority = 1d )
public class RelativeIntensitySpotAnalyzerFactory< T extends RealType< T > & NativeType< T >> implements SpotAnalyzerFactory< T >
{

	private static final String KEY = "RELATIVE_INTENSITY";

	public static final String RELATIVE_INTENSITY = "RELATIVE_INTENSITY";

	public static final List< String > FEATURES = new ArrayList< String >( 1 );

	private static final Map< String, Boolean > IS_INT = new HashMap< String, Boolean >( 1 );

	public static final Map< String, String > FEATURE_NAMES = new HashMap< String, String >( 1 );

	public static final Map< String, String > FEATURE_SHORT_NAMES = new HashMap< String, String >( 1 );

	public static final Map< String, Dimension > FEATURE_DIMENSIONS = new HashMap< String, Dimension >( 1 );

	private static final String NAME = "Relative spot intensity";

	static
	{
		FEATURES.add( RELATIVE_INTENSITY );
		IS_INT.put( RELATIVE_INTENSITY, false );
		FEATURE_SHORT_NAMES.put( RELATIVE_INTENSITY, "Rel. int." );
		FEATURE_NAMES.put( RELATIVE_INTENSITY, "Relative intensity" );
		FEATURE_DIMENSIONS.put( RELATIVE_INTENSITY, Dimension.NONE );
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
		return NAME;
	}

	@Override
	public SpotAnalyzer< T > getAnalyzer( final Model model, final ImgPlus< T > img, final int frame, final int channel )
	{
		return new RelativeIntensitySpotAnalyzer< T >( model, frame );
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
