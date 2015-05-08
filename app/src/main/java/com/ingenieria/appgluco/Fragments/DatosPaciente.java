package com.ingenieria.appgluco.Fragments;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;

import com.ingenieria.appgluco.R;


public class DatosPaciente extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {



    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private static final int FRAGMENT_MAIN_POSITION = 0;
    private static final int FRAGMENT_1_POSITION = 1;
    private static final int FRAGMENT_2_POSITION = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_paciente);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (position){
            case FRAGMENT_MAIN_POSITION:
               fragmentManager.beginTransaction()
                        .replace(R.id.container, FragmentMain.newInstance(position + 1))
                        .commit();
                break;
            case FRAGMENT_1_POSITION:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, FragmentMedicion.newInstance(position + 1))
                        .commit();
                break;
            case FRAGMENT_2_POSITION:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, FragmentEstadistica.newInstance(position + 1))
                        .commit();
                break;
        }

    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.datos_paciente, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_datos_paciente, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((DatosPaciente) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    public static class FragmentMain extends Fragment {

        private static final String ARG_SECTION_NUMBER_1 = "section number 1";
        public static FragmentMain newInstance(int i) {
            FragmentMain fragment = new FragmentMain();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER_1, i);
            fragment.setArguments(args);
            return fragment;
        }
        public FragmentMain(){

        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View root = inflater.inflate(R.layout.fragment_datos_paciente, container, false);
            return root;
        }
        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((DatosPaciente)activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER_1));
        }
    }

   public static class FragmentMedicion extends Fragment {

        private static final String ARG_SECTION_NUMBER_2 = "Section number 2";

        public static Fragment newInstance(int i) {
            FragmentMedicion fragment = new FragmentMedicion();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER_2, i);
            fragment.setArguments(args);
            return fragment;
        }
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View root = inflater.inflate(R.layout.medicion,container, false);
            return root;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((DatosPaciente)activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER_2));
        }
    }

    public static class FragmentEstadistica extends Fragment {

        private static final String ARG_SECTION_NUMBER_3 = "Section number 3";

        public static Fragment newInstance(int i) {
            FragmentEstadistica fragment = new FragmentEstadistica();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER_3, i);
            fragment.setArguments(args);
            return fragment;
        }
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View root = inflater.inflate(R.layout.estadistica,container, false);
            return root;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((DatosPaciente)activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER_3));
        }
    }
}
