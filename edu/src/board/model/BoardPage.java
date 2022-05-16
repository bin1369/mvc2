package board.model;

import java.util.ArrayList;

import common.PageDTO;

public class BoardPage extends PageDTO {
	private ArrayList<BoardDTO> list;

	public ArrayList<BoardDTO> getList() {
		return list;
	}

	public void setList(ArrayList<BoardDTO> list) {
		this.list = list;
	}
	
}
