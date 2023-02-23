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
package plugin.trackmate.examples.tracker;

import java.util.Collections;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import org.jdom2.Element;
import org.scijava.plugin.Plugin;

import fiji.plugin.trackmate.Model;
import fiji.plugin.trackmate.SpotCollection;
import fiji.plugin.trackmate.TrackMatePlugIn;
import fiji.plugin.trackmate.gui.components.ConfigurationPanel;
import fiji.plugin.trackmate.tracking.SpotTracker;
import fiji.plugin.trackmate.tracking.SpotTrackerFactory;
import ij.ImageJ;

@Plugin( type = SpotTrackerFactory.class )
public class DrunkenCellDivisionTrackerFactory implements SpotTrackerFactory
{

	private String errorMessage;

	@Override
	public String getInfoText()
	{
		return "<html>This tracker randomly links one spot to two spots in the next frame.</html>";
	}

	@Override
	public ImageIcon getIcon()
	{
		return null;
	}

	@Override
	public String getKey()
	{
		return "DRUNKEN_CELL_DIVISION_TRACKER";
	}

	@Override
	public String getName()
	{
		return "Random cell division tracker";
	}

	@Override
	public SpotTracker create( final SpotCollection spots, final Map< String, Object > settings )
	{
		return new DrunkenCellDivisionTracker( spots );
	}

	@Override
	public ConfigurationPanel getTrackerConfigurationPanel( final Model model )
	{
		// Make a dummy one on the fly.
		final ConfigurationPanel cp = new ConfigurationPanel()
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void setSettings( final Map< String, Object > settings )
			{
				// Ignore.
			}

			@Override
			public Map< String, Object > getSettings()
			{
				// Return empty map.
				return Collections.emptyMap();
			}

			@Override
			public void clean() {}
		};

		cp.add( new JLabel( getName() ) );
		cp.add( new JLabel( "This tracker has no parameters to tune." ) );

		return cp;
	}

	@Override
	public boolean marshall( final Map< String, Object > settings, final Element element )
	{
		// No parameters = nothing to do.
		return true;
	}

	@Override
	public boolean unmarshall( final Element element, final Map< String, Object > settings )
	{
		// No parameters = nothing to do.
		return true;
	}

	@Override
	public String toString( final Map< String, Object > sm )
	{
		return "This tracker has no parameters to tune.";
	}

	@Override
	public Map< String, Object > getDefaultSettings()
	{
		return Collections.emptyMap();
	}

	@Override
	public boolean checkSettingsValidity( final Map< String, Object > settings )
	{
		// Empty is always right.
		return true;
	}

	@Override
	public String getErrorMessage()
	{
		return errorMessage;
	}

	public static void main( final String[] args )
	{
		ImageJ.main( args );
		new TrackMatePlugIn().run( "../TrackMate/samples/FakeTracks.tif" );
	}

	@Override
	public SpotTrackerFactory copy()
	{
		return new DrunkenCellDivisionTrackerFactory();
	}
}
