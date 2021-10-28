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

import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class Message {

    @Getter
    private final String initial;
    @Getter
    @Setter
    private String message;

    /**
     * Message Constructor (as String)
     *
     * @param msg Message
     */
    public Message(String... msg) {
        String actual = String.join("\n", msg);
        setMessage(actual);
        this.initial = actual;
    }

    /**
     * Replacer
     * <p>
     * Simply replaces object1 with object2.
     * Could be a string, int, double, whatever
     *
     * @param o1 - Object 1
     * @param o2 - Object 2
     * @return - Returns replaced value
     */
    public Message replace(Object o1, Object o2) {
        if (o2 instanceof Integer || o2 instanceof Double || o2 instanceof Long) {
            o2 = Utils.decimalFormat(o2);
        }

        String newMSG = this.message;

        newMSG = newMSG.replaceAll(String.valueOf(o1), String.valueOf(o2));

        setMessage(newMSG);
        return this;
    }

    /**
     * Send Message
     * <p>
     * Sends the message to a CommandSender.
     * Usually a player or console
     *
     * @param sender - Sender
     */
    public void send(CommandSender sender) {
        if (this.message.contains("\n")) {
            String[] msg = getMessage().split("\n");
            for (String s : msg) {
                sender.sendMessage(Utils.color(s));
            }
            this.message = initial;
            return;
        }
        sender.sendMessage(Utils.color(getMessage()));
        setMessage(getInitial());
    }

    /**
     * Send Message to a list of players
     * <p>
     * Simply sends the message to a list of players
     *
     * @param players - List of players
     */
    public void send(List<Player> players) {
        players.forEach(this::send);
    }

    /**
     * Broadcast message
     * <p>
     * Simply broadcasts the message for
     * all to see!
     */
    public void broadcast() {
        if (getMessage().contains("\n")) {
            String[] msg = getMessage().split("\n");
            for (String s : msg) {
                Utils.broadcast(getMessage());
            }
            setMessage(getInitial());
            return;
        }
        Utils.broadcast(getMessage());
        setMessage(getInitial());
    }

}
