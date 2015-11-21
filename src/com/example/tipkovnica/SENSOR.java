package com.example.tipkovnica;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.opengl.Matrix;
import android.view.Gravity;
import android.widget.Toast;

public class SENSOR
{
	public String Message;
	public MojaTipkovnica mKeys;
	public Context context;
	public Toast toast;
	public boolean keysLocked;
	public float maxAccX;
	public float maxAccY;
	public float maxAccZ;
	public float speedX;
	public float speedY;
	public float speedZ;
	public float distanceX;
	public float distanceY;
	public float distanceZ;
	public long startTimeX;
	public long stopTimeX;
	public long startTimeY;
	public long stopTimeY;
	public long startTimeZ;
	public long stopTimeZ;
	public long clickTimeZ;
	public int clickZ;
	private boolean timeFlagX;
	private boolean timeFlagY;
	private boolean timeFlagZ;
	public String vertical;
	public String horizontal;
	public float verLast;
	public float horLast;
	private boolean horFlag;
	private boolean verFlag;
	private boolean calcIndex = true;
	public float firstX;
	public float firstY;
	public float firstZ;
	public int Index;
	public int Row;
	
	private int counterInput;
	private int counterRow;
	
	public SensorEvent e;
	public float Roll;
	public float Pitch;
	public float Yaw;
	private float lastRoll;
	public float rollDistance;
	private float lastRollDistance;
	
	public float firstRoll = 0;
	public float firstPitch = 0;
	public float firstYaw = 0;

	public float AccX;
	public float AccY;
	public float AccZ;
	
	public float rotationX;
	public float rotationY;
	public float rotationZ;
	
	public float alpha;
	public float[] gravity;
	public float[] geomagnetic;
	
	
	
	public float[] trueacceleration = new float[4];
	public float[] R = new float[16];
	public float[] RINV = new float[16];    
	public float[] I = new float[16];
	public float[] linear;
	public float azimut;
	public float azimutTrue;
	
	private boolean firstTime = true;
	 /* <Row>
        <Key android:codes="97"    android:keyLabel="a" android:keyEdgeFlags="left" android:popupKeyboard="@xml/popup" android:popupCharacters="A"  />
       	<Key android:codes="115"    android:keyLabel="s" android:popupKeyboard="@xml/popup" android:popupCharacters="S" />
        <Key android:codes="100"    android:keyLabel="d" android:popupKeyboard="@xml/popup" android:popupCharacters="D" />
        <Key android:codes="102"    android:keyLabel="f" android:popupKeyboard="@xml/popup" android:popupCharacters="F" android:horizontalGap="6.25%p" />
        <Key android:codes="103"    android:keyLabel="g" android:popupKeyboard="@xml/popup" android:popupCharacters="G" />
        <Key android:codes="104"    android:keyLabel="h" android:popupKeyboard="@xml/popup" android:popupCharacters="H" />
        <Key android:codes="106"    android:keyLabel="j" android:popupKeyboard="@xml/popup" android:popupCharacters="J" />
        <Key android:codes="107"    android:keyLabel="k" android:popupKeyboard="@xml/popup" android:popupCharacters="K" />
        <Key android:codes="108"    android:keyLabel="l" android:popupKeyboard="@xml/popup" android:popupCharacters="L"  />
         </Row>*/
	/*public int[][] Keys = {{113,119,101,114,116,121,117,105,111,112},
			{97,97,115,100,102,103,104,106,107,108},
			{122,122,120,99,118,98,110,109,-50,-5}};*/
	public int[][] Keys = {{113,119,101,114,116,121,117},
							{97,115,100,102,103,104,106},
							{122,120,99,118,98,110,109},
							{105,105,111,112,107,108,108},
							{-55,46,-50,-50,44,-5,-5}};
	private int[][] KeysLeft = {{0,116,114,101,119,113},
								{0,103,102,100,115,97}};
	private int[][] KeysRight = {{0,121,117,105,111,112},
								{0,104,106,107,108,108}};
	private float[][] IndexValues= {{}};
	public int currKey;
	public int currIndex;
	public int currRow;
    public int keyPosition;
    
    public static int CODE = -100;
    private OnKeyboardActionListener mOnKeyboardActionListener = new OnKeyboardActionListener() {

        @Override public void onKey(int primaryCode, int[] keyCodes) {
        	if (CODE == primaryCode){
        		int tmp = 0;
				if ((mKeys.Shift)&&(Keys[Row][Index] > 96) && (Keys[Row][Index] < 123)) tmp = -32;
        		mKeys.putIntChar(Keys[Row][Index] + tmp);
        	}
        }

		@Override
		public void onPress(int primaryCode) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onRelease(int primaryCode) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onText(CharSequence text) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void swipeLeft() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void swipeRight() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void swipeDown() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void swipeUp() {
			// TODO Auto-generated method stub
			
		}
        
     };
	public SENSOR(){
		this.Message="";
		this.currRow = 0;
		this.lastRoll = 0;
		this.counterInput = 0;
		this.counterRow = 0;
		this.maxAccX=0;
		this.maxAccY=0;
		this.maxAccZ=0;
		this.distanceX = 0;
		this.distanceY = 0;
		this.distanceZ = 0;
		this.firstX = 0;
		this.firstY = 0;
		this.firstZ = 0;
		
		this.timeFlagX = false;
		this.timeFlagY = false;
		this.timeFlagZ = false;
		this.keysLocked = false;
		
		this.horFlag = false;
		this.verFlag = false;
		
		this.stopTimeX = 0;
		this.stopTimeY = 0;
		this.stopTimeZ = 0;
		this.speedX = 0;
		this.speedY = 0;
		this.speedZ = 0;
		this.clickZ = 0;
		
		this.vertical = "none";
		this.horizontal = "none";
		this.verLast = 0;
		this.horLast = 0;
		this.Row = 0;
	}
	public void setRoll(float values){
		this.Roll = values;
	}
	public void setPitch(float var){
		this.Pitch = var;
	}
	public void setYaw(float var){
		this.Yaw = var;
	}
	public void setRotationX(float var){
		this.rotationX = var;
	}
	public void setRotationY(float var){
		this.rotationY = var;
	}
	public void setRotationZ(float var){
		this.rotationZ = var;
	}
	public void setAccX(float var){
		this.AccX = var;
	}
	
	public void setAccY(float var){
		this.AccY = var;
	}
	public void setAccZ(float var){
		this.AccZ = var;
	}
	public void calculateIndex(){
		if (System.currentTimeMillis()-this.clickTimeZ >700) this.clickZ = 0;
		float step = (float) 0.37;
		//float midStep = (float) 0.05;
		if ((this.azimut < 	-step)) this.azimutTrue = 3;
		if ((this.azimut <  -2*step) && (this.azimut > -3*step)) this.azimutTrue = 2;
		if ((this.azimut <  -3*step) && (this.azimut > -4*step)) this.azimutTrue = 1;
		if ((this.azimut <  -4*step) && (this.azimut > -5*step)) this.azimutTrue = 0;
		if ((this.azimut > -step) && (this.azimut < step)) this.azimutTrue = 4;
		if ((this.azimut > 	step) && (this.azimut < 2*step)) this.azimutTrue = 5;
		if ((this.azimut >  2*step) && (this.azimut < 3*step)) this.azimutTrue = 6;
		if ((this.azimut >  3*step) && (this.azimut < 4*step)) this.azimutTrue = 7;
		if ((this.azimut >  4*step) && (this.azimut < 5*step)) this.azimutTrue = 8;
		if ((this.azimut >  5*step)) this.azimutTrue = 9;
		this.calcIndex = true;
		if (this.calcIndex){
			if (this.clickZ <1){
		//float midStep = (float) 0.05;
		/*if ((this.azimut < 	-step) && (this.azimut > -2*step)) this.Index = 3;
		if ((this.azimut <  -2*step) && (this.azimut > -3*step)) this.Index = 2;
		if ((this.azimut <  -3*step) && (this.azimut > -4*step)) this.Index = 1;
		if ((this.azimut <  -4*step) && (this.azimut > -5*step)) this.Index = 0;
		if ((this.azimut > -step) && (this.azimut < step)) this.Index = 4;
		if ((this.azimut > 	step) && (this.azimut < 2*step)) this.Index = 5;
		if ((this.azimut >  2*step) && (this.azimut < 3*step)) this.Index = 6;
		if ((this.azimut >  3*step) && (this.azimut < 4*step)) this.Index = 7;
		if ((this.azimut >  4*step) && (this.azimut < 5*step)) this.Index = 8;
		if ((this.azimut >  5*step) && (this.azimut < 6*step)) this.Index = 9;*/
				if ((this.azimut < 	-step) && (this.azimut > -2*step)) this.Index = 3;
				if ((this.azimut <  -2*step) && (this.azimut > -3*step)) this.Index = 2;
				if ((this.azimut <  -3*step) && (this.azimut > -4*step)) this.Index = 1;
				if ((this.azimut <  -4*step) && (this.azimut > -5*step)) this.Index = 0;
				if ((this.azimut > -step) && (this.azimut < step)) this.Index = 4;
				if ((this.azimut > 	step) && (this.azimut < 2*step)) this.Index = 5;
				if ((this.azimut >  2*step) && (this.azimut < 3*step)) this.Index = 6;
		this.calcIndex = false;
			}
		}
	}
	public void calculateRow(){
		/*if ((this.RINV[10] > 0.8)&&(this.RINV[10] < 0.9)) this.Row = 1;
		if ((this.RINV[10] > 0.93)) this.Row = 2;
		if ((this.RINV[10] < 0.75)) this.Row = 0;*/
		if ((this.RINV[10] < 0.40)) this.Row = 0;
		if ((this.RINV[10] > 0.47)&&(this.RINV[10] < 0.6)) this.Row = 1;
		if ((this.RINV[10] > 0.6)&&(this.RINV[10] < 0.73)) this.Row = 2;
		if ((this.RINV[10] > 0.73)&&(this.RINV[10] < 0.86)) this.Row = 3;
		if ((this.RINV[10] > 0.88)) this.Row = 4;
	}
	public void firstConfigDirection(){
		if (this.firstTime){
			this.firstTime = false;
			this.firstX = this.azimut;
			this.firstY = this.RINV[10];
		}
		this.azimut = this.azimut - this.firstX;
	}
	public void calculateKey(){
		if (!keysLocked){
		this.calculateIndex();
		this.calculateRow();
		}
	}
	public void calculateDirection(){
		if (this.horFlag){
		if (this.azimut > this.horLast) {
			this.horizontal = "right";
			this.horLast = this.azimut;
		}
		else if (this.azimut < this.horLast){
			this.horizontal = "left";
			this.horLast = this.azimut;
		}
		if (this.RINV[10] < this.verLast) {
			this.vertical = "up";
			this.verLast = this.RINV[10];
		}
		else {
			this.vertical = "down";
			this.verLast = this.RINV[10];
		}
		this.horFlag = false;
		}
		if (!this.horFlag){
			this.horFlag = true;
			this.horLast = this.azimut;
			this.verLast = this.RINV[10];
		}
	}
	public void input(SensorEvent event,MojaTipkovnica keys,Context context) {
		this.context = context;
		this.mKeys = keys;
		this.e = event;
		this.values(event);
	}
	public void values(SensorEvent event){
		if (event.accuracy != SensorManager.SENSOR_STATUS_UNRELIABLE){
			if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
				this.gravity = event.values;
				this.setYaw(event.values[0] - this.firstYaw);
				this.setPitch(event.values[1] - this.firstPitch);
				this.setRoll(event.values[2] - this.firstRoll);

			}
			if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION){
				float tempVal = 1;
				this.linear = event.values;
				this.setAccX(event.values[0]);
				this.setAccY(event.values[1]);
				this.setAccZ(event.values[2]);
				/// Distance X
				if (!timeFlagX){
					if (this.AccX > tempVal){
						this.startTimeX = System.currentTimeMillis();
						this.timeFlagX = true;
						this.firstRoll = this.AccX;
						this.speedX = this.AccX;
						this.calculateDirection();
					}
				}else {
					this.calcIndex = true;
					if (this.speedX < this.AccX) this.speedX = this.AccX;
					if ((this.AccX > -tempVal) && (this.AccX < tempVal)){
						this.calculateKey();
						this.stopTimeX = System.currentTimeMillis() - this.startTimeX;
						this.startTimeX = System.currentTimeMillis();
						if (this.stopTimeX > 3){
							if ( (Math.abs(this.AccX * this.stopTimeX/1000 * this.stopTimeX/1000)) > 0.005){
								this.calculateDirection();
								// TO DO
								// NApraviti sigurne vrijednosti svih slova! primjer -> 'a'=>0.1,izmeðuSlova=0.05 -- distance = 0.13 -> distance se zaokružuje na 0.1...znaèi bitno je zaokruživanje na cijeline slova!!
								if (this.horizontal == "right") this.distanceX += Math.abs(this.AccX * this.stopTimeX/1000 * this.stopTimeX/1000);
								else this.distanceX -= Math.abs(this.AccX * this.stopTimeX/1000 * this.stopTimeX/1000);
								this.calculateDirection();
							}
						}
					}
					if (this.AccX < -tempVal){
						this.stopTimeX = System.currentTimeMillis() - this.startTimeX;
						if (this.stopTimeX > 3){
							if ((Math.abs(this.speedX * this.stopTimeX/1000)) > 0.005){
								//this.calculateDirection();
								if (this.horizontal == "right") this.distanceX += Math.abs(this.speedX * this.stopTimeX/1000);
								else this.distanceX -= Math.abs(this.speedX * this.stopTimeX/1000);
								this.calculateKey();
								this.maxAccX = this.AccX;
								this.calculateDirection();
							}
						}
						this.timeFlagX = false;
					}
				}
				/// Distance Y
				if (!timeFlagY){
					if (this.AccY > tempVal){
						this.startTimeY = System.currentTimeMillis();
						this.timeFlagY = true;
						this.firstRoll = this.AccY;
						this.speedY = this.AccY;
					}
				}else {
					if ((this.AccY > -tempVal) && (this.AccY < tempVal)){
						this.stopTimeY = System.currentTimeMillis() - this.startTimeY;
						this.startTimeY = System.currentTimeMillis();
						if (this.stopTimeY > 3){
							if ( (Math.abs(this.AccY * this.stopTimeY/1000 * this.stopTimeY/1000)) > 0.005){
								this.distanceY += Math.abs(this.AccY * this.stopTimeY/1000 * this.stopTimeY/1000);
							}
						}
					}
					if (this.AccY < -1){
						this.stopTimeY = System.currentTimeMillis() - this.startTimeY;
						if (this.stopTimeY > 3){
							if ((Math.abs(this.stopTimeZ * this.stopTimeY/1000)) > 0.005){
								this.distanceY += Math.abs(this.speedY * this.stopTimeY/1000);
								this.maxAccY = this.AccY;
							}
						}
						this.timeFlagY = false;
					}
				}
				/// Distance Z
				if (this.clickZ == 1) this.Message = "Zakljuèan";
				else this.Message = "";
				if (System.currentTimeMillis() - startTimeZ >140) this.keysLocked = false;
				if (!timeFlagZ){
					if (this.AccZ > tempVal+1){
						this.startTimeZ = System.currentTimeMillis();
						this.timeFlagZ = true;
						this.firstRoll = this.AccZ;
						this.speedZ = this.AccZ;
						this.calcIndex = false;
						this.keysLocked = true;
					}
				}else {
					if ((this.AccZ > -tempVal) && (this.AccZ < tempVal)){
						this.stopTimeZ = System.currentTimeMillis() - this.startTimeZ;
						this.startTimeZ = System.currentTimeMillis();
						if (this.stopTimeZ > 3){
							
							if ( (Math.abs(this.AccZ * this.stopTimeZ/1000 * this.stopTimeZ/1000)) > 0.005){
								this.distanceZ += Math.abs(this.AccZ * this.stopTimeZ/1000 * this.stopTimeZ/1000);
							}
						}
					}
					if (this.AccZ < -1){
						this.stopTimeZ = System.currentTimeMillis() - this.startTimeZ;
						if (this.stopTimeZ > 3){
							if ((Math.abs(this.speedZ * this.stopTimeZ/1000)) > 0.005){
								this.distanceZ += Math.abs(this.speedZ * this.stopTimeZ/1000);
								this.maxAccZ = this.AccZ;
								if (this.distanceZ > 0.02) {
									this.clickZ++;
									if (this.clickZ == 2){
									if (System.currentTimeMillis()-this.clickTimeZ > 80){
										int tmp = 0;
										if ((this.mKeys.Shift)&&(this.Keys[this.Row][this.Index] > 96) && (this.Keys[this.Row][this.Index] < 123)) tmp = -32;
									//this.mKeys.putIntChar(this.Keys[this.Row][this.Index] + tmp);
									}
									this.clickZ = 0;
									}
								}
								this.clickTimeZ = System.currentTimeMillis();
							}
						}
						this.keysLocked = false;
						this.timeFlagZ = false;
						this.calcIndex = true;
					}
				}
			}
			if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE){
				this.setRotationX(event.values[0]);
				this.setRotationY(event.values[1]);
				this.setRotationZ(event.values[2]);
			}
			if (event.sensor.getType() == Sensor.TYPE_GRAVITY){
				//this.gravity = event.values;
			}
			if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
				this.geomagnetic = event.values;
				boolean success = SensorManager.getRotationMatrix(this.R, this.I, this.gravity, this.geomagnetic);
				if (success) {
			        float orientation[] = new float[3];
			        SensorManager.getOrientation(R, orientation);
			        this.azimut = orientation[0]; // orientation contains: azimut, pitch and roll
			        this.firstConfigDirection();
			      }
				Matrix.invertM(this.RINV, 0, this.R, 0);
				//this.RINV[1]  = this.RINV[1] - this.firstX;
				//Matrix.multiplyMV(trueacceleration, 0, RINV, 0, linear, 0);
				this.calculateKey();
			}
			
		}
	}
	public void showCurrentCharacter(){
		
		int tmp = 0;
		if ((this.mKeys.Shift)&&(this.Keys[this.Row][this.Index] > 96) && (this.Keys[this.Row][this.Index] < 123)) tmp = -32;
		CharSequence text = "Slovo: " + (char)(this.Keys[this.Row][this.Index]+tmp);
		int duration = Toast.LENGTH_SHORT;
		this.toast = Toast.makeText(context, text, duration);
		this.toast.setGravity(Gravity.TOP|Gravity.LEFT, 0, 0);
		//this.toast.show();
	}
	public String isWorking(){
		String what = null;
		
		return what;
	}
}