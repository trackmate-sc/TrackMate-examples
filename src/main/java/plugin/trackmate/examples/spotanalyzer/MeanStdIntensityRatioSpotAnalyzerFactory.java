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
