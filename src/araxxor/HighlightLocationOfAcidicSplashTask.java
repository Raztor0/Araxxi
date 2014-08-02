package araxxor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import org.powerbot.script.Tile;
import org.powerbot.script.rt6.ClientContext;

public class HighlightLocationOfAcidicSplashTask extends Task<ClientContext>{
	private final int acidicSplashId = 91671;
	
	public HighlightLocationOfAcidicSplashTask(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public boolean activate() {
		if(!ctx.objects.select().id(this.acidicSplashId).isEmpty()) { /* The acidic splash is visible somewhere */
			return true;
		} else { /* There's no acidic splash */
			return false;
		}
	}

	@Override
	public void execute() {
		// nothing to execute
	}
	
	@Override
	public void repaint(Graphics g) {
		g.setColor(Color.RED);
		if(!ctx.objects.select().id(this.acidicSplashId).isEmpty()) {
			g.drawPolygon(ctx.objects.select().id(this.acidicSplashId).poll().area().getPolygon());
		}
	}
}
