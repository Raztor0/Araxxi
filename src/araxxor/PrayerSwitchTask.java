package araxxor;

import java.util.Iterator;

import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Npc;
import org.powerbot.script.rt6.Projectile;

public class PrayerSwitchTask extends Task<ClientContext> {
	private final int[] attackIds = {
			24047,	// range attack
			24095	// mage attack
	};
	
	private final int mageProjectileAraxxorId = 4979;
	private final int rangeProjectileAraxxorId = 4997;
	
	private final String prayRangeHotKey = "a";
	private final String prayMageHotKey = "b";
	
	private final int rangePrayerIcon = 14;
	private final int magePrayerIcon = 13;

	public PrayerSwitchTask(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public boolean activate() {
		if (ctx.npcs.select().name("Araxxi").isEmpty()) { /* We didn't find araxxi yet */
//			Npc araxxor = ctx.npcs.select().name("Araxxor").first().poll();
			
			Projectile closestMageProjectile = null;
			Projectile closestRangeProjectile = null;
			
			if(!ctx.projectiles.select().id(this.mageProjectileAraxxorId).isEmpty()) { /* There's a mage projectile somewhere */
				Iterator<Projectile> iterator = ctx.projectiles.select().id(this.mageProjectileAraxxorId).iterator();
				while(iterator.hasNext()) {
					Projectile projectile = iterator.next();
					if(closestMageProjectile == null) {
						closestMageProjectile = projectile;
						continue;
					} else {
						if(ctx.movement.distance(projectile) < ctx.movement.distance(closestMageProjectile)) {
							closestMageProjectile = projectile;
						}
					}
				}
			}
			
			if(!ctx.projectiles.select().id(rangeProjectileAraxxorId).isEmpty()) { /* There's a range projectile somewhere */
				Iterator<Projectile> iterator = ctx.projectiles.select().id(this.rangeProjectileAraxxorId).iterator();
				while(iterator.hasNext()) {
					Projectile projectile = iterator.next();
					if(closestRangeProjectile == null) {
						closestRangeProjectile = projectile;
					} else {
						if(ctx.movement.distance(projectile) < ctx.movement.distance(closestRangeProjectile)) {
							closestRangeProjectile = projectile;
						}
					}
				}
			}
			
			/* At this point we have the closest range projectile and the closest mage projectile to the player */
			if(closestMageProjectile != null) {
				if(closestRangeProjectile != null) { /* There's both a range and mage projectile */
					if(ctx.movement.distance(closestMageProjectile) < ctx.movement.distance(closestRangeProjectile)) {
						return !this.isPlayerPrayingMage();
					} else {
						return !this.isPlayerPrayingRange();
					}
				} else { /* There's no range projectile */
					return !this.isPlayerPrayingMage();
				}
			} else if(closestRangeProjectile != null) { /* If we get here it means we have no mage projectile
														   but we do have a range projectile */
				return !this.isPlayerPrayingRange();
			}
		} else { /* We found araxxi */
			Npc araxxi = ctx.npcs.select().name("Araxxi").first().poll();
			int animation = araxxi.animation();
			if (animation == attackIds[0]) { /* He's hitting with range */
				System.out.println("Araxxi is about to hit us with range");
				return !this.isPlayerPrayingRange();
			} else if (animation == attackIds[1]) { /* He's hitting with mage */
				System.out.println("Araxxi is about to hit us with mage");
				return !this.isPlayerPrayingMage();
			} else { /* He's not attacking right now */
				return false;
			}
		}
		
		return false;
	}

	@Override
	public void execute() {
		/*
		 * If we get here it means we need to switch our prayer
		 */
		System.out.println("Switching prayers");
		if (this.isPlayerPrayingRange()) {
			this.prayMage();
		} else {
			this.prayRange();
		}
		
		/* Sleep a little bit so as to account for any delays between pressing the key and having the prayer icon change */
		try {
			Thread.sleep(1000, 1200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private Boolean isPlayerPrayingRange() {
		return ctx.players.local().prayerIcon() == this.rangePrayerIcon;
	}
	
	private Boolean isPlayerPrayingMage() {
		return ctx.players.local().prayerIcon() == this.magePrayerIcon;
	}
	
	private void prayRange() {
		ctx.input.send(this.prayRangeHotKey);
	}
	
	private void prayMage() {
		ctx.input.send(this.prayMageHotKey);
	}

}
