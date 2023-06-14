package com.lucao.entities;

import java.awt.image.BufferedImage;

import com.lucao.main.Game;
import com.lucao.world.World;

public class Enemy extends Entity{
	
	private double speed=1;

	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
	}
	
	public void tick() {
		if((int)x<Game.player.GetX() && World.isFree((int)(x+speed), this.GetY())) {
			x+=speed;
		}
		else if((int)x>Game.player.GetX() && World.isFree((int)(x-speed), this.GetY())) {
			x-=speed;
		}
		else if((int)y<Game.player.GetY() && World.isFree(this.GetX(), (int)(y+speed))) {
			y+=speed;
		}
		else if((int)y>Game.player.GetY() && World.isFree(this.GetX(), (int)(y-speed))) {
			y-=speed;
		}
	}

}
