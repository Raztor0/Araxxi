package araxxor;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.PaintListener;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;

@Script.Manifest(name="Raz Arraxor", description="Switches prayers based on arraxor's attack style, so you don't have to.", properties = "client=6")
public class RazArraxor extends PollingScript<ClientContext> implements PaintListener{
	private List<Task> taskList = new ArrayList<Task>();
	
	@Override
	public void start() {
		taskList.addAll(Arrays.asList(new PrayerSwitchTask(ctx), new HighlightLocationOfLightTask(ctx), new ProjectileTrackingTask(ctx),
				new HighlightLocationOfAcidicSplashTask(ctx)));
	}

	@Override
	public void poll() {
		for (Task task : this.taskList) {
			if(task.activate()) {
				task.execute();
			}
		}
	}

	@Override
	public void repaint(Graphics g) {
		for (Task task : this.taskList) {
			if (task.activate()) {
				task.repaint(g);
			}
		}
	}
}
