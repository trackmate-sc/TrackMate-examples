package plugin.trackmate.examples.detector;

import static fiji.plugin.trackmate.detection.DetectorKeys.KEY_DO_MEDIAN_FILTERING;
import static fiji.plugin.trackmate.detection.DetectorKeys.KEY_DO_SUBPIXEL_LOCALIZATION;
import static fiji.plugin.trackmate.detection.DetectorKeys.KEY_RADIUS;
import static fiji.plugin.trackmate.detection.DetectorKeys.KEY_TARGET_CHANNEL;
import static fiji.plugin.trackmate.detection.DetectorKeys.KEY_THRESHOLD;
import static fiji.plugin.trackmate.util.TMUtils.checkMapKeys;
import static fiji.plugin.trackmate.util.TMUtils.checkParameter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ij.ImageJ;
import ij.ImagePlus;

import javax.swing.ImageIcon;

import net.imglib2.Interval;
import net.imglib2.RandomAccessible;
import net.imglib2.type.NativeType;
import net.imglib2.type.numeric.RealType;
import net.imglib2.view.Views;

import org.scijava.plugin.Plugin;

import fiji.plugin.trackmate.Model;
import fiji.plugin.trackmate.Settings;
import fiji.plugin.trackmate.TrackMatePlugIn_;
import fiji.plugin.trackmate.detection.DogDetector;
import fiji.plugin.trackmate.detection.DogDetectorFactory;
import fiji.plugin.trackmate.detection.SpotDetector;
import fiji.plugin.trackmate.detection.SpotDetectorFactory;
import fiji.plugin.trackmate.gui.ConfigurationPanel;
import fiji.plugin.trackmate.util.TMUtils;

@Plugin( type = SpotDetectorFactory.class )
public class PreProcessingDoGDetectorFactory< T extends RealType< T > & NativeType< T >> extends DogDetectorFactory< T >
{

	private static final String INFO_TEXT = "<html>This segmenter adds preprocessing to the DoG detector.</html>";

	private static final String KEY = "PREPROCESSING_DOG_DETECTOR";

	private static final String NAME = "Pre-processing DoG Detector";

	static final String KEY_BG_RADIUS = "KEY_BG_RADIUS";

	@Override
	public String getInfoText()
	{
		return INFO_TEXT;
	}

	@Override
	public ImageIcon getIcon()
	{
		return null;
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
	public SpotDetector< T > getDetector( final Interval interval, final int frame )
	{
		final double radius = ( Double ) settings.get( KEY_RADIUS );
		final double threshold = ( Double ) settings.get( KEY_THRESHOLD );
		final boolean doMedian = ( Boolean ) settings.get( KEY_DO_MEDIAN_FILTERING );
		final boolean doSubpixel = ( Boolean ) settings.get( KEY_DO_SUBPIXEL_LOCALIZATION );
		final double bgRadius = ( Double ) settings.get( KEY_BG_RADIUS );
		final double[] calibration = TMUtils.getSpatialCalibration( img );

		RandomAccessible< T > imFrame;
		final int cDim = TMUtils.findCAxisIndex( img );
		if ( cDim < 0 )
		{
			imFrame = img;
		}
		else
		{
			// In ImgLib2, dimensions are 0-based.
			final int channel = ( Integer ) settings.get( KEY_TARGET_CHANNEL ) - 1;
			imFrame = Views.hyperSlice( img, cDim, channel );
		}

		int timeDim = TMUtils.findTAxisIndex( img );
		if ( timeDim >= 0 )
		{
			if ( cDim >= 0 && timeDim > cDim )
			{
				timeDim--;
			}
			imFrame = Views.hyperSlice( imFrame, timeDim, frame );
		}
		final DogDetector< T > detector = new PreProcessingDoGDetector< T >( imFrame, interval, calibration, radius, threshold, doSubpixel, doMedian, bgRadius );
		detector.setNumThreads( 1 );
		return detector;
	}

	@Override
	public ConfigurationPanel getDetectorConfigurationPanel( final Settings settings, final Model model )
	{
		// We return a simple configuration panel.
		return new PreProcessingDoGConfigurationPanel( settings.imp, INFO_TEXT, NAME, model );
	}

	/* This is unfortunately needed because LogDetectorFactory was not designed extensibly... */
	/** @inherit */
	@Override
	public boolean checkSettings( final Map< String, Object > settings )
	{
		boolean ok = true;
		final StringBuilder errorHolder = new StringBuilder();
		ok = ok & checkParameter( settings, KEY_TARGET_CHANNEL, Integer.class, errorHolder );
		ok = ok & checkParameter( settings, KEY_RADIUS, Double.class, errorHolder );
		ok = ok & checkParameter( settings, KEY_THRESHOLD, Double.class, errorHolder );
		ok = ok & checkParameter( settings, KEY_DO_MEDIAN_FILTERING, Boolean.class, errorHolder );
		ok = ok & checkParameter( settings, KEY_DO_SUBPIXEL_LOCALIZATION, Boolean.class, errorHolder );
		final List< String > mandatoryKeys = new ArrayList< String >();
		mandatoryKeys.add( KEY_TARGET_CHANNEL );
		mandatoryKeys.add( KEY_RADIUS );
		mandatoryKeys.add( KEY_THRESHOLD );
		mandatoryKeys.add( KEY_DO_MEDIAN_FILTERING );
		mandatoryKeys.add( KEY_DO_SUBPIXEL_LOCALIZATION );
		mandatoryKeys.add( KEY_BG_RADIUS );
		ok = ok & checkMapKeys( settings, mandatoryKeys, null, errorHolder );
		if ( !ok )
		{
			errorMessage = errorHolder.toString();
		}
		return ok;
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
