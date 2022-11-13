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
package plugin.trackmate.examples.edgeanalyzer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
import fiji.plugin.trackmate.TrackMatePlugIn;
import fiji.plugin.trackmate.features.edges.EdgeAnalyzer;
import ij.ImageJ;
import ij.ImagePlus;

@Plugin( type = EdgeAnalyzer.class )
public class EdgeAngleAnalyzer implements EdgeAnalyzer
{

	// The string key that identifies our analyzer.
	private static final String KEY = "Edge angle";

	// The only feature we compute here.
	private static final String EDGE_ANGLE = "EDGE_ANGLE";

	private static final List< String > FEATURES = new ArrayList< >( 1 );

	private static final Map< String, Boolean > IS_INT = new HashMap< >( 1 );

	public static final Map< String, String > FEATURE_NAMES = new HashMap< >( 1 );

	public static final Map< String, String > FEATURE_SHORT_NAMES = new HashMap< >( 1 );

	public static final Map< String, Dimension > FEATURE_DIMENSIONS = new HashMap< >( 1 );

	// Let's set the feature list, names, short names and dimensions.
	static
	{
		FEATURES.add( EDGE_ANGLE );
		IS_INT.put( EDGE_ANGLE,  false );
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

	// Return a user-compliant name for this analyzer.
	@Override
	public String getName()
	{
		return "Edge angle";
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
		new ImagePlus( "samples/FakeTracks.tif" ).show();
		new TrackMatePlugIn().run( "" );
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
