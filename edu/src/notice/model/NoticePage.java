package notice.model;

import java.util.ArrayList;

import common.PageDTO;

public class NoticePage extends PageDTO{
	
	private ArrayList<NoticeDTO> list;
	
	public ArrayList<NoticeDTO> getList() {
		return list;
	}
	
	public void setList(ArrayList<NoticeDTO> list) {
		this.list = list;
	}
	
}
