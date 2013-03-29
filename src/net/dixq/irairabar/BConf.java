package net.dixq.irairabar;

public class BConf {
	public float speed = 0;
	public Barricade.eType type = Barricade.eType.OUT;
	
	public BConf(Barricade.eType atype){
		type = atype;
	}

	public BConf(float aspeed){
		speed = aspeed;
	}

}
