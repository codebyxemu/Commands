package me.xemu.commands;

import org.bukkit.plugin.java.JavaPlugin;

public class Commands {

	private static JavaPlugin instance;

	private static String permissionMessage;

	public static JavaPlugin getInstance() {
		return instance;
	}

	public static String getPermissionMessage() {
		return permissionMessage;
	}

	public static void setInstance(JavaPlugin instance) {
		Commands.instance = instance;
	}

	public static void setPermissionMessage(String permissionMessage) {
		Commands.permissionMessage = permissionMessage;
	}
}
