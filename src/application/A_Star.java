package application;

import java.util.ArrayList;

public class A_Star {
	
	int map[][];
	int colSize, rowSize;
	
	int start_x, start_y;
	int end_x, end_y;
	
	int current_x, current_y;
	int h, g, f;
	
	boolean diagNodes = true;
	
	ArrayList<LinkedNodes> seenNodes = new ArrayList<LinkedNodes>();
	ArrayList<LinkedNodes> path = new ArrayList<LinkedNodes>();
	ArrayList<LinkedNodes> currentBlock;
	
	LinkedNodes current_node;
	LinkedNodes tmp_node;
	
	public A_Star(int start_point[], int end_point[], int map[][], int colSize, int rowSize) {
		
		this.start_x = start_point[1]; //start x
		this.start_y = start_point[0]; //start y
		this.end_x = end_point[1];
		this.end_y = end_point[0];	
		this.map = map;				   //whole map (2D-array)
		this.colSize = colSize;		   //map colmuns
		this.rowSize = rowSize;        //map rows
		
		current_node = new LinkedNodes(start_x,start_y,0,0,0);	
		
	}
	
	/**
	 * Awfully big method that could be split up into smaller piece.
	 * Calculates all the adjacent node to the current one and creates a new
	 * node with the h- ,g- ,f-cost, x-cord, y-cord and parent node
	 * Also does test for adjacent walls, where no node can be created
	 * @return
	 */
	public ArrayList<LinkedNodes> calcAdjacentNodes() {

			current_x = current_node.getX();
			current_y = current_node.getY();
			System.out.println("current x: " +current_x +" current_y: "+current_y);
			
			currentBlock = new ArrayList<LinkedNodes>();
			
				//left
				 if(current_x - 1 >= 0 && (map[current_y][current_x - 1] == 0 
						 || map[current_y][current_x - 1] == 5)) {
					h = calcHCost(current_y, current_x-1); //get the heursitic cost
					g = calcGCost(false);				   // get the g cost
					f = g+h;							   //calc f cost
					tmp_node = new LinkedNodes(current_x - 1,current_y,h,g,f);	//create new node with given data
					tmp_node.setParent(current_node);							//set parent node
					if(tmp_node.getParent() != null) {
						tmp_node.setG(g + tmp_node.getParent().getG());		    //if node hast a parent, get g cost and add it to current g cost
						tmp_node.setF(h + tmp_node.getG());
					}
					boolean alreadyContained = true;
					for(LinkedNodes seen : seenNodes) {
						if(seen.getX() == tmp_node.getX() && seen.getY() == tmp_node.getY()) {
							alreadyContained = false;
						}
					}
					if(alreadyContained) {
						seenNodes.add(tmp_node);				
					}
					currentBlock.add(tmp_node);			
				 }
				//right
				 if(current_x + 1 < colSize && (map[current_y][current_x + 1] == 0 
						 || map[current_y ][current_x + 1] == 5)) {
					h = calcHCost(current_y, current_x+1);
					g = calcGCost(false);
					f = g+h;
					tmp_node = new LinkedNodes(current_x + 1,current_y,h,g,f);
					tmp_node.setParent(current_node);
					if(tmp_node.getParent() != null) {
						tmp_node.setG(g + tmp_node.getParent().getG());
						tmp_node.setF(h + tmp_node.getG());
					}
					boolean alreadyContained = true;
					for(LinkedNodes seen : seenNodes) {
						if(seen.getX() == tmp_node.getX() && seen.getY() == tmp_node.getY()) {
							alreadyContained = false;
						}
					}
					if(alreadyContained) {
						seenNodes.add(tmp_node);						
					}
					currentBlock.add(tmp_node);				
				 }
				//up-middle
				 if(current_y - 1 >= 0 && (map[current_y -1 ][current_x] == 0 
						 || map[current_y - 1][current_x] == 5)) {
					h = calcHCost(current_y - 1, current_x);
					g = calcGCost(false);
					f = g+h;
					tmp_node = new LinkedNodes(current_x ,current_y - 1,h,g,f);
					tmp_node.setParent(current_node);
					if(tmp_node.getParent() != null) {
						tmp_node.setG(g + tmp_node.getParent().getG());
						tmp_node.setF(h + tmp_node.getG());
					}
					boolean alreadyContained = true;
					for(LinkedNodes seen : seenNodes) {
						if(seen.getX() == tmp_node.getX() && seen.getY() == tmp_node.getY()) {
							alreadyContained = false;
						}
					}
					if(alreadyContained) {
						seenNodes.add(tmp_node);					
					}
					currentBlock.add(tmp_node);					
				 }
				//down-middle
				 if(current_y + 1 < colSize && (map[current_y + 1][current_x] == 0 
						 || map[current_y + 1][current_x] == 5)) {
					h = calcHCost(current_y + 1, current_x);
					g = calcGCost(false);
					f = g+h;
					tmp_node = new LinkedNodes(current_x ,current_y + 1,h,g,f);
					tmp_node.setParent(current_node);
					if(tmp_node.getParent() != null) {
						tmp_node.setG(g + tmp_node.getParent().getG());
						tmp_node.setF(h + tmp_node.getG());
					}
					boolean alreadyContained = true;
					for(LinkedNodes seen : seenNodes) {
						if(seen.getX() == tmp_node.getX() && seen.getY() == tmp_node.getY()) {
							alreadyContained = false;
						}
					}
					if(alreadyContained) {
						seenNodes.add(tmp_node);					
					}
					currentBlock.add(tmp_node);				
				 }
				 if(diagNodes) { //diagnal aswell
					//up-left
					 if(current_x - 1 >= 0 && current_y - 1 >= 0 && 
							 (map[current_y][current_x - 1] != 1  || map[current_y-1][current_x] != 1 )) {//so we don't search through walls
					 if(current_y - 1 >= 0 && current_x - 1 >= 0 && (map[current_y - 1][current_x - 1] == 0 
							 || map[current_y - 1][current_x - 1] == 5)) {
						h = calcHCost(current_y - 1, current_x - 1);
						g = calcGCost(true);
						f = g+h;
						tmp_node = new LinkedNodes(current_x - 1,current_y - 1,h,g,f);
						tmp_node.setParent(current_node);
						if(tmp_node.getParent() != null) {
							tmp_node.setG(g + tmp_node.getParent().getG());
							tmp_node.setF(h + tmp_node.getG());
						}
						boolean alreadyContained = true;
						for(LinkedNodes seen : seenNodes) {
							if(seen.getX() == tmp_node.getX() && seen.getY() == tmp_node.getY()) {
								alreadyContained = false;
							}
						}
						if(alreadyContained) {
							seenNodes.add(tmp_node);						
						}
						currentBlock.add(tmp_node);					
					 	}
					 }
					//up-right
					 if(current_x + 1 < colSize && current_y - 1 >= colSize &&  
							 map[current_y][current_x + 1] != 1 || map[current_y - 1][current_x] != 1) {
					 if(current_y - 1 >= 0 && current_x + 1 < colSize && (map[current_y - 1][current_x + 1] == 0 
							 || map[current_y - 1][current_x + 1] == 5)) {
						h = calcHCost(current_y - 1, current_x + 1);
						g = calcGCost(true);
						f = g+h;
						tmp_node = new LinkedNodes(current_x + 1,current_y - 1,h,g,f);
						tmp_node.setParent(current_node);
						if(tmp_node.getParent() != null) {
							tmp_node.setG(g + tmp_node.getParent().getG());
							tmp_node.setF(h + tmp_node.getG());
						}
						boolean alreadyContained = true;
						for(LinkedNodes seen : seenNodes) {
							if(seen.getX() == tmp_node.getX() && seen.getY() == tmp_node.getY()) {
								alreadyContained = false;
							}
						}
						if(alreadyContained) {
							seenNodes.add(tmp_node);
						
						}
						currentBlock.add(tmp_node);
					 }
				 }
					//down-right
					 if(current_x + 1 < colSize &&  current_y + 1 < colSize && 
							 (map[current_y][current_x + 1] != 1  || map[current_y + 1][current_x] != 1 )) {
					 if(current_x + 1 < colSize && current_y + 1 < colSize &&
							 (map[current_y + 1][current_x + 1] == 0 || map[current_y + 1][current_x + 1] == 5)) {
						h = calcHCost(current_y + 1, current_x + 1);
						g = calcGCost(true);
						f = g+h;
						tmp_node = new LinkedNodes(current_x + 1,current_y + 1,h,g,f);
						tmp_node.setParent(current_node);
						if(tmp_node.getParent() != null) {
							tmp_node.setG(g + tmp_node.getParent().getG());
							tmp_node.setF(h + tmp_node.getG());
						}
						boolean alreadyContained = true;
						for(LinkedNodes seen : seenNodes) {
							if(seen.getX() == tmp_node.getX() && seen.getY() == tmp_node.getY()) {
								alreadyContained = false;
							}
						}
						if(alreadyContained) {
							seenNodes.add(tmp_node);						
						}
						currentBlock.add(tmp_node);
					 }
					 }
					//down-left
					 if(current_x - 1 >= 0 &&  current_y + 1 >= 0 && 
							 map[current_y][current_x - 1] != 1  || map[current_y + 1][current_x] != 1 ) {
					 if(current_y + 1 < colSize && current_x - 1 >= 0 && (map[current_y + 1][current_x - 1] == 0 
							 || map[current_y + 1][current_x - 1] == 5)) {
						h = calcHCost(current_y + 1, current_x - 1);
						g = calcGCost(true);
						f = g+h;
						tmp_node = new LinkedNodes(current_x - 1,current_y + 1,h,g,f);
						tmp_node.setParent(current_node);
						if(tmp_node.getParent() != null) {
							tmp_node.setG(g + tmp_node.getParent().getG());
							tmp_node.setF(h + tmp_node.getG());
						}
						boolean alreadyContained = true;
						for(LinkedNodes seen : seenNodes) {
							if(seen.getX() == tmp_node.getX() && seen.getY() == tmp_node.getY()) {
								alreadyContained = false;
							}
						}
						if(alreadyContained) {
							seenNodes.add(tmp_node);
							
						}
						currentBlock.add(tmp_node);
					 }
				 }				 				 
				 }			
				 return seenNodes;
	}
	
	boolean found_path = false;
	int lowest_f;
	/**
	 * Find the node with the lowest f value, if there is more than 
	 * 1 node with the same f value, looks at the h cost 
	 * @return
	 */
	public ArrayList<LinkedNodes> findLowestCost() {
		
		
		if(currentBlock.size() != 0) {	
			lowest_f = currentBlock.get(0).getF();	
		ArrayList<LinkedNodes> tmp_nodes = new ArrayList<LinkedNodes>();
		
		//look for smalles f value
		for(int i = 0; i < currentBlock.size(); i++) {
			System.out.println("lowest: " +currentBlock.get(i).getF());
			if(currentBlock.get(i).getF() <= lowest_f) {
				lowest_f = currentBlock.get(i).getF();

			}
		}
		
		//if there a more than 1 with this f value add them to a list
		for(int i = 0; i < currentBlock.size(); i++) {
			if(currentBlock.get(i).getF() == lowest_f) {
				tmp_nodes.add(currentBlock.get(i));
				currentBlock.get(i).setInPath(true);
			}
		}
		
		/*from the list that contain all nodes with the same f value,
		 * select the one with the lowest h value.
		 * If all h values are the same, select one at random
		 */	
		if(tmp_nodes.size() > 1) {		
			for(int i = 0; i < tmp_nodes.size(); i++) {
				if(i+1 < tmp_nodes.size()) {
					if(tmp_nodes.get(i).getH() < tmp_nodes.get(i+1).getH()) {
						tmp_nodes.remove(i+1);
					} else if(tmp_nodes.get(i).getH() > tmp_nodes.get(i+1).getH()) {
						tmp_nodes.remove(i);
					}
				}
			}	
		}
	
		//If there is a cheaper node in the seenNode, choose that one
		for(LinkedNodes seen : seenNodes) {
					if(seen.getF() <= tmp_nodes.get(0).getF() && seen.getInPath() == false) {
						System.out.println("seen F:  "+seen.getF() +" x: " +seen.getX() + " y: " +seen.getY());
						tmp_nodes.add(0, seen);
						seen.setInPath(true);
					}
				}
		
		for(LinkedNodes seen : seenNodes) {
			if(tmp_nodes.get(0).getX() == seen.getX() && tmp_nodes.get(0).getY() == seen.getY() ) {
				seen.setInPath(true);
			}
		}
		

		path.add(tmp_nodes.get(0));
		current_node = tmp_nodes.get(0);
		
	
			return path;
		} else {
			System.out.println("No path found");
			return null;
		}
		
		

	}
	
	/**
	 * Calculates the h cost using the euclidean distance times 10 as a heuristic
	 * @param start_x
	 * @param start_y
	 * @return
	 */
	public int calcHCost(int start_y, int start_x) {
		int x = end_x - start_x;
		int y = end_y - start_y;
		x = Math.abs(x);
		y = Math.abs(y);
		int h_x = (int) Math.pow(x , 2);
		int h_y = (int) Math.pow(y , 2);
		
		int res = (int) (Math.sqrt(h_x + h_y) * 10);
		return res;
	}
	
	
	/**
	 * Return the g cost to get to this node, from the current node.
	 * If it is a diagonal node, the cost i sqrt(2) * 10, if not 1*10
	 * @param diag	true, if node is diagonal
	 * @return
	 */
	public int calcGCost(boolean diag) {
		if(diag) {
			return (int) (Math.sqrt(2) * 10);
		} else {
			return (1*10);
		}
	}

}
