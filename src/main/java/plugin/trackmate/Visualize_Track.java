package plugin.trackmate;

import fiji.util.gui.GenericDialogPlus;
import ij.IJ;
import ij.ImageJ;
import ij.ImagePlus;
import ij.io.OpenDialog;
import ij.plugin.PlugIn;
import ij.process.Blitter;
import ij.process.ImageProcessor;

import java.awt.Rectangle;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.scijava.util.XML;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Visualize_Track implements PlugIn {

	@Override
	public void run(String arg) {
		if (arg == null || "".equals(arg)) {
			final OpenDialog dialog = new OpenDialog("Please specify the .xml file");
			final String dir = dialog.getDirectory();
			final String file = dialog.getFileName();
			if (dir == null || file == null) return;
			arg = dir + file;
		}
		try {
			final XML xml = new XML(new File(arg));
			final String[] trackNames = getTrackNames(xml);
			final GenericDialogPlus dialog = new GenericDialogPlus("Track Visualisation");
			dialog.addChoice("Track", trackNames, trackNames[0]);
			dialog.addFileField("Original movie", "");
			dialog.addFileField("Pre-processed movie", "");
			dialog.showDialog();
			if (dialog.wasCanceled()) return;

			final String trackName = dialog.getNextChoice();
			final String originalPath = dialog.getNextString();
			final String preprocessedPath = dialog.getNextString();
			final ImagePlus original = IJ.openImage(originalPath);
			final ImagePlus preprocessed = IJ.openImage(preprocessedPath);

			final ImagePlus kymograph = generateKymograph(xml, trackName, original, preprocessed);
			kymograph.show();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private String[] getTrackNames(XML xml) {
		final List<String> names = new ArrayList<String>();
		final NodeList list = xml.xpath("/TrackMate/Model/AllTracks/Track[@TRACK_ID = //TrackMate/Model/FilteredTracks/TrackID/@TRACK_ID]/@name");
		for (int i = 0; i < list.getLength(); i++) {
			final Node node = list.item(i);
			names.add(node.getNodeValue());
		}

		return names.toArray(new String[0]);
	}

	private ImagePlus generateKymograph(XML xml, String trackName,
			ImagePlus original, ImagePlus preprocessed) {
		final String matchingEdge = "/TrackMate/Model/AllTracks/Track[@name = \"" + trackName + "\"]/Edge";
		// testing for SPOT_SOURCE_ID and SPOT_TARGET_ID explicitly is twice as slow, hence we test for all attributes (@*)
		final NodeList list = xml.xpath("/TrackMate/Model/AllSpots/SpotsInFrame/Spot[@ID = " + matchingEdge + "/@*]");

		// first pass: determine maximal values
		int cellWidth = 0;
		int minFrame = Integer.MAX_VALUE;
		int maxFrame = 0;
		for (int i = 0; i < list.getLength(); i++) {
			final Node spot = list.item(i);
			final NamedNodeMap attrs = spot.getAttributes();
			final double radius = Double.parseDouble(attrs.getNamedItem("RADIUS").getNodeValue());
			if (cellWidth < 2 * radius) cellWidth = (int) Math.ceil(radius * 2);
			final int frame = Integer.parseInt(attrs.getNamedItem("FRAME").getNodeValue());
			if (minFrame > frame) minFrame = frame;
			if (maxFrame < frame) maxFrame = frame;
		}

		final int margin = 2;
		final int cellSpacing = cellWidth + margin;
		final ImagePlus result = IJ.createImage("Kymograph", "8-bit white",
				cellSpacing * (1 + maxFrame - minFrame), cellSpacing * 2, 1);
		for (int i = 0; i < list.getLength(); i++) {
			final Node spot = list.item(i);
			final NamedNodeMap attrs = spot.getAttributes();
			final double radius = Double.parseDouble(attrs.getNamedItem("RADIUS").getNodeValue());
			final double x = Double.parseDouble(attrs.getNamedItem("POSITION_X").getNodeValue());
			final double y = Double.parseDouble(attrs.getNamedItem("POSITION_Y").getNodeValue());
			final int frame = Integer.parseInt(attrs.getNamedItem("FRAME").getNodeValue());
			paste(result.getProcessor(), 1 + cellSpacing * frame, 1,
					original.getStack().getProcessor(frame + 1), (int) Math.round(x - radius), (int) Math.round(y - radius), cellWidth, cellWidth);
			paste(result.getProcessor(), 1 + cellSpacing * frame, 1 + cellSpacing,
					preprocessed.getStack().getProcessor(frame + 1), (int) Math.round(x - radius), (int) Math.round(y - radius), cellWidth, cellWidth);
		}
		return result;
	}

	private void paste(ImageProcessor target, int targetX, int targetY,
			ImageProcessor source, int sourceX, int sourceY, int width,
			int height) {
		source.setRoi(new Rectangle(sourceX, sourceY, width, height));
		target.copyBits(source.crop(), targetX, targetY, Blitter.COPY);
	}

	public static void main(String[] args) throws Exception {
		//new Visualize_Track().run("../../../samples/FakeTracks-2.xml");
		final XML xml = new XML(new File("../../../samples/FakeTracks-2.xml"));
		System.err.println(Arrays.toString(new Visualize_Track().getTrackNames(xml)));
		new ImageJ();
		new Visualize_Track().generateKymograph(xml, "Track_0", IJ.openImage("FakeTracks.tif"), IJ.openImage("translated.tif")).show();
	}
}
