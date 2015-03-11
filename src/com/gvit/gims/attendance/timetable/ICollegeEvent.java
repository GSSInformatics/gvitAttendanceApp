/**
 * 
 */
package com.gvit.gims.attendance.timetable;

import java.util.List;
import java.util.Map;

/**
 * @author VAMSI KRISHNA
 *
 */
public interface ICollegeEvent {

	Map<String, List<IEventTime>> getEvents();
}
