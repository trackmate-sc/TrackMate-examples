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
