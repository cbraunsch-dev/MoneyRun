package chris.braunschweiler.touchngo.utils;

import chris.braunschweiler.touchngo.entities.INonPlayableCharacter;

/**
 * This class encapsulates an NPC and allows it to be sorted in a list of NPCs according to its distance to
 * a given position. This is useful because it allows the level handler to tell the NPC with the shortest distance
 * to its target to calculate the path to said target first.
 * @author chrisbraunschweiler
 *
 */
public class SortableNpcContainer implements Comparable<SortableNpcContainer>{
	private int distanceToTarget;
	private INonPlayableCharacter npc;
	
	public SortableNpcContainer(int distanceToTarget, INonPlayableCharacter npc){
		this.distanceToTarget = distanceToTarget;
		this.npc = npc;
	}
	
	public INonPlayableCharacter getNPC(){
		return this.npc;
	}
	
	/**
	 * Compare a given NPC to another NPC based on the target distance.
	 * If the distance of this NPC to its target is shorter than that of the other
	 * NPC to its target, then this NPC is less than the other NPC (will come first in the sort order).
	 */
	public int compareTo(SortableNpcContainer another) {
		return this.distanceToTarget - another.distanceToTarget;
	}
}
