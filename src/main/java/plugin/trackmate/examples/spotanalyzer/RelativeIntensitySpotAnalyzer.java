package plugin.trackmate.examples.spotanalyzer;

import static plugin.trackmate.examples.spotanalyzer.RelativeIntensitySpotAnalyzerFactory.RELATIVE_INTENSITY;

import java.util.Iterator;

import fiji.plugin.trackmate.Model;
import fiji.plugin.trackmate.Spot;
import fiji.plugin.trackmate.SpotCollection;
import fiji.plugin.trackmate.features.spot.SpotAnalyzer;
import fiji.plugin.trackmate.features.spot.SpotIntensityAnalyzerFactory;

public class RelativeIntensitySpotAnalyzer< T > implements SpotAnalyzer< T >
{

	private final Model model;

	private final int frame;

	private String errorMessage;

	private long processingTime;

	public RelativeIntensitySpotAnalyzer( final Model model, final int frame )
	{
		this.model = model;
		this.frame = frame;
	}

	@Override
	public boolean checkInput()
	{
		return true;
	}

	@Override
	public boolean process()
	{
		/*
		 * Collect all the spots from the target frame. In a SpotAnalyzer, you
		 * cannot interrogate only visible spots, because spot features are
		 * typically used to determine whether spots are going to be visible or
		 * not. This happens in the GUI at the spot filtering stage: We are
		 * actually building a feature on which a filter can be applied. So the
		 * spot features must be calculated over ALL the spots.
		 */

		/*
		 * The spots are stored in a SpotCollection before they are tracked. The
		 * SpotCollection is the product of the detection step.
		 */
		final SpotCollection sc = model.getSpots();
		// 'false' means 'not only the visible spots, but all spots'.
		Iterator< Spot > spotIt = sc.iterator( frame, false );

		/*
		 * Compute the mean intensity for all these spots.
		 */

		double sum = 0;
		int n = 0;
		while ( spotIt.hasNext() )
		{
			final Spot spot = spotIt.next();
			// Collect the mean intensity in the spot radius.
			final double val = spot.getFeature( SpotIntensityAnalyzerFactory.MEAN_INTENSITY );
			sum += val;
			n++;
		}

		if ( n == 0 )
		{
			// Nothing to do here.
			return true;
		}

		final double mean = sum / n;

		/*
		 * Make a second pass to set the relative intensity of these spots with
		 * respect to the mean we just calculated.
		 */

		spotIt = sc.iterator( frame, false );
		while ( spotIt.hasNext() )
		{
			final Spot spot = spotIt.next();
			final double val = spot.getFeature( SpotIntensityAnalyzerFactory.MEAN_INTENSITY );
			final double relMean = val / mean;
			// Store the new feature in the spot
			spot.putFeature( RELATIVE_INTENSITY, Double.valueOf( relMean ) );
		}

		return true;
	}

	@Override
	public String getErrorMessage()
	{
		return errorMessage;
	}

	@Override
	public long getProcessingTime()
	{
		return processingTime;
	}

}
