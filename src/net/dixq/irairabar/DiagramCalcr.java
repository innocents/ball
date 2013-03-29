package net.dixq.irairabar;

import android.graphics.PointF;

public class DiagramCalcr {

	// center‚ğ’†S‚ÉŠp“xangA’¸“_ŒQpt‚ğ‰ñ“]‚·‚é
	public static void RotateDiagram(PointF pt[], final PointF center, final float ang) {
		for (int i = 0; i < pt.length; i++) {
			RotatePt(pt[i], center, ang);
		}
	}

	// rotaPt‚ğ’†S‚ÉŠp“xangAorigPt‚ğ‰ñ“]‚·‚é
	public static void RotatePt(PointF rotaPt, final PointF origPt, final float ang) {
		float cx = origPt.x;
		float cy = origPt.y;
		float x = rotaPt.x;
		float y = rotaPt.y;
		rotaPt.x = (float) (cx + Math.cos(ang) * (x - cx) - Math.sin(ang) * (y - cy));
		rotaPt.y = (float) (cy + Math.sin(ang) * (x - cx) + Math.cos(ang) * (y - cy));
	}

	// ’¸“_ŒQpt‚Ìü•ª‚Æcir‚ªÚG‚µ‚Ä‚¢‚½‚ç‚»‚ÌÚG‚µ‚Ä‚¢‚éƒxƒNƒgƒ‹‚ğver‚ÉŠi”[‚µ‚Ätrue‚ğ•Ô‚·
	public static boolean isHit(PointF pt[], final Circle cir, Vec vec) {
		if (pt.length < 2) { // ü‚Å‚È‚¯‚ê‚Î
			return false;
		}
		int len = pt.length;
		for (int i = 1; i <= len; i++) {//—á‚¦‚Îü•ª0-1,1-2,2-3,3-0‚Æƒ‹[ƒv‚³‚¹‚Ä‚Â‚È‚°‚éˆ×%‚ğg—p‚µ‚Äƒ‹[ƒv
			Line line = new Line(pt[i - 1].x, pt[i - 1].y, pt[i % len].x, pt[i % len].y);
			if (isHitLC(line, cir) == true) {//ÚG‚µ‚Ä‚¢‚ê‚Î
				vec._x= pt[i % len].x - pt[i - 1].x;//‚»‚Ìü•ª‚ÌƒxƒNƒgƒ‹‚ğŠi”[‚·‚é
				vec._y= pt[i % len].y - pt[i - 1].y;
				return true;
			}
		}
		return false;
	}

	//ü•ªline‚Æ‰~cir‚ª“–‚½‚Á‚Ä‚¢‚ê‚Îtrue‚ğ•Ô‚·
	public static boolean isHitLC(Line L,Circle C){
		// ‰~‚Æü•ª‚Ì“–‚½‚è”»’èŠÖ”
		if((L._sx*(C._x-L._x) + L._sy*(C._y-L._y)) <= 0){
			// n“_‚ğ’Ê‚é¤ü•ª‚É‚’¼‚Èü‚ğ’u‚¢‚½‚Æ‚«¤‰~‚Ì’†S‚ªü•ª‚Ì”ÍˆÍŠO‚É‚ ‚Á‚½‚Æ‚«
			// ¢ü•ª‚Ìn“_‚©‚ç‰~‚Ì’†S‚Ü‚Å‚Ì‹——£‚Ì‚Qæ£‚Æ¢‰~‚Ì”¼Œa‚Ì‚Qæ£‚Æ‚Ì”äŠr
			return (C._r*C._r >= (C._x-L._x)*(C._x-L._x)+(C._y-L._y)*(C._y-L._y));
		} else if(((-L._sx)*(C._x-(L._x+L._sx)) + (-L._sy)*(C._y-(L._y+L._sy))) <= 0){
			// I“_‚ğ’Ê‚é¤ü•ª‚É‚’¼‚Èü‚ğ’u‚¢‚½‚Æ‚«¤‰~‚Ì’†S‚ªü•ª‚Ì”ÍˆÍŠO‚É‚ ‚Á‚½‚Æ‚«
			// ¢ü•ª‚ÌI“_‚©‚ç‰~‚Ì’†S‚Ü‚Å‚Ì‹——£‚Ì‚Qæ£‚Æ¢‰~‚Ì”¼Œa‚Ì‚Qæ£‚Æ‚Ì”äŠr
			return (C._r*C._r >= (C._x-(L._x+L._sx))*(C._x-(L._x+L._sx))+(C._y-(L._y+L._sy))*(C._y-(L._y+L._sy)));
		} else {
			// ü•ª‚Ìn“_I“_‚É‚ü‚ğˆø‚Á’£‚Á‚½‚Æ‚«¤‰~‚Ì’†S‚ª‚»‚Ì”ÍˆÍ“à‚É‚ ‚Á‚½‚Æ‚«
			float e = (float) Math.sqrt(L._sx*L._sx + L._sy*L._sy);   // ‚±‚ê‚Åx,y¬•ª‚ğŠ„‚ê‚Î’PƒxƒNƒgƒ‹‚É‚È‚é
			float c2 = (C._x-L._x)*(C._x-L._x)+(C._y-L._y)*(C._y-L._y);
			float b = (C._x-L._x)*(L._sx/e)+(C._y-L._y)*(L._sy/e);   // “àÏ‚ÅZo‚µ‚½¤—×•Ó‚Ì’·‚³
			return (C._r*C._r >= c2 - b*b);
		}
	}
}
