package com.rs.game.item;

import com.rs.game.WorldTile;
import com.rs.game.player.Player;

@SuppressWarnings("serial")
public class FloorItem extends Item {

	private WorldTile tile;
	private Player owner;
	private Player loser;
	private boolean invisible;
	private boolean grave;

	public FloorItem(int id) {
		super(id);
	}

	@Override
	public void setAmount(int amount) {
		this.amount = amount;
	}

	public FloorItem(Item item, WorldTile tile, Player owner, Player loser,
			boolean underGrave, boolean invisible) {
		super(item.getId(), item.getAmount());
		this.tile = tile;
		this.owner = owner;
		grave = underGrave;
		this.invisible = invisible;
	}

	public WorldTile getTile() {
		return tile;
	}

	public boolean isGrave() {
		return grave;
	}

	public boolean isInvisible() {
		return invisible;
	}

	public Player getOwner() {
		return owner;
	}

	public Player getLoser() {
		return loser;
	}
	
	public void setLoser(Player loser) {
		this.loser = loser;
	}	
	
	public void setInvisible(boolean invisible) {
		this.invisible = invisible;
	}

}
