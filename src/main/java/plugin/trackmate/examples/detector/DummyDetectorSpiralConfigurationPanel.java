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
package plugin.trackmate.examples.detector;

import java.awt.Dimension;
import java.util.Collections;
import java.util.Map;

import javax.swing.JLabel;

import fiji.plugin.trackmate.gui.Fonts;
import fiji.plugin.trackmate.gui.components.ConfigurationPanel;

public class DummyDetectorSpiralConfigurationPanel extends ConfigurationPanel
{

	private static final long serialVersionUID = 1L;

	public DummyDetectorSpiralConfigurationPanel()
	{
		/*
		 * We have no settings, so nothing to tune. Just display an explanatory
		 * text.
		 */

		final JLabel label1 = new JLabel( SpiralDummyDetectorFactory.NAME );
		label1.setFont( Fonts.BIG_FONT );
		add( label1 );

		final JLabel label2 = new JLabel( SpiralDummyDetectorFactory.INFO_TEXT );
		label2.setFont( Fonts.FONT );
		label2.setPreferredSize( new Dimension( 200, 300 ) );
		add( label2 );

	}

	@Override
	public void setSettings( final Map< String, Object > settings )
	{
		/*
		 * With this method, you can receive the settings that were set
		 * elsewhere (e.g. by loading from a file) and display these settings on
		 * this panel. Since we have not settings, there is nothing to do.
		 */
	}

	@Override
	public Map< String, Object > getSettings()
	{
		/*
		 * We are expected to return the settings the user set here. In our case
		 * we must return an empty settings map.
		 */
		return Collections.emptyMap();
	}

	@Override
	public void clean() {}

}
