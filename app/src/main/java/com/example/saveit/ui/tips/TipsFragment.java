package com.example.saveit.ui.tips;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.saveit.R;

public class TipsFragment extends Fragment {

    private TipsViewModel tipsViewModel;
    private WebView webView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View tipsView = inflater.inflate(R.layout.fragment_tips, container, false);

        WebView webView = tipsView.findViewById(R.id.tipsWebView);
        webView.loadUrl("https://blogsaveit.vercel.app/");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }
        });

        /*
        tipsViewModel =
                new ViewModelProvider(this).get(TipsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tips, container, false);
        final TextView textView = root.findViewById(R.id.text_tips);

        tipsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        */

        //String url = "http://www.example.com";
        //WebView view = (WebView) getView().findViewById(R.id.tipsWebview);
        //view.loadUrl(url);

        return tipsView;

    }
}