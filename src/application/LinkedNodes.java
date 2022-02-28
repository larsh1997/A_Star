package application;

import java.util.ArrayList;


public class LinkedNodes {
	
	int h;
	int g;
	int f;
	int x,y;
	LinkedNodes parent = null;
	boolean isInPath = false;

	
	public LinkedNodes(int x,int y, int h, int g, int f) {
		this.x = x;
		this.y = y;
		this.h = h;
		this.g = g;
		this.f = f;

		
	}
	public boolean getInPath() {
		return isInPath;
	}
	
	public void setInPath(boolean isInPath) {
		this.isInPath = isInPath;
	}


	public int getH() {
		return h;
	}


	public void setH(int h) {
		this.h = h;
	}


	public int getG() {
		return g;
	}


	public void setG(int g) {
		this.g = g;
	}


	public int getF() {
		return f;
	}


	public void setF(int f) {
		this.f = f;
	}


	public int getX() {
		return x;
	}


	public void setX(int x) {
		this.x = x;
	}


	public int getY() {
		return y;
	}


	public void setY(int y) {
		this.y = y;
	}


	public LinkedNodes getParent() {
		return parent;
	}


	public void setParent(LinkedNodes parent) {
		this.parent = parent;
	}
}
