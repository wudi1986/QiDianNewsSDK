package com.news.qidian.widget.customdialog;


import com.news.qidian.widget.customdialog.effects.BaseEffects;
import com.news.qidian.widget.customdialog.effects.FadeIn;
import com.news.qidian.widget.customdialog.effects.FallType;
import com.news.qidian.widget.customdialog.effects.FlipH;
import com.news.qidian.widget.customdialog.effects.FlipV;
import com.news.qidian.widget.customdialog.effects.NewsPaper;
import com.news.qidian.widget.customdialog.effects.RotateBottom;
import com.news.qidian.widget.customdialog.effects.RotateLeft;
import com.news.qidian.widget.customdialog.effects.ShakeType;
import com.news.qidian.widget.customdialog.effects.SideFall;
import com.news.qidian.widget.customdialog.effects.SlideBottom;
import com.news.qidian.widget.customdialog.effects.SlideLeft;
import com.news.qidian.widget.customdialog.effects.SlideRight;
import com.news.qidian.widget.customdialog.effects.SlideTop;
import com.news.qidian.widget.customdialog.effects.SlitType;

public enum  Effectstype {

    Fadein(FadeIn.class),
    Slideleft(SlideLeft.class),
    Slidetop(SlideTop.class),
    Slidebottom(SlideBottom.class),
    Slideright(SlideRight.class),
    Fall(FallType.class),
    Newspager(NewsPaper.class),
    Fliph(FlipH.class),
    Flipv(FlipV.class),
    Rotatebottom(RotateBottom.class),
    Rotateleft(RotateLeft.class),
    Slit(SlitType.class),
    Shake(ShakeType.class),
    Sidefill(SideFall.class);
    private Class<? extends BaseEffects> effectsClazz;

    private Effectstype(Class<? extends BaseEffects> mclass) {
        effectsClazz = mclass;
    }

    public BaseEffects getAnimator() {
        BaseEffects bEffects=null;
	try {
		bEffects = effectsClazz.newInstance();
	} catch (ClassCastException e) {
		throw new Error("Can not init animatorClazz instance");
	} catch (InstantiationException e) {
		throw new Error("Can not init animatorClazz instance");
	} catch (IllegalAccessException e) {
		// TODO Auto-generated catch block
		throw new Error("Can not init animatorClazz instance");
	}
	return bEffects;
    }
}
