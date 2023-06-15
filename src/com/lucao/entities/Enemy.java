package com.lucao.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.lucao.main.Game;
import com.lucao.world.Camera;
import com.lucao.world.World;

public class Enemy extends Entity{
	
	private double speed=1;
	
	private int maskx=8, masky=8,maskw=10,maskh=10;
	
	private  int frames=0, maxFrames=20,index=0,maxIndex=1;
	
	private BufferedImage[] sprites;
	
	private int life=5;
	
	private boolean isDamaged=false;
	private int damageFrames=10,damageCurrent=0;

	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		sprites=new BufferedImage[2];
		sprites[0]=Game.spritesheet.getSprite(112, 16, 16,16);
		sprites[1]=Game.spritesheet.getSprite(112+16, 16, 16,16);
	}
	
	public void tick() {
		if(this.isCollidingWithPlayer()==false) {
		if((int)x<Game.player.GetX() && World.isFree((int)(x+speed), this.GetY()) && !isColliding((int)(x+speed), this.GetY())) {
			x+=speed;
		}
		else if((int)x>Game.player.GetX() && World.isFree((int)(x-speed), this.GetY()) && !isColliding((int)(x-speed), this.GetY())) {
			x-=speed;
		}
		else if((int)y<Game.player.GetY() && World.isFree(this.GetX(), (int)(y+speed)) && !isColliding(this.GetX(), (int)(y+speed))) {
			y+=speed;
		}
		else if((int)y>Game.player.GetY() && World.isFree(this.GetX(), (int)(y-speed)) && !isColliding(this.GetX(), (int)(y-speed))) {
			y-=speed;
		}
		}else {
			if(Game.rand.nextInt(100)<10) {
				Game.player.life-=Game.rand.nextInt(5);
				Game.player.isDamaged=true;
			}
		}
			frames++;
			if(frames==maxFrames) {
				frames=0;
				index++;
				if(index>maxIndex) {
					index=0;
				}
			}
			
			collidingBullet();
			
			if(life<=0) {
				destroySelf();
				return;
			}

			if(isDamaged) {
				this.damageCurrent++;
				if(this.damageCurrent==this.damageFrames) {
					this.damageCurrent=0;
					this.isDamaged=false;
				}
			}
	}
	
	public void destroySelf() {
		Game.enemys.remove(this);
		Game.entities.remove(this);
	}
	
	public void collidingBullet() {
		for(int i=0;i<Game.bullets.size();i++) {
			Entity e=Game.bullets.get(i);
			if(e instanceof BulletShoot) {
				if(Entity.isColliding(this, e)) {
					isDamaged=true;
					life--;
					Game.bullets.remove(i);
					return;
				}
			}
		}
	}
	
	public  boolean isCollidingWithPlayer() {
		Rectangle enemyCurrent=new Rectangle(this.GetX()+maskx, this.GetY()+masky, maskw, maskh);
		Rectangle player= new Rectangle(Game.player.GetX(), Game.player.GetY(),16,16);
		
		return enemyCurrent.intersects(player);
	}
	
	public boolean isColliding(int xnext, int ynext) {
		Rectangle enemyCurrent=new Rectangle(xnext+maskx, ynext+masky, maskw, maskh);
		for(int i=0;i<Game.enemys.size(); i++) {
			Enemy e=Game.enemys.get(i);
			if(e==this) {
				continue;
			}
			Rectangle targetEnemy=new Rectangle(e.GetX()+maskx,e.GetY()+masky, maskw, maskh);
			if(enemyCurrent.intersects(targetEnemy)) {
				return true;
			}
		}
		return false;
	}
	
	public void render(Graphics g) {
		if(!isDamaged) {
			g.drawImage(sprites[index],this.GetX()-Camera.x,this.GetY()-Camera.y,null);
		}else {
			g.drawImage(Entity.ENEMY_DAMAGE,this.GetX()-Camera.x,this.GetY()-Camera.y,null);
		}
	}
	

}
