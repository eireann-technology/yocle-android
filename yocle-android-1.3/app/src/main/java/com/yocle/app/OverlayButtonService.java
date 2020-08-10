package com.yocle.app;

import android.app.Service;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class OverlayButtonService extends Service implements OnTouchListener, OnClickListener {

    private View topLeftView;

    private Button overlayedButton;
    private float offsetY;
    private int originalYPos;
    private WindowManager wm;
    private LinearLayout parent;
    private RelativeLayout outerlayout0;
    private RelativeLayout outerlayoutL;
    private LinearLayout outerlayout;
    private int screenHeight;
    private int overlayHeight=0;
    private int overlayWidth=0;
    WindowManager.LayoutParams topLeftParams;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);


        LayoutInflater li = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        topLeftView= li.inflate(R.layout.overlaybutton, null);

     //   outerlayout0 = (RelativeLayout)topLeftView.findViewById(R.id.outerlayout);
        outerlayout = (LinearLayout)topLeftView.findViewById(R.id.outerlayout);
        parent = (LinearLayout)topLeftView.findViewById(R.id.innerlayout);
        overlayedButton = (Button)topLeftView.findViewById(R.id.overlaybutton);
        overlayedButton.setOnClickListener(this);
        parent.setOnTouchListener(this);
     //   topLeftView.setOnTouchListener(this);

        WindowManager window = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = window.getDefaultDisplay();
        int width = display.getWidth();
        screenHeight = display.getHeight();

/*
        parent = new LinearLayout(this);
        parent.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        parent.setBackgroundColor(0xFF2E2EFE);
        parent.setPadding(5, 5, 5, 5);
        parent.setOrientation(LinearLayout.VERTICAL);
     //   parent.setAlpha(0.5f);
        parent.setOnTouchListener(this);

        TextView tv1 = new TextView(this);
        tv1.setTextAppearance(this, R.style.fontForOverlayTextView);
        tv1.setText(R.string.pasteinstruction);
        tv1.setPadding(5,5,5,5);
        parent.addView(tv1);

        overlayedButton = new Button(this);
        overlayedButton.setText(R.string.goback);
     //   overlayedButton.setOnTouchListener(this);
     //   overlayedButton.setAlpha(0.0f);
     //   overlayedButton.setBackgroundColor(0x55fe4444);
        overlayedButton.setOnClickListener(this);

        parent.addView(overlayedButton);
*/

/*
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.TYPE_SYSTEM_ALERT, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.LEFT | Gravity.BOTTOM;
        params.x = 0;
        params.y = 0;
        params.alpha = 0.8f;
        wm.addView(outerlayout0, params);
*/

//        topLeftView = new View(this);
//        WindowManager.LayoutParams topLeftParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.TYPE_SYSTEM_ALERT, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);
        topLeftParams = new WindowManager.LayoutParams(LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.TYPE_SYSTEM_ALERT, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE| WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, PixelFormat.TRANSLUCENT);
        topLeftParams.width = LayoutParams.MATCH_PARENT;
        topLeftParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        topLeftParams.gravity = Gravity.CENTER | Gravity.BOTTOM;
        topLeftParams.x = 0;
        topLeftParams.y = 0;
        topLeftParams.alpha = 0.8f;
        wm.addView(topLeftView, topLeftParams);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (topLeftView != null) {
            wm.removeView(topLeftView);
        }
    }


    public boolean insideL(int x, int y, int w, int h, int mx, int my) {
        if(mx>=x && mx<=x+w && my >= y && my <= y+h) {
            return true;
        }
        else {
            return false;
        }

    }

    int boxW;
    int boxH;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
//        int location[] = new int[2];
 //       parent.getLocationOnScreen(location);

    //    if(insideL((int)parent.getX(), (int)parent.getY(), parent.getWidth(), parent.getHeight(), (int)event.getRawX(), (int)event.getRawY())) {

            final int Y = (int) event.getRawY();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    int location[] = new int[2];
                    parent.getLocationOnScreen(location);
                    originalYPos = location[1];
                //    originalYPos = (int) parent.getY();
                    offsetY = originalYPos - Y;
                    if (overlayHeight == 0) {
                        overlayWidth = parent.getWidth();
                        overlayHeight = parent.getHeight();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    offsetY = 0;
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    break;
                case MotionEvent.ACTION_MOVE:
/*
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                    params.topMargin = Y + (int) offsetY;
                    params.leftMargin = 0;

                    if (params.topMargin >= 0 && params.topMargin + overlayHeight <= screenHeight) {
                        parent.setLayoutParams(params);
                        //outerlayout.invalidate();
                    }
*/


                    topLeftParams.x = 0;
                    topLeftParams.y = Y + (int)offsetY;
                    topLeftParams.gravity = Gravity.CENTER | Gravity.TOP;
                    wm.updateViewLayout(topLeftView, topLeftParams);
//                    wm.updateViewLayout(topLeftView, lp);

                    break;
            }

            return true;
     //   }
    //    else {
     //       return false;
    //   }


/*
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            y0 = event.getRawY();

            moving = false;


            parent.getLocationOnScreen(location);
            originalYPos = location[1];


        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            y1 = event.getRawY();
            WindowManager.LayoutParams params = (LayoutParams) parent.getLayoutParams();

            parent.getLocationOnScreen(location);
            originalYPos = location[1];
            float diff = y1-y0;
            params.y = originalYPos + (int)diff;

            wm.updateViewLayout(v, params);
            y0 = y1;
            moving = true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (moving) {
                return true;
            }
        }

        return false;
*/
    }


/*
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(v==outerlayout0) return false;


        final int Y = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                originalYPos = (int)parent.getY();
                offsetY = originalYPos-Y;
                if(overlayHeight==0)
                    overlayHeight = parent.getHeight();
                break;
            case MotionEvent.ACTION_UP:
                offsetY = 0;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                params.topMargin =  Y + (int)offsetY;
                params.leftMargin = 0;

                if(params.topMargin>=0 && params.topMargin+overlayHeight<=screenHeight) {
                    parent.setLayoutParams(params);
                    outerlayout.invalidate();
                }
                break;
        }

        return true;


}


*/


    @Override
    public void onClick(View v) {
        CharSequence pasteData="";
        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            pasteData = clipboard.getText();
        } else {
            // this api requires SDK version 11 and above, so suppress warning for now
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
            pasteData = item.getText();

        }
/*
        Toast.makeText(this, pasteData, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
*/
        stopSelf();
    }

}

/*
package com.invest.com.edu.yocle;

import android.app.Service;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.IBinder;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class OverlayButtonService extends Service implements OnTouchListener, OnClickListener {

    private View topLeftView;

    private Button overlayedButton;
    private float offsetX;
    private float offsetY;
    private int originalXPos;
    private int originalYPos;
    private boolean moving;
    private WindowManager wm;
    private LinearLayout parent;
    private int prevY;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

        //floatingView = View.inflate(getBaseContext(),R.layout.floating_layout,null);

        parent = new LinearLayout(this);
        parent.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        parent.setBackgroundColor(0xFFDBA901);
        parent.setPadding(5, 5, 5, 5);
        parent.setOrientation(LinearLayout.HORIZONTAL);
     //   parent.setAlpha(0.5f);
        parent.setOnTouchListener(this);

        TextView tv1 = new TextView(this);
        tv1.setTextAppearance(this, R.style.fontForOverlayTextView);
        tv1.setText(R.string.pasteinstruction);
        tv1.setPadding(5, 5, 5, 5);

        LinearLayout.LayoutParams params0 = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params0.weight = 4.0f;
        params0.gravity = RelativeLayout.ALIGN_PARENT_LEFT | Gravity.CENTER_VERTICAL | Gravity.LEFT;;
        tv1.setLayoutParams(params0);
        parent.addView(tv1);

//        Drawable resImg = this.getResources().getDrawable(R.drawable.roundbutton);

        overlayedButton = new Button(this);
        overlayedButton.setText(R.string.goback);

        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params1.weight = 1.0f;
//        params1.rightMargin = 10;
        params1.gravity = RelativeLayout.ALIGN_PARENT_RIGHT | Gravity.CENTER_VERTICAL | Gravity.RIGHT;;
        overlayedButton.setLayoutParams(params1);
  //      overlayedButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, 22);
     //   overlayedButton.setBackground(resImg);
     //   overlayedButton.setOnTouchListener(this);
     //   overlayedButton.setAlpha(0.0f);
//        overlayedButton.setBackgroundColor(0x55fe4444);
        overlayedButton.setOnClickListener(this);

        parent.addView(overlayedButton);

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.TYPE_SYSTEM_ALERT, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.LEFT | Gravity.BOTTOM;
        params.x = 0;
        params.y = 0;
        params.alpha = 0.8f;
        wm.addView(parent, params);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (parent != null) {
            wm.removeView(parent);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float x = event.getRawX();
            float y = event.getRawY();

            moving = false;

            int[] location = new int[2];
            parent.getLocationOnScreen(location);

            originalXPos = location[0];
            originalYPos = location[1];

            offsetX = originalXPos - x;
            offsetY = originalYPos - y;

        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {

            float x = event.getRawX();
            float y = event.getRawY();

            WindowManager.LayoutParams params = (LayoutParams) parent.getLayoutParams();

            int newX = (int) (offsetX + x);
            int newY = (int) (offsetY - y);

            if (Math.abs(newX - originalXPos) < 1 && Math.abs(newY - originalYPos) < 1 && !moving) {
                return false;
            }

         //   params.x = newX - (topLeftLocationOnScreen[0]);
         //   params.y = newY - (topLeftLocationOnScreen[1]);
            params.y = newY;

            wm.updateViewLayout(parent, params);
            moving = true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (moving) {
                return true;
            }
        }

        return false;    }

    @Override
    public void onClick(View v) {
        CharSequence pasteData="";
        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            pasteData = clipboard.getText();
        } else {
            // this api requires SDK version 11 and above, so suppress warning for now
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
            pasteData = item.getText();

        }

        Toast.makeText(this, pasteData, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);

        stopSelf();
    }

}
*/