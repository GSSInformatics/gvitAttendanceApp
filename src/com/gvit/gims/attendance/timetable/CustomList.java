/**
 * 
 */
package com.gvit.gims.attendance.timetable;

import java.util.List;

import com.gvit.gims.attendance.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * @author VAMSI KRISHNA
 *
 */
public class CustomList extends ArrayAdapter<IEventTime> {

	private Activity context;
	private List<IEventTime> events;

	public CustomList(Activity _context, List<IEventTime> eventData) {
		super(_context, R.layout.eventslistview, eventData);

		this.context = _context;
		this.events = eventData;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.eventslistview, null, true);
		TextView eventTiming = (TextView) rowView.findViewById(R.id.timings);
		eventTiming.setText(events.get(position).getTimeRange());

		TextView eventName = (TextView) rowView.findViewById(R.id.eventName);
		eventName.setText(events.get(position).getEventName());

		return rowView;
	}

}
