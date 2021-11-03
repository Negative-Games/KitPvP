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

import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class NMSParticle {

    private EnumParticle particleType;
    private boolean longDistance;
    private Location location;
    private float offSetX;
    private float offSetY;
    private float offSetZ;
    private float speed;
    private int amount;


    public NMSParticle(EnumParticle particleType, Location location, boolean longDistance, float offSetX, float offSetY, float offSetZ, float speed, int amount) {
        this.particleType = particleType;
        this.location = location;
        this.longDistance = longDistance;
        this.offSetX = offSetX;
        this.offSetY = offSetY;
        this.offSetZ = offSetZ;
        this.speed = speed;
        this.amount = amount;
    }

    public void send(Player player){
        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(this.particleType, this.longDistance, (float)this.location.getX(), (float)this.location.getY(),
                (float)this.location.getZ(), this.offSetX, this.offSetY, this.offSetZ, this.speed, this.amount);

        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
    }

}
