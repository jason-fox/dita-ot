/*
 * This file is part of the DITA Open Toolkit project.
 *
 * Copyright 2019 Jarno Elovirta
 *
 * See the accompanying LICENSE file for applicable license.
 */

package org.dita.dost.invoker;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class UninstallArguments extends Arguments {

    String uninstallId;

    @Override
    UninstallArguments parse(final String[] arguments) {
        final Deque<String> args = new ArrayDeque<>(Arrays.asList(arguments));
        while (!args.isEmpty()) {
            final String arg = args.pop();
            if (arg.equals("uninstall")) {
                handleSubcommandUninstall(arg, args);
            } else if (isLongForm(arg, "-uninstall")) {
                handleArgUninstall(arg, args);
            } else {
                parseCommonOptions(arg, args);
            }
        }
        if (msgOutputLevel < Project.MSG_INFO) {
            emacsMode = true;
        }
        return this;
    }

    /**
     * Handle the --uninstall argument
     */
    private void handleArgUninstall(final String arg, final Deque<String> args) {
        String name = arg;
        final int posEq = name.indexOf("=");
        String value;
        if (posEq != -1) {
            value = name.substring(posEq + 1);
        } else {
            value = args.peek();
            if (value != null && !value.startsWith("-")) {
                value = args.pop();
            } else {
                value = null;
            }
        }
        if (value == null) {
            throw new BuildException("You must specify a installation package when using the --uninstall argument");
        }
        uninstallId = value;
    }

    private void handleSubcommandUninstall(final String arg, final Deque<String> args) {
        String value;
        value = args.peek();
        if (value != null && !value.startsWith("-")) {
            value = args.pop();
        } else {
            value = null;
        }
        uninstallId = value;
    }

    @Override
    void printUsage() {
        final StringBuilder msg = new StringBuilder();
        msg.append("Usage: dita uninstall <id>\n");
        msg.append("Arguments: \n");
        msg.append("  <id>                         uninstall plug-in with the ID\n");
        msg.append("Options: \n");
        msg.append("  -d, --debug                  print debugging information\n");
        msg.append("  -h, --help                   print this message\n");
        msg.append("  -v, --verbose                verbose logging\n");
        System.out.println(msg.toString());
    }


}
