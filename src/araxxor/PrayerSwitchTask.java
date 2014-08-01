package araxxor;

import java.util.Iterator;

import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.MobileIdNameQuery;
import org.powerbot.script.rt6.Npc;
import org.powerbot.script.rt6.Projectile;

import sun.util.logging.resources.logging;

public class PrayerSwitchTask extends Task<ClientContext> {
	private final int[] attackIds = {
			24047,	// range attack
			24095	// mage attack
	};
	
	private final int prayRangeHotKey = 0;
	private final int prayMageHotKey = 1;
	
	private final int rangePrayerIcon = 14;
	private final int magePrayerIcon = 13;

	public PrayerSwitchTask(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public boolean activate() {
		if (!ctx.npcs.select().name("Araxxor").isEmpty()) { /* We found araxxor */
			Npc araxxor = ctx.npcs.select().name("Araxxor").first().poll();
			int animation = araxxor.animation();
			/*
			 * Do nothing for now.
			 */
			System.out.println("Found araxxor");
			System.out.println("Araxxor's current animation id is: " + animation);
			System.out.println("Doing nothing.. for now");
		} else if (!ctx.npcs.select().name("Araxxi").isEmpty()) { /* We found araxxi */
			Npc araxxi = ctx.npcs.select().name("Araxxi").first().poll();
			System.out.println("Found araxxi");
			
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

//		Iterator<Projectile> iterator = ctx.projectiles.select().iterator();
//		while (iterator.hasNext()) {
//			Projectile projectile = iterator.next();
//		}

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
		ctx.input.send("a");
	}
	
	private void prayMage() {
		ctx.input.send("b");
	}

}
