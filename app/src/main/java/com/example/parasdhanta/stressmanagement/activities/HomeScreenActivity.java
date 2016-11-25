package com.example.parasdhanta.stressmanagement.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.parasdhanta.stressmanagement.R;
import com.example.parasdhanta.stressmanagement.adapters.HomeScrDataAdapter;
import com.example.parasdhanta.stressmanagement.adapters.RecyclerItemClickListener;
import com.example.parasdhanta.stressmanagement.constants.Constants;
import com.example.parasdhanta.stressmanagement.interfaces.NavigationListeners;
import com.example.parasdhanta.stressmanagement.managers.BaseActivity;
import com.example.parasdhanta.stressmanagement.pojos.eventbus.FragmentArguements;
import com.example.parasdhanta.stressmanagement.pojos.LoginUser;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Paras Dhanta on 10/24/2016.
 */

public class HomeScreenActivity extends BaseActivity implements NavigationListeners {
    //initialize toolbar
    @BindView(R.id.main_toolbar)
    Toolbar toolbar;

    LoginUser loginUser;

    //initialize navigation menu
    @BindView(R.id.navigation_view)
    NavigationView navigationView;

    //initialize drawerlayout
    @BindView(R.id.drawer)
    DrawerLayout drawerLayout;

    //initialize recyclerview
    @Nullable
    @BindView(R.id.home_recycler_view)
    RecyclerView recyclerView;

    //initialize drawer-frame
    @Nullable
    @BindView(R.id.drawer_frame)
    CoordinatorLayout drawerFrame;


    //list of all the conditions stress, depression,OCD,personality disorder
    List<String> conditionsList;

    @BindView(R.id.floating_menu_button) FloatingActionMenu menuFab;
    @BindView(R.id.stress_ques) FloatingActionButton floatingActionButton1;
    @BindView(R.id.depression_ques) FloatingActionButton floatingActionButton2;
    @BindView(R.id.anxiety_ques) FloatingActionButton floatingActionButton3;
    @BindView(R.id.po_ques) FloatingActionButton floatingActionButton4;
    @BindView(R.id.ocd_ques) FloatingActionButton floatingActionButton5;
    @BindView(R.id.health_tips) FloatingActionButton floatingActionButton6;


    @OnClick({R.id.stress_ques,R.id.depression_ques,R.id.anxiety_ques,
    R.id.po_ques,R.id.ocd_ques,R.id.health_tips})
    public void fabButtonListener(View view) {
          switch (view.getId())
          {
              case R.id.stress_ques:
                  break;

              case R.id.depression_ques:
                  break;

              case R.id.anxiety_ques:
                  break;

              case R.id.po_ques:
                  break;

              case R.id.ocd_ques:
                  break;

              case R.id.health_tips:
                  break;

          }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.topbar_menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        navigationItemSelected();
        handleActionBarToggle();

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.home_screen;
    }

    @Override
    protected void setAppToolBar() {
        setSupportActionBar(toolbar);
    }

    public void init() {
        setContentView(getLayoutResource());
        initButterKnife();
        setAppToolBar();

        /*recycler view logic for home screen mail list*/
        conditionRecyclerListLogic();
        mainAppController.addActivity(this);
        menuFab.setClosedOnTouchOutside(true);

        menuFab.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener()
        {
            @Override public void onMenuToggle(boolean opened) {
                if (opened) {
                    recyclerView.setAlpha(0.1f);
                }
                else{
                    recyclerView.setAlpha(1.0f);
                }
            }

        });

        loginUser = getIntent().getParcelableExtra("user_object");

        navigationHeader();
    }

    // navigation item selected in the drawer
    @Override
    public void navigationItemSelected() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    //Action bar toggle logic
    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        } else {
            getSupportFragmentManager().popBackStack();
            backFrmFragment();
        }
    }
    @Override
    public void handleActionBarToggle() {


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer,
                R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    @Override
    public Fragment loadFragmentInstance(String title,String content) {
        return super.loadFragmentInstance(title,content);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void conditionRecyclerListLogic() {
        //initialize conditions arraylist
        conditionsList = new ArrayList<String>();
        for (String temp : Constants.CONDITIONS_LIST) {
            conditionsList.add(temp);
        }

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        RecyclerView.Adapter mAdapter = new HomeScrDataAdapter(conditionsList);

        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                        String item = ((TextView) view.findViewById(R.id.tv_home_list)).getText().toString();

                        startFragment(item,getResources().getString(R.string.temp));
                        EventBus.getDefault().postSticky(new FragmentArguements(item,getResources().getString(R.string.temp)));

                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
    }

    public void startFragment(String title,String content)
    {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.root_layout,loadFragmentInstance(title,content))
                .addToBackStack("description_fragment")
                .commit();
        recyclerView.setVisibility(View.GONE);
        menuFab.setVisibility(View.GONE);
    }

    public void backFrmFragment(){
        toolbar.setTitle(getAppName(getApplicationContext()));
        recyclerView.setVisibility(View.VISIBLE);
        menuFab.setVisibility(View.VISIBLE);
        handleActionBarToggle();
    }

    public void navigationHeader(){
        View view = navigationView.getHeaderView(0);
        CircleImageView imgView  = (CircleImageView) view.findViewById(R.id.profile_image);
        TextView userName  = (TextView) view.findViewById(R.id.username);
        TextView emailId  = (TextView) view.findViewById(R.id.email);
        if(loginUser!=null) {
            if(!TextUtils.isEmpty(loginUser.userName)&&!TextUtils.isEmpty(loginUser.imageUrl))
            userName.setText(loginUser.userName);
            emailId.setText(loginUser.emailId);
            imageLogic(imgView, this, loginUser.imageUrl);
        }
        else{
            emailId.setText(loginUser.emailId);
        }
    }
}
