package com.example.android.securelogin;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;

import java.util.ArrayList;
import java.util.Random;

public class MathCaptcha extends Captcha {
	int number;
	int one = 0;
	int two = 0;
	int three = 0;
	int four = 0;
	public MathOptions options;
	
	public enum MathOptions{
		PLUS_MINUS,
		PLUS_MINUS_MULTIPLY
}
	public MathCaptcha(int width, int height, MathOptions opt, int number){
		this.height = height;
    	setWidth(width);
    	this.options = opt;
		usedColors = new ArrayList<Integer>();
		this.image = image();
		this.number = number;
		//System.out.println(number);

	}
	Bitmap bitmap;
	@Override
	protected Bitmap image() {
	    int math = 0;
	    LinearGradient gradient = new LinearGradient(0, 0, getWidth() / 2, this.height / 2, color(), color(), Shader.TileMode.MIRROR);
	    Paint p = new Paint();
	    p.setDither(true);
	    p.setShader(gradient);
	    bitmap = Bitmap.createBitmap(getWidth(), this.height, Config.ARGB_8888);
	    Canvas c = new Canvas(bitmap);
	    c.drawRect(0, 0, getWidth(), this.height, p);
	    
	    LinearGradient fontGrad = new LinearGradient(0, 0, getWidth() / 2, this.height / 2, color(), color(), Shader.TileMode.CLAMP);
	    Paint tp = new Paint();
	    tp.setDither(true);
	    tp.setShader(fontGrad);
	    tp.setTextSize(getWidth() / this.height * 20);
	    Random r = new Random(System.currentTimeMillis());
		number = r.nextInt(9999-1000)+1000;
		System.out.println("Number recieved is "+number);
		one = number%10;
		//one = r.nextInt(9) + 1;
		two = (number/10)%10;
		// two = r.nextInt(9) + 1;
		three = (number/100)%10;
		// three = r.nextInt(9) + 1 ;
		four = (number/1000)%10;
		//four = r.nextInt(9) + 1;
		//math = r.nextInt((options == MathOptions.PLUS_MINUS_MULTIPLY)?3:2);
	/*	if (one < two) {
			Integer temp = one;
			one = two;
			two = temp;
		} */
		
	/*	switch (math) {
			case 0:
				this.ans = (1000*one + 100*two + 10*three + four) ;
				break;
			case 1:
				this.ans = (1000*one + 100*two + 10*three + four);
				break;
			case 2:
				this.ans = (1000*one + 100*two + 10*three + four);
				break;
		}
	*/

	    char[] data = new char[]{String.valueOf(one).toCharArray()[0], String.valueOf(two).toCharArray()[0] , String.valueOf(three).toCharArray()[0], String.valueOf(four).toCharArray()[0]};
		this.ans = (1000*one + 100*two + 10*three + four);
		for (int i=0; i<data.length; i++) {
	        x += 20 + (Math.abs(r.nextInt()) % 65);
	        y = 50 + Math.abs(r.nextInt()) % 50;
	        Canvas cc = new Canvas(bitmap);
	        if(i != 1)
	        	tp.setTextSkewX(r.nextFloat() - r.nextFloat());
	        cc.drawText(data, i, 1, x, y, tp);
	        tp.setTextSkewX(0);
	    }
	    return bitmap;
	}
	
	public static char oper (Integer math) {
		switch (math) {
		case 0:
			return '+';
		case 1:
			return '-';
		case 2:
			return '*';
		}
		return '+';
	}
}
