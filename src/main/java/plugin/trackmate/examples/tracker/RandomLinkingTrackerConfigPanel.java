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
package plugin.trackmate.examples.tracker;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;

import fiji.plugin.trackmate.gui.components.ConfigurationPanel;

public class RandomLinkingTrackerConfigPanel extends ConfigurationPanel
{

	private static final long serialVersionUID = 1L;

	private JFormattedTextField maxTF;

	public RandomLinkingTrackerConfigPanel()
	{
		initGui();
	}

	private void initGui()
	{
		add( new JLabel( "Max number of links per track:" ) );
		maxTF = new JFormattedTextField( Integer.valueOf( RandomLinkingTrackerFactory.DEFAULT_MAX_LINKS_PER_FRAME ) );
		add( maxTF );
	}

	@Override
	public void setSettings( final Map< String, Object > settings )
	{
		maxTF.setValue( settings.get( RandomLinkingTrackerFactory.MAX_LINKS_PER_FRAME ) );
	}

	@Override
	public Map< String, Object > getSettings()
	{
		final Map< String, Object > map = new HashMap< >( 1 );
		map.put( RandomLinkingTrackerFactory.MAX_LINKS_PER_FRAME, ( ( Number ) maxTF.getValue() ).intValue() );
		return map;
	}

	@Override
	public void clean() {}
}
