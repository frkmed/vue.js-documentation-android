package company.yak.vuejsdocumentation;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static String fileName = "index.html";
    private AdView mAdView;

    Context mContext;
    Toolbar mToolbar;
    DrawerLayout mDrawer;
    NavigationView mNavigationView;
    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = getApplicationContext();
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mWebView = (WebView) findViewById(R.id.web_view_documentation);

        setSupportActionBar(mToolbar);
        mNavigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d(TAG, "shouldOverrideUrlLoading: " + url);

                if (url.startsWith("http")) {
                    CustomTabsIntent tabsIntent = new CustomTabsIntent.Builder().build();
                    tabsIntent.launchUrl(MainActivity.this, Uri.parse(url));
                } else {
                    String fileName = url.replace("file:///android_asset/", "");
                    onDocumentationItemSelected(fileName);
                }

                return true;
            }
        });

        getSupportActionBar().setTitle("The Progressive");
        getSupportActionBar().setSubtitle("JavaScript Framework");
        onDocumentationItemSelected(this.fileName);

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.essentials_installation:
                getSupportActionBar().setTitle("Installation");
                getSupportActionBar().setSubtitle("Essentials");
                onDocumentationItemSelected("v2/guide/installation.html");
                break;
            case R.id.essentials_introduction:
                getSupportActionBar().setTitle("Introduction");
                getSupportActionBar().setSubtitle("Essentials");
                onDocumentationItemSelected("v2/guide/index.html");
                break;
            case R.id.essentials_the_vue_instance:
                getSupportActionBar().setTitle("The Vue Instance");
                getSupportActionBar().setSubtitle("Essentials");
                onDocumentationItemSelected("v2/guide/instance.html");
                break;
            case R.id.essentials_template_syntax:
                getSupportActionBar().setTitle("Template Syntax");
                getSupportActionBar().setSubtitle("Essentials");
                onDocumentationItemSelected("v2/guide/syntax.html");
                break;
            case R.id.essentials_computed_properties_and_watchers:
                getSupportActionBar().setTitle("Computed Properties and Watchers");
                getSupportActionBar().setSubtitle("Essentials");
                onDocumentationItemSelected("v2/guide/computed.html");
                break;
            case R.id.essentials_class_and_style_bindings:
                getSupportActionBar().setTitle("Class and Style Bindings");
                getSupportActionBar().setSubtitle("Essentials");
                onDocumentationItemSelected("v2/guide/class-and-style.html");
                break;
            case R.id.essentials_conditional_rendering:
                getSupportActionBar().setTitle("Conditional Rendering");
                getSupportActionBar().setSubtitle("Essentials");
                onDocumentationItemSelected("v2/guide/conditional.html");
                break;
            case R.id.essentials_list_rendering:
                getSupportActionBar().setTitle("List Rendering");
                getSupportActionBar().setSubtitle("Essentials");
                onDocumentationItemSelected("v2/guide/list.html");
                break;
            case R.id.essentials_event_handling:
                getSupportActionBar().setTitle("Event Handling");
                getSupportActionBar().setSubtitle("Essentials");
                onDocumentationItemSelected("v2/guide/events.html");
                break;
            case R.id.essentials_form_input_bindings:
                getSupportActionBar().setTitle("Form Input Bindings");
                getSupportActionBar().setSubtitle("Essentials");
                onDocumentationItemSelected("v2/guide/forms.html");
                break;
            case R.id.essentials_components:
                getSupportActionBar().setTitle("Components");
                getSupportActionBar().setSubtitle("Essentials");
                onDocumentationItemSelected("v2/guide/components.html");
                break;
            case R.id.advanced_reactivity_in_depth:
                getSupportActionBar().setTitle("Reactivity in Depth");
                getSupportActionBar().setSubtitle("Advanced");
                onDocumentationItemSelected("v2/guide/reactivity.html");
                break;
            case R.id.advanced_transition_effects:
                getSupportActionBar().setTitle("Transition Effects");
                getSupportActionBar().setSubtitle("Advanced");
                onDocumentationItemSelected("v2/guide/transitions.html");
                break;
            case R.id.advanced_transitioning_state:
                getSupportActionBar().setTitle("Transitioning State");
                getSupportActionBar().setSubtitle("Advanced");
                onDocumentationItemSelected("v2/guide/transitioning-state.html");
                break;
            case R.id.advanced_render_functions:
                getSupportActionBar().setTitle("Render Functions");
                getSupportActionBar().setSubtitle("Advanced");
                onDocumentationItemSelected("v2/guide/render-function.html");
                break;
            case R.id.advanced_custom_directives:
                getSupportActionBar().setTitle("Custom Directives");
                getSupportActionBar().setSubtitle("Advanced");
                onDocumentationItemSelected("v2/guide/custom-directive.html");
                break;
            case R.id.advanced_mixins:
                getSupportActionBar().setTitle("Mixins");
                getSupportActionBar().setSubtitle("Advanced");
                onDocumentationItemSelected("v2/guide/mixins.html");
                break;
            case R.id.advanced_plugins:
                getSupportActionBar().setTitle("Plugins");
                getSupportActionBar().setSubtitle("Advanced");
                onDocumentationItemSelected("v2/guide/plugins.html");
                break;
            case R.id.advanced_single_file_components:
                getSupportActionBar().setTitle("Single File Components");
                getSupportActionBar().setSubtitle("Advanced");
                onDocumentationItemSelected("v2/guide/single-file-components.html");
                break;
            case R.id.advanced_deploying_for_production:
                getSupportActionBar().setTitle("Deploying For Production");
                getSupportActionBar().setSubtitle("Advanced");
                onDocumentationItemSelected("v2/guide/deployment.html");
                break;
            case R.id.advanced_routing:
                getSupportActionBar().setTitle("Routing");
                getSupportActionBar().setSubtitle("Advanced");
                onDocumentationItemSelected("v2/guide/routing.html");
                break;
            case R.id.advanced_state_management:
                getSupportActionBar().setTitle("State Management");
                getSupportActionBar().setSubtitle("Advanced");
                onDocumentationItemSelected("v2/guide/state-management.html");
                break;
            case R.id.advanced_unit_testing:
                getSupportActionBar().setTitle("Unit Testing");
                getSupportActionBar().setSubtitle("Advanced");
                onDocumentationItemSelected("v2/guide/unit-testing.html");
                break;
            case R.id.advanced_server_side_rendering:
                getSupportActionBar().setTitle("Server-Side Rendering");
                getSupportActionBar().setSubtitle("Advanced");
                onDocumentationItemSelected("v2/guide/ssr.html");
                break;
            case R.id.migrating_migration_from_vue_1_x:
                getSupportActionBar().setTitle("Migration from Vue 1.x");
                getSupportActionBar().setSubtitle("Migrating");
                onDocumentationItemSelected("v2/guide/migration.html");
                break;
            case R.id.migrating_migration_from_vue_router_0_7_x:
                getSupportActionBar().setTitle("Migration from Vue Router 0.7.x");
                getSupportActionBar().setSubtitle("Migrating");
                onDocumentationItemSelected("v2/guide/migration-vue-router.html");
                break;
            case R.id.migrating_migration_from_vuex_0_6_x_to_1_0:
                getSupportActionBar().setTitle("Migration from Vuex 0.6.x to 1.0");
                getSupportActionBar().setSubtitle("Migrating");
                onDocumentationItemSelected("v2/guide/migration-vuex.html");
                break;
            case R.id.meta_comparison_with_other_frameworks:
                getSupportActionBar().setTitle("Comparison with Other Frameworks");
                getSupportActionBar().setSubtitle("Meta");
                onDocumentationItemSelected("v2/guide/comparison.html");
                break;
            case R.id.meta_join_the_vue_js_community:
                getSupportActionBar().setTitle("Join the Vue.js Community!");
                getSupportActionBar().setSubtitle("Meta");
                onDocumentationItemSelected("v2/guide/join.html");
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void onDocumentationItemSelected(String fileName) {
        this.fileName = fileName;

        switch (fileName) {
            case "v2/guide/installation.html":
                getSupportActionBar().setTitle("Installation");
                getSupportActionBar().setSubtitle("Essentials");
                mNavigationView.setCheckedItem(R.id.essentials_installation);
                break;
            case "v2/guide/index.html":
                getSupportActionBar().setTitle("Introduction");
                getSupportActionBar().setSubtitle("Essentials");
                mNavigationView.setCheckedItem(R.id.essentials_introduction);
                break;
            case "v2/guide/instance.html":
                getSupportActionBar().setTitle("The Vue Instance");
                getSupportActionBar().setSubtitle("Essentials");
                mNavigationView.setCheckedItem(R.id.essentials_the_vue_instance);
                break;
            case "v2/guide/syntax.html":
                getSupportActionBar().setTitle("Template Syntax");
                getSupportActionBar().setSubtitle("Essentials");
                mNavigationView.setCheckedItem(R.id.essentials_template_syntax);
                break;
            case "v2/guide/computed.html":
                getSupportActionBar().setTitle("Computed Properties and Watchers");
                getSupportActionBar().setSubtitle("Essentials");
                mNavigationView.setCheckedItem(R.id.essentials_computed_properties_and_watchers);
                break;
            case "v2/guide/class-and-style.html":
                getSupportActionBar().setTitle("Class and Style Bindings");
                getSupportActionBar().setSubtitle("Essentials");
                mNavigationView.setCheckedItem(R.id.essentials_class_and_style_bindings);
                break;
            case "v2/guide/conditional.html":
                getSupportActionBar().setTitle("Conditional Rendering");
                getSupportActionBar().setSubtitle("Essentials");
                mNavigationView.setCheckedItem(R.id.essentials_conditional_rendering);
                break;
            case "v2/guide/list.html":
                getSupportActionBar().setTitle("List Rendering");
                getSupportActionBar().setSubtitle("Essentials");
                mNavigationView.setCheckedItem(R.id.essentials_list_rendering);
                break;
            case "v2/guide/events.html":
                getSupportActionBar().setTitle("Event Handling");
                getSupportActionBar().setSubtitle("Essentials");
                mNavigationView.setCheckedItem(R.id.essentials_event_handling);
                break;
            case "v2/guide/forms.html":
                getSupportActionBar().setTitle("Form Input Bindings");
                getSupportActionBar().setSubtitle("Essentials");
                mNavigationView.setCheckedItem(R.id.essentials_form_input_bindings);
                break;
            case "v2/guide/components.html":
                getSupportActionBar().setTitle("Components");
                getSupportActionBar().setSubtitle("Essentials");
                mNavigationView.setCheckedItem(R.id.essentials_components);
                break;
            case "v2/guide/reactivity.html":
                getSupportActionBar().setTitle("Reactivity in Depth");
                getSupportActionBar().setSubtitle("Advanced");
                mNavigationView.setCheckedItem(R.id.advanced_reactivity_in_depth);
                break;
            case "v2/guide/transitions.html":
                getSupportActionBar().setTitle("Transition Effects");
                getSupportActionBar().setSubtitle("Advanced");
                mNavigationView.setCheckedItem(R.id.advanced_transition_effects);
                break;
            case "v2/guide/transitioning-state.html":
                getSupportActionBar().setTitle("Transitioning State");
                getSupportActionBar().setSubtitle("Advanced");
                mNavigationView.setCheckedItem(R.id.advanced_transitioning_state);
                break;
            case "v2/guide/render-function.html":
                getSupportActionBar().setTitle("Render Functions");
                getSupportActionBar().setSubtitle("Advanced");
                mNavigationView.setCheckedItem(R.id.advanced_render_functions);
                break;
            case "v2/guide/custom-directive.html":
                getSupportActionBar().setTitle("Custom Directives");
                getSupportActionBar().setSubtitle("Advanced");
                mNavigationView.setCheckedItem(R.id.advanced_custom_directives);
                break;
            case "v2/guide/mixins.html":
                getSupportActionBar().setTitle("Mixins");
                getSupportActionBar().setSubtitle("Advanced");
                mNavigationView.setCheckedItem(R.id.advanced_mixins);
                break;
            case "v2/guide/plugins.html":
                getSupportActionBar().setTitle("Plugins");
                getSupportActionBar().setSubtitle("Advanced");
                mNavigationView.setCheckedItem(R.id.advanced_plugins);
                break;
            case "v2/guide/single-file-components.html":
                getSupportActionBar().setTitle("Single File Components");
                getSupportActionBar().setSubtitle("Advanced");
                mNavigationView.setCheckedItem(R.id.advanced_single_file_components);
                break;
            case "v2/guide/deployment.html":
                getSupportActionBar().setTitle("Deploying For Production");
                getSupportActionBar().setSubtitle("Advanced");
                mNavigationView.setCheckedItem(R.id.advanced_deploying_for_production);
                break;
            case "v2/guide/routing.html":
                getSupportActionBar().setTitle("Routing");
                getSupportActionBar().setSubtitle("Advanced");
                mNavigationView.setCheckedItem(R.id.advanced_routing);
                break;
            case "v2/guide/state-management.html":
                getSupportActionBar().setTitle("State Management");
                getSupportActionBar().setSubtitle("Advanced");
                mNavigationView.setCheckedItem(R.id.advanced_state_management);
                break;
            case "v2/guide/unit-testing.html":
                getSupportActionBar().setTitle("Unit Testing");
                getSupportActionBar().setSubtitle("Advanced");
                mNavigationView.setCheckedItem(R.id.advanced_unit_testing);
                break;
            case "v2/guide/ssr.html":
                getSupportActionBar().setTitle("Server-Side Rendering");
                getSupportActionBar().setSubtitle("Advanced");
                mNavigationView.setCheckedItem(R.id.advanced_server_side_rendering);
                break;
            case "v2/guide/migration.html":
                getSupportActionBar().setTitle("Migration from Vue 1.x");
                getSupportActionBar().setSubtitle("Migrating");
                mNavigationView.setCheckedItem(R.id.migrating_migration_from_vue_1_x);
                break;
            case "v2/guide/migration-vue-router.html":
                getSupportActionBar().setTitle("Migration from Vue Router 0.7.x");
                getSupportActionBar().setSubtitle("Migrating");
                mNavigationView.setCheckedItem(R.id.migrating_migration_from_vue_router_0_7_x);
                break;
            case "v2/guide/migration-vuex.html":
                getSupportActionBar().setTitle("Migration from Vuex 0.6.x to 1.0");
                getSupportActionBar().setSubtitle("Migrating");
                mNavigationView.setCheckedItem(R.id.migrating_migration_from_vuex_0_6_x_to_1_0);
                break;
            case "v2/guide/comparison.html":
                getSupportActionBar().setTitle("Comparison with Other Frameworks");
                getSupportActionBar().setSubtitle("Meta");
                mNavigationView.setCheckedItem(R.id.meta_comparison_with_other_frameworks);
                break;
            case "v2/guide/join.html":
                getSupportActionBar().setTitle("Join the Vue.js Community!");
                getSupportActionBar().setSubtitle("Meta");
                mNavigationView.setCheckedItem(R.id.meta_join_the_vue_js_community);
                break;
        }

        String htmlDocumentation = null;
        try {
            htmlDocumentation = readStream(getAssets().open(fileName));
            htmlDocumentation = htmlDocumentation.replace("</head>", "<link rel=\"stylesheet\" href=\"file:///android_asset/stylesheet.css\"></head>");
        } catch (IOException e) {
            e.printStackTrace();
        }

        mWebView.loadDataWithBaseURL("file:///android_asset/",
                htmlDocumentation, "text/html", "UTF-8", "");
    }

    @NonNull
    private String readStream(InputStream inputStream) {
        StringBuilder builder = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(inputStream, "UTF-8"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                builder.append(line + "\n");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return builder.toString();
    }
}
