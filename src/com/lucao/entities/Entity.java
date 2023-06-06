package com.lucao.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.lucao.main.Game;
import com.lucao.world.Camera;

public class Entity {
	
	public static BufferedImage LIFEPACK_EN=Game.spritesheet.getSprite(6*16, 0, 16, 16);
	public static BufferedImage WEAPON_EN=Game.spritesheet.getSprite(7*16, 0, 16, 16);
	public static BufferedImage BULLET_EN=Game.spritesheet.getSprite(6*16,16, 16, 16);
	public static BufferedImage ENEMY_EN=Game.spritesheet.getSprite(7*16, 16, 16, 16);
	
	protected double x;
	protected double y;
	protected int width;
	protected int height;
	
	private BufferedImage sprite;
	
	public Entity(int x,int y,int width,int height, BufferedImage sprite) {
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		this.sprite=sprite;
	}
	
	public void setX(int newX) {
		this.x=newX;
	}
	public void setY(int newY)
	{
		this.y=newY;
	}
	public int GetX() {
		return (int)  this.x;
	}

	public int GetY() {
		return (int) this.y;
	}

	public int GetWidth() {
		return this.width;
	}

	public int GetHeight() {
		return this.height;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, this.GetX()-Camera.x,this.GetY()-Camera.y, null);
	}
	
	

}
