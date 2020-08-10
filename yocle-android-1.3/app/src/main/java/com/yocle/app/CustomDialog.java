package com.yocle.app;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yocle.json.GenToken;

public class CustomDialog {

    public static void RedeemCancelDialog(final Context context, final int iid, final String cookie) {
/*
        final Dialog dialog = new Dialog(context, R.style.cust_dialog);
        dialog.setContentView(R.layout.twobuttonscustomdialog);
        dialog.setTitle(context.getResources().getString(R.string.paidservice));

        TextView tv = (TextView) dialog.findViewById(R.id.desc);
        tv.setText(context.getResources().getString(R.string.redeemmessage));

        Button button1 = (Button) dialog.findViewById(R.id.button1);
        button1.setText(context.getResources().getString(R.string.redeem));

        // if button is clicked, close the custom dialog
            button1.setOnClickListener(new Button.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                              // ((MainActivity) context).showPage("/scheme/scheme.html");

                                               JsonHttpUtil json = new JsonHttpUtil(new FragmentJsonCallback() {
                                                   @Override
                                                   public void onTaskDone(String rv) {
                                                       Gson gson = new Gson();
                                                       Redeem t = gson.fromJson(rv, Redeem.class);
                                                       if(t.result==1) {
                                                           ((MainActivity)context).showToast((String)((MainActivity)context).getResources().getText(R.string.redeemsuccess));
                                                       }
                                                       else {
                                                           ((MainActivity)context).showToast((String)((MainActivity)context).getResources().getText(R.string.redeemfailure));
                                                       }

                                                   }
                                               },1);
                                               json.execute(Config.HTTP_SERVER_ROOT + "/invest/servlet/shareredeem", cookie, "items=" + iid);


                                               dialog.dismiss();
                                           }
                                       }
            );

        Button button2 = (Button) dialog.findViewById(R.id.button2);
        button2.setText(context.getResources().getString(R.string.cancel));
        // if button is clicked, close the custom dialog
        button2.setOnClickListener(new Button.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           dialog.dismiss();
                                       }
                                   }
        );


        dialog.show();
*/
    }

    public static void LoginCancelDialog(final Context context) {

        final Dialog dialog = new Dialog(context, R.style.cust_dialog);
        dialog.setContentView(R.layout.twobuttonscustomdialog);
        dialog.setTitle(context.getResources().getString(R.string.paidservice));

        TextView tv = (TextView) dialog.findViewById(R.id.desc);
        tv.setText(context.getResources().getString(R.string.loginmessage));

        Button button1 = (Button) dialog.findViewById(R.id.button1);
        button1.setText(context.getResources().getString(R.string.login));
        // if button is clicked, close the custom dialog
        button1.setOnClickListener(new Button.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           ((MainActivity) context).showPage("/loginform.html");
                                           dialog.dismiss();
                                       }
                                   }
        );

        Button button2 = (Button) dialog.findViewById(R.id.button2);
        button2.setText(context.getResources().getString(R.string.cancel));
        // if button is clicked, close the custom dialog
        button2.setOnClickListener(new Button.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           dialog.dismiss();
                                       }
                                   }
        );


        dialog.show();
    }

    public static void LoginCancelDialog_share(final Context context) {

        final Dialog dialog = new Dialog(context, R.style.cust_dialog);
        dialog.setContentView(R.layout.twobuttonscustomdialog);
        dialog.setTitle(context.getResources().getString(R.string.pleaseloginfirst));

        TextView tv = (TextView) dialog.findViewById(R.id.desc);
        tv.setText(context.getResources().getString(R.string.pleaseloginbeforeshare));

        Button button1 = (Button) dialog.findViewById(R.id.button1);
        button1.setText(context.getResources().getString(R.string.login));
        // if button is clicked, close the custom dialog
        button1.setOnClickListener(new Button.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           ((MainActivity) context).showPage("/loginform.html");
                                           dialog.dismiss();
                                       }
                                   }
        );

        Button button2 = (Button) dialog.findViewById(R.id.button2);
        button2.setText(context.getResources().getString(R.string.cancel));
        // if button is clicked, close the custom dialog
        button2.setOnClickListener(new Button.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           dialog.dismiss();
                                       }
                                   }
        );


        dialog.show();
    }



    public static void ShareCancelDialog(final Context context, final int iid, final String thumbnail, final String cookie) {

        final Dialog dialog = new Dialog(context, R.style.cust_dialog);
        dialog.setContentView(R.layout.sharecanceldialog);
        dialog.setTitle(context.getResources().getString(R.string.paidservice));

        TextView tv = (TextView) dialog.findViewById(R.id.desc);
        tv.setText(context.getResources().getString(R.string.sharemessage));

        Button button1 = (Button) dialog.findViewById(R.id.button1);
        button1.setText(context.getResources().getString(R.string.understandshare));
        // if button is clicked, close the custom dialog
        button1.setOnClickListener(new Button.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           ((MainActivity) context).showPage("/scheme/scheme.html");
                                           dialog.dismiss();
                                       }
                                   }
        );

        Button button2 = (Button) dialog.findViewById(R.id.button2);
        button2.setText(context.getResources().getString(R.string.cancel));
        // if button is clicked, close the custom dialog
        button2.setOnClickListener(new Button.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           dialog.dismiss();
                                       }
                                   }
        );


        Button facebook = (Button) dialog.findViewById(R.id.share_fb);
        facebook.setText(context.getResources().getString(R.string.buttonsharefb));
        // if button is clicked, close the custom dialog
        facebook.setOnClickListener(new Button.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            toshare(context, iid, "1", thumbnail, cookie);
                                            dialog.dismiss();
                                        }
                                    }
        );

        Button whatapps = (Button) dialog.findViewById(R.id.share_whatapps);
        whatapps.setText(context.getResources().getString(R.string.buttonsharewhatapps));
        whatapps.setOnClickListener(new Button.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            toshare(context, iid, "2", thumbnail, cookie);
                                            dialog.dismiss();
                                        }
                                    }
        );


        Button wechat = (Button) dialog.findViewById(R.id.share_wechat);
        wechat.setText(context.getResources().getString(R.string.buttonsharewechat));
        wechat.setOnClickListener(new Button.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          toshare(context, iid, "3", thumbnail, cookie);
                                          dialog.dismiss();
                                      }
                                  }
        );

        dialog.show();
    }


    private static void toshare(final Context context, final int iid, final String socialnetwork, final String imguri, final String cookie) {

        JsonHttpUtil json = new JsonHttpUtil(new FragmentJsonCallback() {
            @Override
            public void onTaskDone(String rv) {
                Gson gson = new Gson();
                final GenToken t = gson.fromJson(rv, GenToken.class);

                if(!t.error.equals("")) {
                    ((MainActivity)context).showToast(context.getResources().getString(R.string.shareerror));
                }
                else {


                 //       Handler mUiHandler = new Handler();
                 //       mUiHandler.post(new Runnable() {
                 //          @Override
                 //          public void run() {
                //    ((MainActivity) context).startShareEditActivity(context, t.share_message, socialnetwork, imguri);
                           }
                 //      });
                Intent intent = new Intent(context, EditTextActivity.class);
//					intent.putExtra("defaulttext", getResources().getString(R.string.defaulttext));
                intent.putExtra("defaulttext", t.share_message);
                intent.putExtra("socialnetwork", String.valueOf(socialnetwork));
                intent.putExtra("imguri", imguri);
                ((MainActivity)context).startActivityForResult(intent, WebViewFragment.REQ_SOCIAL_NETWORK_SHARE);

            }
        },1);

        //json.execute(Config.HTTP_SERVER_ROOT + "/invest/servlet/sharegentoken", cookie, "iid=" + iid + "&media=" + Config.socialnetworkmediaids[Integer.valueOf(socialnetwork).intValue()]);
    }

    public static void BuyMembershipDialog(final Context context, int iid, final int m1, final int m3, final int m6, final int m12) {
        final Dialog dialog = new Dialog(context, R.style.cust_dialog);
        dialog.setContentView(R.layout.buymembership);
        dialog.setTitle(context.getResources().getString(R.string.paidservice));

        TextView tv = (TextView) dialog.findViewById(R.id.desc);
        tv.setText(context.getResources().getString(R.string.buymembershipmessage));

        Button bm1 = (Button) dialog.findViewById(R.id.m1);
        bm1.setText("HK$ "+m1+" - "+context.getResources().getString(R.string.onemonth));
        // if button is clicked, close the custom dialog
        bm1.setOnClickListener(new Button.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       // ((MainActivity) context).showPage("/scheme/scheme.html");
                                       ((MainActivity) context).showPaymentPage("/paydollarform.html?iid=m1&price=" + m1);
                                       dialog.dismiss();
                                   }
                               }
        );

        Button bm3 = (Button) dialog.findViewById(R.id.m3);
        bm3.setText("HK$"+m3+" - "+context.getResources().getString(R.string.threemonth));
        // if button is clicked, close the custom dialog
        bm3.setOnClickListener(new Button.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       ((MainActivity) context).showPaymentPage("/paydollarform.html?iid=m3&price="+m3);
                                       dialog.dismiss();
                                   }
                               }
        );

        Button bm6 = (Button) dialog.findViewById(R.id.m6);
        bm6.setText("HK$"+m6+" - "+context.getResources().getString(R.string.sixmonth));
        // if button is clicked, close the custom dialog
        bm6.setOnClickListener(new Button.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       ((MainActivity) context).showPaymentPage("/paydollarform.html?iid=m6&price="+m6);
                                       dialog.dismiss();
                                   }
                               }
        );

        Button bm12 = (Button) dialog.findViewById(R.id.m12);
        bm12.setText("HK$" + m12 + " - " + context.getResources().getString(R.string.oneyear));
        // if button is clicked, close the custom dialog
        bm12.setOnClickListener(new Button.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        ((MainActivity) context).showPaymentPage("/paydollarform.html?iid=m12&price="+m12);
                                        dialog.dismiss();
                                    }
                                }
        );

        Button cancel = (Button) dialog.findViewById(R.id.cancel);
        cancel.setText(context.getResources().getString(R.string.cancel));
        // if button is clicked, close the custom dialog
        cancel.setOnClickListener(new Button.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          dialog.dismiss();
                                      }
                                  }
        );

        dialog.show();
    }


    public static void BuyMembershipReportDialog(final Context context, String title, final int iid, final int price, final int m1, final int m3, final int m6, final int m12) {
        final Dialog dialog = new Dialog(context, R.style.cust_dialog);
        dialog.setContentView(R.layout.buymemberreport);
        dialog.setTitle(context.getResources().getString(R.string.paidservice));

        TextView tv = (TextView) dialog.findViewById(R.id.desc);
        tv.setText(context.getResources().getString(R.string.buymembershipreportmessage));

        Button bm1 = (Button) dialog.findViewById(R.id.m1);
        bm1.setText("HK$ "+m1+" - "+context.getResources().getString(R.string.onemonth));
        // if button is clicked, close the custom dialog
        bm1.setOnClickListener(new Button.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       ((MainActivity) context).showPaymentPage("/paydollarform.html?iid=m1&price="+m1);
                                       dialog.dismiss();
                                   }
                               }
        );

        Button bm3 = (Button) dialog.findViewById(R.id.m3);
        bm3.setText("HK$"+m3+" - "+context.getResources().getString(R.string.threemonth));
        // if button is clicked, close the custom dialog
        bm3.setOnClickListener(new Button.OnClickListener() {
                                  @Override
                                  public void onClick(View v) {
                                      ((MainActivity) context).showPaymentPage("/paydollarform.html?iid=m3&price="+m3);
                                      dialog.dismiss();
                                  }
                              }
        );

        Button bm6 = (Button) dialog.findViewById(R.id.m6);
        bm6.setText("HK$"+m6+" - "+context.getResources().getString(R.string.sixmonth));
        // if button is clicked, close the custom dialog
        bm6.setOnClickListener(new Button.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       ((MainActivity) context).showPaymentPage("/paydollarform.html?iid=m6&price="+m6);
                                       dialog.dismiss();
                                   }
                               }
        );

        Button bm12 = (Button) dialog.findViewById(R.id.m12);
        bm12.setText("HK$" + m12 + " - " + context.getResources().getString(R.string.oneyear));
        // if button is clicked, close the custom dialog
        bm12.setOnClickListener(new Button.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       ((MainActivity) context).showPaymentPage("/paydollarform.html?iid=m12&price="+m12);
                                       dialog.dismiss();
                                   }
                               }
        );


        Button report = (Button) dialog.findViewById(R.id.report);
        title = title.substring(0, title.indexOf("."));
        report.setText("HK$"+price+" - "+" \""+title+"\" "+context.getResources().getString(R.string.researchreport));
        // if button is clicked, close the custom dialog
        report.setOnClickListener(new Button.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           ((MainActivity) context).showPaymentPage("/paydollarform.html?iid="+iid+"&price="+price);
                                           dialog.dismiss();
                                       }
                                   }
        );

        Button cancel = (Button) dialog.findViewById(R.id.cancel);
        cancel.setText(context.getResources().getString(R.string.cancel));
        // if button is clicked, close the custom dialog
        cancel.setOnClickListener(new Button.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          dialog.dismiss();
                                      }
                                  }
        );

        dialog.show();
    }
}