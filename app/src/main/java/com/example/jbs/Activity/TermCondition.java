package com.example.jbs.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jbs.R;

public class TermCondition extends AppCompatActivity implements View.OnClickListener {
    private TextView termConditionTextView;
    private Button acceptBtn;
    private Intent phoneRegisterIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_condition);
        getSupportActionBar().hide();

        phoneRegisterIntent = new Intent(this, PhoneRegisterActivity.class);

        termConditionTextView = findViewById(R.id.tvTermCondition);
        termConditionTextView.setMovementMethod(new ScrollingMovementMethod());

        acceptBtn = findViewById(R.id.btnAccept);
        acceptBtn.setOnClickListener(this);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            termConditionTextView.setText(Html.fromHtml(TermConditionHTML, Html.FROM_HTML_MODE_COMPACT));
        } else {
            termConditionTextView.setText(Html.fromHtml(TermConditionHTML));
        }


    }

    private final String TermConditionHTML = "" +
            "<h1>Lorem Ipsum</h1>" +
            "<div id=\"Content\">\n" +
            "<div id=\"bannerL\"><div id=\"div-gpt-ad-1474537762122-2\" data-google-query-id=\"CMW2mpWQyOACFQwovQodzkoHOg\" style=\"\">\n" +
            "\n" +
            "<div id=\"google_ads_iframe_/15188745/Lipsum-Unit3_0__container__\" style=\"border: 0pt none; display: inline-block; width: 160px; height: 600px;\"><iframe frameborder=\"0\" src=\"https://tpc.googlesyndication.com/safeframe/1-0-32/html/container.html\" id=\"google_ads_iframe_/15188745/Lipsum-Unit3_0\" title=\"3rd party ad content\" name=\"\" scrolling=\"no\" marginwidth=\"0\" marginheight=\"0\" width=\"160\" height=\"600\" data-is-safeframe=\"true\" sandbox=\"allow-forms allow-pointer-lock allow-popups allow-popups-to-escape-sandbox allow-same-origin allow-scripts allow-top-navigation-by-user-activation\" data-google-container-id=\"2\" style=\"border: 0px; vertical-align: bottom;\" data-load-complete=\"true\"></iframe></div></div></div>\n" +
            "<div id=\"bannerR\"><div id=\"div-gpt-ad-1474537762122-3\" data-google-query-id=\"CKbZq5WQyOACFRxsvQoduYkCeA\" style=\"\">\n" +
            "\n" +
            "<div id=\"google_ads_iframe_/15188745/Lipsum-Unit4_0__container__\" style=\"border: 0pt none; display: inline-block; width: 160px; height: 600px;\"><iframe frameborder=\"0\" src=\"https://tpc.googlesyndication.com/safeframe/1-0-32/html/container.html\" id=\"google_ads_iframe_/15188745/Lipsum-Unit4_0\" title=\"3rd party ad content\" name=\"\" scrolling=\"no\" marginwidth=\"0\" marginheight=\"0\" width=\"160\" height=\"600\" data-is-safeframe=\"true\" sandbox=\"allow-forms allow-pointer-lock allow-popups allow-popups-to-escape-sandbox allow-same-origin allow-scripts allow-top-navigation-by-user-activation\" data-google-container-id=\"3\" style=\"border: 0px; vertical-align: bottom;\" data-load-complete=\"true\"></iframe></div></div></div>\n" +
            "<div class=\"boxed\"><!-- \n" +
            "\n" +
            "\n" +
            "If you want to use Lorem Ipsum within another program please contact us for details\n" +
            "on our API rather than parse the HTML below, we have XML and JSON available.\n" +
            "\n" +
            "\n" +
            " --><div id=\"lipsum\">\n" +
            "<p>\n" +
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed convallis ligula dui, non sollicitudin orci pharetra ut. Integer lobortis neque sed neque tincidunt vestibulum. Mauris vitae urna tincidunt, fringilla neque nec, luctus tortor. Curabitur ornare ultrices quam a placerat. Donec vehicula ante vel lacus tempus mollis. Nam eu tincidunt est. Suspendisse venenatis viverra elit a pellentesque. Mauris a malesuada justo. Nunc hendrerit nisi in lectus placerat ornare. Morbi eget justo est. Vestibulum porttitor efficitur vulputate. Vestibulum ligula dui, rutrum eget nibh nec, accumsan placerat enim. Etiam ullamcorper ut massa sed maximus. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi sed tellus nibh. Vivamus a faucibus eros.\n" +
            "</p>\n" +
            "<p>\n" +
            "Curabitur accumsan lacus eget sem vehicula maximus. Aenean massa quam, tempus id libero sed, luctus maximus turpis. In eu risus ultricies, ornare sem nec, venenatis orci. Integer ac risus ac nisi pretium tristique sed ut turpis. Integer at porta quam. Aenean quis consectetur metus. Cras auctor placerat felis. Duis ligula nisi, condimentum ac facilisis eu, rhoncus nec purus. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas ullamcorper lobortis enim, id semper turpis molestie vitae. In sit amet auctor eros. Vivamus efficitur nunc at tempor mollis. Nulla non dapibus dui. Fusce luctus, sem ut sodales congue, massa leo dapibus nisl, sit amet porta risus lacus vel tortor. Pellentesque lacinia odio sit amet ex finibus laoreet. Cras ac justo sollicitudin, posuere eros sed, ultricies nulla.\n" +
            "</p>\n" +
            "<p>\n" +
            "Ut faucibus lorem vitae lorem gravida, a rutrum justo tincidunt. Nulla et eleifend ante, a lacinia elit. Proin consequat semper diam, non pretium nisi semper sed. Ut vitae varius mi. Nullam ex ante, malesuada quis fermentum eu, laoreet at orci. Proin libero nulla, aliquet ac maximus at, iaculis eu nulla. Ut dictum, purus et molestie porta, sapien ligula facilisis risus, at feugiat libero enim vel nunc. Proin vel volutpat nibh, nec malesuada leo. Quisque luctus vel ex eget tincidunt. Sed metus diam, posuere id fermentum in, placerat in massa. Nullam vestibulum elementum tristique. Sed odio elit, rutrum quis lacus ac, elementum consequat est. Curabitur finibus enim vitae quam facilisis commodo. Praesent aliquet, ligula quis semper dapibus, nibh nisl faucibus nisi, ac mollis felis diam id dui. Phasellus vitae velit nulla. Nunc at dolor non arcu commodo vulputate et non turpis.\n" +
            "</p>\n" +
            "<p>\n" +
            "In eget massa consectetur, venenatis dolor ac, vulputate lectus. Aliquam aliquam a augue quis lacinia. Proin ullamcorper purus non nulla semper, in tincidunt dui commodo. Integer gravida suscipit ante. Maecenas volutpat, quam quis dignissim interdum, enim urna finibus urna, nec elementum tortor neque mattis enim. Pellentesque mauris arcu, viverra vel arcu quis, rhoncus ultricies nibh. Duis nec malesuada massa. Aliquam enim massa, sagittis in pretium ut, molestie ac nunc. Nam in dolor et nisi rhoncus facilisis.\n" +
            "</p>\n" +
            "<p>\n" +
            "Curabitur in fringilla leo, a dictum massa. Suspendisse laoreet luctus arcu, sit amet condimentum eros accumsan eget. Praesent eget erat neque. Quisque blandit sapien a pulvinar eleifend. Donec gravida mauris volutpat mauris rhoncus, vel mollis augue dapibus. Vivamus lobortis ligula ligula, sit amet vehicula quam pulvinar eget. Vivamus facilisis commodo felis ut posuere. Vestibulum augue sapien, consectetur in dignissim eget, faucibus quis ex. Morbi enim ex, sagittis sit amet hendrerit vitae, sollicitudin eu nisl. Aenean molestie neque ut massa iaculis, non convallis nunc sagittis. Nullam quis rhoncus ex. Etiam rutrum mi nunc, at tristique nulla consequat vitae. Curabitur ac sapien venenatis, laoreet augue sed, semper tortor. Sed pretium mi turpis.\n" +
            "</p></div>\n" +
            "<div id=\"generated\">Generated 5 paragraphs, 557 words, 3717 bytes of <a href=\"https://www.lipsum.com/\" title=\"Lorem Ipsum\">Lorem Ipsum</a></div>\n" +
            "</div>\n" +
            "</div>";

    @Override
    public void onClick(View v) {
        startActivity(phoneRegisterIntent);
        finish();
    }
}
