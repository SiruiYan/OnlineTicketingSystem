package onlineticketing.datasource;

import java.util.ArrayList;

import onlineticketing.domain.ScreeningRoom;
import onlineticketing.domain.Seat;

public class ScreeningRoomMapper {
	
	/**
	 * Find a screening room with a screening room ID
	 * @param screeningRoomId  the input screening room ID
	 * @return  the ScreeningRoom object with the input screening room ID
	 */
	public static ScreeningRoom findScreeningRoomById(int screeningRoomId) {
		ScreeningRoom targetScreeningRoom = new ScreeningRoom();
		IdentityMap<ScreeningRoom> screeningRoomMap = IdentityMap.getInstance(targetScreeningRoom);
		
		targetScreeningRoom = screeningRoomMap.get(Integer.toString(screeningRoomId));
		if(targetScreeningRoom != null) 
			return targetScreeningRoom;
		
		ArrayList<Seat> seats = SeatMapper.findSeatsByScreeningRoomId(screeningRoomId);
		ScreeningRoom screeningRoom = new ScreeningRoom(screeningRoomId, seats);
			return screeningRoom;
		
	}
}
