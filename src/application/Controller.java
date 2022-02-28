package application;


import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class Controller implements Initializable   {

	
	int rowSize;
	int colSize;
	int tileSize = 40; //25x25
	int start_point[] = new int[2];
	int end_point[] = new int[2];
	
	@FXML
	GridPane root;
	
	@FXML
	CheckBox start_tile;
	
	@FXML
	CheckBox end_tile;
	
	@FXML
	Button run_button;
	
	@FXML
	Button clear_button;
	
	@FXML 
	Label label;
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		fillGridPane();
		clear(4);
	
	}

	
	A_Star aSearch;
	ArrayList<LinkedNodes> nodes;
	boolean flag = true;
	boolean drawFlag = false;
	String fVal, hVal, gVal;
	/**
	 * Called from the step button to show the next step.
	 * @param a
	 */
	public void runSearch(ActionEvent a) {
		if(flag) {
		
			aSearch = new A_Star(start_point, end_point,tileColour, colSize, rowSize);
			flag = false;
		}
		

		if(!(drawFlag)) {
		nodes = aSearch.calcAdjacentNodes();
			for(int i = 0 ; i < nodes.size(); i++) {
				if(nodes.get(i).getParent() != null) {
					fVal = Integer.toString(nodes.get(i).getF());
					hVal = Integer.toString(nodes.get(i).getH());
					gVal = Integer.toString(nodes.get(i).getG());
					drawTiles(nodes.get(i).getY(),nodes.get(i).getX(), "NODES");					
				}			
			}
	
			drawFlag = true;
		} else if (drawFlag) {	
			
			nodes = aSearch.findLowestCost();
			if(nodes ==  null) {
				foundPath("No path found!");
			} else {
			for(int i = 0 ; i < nodes.size(); i++) {
				if(nodes.get(i).getParent() != null) {
					fVal = Integer.toString(nodes.get(i).getF());
					hVal = Integer.toString(nodes.get(i).getH());
					gVal = Integer.toString(nodes.get(i).getG());
					drawTiles(nodes.get(i).getY(),nodes.get(i).getX(), "PATH");
					if(nodes.get(i).getH() == 10 || nodes.get(i).getH() == 14) {
						foundPath("Path found!");
					}				
				}
			 }
			}
			nodes = aSearch.calcAdjacentNodes();
			for(int i = 0 ; i < nodes.size(); i++) {
				if(nodes.get(i).getParent() != null) {
					fVal = Integer.toString(nodes.get(i).getF());
					hVal = Integer.toString(nodes.get(i).getH());
					gVal = Integer.toString(nodes.get(i).getG());
					drawTiles(nodes.get(i).getY(),nodes.get(i).getX(), "NODES");					
				}				
			}
		
		} 
		
		}
	
	
	/**
	 * Called when a path is/is not found, lockes the run button and sets the label text
	 * @param result	either "path found" or "path not found"
	 */
	public void foundPath(String result) {
		label.setText(result);
		run_button.setDisable(true);
		
	}
	
	/**
	 * Button to clear all tiles and reset flag,label, and run button 
	 * @param a
	 */
	public void clearButton(ActionEvent a) {
		clear(4);
		flag = true;
		drawFlag = false;
		label.setText("");
		run_button.setDisable(false);
	}
	
	
	/**
	 * OnMouseDragged listener
	 * Used to get the view coordinates to draw/erase the tiles
	 * @param e
	 */
	public void onMouseDragged(MouseEvent e) {
		int x = (int) e.getX();
		int y = (int) e.getY();
		
		String switch_string = "";
		if(e.getButton() == MouseButton.PRIMARY && !(start_tile.isSelected())) {
			switch_string = "PRIMARY";
			calcIndex(x,y,switch_string);
		} else if (e.getButton() == MouseButton.SECONDARY) {
			switch_string = "SECONDARY";
			calcIndex(x,y,switch_string);
		} 
	
	}
	
	
	boolean start_isSet = false;
	boolean end_isSet = false;
	/**
	 * Is for getting the view coordinates on click, to later calc the row/col 
	 * of the start/end tile
	 * @param e
	 */
	public void onMouseClicked(MouseEvent e) {
		
		int x = (int) e.getX();
		int y = (int) e.getY();
		String mouse_button = "";
		if(start_tile.isSelected()) {
			mouse_button = "START_TILE";
			start_isSet = true;
			calcIndex(x,y,mouse_button);
		} else if (end_tile.isSelected()) {
			mouse_button = "END_TILE";
			end_isSet = true;
			calcIndex(x,y,mouse_button);
		}
	}
	
	
	
	
	
	ImageView imageViews[][];
	Text fTexts[][];
	Text hTexts[][];
	Text gTexts[][];
	Image gray_image = new Image("application/1.png");
	Image white_image = new Image("application/0.png");
	Image blue_image = new Image("application/2.png");
	Image red_image = new Image("application/3.png");
	Image lightBlue_image = new Image("application/4.png");
	Image darkRed_image = new Image("application/6.png");
	int tileColour[][];
	
	/**
	 * Called once from the init method. Fills the Gridpane with cells, where each cell
	 * contain 3 Texts, one for the h cost, one for the g cost and one for the f cost. 
	 * Also adds 1 ImageView to each cell
	 */
	@SuppressWarnings("static-access")
	private void fillGridPane() {

		ImageView iView;
		Text fText;
		Text hText;
		Text gText;
		
		rowSize = (int) (root.getMaxWidth()/tileSize);
		colSize = (int) (root.getMaxHeight()/tileSize);

		imageViews = new ImageView[rowSize][colSize];
		fTexts = new Text[rowSize][colSize];
		hTexts = new Text[rowSize][colSize];
		gTexts = new Text[rowSize][colSize];
		
		tileColour = new int[rowSize][colSize];
		
		for(int row = 0; row < rowSize; row++) {
			for(int col = 0; col < colSize; col++) {
				iView = new ImageView();
				iView.setFitHeight(tileSize);
				iView.setFitWidth(tileSize);
				
				fText = new Text();
				fText.setFont(new Font(tileSize/3));
				fText.setText("");
				
				hText = new Text();
				hText.setFont(new Font(tileSize/4));
				hText.setText("");
				
				gText = new Text();
				gText.setFont(new Font(tileSize/4));
				gText.setText("");				
				
				root.setHalignment(fText, HPos.CENTER);
				root.setHalignment(hText, HPos.LEFT);
				root.setValignment(hText, VPos.TOP);
				root.setHalignment(gText, HPos.RIGHT);
				root.setValignment(gText, VPos.BOTTOM);			
				
				root.add(iView, col, row);
				
				fTexts[row][col] = fText;
				hTexts[row][col] = hText;
				gTexts[row][col] = gText;
				imageViews[row][col] = iView;
				tileColour[row][col] = 0;
				
				root.add(fText, col, row);
				root.add(hText, col, row);
				root.add(gText, col, row);
			
				
			}
		}
	}
	
	
	/**
	 * Calc column and row index for GridPane from scene coordinates
	 * @param x
	 * @param y
	 */
	private void calcIndex(int x, int y, String mouse_button) {
		int col = (int) (x/tileSize);
		int row = (int) (y/tileSize);

		if(mouse_button.equals("START_TILE")) {
			start_point[0] = row;
			start_point[1] = col;
		} else if(mouse_button.equals("END_TILE")) {
			end_point[0] = row;
			end_point[1] = col;
		}
		drawTiles(row, col, mouse_button);
	}
	
	
	/**
	 * Draws the tiles according to the paramerters give, for walls, starting tile, end tile
	 * , seen Node, path and erasing 
	 * @param col
	 * @param row
	 * @param switch_string
	 */
	public void drawTiles(int row, int col, String switch_string) {
		
		if(start_isSet && start_tile.isSelected()) {
			clear(2);
		} else if(end_isSet && end_tile.isSelected()) {
			clear(3);
		}
		
		if(row >= 0 && row < rowSize && col >= 0 && col < colSize) {
			if(tileColour[row][col] == 0 && switch_string.equals("PRIMARY")) { //1 means the tile is gray
				imageViews[row][col].setImage(gray_image);
				tileColour[row][col] = 1;
			} else if(tileColour[row][col] != 0 && switch_string.equals("SECONDARY")) { //for erasing
				imageViews[row][col].setImage(white_image);
				tileColour[row][col] = 0;
			} else if(start_tile.isSelected()) {
				imageViews[row][col].setImage(blue_image);	//starting tile
				tileColour[row][col] = 2;
				start_tile.setSelected(false);
			} else if(end_tile.isSelected()) {
				imageViews[row][col].setImage(red_image);	//end tile
				tileColour[row][col] = 3;			
				end_tile.setSelected(false);
			} else if(switch_string.equals("NODES") && tileColour[row][col] != 6){ //seen Nodes
				imageViews[row][col].setImage(lightBlue_image);
				tileColour[row][col] = 5;
				fTexts[row][col].setText(fVal);
				gTexts[row][col].setText(gVal);
				hTexts[row][col].setText(hVal);
			}
			else if(switch_string.equals("PATH")){	//Path
				imageViews[row][col].setImage(darkRed_image);
				tileColour[row][col] = 6;
				fTexts[row][col].setText(fVal);
				gTexts[row][col].setText(gVal);
				hTexts[row][col].setText(hVal);
			}
		}
		
	}
	
	/**
	 * Removes the coloured tile base on the integer given
	 * 2 -> blue
	 * 3 -> red
	 * 4 -> clear all
	 * @param colour
	 */
	private void clear(int colour) {
			for(int row = 0; row < rowSize; row++) { 
				for(int col = 0; col < colSize; col++) { 
					if(tileColour[row][col] == colour) {
							
					imageViews[row][col].setImage(white_image);
					tileColour[row][col] = 0;
					} else if (colour == 4) {
						imageViews[row][col].setImage(white_image);
						tileColour[row][col] = 0;
						fTexts[row][col].setText("");
						gTexts[row][col].setText("");
						hTexts[row][col].setText("");
					}
			}
		}
	}


	
	
	
	
	
}
