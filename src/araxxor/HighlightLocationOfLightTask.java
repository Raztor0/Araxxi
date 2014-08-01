package araxxor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import org.powerbot.script.Tile;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GameObject;
import org.powerbot.script.rt6.LocalPath;

public class HighlightLocationOfLightTask extends Task<ClientContext> {
	private final int groundLightId = 91665;
	
	private Tile lightTile = null;

	public HighlightLocationOfLightTask(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public boolean activate() {
		if(!ctx.objects.select().id(91665).isEmpty()) { /* The light is visible somewhere */
			return true;
		} else { /* There's no light; let's clear our path so we don't try to draw stuff */
			this.lightTile = null;
			return false;
		}
	}

	@Override
	public void execute() {
		/* Set up our path */
		GameObject light = ctx.objects.select().id(this.groundLightId).poll();
		this.lightTile = light.tile();
	}

	@Override
	public void repaint(Graphics g) {
		g.setColor(Color.CYAN);
		
		if(this.lightTile != null) {
			Point mapPoint = lightTile.matrix(ctx).mapPoint();
			g.fillRect((int)mapPoint.getX() - 2, (int)mapPoint.getY() - 2, 4, 4); // Draw the point on the map
		}
	}

}
