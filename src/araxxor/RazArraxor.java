package araxxor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.PollingScript;

@PollingScript.Manifest(name="Raz Arraxor", description="Switches prayers based on arraxor's attack style, so you don't have to.")
public class RazArraxor extends PollingScript<ClientContext>{
	private List<Task> taskList = new ArrayList<Task>();
	
	@Override
	public void start() {
		taskList.addAll(Arrays.asList(new PrayerSwitchTask(ctx)));
	}

	@Override
	public void poll() {
		for (Task task : this.taskList) {
			if(task.activate()) {
				task.execute();
			}
		}
	}

}
