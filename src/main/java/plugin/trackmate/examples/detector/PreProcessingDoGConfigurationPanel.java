package plugin.trackmate.examples.detector;

import static fiji.plugin.trackmate.gui.TrackMateWizard.FONT;
import ij.ImagePlus;

import java.util.Map;

import javax.swing.JLabel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import fiji.plugin.trackmate.Model;
import fiji.plugin.trackmate.gui.panels.components.JNumericTextField;
import fiji.plugin.trackmate.gui.panels.detector.DogDetectorConfigurationPanel;
import fiji.util.NumberParser;

public class PreProcessingDoGConfigurationPanel extends DogDetectorConfigurationPanel
{

	private static final long serialVersionUID = 1L;

	private static final double DEFAULT_BG_RADIUS = 30;
	private JNumericTextField radius;

	public PreProcessingDoGConfigurationPanel(ImagePlus imp, String infoText, String detectorName, Model model)
	{
		super(imp, infoText, detectorName, model);

		addLabel("<html>Radius for<br />background subtraction:</html>", 360, 16, -16);
		radius = addTextField(360, 168, 383, 208, 5, 5);

		layout.putConstraint(SpringLayout.NORTH, jButtonRefresh, 384, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.SOUTH, jButtonRefresh, 408, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.NORTH, btnPreview, 384, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.SOUTH, btnPreview, 408, SpringLayout.NORTH, this);
	}

	private JNumericTextField addTextField(final int north, final int west, final int south, final int east, final int columns, final int initialValue) {
		final JNumericTextField field = new JNumericTextField();
		layout.putConstraint(SpringLayout.NORTH, field, north, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, field, west, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.SOUTH, field, south, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.EAST, field, east, SpringLayout.WEST, this);
		field.setHorizontalAlignment(SwingConstants.CENTER);
		field.setColumns(columns);
		field.setText("" + initialValue);
		add(field);
		field.setFont(FONT);
		return field;
	}

	protected JLabel addLabel(final String text, int north, int west, int east)
	{
		final JLabel label = new JLabel();
		layout.putConstraint(SpringLayout.NORTH, label, north, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, label, west, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, label, east, SpringLayout.EAST, this);
		add(label);
		label.setText(text);
		label.setFont(FONT);
		return label;
	}

	@Override
	public void setSettings( final Map< String, Object > settings )
	{
		/*
		 * With this method, you can receive the settings that were set
		 * elsewhere (e.g. by loading from a file) and display these settings on
		 * this panel. Since we have not settings, there is nothing to do.
		 */
		Object bgRadius = settings.get(PreProcessingDoGDetectorFactory.KEY_BG_RADIUS);
		radius.setText("" + (bgRadius == null ? DEFAULT_BG_RADIUS : bgRadius));
		super.setSettings( settings );
	}

	@Override
	public Map< String, Object > getSettings()
	{
		final Map< String, Object > settings = super.getSettings();
		double bgRadius = NumberParser.parseDouble(radius.getText());
		settings.put(PreProcessingDoGDetectorFactory.KEY_BG_RADIUS, bgRadius);
		return settings;
	}

}
