package araxxor;

import java.awt.Graphics;

import org.powerbot.script.ClientAccessor;
import org.powerbot.script.ClientContext;
import org.powerbot.script.PaintListener;

public abstract class Task<C extends ClientContext> extends ClientAccessor<C> implements PaintListener{
	public Task(C ctx) {
		super(ctx);
	}
	
	public abstract boolean activate();
    public abstract void execute();

    public void repaint(Graphics g) {
    	// do nothing
    }
}
