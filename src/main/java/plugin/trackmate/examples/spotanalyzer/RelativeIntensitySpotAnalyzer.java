package plugin.trackmate.examples.spotanalyzer;

import fiji.plugin.trackmate.Model;
import fiji.plugin.trackmate.Spot;
import fiji.plugin.trackmate.SpotCollection;
import fiji.plugin.trackmate.features.spot.SpotAnalyzer;

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
		 * not. So the spot features must be calculated over ALL the spots.
		 */

		final SpotCollection sc = model.getSpots();
		final Iterable< Spot > spotIt = sc.iterable( frame, false );

		/*
		 * Compute the mean intensity for all these spots.
		 */

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
