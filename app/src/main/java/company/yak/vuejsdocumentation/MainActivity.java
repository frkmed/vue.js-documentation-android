package company.yak.vuejsdocumentation;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.view.Menu;
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
    private static String translationDirectory = "vuejs.org";
    private static Boolean toggleNightMode = false;
    public static final String MY_PREFS_NAME = "MyPrefsFile";

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

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String restoredTranslationDirectory = prefs.getString("translationDirectory", null);
        if (restoredTranslationDirectory != null) {
            translationDirectory = restoredTranslationDirectory;
        }
        this.toggleNightMode = prefs.getBoolean("toggleNightMode", false);

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d(TAG, "shouldOverrideUrlLoading: " + url);

                if (url.startsWith("http")) {
                    CustomTabsIntent tabsIntent = new CustomTabsIntent.Builder().build();
                    tabsIntent.launchUrl(MainActivity.this, Uri.parse(url));
                } else {
                    String fileName = url.replace("file:///android_asset/" + translationDirectory + "/", "");
                    onDocumentationItemSelected(fileName);
                }

                return true;
            }
        });

        getSupportActionBar().setTitle("The Progressive");
        getSupportActionBar().setSubtitle("JavaScript Framework");
        onDocumentationItemSelected(this.fileName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_translations, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.translations_en:
                translationDirectory = "vuejs.org";
                mNavigationView.getMenu().findItem(R.id.essentials).setTitle("Essentials");
                mNavigationView.getMenu().findItem(R.id.advanced).setTitle("Advanced");
                mNavigationView.getMenu().findItem(R.id.migrating).setTitle("Migrating");
                mNavigationView.getMenu().findItem(R.id.meta).setTitle("Meta");
                mNavigationView.getMenu().findItem(R.id.essentials_installation).setTitle("Installation");
                mNavigationView.getMenu().findItem(R.id.essentials_introduction).setTitle("Introduction");
                mNavigationView.getMenu().findItem(R.id.essentials_the_vue_instance).setTitle("The Vue Instance");
                mNavigationView.getMenu().findItem(R.id.essentials_template_syntax).setTitle("Template Syntax");
                mNavigationView.getMenu().findItem(R.id.essentials_computed_properties_and_watchers).setTitle("Computed Properties and Watchers");
                mNavigationView.getMenu().findItem(R.id.essentials_class_and_style_bindings).setTitle("Class and Style Bindings");
                mNavigationView.getMenu().findItem(R.id.essentials_conditional_rendering).setTitle("Conditional Rendering");
                mNavigationView.getMenu().findItem(R.id.essentials_list_rendering).setTitle("List Rendering");
                mNavigationView.getMenu().findItem(R.id.essentials_event_handling).setTitle("Event Handling");
                mNavigationView.getMenu().findItem(R.id.essentials_form_input_bindings).setTitle("Form Input Bindings");
                mNavigationView.getMenu().findItem(R.id.essentials_components).setTitle("Components");
                mNavigationView.getMenu().findItem(R.id.advanced_reactivity_in_depth).setTitle("Reactivity in Depth");
                mNavigationView.getMenu().findItem(R.id.advanced_transition_effects).setTitle("Transition Effects");
                mNavigationView.getMenu().findItem(R.id.advanced_transitioning_state).setTitle("Transitioning State");
                mNavigationView.getMenu().findItem(R.id.advanced_render_functions).setTitle("Render Functions");
                mNavigationView.getMenu().findItem(R.id.advanced_custom_directives).setTitle("Custom Directives");
                mNavigationView.getMenu().findItem(R.id.advanced_mixins).setTitle("Mixins");
                mNavigationView.getMenu().findItem(R.id.advanced_plugins).setTitle("Plugins");
                mNavigationView.getMenu().findItem(R.id.advanced_single_file_components).setTitle("Single File Components");
                mNavigationView.getMenu().findItem(R.id.advanced_production_deployment_tips).setTitle("Deploying For Production");
                mNavigationView.getMenu().findItem(R.id.advanced_routing).setTitle("Routing");
                mNavigationView.getMenu().findItem(R.id.advanced_state_management).setTitle("State Management");
                mNavigationView.getMenu().findItem(R.id.advanced_unit_testing).setTitle("Unit Testing");
                mNavigationView.getMenu().findItem(R.id.advanced_server_side_rendering).setTitle("Server-Side Rendering");
                mNavigationView.getMenu().findItem(R.id.advanced_server_typescript_support).setTitle("TypeScript Support");
                mNavigationView.getMenu().findItem(R.id.migrating_migration_from_vue_1_x).setTitle("Migration from Vue 1.x");
                mNavigationView.getMenu().findItem(R.id.migrating_migration_from_vue_router_0_7_x).setTitle("Migration from Vue Router 0.7.x");
                mNavigationView.getMenu().findItem(R.id.migrating_migration_from_vuex_0_6_x_to_1_0).setTitle("Migration from Vuex 0.6.x to 1.0");
                mNavigationView.getMenu().findItem(R.id.meta_comparison_with_other_frameworks).setTitle("Comparison with Other Frameworks");
                mNavigationView.getMenu().findItem(R.id.meta_join_the_vue_js_community).setTitle("Join the Vue.js Community!");
                break;
            case R.id.translations_cn:
                translationDirectory = "cn.vuejs.org";
                mNavigationView.getMenu().findItem(R.id.essentials).setTitle("基础");
                mNavigationView.getMenu().findItem(R.id.advanced).setTitle("进阶");
                mNavigationView.getMenu().findItem(R.id.migrating).setTitle("迁移");
                mNavigationView.getMenu().findItem(R.id.meta).setTitle("更多");
                mNavigationView.getMenu().findItem(R.id.essentials_installation).setTitle("安装");
                mNavigationView.getMenu().findItem(R.id.essentials_introduction).setTitle("介绍");
                mNavigationView.getMenu().findItem(R.id.essentials_the_vue_instance).setTitle("Vue 实例");
                mNavigationView.getMenu().findItem(R.id.essentials_template_syntax).setTitle("模板语法");
                mNavigationView.getMenu().findItem(R.id.essentials_computed_properties_and_watchers).setTitle("计算属性");
                mNavigationView.getMenu().findItem(R.id.essentials_class_and_style_bindings).setTitle("Class 与 Style 绑定");
                mNavigationView.getMenu().findItem(R.id.essentials_conditional_rendering).setTitle("条件渲染");
                mNavigationView.getMenu().findItem(R.id.essentials_list_rendering).setTitle("列表渲染");
                mNavigationView.getMenu().findItem(R.id.essentials_event_handling).setTitle("事件处理器");
                mNavigationView.getMenu().findItem(R.id.essentials_form_input_bindings).setTitle("表单控件绑定");
                mNavigationView.getMenu().findItem(R.id.essentials_components).setTitle("组件");
                mNavigationView.getMenu().findItem(R.id.advanced_reactivity_in_depth).setTitle("深入响应式原理");
                mNavigationView.getMenu().findItem(R.id.advanced_transition_effects).setTitle("过渡效果");
                mNavigationView.getMenu().findItem(R.id.advanced_transitioning_state).setTitle("过渡状态");
                mNavigationView.getMenu().findItem(R.id.advanced_render_functions).setTitle("Render 函数");
                mNavigationView.getMenu().findItem(R.id.advanced_custom_directives).setTitle("自定义指令");
                mNavigationView.getMenu().findItem(R.id.advanced_mixins).setTitle("混合");
                mNavigationView.getMenu().findItem(R.id.advanced_plugins).setTitle("插件");
                mNavigationView.getMenu().findItem(R.id.advanced_single_file_components).setTitle("单文件组件");
                mNavigationView.getMenu().findItem(R.id.advanced_production_deployment_tips).setTitle("生产环境部署");
                mNavigationView.getMenu().findItem(R.id.advanced_routing).setTitle("路由");
                mNavigationView.getMenu().findItem(R.id.advanced_state_management).setTitle("状态管理");
                mNavigationView.getMenu().findItem(R.id.advanced_unit_testing).setTitle("单元测试");
                mNavigationView.getMenu().findItem(R.id.advanced_server_side_rendering).setTitle("服务端渲染");
                mNavigationView.getMenu().findItem(R.id.advanced_server_typescript_support).setTitle("TypeScript Support");
                mNavigationView.getMenu().findItem(R.id.migrating_migration_from_vue_1_x).setTitle("从 Vue 1.x 迁移");
                mNavigationView.getMenu().findItem(R.id.migrating_migration_from_vue_router_0_7_x).setTitle("从 Vue Router 0.7.x 迁移");
                mNavigationView.getMenu().findItem(R.id.migrating_migration_from_vuex_0_6_x_to_1_0).setTitle("从 Vuex 0.6.x 迁移到 1.0");
                mNavigationView.getMenu().findItem(R.id.meta_comparison_with_other_frameworks).setTitle("对比其他框架");
                mNavigationView.getMenu().findItem(R.id.meta_join_the_vue_js_community).setTitle("加入Vue.js社区");
                break;
            case R.id.translations_jp:
                translationDirectory = "jp.vuejs.org";
                mNavigationView.getMenu().findItem(R.id.essentials).setTitle("基本的な使い方");
                mNavigationView.getMenu().findItem(R.id.advanced).setTitle("高度な使い方");
                mNavigationView.getMenu().findItem(R.id.migrating).setTitle("移行");
                mNavigationView.getMenu().findItem(R.id.meta).setTitle("その他");
                mNavigationView.getMenu().findItem(R.id.essentials_installation).setTitle("インストール");
                mNavigationView.getMenu().findItem(R.id.essentials_introduction).setTitle("はじめに");
                mNavigationView.getMenu().findItem(R.id.essentials_the_vue_instance).setTitle("Vue インスタンス");
                mNavigationView.getMenu().findItem(R.id.essentials_template_syntax).setTitle("テンプレート構文");
                mNavigationView.getMenu().findItem(R.id.essentials_computed_properties_and_watchers).setTitle("算出プロパティとウォッチャ");
                mNavigationView.getMenu().findItem(R.id.essentials_class_and_style_bindings).setTitle("クラスとスタイルのバインディング");
                mNavigationView.getMenu().findItem(R.id.essentials_conditional_rendering).setTitle("条件付きレンダリング");
                mNavigationView.getMenu().findItem(R.id.essentials_list_rendering).setTitle("リストレンダリング");
                mNavigationView.getMenu().findItem(R.id.essentials_event_handling).setTitle("イベントハンドリング");
                mNavigationView.getMenu().findItem(R.id.essentials_form_input_bindings).setTitle("フォーム入力バインディング");
                mNavigationView.getMenu().findItem(R.id.essentials_components).setTitle("コンポーネント");
                mNavigationView.getMenu().findItem(R.id.advanced_reactivity_in_depth).setTitle("リアクティブの探求");
                mNavigationView.getMenu().findItem(R.id.advanced_transition_effects).setTitle("トランジション効果");
                mNavigationView.getMenu().findItem(R.id.advanced_transitioning_state).setTitle("状態のトランジション");
                mNavigationView.getMenu().findItem(R.id.advanced_render_functions).setTitle("描画関数");
                mNavigationView.getMenu().findItem(R.id.advanced_custom_directives).setTitle("カスタムディレクティブ");
                mNavigationView.getMenu().findItem(R.id.advanced_mixins).setTitle("ミックスイン");
                mNavigationView.getMenu().findItem(R.id.advanced_plugins).setTitle("プラグイン");
                mNavigationView.getMenu().findItem(R.id.advanced_single_file_components).setTitle("単一ファイルコンポーネント");
                mNavigationView.getMenu().findItem(R.id.advanced_production_deployment_tips).setTitle("プロダクション環境への配信のヒント");
                mNavigationView.getMenu().findItem(R.id.advanced_routing).setTitle("ルーティング");
                mNavigationView.getMenu().findItem(R.id.advanced_state_management).setTitle("状態管理");
                mNavigationView.getMenu().findItem(R.id.advanced_unit_testing).setTitle("単体テスト");
                mNavigationView.getMenu().findItem(R.id.advanced_server_side_rendering).setTitle("サーバサイドレンダリング");
                mNavigationView.getMenu().findItem(R.id.advanced_server_typescript_support).setTitle("TypeScript のサポート");
                mNavigationView.getMenu().findItem(R.id.migrating_migration_from_vue_1_x).setTitle("Vue 1.x からの移行");
                mNavigationView.getMenu().findItem(R.id.migrating_migration_from_vue_router_0_7_x).setTitle("Vue Router 0.7.x からの移行");
                mNavigationView.getMenu().findItem(R.id.migrating_migration_from_vuex_0_6_x_to_1_0).setTitle("Vuex 0.6.x から 1.0 への移行");
                mNavigationView.getMenu().findItem(R.id.meta_comparison_with_other_frameworks).setTitle("他のフレームワークとの比較");
                mNavigationView.getMenu().findItem(R.id.meta_join_the_vue_js_community).setTitle("Vue.js コミュニティへ参加しましょう!");
                break;
            case R.id.translations_ru:
                translationDirectory = "ru.vuejs.org";
                mNavigationView.getMenu().findItem(R.id.essentials).setTitle("Основы");
                mNavigationView.getMenu().findItem(R.id.advanced).setTitle("Продвинутые темы");
                mNavigationView.getMenu().findItem(R.id.migrating).setTitle("Вопросы миграции");
                mNavigationView.getMenu().findItem(R.id.meta).setTitle("Мета");
                mNavigationView.getMenu().findItem(R.id.essentials_installation).setTitle("Установка");
                mNavigationView.getMenu().findItem(R.id.essentials_introduction).setTitle("Введение");
                mNavigationView.getMenu().findItem(R.id.essentials_the_vue_instance).setTitle("Экземпляр Vue");
                mNavigationView.getMenu().findItem(R.id.essentials_template_syntax).setTitle("Синтаксис шаблонов");
                mNavigationView.getMenu().findItem(R.id.essentials_computed_properties_and_watchers).setTitle("Вычисляемые свойства и слежение");
                mNavigationView.getMenu().findItem(R.id.essentials_class_and_style_bindings).setTitle("Работа с классами и стилями");
                mNavigationView.getMenu().findItem(R.id.essentials_conditional_rendering).setTitle("Условный рендеринг");
                mNavigationView.getMenu().findItem(R.id.essentials_list_rendering).setTitle("Рендеринг списков");
                mNavigationView.getMenu().findItem(R.id.essentials_event_handling).setTitle("Обработка событий");
                mNavigationView.getMenu().findItem(R.id.essentials_form_input_bindings).setTitle("Работа с формами");
                mNavigationView.getMenu().findItem(R.id.essentials_components).setTitle("Компоненты");
                mNavigationView.getMenu().findItem(R.id.advanced_reactivity_in_depth).setTitle("Подробно о реактивности");
                mNavigationView.getMenu().findItem(R.id.advanced_transition_effects).setTitle("Анимационные эффекты переходов");
                mNavigationView.getMenu().findItem(R.id.advanced_transitioning_state).setTitle("Анимирование переходов между состояниями");
                mNavigationView.getMenu().findItem(R.id.advanced_render_functions).setTitle("Render-функции");
                mNavigationView.getMenu().findItem(R.id.advanced_custom_directives).setTitle("Пользовательские директивы");
                mNavigationView.getMenu().findItem(R.id.advanced_mixins).setTitle("Примеси");
                mNavigationView.getMenu().findItem(R.id.advanced_plugins).setTitle("Плагины");
                mNavigationView.getMenu().findItem(R.id.advanced_single_file_components).setTitle("Однофайловые компоненты");
                mNavigationView.getMenu().findItem(R.id.advanced_production_deployment_tips).setTitle("Советы по развёртыванию");
                mNavigationView.getMenu().findItem(R.id.advanced_routing).setTitle("Роутинг");
                mNavigationView.getMenu().findItem(R.id.advanced_state_management).setTitle("Управление состоянием приложения");
                mNavigationView.getMenu().findItem(R.id.advanced_unit_testing).setTitle("Модульное тестирование");
                mNavigationView.getMenu().findItem(R.id.advanced_server_side_rendering).setTitle("SSR. Рендеринг на стороне сервера");
                mNavigationView.getMenu().findItem(R.id.advanced_server_typescript_support).setTitle("Поддержка TypeScript");
                mNavigationView.getMenu().findItem(R.id.migrating_migration_from_vue_1_x).setTitle("Миграция с Vue 1.x");
                mNavigationView.getMenu().findItem(R.id.migrating_migration_from_vue_router_0_7_x).setTitle("Миграция с Vue Router 0.7.x");
                mNavigationView.getMenu().findItem(R.id.migrating_migration_from_vuex_0_6_x_to_1_0).setTitle("Миграция с Vuex 0.6.x на 1.0");
                mNavigationView.getMenu().findItem(R.id.meta_comparison_with_other_frameworks).setTitle("Сравнение с другими фреймворками");
                mNavigationView.getMenu().findItem(R.id.meta_join_the_vue_js_community).setTitle("Присоединяйтесь к сообществу Vue.js!");
                break;
            case R.id.translations_kr:
                translationDirectory = "kr.vuejs.org";
                mNavigationView.getMenu().findItem(R.id.essentials).setTitle("필수");
                mNavigationView.getMenu().findItem(R.id.advanced).setTitle("고급");
                mNavigationView.getMenu().findItem(R.id.migrating).setTitle("마이그레이션 방법");
                mNavigationView.getMenu().findItem(R.id.meta).setTitle("기타");
                mNavigationView.getMenu().findItem(R.id.essentials_installation).setTitle("설치방법");
                mNavigationView.getMenu().findItem(R.id.essentials_introduction).setTitle("시작하기");
                mNavigationView.getMenu().findItem(R.id.essentials_the_vue_instance).setTitle("Vue 인스턴스");
                mNavigationView.getMenu().findItem(R.id.essentials_template_syntax).setTitle("템플릿 문법");
                mNavigationView.getMenu().findItem(R.id.essentials_computed_properties_and_watchers).setTitle("계산된 속성과 감시자");
                mNavigationView.getMenu().findItem(R.id.essentials_class_and_style_bindings).setTitle("클래스와 스타일 바인딩");
                mNavigationView.getMenu().findItem(R.id.essentials_conditional_rendering).setTitle("조건부 렌더링");
                mNavigationView.getMenu().findItem(R.id.essentials_list_rendering).setTitle("리스트 렌더링");
                mNavigationView.getMenu().findItem(R.id.essentials_event_handling).setTitle("이벤트 핸들링");
                mNavigationView.getMenu().findItem(R.id.essentials_form_input_bindings).setTitle("폼 입력 바인딩");
                mNavigationView.getMenu().findItem(R.id.essentials_components).setTitle("컴포넌트");
                mNavigationView.getMenu().findItem(R.id.advanced_reactivity_in_depth).setTitle("반응형에 대해 깊이 알아보기");
                mNavigationView.getMenu().findItem(R.id.advanced_transition_effects).setTitle("전환 효과");
                mNavigationView.getMenu().findItem(R.id.advanced_transitioning_state).setTitle("트렌지션 상태");
                mNavigationView.getMenu().findItem(R.id.advanced_render_functions).setTitle("렌더 함수");
                mNavigationView.getMenu().findItem(R.id.advanced_custom_directives).setTitle("사용자 지정 디렉티브");
                mNavigationView.getMenu().findItem(R.id.advanced_mixins).setTitle("믹스인");
                mNavigationView.getMenu().findItem(R.id.advanced_plugins).setTitle("플러그인");
                mNavigationView.getMenu().findItem(R.id.advanced_single_file_components).setTitle("단일 파일 컴포넌트");
                mNavigationView.getMenu().findItem(R.id.advanced_production_deployment_tips).setTitle("프로덕션 배포 팁");
                mNavigationView.getMenu().findItem(R.id.advanced_routing).setTitle("라우팅");
                mNavigationView.getMenu().findItem(R.id.advanced_state_management).setTitle("상태 관리");
                mNavigationView.getMenu().findItem(R.id.advanced_unit_testing).setTitle("단위 테스팅");
                mNavigationView.getMenu().findItem(R.id.advanced_server_side_rendering).setTitle("서버사이드 렌더링");
                mNavigationView.getMenu().findItem(R.id.advanced_server_typescript_support).setTitle("TypeScript 지원");
                mNavigationView.getMenu().findItem(R.id.migrating_migration_from_vue_1_x).setTitle("Vue 1.x에서 마이그레이션");
                mNavigationView.getMenu().findItem(R.id.migrating_migration_from_vue_router_0_7_x).setTitle("Vue Router 0.7.x으로 부터 마이그레이션");
                mNavigationView.getMenu().findItem(R.id.migrating_migration_from_vuex_0_6_x_to_1_0).setTitle("Vuex 0.6.x에서 1.0로 마이그레이션");
                mNavigationView.getMenu().findItem(R.id.meta_comparison_with_other_frameworks).setTitle("다른 프레임워크와의 비교");
                mNavigationView.getMenu().findItem(R.id.meta_join_the_vue_js_community).setTitle("Vue.js 커뮤니티에 참여하세요!");
                break;
            case R.id.toggle_night_mode:
                if (toggleNightMode.booleanValue() == false) {
                    toggleNightMode = true;
                } else {
                    toggleNightMode = false;
                }

                onDocumentationItemSelected(this.fileName);
                break;
        }

        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        if (item.getItemId() != R.id.toggle_night_mode) {
            editor.putString("translationDirectory", translationDirectory);
            editor.commit();
        } else if (item.getItemId() == R.id.toggle_night_mode) {
            editor.putBoolean("toggleNightMode", toggleNightMode);
            editor.commit();
        }

        onDocumentationItemSelected(this.fileName);
        return true;
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
            case R.id.advanced_production_deployment_tips:
                getSupportActionBar().setTitle("Production Deployment Tips");
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
            case R.id.advanced_server_typescript_support:
                getSupportActionBar().setTitle("TypeScript Support");
                getSupportActionBar().setSubtitle("Advanced");
                onDocumentationItemSelected("v2/guide/typescript.html");
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
                getSupportActionBar().setTitle("Production Deployment Tips");
                getSupportActionBar().setSubtitle("Advanced");
                mNavigationView.setCheckedItem(R.id.advanced_production_deployment_tips);
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
            case "v2/guide/typescript.html":
                getSupportActionBar().setTitle("TypeScript Support");
                getSupportActionBar().setSubtitle("Advanced");
                mNavigationView.setCheckedItem(R.id.advanced_server_typescript_support);
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
            htmlDocumentation = readStream(getAssets().open(translationDirectory + "/" + fileName));

            if (toggleNightMode.booleanValue() == true) {
                htmlDocumentation = htmlDocumentation.replace("</head>", "<link rel=\"stylesheet\" href=\"file:///android_asset/stylesheet.css\"><link rel=\"stylesheet\" href=\"file:///android_asset/nighttime.css\"></head>");
            } else {
                htmlDocumentation = htmlDocumentation.replace("</head>", "<link rel=\"stylesheet\" href=\"file:///android_asset/stylesheet.css\"></head>");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        mWebView.loadDataWithBaseURL("file:///android_asset/" + translationDirectory + "/",
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
