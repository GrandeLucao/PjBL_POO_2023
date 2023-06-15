package com.lucao.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.lucao.graficos.Spritesheet;
import com.lucao.main.Game;
import com.lucao.world.Camera;
import com.lucao.world.World;

public class Player extends Entity {
	
	public boolean right,left,up,down;
	public int right_dir=0,left_dir=1;
	public int dir=right_dir;
	public double speed=1.4;
	
	private  int frames=0, maxFrames=5,index=0,maxIndex=3;
	private boolean moved=false;
	private BufferedImage [] rightPlayer;
	private BufferedImage [] leftPlayer;
	
	private BufferedImage playerDamage;
	
	private boolean hasGun=false;
	
	public int ammo=0;
	
	public boolean isDamaged=false;
	private int damageFrames=0;
	
	public boolean isShooting=false,mouseShoot=false;
	
	public double  life=100,maxLife=100;
	public int mx,my;
	
	public Player(int x,int y,int width,int height,BufferedImage sprite) {
		super(x,y,width,height,sprite);
		
		rightPlayer=new BufferedImage[4];
		leftPlayer=new BufferedImage[4];
		playerDamage=Game.spritesheet.getSprite(0,16,16,16);
		
		for(int i=0; i<4;i++) {
			rightPlayer[i]=Game.spritesheet.getSprite(32+(i*16), 0, 16, 16);		
		}
		for(int i=0; i<4;i++) {
			leftPlayer[i]=Game.spritesheet.getSprite(32+(i*16), 16, 16, 16);		
		}
	}
	
	public void tick() {
		moved=false;
		if(right && World.isFree((int)(x+speed),this.GetY())) {
			moved=true;
			dir=right_dir;
			x+=speed;
		}else if(left && World.isFree((int)(x-speed),this.GetY())) {
			moved=true;
			dir=left_dir;
			x-=speed;
		}
		if(up && World.isFree(this.GetX(),(int)(y-speed))) {
			moved=true;
			y-=speed;
		}else if(down && World.isFree(this.GetX(),(int)(y+speed))) {
			moved=true;
			y+=speed;
		}
		
		if(moved) {
			frames++;
			if(frames==maxFrames) {
				frames=0;
				index++;
				if(index>maxIndex) {
					index=0;
				}
			}
		}
		
		checkAmmo();
		checkLifePack();
		checkGun();
		
		if(isDamaged) {
			this.damageFrames++;
			if(this.damageFrames==8) {
				this.damageFrames=0;
				isDamaged=false;
			}
		}
		
		if(isShooting ) {
			isShooting=false;
			if(hasGun && ammo>0) {
			ammo--;
			int dx=0;
			int px=0;
			int py=6;
			if(dir==right_dir) {
				px=18;
				dx=1;
			}else {
				px=-8;
				dx=-1;
			}
			
			BulletShoot bullet= new BulletShoot(this.GetX()+px,this.GetY()+py,3,3,null,dx,0);
			Game.bullets.add(bullet);
			}
		}
		
		if(mouseShoot ) {
			mouseShoot=false;
			if(hasGun && ammo>0) {
			ammo--;
			double angle=0;
			int px=8; int py=8;
			if(dir==right_dir) {
				px=18;
				angle=(Math.atan2(my-(this.GetY()+py-Camera.y),mx-(this.GetX()+px-Camera.x)));
			}else {
				px=-8;
				angle=(Math.atan2(my-(this.GetY()+py-Camera.y),mx-(this.GetX()+px-Camera.x)));
			}

			double dx=Math.cos(angle);
			double dy=Math.sin(angle);
			
			BulletShoot bullet= new BulletShoot(this.GetX()+px,this.GetY()+py,3,3,null,dx,dy);
			Game.bullets.add(bullet);
			}
		}
		
		if(life<=0) {
			life=0;
			Game.gameState="GAME_OVER";
		}
		
		Camera.x=Camera.clamp(this.GetX()-(Game.WIDTH/2),0,World.WIDTH*16-Game.WIDTH);
		Camera.y=Camera.clamp(this.GetY()-(Game.HEIGHT/2),0,World.HEIGHT*16-Game.HEIGHT);
		
	}
	
	public void checkGun() {
		for(int i=0;i<Game.entities.size();i++) {
			Entity e=Game.entities.get(i);
			if(e instanceof Weapon) {
				if(Entity.isColliding(this, e)) {
					hasGun=true;
					Game.entities.remove(i);
					return;
				}				
			}
		}
	}
	
	public void checkAmmo() {
		for(int i=0;i<Game.entities.size();i++) {
			Entity e=Game.entities.get(i);
			if(e instanceof Bullet) {
				if(Entity.isColliding(this, e)) {
					ammo+=25;
					Game.entities.remove(i);
					return;
				}				
			}
		}
	}
	
	public void checkLifePack() {
		for(int i=0;i<Game.entities.size();i++) {
			Entity e=Game.entities.get(i);
			if(e instanceof LifePack) {
				if(Entity.isColliding(this, e)) {
					life+=8;
					if(life>=100) 
						life=100;
					Game.entities.remove(i);
					return;
				}				
			}
		}
	}
	
	public void render(Graphics g) {
		if(!isDamaged) {
			if(dir==right_dir) {
				g.drawImage(rightPlayer[index],this.GetX()-Camera.x,this.GetY()-Camera.y, null);
				if(hasGun) {
					g.drawImage(Entity.GUN_RIGHT, this.GetX()+8-Camera.x, this.GetY()-Camera.y, null);
				}
			}else if(dir==left_dir) {
				g.drawImage(leftPlayer[index],this.GetX()-Camera.x,this.GetY()-Camera.y, null);
				if(hasGun) {
					g.drawImage(Entity.GUN_LEFT, this.GetX()-8-Camera.x, this.GetY()-Camera.y, null);
				}
			}
		}else {
			g.drawImage(playerDamage,this.GetX()-Camera.x,this.GetY()-Camera.y,null);
		}
	}

}
