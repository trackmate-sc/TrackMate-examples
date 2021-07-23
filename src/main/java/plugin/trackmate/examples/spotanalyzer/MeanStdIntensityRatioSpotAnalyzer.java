package plugin.trackmate.examples.spotanalyzer;

import fiji.plugin.trackmate.Spot;
import fiji.plugin.trackmate.features.spot.AbstractSpotFeatureAnalyzer;
import net.imglib2.type.numeric.RealType;

public class MeanStdIntensityRatioSpotAnalyzer< T extends RealType< T > > extends AbstractSpotFeatureAnalyzer< T >
{

	private final int channel;

	public MeanStdIntensityRatioSpotAnalyzer( final int channel )
	{
		this.channel = channel;
	}

	@Override
	public void process( final Spot spot )
	{
		/*
		 * Get the feature values created by the other spot analyzer. The
		 * channel number for feature keys are 1-based, so we need to add to the
		 * channel number we received in the constructor.
		 */
		final Double mean = spot.getFeature( "MEAN_INTENSITY_CH" + ( channel + 1 ) );
		final Double std = spot.getFeature( "STD_INTENSITY_CH" + ( channel + 1 ) );
		if ( std == null || mean == null )
		{
			// Safeguard.
			return;
		}

		// Compute mean / std.
		final double ratio = mean.doubleValue() / std.doubleValue();

		// Store results.
		spot.putFeature( MeanStdIntensityRatioSpotAnalyzerFactory.RELATIVE_INTENSITY + ( channel + 1 ), Double.valueOf( ratio ) );

		// That's it!
	}
}
