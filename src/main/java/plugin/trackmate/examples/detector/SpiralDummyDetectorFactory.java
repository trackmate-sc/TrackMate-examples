package plugin.trackmate.examples.detector;

import java.util.Collections;
import java.util.Map;

import javax.swing.ImageIcon;

import org.jdom2.Element;
import org.scijava.plugin.Plugin;

import fiji.plugin.trackmate.Model;
import fiji.plugin.trackmate.Settings;
import fiji.plugin.trackmate.TrackMatePlugIn;
import fiji.plugin.trackmate.detection.SpotDetector;
import fiji.plugin.trackmate.detection.SpotDetectorFactory;
import fiji.plugin.trackmate.gui.components.ConfigurationPanel;
import fiji.plugin.trackmate.util.TMUtils;
import ij.ImageJ;
import ij.ImagePlus;
import net.imagej.ImgPlus;
import net.imglib2.Interval;
import net.imglib2.type.NativeType;
import net.imglib2.type.numeric.RealType;

@Plugin( type = SpotDetectorFactory.class )
public class SpiralDummyDetectorFactory< T extends RealType< T > & NativeType< T >> implements SpotDetectorFactory< T >
{

	static final String INFO_TEXT = "<html>This is a dummy detector that creates spirals made of spots emerging from the center of the ROI. The actual image content is not used.</html>";

	private static final String KEY = "DUMMY_DETECTOR_SPIRAL";

	static final String NAME = "Dummy detector in spirals";

	private double[] calibration;

	private String errorMessage;

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
		return new SpiralDummyDetector< >( interval, calibration, frame );
	}

	@Override
	public boolean setTarget( final ImgPlus< T > img, final Map< String, Object > settings )
	{
		/*
		 * Well, we do not care for the image at all. We just need to get the
		 * physical calibration and there is a helper method for that.
		 */
		this.calibration = TMUtils.getSpatialCalibration( img );
		// True means that the settings map is OK.
		return true;
	}

	@Override
	public String getErrorMessage()
	{
		/*
		 * If something is not right when calling #setTarget (i.e. the settings
		 * maps is not right), this is how we get an error message.
		 */
		return errorMessage;
	}

	@Override
	public boolean marshall( final Map< String, Object > settings, final Element element )
	{
		/*
		 * This where you save the settings map to a JDom element. Since we do
		 * not have parameters, we have nothing to do.
		 */
		return true;
	}

	@Override
	public boolean unmarshall( final Element element, final Map< String, Object > settings )
	{
		/*
		 * The same goes for loading: there is nothing to load.
		 */
		return true;
	}

	@Override
	public ConfigurationPanel getDetectorConfigurationPanel( final Settings settings, final Model model )
	{
		// We return a simple configuration panel.
		return new DummyDetectorSpiralConfigurationPanel();
	}

	@Override
	public Map< String, Object > getDefaultSettings()
	{
		/*
		 * We just have to return a new empty map.
		 */
		return Collections.emptyMap();
	}

	@Override
	public boolean checkSettings( final Map< String, Object > settings )
	{
		/*
		 * Since we have no settings, we just have to test that we received the
		 * empty map. Otherwise we generate an error.
		 */
		if ( settings.isEmpty() ) { return true; }
		errorMessage = "Expected the settings map to be empty, but it was not: " + settings + '\n';
		return false;
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
