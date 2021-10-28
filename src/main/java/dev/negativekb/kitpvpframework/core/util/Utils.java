/*
 * MIT License
 *
 * Copyright (c) 2021 Negative
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package dev.negativekb.kitpvpframework.core.util;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class Utils {

    private final DecimalFormat df;

    static {
        df = new DecimalFormat("###,###,###,###,###.##");
    }

    /**
     * Takes an Integer and turns it into a fancy string
     *
     * @param i Input
     * @return Output (Fancy String!)
     */
    public String decimalFormat(int i) {
        return df.format(i);
    }

    /**
     * Takes a Double and turns it into a fancy string
     *
     * @param i Input
     * @return Output (Fancy String!)
     */
    public String decimalFormat(double i) {
        return df.format(i);
    }

    /**
     * Takes a Float and turns it into a fancy string
     *
     * @param i Input
     * @return Output (Fancy String!)
     */
    public String decimalFormat(float i) {
        return df.format(i);
    }

    /**
     * Takes an Object and turns it into a fancy string
     *
     * @param i Input
     * @return Output (Fancy String!)
     */
    public String decimalFormat(Object i) {
        return df.format(i);
    }

    /**
     * Get all online players in a List format
     *
     * @return Returns all online players
     */
    public List<Player> getOnlinePlayers() {
        List<? extends Player> collect = new ArrayList<Player>(Bukkit.getOnlinePlayers());
        return new ArrayList<>(collect);
    }

    /**
     * Executes a command as the Console
     *
     * @param command Command input
     */
    public void executeConsoleCommand(String command) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
    }

    /**
     * Broadcast a message to the server
     *
     * @param message Message input
     */
    public void broadcast(String message) {
        Bukkit.broadcastMessage(color(message));
    }

    /**
     * Colorize a String
     *
     * @param input Input String
     * @return Chat Color formatted String
     */
    public String color(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    /**
     * Colorize a List of Strings
     *
     * @param input Input StringList
     * @return Chat Color formatted List of Strings
     */
    public List<String> color(List<String> input) {
        List<String> returnValue = new ArrayList<>();
        input.forEach(s -> returnValue.add(color(s)));
        return returnValue;
    }
}
