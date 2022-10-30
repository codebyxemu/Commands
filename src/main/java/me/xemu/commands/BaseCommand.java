package me.xemu.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public abstract class BaseCommand extends BukkitCommand {
	private final CommandInfo commandInfo;

	public BaseCommand() {
		super("");


		commandInfo = getClass().getDeclaredAnnotation(CommandInfo.class);
		Objects.requireNonNull(commandInfo, "Commands must have CommandInfo annotations.");

		setName(commandInfo.name());

		this.setDescription(commandInfo.description());
		this.setAliases(Arrays.asList(commandInfo.aliases()));

		try {
			Field field = Commands.getInstance().getServer().getClass().getDeclaredField("commandMap");
			field.setAccessible(true);
			CommandMap map = (CommandMap) field.get(Commands.getInstance().getServer());
			map.register(commandInfo.name(), this);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			System.out.println("Could not complete command with Class: " + getClass().getSimpleName());
		}
	}

	public abstract void execute(Player player, String[] args);
	public abstract void execute(CommandSender sender, String[] args);

	public abstract List<String> executeTabComplete(Player player, String[] args);
	public abstract List<String> executeTabComplete(CommandSender sender, String[] args);

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (commandInfo.requiresPlayerSender()) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (!commandInfo.permission().equalsIgnoreCase("")) {
					if (player.hasPermission(commandInfo.permission())) {
						execute(player, args);
					} else {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', Commands.getPermissionMessage()));
					}
				}
			}
			execute(sender, args);
		}
		return false;
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
		if (commandInfo.requiresPlayerSender()) {
			Player player = (Player) sender;
			return executeTabComplete(player, args);
		}
		return executeTabComplete(sender, args);
	}
}
