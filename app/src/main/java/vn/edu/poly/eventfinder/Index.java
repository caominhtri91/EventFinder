package vn.edu.poly.eventfinder;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.aakira.expandablelayout.Utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import vn.edu.poly.eventfinder.callbacks.UserDAOCallback;
import vn.edu.poly.eventfinder.daos.GroupsDAO;
import vn.edu.poly.eventfinder.daos.UserDAO;
import vn.edu.poly.eventfinder.entities.User;
import vn.edu.poly.eventfinder.functionmenu.CapnhatThongtin;
import vn.edu.poly.eventfinder.functionmenu.Sukiendathamgia;
import vn.edu.poly.eventfinder.services.LocationUpdater;

import static vn.edu.poly.eventfinder.R.id.drawer;

public class Index extends AppCompatActivity {
    DrawerLayout mDrawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    FloatingActionButton fab;

    Calendar calendar;

    GroupsDAO groupsDAO;
    UserDAO userDAO;

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, Index.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        // Adding Toolbar to Main screen
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        groupsDAO = new GroupsDAO();
        userDAO = new UserDAO();

        // Setting ViewPager for each Tabs
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        mDrawerLayout = (DrawerLayout) findViewById(drawer);
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();

        //RecycialView
        getSupportActionBar().setTitle(Index.class.getSimpleName());

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final List<ItemModel> data = new ArrayList<>();
        data.add(new ItemModel(
                "Nguyễn A",
                "0123456789",
                R.mipmap.ic_launcher,
                R.color.white,
                R.color.gray_light,
                Utils.createInterpolator(Utils.DECELERATE_INTERPOLATOR)));
        recyclerView.setAdapter(new RecyclerViewRecyclerAdapter(data));

        // Adding menu icon to Toolbar
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        supportActionBar.setDisplayHomeAsUpEnabled(true);

        // Set behavior of Navigation drawer
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);

                        if (menuItem.getItemId() == R.id.dsSukien) {
                            Intent i  = new Intent(Index.this,Index.class);
                            startActivity(i);
                            fab.setVisibility(View.VISIBLE);
                        }
                        if (menuItem.getItemId() == R.id.SuienTG) {
                            fab.setVisibility(View.GONE);
                            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.containerView,new Sukiendathamgia()).commit();

                        }
                        if (menuItem.getItemId() == R.id.Capnhat) {
                            fab.setVisibility(View.GONE);
                            FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                            xfragmentTransaction.replace(R.id.containerView,new CapnhatThongtin()).commit();
                        }
                        mDrawerLayout.closeDrawers();

                        return true;

                    }
                });

        // Adding Floating Action Button to bottom right of main view
        calendar = Calendar.getInstance();
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MaterialDialog materialDialog = new MaterialDialog.Builder(Index.this)
                        .title(R.string.titleDialogAddGroup)
                        .customView(R.layout.dialog_add_group, true)
                        .positiveText(R.string.positiveDialogAddGroup)
                        .negativeText(R.string.negativeDialogAddGroup)
                        .build();

                final View customView = materialDialog.getCustomView();
                TextView datePicker = null;
                AutoCompleteTextView groupName = null;
                AutoCompleteTextView groupDes = null;
                if (customView != null) {
                    groupName = (AutoCompleteTextView) customView.findViewById(R.id.groupName);
                    groupDes = (AutoCompleteTextView) customView.findViewById(R.id.groupDescription);
                    datePicker = (TextView) customView.findViewById(R.id.datePicker);

                    final AutoCompleteTextView finalGroupName = groupName;
                    final AutoCompleteTextView finalGroupDes = groupDes;
                    final TextView finalDatePicker = datePicker;

                    datePicker.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                    finalDatePicker.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                                }
                            };
                            Dialog dialog = new DatePickerDialog(Index.this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                            dialog.show();
                        }
                    });

                    View positiveBtn = materialDialog.getActionButton(DialogAction.POSITIVE);

                    positiveBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            long group_id = groupsDAO.increaseGroupCount();

                            String group_name = finalGroupName.getText().toString();
                            String group_des = finalGroupDes.getText().toString();

                            String group_date = finalDatePicker.getText().toString();
                            DateFormat dateFormat = new SimpleDateFormat("d M yyyy");
                            Date date = null;
                            try {
                                date = dateFormat.parse(group_date);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            long going_date = 0;
                            if (date != null) {
                                going_date = date.getTime() / 1000;
                            }

                            String group_byUser = userDAO.getCurrentUserUID();

                            List<User> list = new ArrayList<User>();

                            //groupsDAO.writeGroupCountOnDatabase(group_id);
                            Toast.makeText(Index.this, "Add dialog", Toast.LENGTH_SHORT).show();
                        }
                    });

                    View negativeBtn = materialDialog.getActionButton(DialogAction.NEGATIVE);
                    negativeBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            materialDialog.dismiss();
                        }
                    });
                }

                materialDialog.show();
            }
        });

        Intent i = new Intent(this, LocationUpdater.class);
        startService(i);

        userDAO.getCurrentUserPhoneNumber(new UserDAOCallback() {
            @Override
            public void onSuccess(User user) {
                String result = user.getPhoneNumber();
                if (result.equals("")) {
                    MaterialDialog materialDialog = new MaterialDialog.Builder(Index.this)
                            .title(R.string.phone_number_input)
                            .content(R.string.phone_number_dialog_content)
                            .inputType(InputType.TYPE_CLASS_PHONE)
                            .input(R.string.input_hint_phone, R.string.input_prefill, new MaterialDialog.InputCallback() {
                                @Override
                                public void onInput(MaterialDialog dialog, CharSequence input) {
                                    userDAO.writeUserPhoneNumber(input.toString());
                                }
                            }).build();
                    materialDialog.show();
                }
            }

            @Override
            public void onError(RuntimeException ex) {

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.toolbar) {
            return true;
        } else if (id == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

}
