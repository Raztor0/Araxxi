package araxxor;

import java.awt.Color;
import java.awt.Graphics;

import org.powerbot.script.PaintListener;
import org.powerbot.script.Tile;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GameObject;
import org.powerbot.script.rt6.LocalPath;

public class HighlightPathToLightTask extends Task<ClientContext> implements PaintListener{
	private final int groundLightId = 91665;
	
	private LocalPath pathToLight = null;

	public HighlightPathToLightTask(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public boolean activate() {
		if(!ctx.objects.select().id(91665).isEmpty()) { /* The light is visible somewhere */
			return true;
		} else { /* There's no light; let's clear our path so we don't try to draw stuff */
			this.pathToLight = null;
			return false;
		}
	}

	@Override
	public void execute() {
		/* Set up our path */
		GameObject light = ctx.objects.select().id(this.groundLightId).poll();
		this.pathToLight = ctx.movement.findPath(light);
	}

	@Override
	public void repaint(Graphics g) {
		g.setColor(Color.CYAN);
		if(this.pathToLight != null) {
			Tile nextTile = this.pathToLight.next();
			while(nextTile != this.pathToLight.end()) { /* Loop through our path until we get to the end */
				nextTile.matrix(ctx).draw(g);
				nextTile = this.pathToLight.next();
			}
		}
	}

}
