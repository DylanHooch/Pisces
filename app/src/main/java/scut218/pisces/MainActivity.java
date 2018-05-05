package scut218.pisces;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import scut218.pisces.fragments.MessageFragment;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId())
                        {
                            case R.id.action_bottom_message:
                                //TODO
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.frame_fragmentholder,MessageFragment.newInstance(MainActivity.this))
                                        .commit();
                                return true;
                            case R.id.action_bottom_find:
                                //TODO
                                Toast.makeText(MainActivity.this,"gg",Toast.LENGTH_LONG);
                                return true;
                            case R.id.action_bottom_me:
                                //TODO
                                Toast.makeText(MainActivity.this,"aa",Toast.LENGTH_LONG);
                                return true;
                        }
                        return false;
                    }
                }
        );

    }
    private void findViews()
    {
        bottomNavigationView=(BottomNavigationView)findViewById(R.id.bottom_navigation);

    }
}
