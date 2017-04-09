package de.generia.tools.model.api.presentation;

import java.io.OutputStream;
import java.io.PrintStream;

import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.IConsoleView;
import org.eclipse.ui.console.MessageConsole;

public class ConsoleUtil {
	private static final String CONSOLE_NAME = "Api Generator";

	private static MessageConsole findConsole(String pName) {
		ConsolePlugin lConsolePlugin = ConsolePlugin.getDefault();
		IConsoleManager lConsoleManager = lConsolePlugin.getConsoleManager();
		IConsole[] lConsoles = lConsoleManager.getConsoles();
		for (int i = 0; i < lConsoles.length; i++) {
			if (pName.equals(lConsoles[i].getName())) {
				return (MessageConsole) lConsoles[i];
			}
		}
		// no console found, so create a new one
		MessageConsole lNewConsole = new MessageConsole(pName, null);
		lConsoleManager.addConsoles(new IConsole[] { lNewConsole });
		return lNewConsole;
	}

	public static PrintStream getConsoleOut(IWorkbenchPage pWorkbenchPage) {
		MessageConsole lConsole = findConsole(CONSOLE_NAME);
		displayConsole(pWorkbenchPage, lConsole);
		OutputStream lConsoleOut = lConsole.newMessageStream();
		return new PrintStream(lConsoleOut);
	}

	private static void displayConsole(IWorkbenchPage pWorkbenchPage, MessageConsole pConsole) {
		String lId = IConsoleConstants.ID_CONSOLE_VIEW;
		IConsoleView lConsoleView;
		try {
			lConsoleView = (IConsoleView) pWorkbenchPage.showView(lId);
		} catch (PartInitException e) {
			throw new RuntimeException("can't get console-view", e);
		}
		lConsoleView.display(pConsole);
	}
}
