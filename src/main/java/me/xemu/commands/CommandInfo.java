package me.xemu.commands;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandInfo {

	String name();
	String description() default "Default Command Description";
	String[] aliases();
	String permission() default "";
	boolean requiresPlayerSender() default false;

}
