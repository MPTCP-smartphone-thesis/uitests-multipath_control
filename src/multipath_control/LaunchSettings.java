package multipath_control;

import utils.Utils;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class LaunchSettings extends UiAutomatorTestCase {

	private static final String ID_MULTIIFACE_BUTTON = "be.uclouvain.multipathcontrol:id/enable_multiiface";

	protected void enable_mptcp(UiObject button)
			throws UiObjectNotFoundException {
		if (!button.isChecked())
			button.click();
	}

	protected void disable_mptcp(UiObject button)
			throws UiObjectNotFoundException {
		if (button.isChecked())
			button.click();
	}

	public void testDemo() throws UiObjectNotFoundException {
		assertTrue("OOOOOpps",
				Utils.openApp(this, "Multipath Control", "be.uclouvain.multipathcontrol"));
		sleep(1000);

		// Get button
		UiObject button = Utils.getObjectWithId(ID_MULTIIFACE_BUTTON);

		String action = getParams().getString("action"); // default: enable
		if (action == null || !action.equals("disable"))
			enable_mptcp(button);
		else
			disable_mptcp(button);
	}
}

