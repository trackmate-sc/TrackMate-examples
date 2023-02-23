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

import fiji.plugin.trackmate.Spot;
import fiji.plugin.trackmate.features.spot.AbstractSpotFeatureAnalyzer;
import net.imglib2.type.numeric.RealType;

public class MeanStdIntensityRatioSpotAnalyzer< T extends RealType< T > > extends AbstractSpotFeatureAnalyzer< T >
{

	@Override
	public void process( final Spot spot )
	{
		/*
		 * Get the feature values created by the other spot analyzer for the
		 * first channel only.
		 * 
		 * These values will be null if the spot intensity analyzer has not been
		 * called before. This is controlled via the priority flag in the
		 * annotation of the factory,
		 * 
		 * The channel number for feature keys are 1-based, so we need to add to
		 * the channel number we received in the constructor.
		 */
		final Double mean = spot.getFeature( "MEAN_INTENSITY_CH1" );
		final Double std = spot.getFeature( "STD_INTENSITY_CH1" );

		// Compute mean / std.
		final double ratio = mean.doubleValue() / std.doubleValue();

		// Store results.
		final String featureName = MeanStdIntensityRatioSpotAnalyzerFactory.MEAN_OVER_STD;
		spot.putFeature( featureName, Double.valueOf( ratio ) );

		// That's it!
	}
}
