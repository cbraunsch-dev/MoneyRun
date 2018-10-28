package chris.braunschweiler.touchngo.dto;

import java.util.List;

import chris.braunschweiler.touchngo.common.CommonDataManager.PlayerAbility;
import chris.braunschweiler.touchngo.utils.PathPoint;

/**
 * A DTO used to store NPC data such as path information.
 * @author chrisbraunschweiler
 *
 */
public class NpcDTO extends PlayerDTO{
	private List<PathPoint> patrolPathPoints;
	private int chaseTargetDistance;
	private int changeTargetMidPathDistance;
	
	public NpcDTO(int xPos, int yPos, List<PathPoint> patrolPathPoints, int chaseTargetDistance, int changeTargetMidPathDistance, PlayerAbility playerAbility){
		super(xPos, yPos, playerAbility);
		this.patrolPathPoints = patrolPathPoints;
		this.chaseTargetDistance = chaseTargetDistance;
		this.changeTargetMidPathDistance = changeTargetMidPathDistance;
	}
	
	public List<PathPoint> getPatrolPathPoints(){
		return this.patrolPathPoints;
	}

	/**
	 * The distance within which the NPC will chase his current target. If the target is within this distance,
	 * the NPC will chase the target.
	 * @return The distance within which the NPC will chase his current target.
	 */
	public int getChaseTargetDistance() {
		return chaseTargetDistance;
	}

	/**
	 * The distance between the NPC and the currently targeted player within which the NPC can change his
	 * destination mid-path (while he's still on his way to his current destination). This will prevent
	 * NPCs which are still really far away from the player to always change their destination when the
	 * user changes his destination. The distance is in pixels not in tiles.
	 * @return The change-target-mid-path distance.
	 */
	public int getChangeTargetMidPathDistance() {
		return changeTargetMidPathDistance;
	}
}
