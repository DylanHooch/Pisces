package scut218.pisces.view;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import scut218.pisces.R;
import scut218.pisces.adapters.MessageAdapter;
import scut218.pisces.beans.Moment;
import scut218.pisces.loader.PreLoadTask;
import scut218.pisces.view.fragments.MessageFragment;
import scut218.pisces.view.fragments.MomentFragment;

public class MainActivity extends AppCompatActivity implements MessageFragment.OnFragmentInteractionListener,
        MomentFragment.OnMomentFragmentInteractionListener{
    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;
    public PreLoadTask preLoadTask=null;
    private String uid;//当前用户的id
    private MessageFragment messageFragment=null;
    private MomentFragment momentFragment=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uid=getIntent().getStringExtra("id");
        setContentView(R.layout.activity_main);
        findViews();
        setSupportActionBar(toolbar);
        if(messageFragment==null){
            messageFragment=MessageFragment.newInstance(MainActivity.this);
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_fragmentholder,messageFragment)
                .commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId())
                        {
                            case R.id.action_bottom_message:
                                if(messageFragment==null){
                                    messageFragment=MessageFragment.newInstance(MainActivity.this);
                                }
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.frame_fragmentholder,messageFragment)
                                        .commit();
                                return true;
                            case R.id.action_bottom_moment:
                                if(momentFragment==null){
                                    momentFragment=MomentFragment.newInstance(MainActivity.this,uid);
                                }
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.frame_fragmentholder, MomentFragment.newInstance(MainActivity.this,uid))
                                        .commit();
                                return true;
                            case R.id.action_bottom_find:
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.frame_fragmentholder, MomentFragment.newInstance(MainActivity.this,uid))
                                        .commit();
                                return true;
                            case R.id.action_bottom_me:
                                Toast.makeText(MainActivity.this,"aa",Toast.LENGTH_SHORT).show();
                                return true;
                        }
                        return false;
                    }
                }
        );

        preLoadTask=new PreLoadTask();
        preLoadTask.execute();

    }
    private void findViews()
    {
        bottomNavigationView=(BottomNavigationView)findViewById(R.id.bottom_navigation);
        toolbar=(Toolbar)findViewById(R.id.toolbar);

    }
    public void onFragmentInteraction(Uri uri){

    }
    public void onMomentFragmentInteraction(Uri uri){

    }
}
