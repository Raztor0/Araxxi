package araxxor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import org.powerbot.script.Tile;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GameObject;
import org.powerbot.script.rt6.LocalPath;

public class HighlightPathToLightTask extends Task<ClientContext> {
	private final int groundLightId = 91665;
	
	private LocalPath pathToLight = null;

	public HighlightPathToLightTask(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public boolean activate() {
//		if(!ctx.objects.select().id(91665).isEmpty()) { /* The light is visible somewhere */
//			System.out.println("Found the light!");
//			return true;
//		} else { /* There's no light; let's clear our path so we don't try to draw stuff */
//			this.pathToLight = null;
//			return false;
//		}
		
		return true;
	}

	@Override
	public void execute() {
		/* Set up our path */
//		GameObject light = ctx.objects.select().id(this.groundLightId).poll();
//		this.pathToLight = ctx.movement.findPath(light);
	}

	@Override
	public void repaint(Graphics g) {
		g.setColor(Color.CYAN);
		g.drawPolygon(ctx.players.local().tile().matrix(ctx).bounds());
		Point mapPoint = ctx.players.local().tile().matrix(ctx).mapPoint();
		g.drawRect((int)mapPoint.getX() - 2, (int)mapPoint.getY() - 2, 4, 4);
//		if(this.pathToLight != null) {
//			Tile nextTile = this.pathToLight.next();
//			while(nextTile != this.pathToLight.end()) { /* Loop through our path until we get to the end */
//				g.drawPolygon(nextTile.matrix(ctx).bounds());
//				nextTile = this.pathToLight.next();
//			}
//		}
	}

}
