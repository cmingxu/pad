package com.example.pad.view;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.example.pad.AppContext;
import com.example.pad.AppManager;
import com.example.pad.R;
import com.example.pad.common.*;
import com.example.pad.models.*;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: xucming
 * Date: 3/24/13
 * Time: 7:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class Login extends SherlockActivity {
    public static final int UPDATE_USER_SELECTOR = 1;
    public static final int ARESOURCE_DONE = 2;
    private Button loginBtn;
    private TextView appNameTv;
    private EditText passwordField;
    private Spinner spinner;
    private ArrayAdapter<String> adapter;
    private ProgressDialog progressDialog;
    private HttpHelper httpHelper;
    private Handler handler;
    private AppContext appContext;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        appContext = (AppContext)getApplication();

        AppManager.getAppManager().addActivity(this);
        ActionBar bar = getSupportActionBar();
        bar.setIcon(R.drawable.icon_zhuye);
        bar.setTitle("PMP");
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.top));

        loginBtn = (Button) findViewById(R.id.login_btn);
        passwordField = (EditText) findViewById(R.id.passwordEt);
        appNameTv = (TextView) findViewById(R.id.app_name);
        appNameTv.setText(R.string.app_name);
        loginBtn.setOnClickListener(new LoginInOnClickListener());


        progressDialog = new ProgressDialog(this);


        ArrayList<String> m = new ArrayList<String>();
        for (User u : User.all(null)) {
            m.add(u.login);
        }

        spinner = (Spinner) findViewById(R.id.login_selector);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, m);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        httpHelper = new HttpHelper(appContext);


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.login_menu, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        if (item.getItemId() == R.id.action_downloaddata) {
            reloadData();

        } else if (item.getItemId() == R.id.action_reloaduser) {
            reloadUser();
        } else if (item.getItemId() == R.id.action_settings) {
            Intent i = new Intent();
            i.setClass(Login.this, Preference.class);
            startActivity(i);
            overridePendingTransition(R.animator.push_down_in, R.animator.push_down_out);

        } else if (item.getItemId() == R.id.action_logout) {
            new AlertDialog.Builder(this).setMessage(R.string.logout_confirm).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    appContext.logout();
                    AppManager manager = AppManager.getAppManager();
                    manager.AppExit(Login.this);
                }
            }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).show();
        }
        return true;
    }

    public void reloadUser() {
        if (!Util.instance().isNetworkConnected(Login.this)) {
            UIHelper.showLongToast(Login.this, getString(R.string.network_error));

            return;
        }


        progressDialog.setTitle(R.string.wait_please);
        progressDialog.setMessage(getString(R.string.users_reloading));
        progressDialog.show();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case UPDATE_USER_SELECTOR:

                        ArrayList<String> m = new ArrayList<String>();
                        for (User u : User.all(null)) {
                            m.add(u.login);
                        }

                        adapter = new ArrayAdapter<String>(Login.this, android.R.layout.simple_spinner_item, m);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(adapter);
                        break;

                    default:
                        break;

                }
            }
        };


        httpHelper.with("users", null, new PadJsonHttpResponseHandler(Login.this, progressDialog) {
            @Override
            public void onSuccess(JSONArray s) {
                User.deleteAll();
                try {
                    ArrayList<User> users = User.fromJsonArray(s);
                    for (User u : users) u.save();
                    Message m = new Message();
                    m.what = UPDATE_USER_SELECTOR;
                    handler.sendMessage(m);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
                Toast.makeText(Login.this, R.string.reload_ok, Toast.LENGTH_SHORT).show();

            }

        });


    }

    public void redirect(Class klass) {
        Intent i = new Intent();
        i.setClass(Login.this, klass);
        startActivity(i);
    }

    public void resourceDoneMessage() {
        Message m = new Message();
        m.what = ARESOURCE_DONE;
        handler.sendMessage(m);
    }

    public void reloadData() {
        if (!Util.instance().isNetworkConnected(Login.this)) {
            Toast.makeText(Login.this, R.string.network_error, Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.show();
        progressDialog.setTitle("消息同步");
        progressDialog.setMessage("数据同步中, 请不要关闭程序");
        handler = new Handler() {

            int total_resource_count = 11;

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case ARESOURCE_DONE:
                        Log.d("resource_done", "" + total_resource_count);
                        total_resource_count -= 1;
                        if (total_resource_count == 0) {
                            progressDialog.dismiss();
                            Toast.makeText(Login.this, R.string.data_sync_ok, Toast.LENGTH_SHORT).show();
                        }

                        break;

                    default:
                        break;

                }
            }
        };
        User.deleteAll();
        Danyuan.deleteAll();
        Louceng.deleteAll();
        Loupan.deleteAll();
        Louge.deleteAll();
        Zhuhu.deleteAll();
        Cidian.deleteAll();
        Xunjiandian.deleteAll();
        Xunjianxiangmu.deleteAll();
        Xunjianzhi.deleteAll();
        Xunjiandan.deleteAll();
        Xunjiandanmingxi.deleteAll();
        DanyuanbiaoChaobiao.deleteAll();


        httpHelper.with("danyuanbiaochaobiaos", null, new PadJsonHttpResponseHandler(Login.this, progressDialog) {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                try {
                    ArrayList<DanyuanbiaoChaobiao> danyuanbiaoChaobiaos = DanyuanbiaoChaobiao.fromJsonArray(jsonArray);
                    for (DanyuanbiaoChaobiao d : danyuanbiaoChaobiaos) d.save();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                resourceDoneMessage();
            }
        });


        httpHelper.with("xunjiandians", null, new PadJsonHttpResponseHandler(Login.this, progressDialog) {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                try {
                    ArrayList<Xunjiandian> xunjiandians = Xunjiandian.fromJsonArray(jsonArray);
                    for (Xunjiandian d : xunjiandians) d.save();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                resourceDoneMessage();
            }
        });

        httpHelper.with("xunjianxiangmus", null, new PadJsonHttpResponseHandler(Login.this, progressDialog) {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                try {
                    ArrayList<Xunjianxiangmu> xunjianxiangmus = Xunjianxiangmu.fromJsonArray(jsonArray);
                    for (Xunjianxiangmu d : xunjianxiangmus) d.save();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                resourceDoneMessage();
            }
        });

        httpHelper.with("xunjianzhis", null, new PadJsonHttpResponseHandler(Login.this, progressDialog) {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                try {
                    ArrayList<Xunjianzhi> xunjianzhis = Xunjianzhi.fromJsonArray(jsonArray);
                    for (Xunjianzhi d : xunjianzhis) d.save();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                resourceDoneMessage();
            }
        });

        httpHelper.with("users", null, new PadJsonHttpResponseHandler(Login.this, progressDialog) {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                try {
                    ArrayList<User> users = User.fromJsonArray(jsonArray);
                    for (User u : users) u.save();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                resourceDoneMessage();
            }
        });

        httpHelper.with("danyuans", null, new PadJsonHttpResponseHandler(Login.this, progressDialog) {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                try {
                    ArrayList<Danyuan> danyuans = Danyuan.fromJsonArray(jsonArray);
                    for (Danyuan d : danyuans) d.save();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                resourceDoneMessage();
            }
        });

        httpHelper.with("loupans", null, new PadJsonHttpResponseHandler(Login.this, progressDialog) {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                try {
                    ArrayList<Loupan> loupans = Loupan.fromJsonArray(jsonArray);
                    for (Loupan l : loupans) l.save();
                } catch (JSONException e) {
                    e.printStackTrace();
                };
                resourceDoneMessage();
            }

        });

        httpHelper.with("louges", null, new PadJsonHttpResponseHandler(Login.this, progressDialog) {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                try {
                    ArrayList<Louge> louges = Louge.fromJsonArray(jsonArray);
                    for (Louge l : louges) l.save();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                resourceDoneMessage();
            }
        });

        httpHelper.with("zhuhus", null, new PadJsonHttpResponseHandler(Login.this, progressDialog) {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                try {
                    ArrayList<Zhuhu> zhuhus = Zhuhu.fromJsonArray(jsonArray);

                    for (Zhuhu l : zhuhus) l.save();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                resourceDoneMessage();


            }
        });

        httpHelper.with("loucengs", null, new PadJsonHttpResponseHandler(Login.this, progressDialog) {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                try {
                    ArrayList<Louceng> loucengs = Louceng.fromJsonArray(jsonArray);
                    for (Louceng l : loucengs) l.save();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                resourceDoneMessage();
            }
        });

        httpHelper.with("dicts", null, new PadJsonHttpResponseHandler(Login.this, progressDialog) {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                try {
                    ArrayList<Cidian> cidians = Cidian.fromJsonArray(jsonArray);
                    for (Cidian l : cidians) l.save();

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                resourceDoneMessage();
            }
        });

    }

    public class LoginInOnClickListener implements Button.OnClickListener {
        public void onClick(View view) {
            if (spinner.getSelectedItem() == null) {
                UIHelper.showLongToast(Login.this, getString(R.string.select_a_login));
                return;
            }

            if (StringUtils.isEmpty(spinner.getSelectedItem().toString())) {
                UIHelper.showLongToast(Login.this, getString(R.string.select_a_login));
                return;
            }


            if (Config.passwordRequired() && passwordField.getText().toString() == null) {
                UIHelper.showLongToast(Login.this, getString(R.string.input_password));
                return;
            }
            User u = User.find_by_login_and_password(spinner.getSelectedItem().toString(), passwordField.getText().toString());
            if (u != null) {
                appContext.login(u);
                redirect(Main.class);
                startService(new Intent(Login.this, NoticeService.class));

            } else {
                Toast.makeText(Login.this, R.string.input_error, Toast.LENGTH_SHORT).show();
            }
        }
    }
}