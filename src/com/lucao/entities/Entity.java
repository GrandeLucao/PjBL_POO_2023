package com.lucao.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.lucao.main.Game;
import com.lucao.world.Camera;

public class Entity {
	
	public static BufferedImage LIFEPACK_EN=Game.spritesheet.getSprite(6*16, 0, 16, 16);
	public static BufferedImage WEAPON_EN=Game.spritesheet.getSprite(7*16, 0, 16, 16);
	public static BufferedImage BULLET_EN=Game.spritesheet.getSprite(6*16,16, 16, 16);
	public static BufferedImage ENEMY_EN=Game.spritesheet.getSprite(7*16, 16, 16, 16);
	public static BufferedImage ENEMY_DAMAGE=Game.spritesheet.getSprite(144,16,16,16);
	public static BufferedImage GUN_LEFT=Game.spritesheet.getSprite(128+16,0,16,16);
	public static BufferedImage GUN_RIGHT=Game.spritesheet.getSprite(128,0,16,16);
	
	protected double x;
	protected double y;
	protected int width;
	protected int height;
	
	private BufferedImage sprite;
	
	private int maskx,masky,mwidth,mheight;
	
	public Entity(int x,int y,int width,int height, BufferedImage sprite) {
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		this.sprite=sprite;
		
		this.maskx=0;
		this.masky=0;
		this.mwidth=width;
		this.mheight=height;
	}
	
	public void setMask(int maskx,int masky,int mwidth, int mheight) {
		this.maskx=maskx;
		this.masky=masky;
		this.mwidth=mwidth;
		this.mheight=mheight;
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
	
	public static boolean isColliding(Entity e1, Entity e2) {
		Rectangle e1Mask=new Rectangle(e1.GetX()+e1.maskx,e1.GetY()+e1.masky,e1.mwidth,e1.mheight);
		Rectangle e2Mask=new Rectangle(e2.GetX()+e2.maskx,e2.GetY()+e2.masky,e2.mwidth,e2.mheight);
		
		return e1Mask.intersects(e2Mask);
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, this.GetX()-Camera.x,this.GetY()-Camera.y, null);
		//g.setColor(Color.red);
		//g.fillRect(this.GetX()+maskx-Camera.x,this.GetY()+masky-Camera.y, mwidth, mheight);
	}
	
	

}
