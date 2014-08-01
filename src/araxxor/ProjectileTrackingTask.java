package araxxor;

import java.util.Iterator;

import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Projectile;

public class ProjectileTrackingTask extends Task<ClientContext>{
	/*
	 * 4979 - Mage projectile first phase
	 */
	
	public ProjectileTrackingTask(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public boolean activate() {
		/* We're just spitting out all the ids of the projectiles right now */
		return !ctx.projectiles.select().isEmpty();
	}

	@Override
	public void execute() {
		Iterator<Projectile> iterator = ctx.projectiles.select().iterator();
		while (iterator.hasNext()) {
			Projectile projectile = iterator.next();
			System.out.println("Found a projectile with id: " + projectile.id());
		}
	}
}
