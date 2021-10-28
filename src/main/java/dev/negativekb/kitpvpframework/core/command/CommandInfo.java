package dev.negativekb.kitpvpframework.core.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * CommandInfo
 * <p>
 * The ability to initialize your command without multiple
 * annotations or using the super methods
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandInfo {

    // Name of the command
    // required
    String name();

    // Aliases of the command
    // not required
    String[] aliases() default "";

    // Permission of the command
    // not required
    String permission() default "";

    // Description of the command
    // not required

    /**
     * @apiNote Only works for {@link club.deltapvp.deltacore.api.commands.Command}!!
     */
    String description() default "";

    // Is this command console only?
    // not required
    boolean consoleOnly() default false;

    // Is this command player only?
    // not required
    boolean playerOnly() default false;

    // Is this command disabled?
    // not required
    boolean disabled() default false;

}
