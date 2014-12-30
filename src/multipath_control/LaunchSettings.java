package multipath_control;

import utils.Utils;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class LaunchSettings extends UiAutomatorTestCase {

	private static final String ID_MULTIIFACE_BUTTON = "be.uclouvain.multipathcontrol:id/enable_multiiface";

	/**
	 * Set the path manager for mptcp
	 */
	protected void sysctlMptcpPM(String pathManager) {
		String[] cmds = { "sysctl -w net.mptcp.mptcp_path_manager="
				+ pathManager };
		Utils.runAsRoot(cmds);
	}

	/**
	 * Force sysctl cmd to enable/diable mptcp after a pause of 0.5 sec
	 */
	protected void sysctlMptcp(boolean enable) {
		sleep(500);
		String[] cmds = { "sysctl -w net.mptcp.mptcp_enabled="
				+ (enable ? "1" : "0") };
		Utils.runAsRoot(cmds);
	}

	protected void enableMptcp(UiObject button)
			throws UiObjectNotFoundException {
		if (button.isChecked()) { // disable: to be sure to reset it
			button.click();
			sleep(500);
		}
		button.click();
		sysctlMptcp(true);
		String pathManager = getParams().getString("pm"); // default: default
		if (pathManager == null)
			pathManager = "default";
		sysctlMptcpPM(pathManager);
	}

	protected void disableMptcp(UiObject button)
			throws UiObjectNotFoundException {
		if (button.isChecked())
			button.click();
		sysctlMptcp(false);
	}

	public void testDemo() throws UiObjectNotFoundException {
		assertTrue("OOOOOpps", Utils.openApp(this, "Multipath Control",
				"be.uclouvain.multipathcontrol", false)); // not kill it before
		sleep(1000);

		// Get button
		UiObject button = Utils.getObjectWithId(ID_MULTIIFACE_BUTTON);

		String action = getParams().getString("action"); // default: enable
		if (action == null || !action.equals("disable"))
			enableMptcp(button);
		else
			disableMptcp(button);
	}
}

