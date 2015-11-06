package jp.ac.it_college.std.jsontest;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_filter_fragment:
                changeFragment(new CategoryFilterFragment());
                break;
            case R.id.menu_add_fragment:
                changeFragment(new AddCategoryFragment());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void changeFragment(Fragment fragment) {
        getFragmentManager().beginTransaction()
                .replace(R.id.container_fragment, fragment)
                .commit();
    }
}
