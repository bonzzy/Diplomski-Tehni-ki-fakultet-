package com.example.tipkovnica;

import android.app.Activity;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.text.Editable;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

class MojaTipkovnica {
	public int Code;
	public final static int CodeDelete   = -5; 
	public final static int CodeInput   = -100; 
	public final static int CodeCalibrate   = -101; 
	public final static int CodeSpace    = -50;
	public final static int CodeShift    = -55;
    public final static int CodeCancel   = -3; 
    public final static int CodePrev     = 55000;
    public final static int CodeAllLeft  = 55001;
    public final static int CodeLeft     = 55002;
    public final static int CodeRight    = 55003;
    public final static int CodeAllRight = 55004;
    public final static int CodeNext     = 55005;
    public final static int CodeClear    = 55006;
    private KeyboardView mKeyboardView;
    private Activity     mHostActivity;
    public boolean Shift = false;
    private OnKeyboardActionListener mOnKeyboardActionListener = new OnKeyboardActionListener() {

        @Override public void onKey(int primaryCode, int[] keyCodes) {
  
            View focusCurrent = mHostActivity.getWindow().getCurrentFocus();
            if( focusCurrent==null || focusCurrent.getClass()!=EditText.class ) return;
            EditText edittext = (EditText) focusCurrent;
            Editable editable = edittext.getText();
            int start = edittext.getSelectionStart();
            if( primaryCode==CodeCancel ) {
                hideCustomKeyboard();
            } else if( primaryCode==CodeCalibrate ) {
            	//putIntChar((char) Code);
            }else if( primaryCode==CodeInput ) {
            	putIntChar((char) Code);
            }else if( primaryCode==CodeShift ) {
            	if (Shift)Shift=false;
            	else Shift = true;
            	mKeyboardView.setShifted(Shift);
            }else if( primaryCode==CodeSpace ) {
                 editable.append(" ");
            }else if( primaryCode==CodeDelete ) {
                if( editable!=null && start>0 ) editable.delete(start - 1, start);
            } else if( primaryCode==CodeClear ) {
                if( editable!=null ) editable.clear();
            } else if( primaryCode==CodeLeft ) {
                if( start>0 ) edittext.setSelection(start - 1);
            } else if( primaryCode==CodeRight ) {
                if (start < edittext.length()) edittext.setSelection(start + 1);
            } else if( primaryCode==CodeAllLeft ) {
                edittext.setSelection(0);
            } else if( primaryCode==CodeAllRight ) {
                edittext.setSelection(edittext.length());
            } else if( primaryCode==CodePrev ) {
                View focusNew= edittext.focusSearch(View.FOCUS_BACKWARD);
                if( focusNew!=null ) focusNew.requestFocus();
            } else if( primaryCode==CodeNext ) {
                View focusNew= edittext.focusSearch(View.FOCUS_FORWARD);
                if( focusNew!=null ) focusNew.requestFocus();
            } else { // insert character
            	putChar((char)primaryCode);
               // editable.insert(start, Character.toString((char) primaryCode));
            }
        }

        @Override public void onPress(int arg0) {
        }

        @Override public void onRelease(int primaryCode) {
        }

        @Override public void onText(CharSequence text) {
        }

        @Override public void swipeDown() {
        	hideCustomKeyboard();
        }

        @Override public void swipeLeft() {
        }

        @Override public void swipeRight() {
   
        }

        @Override public void swipeUp() {
        }
    };

    public void putChar(Character c){
    	if (isCustomKeyboardVisible()){
    	View focusCurrent = mHostActivity.getWindow().getCurrentFocus();
        if( focusCurrent==null || focusCurrent.getClass()!=EditText.class ) return;
        EditText edittext = (EditText) focusCurrent;
        Editable editable = edittext.getText();
        int start = edittext.getSelectionStart();
    	editable.insert(start, Character.toString((char) c));
    	}
    }
    public void putIntChar(int c){
    	View focusCurrent = mHostActivity.getWindow().getCurrentFocus();
        if( focusCurrent==null || focusCurrent.getClass()!=EditText.class ) return;
        EditText edittext = (EditText) focusCurrent;
        Editable editable = edittext.getText();
        int start = edittext.getSelectionStart();
        if( c==CodeCancel ) {
            hideCustomKeyboard();
        } else if( c==CodeShift ) {
        	if (Shift)Shift=false;
        	else Shift = true;
        	mKeyboardView.setShifted(Shift);
        }else if( c==CodeSpace ) {
            if( editable!=null && start>0 ) editable.append(" ");
        }else if( c==CodeDelete ) {
            if( editable!=null && start>0 ) editable.delete(start - 1, start);
        } else if( c==CodeClear ) {
            if( editable!=null ) editable.clear();
        } else if( c==CodeLeft ) {
            if( start>0 ) edittext.setSelection(start - 1);
        } else if( c==CodeRight ) {
            if (start < edittext.length()) edittext.setSelection(start + 1);
        } else if( c==CodeAllLeft ) {
            edittext.setSelection(0);
        } else if( c==CodeAllRight ) {
            edittext.setSelection(edittext.length());
        } else if( c==CodePrev ) {
            View focusNew= edittext.focusSearch(View.FOCUS_BACKWARD);
            if( focusNew!=null ) focusNew.requestFocus();
        } else if( c==CodeNext ) {
            View focusNew= edittext.focusSearch(View.FOCUS_FORWARD);
            if( focusNew!=null ) focusNew.requestFocus();
        } else { // insert character
        	putChar((char)c);
           // editable.insert(start, Character.toString((char) primaryCode));
        }
    }
    public MojaTipkovnica(Activity host, int viewid, int layoutid) {
        mHostActivity= host;
        mKeyboardView= (KeyboardView)mHostActivity.findViewById(viewid);
        mKeyboardView.setKeyboard(new Keyboard(mHostActivity, layoutid));
        mKeyboardView.setPreviewEnabled(false); // NOTE Do not show the preview balloons
        mKeyboardView.setOnKeyboardActionListener(mOnKeyboardActionListener);
        // Hide the standard keyboard initially
        mHostActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public boolean isCustomKeyboardVisible() {
        return mKeyboardView.getVisibility() == View.VISIBLE;
    }

    public void showCustomKeyboard( View v ) {
        mKeyboardView.setVisibility(View.VISIBLE);
        mKeyboardView.setEnabled(true);
        TextView edittext = (TextView) mHostActivity.findViewById(R.id.gyro);
        edittext.setVisibility(View.VISIBLE);
        if( v!=null ) ((InputMethodManager)mHostActivity.getSystemService(Activity.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public void hideCustomKeyboard() {
        mKeyboardView.setVisibility(View.GONE);
        mKeyboardView.setEnabled(false);
        TextView edittext = (TextView) mHostActivity.findViewById(R.id.gyro);
        edittext.setVisibility(View.INVISIBLE);
    }

    public void registerEditText(int resid) {
      
        EditText edittext= (EditText)mHostActivity.findViewById(resid);
       
        edittext.setOnFocusChangeListener(new OnFocusChangeListener() {
          
            @Override public void onFocusChange(View v, boolean hasFocus) {
                if( hasFocus ) showCustomKeyboard(v); else hideCustomKeyboard();
            }
        });
        edittext.setOnClickListener(new OnClickListener() {
             @Override public void onClick(View v) {
                showCustomKeyboard(v);
            }
        });
        edittext.setOnTouchListener(new OnTouchListener() {
            @Override public boolean onTouch(View v, MotionEvent event) {
                EditText edittext = (EditText) v;
                int inType = edittext.getInputType();       // Backup the input type
                edittext.setInputType(InputType.TYPE_NULL); // Disable standard keyboard
                edittext.onTouchEvent(event);               // Call native handler
                edittext.setInputType(inType);              // Restore input type
                return true; // Consume touch event
            }
        });
        edittext.setInputType(edittext.getInputType() | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
    }

}

